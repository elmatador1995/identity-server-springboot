package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.*;
import com.ensa.authmicroservice.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService _service;
    @PostMapping("/registration")
    public ResponseEntity<RespDto> CreateUser(@RequestBody UserDto _dto){
        RespDto resp = _service.CreateUser(_dto);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }
    @GetMapping("/userProfile")
    public ResponseEntity<UserProfilDto> getUserProfile(@RequestParam String username){
        UserProfilDto userProfilDto = _service.getUserProfile(username);
        if(userProfilDto == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(userProfilDto, HttpStatus.OK);
    }
    @PostMapping("/updateProfile")
    public RespDto UpdateProfile(UserDto _dto){
        return null;
    }

    @PostMapping("/forgetPwdSimulation")
    public RespForgetPwdSimulationDto forgetPwdSimulation(ForgetPwdSimulationDto _dto){
        return null;
    }

    @PostMapping("/forgetPwdConfirmation")
    public RespDto forgetPwdConfirmation(ForgetPwdConfirmationDto _dto){
        return null;
    }

    @PostMapping("/updatePwd")
    public RespDto updatePwdSimulation(@RequestBody UpdatePwdDto _dto){
        return null;
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<RespDto> deleteUser(@RequestParam String username){
        RespDto resp = _service.DeleteUser(username);
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<RespDto> deleteAllUsers(){
        RespDto resp = _service.DeleteAllUser();
        if(resp.getStatusCode() == "999")
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
