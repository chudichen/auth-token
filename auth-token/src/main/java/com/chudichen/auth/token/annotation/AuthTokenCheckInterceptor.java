package com.chudichen.auth.token.annotation;

import com.chudichen.auth.token.stp.StpLogic;
import com.chudichen.auth.token.stp.StpUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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
        this(StpUtil.getStpLogic());
    }

    public AuthTokenCheckInterceptor(StpLogic stpLogic) {
        this.stpLogic = stpLogic;
    }

    /**
     * 每次请求前触发校验
     *
     * @param request request
     * @param response response
     * @param handler 处理器
     * @return 是否通过{@code true}表示通过
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取处理method
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;

        // 验证登录
        if (method.hasMethodAnnotation(AuthCheckLogin.class) || method.getBeanType().isAnnotationPresent(AuthCheckLogin.class)) {
            stpLogic.checkLogin();
        }

        // 获取权限注解
        AuthTokenCheckPermission annotation = method.getMethodAnnotation(AuthTokenCheckPermission.class);
        if (annotation == null) {
            annotation = method.getBeanType().getAnnotation(AuthTokenCheckPermission.class);
            if (annotation == null) {
                return true;
            }
        }

        // 开始验证权限
        Object[] codeArray = concatAbc(annotation.value(), annotation.valueInt(), annotation.valueLong());
        return annotation.isAnd() ?
                // 验证AND条件
                stpLogic.checkPermissionAnd(codeArray) :
                // 验证OR条件
                stpLogic.checkPermissionOr(codeArray);
    }

    /**
     * 合并三个数组
     *
     * @param a 元素a
     * @param b 元素b
     * @param c 元素c
     * @return 合并后的值
     */
    private static Object[] concatAbc(Object[] a, int[] b, long[] c) {
        Object[] bObj = Arrays.stream(b).boxed().toArray(Object[]::new);
        Object[] cObj = Arrays.stream(c).boxed().toArray(Object[]::new);

        // 依次赋值
        Object[] d = new Object[a.length + b.length + c.length];
        System.arraycopy(a, 0, d, 0, a.length);
        System.arraycopy(bObj, 0, d, a.length, b.length);
        System.arraycopy(cObj, 0, d, a.length + b.length, c.length);
        return d;
    }
}
