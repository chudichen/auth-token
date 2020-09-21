package com.chudichen.auth.token.util;

/**
 * auth-token工具类
 *
 * @author chudichen
 * @since 2020-09-21
 */
public class AuthTokenInsideUtil {

    /** auth-token版本号 */
    private static final String VERSION_NO = "v1.0.0";

    /** github的地址 */
    public static final String GITHUB_URL = "https://github.com/chudichen/auth-token";

    /** 如果token为本次请求新创建的，则一次字符串为key存储在当前request中 JUST_CREATE_SAVE_KEY */
    public static final String JUST_CREATE_SAVE_KEY = "JUST_CREATE_SAVE_KEY_";

    /**
     * 打印auth-token信息
     *
     */
    public static void printAuthToken() {
        String str =
                "____ _  _ ___ _  _     ___ ____ _  _ ____ _  _ \r\n" +
                "|__| |  |  |  |__|  __  |  |  | |_/  |___ |\\ | \r\n" +
                "|  | |__|  |  |  |      |  |__| | \\_ |___ | \\| \r\n" +
                "sa-token：" + VERSION_NO + "         \r\n" +
                "GitHub：" + GITHUB_URL; // + "\r\n";
        System.out.println(str);
    }
}
