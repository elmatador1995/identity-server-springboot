package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "JwtTokenUsers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenUsersEntity {
    @Id
    private String uid;
    private String username;
    private String jwtToken;
    private String refreshToken;
    private Boolean isExpired;
    private Date DateCreate;
    private Boolean isLogin;
    private Boolean isLogout;
    private Date loginTime;
    private Date logoutTime;
}
