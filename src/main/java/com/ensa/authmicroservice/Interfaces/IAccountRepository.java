package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IAccountRepository extends MongoRepository<UserEntity, String>{
    @Query("{ userName: ?0 }")
    UserEntity findUserByUserName(String userName);

    @Query("{ userName: ?0 }")
    UserEntity deleteUserByUserName(String userName);
}
