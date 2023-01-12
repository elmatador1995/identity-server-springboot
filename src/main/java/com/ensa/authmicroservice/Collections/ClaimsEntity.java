package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Claims")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimsEntity {
    @Id
    private String uid;
    private String claimType;
    private String claimValue;
    private String customerId;
}
