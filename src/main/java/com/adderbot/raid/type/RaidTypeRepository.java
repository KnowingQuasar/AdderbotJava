package com.adderbot.raid.type;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaidTypeRepository extends ReactiveMongoRepository<RaidType, String> {
}
