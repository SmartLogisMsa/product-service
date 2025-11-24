package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidStatusException extends AbstractException {
	public InvalidStatusException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidStatusException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
