package com.chudichen.auth.token.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring MVC 相关的工具类
 *
 * @author Michael Chu
 * @since 2020-09-19 15:26
 */
public interface SpringMvcUtil {

    /**
     * 获取当前会话的Request
     *
     * @return request
     */
    static HttpServletRequest getRequest() {
        // Spring MVC 提供的封装
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("当前环境非Java Web");
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前会话的Response
     *
     * @return request
     */
    static HttpServletResponse getResponse() {
        // Spring MVC 提供的封装
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("当前环境非Java Web");
        }
        return requestAttributes.getResponse();
    }

}
