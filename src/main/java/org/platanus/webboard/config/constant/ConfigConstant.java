package org.platanus.webboard.config.constant;

import lombok.Getter;

/**
 * Config 에서 사용하는 상수
 */
@Getter
public class ConfigConstant {
    public static final String PROPERTY_ENV_PROFILE_LOCAL = "local";
    public static final String PROPERTY_ENV_PROFILE_PRODUCTION = "production";
    public static final String PROPERTY_ENV_PROFILE_NOT_PRODUCTION = "!production";
}
