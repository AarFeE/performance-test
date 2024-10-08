package riwi.aarfee.performance_test.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import riwi.aarfee.performance_test.dtos.responses.ErrorRes;
import riwi.aarfee.performance_test.exceptions.customs.GenericException;
import riwi.aarfee.performance_test.exceptions.customs.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorRes> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorRes errorRes = new ErrorRes(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorRes, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorRes> handleGenericException(GenericException ex) {
        ErrorRes errorResponse = new ErrorRes(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleGenericException(Exception ex) {
        ErrorRes errorResponse = new ErrorRes("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        ErrorRes errorRes = new ErrorRes(errorMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRes> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Invalid state";
        ErrorRes errorRes = new ErrorRes(message, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
    }
}
