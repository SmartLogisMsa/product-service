package com.smartlogis.productservice.domain.entity;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.smartlogis.common.domain.AbstractEntity;
import com.smartlogis.productservice.domain.exception.InsufficientStockException;
import com.smartlogis.productservice.domain.exception.InvalidChangeTypeException;
import com.smartlogis.productservice.domain.exception.InvalidCompanyIdException;
import com.smartlogis.productservice.domain.exception.InvalidHubIdException;
import com.smartlogis.productservice.domain.exception.InvalidManagerIdException;
import com.smartlogis.productservice.domain.exception.InvalidNameException;
import com.smartlogis.productservice.domain.exception.InvalidQuantityException;
import com.smartlogis.productservice.domain.exception.InvalidStatusException;
import com.smartlogis.productservice.domain.exception.InvalidStockException;
import com.smartlogis.productservice.domain.exception.ProductCode;
import com.smartlogis.productservice.interfaces.dto.request.CreateProductRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_product")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product extends AbstractEntity {

	//재고 임계치(이 이하로 떨어지면 재고 부족 이벤트 발행)
	private static final int STOCK_THRESHOLD = 500;

	//목표 재고(재고 보충 시 기준이 되는 안정적인 재고선)
	private static final int TARGET_STOCK = 1000;

	//이벤트 발행 여부
	@Transient
	private boolean lowStockEventPending = false;

	//상품 식별 id
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	//상품명
	@Column(nullable = false)
	private String name;

	//생산업체 id
	@Column(nullable = false)
	private UUID companyId;

	//소속 허브 id
	@Column(nullable = false)
	private UUID hubId;

	//허브에 존재하는 재고
	@Column(nullable = false)
	private Integer stock;

	//상품 상태(ACTIVE, INACTIVE)
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	//상품 담당자 ID = 업체 담당자
	@Column(nullable = false)
	private UUID managerId;

	//=======================================

	// 1. 널 값 검증 메서드
	// 상품명 검증
	private static void validateName(String name){
		if(name == null || name.isBlank()){
			throw new InvalidNameException(ProductCode.INVALID_NAME);
		}
	}

	// 생산업체 id 검증
	private static void validateCompanyId(UUID companyId){
		if(companyId == null){
			throw new InvalidCompanyIdException(ProductCode.INVALID_COMPANY_ID);
		}
	}

	// 소속 허브 id 검증
	private static void validateHubId(UUID hubId){
		if(hubId == null){
			throw new InvalidHubIdException(ProductCode.INVALID_HUB_ID);
		}
	}

	// 담당자 id 검증
	private static void validateManagerId(UUID managerId){
		if(managerId == null){
			throw new InvalidManagerIdException(ProductCode.INVALID_MANAGER_ID);
		}
	}

	//초기 재고
	private static void validateInitialStock(Integer stock){
		if(stock == null || stock < 0){
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}
	}

	//=======================================

	//2. 데이터 변경 메서드
	// 상품명 변경
	public void changeName(String newName){
		validateName(newName);
		this.name = newName;
	}

	public void changeHubId(UUID newHubId){
		validateHubId(newHubId);
		this.hubId = newHubId;
	}

	// 상품 상태 변경
	public void changeStatus(ProductStatus newStatus){
		if(newStatus == null){
			throw new InvalidStatusException(ProductCode.INVALID_STATUS);
		}

		if(this.status == newStatus){
			return;
		}

		this.status = newStatus;
	}

	// 상품 상태 비활성 -> 활성
	public void activate(){
		if(this.status == ProductStatus.ACTIVE){
			return;
		}
		this.status = ProductStatus.ACTIVE;
	}

	// 상품 상태 활성 -> 비활성
	public void inactivate(){
		if(this.status == ProductStatus.INACTIVE){
			return;
		}
		this.status = ProductStatus.INACTIVE;
	}

	// 상품 삭제 시 비활성화
	@Override
	public void delete(){
		super.delete();
		this.inactivate();
	}

	//=======================================

	//3. 재고 관련 메서드

	//재고 관리 메서드
	public StockHistory recordStockChange(
		ChangeType changeType,
		int quantity,
		ChangeSource changeSource
	){
		if(quantity <= 0){
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}

		int before = this.stock;
		int after;

		//변경 타입별 재고 변화
		switch (changeType) {
			case INITIAL -> {
				before = 0;
				after = quantity;
			}

			case STOCK_IN ->  {
				after = before + quantity;
			}

			case STOCK_OUT ->  {
				if(before < quantity){
					throw new InsufficientStockException(ProductCode.INSUFFICIENT_STOCK);
				}
				after = before - quantity;
			}

			case RETURNED ->  {
				after = before + quantity;
			}

			default -> {
				throw new InvalidChangeTypeException(ProductCode.INVALID_CHANGE_TYPE);
			}
		}

		this.stock = after;

		//재고 임계치 도달/복구
		//1. 임계치 이하로 진입할 때
		if(after <= STOCK_THRESHOLD && before > STOCK_THRESHOLD){
			this.lowStockEventPending = true;
		}

		//2. 임계치 위로 복구될 때 리셋
		if(after > STOCK_THRESHOLD && lowStockEventPending){
			this.lowStockEventPending = false;
		}

		return StockHistory.create(
			this.id,
			changeType,
			quantity,
			before,
			after,
			changeSource
		);
	}

	//이벤트 발행 여부. 발행되었으면 다시 초기화
	public boolean pollLowStockEventPendingAndClear(){
		//이벤트 발행 안되었으면
		if(!this.lowStockEventPending){
			return false;
		}

		//이벤트 발행 되었으면 초기화 후 true 반환
		this.lowStockEventPending = false;
		return true;
	}

	public int getStockThreshold(){
		return STOCK_THRESHOLD;
	}

	public int getTargetStock(){
		return TARGET_STOCK;
	}

	//=======================================

	//4. 생성자
	public static Product create(String name, UUID companyId, UUID hubId, Integer stock, UUID managerId) {
		validateName(name);
		validateCompanyId(companyId);
		validateHubId(hubId);
		validateManagerId(managerId);
		validateInitialStock(stock);

		return Product.builder()
			.name(name)
			.companyId(companyId)
			.hubId(hubId)
			.stock(stock)
			.status(ProductStatus.ACTIVE)
			.managerId(managerId)
			.build();
	}

	public static Product create(CreateProductRequest request){
		return create(
			request.getName(),
			request.getCompanyId(),
			request.getHubId(),
			request.getStock(),
			request.getManagerId()
		);
	}

}
