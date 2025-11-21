package com.smartlogis.productservice.interfaces.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smartlogis.productservice.domain.entity.Product;
import com.smartlogis.productservice.domain.entity.ProductStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "상품 상세 조회 응답")
public class ProductDetailResponse {

	@Schema(description = "상품 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID id;

	@Schema(description = "상품명", example = "고양이케이스", nullable = false)
	private String name;

	@Schema(description = "생산 업체 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID companyId;

	@Schema(description = "소속 허브 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID hubId;

	@Schema(description = "상품 상태", example = "ACTIVE", nullable = false)
	private ProductStatus status;

	@Schema(description = "재고", example = "50", nullable = false)
	private Integer stock;

	@Schema(description = "담당 매니저 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID managerId;

	@Schema(description = "생성 일시", example = "2025-11-19T09:30:00", nullable = false)
	private LocalDateTime createdAt;

	@Schema(description = "생성자", example = "admin", nullable = false)
	private String createdBy;

	@Schema(description = "수정 일시", example = "2025-11-19T10:00:00", nullable = false)
	private LocalDateTime updatedAt;

	@Schema(description = "수정자", example = "manager", nullable = false)
	private String updatedBy;

	@Schema(description = "삭제 일시", example = "2025-11-19T11:00:00", nullable = true)
	private LocalDateTime deletedAt;

	@Schema(description = "삭제자", example = "admin", nullable = true)
	private String deletedBy;

	public static ProductDetailResponse from(Product product){
		return ProductDetailResponse.builder()
			.id(product.getId())
			.name(product.getName())
			.companyId(product.getCompanyId())
			.hubId(product.getHubId())
			.stock(product.getStock())
			.status(product.getStatus())
			.managerId(product.getManagerId())
			.createdAt(product.getCreatedAt())
			.createdBy(product.getCreatedBy())
			.updatedAt(product.getUpdatedAt())
			.updatedBy(product.getUpdatedBy())
			.deletedAt(product.getDeletedAt())
			.deletedBy(product.getDeletedBy())
			.build();
	}
}
