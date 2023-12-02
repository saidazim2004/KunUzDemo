package com.example.kunuzdemo.repository;

import com.example.kunuzdemo.entity.Region;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
    @Query(value = "from region r where r.id =:regionID and r.visible = true ")
    Optional<Region> findRegionById(@Param("regionID") @NotNull UUID regionID);

}
