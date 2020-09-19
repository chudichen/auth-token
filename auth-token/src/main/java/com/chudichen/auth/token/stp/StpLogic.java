package com.chudichen.auth.token.stp;

import com.chudichen.auth.token.util.SpringMvcUtil;

import javax.servlet.http.HttpServletRequest;
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
    }


}
