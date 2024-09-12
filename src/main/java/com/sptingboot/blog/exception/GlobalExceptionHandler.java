package com.sptingboot.blog.exception;

import com.sptingboot.blog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    //method to handle resource not found exception
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        return new ResponseEntity<>((new ErrorDetails(new Date(), exception.getMessage(),  webRequest.getDescription(false))), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogApiException.class)
    //method to handle blog api  exception
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception, WebRequest webRequest) {
        HttpStatus status = (exception.getStatus() != null) ? exception.getStatus() : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>((new ErrorDetails(new Date(), exception.getMessage(),  webRequest.getDescription(false))), status);
    }


    @ExceptionHandler(Exception.class)
    //method to handle Global exception
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
        return new ResponseEntity<>((new ErrorDetails(new Date(), exception.getMessage(),  webRequest.getDescription(false))), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
