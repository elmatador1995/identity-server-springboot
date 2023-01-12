package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.ClaimsTypeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IClaimsTypeRepository extends MongoRepository<ClaimsTypeEntity, String>{
    @Query("{ name: ?0 }")
    ClaimsTypeEntity findByClaimTypeName(String claimTypeName);

    @Query("{ uid: ?0 }")
    ClaimsTypeEntity findByClaimTypeId(String uid);
}
