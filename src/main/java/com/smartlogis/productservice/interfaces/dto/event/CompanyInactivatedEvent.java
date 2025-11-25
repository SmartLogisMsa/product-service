package com.smartlogis.productservice.interfaces.dto.event;

import java.util.UUID;

public record CompanyInactivatedEvent(UUID companyId) {
}
