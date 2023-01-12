package com.ensa.authmicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String uid;
    private String firstName;
    private String lastName;
    private String phonenumber;
    private String userName;
    private String validationKey;
    private String password;
    private String roleId;
}
