package com.chudichen.auth.token.spring;

import com.chudichen.auth.token.AuthTokenManager;
import com.chudichen.auth.token.config.AuthTokenConfig;
import com.chudichen.auth.token.dao.AuthTokenDao;
import com.chudichen.auth.token.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 与SpringBoot集成，保证此类被扫描，即可完成auth-token与SpringBoot的集成
 *
 * @author chudichen
 * @since 2020-09-22
 */
@Component
public class SpringAuthToken {

    /**
     * 获取配置Bean
     *
     * @return 配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.auth-token")
    public AuthTokenConfig getAuthTokenConfig() {
        return AuthTokenConfig.createDefault();
    }

    /**
     * 注入配置Bean
     *
     * @param authTokenConfig 配置
     */
    @Autowired
    public void setConfig(AuthTokenConfig authTokenConfig) {
        AuthTokenManager.setConfig(authTokenConfig);
    }

    /**
     * 注入持久化Bean
     *
     * @param dao 持久层
     */
    @Autowired(required = false)
    public void setDao(AuthTokenDao dao) {
        AuthTokenManager.setDao(dao);
    }

    /**
     * 注入权限认证Bean
     *
     * @param stp 权限
     */
    public void setStp(StpInterface<Object> stp) {
        AuthTokenManager.setStp(stp);
    }
}
