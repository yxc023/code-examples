package com.yangxiaochen.examples.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author yangxiaochen
 * @date 2017/7/20 23:46
 */
public class AQSImpl extends AbstractQueuedSynchronizer {


    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }

    @Override
    protected boolean isHeldExclusively() {
        return super.isHeldExclusively();
    }

    public static void main(String[] args) {

    }
}
