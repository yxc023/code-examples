package com.yangxiaochen.examples.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangxiaochen
 * @date 16/5/11 上午12:52
 */
public class Main {

    public static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.error("a error {} in {}", e.getMessage(), t.getName());
            }
        });

        assert (1 + 1 == 3);
    }
}
