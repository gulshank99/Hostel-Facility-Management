package com.hms.exceptions;

import com.hms.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourseNotFoundExceptionHandler(ResourceNotFoundException ex){
        String msg = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(msg,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> exceptionDetails = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String field = ((FieldError)error).getField();
            String msg = error.getDefaultMessage();
            exceptionDetails.put(field,msg);
        });
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> userExistsExceptionHandler(SQLIntegrityConstraintViolationException ex){
        String msg = "Something went wrong";
        return new ResponseEntity<>(new ApiResponse(msg,false),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ApiResponse> userDoesNotExistsException(UserDoesNotExistException ex){
        log.info("Inside User does not exists exception");
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ApiResponse(msg,false),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ApiResponse> userExistsException(UserExistException ex){
        log.info("Inside User exists exception");
        String msg = ex.getMessage();
        return new ResponseEntity<>(new ApiResponse(msg,false),HttpStatus.CONFLICT);
    }
}
