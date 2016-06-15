package com.yangxiaochen.examples.bean.form.forms;

import com.yangxiaochen.examples.bean.form.annotations.Required;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(min = 2, max = 14)
    private String tel;
    private int gender;

}
