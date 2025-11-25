package com.smartlogis.productservice.interfaces.dto.event;

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
public class CompanyOrderCreatedEvent {

	private UUID orderId;
	private List<OrderItemDetail> orderItems;
	private String address;
	private UUID receiptUserId;
	private UUID destinationHubId; //도착 허브 id

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class OrderItemDetail {
		private UUID productId;
		private Integer quantity;
	}
}