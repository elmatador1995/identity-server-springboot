package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.ClaimsEntity;
import com.ensa.authmicroservice.Collections.GlobalConfigurationEntity;
import com.ensa.authmicroservice.Dto.ClaimsDto;
import com.ensa.authmicroservice.Dto.GlobalConfigDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Dto.UserProfilDto;
import com.ensa.authmicroservice.Interfaces.IGlobalConfigurationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GlobalConfigurationService {
    @Autowired
    private IGlobalConfigurationRepository _repository;
    public List<GlobalConfigDto> getAllConfigurations(){
        List<GlobalConfigurationEntity> globalConfig = _repository.findAll();

        if(globalConfig == null)
            return null;

        ModelMapper modelMapper = new ModelMapper();
        List<GlobalConfigDto> dtos = globalConfig
                .stream()
                .map(globalConfigEntity -> modelMapper.map(globalConfigEntity, GlobalConfigDto.class))
                .collect(Collectors.toList());

        return dtos;
    }

    public GlobalConfigDto getConfigByKey(String key){
        GlobalConfigurationEntity globalConfig = _repository.findConfigByKey(key);

        if(globalConfig == null)
            return null;

        GlobalConfigDto globalConfigDto = new GlobalConfigDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(globalConfig, globalConfigDto);

        return globalConfigDto;
    }

    public RespDto InsertConfig(GlobalConfigDto _dto){
        RespDto respDto = new RespDto();
        try{
            GlobalConfigurationEntity getConfig = _repository.findConfigByKey(_dto.getConfigurationKey());

            if(getConfig != null){
                respDto.setStatusLabel("Config Already Exist");
                respDto.setStatusCode("001");
                return respDto;
            }
            _dto.setUid(UUID.randomUUID().toString());
            GlobalConfigurationEntity globalConfig = new GlobalConfigurationEntity();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, globalConfig);
            _repository.save(globalConfig);

            respDto.setStatusLabel("Operation Successful");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("Error System");
            respDto.setStatusCode("999");
        }
        return respDto;
    }

    public RespDto UpdateConfig(GlobalConfigDto _dto){
        RespDto respDto = new RespDto();
        try{
            GlobalConfigurationEntity getConfig = _repository.findConfigByUid(_dto.getUid());

            if(getConfig == null){
                respDto.setStatusLabel("Config Not Exist");
                respDto.setStatusCode("001");
                return respDto;
            }
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, getConfig);
            _repository.save(getConfig);

            respDto.setStatusLabel("Operation Successful");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("Error System");
            respDto.setStatusCode("999");
        }
        return respDto;
    }

    public RespDto DeleteAllConfig() {
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

    public RespDto DeleteConfig(String uid) {
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
}
