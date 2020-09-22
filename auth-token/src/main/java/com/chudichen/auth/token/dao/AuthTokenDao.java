package com.chudichen.auth.token.dao;

import com.chudichen.auth.token.session.AuthSession;

/**
 * auth-token持久层的接口
 *
 * @author chudichen
 * @since 2020-09-22
 */
public interface AuthTokenDao {

    /**
     * 根据key获取value，如果没有，则返回空
     *
     * @param key 键的名称
     * @return value
     */
    String getValue(String key);

    /**
     * 写入指定key-value键值对，并设定过期时间（单位：秒）
     *
     * @param key 键名称
     * @param value 值
     * @param timeout 过期时间，单位：s
     */
    void setValue(String key, String value, long timeout);

    /**
     * 删除一个指定的key
     *
     * @param key 键名称
     */
    void delKey(String key);

    /**
     * 根据指定key的session，如果没有，则返回空
     *
     * @param sessionId 键名称
     * @return AuthSession
     */
    AuthSession getAuthSession(String sessionId);

    /**
     * 将指定session持久化
     *
     * @param session 要保存的session对象
     * @param timeout 过期时间，单位：s
     */
    void saveAuthSession(AuthSession session, long timeout);

    /**
     * 更新指定session
     *
     * @param session 要更新的session对象
     */
    void updateAuthSession(AuthSession session);

    /**
     * 删除一个指定的session
     *
     * @param sessionId sessionId
     */
    void delAuthSession(String sessionId);
}
