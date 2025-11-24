package com.smartlogis.productservice.domain.exception;


import org.springframework.http.HttpStatus;

import com.smartlogis.common.exception.MessageCode;

public enum ProductCode implements MessageCode {
	INVALID_NAME("PRODUCT.INVALID_NAME", HttpStatus.BAD_REQUEST),
	INVALID_COMPANY_ID("PRODUCT.INVALID_COMPANY_ID", HttpStatus.BAD_REQUEST),
	INVALID_HUB_ID("PRODUCT.INVALID_HUB_ID", HttpStatus.BAD_REQUEST),
	INVALID_MANAGER_ID("PRODUCT.INVALID_MANAGER_ID", HttpStatus.BAD_REQUEST),
	INSUFFICIENT_STOCK("PRODUCT.INSUFFICIENT_STOCK", HttpStatus.BAD_REQUEST),
	INVALID_STATUS("PRODUCT.INVALID_STATUS", HttpStatus.BAD_REQUEST),


	//재고 기록 관련
	INVALID_PRODUCT_ID("PRODUCT.INVALID_PRODUCT_ID", HttpStatus.BAD_REQUEST),
	INVALID_QUANTITY("PRODUCT.INVALID_QUANTITY", HttpStatus.BAD_REQUEST),
	INVALID_STOCK("PRODUCT.INVALID_STOCK", HttpStatus.BAD_REQUEST),
	INVALID_CHANGE_TYPE("PRODUCT.INVALID_CHANGE_TYPE", HttpStatus.BAD_REQUEST),
	;

	private final String code;
	private final HttpStatus status;

	ProductCode(String code, HttpStatus status) {
		this.code = code;
		this.status = status;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}
}
