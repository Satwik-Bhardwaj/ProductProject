package com.mark.product.exception;

import com.mark.product.persistence.dto.ErrorResponseModel;
import com.mark.product.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseModel> handleBadRequestException(RuntimeException ex) {
        return buildBadRequestResponse(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseModel> handleInvalidArgumentException(MethodArgumentNotValidException ex) {
        return handleValidationExceptions(ex);
    }

    private ResponseEntity<ErrorResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponseModel errorResponse = ResponseUtil.error("Invalid fields value!", HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponseModel> buildBadRequestResponse(Exception ex) {
        ErrorResponseModel errorResponse = ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorResponseModel errorResponse = ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToSaveDeleteException.class)
    public ResponseEntity<ErrorResponseModel> handleFailedToSaveException(FailedToSaveDeleteException ex) {
        ErrorResponseModel errorResponse = ResponseUtil.error("Failed to Save", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseModel> handleGeneralException(Exception ex) {
        ErrorResponseModel errorResponse = ResponseUtil.error("Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
