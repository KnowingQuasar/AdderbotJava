package com.adderbot.raid.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RaidTypeService {
    /**
     * Repository used for Raids
     */
    private final RaidTypeRepository raidTypeRepository;

    public Map<String, RaidType> getAllRaidTypesMapById() {
        return raidTypeRepository.findAll().collectMap(RaidType::getId).block();
    }
    public RaidType getRaidTypeById(String raidTypeId) {
        return raidTypeRepository.findById(raidTypeId).block();
    }
}
