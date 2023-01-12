package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Dto.*;
import com.ensa.authmicroservice.Manager.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {
    @Autowired
    private AccountService _accountService;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    public AuthRespDto Login(AuthReqDto _dto){
        AuthRespDto auth = new AuthRespDto();
        if(_dto.getPassword() == null || _dto.getPassword().isBlank()
                || _dto.getPassword() == null || _dto.getPassword().isBlank()){
            auth.setStatusCode("001");
            auth.setStatusLabel("Password Or Username Required");
            return auth;
        }

        //Check Credentials
        UserProfilDto user = _accountService.getUserProfile(_dto.getUsername());
        if(user == null){
            auth.setStatusCode("002");
            auth.setStatusLabel("userNotFound");
            return auth;
        }

        if(!user.getPassword().equals( _dto.getPassword())){
            auth.setStatusCode("003");
            auth.setStatusLabel("Password Or Username Incorrect");
            return auth;
        }

        //Generate Jwt
        JwtTokenUsersDto jwtTokendto = jwtTokenManager.generateJwtToken(user, true);
        if(jwtTokendto.getJwtToken().isBlank()){
            auth.setStatusCode("999");
            auth.setStatusLabel("System Error");
            return auth;
        }

        auth.setAccessToken(jwtTokendto.getJwtToken());
        auth.setRefreshToken(jwtTokendto.getRefreshToken());
        auth.setStatusCode("000");
        auth.setStatusLabel("Operation Successfully");
        return  auth;
    }
    public RespDto Logout(String token){
        token = token.replaceAll(" ","").replace("Bearer","");
        RespDto _dto = new RespDto();
        try{
            if(token == null || token.isBlank()){
                _dto.setStatusCode("001");
                _dto.setStatusLabel("Token not exist");
                return _dto;
            }
            String username = jwtTokenManager.getUsernameFromToken(token);
            if(!jwtTokenService.LogoutJwtToken(token,username)){
                _dto.setStatusCode("999");
                _dto.setStatusLabel("Error System");
                return _dto;
            }

            _dto.setStatusCode("000");
            _dto.setStatusLabel("Logout Successful");
            return _dto;
        }catch (Exception ex){
            _dto.setStatusCode("999");
            _dto.setStatusLabel("Error System");
            return _dto;
        }
    }
}
