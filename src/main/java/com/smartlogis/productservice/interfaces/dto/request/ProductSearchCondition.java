package com.smartlogis.productservice.interfaces.dto.request;

import java.util.UUID;

import com.smartlogis.productservice.domain.entity.ProductStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "조건별 상품 조회 요청")
public class ProductSearchCondition {

	@Schema(description = "상품명별 조회", example = "상품1", nullable = true)
	private String name;

	@Schema(description = "허브 id별 조회", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = true)
	private UUID hubId;

	@Schema(description = "업체 id별 조회", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = true)
	private UUID companyId;

	@Schema(description = "상태별 조회", example = "ACTIVE", nullable = true)
	private ProductStatus status;

}
