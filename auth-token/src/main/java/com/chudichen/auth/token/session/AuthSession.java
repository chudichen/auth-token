package com.chudichen.auth.token.session;

import com.chudichen.auth.token.AuthTokenManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * session会话
 *
 * @author chudichen
 * @since 2020-09-22
 */
public class AuthSession implements Serializable {

    private static final long serialVersionUID = -7740995968521842141L;

    /** 会话id */
    private String id;

    /** 当前会话创建时间 */
    private Long createTime;

    /** 当前会话键值对 */
    private Map<String, Object> dataMap;

    /**
     * 构建一个session对象
     *
     * @param id 会话id
     */
    public AuthSession(String id) {
        this.id = id;
        this.createTime = System.currentTimeMillis();
        this.dataMap = new HashMap<>(16);
    }

    /**
     * 获取会话id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 返回当前会话创建时间
     *
     * @return 时间戳
     */
    public long getCreateTime() {
        return createTime;
    }

    /**
     * 写入一个值
     *
     * @param key 名称
     * @param value 值
     */
    public void setAttribute(String key, Object value) {
        dataMap.put(key, value);
        update();
    }

    /**
     * 通过key取出对应的值
     *
     * @param key 名称
     * @return 值
     */
    public Object getAttribute(String key) {
        return dataMap.get(key);
    }

    /**
     * 取不到值使用默认值代替
     *
     * @param key key
     * @param defaultValue 默认值
     * @return 获取到的值
     */
    public Object getAttribute(String key, Object defaultValue) {
        return dataMap.getOrDefault(key, defaultValue);
    }

    /**
     * 删除一个值
     *
     * @param key 需要删除的key
     */
    public void clearAttribute(String key) {
        dataMap.remove(key);
        update();
    }

    /**
     * 是否包含指定key
     *
     * @param key 指定的key
     * @return 所有值的列表
     */
    public boolean containsAttribute(String key) {
        return dataMap.containsKey(key);
    }

    /**
     * 获取数据集(如果更新map里的值，注意调用session.update()防止数据过期)
     *
     * @return 所有的值
     */
    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    /**
     * 将这个session从持久库中更新一下
     */
    public void update() {
        AuthTokenManager.getDao().updateAuthSession(this);
    }
}
