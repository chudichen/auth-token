package com.chudichen.auth.token.dao;

import com.chudichen.auth.token.session.AuthSession;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * auth-token持久层默认的实现类，基于内存Map
 *
 * @author chudichen
 * @since 2020-09-22
 */
public class AuthTokenDaoDefault implements AuthTokenDao {

    /** 所有的数据集合 */
    private final Cache<String, Object> data;

    public AuthTokenDaoDefault() {
        data = CacheBuilder.newBuilder()
                .maximumSize(1024)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .refreshAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }


    @Override
    public String getValue(String key) {
        return (String) data.getIfPresent(key);
    }

    @Override
    public void setValue(String key, String value, long timeout) {
        data.put(key, value);
    }

    @Override
    public void delKey(String key) {
        data.invalidate(key);
    }

    @Override
    public AuthSession getAuthSession(String sessionId) {
        return (AuthSession) data.getIfPresent(sessionId);
    }

    @Override
    public void saveAuthSession(AuthSession session, long timeout) {
        data.put(session.getId(), session);
    }

    @Override
    public void updateAuthSession(AuthSession session) {
        // 无动作
    }

    @Override
    public void delAuthSession(String sessionId) {
        data.invalidate(sessionId);
    }
}
