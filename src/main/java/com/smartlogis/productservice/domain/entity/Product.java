package com.smartlogis.productservice.domain.entity;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.smartlogis.common.domain.AbstractEntity;
import com.smartlogis.productservice.domain.exception.InsufficientStockException;
import com.smartlogis.productservice.domain.exception.InvalidCompanyIdException;
import com.smartlogis.productservice.domain.exception.InvalidHubIdException;
import com.smartlogis.productservice.domain.exception.InvalidManagerIdException;
import com.smartlogis.productservice.domain.exception.InvalidNameException;
import com.smartlogis.productservice.domain.exception.InvalidQuantityException;
import com.smartlogis.productservice.domain.exception.InvalidStatusException;
import com.smartlogis.productservice.domain.exception.ProductCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	//재고 임계치
	private static final int STOCK_THRESHOLD = 50;

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
	// 재고 감소
	public void decreaseStock(int quantity){
		if(quantity <= 0){
			//유효하지 않은 상품 개수
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}
		if(stock < quantity){
			//재고 부족
			throw new InsufficientStockException(ProductCode.INSUFFICIENT_STOCK);
		}

		stock -= quantity;
	}

	// 재고 추가
	public void addStock(int quantity){
		if(quantity <= 0){
			//유효하지 않은 상품 개수
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}
		stock += quantity;
	}

	// 임계치 이하인지 판단
	public boolean isStockBelowThreshold(){
		return stock <= STOCK_THRESHOLD;
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

}
