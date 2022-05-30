package com.adderbot.raid.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaidTypeService {
    /**
     * Repository used for Raids
     */
    private final RaidTypeRepository raidTypeRepository;

    public RaidType getRaidTypeById(String raidTypeId) {
        return raidTypeRepository.findById(raidTypeId).block();
    }
}
