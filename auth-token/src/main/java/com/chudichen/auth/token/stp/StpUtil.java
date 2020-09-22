package com.chudichen.auth.token.stp;

/**
 * 一个默认实现
 *
 * @author chudichen
 * @since 2020-09-21
 */
public class StpUtil {

    /** 底层的StpLogic对象 */
    private static StpLogic stpLogic = new StpLogic("login");

    /**
     * 获取StpLogic
     *
     * @return StpLogic
     */
    public static StpLogic getStpLogic() {
        return stpLogic;
    }

    // =================== 获取token 相关 ===================
}
