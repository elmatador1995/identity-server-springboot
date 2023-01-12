package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.JwtTokenUsersEntity;
import com.ensa.authmicroservice.Dto.JwtTokenUsersDto;
import com.ensa.authmicroservice.Dto.RefreshTokenDto;
import com.ensa.authmicroservice.Dto.UserProfilDto;
import com.ensa.authmicroservice.Interfaces.IJwtRepository;
import com.ensa.authmicroservice.Manager.JwtTokenManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenService {
    @Autowired
    private IJwtRepository _repository;
    @Autowired
    private AccountService _accountService;
    public boolean InsertJwtToken(String token, String username, String refreshToken){
        try{
            Date currentDate = new Date();
            JwtTokenUsersEntity jwtTokenUsers = new JwtTokenUsersEntity();
            jwtTokenUsers.setUid(UUID.randomUUID().toString());
            jwtTokenUsers.setUsername(username);
            jwtTokenUsers.setJwtToken(token);
            jwtTokenUsers.setRefreshToken(refreshToken);
            jwtTokenUsers.setDateCreate(currentDate);
            jwtTokenUsers.setLoginTime(currentDate);
            jwtTokenUsers.setIsLogin(true);
            jwtTokenUsers.setIsLogout(false);

            _repository.save(jwtTokenUsers);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean UpdateJwtToken(JwtTokenUsersDto _dto){
        try{
            JwtTokenUsersEntity jwtToken = _repository.findByJwtUserByUid(_dto.getUid());
            if(jwtToken == null)
                return true;

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, jwtToken);
            _repository.save(jwtToken);
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    public boolean LogoutJwtToken(String token, String username){
        try{
            JwtTokenUsersEntity jwtToken = _repository.findByJwtByTokenAndUserId(token, username);
            if(jwtToken == null)
                return true;

            jwtToken.setIsLogout(true);
            jwtToken.setLogoutTime(new Date());
            _repository.save(jwtToken);
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    public JwtTokenUsersDto IsExistJwtTokenByUserId(String token, String username) {
        JwtTokenUsersEntity jwtToken = _repository.findByJwtByTokenAndUserId(token,username);
        if(jwtToken == null)
            return null;

        JwtTokenUsersDto _dto = new JwtTokenUsersDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(jwtToken, _dto);

        return _dto;
    }

    public JwtTokenUsersDto jwtTokenByRefreshToken(String refreshToken) {
        JwtTokenUsersEntity jwtToken = _repository.findByJwtByRefreshToken(refreshToken);
        if(jwtToken == null)
            return null;

        JwtTokenUsersDto _dto = new JwtTokenUsersDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(jwtToken, _dto);

        return _dto;
    }

}
