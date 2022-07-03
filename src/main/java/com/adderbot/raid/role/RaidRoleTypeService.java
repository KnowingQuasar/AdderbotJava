package com.adderbot.raid.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RaidRoleTypeService {

    private final RaidRoleTypeRepository _raidRoleTypeRepository;

    public Map<String, RaidRoleType> getAllRaidRolesMapById() {
        return _raidRoleTypeRepository.findAll().collectMap(RaidRoleType::getId).block();
    }
}
