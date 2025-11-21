package com.smartlogis.productservice.interfaces.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 재고 수정 요청")
public class UpdateStockRequest {

	@Schema(description = "재고")
	private Integer stock;
}
