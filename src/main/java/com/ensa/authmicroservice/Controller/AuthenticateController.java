package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.AuthReqDto;
import com.ensa.authmicroservice.Dto.AuthRespDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {
    @Autowired
    private AuthenticateService _service;

    @PostMapping("/login")
    public ResponseEntity<AuthRespDto> Login(@RequestBody AuthReqDto _dto){
        AuthRespDto resp = _service.Login(_dto);
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<RespDto>  Logout (@RequestHeader String authorization){
        RespDto resp = _service.Logout(authorization);
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
