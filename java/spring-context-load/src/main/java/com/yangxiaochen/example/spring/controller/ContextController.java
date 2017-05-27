package com.yangxiaochen.example.spring.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangxiaochen
 * @date 2017/5/27 12:17
 */
@Controller
public class ContextController implements ApplicationContextAware {

    ApplicationContext context;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", "hello world");
        mv.addObject("context", context);
        String[] names = context.getBeanDefinitionNames();
        return mv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
