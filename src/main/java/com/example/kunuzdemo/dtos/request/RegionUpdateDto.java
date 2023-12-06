package com.example.kunuzdemo.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionUpdateDto {

    private String nameUZ;
    private String nameRU;
    private String nameEN;
    private boolean visible;

}
