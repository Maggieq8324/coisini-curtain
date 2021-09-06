package com.coisini.curtain.core.common;

import com.coisini.curtain.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description User 缓存
 * @author coisini
 * @date Aug 20, 2021
 * @Version 1.0
 */
public class LocalUser {

    /**
     * 线程安全缓存用户
     */
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * 缓存当前用户
     * @param user
     * @param scope
     */
    public static void set(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.threadLocal.set(map);
    }

    /**
     * 清除用户缓存
     */
    public static void clear() {
        LocalUser.threadLocal.remove();
    }

    /**
     * 获取当前用户
     * @return
     */
    public static User getUser () {
        Map<String, Object> map = LocalUser.threadLocal.get();
        User user = (User) map.get("user");
        return user;
    }

    /**
     * 获取权限等级
     * @return
     */
    public static Integer getScope () {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (Integer) map.get("scope");
    }

}
