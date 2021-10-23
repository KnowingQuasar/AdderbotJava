package com.adderbot.repositories;

import com.adderbot.model.RaidType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaidTypeRepository extends MongoRepository<RaidType, String> {

}
