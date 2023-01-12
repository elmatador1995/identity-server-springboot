package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesEntity {
    @Id
    private String uid;
    private String roleName;

}
