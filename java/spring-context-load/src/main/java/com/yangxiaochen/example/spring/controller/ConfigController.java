package com.yangxiaochen.example.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangxiaochen
 * @date 2017/5/30 01:22
 */
@Component
public class ConfigController {
    @Autowired
    ContextController contextController;
}
