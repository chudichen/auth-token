package com.chudichen.auth.token;

import com.chudichen.auth.token.config.AuthTokenConfig;
import com.chudichen.auth.token.config.AuthTokenConfigFactory;
import com.chudichen.auth.token.dao.AuthTokenDao;
import com.chudichen.auth.token.dao.AuthTokenDaoDefault;
import com.chudichen.auth.token.stp.StpInterface;
import com.chudichen.auth.token.stp.StpInterfaceDefaultImpl;
import com.chudichen.auth.token.util.AuthTokenInsideUtil;

/**
 * 管理所有的auth-token对象
 *
 * @author Michael Chu
 * @since 2020-09-19 15:01
 */
public class AuthTokenManager {

    /** 配置文件Bean */
    private static volatile AuthTokenConfig authTokenConfig;
    /** 持久层dao */
    private static AuthTokenDao dao;
    /** 权限验证 */
    private static StpInterface<Object> stp;

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
            AuthTokenInsideUtil.printAuthToken();
        }
    }

    public synchronized static void initConfig() {
        if (authTokenConfig == null) {
            setConfig(AuthTokenConfigFactory.createConfig());
        }
    }

    public static AuthTokenDao getDao() {
        if (dao == null) {
            initDao();
        }
        return dao;
    }

    public synchronized static void setDao(AuthTokenDao dao) {
        AuthTokenManager.dao = dao;
    }

    public synchronized static void initDao() {
        if (dao == null) {
            setDao(new AuthTokenDaoDefault());
        }
    }

    public static StpInterface<Object> getStp() {
        if (stp == null) {
            initStp();
        }

        return stp;
    }

    public static void setStp(StpInterface<Object> stp) {
        AuthTokenManager.stp = stp;
    }

    public synchronized static void initStp() {
        if (stp == null) {
            setStp(new StpInterfaceDefaultImpl());
        }
    }
}
