package com.example.deploytest.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> errorMap = new HashMap<>();
        List<String> errorList = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        errorMap.put("errors",errorList);
        return ResponseEntity.
                status(HttpStatus.BAD_REQUEST)
                .body(errorMap);
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<Object> taskAlreadyExistsExceptionHandler(TaskAlreadyExistsException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errros",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorMap);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> taskAlreadyExistsExceptionHandler(TaskNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errros",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorMap);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("errors","TASK_DETAILS_REQUIRED");
        return ResponseEntity
                .badRequest()
                .body(errorMap);
    }
}
