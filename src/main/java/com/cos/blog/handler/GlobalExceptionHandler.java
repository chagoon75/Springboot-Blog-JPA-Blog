package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public String handlerArgumentException(Exception e) {
		return "<h1><font color=red>" + e.getMessage() + "</font></h1>";
	}
}
