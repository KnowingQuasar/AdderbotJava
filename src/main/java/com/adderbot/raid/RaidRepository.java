package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RaidRepository  extends ReactiveMongoRepository<RaidType, String> {
}
