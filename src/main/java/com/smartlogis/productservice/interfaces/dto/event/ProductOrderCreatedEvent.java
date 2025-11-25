package com.smartlogis.productservice.interfaces.dto.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrderCreatedEvent {

	private UUID orderId;
	private UUID receiptCompanyId;
	private List<ProductOrderItemDetail> orderItems;
	private String requestDetails;
	private String address;
	private UUID receiptUserId;
	private LocalDateTime createdAt;
	private String createdBy;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ProductOrderItemDetail {
		private UUID productId;
		private Integer quantity;
		private UUID departureHubId;
	}
}
