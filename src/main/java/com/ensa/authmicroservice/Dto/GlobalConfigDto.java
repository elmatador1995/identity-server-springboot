package com.ensa.authmicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalConfigDto {
    private String uid;
    private String ConfigurationKey;
    private String ConfigurationValue;
}
