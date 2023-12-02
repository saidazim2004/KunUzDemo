package com.example.kunuzdemo.dtos.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegionResponseDTO {
    private String nameUZ;
    private String nameRU;
    private String nameEN;
}
