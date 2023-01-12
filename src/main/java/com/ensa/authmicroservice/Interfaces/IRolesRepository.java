package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.RolesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IRolesRepository extends MongoRepository<RolesEntity, String>{
    @Query("{ roleName: ?0}")
    RolesEntity findByRoleName(String roleName);
    @Query("{ uid: ?0}")
    RolesEntity findByUid(String uid);
}
