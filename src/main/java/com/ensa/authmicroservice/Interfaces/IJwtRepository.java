package com.ensa.authmicroservice.Interfaces;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.JwtTokenUsersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IJwtRepository extends MongoRepository<JwtTokenUsersEntity, String>{
    @Query("{ jwtToken: ?0, username: ?1 }")
    JwtTokenUsersEntity findByJwtByTokenAndUserId(String jwtToken, String userId);

    @Query("{ refreshToken: ?0 }")
    JwtTokenUsersEntity findByJwtByRefreshToken(String refreshToken);
    @Query("{ uid: ?0 }")
    JwtTokenUsersEntity findByJwtUserByUid(String uid);
}


