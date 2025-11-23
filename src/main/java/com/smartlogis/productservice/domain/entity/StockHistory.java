package com.smartlogis.productservice.domain.entity;

import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.smartlogis.common.domain.AbstractEntity;
import com.smartlogis.productservice.domain.exception.InvalidProductException;
import com.smartlogis.productservice.domain.exception.InvalidQuantityException;
import com.smartlogis.productservice.domain.exception.InvalidStockException;
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
@Table(name = "p_stock_history")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class StockHistory extends AbstractEntity {

	//재고 기록 Id
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	//상품 id
	@Column(name = "product_id", nullable = false)
	private UUID productId;

	//변경 타입
	@Enumerated(EnumType.STRING)
	@Column(name = "change_type", nullable = false)
	private ChangeType changeType;

	//변화량
	@Column(name = "change_quantity", nullable = false)
	private Integer changeQuantity;

	//변경 전 재고
	@Column(name = "before_quantity", nullable = false)
	private Integer beforeQuantity;

	//변경 후 재고
	@Column(name = "after_quantity", nullable = false)
	private Integer afterQuantity;

	//변경 원인이 되는 서비스
	@Enumerated(EnumType.STRING)
	@Column(name = "change_source", nullable = false)
	private ChangeSource changeSource;


	//=========================================

	//1. 유효성 검증
	//상품 정보 검증
	private static void validateProductId(UUID productId) {
		if(productId == null) {
			throw new InvalidProductException(ProductCode.INVALID_PRODUCT_ID);
		}
	}

	//재고량 검증
	private static void validateQuantity(int changeQuantity, int beforeQuantity, int afterQuantity) {
		if(changeQuantity <= 0){
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}
		if(beforeQuantity < 0 || afterQuantity < 0){
			throw new InvalidStockException(ProductCode.INVALID_STOCK);
		}
		if(Math.abs(afterQuantity - beforeQuantity) != changeQuantity){
			throw new InvalidQuantityException(ProductCode.INVALID_QUANTITY);
		}
	}

	//===========================================

	//2. 재고 기록 생성
	public static StockHistory create(
		UUID productId,
		ChangeType changeType,
		int changeQuantity,
		int beforeQuantity,
		int afterQuantity,
		ChangeSource changeSource
		){
		validateProductId(productId);
		validateQuantity(changeQuantity, beforeQuantity, afterQuantity);

		return StockHistory.builder()
			.productId(productId)
			.changeType(changeType)
			.changeQuantity(changeQuantity)
			.beforeQuantity(beforeQuantity)
			.afterQuantity(afterQuantity)
			.changeSource(changeSource)
			.build();
	}
}
