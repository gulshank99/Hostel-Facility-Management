package com.hms.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String msg) {
        super(msg);
    }
}
