package com.smartlogis.productservice.interfaces.dto.response;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "재고 확인 응답")
public class InventoryCheckResponse {

	private List<Result> results;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Result {

		@Schema(description = "상품 id")
		private UUID productId;

		@Schema(description = "주문 가능 여부")
		private boolean available;
	}
}
