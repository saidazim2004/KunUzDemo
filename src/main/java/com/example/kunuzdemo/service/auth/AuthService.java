package com.example.kunuzdemo.service.auth;

import com.example.kunuzdemo.dtos.request.UserCreateDto;
import com.example.kunuzdemo.dtos.response.AuthResponseDTO;
import com.example.kunuzdemo.dtos.response.UserResponseDTO;

public interface AuthService {
    AuthResponseDTO<UserResponseDTO> create(UserCreateDto userCreateDto);
    String verify(String email, String verificationCode);
}
