package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private String uid;
    private String firstName;
    private String lastName;
    private String userName;
    private String phonenumber;
    private String validationKey;
    private String password;
    private String roleId;
}
