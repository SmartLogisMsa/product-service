package com.smartlogis.productservice.interfaces.dto.event;

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
	private UUID productId;
	private UUID departureHubId;
	private UUID destinationHubId;
	private String address;
	private UUID receiptUserId;
}
