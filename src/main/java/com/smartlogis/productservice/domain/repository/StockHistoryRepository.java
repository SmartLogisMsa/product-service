package com.smartlogis.productservice.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smartlogis.productservice.domain.entity.StockHistory;

public interface StockHistoryRepository extends JpaRepository<StockHistory, UUID> {

	Page<StockHistory> findByProductId(UUID productId, Pageable pageable);

	Optional<StockHistory> findByIdAndProductId(UUID id, UUID productId);
}
