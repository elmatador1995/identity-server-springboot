package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.PermissionEntity;
import com.ensa.authmicroservice.Collections.RolesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IPermissionsRepository extends MongoRepository<PermissionEntity, String>{
    @Query("{ roleId: ?0}")
    List<PermissionEntity> findByRoleId(String roleId);
    @Query("{ permissionId: ?0, roleId: ?1}")
    PermissionEntity findByPermissionIdAndRoleId(String permissionId, String roleId);
    @Query("{ uid: ?0}")
    PermissionEntity findByUid(String uid);
}
