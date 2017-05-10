package com.yangxiaochen.example.async.engine;

import com.yangxiaochen.example.async.AsyncAction;
import com.yangxiaochen.example.async.exception.AsyncActionExecuteException;

/**
 * @author yangxiaochen
 * @date 2017/4/11 01:13
 */
public class MemoryEngine extends AbstractEngine {
    @Override
    public void start() {
//        super.start();
    }

    @Override
    public void stop() {
//        super.stop();
    }

    @Override
    public void submitAction(AsyncAction retryAble) {

        try {
            retryAble.doAction();
        } catch (Exception e) {
            throw new AsyncActionExecuteException();
        }
    }
}
