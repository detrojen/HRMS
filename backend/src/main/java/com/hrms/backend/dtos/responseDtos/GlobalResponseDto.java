package com.hrms.backend.dtos.responseDtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor()
public class GlobalResponseDto<T> {
    private T data;
    private HttpStatus status;
    private String message;
    private String authToken;
    private Map<String,?> errors;
    public GlobalResponseDto(T data){
        this.data = data;
    }
    public GlobalResponseDto(T data,String message, HttpStatus status){
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
