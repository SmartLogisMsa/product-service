package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidChangeTypeException extends AbstractException {
	public InvalidChangeTypeException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidChangeTypeException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
