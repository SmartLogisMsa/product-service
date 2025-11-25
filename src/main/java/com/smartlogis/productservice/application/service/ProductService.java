package com.smartlogis.productservice.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.productservice.domain.entity.ChangeSource;
import com.smartlogis.productservice.domain.entity.ChangeType;
import com.smartlogis.productservice.domain.entity.Product;
import com.smartlogis.productservice.domain.entity.StockHistory;
import com.smartlogis.productservice.domain.exception.ProductAlreadyExistException;
import com.smartlogis.productservice.domain.exception.ProductCode;
import com.smartlogis.productservice.domain.exception.ProductNotFoundException;
import com.smartlogis.productservice.domain.repository.ProductRepository;
import com.smartlogis.productservice.domain.repository.StockHistoryRepository;
import com.smartlogis.productservice.infrastructure.event.publisher.ProductEventPublisher;
import com.smartlogis.productservice.interfaces.dto.event.CompanyInactivatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.CompanyOrderCreatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.ProductOrderCreatedEvent;
import com.smartlogis.productservice.interfaces.dto.event.OrderCanceledEvent;
import com.smartlogis.productservice.interfaces.dto.request.CreateProductRequest;
import com.smartlogis.productservice.interfaces.dto.request.InventoryCheckRequest;
import com.smartlogis.productservice.interfaces.dto.request.ProductSearchCondition;
import com.smartlogis.productservice.interfaces.dto.request.UpdateProductRequest;
import com.smartlogis.productservice.interfaces.dto.request.UpdateStockRequest;
import com.smartlogis.productservice.interfaces.dto.response.InventoryCheckResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductDetailResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductListResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	private final StockHistoryRepository stockHistoryRepository;
	private final ProductEventPublisher productEventPublisher;

	//1. 상품 생성
	@Transactional
	public ProductResponse createProduct(CreateProductRequest request) {

		//중복 등록 확인
		boolean exists = productRepository.existsByNameAndCompanyId(request.getName(), request.getCompanyId());
		if (exists) {
			throw new ProductAlreadyExistException(ProductCode.PRODUCT_ALREADY_EXIST);
		}

		Product newProduct = Product.create(request);

		Product savedProduct = productRepository.save(newProduct);

		//최초 상품 입고 기록
		if(savedProduct.getStock() > 0){
			StockHistory stockHistory = savedProduct.recordStockChange(
				ChangeType.INITIAL,
				savedProduct.getStock(),
				ChangeSource.COMPANY_SERVICE
			);
			stockHistoryRepository.save(stockHistory);
		}

		return ProductResponse.of(savedProduct);
	}

	//2. 상품 수정
	@Transactional
	public ProductResponse updateProduct(UUID id, UpdateProductRequest request) {

		//상품 존재 여부 확인
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

		if(request.getName() != null){
			product.changeName(request.getName());
		}
		if(request.getStatus() != null){
			product.changeStatus(request.getStatus());
		}

		return ProductResponse.of(product);
	}

	//3. 관리자의 상품 재고 수정
	@Transactional
	public ProductResponse updateStock(UUID id, UpdateStockRequest request) {

		//상품 존재 여부 확인
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

		int beforeStock = product.getStock();
		int newStock = request.getStock();

		//변화량
		int quantityChange = Math.abs(newStock - beforeStock);

		//이전 재고 값과 비교해서 changeType 결정
		ChangeType changeType = newStock >= beforeStock ? ChangeType.STOCK_IN : ChangeType.STOCK_OUT;

		StockHistory stockHistory = product.recordStockChange(
			changeType,
			quantityChange,
			ChangeSource.ADMIN
		);

		stockHistoryRepository.save(stockHistory);

		return ProductResponse.of(product);
	}

	//4. 상품 삭제
	@Transactional
	public void deleteProduct(UUID id) {

		//상품 존재 여부 확인
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

		product.delete();
	}

	//5. 상품 상세 조회
	@Transactional(readOnly = true)
	public ProductDetailResponse getProductDetails(UUID id) {

		//상품 존재 여부 확인
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

		return ProductDetailResponse.from(product);
	}

	//6. 상품 목록 조회
	@Transactional(readOnly = true)
	public PageResponse<ProductListResponse> getProducts(ProductSearchCondition condition, Pageable pageable) {

		Page<Product> products = productRepository.searchProducts(condition, pageable);

		return PageResponse.from(
			products.map(ProductListResponse::from)
		);
 	}

	 //7. 재고 확인
	@Transactional(readOnly = true)
	public InventoryCheckResponse checkInventory(InventoryCheckRequest request) {

		List<InventoryCheckResponse.Result> results = request.getInventoryChecks().stream()
			.map(item -> {
				Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

				boolean available = product.getStock() >= item.getRequestedQuantity();
				return new InventoryCheckResponse.Result(item.getProductId(), available);
			})
			.toList();

		return new InventoryCheckResponse(results);
	}

	//8. 주문 생성 이벤트 처리
	@Transactional
	public void applyOrderStock(CompanyOrderCreatedEvent event){

		//주문 아이템 하나씩 순회하면서
		event.getOrderItems().forEach(item -> {

			UUID productId = item.getProductId();
			int quantity = item.getQuantity();

			//상품 아이디로 상품 조회
			Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

			//재고 처리
			StockHistory stockHistory = product.recordStockChange(
				ChangeType.STOCK_OUT,
				quantity,
				ChangeSource.ORDER_SERVICE
			);

			//기록 저장
			stockHistoryRepository.save(stockHistory);
		});
	}

	//9. 주문 취소 이벤트 처리
	@Transactional
	public void revertOrderStock(OrderCanceledEvent event){

		event.getOrderItems().forEach(item -> {

			UUID productId = item.getProductId();
			int quantity = item.getQuantity();

			//상품 아이디로 상품 조회
			Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND));

			StockHistory stockHistory = product.recordStockChange(
				ChangeType.RETURNED,
				quantity,
				ChangeSource.ORDER_SERVICE
			);

			stockHistoryRepository.save(stockHistory);
		});
	}

	//10. 주문 생성 이벤트에 hub id 추가하기
	@Transactional
	public void handleOrderCreatedEvent(CompanyOrderCreatedEvent event){

		for(CompanyOrderCreatedEvent.OrderItemDetail item : event.getOrderItems()) {
			UUID productId = item.getProductId();

			//상품 아이디로 상품 조회
			Optional<Product> productOpt = productRepository.findById(productId);

			if(productOpt.isEmpty()){
				log.warn("상품 없음: {}", productId);
				continue;
			}
			Product product = productOpt.get();

			//출발 허브 id 포함시키기
			UUID departureHubId = product.getHubId();

			ProductOrderCreatedEvent productEvent = ProductOrderCreatedEvent.builder()
				.orderId(event.getOrderId())
				.productId(productId)
				.departureHubId(departureHubId)
				.destinationHubId(event.getDestinationHubId())
				.address(event.getAddress())
				.receiptUserId(event.getReceiptUserId())
				.build();

			productEventPublisher.publishToHub(productEvent);
		}
	}

	//11. 업체 비활성화 이벤트 처리
	@Transactional
	public void handleCompanyInactivated(CompanyInactivatedEvent event){
		UUID companyId = event.companyId();

		List<Product> products = productRepository.findByCompanyId(companyId);

		products.forEach(Product::inactivate);
	}
}
