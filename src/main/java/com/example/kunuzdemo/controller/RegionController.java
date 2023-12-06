package com.example.kunuzdemo.controller;

import com.example.kunuzdemo.dtos.request.RegionCreateDTO;
import com.example.kunuzdemo.dtos.request.RegionUpdateDto;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.service.region.RegionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


    @GetMapping("/getAll")
    public ResponseEntity<List<RegionResponseDTO>> getAll(){
        List<RegionResponseDTO> regionResponseDTOS = regionService.getAll();

        return ResponseEntity.ok(regionResponseDTOS);
    }


    @GetMapping("/getAllVisible")
    public ResponseEntity<List<RegionResponseDTO>> getAllVisible(){
        List<RegionResponseDTO> regionResponseDTOS = regionService.getAllVisible();

        return ResponseEntity.ok(regionResponseDTOS);
    }

    @GetMapping("/getAllUnVisible")
    public ResponseEntity<List<RegionResponseDTO>> getAllUnVisible(){
        List<RegionResponseDTO> regionResponseDTOS = regionService.getAllUnVisible();

        return ResponseEntity.ok(regionResponseDTOS);
    }



    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{regionID}")
    public ResponseEntity<RegionResponseDTO> update(
            @PathVariable @NotNull UUID regionID,
            @Valid @RequestBody RegionUpdateDto updateDTO
    ) {
        return ResponseEntity.ok(regionService.update(regionID, updateDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{regionID}")
    public ResponseEntity<String> delete(
            @PathVariable @NotNull UUID regionID
    ) {
        regionService.deleteByID(regionID);
        return ResponseEntity.ok("Successfully deleted!");
    }



    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/all-selected")
    public ResponseEntity<String> deleteSelectedRegions(
            @RequestBody @Valid List<UUID> regionIDs
    ) {
        regionService.deleteSelectedRegions(regionIDs);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
