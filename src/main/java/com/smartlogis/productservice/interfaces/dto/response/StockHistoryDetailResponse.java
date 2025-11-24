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
@Schema(description = "상품 재고 내역 상세 조회 응답")
public class StockHistoryDetailResponse {

	@Schema(description = "재고 내역 id", example = "a1b2c3d4-e5f6-7890-abcd-ef0123456789", nullable = false)
	private UUID id;

	@Schema(description = "재고 변경 타입", example = "STOCK_IN", nullable = false)
	private ChangeType changeType;

	@Schema(description = "재고 변화량", example = "5", nullable = false)
	private Integer changeQuantity;

	@Schema(description = "변경 전 재고", example = "30", nullable = false)
	private Integer beforeQuantity;

	@Schema(description = "변경 후 재고", example = "35", nullable = false)
	private Integer afterQuantity;

	@Schema(description = "변경 원인이 되는 서비스명", example = "COMPANY_SERVICE", nullable = false)
	private ChangeSource changeSource;

	@Schema(description = "생성 일시", example = "2025-11-19T09:30:00", nullable = false)
	private LocalDateTime createdAt;

	@Schema(description = "생성자", example = "manager01", nullable = false)
	private String createdBy;


	public static StockHistoryDetailResponse from(StockHistory stockHistory) {
		return StockHistoryDetailResponse.builder()
			.id(stockHistory.getId())
			.changeType(stockHistory.getChangeType())
			.changeQuantity(stockHistory.getChangeQuantity())
			.beforeQuantity(stockHistory.getBeforeQuantity())
			.afterQuantity(stockHistory.getAfterQuantity())
			.changeSource(stockHistory.getChangeSource())
			.createdAt(stockHistory.getCreatedAt())
			.createdBy(stockHistory.getCreatedBy())
			.build();
	}
}
