package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidProductException extends AbstractException {
	public InvalidProductException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidProductException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
