package com.rolldata.core.common.pojo;

import com.rolldata.core.util.CacheUtils;
import com.rolldata.web.system.util.UserUtils;

import java.io.Serializable;

/**
 * 限制下载对象信息
 * 新增报表组控制[2025-2-24]
 *
 * @Title: Restrict
 * @Description: Restrict
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2023-04-17
 * @version: V1.0
 */
public class Restrict implements Serializable {

    private static final long serialVersionUID = 3766927102734674492L;

    /**
     * 一分钟
     */
    public static final int ONE_MINUTES = 1 * 60;

    /**
     * 十分钟
     */
    //public static final int TEN_MINUTES = 10 * 60;
    public static final String RUN = "RUN";
    public static final String STOP = "STOP";
    public static final String DOWNLOAD_RESTRICT = "DOWNLOAD_RESTRICT";

    public Restrict(long expireLayout, String fileName) {

        this.expireLayout = expireLayout;
        Thread thread = Thread.currentThread();
        this.threadId = thread.getId();
        this.threadName = thread.getName();
        this.currentTimeMillis = System.currentTimeMillis();
        this.currentThread = Thread.currentThread();

        // 计算结束
        this.expireTimeMillis = currentTimeMillis + 1000 * expireLayout;
        this.fileName = fileName;
    }

    /**
     * 线程 ID
     */
    private final long threadId;

    /**
     * 线程名称
     */
    private final String threadName;

    /**
     * 放入缓存的时间
     */
    private final long currentTimeMillis;

    /**
     * 过期时间(超时结束程序)
     */
    private final long expireTimeMillis;

    /**
     * 超时秒数布局
     */
    private final long expireLayout;

    private final Thread currentThread;

    /**
     * 导出的名字
     */
    private final String fileName;

    /**
     * 执行状态
     */
    private String state = RUN;

    /**
     * 获取放入缓存的 key
     *
     * @return 键值
     */
    public static String getCacheKey() {

        return DOWNLOAD_RESTRICT + UserUtils.getUser().getId();
    }


    /**
     * 下载完后,改变状态
     */
    public static void stop() {

        Restrict restrict = (Restrict) CacheUtils.get(getCacheKey());
        if (null == restrict) {
            return;
        }
        restrict.setState(STOP);
    }

    /**
     * 检查并设置缓存
     *
     * @param fileName 文件名
     * @throws Exception 异常
     */
    public static void checkPutCache(String fileName) throws Exception {

        Restrict restrict = (Restrict) CacheUtils.get(getCacheKey());
        if (null != restrict &&
                Restrict.RUN.equals(restrict.getState()) &&
                restrict.getExpireTimeMillis() > System.currentTimeMillis()) {
            throw new RuntimeException("违规操作,导出[" + restrict.getFileName() + "]正在执行");
        }
        restrict = null;
        CacheUtils.put(getCacheKey(), new Restrict(Restrict.ONE_MINUTES, fileName));
    }

    public static Restrict getCacheData() {

        return (Restrict) CacheUtils.get(getCacheKey());
    }

    public long getExpireTimeMillis() {
        return expireTimeMillis;
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    public String getFileName() {
        return fileName;
    }

    public String getState() {
        return state;
    }

    public Restrict setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String toString() {

        return "Restrict{" +
            "threadId=" + threadId +
            ", threadName='" + threadName + '\'' +
            ", currentTimeMillis=" + currentTimeMillis +
            ", expireTimeMillis=" + expireTimeMillis +
            ", expireLayout=" + expireLayout +
            ", currentThread=" + currentThread +
            ", fileName='" + fileName + '\'' +
            ", state='" + state + '\'' +
            '}';
    }
}
