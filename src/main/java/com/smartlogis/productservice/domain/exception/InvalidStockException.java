package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidStockException extends AbstractException {

	public InvalidStockException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidStockException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
