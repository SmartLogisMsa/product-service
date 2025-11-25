package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class ProductNotFoundException extends AbstractException {
	public ProductNotFoundException(MessageCode messageCode) {
		super(messageCode);
	}

	public ProductNotFoundException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
