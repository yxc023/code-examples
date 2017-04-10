package com.yangxiaochen.example.retry;

import java.io.Serializable;

/**
 * @author yangxiaochen
 * @date 2017/4/11 01:07
 */
public interface RetryAble {

    void retry(Serializable param, int times);
}
