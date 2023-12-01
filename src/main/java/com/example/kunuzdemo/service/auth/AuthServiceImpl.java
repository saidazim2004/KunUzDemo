package com.example.kunuzdemo.service.auth;

import com.example.kunuzdemo.config.jwt.JwtService;
import com.example.kunuzdemo.dtos.request.UserCreateDto;
import com.example.kunuzdemo.dtos.response.AuthResponseDTO;
import com.example.kunuzdemo.dtos.response.UserResponseDTO;
import com.example.kunuzdemo.entity.UserEntity;
import com.example.kunuzdemo.entity.VerificationData;
import com.example.kunuzdemo.enums.UserRole;
import com.example.kunuzdemo.enums.UserStatus;
import com.example.kunuzdemo.exceptions.BadRequestException;
import com.example.kunuzdemo.exceptions.DuplicateValueException;
import com.example.kunuzdemo.exceptions.UserPasswordWrongException;
import com.example.kunuzdemo.repository.UserRepository;
import com.example.kunuzdemo.service.mail.MailSenderService;
import com.example.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private Map<String, UserEntity> userMap = new HashMap<>();
    @Override
    public AuthResponseDTO<UserResponseDTO> create(UserCreateDto userCreateDto) {
        checkEmailUnique(userCreateDto.getEmail());
        checkUserPasswordAndIsValid(userCreateDto.getPassword(), userCreateDto.getConfirmPassword());
        UserEntity user = modelMapper.map(userCreateDto, UserEntity.class);

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setVerificationData(generateVerificationData());

        userMap.put(userCreateDto.getEmail(), user);
        String message = mailSenderService.sendVerificationCode(user.getEmail(),
                user.getVerificationData().getVerificationCode());
        UserResponseDTO mappedUser = modelMapper.map(user, UserResponseDTO.class);
        return new AuthResponseDTO<>(message, mappedUser);
    }


    private void checkEmailUnique(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("User already exists with Email: " + email);
    }
    private void checkUserPasswordAndIsValid(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword))
            throw new UserPasswordWrongException("Password must be same with confirm password: " + confirmPassword);
        checkPasswordIsValid(password);
    }

    private void checkPasswordIsValid(String password) {
        String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d).{8,20}$";
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }
    private VerificationData generateVerificationData() {
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(100000, 1000000));
        return new VerificationData(verificationCode, LocalDateTime.now());
    }

    @Override
    public String verify(String email, String verificationCode) {
        UserEntity user = userMap.get(email);
        if (checkVerificationCodeAndExpiration(user.getVerificationData(), verificationCode))
            return "Verification code wrong";
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        userMap.remove(email);
        return "Successfully verified";
    }


    public boolean checkVerificationCodeAndExpiration(VerificationData verificationData, String verificationCode) {
        if (!verificationData.getVerificationDate().plusMinutes(100).isAfter(LocalDateTime.now()))
            throw new BadRequestException("Verification code expired");
        return !Objects.equals(verificationData.getVerificationCode(), verificationCode);
    }
}
