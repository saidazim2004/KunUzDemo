package com.example.kunuzdemo.service.region;

import com.example.kunuzdemo.dtos.request.RegionCreateDTO;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.entity.Region;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{
    private final RegionRepository regionRepository ;
    private final ModelMapper modelMapper ;

    @Override
    public RegionResponseDTO create(RegionCreateDTO dto) {
        Region mappedRegion = modelMapper.map(dto, Region.class);
        Region savedRegion = regionRepository.save(mappedRegion);
        return modelMapper.map(savedRegion, RegionResponseDTO.class);
    }
    @Override
    public Region getRegion(UUID regionID) {
        return getRegionByID(regionID);
    }

    private Region getRegionByID(UUID regionID) {
        return regionRepository.findRegionById(regionID).orElseThrow(
                () -> new DataNotFoundException("Region not found with ID: " + regionID)
        );
    }
}
