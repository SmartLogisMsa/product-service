package com.smartlogis.productservice.interfaces.dto.response;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 요청 공통 응답")
public class ProductResponse {

	@Schema(description = "상품 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID id;

	@Schema(description = "상품명", example = "고양이케이스", nullable = false)
	private String name;

	@Schema(description = "생산 업체 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID companyId;

	@Schema(description = "소속 허브 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID hubId;

	@Schema(description = "재고", example = "50", nullable = false)
	private Integer stock;

	@Schema(description = "상품 상태(ACTIVE/INACTIVE)", example = "ACTIVE",  nullable = false)
	private ProductStatus status;

	@Schema(description = "담당자 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID managerId;

	public static ProductResponse of(Product product) {
		return ProductResponse.builder()
			.id(product.getId())
			.name(product.getName())
			.companyId(product.getCompanyId())
			.hubId(product.getHubId())
			.stock(product.getStock())
			.status(product.getStatus())
			.managerId(product.getManagerId())
			.build();
	}
}
