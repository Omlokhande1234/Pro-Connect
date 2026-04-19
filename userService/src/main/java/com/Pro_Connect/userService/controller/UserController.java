package com.Pro_Connect.userService.controller;

import com.Pro_Connect.userService.dto.LoginRequestDto;
import com.Pro_Connect.userService.dto.SignupRequestDto;
import com.Pro_Connect.userService.dto.UserDto;
import com.Pro_Connect.userService.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        UserDto userDto = authService.signup(signupRequestDto);
//        return ResponseEntity.ok(userDto.toString());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody LoginRequestDto loginRequestDto) {
        String token=authService.login(loginRequestDto);
        return ResponseEntity.ok(token);
    }
}
