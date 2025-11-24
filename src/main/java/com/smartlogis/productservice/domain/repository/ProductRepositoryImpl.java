package com.smartlogis.productservice.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smartlogis.common.utils.QuerydslSortUtils;
import com.smartlogis.productservice.domain.entity.Product;
import com.smartlogis.productservice.domain.entity.QProduct;
import com.smartlogis.productservice.interfaces.dto.request.ProductSearchCondition;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {

		QProduct product = QProduct.product;

		BooleanBuilder builder = new BooleanBuilder();

		if(condition.getName() != null) {
			builder.and(product.name.eq(condition.getName()));
		}
		if(condition.getHubId() != null) {
			builder.and(product.hubId.eq(condition.getHubId()));
		}
		if(condition.getCompanyId() != null) {
			builder.and(product.companyId.eq(condition.getCompanyId()));
		}
		if(condition.getStatus() != null) {
			builder.and(product.status.eq(condition.getStatus()));
		}

		long total = queryFactory
			.select(product.count())
			.from(product)
			.where(builder)
			.fetchOne();

		List<Product> products = queryFactory
			.selectFrom(product)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(
				QuerydslSortUtils.toOrderSpecifiers(
					Product.class,
					"createdAt",
					pageable.getSort()
				)
			)
			.fetch();

		return new PageImpl<>(products, pageable, total);
	}
}
