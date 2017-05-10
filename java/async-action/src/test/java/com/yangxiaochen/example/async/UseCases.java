package com.yangxiaochen.example.async;

import com.yangxiaochen.example.async.engine.MemoryEngine;
import org.junit.Test;

/**
 * @author yangxiaochen
 * @date 2017/5/2 22:41
 */
public class UseCases {


    public static class PrintAction implements AsyncAction {
        private String log;

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }


        @Override
        public boolean doAction() throws Exception {
            System.out.println(log);
            return false;
        }
    }

    @Test
    public void usecase() {
        Engine engine = new MemoryEngine();
        engine.start();


        PrintAction event = new PrintAction();
        event.setLog("hahaha");
        engine.submitAction(event);
    }
}
