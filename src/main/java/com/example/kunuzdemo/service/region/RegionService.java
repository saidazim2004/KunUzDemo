package com.example.kunuzdemo.service.region;

import com.example.kunuzdemo.dtos.request.RegionCreateDTO;
import com.example.kunuzdemo.dtos.request.RegionUpdateDto;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.entity.Region;

import java.util.List;
import java.util.UUID;

public interface RegionService {
    Region getRegion(UUID regionID);

    RegionResponseDTO create(RegionCreateDTO createDTO);

    RegionResponseDTO getById(UUID regionId);

    List<RegionResponseDTO> getAll();


    List<RegionResponseDTO> getAllVisible();


    List<RegionResponseDTO> getAllUnVisible();

    RegionResponseDTO update(UUID regionID, RegionUpdateDto updateDTO);

    void deleteByID(UUID regionID);

    void deleteSelectedRegions(List<UUID> regionIDs);

}
