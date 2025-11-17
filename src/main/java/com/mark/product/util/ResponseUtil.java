package com.mark.product.util;

import com.mark.product.persistence.dto.ErrorResponseModel;
import com.mark.product.persistence.dto.ResponseModel;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public final class ResponseUtil {
    private ResponseUtil() {}

    // Success response with data and message
    public static <R> ResponseModel<R> success(R data, String message) {
        return ResponseModel.<R>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }

    // Error response with message and status
    public static ErrorResponseModel error(String message, HttpStatus status) {
        return ErrorResponseModel.builder()
                .status(status.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Error response with message, status, and field errors
    public static ErrorResponseModel error(String message, HttpStatus status, Map<String, String> fieldErrors) {
        return ErrorResponseModel.builder()
                .status(status.getReasonPhrase())
                .message(message)
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors)
                .build();
    }
}
