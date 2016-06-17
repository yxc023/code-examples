package com.yangxiaochen.examples.bean.form.volidators;

import com.yangxiaochen.examples.bean.form.annotations.Required;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yangxiaochen
 * @date 16/6/16 下午12:28
 */
public class RequiredValidator implements ConstraintValidator<Required, Object> {

    @Override
    public void initialize(Required constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            String s = (String) value;
            if (StringUtils.isEmpty(s)) {
                return false;
            }
        }

        return true;
    }
}
