package com.chudichen.auth.token.stp;

import java.util.Collections;
import java.util.List;

/**
 * 鉴权接口的默认实现
 *
 * @author Michael Chu
 * @since 2020-09-19 15:18
 */
public class StpInterfaceDefaultImpl implements StpInterface<Object> {

    @Override
    public List<Object> getPermissionCodeList(Object loginId, String loginKey) {
        return Collections.emptyList();
    }
}
