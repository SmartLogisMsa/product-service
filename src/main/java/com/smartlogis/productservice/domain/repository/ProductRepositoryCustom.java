package com.smartlogis.productservice.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartlogis.productservice.domain.entity.Product;
import com.smartlogis.productservice.interfaces.dto.request.ProductSearchCondition;

public interface ProductRepositoryCustom {

	// 통합 검색(hub id, company id, status, keyword, 페이징)
	Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable);
}
