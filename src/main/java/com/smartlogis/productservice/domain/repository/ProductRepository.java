package com.smartlogis.productservice.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartlogis.productservice.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom {

	// 상품 중복 등록 여부 확인
	boolean existsByNameAndCompanyId(String name, UUID companyId);
}
