package com.cybage.gms.app.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cybage.gms.app.exception.InvalidIdException;
import com.cybage.gms.app.exception.InvalidTopicException;
import com.cybage.gms.app.model.ApiErrorResponse;

/**
 * 
 * @author anmolm
 * 
 */
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({InvalidIdException.class})
    public ResponseEntity<ApiErrorResponse> idNotFound(RuntimeException ex) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
            .ApiErrorResponseBuilder()
            .withDetail("Not able to find the record")
            .withMessage(ex.getMessage())
            .withError_code("404")
            .withStatus(HttpStatus.NOT_FOUND)
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
    @ExceptionHandler({InvalidTopicException.class})
    public ResponseEntity<ApiErrorResponse> topicNotFound(RuntimeException ex) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
            .ApiErrorResponseBuilder()
            .withDetail("Not able to find the topic")
            .withMessage(ex.getMessage())
            .withError_code("404")
            .withStatus(HttpStatus.NOT_FOUND)
            .atTime(LocalDateTime.now(ZoneOffset.UTC))
            .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(SQLIntegrityConstraintViolationException ex) {
    	String detail = "";
    	try {
    		detail = ex.getMessage().split("for key")[0];
    	}catch (Exception e) {}
    	if(detail == "") {
    		detail = "bad request";
    	}
    	ApiErrorResponse response = new ApiErrorResponse
    			.ApiErrorResponseBuilder()
    			.withDetail(detail)
    			.withMessage(detail)
    			.withError_code("404")
    			.withStatus(HttpStatus.BAD_REQUEST)
    			.atTime(LocalDateTime.now(ZoneOffset.UTC))
    			.build();
    	return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    			
    }
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        BodyBuilder badRequest = ResponseEntity.badRequest();
        badRequest.body(errors);
        badRequest.header("Content-Type", "application/json");
        return badRequest.build();
	}
}