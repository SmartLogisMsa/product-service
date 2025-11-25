package com.smartlogis.productservice.interfaces.dto.request;

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
@Schema(description = "재고 확인 요청")
public class InventoryCheckRequest {

	private List<ProductQuantity> inventoryChecks;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductQuantity {

		@Schema(description = "상품 id")
		private UUID productId;

		@Schema(description = "필요한 수량")
		private int requestedQuantity;
	}
}
