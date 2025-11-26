package com.smartlogis.productservice.interfaces.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartlogis.common.presentation.ApiResponse;
import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.productservice.application.service.ProductService;
import com.smartlogis.productservice.interfaces.dto.request.CreateProductRequest;
import com.smartlogis.productservice.interfaces.dto.request.InventoryCheckRequest;
import com.smartlogis.productservice.interfaces.dto.request.ProductSearchCondition;
import com.smartlogis.productservice.interfaces.dto.request.UpdateProductRequest;
import com.smartlogis.productservice.interfaces.dto.request.UpdateStockRequest;
import com.smartlogis.productservice.interfaces.dto.response.InventoryCheckResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductDetailResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductListResponse;
import com.smartlogis.productservice.interfaces.dto.response.ProductResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 관련 API")
public class ProductController {

	private final ProductService productService;

	//1. 상품 생성
	@PostMapping("")
	@Operation(summary = "새 상품 생성", description = "새로운 상품을 생성합니다.")
	@PreAuthorize("hasRole('MASTER') or hasRole('HUB_MANAGER') or hasRole('COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
		@RequestBody @Valid CreateProductRequest request
	){
		ProductResponse response = productService.createProduct(request);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//2. 상품 수정
	@PutMapping("/{id}")
	@Operation(summary = "상품 수정", description = "상품명 또는 상태를 수정합니다.")
	@PreAuthorize("hasRole('MASTER') or hasRole('HUB_MANAGER') or hasRole('COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
		@PathVariable UUID id,
		@RequestBody @Valid UpdateProductRequest request
	){
		ProductResponse response =  productService.updateProduct(id, request);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//3. 관리자의 상품 재고 수정
	@PatchMapping("/{id}/stock")
	@Operation(summary = "상품 재고 수정", description = "관리자에 의한 상품 재고 수정입니다.")
	@PreAuthorize("hasRole('MASTER')")
	public ResponseEntity<ApiResponse<ProductResponse>> updateStock(
		@PathVariable UUID id,
		@RequestBody @Valid UpdateStockRequest request
	){
		ProductResponse response = productService.updateStock(id, request);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//4. 상품 삭제
	@DeleteMapping("/{id}")
	@Operation(summary = "상품 삭제", description = "하나의 상품을 삭제합니다.")
	@PreAuthorize("hasRole('MASTER') or hasRole('HUB_MANAGER')")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(
		@PathVariable UUID id
	){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	//5. 상품 상세 조회
	@GetMapping("/{id}")
	@Operation(summary = "상품 상세 조회", description = "하나의 상품을 조회합니다.")
	public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductDetails(
		@PathVariable UUID id
	) {
		ProductDetailResponse response = productService.getProductDetails(id);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//6. 상품 목록 조회
	@GetMapping("")
	@Operation(summary = "상품 목록 조회", description = "전체 상품의 목록을 조회합니다.")
	public ResponseEntity<ApiResponse<PageResponse<ProductListResponse>>> getProducts(
		@ModelAttribute ProductSearchCondition condition,
		Pageable pageable
	){
		PageResponse<ProductListResponse> response = productService.getProducts(condition, pageable);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//7. 상품 재고 일괄 확인
	@PostMapping("/inventory/check")
	@Operation(summary = "상품 재고 일괄 확인", description = "재고가 주문 수량 이상 존재하는지 확인합니다.")
	public ResponseEntity<ApiResponse<InventoryCheckResponse>> checkInventory(
		@Valid @RequestBody InventoryCheckRequest request
	){
		InventoryCheckResponse response = productService.checkInventory(request);
		return  ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}
}
