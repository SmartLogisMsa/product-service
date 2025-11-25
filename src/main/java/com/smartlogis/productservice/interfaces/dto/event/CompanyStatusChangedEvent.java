package com.smartlogis.productservice.interfaces.dto.event;

import java.util.UUID;

public record CompanyStatusChangedEvent(
	UUID companyId,
	String status
) {
}
