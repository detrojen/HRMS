package com.hrms.backend.exceptions;

public class SlotCanNotBeBookedException extends RuntimeException{
    public SlotCanNotBeBookedException(String msg){
        super(msg);
    }

}
