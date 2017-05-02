package com.yangxiaochen.example.retry;

import com.yangxiaochen.example.retry.engine.MemoryEngine;
import org.junit.Test;

/**
 * @author yangxiaochen
 * @date 2017/5/2 22:41
 */
public class UseCases {


    public static class PrintEvent implements RetryAble {
        private String log;

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        @Override
        public boolean retry() {
            System.out.println(log);
            return false;
        }
    }

    @Test
    public void usecase() {
        Engine engine = new MemoryEngine();
        engine.start();


        PrintEvent event = new PrintEvent();
        event.setLog("hahaha");
        engine.submitRetry(event);
    }
}
