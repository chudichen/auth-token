package com.chudichen.auth.token.annotation;

/**
 * 标注一个路由方法，当前会话必须具有指定权限才可以通过
 *
 * @author chudichen
 * @since 2020-09-22
 */
public @interface AuthTokenCheckPermission {

    /**
     * 返回一个权限码的数据，String类型
     *
     * @return 权限码数组
     */
    String[] value() default {};

    /**
     * 权限码数组，int类型
     *
     * @return 权限码数组
     */
    int[] valueInt() default {};

    /**
     * 权限码数组，long类型
     *
     * @return 权限码
     */
    long[] valueLong() default {};

    /**
     * 是否属于and型验证，true=必须全部具有，false=只要具有一个就可以通过
     *
     * @return and
     */
    boolean isAnd() default true;
}
