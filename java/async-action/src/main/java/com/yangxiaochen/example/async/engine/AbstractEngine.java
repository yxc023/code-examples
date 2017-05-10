package com.yangxiaochen.example.async.engine;

import com.yangxiaochen.example.async.Engine;
import com.yangxiaochen.example.async.AsyncAction;

/**
 * @author yangxiaochen
 * @date 2017/5/2 22:40
 */
public class AbstractEngine implements Engine {



    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void saveAction() {

    }

    public void getActions() {

    }

    @Override
    public void submitAction(AsyncAction action) {


//        retryAble.doAction();
        throw new UnsupportedOperationException();
    }
}
