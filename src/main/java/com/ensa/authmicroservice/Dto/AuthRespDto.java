package com.ensa.authmicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRespDto {
    private String accessToken;
    private String refreshToken;
    private String StatusLabel;
    private String StatusCode;
}
