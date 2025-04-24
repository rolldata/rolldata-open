package com.rolldata.core.task.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:  CollectionPlanThreadFactory
 * @Description:  CollectionPlanThreadFactory
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2024-2-26
 * @version:V1.0
 */
public class CollectionPlanThreadFactory implements ThreadFactory {

    private static final Logger log = LogManager.getLogger(CollectionPlanThreadFactory.class);

    private final String namePrefix;

    private final AtomicInteger nextId = new AtomicInteger(1);

    /**
     * 定义线程组名称，在 jstack 问题排查时，非常有帮助
     *
     * @param whatFeaturOfGroup
     */
    public CollectionPlanThreadFactory(String whatFeaturOfGroup) {
        namePrefix = "From CollectionPlanThreadFactory's " + whatFeaturOfGroup + "-Worker-";
    }

    /**
     * Constructs a new {@code Thread}.  Implementations may also initialize priority, name, daemon status, {@code
     * ThreadGroup}, etc.
     *
     * @param r a runnable to be executed by new thread instance
     * @return constructed thread, or {@code null} if the request to create a thread is rejected
     */
    @Override
    public Thread newThread(Runnable task) {

        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(null, task, name, 0);
        log.info("荣培分配线程名称:{}", thread.getName());
        return thread;
    }
}
