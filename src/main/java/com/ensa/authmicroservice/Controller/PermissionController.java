package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.PermissionDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Dto.PermissionDto;
import com.ensa.authmicroservice.Services.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionsService _service;
    @GetMapping("/getPermissionsByRoleId")
    public List<PermissionDto> GetAllPermissions(@RequestParam String roleId){
        return _service.getPermissionsByRoleId(roleId);
    }

    @PostMapping("/insertPermission")
    public ResponseEntity<RespDto> InsertPermission(@RequestBody PermissionDto _dto){
        RespDto resp = _service.InsertPermission(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/updatePermission")
    public ResponseEntity<RespDto> UpdatePermission(@RequestBody PermissionDto _dto){
        RespDto resp = _service.UpdatePermission(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deletePermission")
    public ResponseEntity<RespDto> deletePermission(@RequestParam String uid){
        RespDto resp = _service.DeletePermission(uid);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllPermissions")
    public ResponseEntity<RespDto> DeleteAllPermissions(){
        RespDto resp = _service.DeleteAllPermission();
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
