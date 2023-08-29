package com.heima.utils.thread;

import com.heima.model.user.pojos.ApUser;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.utils.thread
 * @CLASS_NAME: AppThreadLocalUtil
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/25 18:28
 * @Emial: 1299694047@qq.com
 */
public class AppThreadLocalUtil {
    private final static ThreadLocal<ApUser> APP_USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程中
    public static void setUser(ApUser apUser) {
        APP_USER_THREAD_LOCAL.set(apUser);
    }

    // 从线程中获取
    public static ApUser getUser(){
        return APP_USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void clear(){
        APP_USER_THREAD_LOCAL.remove();
    }
}
