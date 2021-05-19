package com.sucursal.api;

import java.sql.SQLDataException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sucursal.exception.RecurnoNoEncontradoException;
import com.sucursal.model.ErrorMsj;

@ControllerAdvice
public class ErrorHandler {

	private Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	/* Manejadores para los errores 4XX */
	
	/**
	 * Manejador para 404
	 * 
	 * @return HTTP 404
	 */
	@ExceptionHandler({ RecurnoNoEncontradoException.class })
	public ResponseEntity<ErrorMsj> handleRecursoNoencontrado(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		
		logger.error(HttpStatus.NOT_FOUND.value()+ " - "+ HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getStackTrace());
		
		ErrorMsj mensaje = new ErrorMsj(HttpStatus.NOT_FOUND.value(), "Recurso no encontrado");
		
		return new ResponseEntity<ErrorMsj>(mensaje, HttpStatus.NOT_FOUND);
	}
	
	
	/* Manejadores para los errores 5XX */

	/**
	 * Manejador para excepciones SQL
	 * 
	 * @return HTTP 500
	 */
	@ExceptionHandler({ SQLException.class, SQLDataException.class })
	public ResponseEntity<ErrorMsj> handleSQL(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		
		logger.error(HttpStatus.INTERNAL_SERVER_ERROR.value()+ " - "+ HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getStackTrace());
		
		ErrorMsj mensaje = new ErrorMsj(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Reintente en unos instantes");
		
		return new ResponseEntity<ErrorMsj>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	


	/**
	 * Manejador por default.
	 * 
	 * @return HTTP 500
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMsj> handleDefault(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		
		logger.error(HttpStatus.INTERNAL_SERVER_ERROR.value()+ " - "+ HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getStackTrace());
		
		ErrorMsj mensaje = new ErrorMsj(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Reintente en unos instantes");
		
		return new ResponseEntity<ErrorMsj>(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
