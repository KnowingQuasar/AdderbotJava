package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaidRepository  extends ReactiveMongoRepository<Raid, String> {
}
