package com.heima.utils.thread;

import com.heima.model.wemedia.pojos.WmUser;

/**
 * @PROJECT_NAME: heima-leadnews
 * @PACKAGE_NAME: com.heima.utils.thread
 * @CLASS_NAME: WmThreadLocalUtil
 * @USER: hongyaoyao
 * @DATETIME: 2023/8/20 21:54
 * @Emial: 1299694047@qq.com
 */
public class WmThreadLocalUtil {

    private final static ThreadLocal<WmUser> WM_USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程中
    public static void setUser(WmUser wmUser) {
        WM_USER_THREAD_LOCAL.set(wmUser);
    }

    // 从线程中获取
    public static WmUser getUser(){
        return WM_USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void clear(){
        WM_USER_THREAD_LOCAL.remove();
    }
}
