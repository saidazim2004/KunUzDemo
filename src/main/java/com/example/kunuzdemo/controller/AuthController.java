package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.LoginDTO;
import com.example.kunuzdemo.dtos.request.ResetPasswordDto;
import com.example.kunuzdemo.dtos.request.UpdatePasswordDto;
import com.example.kunuzdemo.dtos.request.UserCreateDto;
import com.example.kunuzdemo.dtos.response.AuthResponseDTO;
import com.example.kunuzdemo.dtos.response.TokenDTO;
import com.example.kunuzdemo.dtos.response.UserResponseDTO;
import com.example.kunuzdemo.dtos.response.VerifyDTO;
import com.example.kunuzdemo.service.auth.AuthServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService ;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO<UserResponseDTO>> create(@Valid @RequestBody UserCreateDto userCreateDto){
        System.out.println("userCreateDto.getEmail() = " + userCreateDto.getEmail());
        AuthResponseDTO<UserResponseDTO> responseDTO = authService.create(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(
            @Valid @RequestBody VerifyDTO verifyDTO
    ) {
        String response = authService.verify(verifyDTO.getEmail(), verifyDTO.getVerificationCode());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new-verification-code")
    public ResponseEntity<String> newVerificationCode(@RequestParam String email){
        String res = authService.newVerifyCode(email);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDTO> signIn(@Valid @RequestBody LoginDTO loginDTO){
        TokenDTO tokenDTO = authService.signIn(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam @Email String email){
        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPassword){

        String response = authService.resetPassword(resetPassword);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto){
        String response = authService.updatePassword(updatePasswordDto);
        return ResponseEntity.ok(response);
    }
}
