package com.hrms.backend.exceptions;

public class ItemNotFoundExpection extends RuntimeException{
    public ItemNotFoundExpection(String msg){
        super(msg);
    }
}
