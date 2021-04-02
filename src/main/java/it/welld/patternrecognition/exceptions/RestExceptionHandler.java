package it.welld.patternrecognition.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.welld.patternrecognition.dtos.SimpleResponse;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(BindingException.class)
	public final ResponseEntity<SimpleResponse> exceptionBindingHandler(Exception ex){
		return new ResponseEntity<SimpleResponse>(new SimpleResponse(400, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DeleteException.class)
	public final ResponseEntity<SimpleResponse> exceptionDeleteHandler(Exception ex){
		return new ResponseEntity<SimpleResponse>(new SimpleResponse(400, "Error while deleting."), HttpStatus.BAD_REQUEST);
	}
	
}
