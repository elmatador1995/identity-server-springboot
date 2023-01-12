package com.ensa.authmicroservice.Services;

import com.ensa.authmicroservice.Collections.PermissionEntity;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Dto.PermissionDto;
import com.ensa.authmicroservice.Interfaces.IPermissionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionsService {
    @Autowired
    private IPermissionsRepository _repository;

    public List<PermissionDto> getPermissionsByRoleId(String roleId) {
        List<PermissionEntity> Permissions = _repository.findByRoleId(roleId);
        List<PermissionDto> PermissionDto = new ArrayList<PermissionDto>();
        ModelMapper modelMapper = new ModelMapper();
        List<PermissionDto> dtos = Permissions
                .stream()
                .map(PermissionEntity -> modelMapper.map(PermissionEntity, PermissionDto.class))
                .collect(Collectors.toList());
        return dtos;
    }


    public RespDto InsertPermission(PermissionDto _dto) {
        RespDto respDto = new RespDto();
        try{
            PermissionEntity PermissionEntity = _repository.findByPermissionIdAndRoleId(_dto.getPermissionId(), _dto.getRoleId());
            if(PermissionEntity != null){
                respDto.setStatusLabel("Permissions Already Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(UUID.randomUUID().toString());
            PermissionEntity claims = new PermissionEntity();
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


    public RespDto DeletePermission(String uid) {
        RespDto respDto = new RespDto();
        try{
            _repository.deleteById(uid);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusLabel("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusLabel("999");
        }
        return respDto;
    }
    public RespDto DeleteAllPermission() {
        RespDto respDto = new RespDto();
        try{
            _repository.deleteAll();
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusLabel("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusLabel("999");
        }
        return respDto;
    }

    public RespDto UpdatePermission(PermissionDto _dto) {
        RespDto respDto = new RespDto();
        try{
            PermissionEntity PermissionEntity = _repository.findByUid(_dto.getUid());

            if(PermissionEntity == null){
                respDto.setStatusLabel("Permissions Not Exist");
                respDto.setStatusCode("001");
                return respDto;
            }

            _dto.setUid(PermissionEntity.getUid());
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(_dto, PermissionEntity);
            _repository.save(PermissionEntity);
            respDto.setStatusLabel("Operation Successfully");
            respDto.setStatusLabel("000");
        }catch (Exception ex){
            respDto.setStatusLabel("System Error");
            respDto.setStatusLabel("999");
        }
        return respDto;
    }
}
