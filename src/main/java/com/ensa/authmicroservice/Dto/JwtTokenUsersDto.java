package com.ensa.authmicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenUsersDto {
    private String uid;
    private String username;
    private String refreshToken;
    private Boolean isExpired;
    private String jwtToken;
    private Date DateCreate;
    private Boolean isLogin;
    private Boolean isLogout;
    private Date loginTime;
    private Date logoutTime;
}
