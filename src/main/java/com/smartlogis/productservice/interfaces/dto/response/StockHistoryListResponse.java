package com.smartlogis.productservice.interfaces.dto.response;

import java.util.UUID;

import com.smartlogis.productservice.domain.entity.ChangeSource;
import com.smartlogis.productservice.domain.entity.ChangeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "재고 내역 전체 조회")
public class StockHistoryListResponse {

	@Schema()
	private UUID id;

	private ChangeType changeType;

	private Integer changeQuantity;

	private ChangeSource changeSource;
}
