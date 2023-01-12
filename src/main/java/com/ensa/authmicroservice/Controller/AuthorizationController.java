package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.JwtTokenValidationDto;
import com.ensa.authmicroservice.Dto.RefreshTokenDto;
import com.ensa.authmicroservice.Services.AuthenticateService;
import com.ensa.authmicroservice.Services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authorization")
public class AuthorizationController {
    @Autowired
    private AuthorizationService _authorizationService;

    @PostMapping("/verifyToken")
    public ResponseEntity<JwtTokenValidationDto> verifyToken(@RequestHeader String authorization){
        JwtTokenValidationDto _dto = _authorizationService.IsAccessTokenValid(authorization);
        if(_dto.getStatusCode().equals("001") || _dto.getStatusCode().equals("002")){
            _dto.setStatusCode("000");
            return new ResponseEntity<>(_dto, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(_dto, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<RefreshTokenDto> refreshToken(@RequestHeader String refreshToken){
        RefreshTokenDto _dto = _authorizationService.RefreshToken(refreshToken);
        if(_dto == null)
            return new ResponseEntity<>(_dto, HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(_dto, HttpStatus.OK);
    }
}
