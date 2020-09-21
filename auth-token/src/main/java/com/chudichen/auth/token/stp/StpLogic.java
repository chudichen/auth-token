package com.chudichen.auth.token.stp;

import com.chudichen.auth.token.AuthTokenManager;
import com.chudichen.auth.token.config.AuthTokenConfig;
import com.chudichen.auth.token.util.AuthTokenInsideUtil;
import com.chudichen.auth.token.util.SpringMvcUtil;
import org.springframework.util.StringUtils;

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
        AuthTokenConfig config = AuthTokenManager.getConfig();
        String keyTokenName = getKeyTokenName();

        // 2. 尝试从request里获取
        if (request.getAttribute(AuthTokenInsideUtil.JUST_CREATE_SAVE_KEY) != null) {
            return String.valueOf(request.getAttribute(AuthTokenInsideUtil.JUST_CREATE_SAVE_KEY));
        }

        // 3. 尝试从请求体里面读取
        if (config.getReadFromBody()) {
            String tokenValue = request.getParameter(keyTokenName);
            if (tokenValue != null) {
                return tokenValue;
            }
        }

        // 4. 尝试从header里取
        if (config.getReadFromHead()) {
            String tokenValue = request.getHeader(keyTokenName);
            if (tokenValue != null) {
                return tokenValue;
            }
        }

        // 5. 都获取不到就返回空
        return null;
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

}
