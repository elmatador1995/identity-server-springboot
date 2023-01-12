package com.ensa.authmicroservice.Controller;

import com.ensa.authmicroservice.Dto.GlobalConfigDto;
import com.ensa.authmicroservice.Dto.RespDto;
import com.ensa.authmicroservice.Services.GlobalConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class GlobalConfigurationController {
    @Autowired
    private GlobalConfigurationService _service;

    @GetMapping("/getAllConfigurations")
    public ResponseEntity<List<GlobalConfigDto>> getAllConfigurations(){
        List<GlobalConfigDto> resp = _service.getAllConfigurations();
        if(resp == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/getConfigByKey")
    public ResponseEntity<GlobalConfigDto> getConfigByKey(@RequestParam String key){
        GlobalConfigDto resp = _service.getConfigByKey(key);
        if(resp == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/insertConfig")
    public ResponseEntity<RespDto> InsertConfig(@RequestBody GlobalConfigDto _dto){
        RespDto resp = _service.InsertConfig(_dto);
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/updateConfig")
    public ResponseEntity<RespDto> updateConfig(@RequestBody GlobalConfigDto _dto){
        RespDto resp = _service.UpdateConfig(_dto);
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllConfig")
    public ResponseEntity<RespDto> DeleteAllConfig(){
        RespDto resp = _service.DeleteAllConfig();
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/deleteConfig")
    public ResponseEntity<RespDto> DeleteConfig(@RequestParam String uid){
        RespDto resp = _service.DeleteConfig(uid);
        if(resp.getStatusCode().equals("999"))
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
