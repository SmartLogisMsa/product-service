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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "상품 목록 조회 응답")
public class ProductListResponse {

	@Schema(description = "상품 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID id;

	@Schema(description = "상품명", example = "고양이케이스", nullable = false)
	private String name;

	@Schema(description = "상품 상태", example = "ACTIVE", nullable = false)
	private ProductStatus status;

	public static ProductListResponse from(Product product) {
		return ProductListResponse.builder()
			.id(product.getId())
			.name(product.getName())
			.status(product.getStatus())
			.build();
	}
}
