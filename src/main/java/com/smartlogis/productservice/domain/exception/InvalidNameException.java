package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidNameException extends AbstractException {
	public InvalidNameException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidNameException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
