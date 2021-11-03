package com.adderbot.raid.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidTypeService {
    /**
     * Repository used for Raids
     */
    private final RaidTypeRepository raidTypeRepository;

    @Autowired
    public RaidTypeService(RaidTypeRepository raidTypeRepository) {
        this.raidTypeRepository = raidTypeRepository;
    }

    public RaidType getRaidTypeById(String raidTypeId) {
        return raidTypeRepository.findById(raidTypeId).block();
    }
}
