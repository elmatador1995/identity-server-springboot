package com.ensa.authmicroservice.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GlobalConfiguration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalConfigurationEntity {
    @Id
    private String uid;
    private String configurationKey;
    private String configurationValue;

}
