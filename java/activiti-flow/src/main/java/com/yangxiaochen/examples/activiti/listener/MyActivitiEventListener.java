package com.yangxiaochen.examples.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author yangxiaochen
 * @date 16/4/9 下午5:02
 */
public class MyActivitiEventListener implements ActivitiEventListener {
    private static Logger log = LogManager.getLogger(MyActivitiEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                System.out.println("A job well done!");
                break;

            case JOB_EXECUTION_FAILURE:
                System.out.println("A job has failed...");
                break;

            default:
                log.info("Event received: {}", event.getType());
        }

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
