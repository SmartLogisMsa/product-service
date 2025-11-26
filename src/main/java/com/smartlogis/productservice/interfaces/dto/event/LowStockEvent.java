package com.smartlogis.productservice.interfaces.dto.event;

import java.util.UUID;

public record LowStockEvent(
	UUID productId,
	UUID companyId,
	int requiredQuantity
) {
}
