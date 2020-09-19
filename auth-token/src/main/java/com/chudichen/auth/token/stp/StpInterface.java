package com.chudichen.auth.token.stp;

import java.util.List;

/**
 * 开放式鉴权验证接口，方便重写
 *
 * @author Michael Chu
 * @since 2020-09-19 15:14
 */
public interface StpInterface<T> {

    /**
     * 返回指定login_id所有拥有的权限码集合
     *
     * @param loginId 账号id
     * @param loginKey 具体的stp表示标示
     * @return 具体的权限集合
     */
    List<T> getPermissionCodeList(Object loginId, String loginKey);
}
