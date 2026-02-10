package com.hrms.backend.exceptions;

import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialException(InvalidCredentialsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(null,e.getMessage(),HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleJwtExpiration(ExpiredJwtException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GlobalResponseDto<>(null,"please login!",HttpStatus.UNAUTHORIZED));

    }
//    ExpiredJwtException
}
