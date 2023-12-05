package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.RegionCreateDTO;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.service.region.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RegionResponseDTO> create(
            @Valid @RequestBody RegionCreateDTO createDTO
    ) {
        RegionResponseDTO regionDTO = regionService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionDTO);
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<RegionResponseDTO> getByID(@RequestParam UUID regionId){
        RegionResponseDTO regionResponseDTO = regionService.getById(regionId);
        return ResponseEntity.ok(regionResponseDTO);
    }
}
