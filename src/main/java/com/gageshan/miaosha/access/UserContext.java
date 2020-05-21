package com.gageshan.miaosha.access;

import com.gageshan.miaosha.model.User;

/**
 * Create by gageshan on 2020/5/21 21:14
 */
public class UserContext {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        userHolder.set(user);
    }
    public static User getUser() {
        return userHolder.get();
    }
}
