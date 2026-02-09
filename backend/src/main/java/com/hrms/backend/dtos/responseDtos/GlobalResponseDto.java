package com.hrms.backend.dtos.responseDtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor()
public class GlobalResponseDto<T> {
    private T data;
    private int status;
    private String message;
    private String authToken;
    private Map<String,?> errors;
    public GlobalResponseDto(T data){
        this.data = data;
    }
}
