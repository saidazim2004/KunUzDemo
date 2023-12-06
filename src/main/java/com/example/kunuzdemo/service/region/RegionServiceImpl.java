package com.example.kunuzdemo.service.region;

import com.example.kunuzdemo.dtos.request.RegionCreateDTO;
import com.example.kunuzdemo.dtos.request.RegionUpdateDto;
import com.example.kunuzdemo.dtos.response.RegionResponseDTO;
import com.example.kunuzdemo.entity.Region;
import com.example.kunuzdemo.exceptions.DataNotFoundException;
import com.example.kunuzdemo.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
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



    @Override
    public RegionResponseDTO getById(UUID regionId) {
        Region regionByID = getRegionByID(regionId);

        return modelMapper.map(regionByID, RegionResponseDTO.class);
    }



    @Override
    public List<RegionResponseDTO> getAllUnVisible() {
        return modelMapper.map(regionRepository.findAllUnVisible(), new TypeToken<List<RegionResponseDTO>>() {}.getType());
    }

    @Override
    public List<RegionResponseDTO> getAllVisible() {
        return modelMapper.map(regionRepository.findAllVisible(), new TypeToken<List<RegionResponseDTO>>() {}.getType());
    }

    @Override
    public List<RegionResponseDTO> getAll() {
        return modelMapper.map(regionRepository.findAll() , new TypeToken<List<RegionResponseDTO>>(){}.getType());
    }


    @Override
    public RegionResponseDTO update(UUID regionID, RegionUpdateDto updateDTO) {
        Region region = regionRepository.findById(regionID).orElseThrow(
                () -> new DataNotFoundException("Region not found with ID: " + regionID)
        );
        modelMapper.map(updateDTO, region);
        Region savedRegion = regionRepository.save(region);
        return modelMapper.map(savedRegion, RegionResponseDTO.class);
    }



    @Override
    public void deleteByID(UUID regionID) {
        if (!regionRepository.existsById(regionID))
            throw new DataNotFoundException("Region not found with ID: " + regionID);
        regionRepository.deleteById(regionID);
    }

    @Override
    public void deleteSelectedRegions(List<UUID> regionIDs) {
        for (UUID regionID : regionIDs) {
            deleteByID(regionID);
        }
    }
}
