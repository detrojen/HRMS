package com.hrms.backend.exceptions;

public class TravelNotFoundException extends ItemNotFoundExpection{
    public TravelNotFoundException(){
        super("Travel not found");
    }
}
