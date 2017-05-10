package com.yangxiaochen.example.async;

/**
 * @author yangxiaochen
 * @date 2017/4/11 01:05
 */
public interface Engine {
    void start();
    void stop();

    void submitAction(AsyncAction retryAble);


}
