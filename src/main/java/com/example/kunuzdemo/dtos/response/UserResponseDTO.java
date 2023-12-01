package com.example.kunuzdemo.dtos.response;

import com.example.kunuzdemo.enums.UserRole;
import com.example.kunuzdemo.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private UserRole role;

    private UserStatus status;

    private Long mediaId;
}
