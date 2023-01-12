package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Dto.ClaimsDto;
import com.ensa.authmicroservice.Dto.ClaimsDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Interfaces.IClaimsRepository;
import com.ensa.authmicroservice.Interfaces.IClaimsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ClaimsService {
    @Autowired
    private IClaimsRepository _repository;

    public List<ClaimsDto> getClaims() {
        List<ClaimsEntity> Claims = _repository.findAll();
        List<ClaimsDto> ClaimsDto = new ArrayList<ClaimsDto>();
        ModelMapper modelMapper = new ModelMapper();
        List<ClaimsDto> dtos = Claims
                .stream()
                .map(ClaimsEntity -> modelMapper.map(ClaimsEntity, ClaimsDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public List<ClaimsDto> getClaimsByUserId(String userId){
        List<ClaimsEntity> Claims = _repository.findByUserId(userId);
        List<ClaimsDto> ClaimsDto = new ArrayList<ClaimsDto>();
        ModelMapper modelMapper = new ModelMapper();
        List<ClaimsDto> dtos = Claims
                .stream()
                .map(ClaimsEntity -> modelMapper.map(ClaimsEntity, ClaimsDto.class))
                .collect(Collectors.toList());
        return dtos;
    }
    public RespDto InsertClaim(ClaimsDto _dto) {
        RespDto respDto = new RespDto();
        try{
            ClaimsEntity ClaimsEntity = _repository.findByUserIdAndClaimsType(_dto.getCustomerId(),_dto.getClaimType());

            if(ClaimsEntity != null){
                respDto.setStatusLabel("Claims Already Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(UUID.randomUUID().toString());
            ClaimsEntity claims = new ClaimsEntity();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, claims);
            _repository.save(claims);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }


    public RespDto DeleteClaim(String uid) {
        RespDto respDto = new RespDto();
        try{
            _repository.deleteById(uid);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }
    public RespDto DeleteAllClaim() {
        RespDto respDto = new RespDto();
        try{
            _repository.deleteAll();
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }

    public RespDto UpdateClaim(ClaimsDto _dto) {
        RespDto respDto = new RespDto();
        try{
            ClaimsEntity ClaimsEntity = _repository.findByUserIdAndClaimsType(_dto.getCustomerId(),_dto.getClaimType());
            if(ClaimsEntity == null){
                respDto.setStatusLabel("Claims Type Not Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(ClaimsEntity.getUid());
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, ClaimsEntity);
            _repository.save(ClaimsEntity);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }
}
