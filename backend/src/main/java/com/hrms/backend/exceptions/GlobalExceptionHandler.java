package com.hrms.backend.exceptions;

import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidCredentialException(InvalidCredentialsException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid credential",HttpStatus.BAD_REQUEST));

    }
    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidActionException(InvalidActionException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid credential",HttpStatus.BAD_REQUEST));

    }
    @ExceptionHandler(InvalidDeleteAction.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidDeleteAction(InvalidDeleteAction e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid credential",HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleJwtExpiration(ExpiredJwtException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GlobalResponseDto<>(errors,"session expires",HttpStatus.UNAUTHORIZED));

    }
    @ExceptionHandler(JwtTokenRequired.class)
    public ResponseEntity<GlobalResponseDto<?>> handleJwtRequired(JwtTokenRequired e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", "Login required"));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new GlobalResponseDto<>(errors,"login required!",HttpStatus.UNAUTHORIZED));

    }
    @ExceptionHandler(ServerError.class)
    public ResponseEntity<GlobalResponseDto<?>> handleJwtRequired(ServerError e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GlobalResponseDto<>(errors,"Server error",HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @ExceptionHandler(SlotCanNotBeBookedException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleSlotCanNotBeBookedException(SlotCanNotBeBookedException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"slot can not booked",HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(ItemNotFoundExpection.class)
    public ResponseEntity<GlobalResponseDto<?>> handleItemNotFoundExpection(SlotCanNotBeBookedException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"item not found",HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto<List< Map<String, String>>>> handleValidationError(MethodArgumentNotValidException ex) {
       List< Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new GlobalResponseDto<>(errors,"validation failed",HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<GlobalResponseDto<List< Map<String, String>>>> handleUnauthorised(HttpClientErrorException.Unauthorized e) {
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"item not found",HttpStatus.BAD_REQUEST));

    }
}
