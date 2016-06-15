package com.yangxiaochen.examples.bean.form.forms;

import com.yangxiaochen.examples.bean.form.annotations.Required;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author yangxiaochen
 * @date 16/6/13 下午4:49
 */
@ToString
@Data
public class ReportForm {
    @Required
    @NotNull
    private String name;
    private int gender;

}
