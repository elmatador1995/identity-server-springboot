package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.JwtTokenUsersEntity;
import com.ensa.authmicroservice.Dto.JwtTokenUsersDto;
import com.ensa.authmicroservice.Dto.JwtTokenValidationDto;
import com.ensa.authmicroservice.Dto.RefreshTokenDto;
import com.ensa.authmicroservice.Manager.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    private JwtTokenManager _jwtTokenManager;

    public JwtTokenValidationDto IsAccessTokenValid(String jwtToken){
        jwtToken = jwtToken.replaceAll(" ","").replace("Bearer","");
        return _jwtTokenManager.isValidateToken(jwtToken);
    }

    public RefreshTokenDto RefreshToken(String refreshToken){
        return _jwtTokenManager.RefreshJwtToken(refreshToken);
    }
}
