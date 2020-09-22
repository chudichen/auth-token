package com.chudichen.auth.token.session;

import com.chudichen.auth.token.AuthTokenManager;

/**
 * 自定义的Auth Session工具类
 *
 * @author chudichen
 * @since 2020-09-22
 */
public class AuthSessionCustomUtil {

    /** 添加上指定前缀，防止恶意伪造session */
    public static final String SESSION_KEY = "custom";

    public static String getSessionKey(String sessionId) {
        return AuthTokenManager.getConfig().getTokenName() + ":" + SESSION_KEY + ":session:" + sessionId;
    }

    /**
     * 指定key的session是否存在
     *
     * @param sessionId session的id
     * @return 是否存在
     */
    public boolean isExists(String sessionId) {
        return AuthTokenManager.getDao().getAuthSession(getSessionKey(sessionId)) != null;
    }

    /**
     * 获取指定的session
     *
     * @param sessionId sessionId
     * @param isCreate 如果不存在是否创建
     * @return 会话信息
     */
    public static AuthSession getSessionById(String sessionId, boolean isCreate) {
        String sessionKey = getSessionKey(sessionId);
        AuthSession session = AuthTokenManager.getDao().getAuthSession(sessionKey);
        if (session == null && isCreate) {
            session = new AuthSession(sessionKey);
            AuthTokenManager.getDao().saveAuthSession(session, AuthTokenManager.getConfig().getTimeout());
        }
        return session;
    }

    /**
     * 获取指定key的session，如果没有则新建并返回
     *
     * @param sessionId key
     * @return session对象
     */
    public static AuthSession getSessionById(String sessionId) {
        return getSessionById(sessionId, true);
    }

    /**
     * 删除指定key的session
     *
     * @param sessionId 删除指定key
     */
    public static void delSessionById(String sessionId) {
        AuthTokenManager.getDao().delAuthSession(getSessionKey(sessionId));
    }
}
