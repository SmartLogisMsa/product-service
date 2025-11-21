package com.smartlogis.productservice.interfaces.dto.request;

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
@Schema(description = "상품 정보 수정 요청")
public class UpdateProductRequest {

	@Schema(description = "상품명")
	private String name;

	@Schema(description = "상품 상태(ACTIVE/INACTIVE")
	private ProductStatus status;
}
