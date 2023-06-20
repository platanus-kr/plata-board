package org.platanus.webboard.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "plataboard.environment")
@RequiredArgsConstructor
public class PropertyEnvironment {

    private final String profile;
    private final String frontendAddress;
    private final String attachFileStoragePath;
}
