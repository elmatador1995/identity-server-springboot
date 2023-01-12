package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.RolesDto;
import com.ensa.authmicroservice.Dto.RespDto;

import com.ensa.authmicroservice.Services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    private RolesService _service;
    @GetMapping("/getAllRoles")
    public List<RolesDto> GetAllRoles(){
        return _service.getRoles();
    }

    @GetMapping("/getRoleByRoleName")
    public RolesDto GetRoleByRoleName(@RequestParam String roleName){
        return _service.getRolesByName(roleName);
    }

    @GetMapping("/getRoleByRoleId")
    public RolesDto GetRoleByRoleId(@RequestParam String roleId){
        return _service.getRolesByUid(roleId);
    }

    @PostMapping("/insertRole")
    public ResponseEntity<RespDto> InsertRole(@RequestBody RolesDto _dto){
        RespDto resp = _service.InsertRole(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/updateRole")
    public ResponseEntity<RespDto> UpdateRole(@RequestBody RolesDto _dto){
        RespDto resp = _service.UpdateRole(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<RespDto> deleteRole(@RequestParam String uid){
        RespDto resp = _service.DeleteRole(uid);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllRoles")
    public ResponseEntity<RespDto> DeleteAllRoles(){
        RespDto resp = _service.DeleteAllRole();
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
