package com.hrms.backend.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("eithor email or password is wrong");
    }
}
