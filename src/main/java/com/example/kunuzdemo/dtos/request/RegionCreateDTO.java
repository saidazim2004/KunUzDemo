package com.example.kunuzdemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionCreateDTO {

    @NotBlank(message = "Name UZ must not be blank")
    private String nameUZ;

    @NotBlank(message = "Name RU must not be blank")
    private String nameRU;

    @NotBlank(message = "Name EN must not be blank")
    private String nameEN;
}
