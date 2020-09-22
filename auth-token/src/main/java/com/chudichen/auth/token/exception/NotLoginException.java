package com.chudichen.auth.token.exception;

import com.chudichen.auth.token.stp.StpUtil;

/**
 * 没有登录抛出的异常
 *
 * @author chudichen
 * @since 2020-09-22
 */
public class NotLoginException extends RuntimeException {

    private static final long serialVersionUID = 1961751685199049160L;

    /** 登录key */
    private String loginKey;

    public NotLoginException() {
        this(StpUtil.getStpLogic().getLoginKey());
    }

    /**
     * 创建一个
     *
     * @param loginKey login_key
     */
    public NotLoginException(String loginKey) {
        super("当前会话未登录");
        this.loginKey = loginKey;
    }

    /**
     * 获得login_key
     *
     * @return login_key
     */
    public String getLoginKey() {
        return loginKey;
    }
}
