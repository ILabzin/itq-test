package com.labzin.document_service_itq_test_api.exception;

public class ValidationException extends RuntimeException{
    public ValidationException (String message) {
        super(message);
    }
}
