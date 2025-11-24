package com.smartlogis.productservice.domain.exception;

import com.smartlogis.common.exception.AbstractException;
import com.smartlogis.common.exception.MessageCode;

public class HistoryNotFoundException extends AbstractException {
	public HistoryNotFoundException(MessageCode messageCode) {
		super(messageCode);
	}

	public HistoryNotFoundException(MessageCode messageCode, Object... messageArguments) {
		super(messageCode, messageArguments);
	}
}
