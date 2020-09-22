package com.chudichen.auth.token.exception;

import com.chudichen.auth.token.stp.StpUtil;

/**
 * 没有指定权限码，抛出的异常
 *
 * @author chudichen
 * @since 2020-09-22
 */
public class NotPermissionException extends RuntimeException {

    private static final long serialVersionUID = 4162876163453562429L;

    /** 权限码 */
    private Object code;
    /** 获取login_key */
    private String loginKey;

    public NotPermissionException(Object code) {
        this(code, StpUtil.getStpLogic().getLoginKey());
    }

    public NotPermissionException(Object code, String loginKey) {
        super("无此权限：" + code);
        this.code = code;
        this.loginKey = loginKey;
    }

    /**
     * 获得权限码
     *
     * @return 权限码
     */
    public Object getCode() {
        return code;
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
