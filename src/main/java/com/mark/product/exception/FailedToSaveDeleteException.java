package com.mark.product.exception;

public class FailedToSaveDeleteException extends RuntimeException {

    public FailedToSaveDeleteException() {
        super();
    }
    public FailedToSaveDeleteException(String message) {
        super(message);
    }

    public FailedToSaveDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

}