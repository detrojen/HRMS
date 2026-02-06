package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDtos.LoginRequestDto;
import com.hrms.backend.dtos.responseDtos.LoginResponseDto;
import com.hrms.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private  final AuthService _authService;

    @Autowired
    public AuthController(AuthService authService){
        _authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDTO){
        String token = _authService.login(requestDTO.getEmail(),requestDTO.getPassword());
        return ResponseEntity.ok().body(new LoginResponseDto(token));
    }

}
