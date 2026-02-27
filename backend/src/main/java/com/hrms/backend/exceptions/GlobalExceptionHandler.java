package com.hrms.backend.exceptions;

import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidCredentialException(InvalidCredentialsException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid credential",HttpStatus.BAD_REQUEST));

    }
    @ExceptionHandler(InvalidActionException.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidActionException(InvalidActionException e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.warn("Invalid action: ",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid action",HttpStatus.BAD_REQUEST));

    }
    @ExceptionHandler(InvalidDeleteAction.class)
    public ResponseEntity<GlobalResponseDto<?>> handleInvalidDeleteAction(InvalidDeleteAction e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.warn("invalid delete action: {}",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"Invalid delete action",HttpStatus.BAD_REQUEST));

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
        log.warn("Jwt token required");
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
        log.warn("Slot can not booked: {}",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"slot can not booked",HttpStatus.BAD_REQUEST));

    }
    @ExceptionHandler(ItemNotFoundExpection.class)
    public ResponseEntity<GlobalResponseDto<?>> handleItemNotFoundExpection(ItemNotFoundExpection e){
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.warn("resource not found: {}",e.getMessage() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"item not found",HttpStatus.BAD_REQUEST));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponseDto<List< Map<String, String>>>> handleValidationError(MethodArgumentNotValidException ex) {
       List< Map<String, String>> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .toList();

       errors.forEach(error->log.warn("validation error: {} - {}",error.get("field"),error.get("message")));
        return ResponseEntity.badRequest().body(new GlobalResponseDto<>(errors,"validation failed",HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<GlobalResponseDto<List< Map<String, String>>>> handleUnauthorised(HttpClientErrorException.Unauthorized e) {
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.warn("access forbidden method");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new GlobalResponseDto<>(errors,"item not found",HttpStatus.FORBIDDEN));

    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<GlobalResponseDto<List< Map<String, String>>>> handleEntityNotFoundException(EntityNotFoundException e) {
        List<Map<String,String>> errors= new ArrayList<>(){};
        errors.add(Map.of( "message", e.getMessage()));
        log.warn("entity not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalResponseDto<>(errors,"item not found",HttpStatus.BAD_REQUEST));

    }


}
