package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InsufficientStockException extends AbstractException {
	public InsufficientStockException(MessageCode messageCode) {
		super(messageCode);
	}

	public InsufficientStockException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
