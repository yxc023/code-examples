package com.yangxiaochen.examples.groovy;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.ArrayList;

/**
 * @author yangxiaochen
 * @date 2017/6/28 10:50
 */
public abstract class ScriptResolver extends Script {
    protected ArrayList<Integer> list;

    public ScriptResolver() {
        super();
        list = new ArrayList<>();
        list.add(1);
        list.add(100);
    }

    public ScriptResolver(Binding binding) {
        super(binding);
        list = new ArrayList<>();
        list.add(1);
        list.add(100);
    }
}
