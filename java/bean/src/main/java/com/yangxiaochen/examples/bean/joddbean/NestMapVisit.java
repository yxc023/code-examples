package com.yangxiaochen.examples.bean.joddbean;


import jodd.bean.BeanUtil;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangxiaochen
 * @date 16/6/7 下午10:59
 */
@Log4j2
public class NestMapVisit {
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap();
        Map<String,Object> map2 = new HashMap();
        List<Object> list = new ArrayList<>();
        Map<String,Object> map3 = new HashMap();

        map3.put("msg","hello world!");
        list.add(map3);
        list.add(map3);
        map2.put("msgList",list);
        map.put("map2",map2);

        Object value = BeanUtil.pojo.getProperty(map,"[map2.msgList[1].msg]");
        log.info(value);

        value = BeanUtil.silent.getProperty(map,"map2.msgList[2].msg");
        log.info(value);

        value = BeanUtil.declaredSilent.getProperty(map,"map2.msgList[1].msg2");
        log.info(value);

    }
}
