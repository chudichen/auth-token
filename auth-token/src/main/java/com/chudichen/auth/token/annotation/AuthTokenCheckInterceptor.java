package com.chudichen.auth.token.annotation;

import com.chudichen.auth.token.stp.StpLogic;
import com.chudichen.auth.token.stp.StpUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 注解式鉴权 - 拦截器
 *
 * @author Michael Chu
 * @since 2020-09-19 15:03
 */
public class AuthTokenCheckInterceptor implements HandlerInterceptor {

    /** 底层的StpLogic对象 */
    private StpLogic stpLogic;

    /**
     * 创建，并指定一个默认的StpLogic
     */
    public AuthTokenCheckInterceptor() {
        this(StpUtil.stpLogic);
    }

    public AuthTokenCheckInterceptor(StpLogic stpLogic) {
        this.stpLogic = stpLogic;
    }
}
