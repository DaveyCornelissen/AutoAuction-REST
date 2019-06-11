package com.dcorn.api.utils.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String location;
}
