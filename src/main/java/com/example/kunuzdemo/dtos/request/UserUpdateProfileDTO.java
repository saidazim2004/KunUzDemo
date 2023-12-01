package com.example.kunuzdemo.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateProfileDTO {

    private String firstName;
    private String lastName;
    private Long mediaId;

}
