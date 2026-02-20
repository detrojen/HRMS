package com.hrms.backend.exceptions;

public class ServerError extends RuntimeException{
    public ServerError(String msg){
        super(msg);
    }
}
