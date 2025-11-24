package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class ProductAlreadyExistException extends AbstractException {
	public ProductAlreadyExistException(MessageCode messageCode) {
		super(messageCode);
	}

	public ProductAlreadyExistException(MessageCode messageCode,
		Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
