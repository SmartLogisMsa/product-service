package com.smartlogis.productservice.interfaces.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartlogis.common.presentation.ApiResponse;
import com.smartlogis.common.presentation.dto.PageResponse;
import com.smartlogis.productservice.application.service.StockHistoryService;
import com.smartlogis.productservice.interfaces.dto.response.StockHistoryDetailResponse;
import com.smartlogis.productservice.interfaces.dto.response.StockHistoryListResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Tag(name = "재고 내역", description = "재고 내역 관련 API")
public class StockHistoryController {

	private final StockHistoryService stockHistoryService;

	//1. 상품별 재고 내역 전체 조회
	@GetMapping("/{productId}/history")
	@Operation(summary = "재고 내역 전체 조회", description = "상품id별 재고 내역 전체를 조회합니다.")
	public ResponseEntity<ApiResponse<PageResponse<StockHistoryListResponse>>> getStockHistory(
		@PathVariable("productId") UUID productId,
		Pageable pageable
	) {
		PageResponse<StockHistoryListResponse> response = stockHistoryService.getStockHistory(productId, pageable);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}

	//2. 재고 내역 상세 조회
	@GetMapping("/{productId}/history/{id}")
	@Operation(summary = "재고 내역 상세 조회", description = "재고 내역 id로 재고 내역을 상세 조회합니다.")
	public ResponseEntity<ApiResponse<StockHistoryDetailResponse>> getStockHistoryDetails(
		@PathVariable("productId") UUID productId,
		@PathVariable("id") UUID id
	) {
		StockHistoryDetailResponse response = stockHistoryService.getStockHistoryDetails(productId, id);
		return ResponseEntity.ok(ApiResponse.successWithDataOnly(response));
	}
}
