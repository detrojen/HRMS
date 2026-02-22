package com.hrms.backend.exceptions;

public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String msg){
        super(msg);
    }
}
