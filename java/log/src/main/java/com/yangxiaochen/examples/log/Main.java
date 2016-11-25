package com.yangxiaochen.examples.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;


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

        try {
            Object a = null;
            a.toString();
        } catch (Exception e) {
            logger.error("空指针", e);
            logger.error(e.getMessage(), e);
            logger.error(e);
            logger.error(e.getMessage(), e);
            logger.error(MarkerManager.getMarker("mark"),"something",e);
            logger.error("调用UC的getEhrUserByUserId方法出错，userId is {}", 1, e);
        }

        try {
            throw ServiceException.create("haha");
        } catch (Exception e) {
            logger.error("出错了", e);
        }

        logger.trace("end...");


    }
}
