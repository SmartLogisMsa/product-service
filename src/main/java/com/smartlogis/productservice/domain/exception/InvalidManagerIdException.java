package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidManagerIdException extends AbstractException {
	public InvalidManagerIdException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidManagerIdException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
