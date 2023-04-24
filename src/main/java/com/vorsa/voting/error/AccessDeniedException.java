package com.vorsa.voting.error;

public class AccessDeniedException extends AppException{
    public AccessDeniedException(String msg) {
        super(msg);
    }
}
