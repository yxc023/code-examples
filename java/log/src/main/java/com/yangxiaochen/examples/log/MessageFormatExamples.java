package com.yangxiaochen.examples.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author yangxiaochen
 * @date 16/5/22 下午1:49
 */
public class MessageFormatExamples {
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("this is a {} message", "format");

        // use log4j2's ParameterizedMessage
        String msg = ParameterizedMessage.format("this is a {} message", new Object[]{"format"});
        logger.info(msg);

        // use slf4j's MessageFormatter
        msg = MessageFormatter.arrayFormat("this is a {} message", new Object[]{"format"}).getMessage();
        logger.info(msg);
    }
}
