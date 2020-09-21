package com.chudichen.auth.token;

import com.chudichen.auth.token.config.AuthTokenConfig;
import com.chudichen.auth.token.config.AuthTokenConfigFactory;

/**
 * 管理所有的auth-token对象
 *
 * @author Michael Chu
 * @since 2020-09-19 15:01
 */
public class AuthTokenManager {

    /** 配置文件Bean */
    private static volatile AuthTokenConfig authTokenConfig;

    public static AuthTokenConfig getConfig() {
        if (authTokenConfig == null) {
            initConfig();
        }
        return authTokenConfig;
    }

    /**
     * 设置配置值
     *
     * @param authTokenConfig 配置
     */
    public static void setConfig(AuthTokenConfig authTokenConfig) {
        AuthTokenManager.authTokenConfig = authTokenConfig;
        if (AuthTokenManager.authTokenConfig.getShowValue()) {

        }
    }

    public synchronized static void initConfig() {
        if (authTokenConfig == null) {
            setConfig(AuthTokenConfigFactory.createConfig());
        }
    }


}
