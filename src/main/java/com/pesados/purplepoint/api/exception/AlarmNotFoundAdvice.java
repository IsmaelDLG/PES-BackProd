package com.pesados.purplepoint.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AlarmNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(AlarmNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String alarmNotFoundHandler(AlarmNotFoundException ex) {
		return ex.getMessage();
	}
}