package com.chudichen.auth.token.stp;

import com.chudichen.auth.token.AuthTokenManager;
import com.chudichen.auth.token.config.AuthTokenConfig;
import com.chudichen.auth.token.exception.NotPermissionException;
import com.chudichen.auth.token.session.AuthSession;
import com.chudichen.auth.token.util.AuthTokenInsideUtil;
import com.chudichen.auth.token.util.SpringMvcUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * auth-token，鉴权逻辑的实现类
 *
 * @author Michael Chu
 * @since 2020-09-19 15:20
 */
public class StpLogic {

    /**
     * 持久化的key前缀，多账号体系时以此来区分，比如：login，user，admin
     */
    private String loginKey;

    /**
     * 创建StpLogic，并指定loginKey
     *
     * @param loginKey 指定loginKey
     */
    public StpLogic(String loginKey) {
        this.loginKey = loginKey;
    }

    // =================== 获取token 相关 ===================

    /**
     * 随机生成一个tokenValue
     *
     * @return 生成的tokenValue
     */
    public String randomTokenValue() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取当前tokenValue
     *
     * @return 当前tokenValue
     */
    public String getTokenValue() {
        // 1. 获取相应对象
        HttpServletRequest request = SpringMvcUtil.getRequest();
        AuthTokenConfig config = AuthTokenManager.getConfig();
        String keyTokenName = getKeyTokenName();

        // 2. 尝试从request里获取
        if (request.getAttribute(AuthTokenInsideUtil.JUST_CREATE_SAVE_KEY) != null) {
            return String.valueOf(request.getAttribute(AuthTokenInsideUtil.JUST_CREATE_SAVE_KEY));
        }

        // 3. 尝试从请求体里面读取
        if (config.getReadFromBody()) {
            String tokenValue = request.getParameter(keyTokenName);
            if (!StringUtils.isEmpty(tokenValue)) {
                return tokenValue;
            }
        }

        // 4. 尝试从header里取
        if (config.getReadFromHead()) {
            String tokenValue = request.getHeader(keyTokenName);
            if (!StringUtils.isEmpty(tokenValue)) {
                return tokenValue;
            }
        }

        // 5. 都获取不到就返回空
        return null;
    }

    /**
     * 获取指定id对应的tokenValue
     *
     * @param loginId 登录id
     * @return value
     */
    public String getTokenValueByLoginId(Object loginId) {
        return AuthTokenManager.getDao().getValue(getKeyLoginId(loginId));
    }

    /**
     * 获取当前会话的token信息：tokenName和tokenValue
     *
     * @return 一个Map对象
     */
    public Map<String, String> getTokenInfo() {
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenName", getKeyTokenName());
        map.put("tokenValue", getTokenValue());
        return map;
    }

    // =================== 登录相关操作 ===================

    /**
     * 检验当前会话是否已经登录，如未登录，则抛出异常
     */
    public void checkLogin() {
        getLoginId();
    }

    /**
     * 获取当前会话id，如果未登录，则抛出异常
     * @return .
     */
    private Object getLoginId() {
        return null;
    }

