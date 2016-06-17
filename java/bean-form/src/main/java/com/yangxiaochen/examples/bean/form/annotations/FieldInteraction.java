package com.yangxiaochen.examples.bean.form.annotations;

import com.yangxiaochen.examples.bean.form.volidators.FieldInteractionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yangxiaochen
 * @date 16/6/16 下午6:34
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FieldInteractionValidator.class})
public @interface FieldInteraction {

    String expressionCondition();
    String expressionResult();

    String message() default "When \\{{expressionCondition}\\} is true, \\{{expressionResult}\\} must be true";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        FieldInteraction[] value();
    }
}
