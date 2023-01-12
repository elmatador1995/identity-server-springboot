package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.RolesEntity;
import com.ensa.authmicroservice.Collections.UserEntity;
import com.ensa.authmicroservice.Dto.*;
import com.ensa.authmicroservice.Interfaces.IAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private IAccountRepository _repositoryUser;
    @Autowired
    private ClaimsService _claimsService;
    @Autowired
    private ClaimsTypeService _claimsTypeService;
    @Autowired
    private RolesService _rolesService;
    RespDto respDto = new RespDto();
    public RespDto CreateUser(UserDto _dto){
        try{
            UserEntity getUser = _repositoryUser.findUserByUserName(_dto.getUserName());
            if(getUser != null){
                respDto.setStatusLabel("User Already Exist, Please Change UserName");
                respDto.setStatusCode("001");
                return  respDto;
            }

            //CreateUser
            _dto.setUid(UUID.randomUUID().toString());
            UserEntity user = new UserEntity();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, user);
            _repositoryUser.save(user);

            //Create UserClaims
            List<ClaimsTypeDto> getAllClaimsType = _claimsTypeService.getClaimsType();

            if(getAllClaimsType.size() == 0){
                respDto.setStatusLabel("Need to Configure Claims Type");
                respDto.setStatusCode("001");
                return  respDto;
            }

            for (ClaimsTypeDto item : getAllClaimsType) {
                ClaimsDto claimsDto = new ClaimsDto();
                claimsDto.setUid(UUID.randomUUID().toString());
                claimsDto.setCustomerId(_dto.getUid());
                claimsDto.setClaimType(item.getName());
                switch (item.getName()){
                    case "first_name":
                        claimsDto.setClaimValue(_dto.getFirstName());
                        break;
                    case "last_name":
                        claimsDto.setClaimValue(_dto.getLastName());
                        break;
                    case "user_name":
                        claimsDto.setClaimValue(_dto.getUserName());
                        break;
                    case "validation_key":
                        claimsDto.setClaimValue(_dto.getValidationKey());
                        break;
                    case "phone_number":
                        claimsDto.setClaimValue(_dto.getPhonenumber());
                        break;
                    case "role_name":
                        var getRoleById = _rolesService.getRolesByUid(_dto.getRoleId());
                        claimsDto.setClaimValue(getRoleById.getRoleName());
                        break;
                    case "role_id":
                        claimsDto.setClaimValue(_dto.getRoleId());
                        break;
                }
                _claimsService.InsertClaim(claimsDto);
            }

            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
       return respDto;
    }

    public UserProfilDto getUserProfile(String username){
        UserProfilDto userProfilDto = new UserProfilDto();
        UserEntity getUser = _repositoryUser.findUserByUserName(username);
        if(getUser == null)
            return null;

        RolesDto getRole = _rolesService.getRolesByUid(getUser.getRoleId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(getUser, userProfilDto);
        userProfilDto.setRoleName(getRole.getRoleName());
        return  userProfilDto;
    }

    public RespDto DeleteUser(String username) {
        RespDto respDto = new RespDto();
        try{
            _repositoryUser.deleteUserByUserName(username);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }

    public RespDto DeleteAllUser() {
        RespDto respDto = new RespDto();
        try{
            _repositoryUser.deleteAll();
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }
}
