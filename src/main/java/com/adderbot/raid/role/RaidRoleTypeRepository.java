package com.adderbot.raid.role;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaidRoleTypeRepository extends ReactiveMongoRepository<RaidRoleType, String> {
}
