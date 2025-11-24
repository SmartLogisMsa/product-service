package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class InvalidCompanyIdException extends AbstractException {
	public InvalidCompanyIdException(MessageCode messageCode) {
		super(messageCode);
	}

	public InvalidCompanyIdException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