    /**
     * 获取当前会话登录id，如果未登录，则返回默认值
     *
     * @param defaultValue id
     * @param <T> 类型
     * @return loginId
     */
    @SuppressWarnings("unchecked")
    public <T> T getLoginId(T defaultValue) {
        Object loginId = getLoginIdDefaultNull();
        if (loginId == null) {
            return defaultValue;
        }
        if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(loginId.toString());
        }
        if (defaultValue instanceof Long) {
            return (T) Long.valueOf(loginId.toString());
        }
        if (defaultValue instanceof String) {
            return (T) loginId.toString();
        }
        return (T) loginId;
    }

    /**
     * 获取当前会话登录id，如果未登录，则返回null
     *
     * @return 登录id
     */
    public Object getLoginIdDefaultNull() {
        String tokenValue = getTokenValue();
        if (tokenValue != null) {
            return AuthTokenManager.getDao().getValue(getKeyTokenValue(tokenValue));
        }
        return null;
    }

    /**
     * 获取当前会话登录id，并转换为String
     *
     * @return 登录id
     */
    public String getLoginIdAsString() {
        return String.valueOf(getLoginId());
    }

    /**
     * 获取当前会话登录id，并转换为int
     *
     * @return id值
     */
    public int getLoginIdAsInt() {
        return Integer.parseInt(String.valueOf(getLoginId()));
    }

    /**
     * 获取当前会话登录id，并转换为long
     *
     * @return 转换为long
     */
    public long getLoginIdAsLong() {
        return Long.parseLong(String.valueOf(getLoginId()));
    }

    /**
     * 获取指定token对应的登录id，如果未登录，则返回null
     *
     * @param tokenValue token
     * @return 对应的token
     */
    public Object getLoginIdByToken(String tokenValue) {
        return Long.valueOf(String.valueOf(getLoginId()));
    }

    // =================== session相关 ===================

    /**
     * 获取指定key的session，如果没有，isCreate是否创建
     *
     * @param sessionId sessionId
     * @param isCreate 是否创建
     * @return Session
     */
    protected AuthSession getSessionBySessionId(String sessionId, boolean isCreate) {
        AuthSession session = AuthTokenManager.getDao().getAuthSession(sessionId);
        if (session == null && isCreate) {
            session = new AuthSession(sessionId);
            AuthTokenManager.getDao().saveAuthSession(session, AuthTokenManager.getConfig().getTimeout());
        }
        return session;
    }

    /**
     * 获取指定loginId的session，如果没有，isCreate来看是否需要创建
     *
     * @param loginId 登录id
     * @param isCreate 是否新建
     * @return Session
     */
    public AuthSession getSessionByLoginId(Object loginId, boolean isCreate) {
        return getSessionBySessionId(getKeySession(loginId), isCreate);
    }

    /**
     * 获取指定loginId的session
     *
     * @param loginId 登录id
     * @return Session
     */
    public AuthSession getSessionByLoginId(Object loginId) {
        return getSessionByLoginId(getKeySession(loginId), false);
    }

    /**
     * 获取当前会话的session
     *
     * @return Session
     */
    public AuthSession getSession() {
        return getSessionBySessionId(getKeySession(getLoginId()), true);
    }

    // =================== 权限验证操作 ===================

    /**
     * 指定loginId是否含有指定权限
     *
     * @param loginId 登录id
     * @param pCode 权限
     * @return {@code true}标示包含
     */
    public boolean hasPermission(Object loginId, Object pCode) {
        List<Object> permissionCodeList = AuthTokenManager.getStp().getPermissionCodeList(loginId, loginKey);
        return permissionCodeList != null && permissionCodeList.contains(pCode);
    }

    /**
     * 当前会话是否含有指定权限
     *
     * @param pCode 权限
     * @return {@code true} 表示通过
     */
    public boolean hasPermission(Object pCode) {
        return hasPermission(getLoginId(), pCode);
    }

    /**
     * 当前账号是否包含指定权限，没有则抛出异常
     *
     * @param pCode 权限
     */
    public void checkPermission(Object pCode) {
        if (!hasPermission(pCode)) {
            throw new NotPermissionException(pCode, this.loginKey);
        }
    }

    /**
     * 当前账号是否含有指定权限， 【指定多个必须全部含有】
     *
     * @param objects 待检测的权限
     * @return {@code true} 标示验证通过
     */
    public boolean checkPermissionAnd(Object... objects) {
        Object loginId = getLoginId();
        List<Object> permissionCodeList = AuthTokenManager.getStp().getPermissionCodeList(loginId, loginKey);
        for (Object object : objects) {
            if (!permissionCodeList.contains(object)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 当前账号是否含有指定权限，【指定多个，有一个就可以了】
     *
     * @param objects 待检测的权限
     * @return {@code true} 标示验证通过
     */
    public boolean checkPermissionOr(Object... objects) {
        if (objects.length == 1) {
            return true;
        }

        Object loginId = getLoginId();
        List<Object> permissionCodeList = AuthTokenManager.getStp().getPermissionCodeList(loginId, loginKey);

        for (Object object : objects) {
            if (permissionCodeList.contains(object)) {
                return true;
            }
        }

        // 验证不通过
        return false;
    }

    // =================== 返回相应key ===================

    /**
     * 获取key：客户端tokenName
     *
     * @return tokenName
     */
    public String getKeyTokenName() {
        return AuthTokenManager.getConfig().getTokenName();
    }

    /**
     * 获取key前缀
     *
     * @return key前缀
     */
    public String getLoginKey() {
        return loginKey;
    }

    /**
     * 获取key： tokenValue持久化
     *
     * @param tokenValue .
     * @return key
     */
    public String getKeyTokenValue(String tokenValue) {
        return AuthTokenManager.getConfig().getTokenName() + ":" + loginKey + ":token:" + tokenValue;
    }

    /**
     * 获取key：id持久化
     *
     * @param loginId 登录id
     * @return key
     */
    public String getKeyLoginId(Object loginId) {
        return AuthTokenManager.getConfig().getTokenName() + ":" + loginKey + ":id:" + loginId;
    }

    /**
     * 获取key： 通过loginId获取sessionId
     *
     * @param loginId 登录id
     * @return session的key
     */
    public String getKeySession(Object loginId) {
        return AuthTokenManager.getConfig().getTokenName() + ":" + loginKey + ":session:" + loginId;
    }
}
