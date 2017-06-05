package com.yangxiaochen.example.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author yangxiaochen
 * @date 2017/5/27 12:17
 */
@Controller
public class ContextController implements ApplicationContextAware {

    ConfigurableApplicationContext context;
    @Autowired
    ContextController contextController;


    @RequestMapping("/")
    public ModelAndView index() throws JsonProcessingException {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("message", "hello world");
        mv.addObject("context", context);
        ConfigurableListableBeanFactory factory = context.getBeanFactory();

        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        String[] names = factory.getBeanDefinitionNames();
        Map<Class, List<String>> map = new HashMap<>();
        for (String name : names) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            beanDefinition.getBeanClassName();
            beanDefinition.getRole();
            beanDefinition.getSource();
            beanDefinition.getClass();
            if (beanDefinition.getRole() != BeanDefinition.ROLE_INFRASTRUCTURE && beanDefinition.getSource() != null) {
                beanDefinitions.add(beanDefinition);
            } else {
                System.out.println(name);
            }

            List<String> subNames = map.get(beanDefinition.getClass());
            if (subNames == null) {
                subNames = new ArrayList<>();
                map.put(beanDefinition.getClass(), subNames);
            }
            subNames.add(name);
        }
        mv.addObject("beanDefinitions", beanDefinitions);
        return mv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = (ConfigurableApplicationContext) applicationContext;
    }

//    public void setContextController(ContextController contextController) {
//        this.contextController = contextController;
//    }
}
