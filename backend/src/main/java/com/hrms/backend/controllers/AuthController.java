package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDto.auth.LoginRequestDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.auth.LoginResponseDto;
import com.hrms.backend.dtos.responseDtos.employee.SelfDetailResponseDto;
import com.hrms.backend.services.AuthServices.AuthService;
import com.hrms.backend.services.AuthServices.JwtService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
public class AuthController {

    private  final AuthService authService;
    private final EmployeeService employeeService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService, EmployeeService employeeService){
        this.authService = authService;
        this.jwtService = jwtService;
        this.employeeService = employeeService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<GlobalResponseDto<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDTO){
        String token = authService.login(requestDTO.getEmail(),requestDTO.getPassword());
        String refreshToken = jwtService.createRefreshToken(requestDTO.getEmail());
        ResponseCookie cookie = ResponseCookie.from("HRMS_REFRESH_TOKEN",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(7))
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(new GlobalResponseDto<>(new LoginResponseDto(token)));
    }

    @PostMapping("/auth/switch-role/{roleId}")
    public ResponseEntity<GlobalResponseDto<SelfDetailResponseDto>> switchRole(@PathVariable Long roleId){
        SelfDetailResponseDto responseDto = employeeService.switchRole(roleId);
        String token = authService.login(responseDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,token);
        return ResponseEntity.ok()
                .headers(headers).body(
                    new GlobalResponseDto<>(responseDto)
                );

    }

}
