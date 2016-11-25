package com.yangxiaochen.examples.log;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

/**
 * @author yangxiaochen
 * @date 2016/11/7 18:35
 */
public class ServiceException extends RuntimeException {

    private long code;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

//    public ServiceException(String message, Object... params) {
//        super(ParameterizedMessage.format(message, params));
//    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }


//    public ServiceException(Throwable cause, String message, Object... params) {
//        super(ParameterizedMessage.format(message, params), cause);
//    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     *
     * @param message
     * @param paramsAndException
     * @return
     */
    public static ServiceException create(String message, Object... paramsAndException) {
        Message msg = new ParameterizedMessage(message, paramsAndException);
        return new ServiceException(msg.getFormattedMessage(), msg.getThrowable());
    }


}
