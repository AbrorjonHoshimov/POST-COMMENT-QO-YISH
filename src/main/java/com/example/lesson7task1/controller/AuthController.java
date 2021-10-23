package com.example.lesson7task1.controller;

import com.example.lesson7task1.payload.ApiResponse;
import com.example.lesson7task1.payload.LoginDto;
import com.example.lesson7task1.payload.RegisterDto;
import com.example.lesson7task1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService userService;

    @PostMapping
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = userService.register(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        ApiResponse token = userService.login(loginDto);
        return ResponseEntity.ok(token.getObject());
    }
}
