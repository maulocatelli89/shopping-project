package com.locatelli.java.back.end.exception.advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.locatelli.java.back.end.dto.ErrorDTO;
import com.locatelli.java.back.end.dto.exception.ProductNotFoundException;
import com.locatelli.java.back.end.dto.exception.UserNotFoundException;

@ControllerAdvice(basePackages = "com.locatelli.java.back.end.controller")
public class ShoppingControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProductNotFoundException.class)
	public ErrorDTO handleProductNotFound(
			ProductNotFoundException productNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Produto não encontrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public ErrorDTO handleUserNotFound(
			UserNotFoundException userNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Usuário não encontrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
}
