package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidQuantityException extends AbstractException {
	public InvalidQuantityException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidQuantityException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
