package com.hrms.backend.dtos.responseDtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor()
public class GlobalResponseDto<T> {
    private T data;
    private HttpStatus status;
    private String message;
    private String authToken;
    private List< Map<String, String>> errors;
    public GlobalResponseDto(T data){
        this.data = data;
        this.status = HttpStatus.OK;
    }
    public GlobalResponseDto(T data,String message, HttpStatus status){
        this.data = data;
        this.message = message;
        this.status = status;
    }
    public GlobalResponseDto(T data,String message){
        this.data = data;
        this.message = message;
        this.status = HttpStatus.OK;
    }
    public GlobalResponseDto(List< Map<String, String>> errors,String message, HttpStatus status){
        this.errors = errors;
        this.message = message;
        this.status = status;
    }
}
