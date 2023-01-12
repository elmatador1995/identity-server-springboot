package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.ClaimsTypeEntity;
import com.ensa.authmicroservice.Dto.ClaimsTypeDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Interfaces.IClaimsTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClaimsTypeService{
    @Autowired
    private IClaimsTypeRepository _repository;

    public List<ClaimsTypeDto> getClaimsType() {
        List<ClaimsTypeEntity> claimsType = _repository.findAll();
        List<ClaimsTypeDto> claimsTypeDto = new ArrayList<ClaimsTypeDto>();
        ModelMapper modelMapper = new ModelMapper();
        List<ClaimsTypeDto> dtos = claimsType
                .stream()
                .map(claimsTypeEntity -> modelMapper.map(claimsTypeEntity, ClaimsTypeDto.class))
                .collect(Collectors.toList());

        return dtos;
    }


    public RespDto InsertClaimType(ClaimsTypeDto _dto) {
        RespDto respDto = new RespDto();
        try{
            ClaimsTypeEntity claimsTypeEntity = _repository.findByClaimTypeName(_dto.getName());
            if(claimsTypeEntity != null){
                respDto.setStatusLabel("Claims Type Already Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(UUID.randomUUID().toString());
            ClaimsTypeEntity claimsType = new ClaimsTypeEntity();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, claimsType);
            _repository.save(claimsType);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }


    public RespDto DeleteClaimType(String uid) {
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

    public RespDto DeleteAllClaimType() {
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

    public RespDto UpdateClaimType(ClaimsTypeDto _dto) {
        RespDto respDto = new RespDto();
        try{
            ClaimsTypeEntity claimsTypeEntity = _repository.findByClaimTypeId(_dto.getUid());
            if(claimsTypeEntity == null){
                respDto.setStatusLabel("Claims Type Not Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            claimsTypeEntity.setName(_dto.getName());
            _repository.save(claimsTypeEntity);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }
}
