package com.smartlogis.productservice.domain.entity;

public enum ChangeType {
	STOCK_IN, //재고 증가
	STOCK_OUT, //재고 감소
	RETURNED //주문 취소로 인한 반환
}
