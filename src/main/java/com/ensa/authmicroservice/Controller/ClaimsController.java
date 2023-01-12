package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.ClaimsDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Services.ClaimsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimsController {
    @Autowired
    private ClaimsService _service;

    @GetMapping("/getAllClaims")
    public List<ClaimsDto> GetAllClaims(){
        return _service.getClaims();
    }

    @GetMapping("/getClaimsByUserId")
    public List<ClaimsDto> GetClaimsByUserId(@RequestParam String userId){
        return _service.getClaimsByUserId(userId);
    }

    @PostMapping("/insertClaim")
    public ResponseEntity<RespDto> InsertClaim(@RequestBody ClaimsDto _dto){
        RespDto resp = _service.InsertClaim(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/updateClaim")
    public ResponseEntity<RespDto> UpdateClaim(@RequestBody ClaimsDto _dto){
        RespDto resp = _service.UpdateClaim(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteClaim")
    public ResponseEntity<RespDto> deleteClaim(@RequestParam String uid){
        RespDto resp = _service.DeleteClaim(uid);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllClaims")
    public ResponseEntity<RespDto> DeleteAllClaims(){
        RespDto resp = _service.DeleteAllClaim();
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
