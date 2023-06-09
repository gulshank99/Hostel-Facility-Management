package com.hms.exceptions;

public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(String msg){
        super(msg);
    }

}
