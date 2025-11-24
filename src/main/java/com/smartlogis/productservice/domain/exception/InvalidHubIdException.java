package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidHubIdException extends AbstractException {
	public InvalidHubIdException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidHubIdException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
