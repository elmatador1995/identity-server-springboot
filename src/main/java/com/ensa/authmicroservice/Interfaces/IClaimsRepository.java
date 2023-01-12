package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IClaimsRepository extends MongoRepository<ClaimsEntity, String>{
    @Query("{ uid: ?0, claimsType: ?1 }")
    ClaimsEntity findByUserIdAndClaimsType(String userId, String claimsType);
    @Query("{ customerId: ?0}")
    List<ClaimsEntity> findByUserId(String userId);
}
