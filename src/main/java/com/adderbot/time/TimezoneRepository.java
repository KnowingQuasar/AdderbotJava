package com.adderbot.time;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TimezoneRepository extends ReactiveMongoRepository<TimezoneDto, String> {
}
