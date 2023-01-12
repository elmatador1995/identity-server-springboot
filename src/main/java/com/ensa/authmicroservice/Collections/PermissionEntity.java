package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity {
    @Id
    private String uid;
    private String roleId;
    private String permissionId; //
    private String serviceName;
}
