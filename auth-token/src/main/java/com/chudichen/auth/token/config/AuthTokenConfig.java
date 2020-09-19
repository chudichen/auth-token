package com.chudichen.auth.token.config;

import lombok.Data;

/**
 * auth-token 的配置类
 *
 * @author Michael Chu
 * @since 2020-09-19 15:38
 */
@Data
public class AuthTokenConfig {

    /** token的名称（同时也是cookie的名称） */
    private String tokenName;
    /** token的有效期，单位是秒，默认为30天 */
    private Long timeout;
    /** 多账号登陆时，是否共享会话（为true时共用一个，为false时新登陆挤掉旧登陆） */
    private Boolean share;
    /** 是否尝试从body中获取token */
    private Boolean readFromBody;
    /** 是否尝试从head中获取token */
    private Boolean readFromHead;
    /** 是否尝试从cookie中获取token */
    private Boolean readFromCookie;
    /** 是否在初始化配置时打印版本信息 */
    private Boolean showValue;


    /**
     * 创建默认的AuthTokenConfig
     *
     * @return 默认值
     */
    public static AuthTokenConfig createDefault() {
        AuthTokenConfig authTokenConfig = new AuthTokenConfig();
        authTokenConfig.setTokenName("auth_token");
        authTokenConfig.setTimeout((long) (30 * 24 * 60 * 60));
        authTokenConfig.setShare(true);
        authTokenConfig.setReadFromBody(true);
        authTokenConfig.setReadFromHead(true);
        authTokenConfig.setReadFromCookie(true);
        authTokenConfig.setShowValue(true);
        return authTokenConfig;
    }

}
