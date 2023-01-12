package com.ensa.authmicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimsDto {
    private String uid;
    private String claimType;
    private String claimValue;
    private String customerId;
}
