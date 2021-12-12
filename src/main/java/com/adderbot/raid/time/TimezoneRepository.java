package com.adderbot.raid.time;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TimezoneRepository extends ReactiveMongoRepository<TimezoneDto, String> {
}
