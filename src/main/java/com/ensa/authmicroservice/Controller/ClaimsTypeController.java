package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.ClaimsTypeDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Dto.UserDto;
import com.ensa.authmicroservice.Services.ClaimsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claimsType")
public class ClaimsTypeController {
    @Autowired
    private ClaimsTypeService _service;

    @GetMapping("/getAllClaimsType")
    public List<ClaimsTypeDto> GetAllClaims(){
        return _service.getClaimsType();
    }

    @PostMapping("/insertClaimType")
    public ResponseEntity<RespDto> InsertClaimType(@RequestBody ClaimsTypeDto _dto){
        RespDto resp = _service.InsertClaimType(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/updateClaimType")
    public ResponseEntity<RespDto> UpdateClaimType(@RequestBody ClaimsTypeDto _dto){
        RespDto resp = _service.UpdateClaimType(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClaimType")
    public ResponseEntity<RespDto> DeleteClaimType(@RequestParam String uid){
        RespDto resp = _service.DeleteClaimType(uid);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllClaimsType")
    public ResponseEntity<RespDto> DeleteAllClaimsType(){
        RespDto resp = _service.DeleteAllClaimType();
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
