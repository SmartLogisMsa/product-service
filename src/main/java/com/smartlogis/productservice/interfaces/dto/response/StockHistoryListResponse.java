package com.smartlogis.productservice.interfaces.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smartlogis.productservice.domain.entity.ChangeSource;
import com.smartlogis.productservice.domain.entity.ChangeType;
import com.smartlogis.productservice.domain.entity.StockHistory;

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

	@Schema(description = "재고 내역 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID id;

	@Schema(description = "재고 변경 타입", example = "STOCK_IN", nullable = false)
	private ChangeType changeType;

	@Schema(description = "재고 변화량", example = "5", nullable = false)
	private Integer changeQuantity;

	@Schema(description = "변경 원인이 되는 서비스명", example = "COMPANY_SERVICE", nullable = false)
	private ChangeSource changeSource;

	@Schema(description = "생성 일시", example = "2025-11-19T09:30:00", nullable = false)
	private LocalDateTime createdAt;

	public static StockHistoryListResponse of(StockHistory stockHistory) {
		return StockHistoryListResponse.builder()
			.id(stockHistory.getId())
			.changeType(stockHistory.getChangeType())
			.changeQuantity(stockHistory.getChangeQuantity())
			.changeSource(stockHistory.getChangeSource())
			.createdAt(stockHistory.getCreatedAt())
			.build();
	}
}
