package com.yangxiaochen.examples.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author yangxiaochen
 * @date 16/5/11 上午12:52
 */
public class Main {

    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.trace("start...");
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.error("a error {} in {} use {}", e.getMessage(), t.getName(), logger.getClass());
            }
        });
        logger.trace("end...");


    }
}
