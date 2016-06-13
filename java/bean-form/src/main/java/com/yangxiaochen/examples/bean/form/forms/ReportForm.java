package com.yangxiaochen.examples.bean.form.forms;

import com.yangxiaochen.examples.bean.form.annotations.Required;
import lombok.Data;
import lombok.ToString;

/**
 * @author yangxiaochen
 * @date 16/6/13 下午4:49
 */
@ToString
@Data
public class ReportForm {
    @Required
    private String name;
    private int gender;

}
