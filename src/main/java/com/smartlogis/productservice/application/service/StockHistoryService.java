package com.smartlogis.productservice.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.productservice.domain.entity.StockHistory;
import com.smartlogis.productservice.domain.exception.HistoryNotFoundException;
import com.smartlogis.productservice.domain.exception.ProductCode;
import com.smartlogis.productservice.domain.repository.StockHistoryRepository;
import com.smartlogis.productservice.interfaces.dto.response.StockHistoryDetailResponse;
import com.smartlogis.productservice.interfaces.dto.response.StockHistoryListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockHistoryService {

	private final StockHistoryRepository stockHistoryRepository;

	//1. 전체 조회
	@Transactional(readOnly = true)
	public PageResponse<StockHistoryListResponse> getStockHistory(
		UUID productId, Pageable pageable
	) {
		Page<StockHistory> histories = stockHistoryRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable);

		return PageResponse.from(
			histories.map(StockHistoryListResponse::of)
		);
	}

	//2. 상세 조회
	@Transactional(readOnly = true)
	public StockHistoryDetailResponse getStockHistoryDetails(
		UUID productId, UUID id
	) {
		StockHistory stockHistory = stockHistoryRepository.findByIdAndProductId(id, productId)
			.orElseThrow(() -> new HistoryNotFoundException(ProductCode.HISTORY_NOT_FOUND));

		return StockHistoryDetailResponse.from(stockHistory);
	}
}
