package com.chudichen.auth.token.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 创建AuthToken的工厂类
 *
 * @author Michael Chu
 * @since 2020-09-19 15:58
 */
public class AuthTokenConfigFactory {

    /** 默认的配置文件地址 */
    private static final String CONFIG_PATH = "auth-token.properties";

    /**
     * 根据指定路径获取配置信息
     *
     * @return {@link AuthTokenConfig} 配置对象
     */
    public static AuthTokenConfig createConfig() {
        Map<String, String> map = readPropToMap();
        if (map == null) {
            throw new RuntimeException("找不到配置文件：" + CONFIG_PATH, null);
        }
        return (AuthTokenConfig) initPropByMap(map, new AuthTokenConfig());
    }

    /**
     * 将指定路径的properties配置文件读取到Map中
     *
     * @return 一个Map
     */
    private static Map<String, String> readPropToMap() {
        Map<String, String> map = new HashMap<>(8);
        try {
            InputStream is = AuthTokenConfigFactory.class.getClassLoader().getResourceAsStream(AuthTokenConfigFactory.CONFIG_PATH);
            if (is == null) {
                return null;
            }
            Properties prop = new Properties();
            prop.load(is);
            for (String key : prop.stringPropertyNames()) {
                map.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("配置文件(" + AuthTokenConfigFactory.CONFIG_PATH + ")加载失败", e);
        }
        return map;
    }

    /**
     * 将Map的值映射到Model上
     *
     * @param map 属性集合
     * @param obj 对象，或类型
     * @return 返回实例化后的对象
     */
    private static Object initPropByMap(Map<String, String> map, Object obj) {
        if (map == null) {
            map = new HashMap<>(8);
        }

        // 1. 取出类型
        Class<?> cs = null;
        if (obj instanceof Class) {
            cs = (Class<?>) obj;
            obj = null;
        } else {
            cs = obj.getClass();
        }

        // 2. 遍历类型属性，反射赋值
        for (Field field : cs.getDeclaredFields()) {
            String value = map.get(field.getName());
            if (value == null) {
                continue;
            }
            try {
                // 转换值类型
                Object valueConvert = getObjectByClass(value, field.getType());
                field.setAccessible(true);
                field.set(obj, valueConvert);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("属性赋值出错：" + field.getName(), e);
            }
        }
        return obj;
    }

    private static <T> T getObjectByClass(String str, Class<T> cs) {
        Object value;
        if(str == null){
            value = null;
        }else if (cs.equals(String.class)) {
            value = str;
        } else if (cs.equals(int.class)||cs.equals(Integer.class)) {
            value = new Integer(str);
        } else if (cs.equals(long.class)||cs.equals(Long.class)) {
            value = new Long(str);
        } else if (cs.equals(short.class)||cs.equals(Short.class)) {
            value = new Short(str);
        } else if (cs.equals(float.class)||cs.equals(Float.class)) {
            value = new Float(str);
        } else if (cs.equals(double.class)||cs.equals(Double.class)) {
            value = new Double(str);
        } else if (cs.equals(boolean.class)||cs.equals(Boolean.class)) {
            value = Boolean.valueOf(str);
        }else{
            throw new RuntimeException("未能将值：" + str + "，转换类型为：" + cs, null);
        }
        //noinspection unchecked
        return (T)value;
    }
}
