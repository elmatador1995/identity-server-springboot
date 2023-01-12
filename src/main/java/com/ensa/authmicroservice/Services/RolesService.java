package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.RolesEntity;
import com.ensa.authmicroservice.Dto.RolesDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Interfaces.IRolesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolesService {
    @Autowired
    private IRolesRepository _repository;

    public List<RolesDto> getRoles() {
        List<RolesEntity> Roles = _repository.findAll();
        List<RolesDto> RolesDto = new ArrayList<RolesDto>();
        ModelMapper modelMapper = new ModelMapper();
        List<RolesDto> dtos = Roles
                .stream()
                .map(RolesEntity -> modelMapper.map(RolesEntity, RolesDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public RolesDto getRolesByUid(String roleId) {
        RolesEntity RolesEntity = _repository.findByUid(roleId);
        RolesDto RolesDto = new RolesDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(RolesEntity, RolesDto);
        return RolesDto;
    }

    public RolesDto getRolesByName(String roleName) {
        RolesEntity RolesEntity = _repository.findByRoleName(roleName);
        RolesDto RolesDto = new RolesDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(RolesEntity, RolesDto);
        return RolesDto;
    }

    public RespDto InsertRole(RolesDto _dto) {
        RespDto respDto = new RespDto();
        try{
            RolesEntity RolesEntity = _repository.findByRoleName(_dto.getRoleName());

            if(RolesEntity != null){
                respDto.setStatusLabel("Roles Already Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(UUID.randomUUID().toString());
            RolesEntity roles = new RolesEntity();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, roles);
            _repository.save(roles);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
        }
        return respDto;
    }


    public RespDto DeleteRole(String uid) {
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
    public RespDto DeleteAllRole() {
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

    public RespDto UpdateRole(RolesDto _dto) {
        RespDto respDto = new RespDto();
        try{
            RolesEntity RolesEntity = _repository.findByUid(_dto.getUid());
            if(RolesEntity == null){
                respDto.setStatusLabel("Roles Type Not Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(RolesEntity.getUid());
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, RolesEntity);
            _repository.save(RolesEntity);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusCode("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusCode("999");
            return respDto;
        }
        return respDto;
    }
}
