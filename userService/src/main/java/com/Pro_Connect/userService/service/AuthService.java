package com.Pro_Connect.userService.service;

import com.Pro_Connect.userService.Event.UserCreatedEvent;
import com.Pro_Connect.userService.dto.LoginRequestDto;
import com.Pro_Connect.userService.dto.SignupRequestDto;
import com.Pro_Connect.userService.dto.UserDto;
import com.Pro_Connect.userService.entity.User;
import com.Pro_Connect.userService.exception.BadRequestException;
import com.Pro_Connect.userService.exception.ResourceNotFoundException;
import com.Pro_Connect.userService.repository.UserRepository;
import com.Pro_Connect.userService.utils.Bcrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;
    private final KafkaTemplate<Long, UserCreatedEvent> userCreatedEventKafkaTemplate;
    public UserDto signup(SignupRequestDto signupRequestDto) {
        log.info("signup user with email :{}", signupRequestDto.getEmail());
        boolean exists=userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists){
            throw new BadRequestException("User with email already exists");
        }
        User user=modelMapper.map(signupRequestDto, User.class);
        user.setPassword(Bcrypt.hash(signupRequestDto.getPassword()));
        user=userRepository.save(user);
        UserCreatedEvent userCreatedEvent=UserCreatedEvent.builder()
                .userId(user.getId())
                .name(user.getName())
                .build();

        userCreatedEventKafkaTemplate.send("userCreatedTopic", userCreatedEvent);
        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email: {}",loginRequestDto.getEmail());
        User user=userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException
                        ("Incorrect email or password"));
        boolean isPasswordMatch=Bcrypt.check(loginRequestDto.getPassword(),user.getPassword());
        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect email or password");
        }
        return jwtService.generateAccessToken(user);
    }
}
