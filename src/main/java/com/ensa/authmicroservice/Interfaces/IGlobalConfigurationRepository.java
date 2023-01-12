package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.GlobalConfigurationEntity;
import com.ensa.authmicroservice.Collections.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IGlobalConfigurationRepository extends MongoRepository<GlobalConfigurationEntity, String>{
    @Query("{ configurationKey: ?0 }")
    GlobalConfigurationEntity findConfigByKey(String configurationKey);
    @Query("{ uid: ?0 }")
    GlobalConfigurationEntity findConfigByUid(String uid);
}
