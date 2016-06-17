package com.yangxiaochen.examples.bean.form.annotations;

import com.yangxiaochen.examples.bean.form.volidators.ConsistentDateParameterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yangxiaochen
 * @date 16/6/16 下午3:38
 */
@Constraint(validatedBy = ConsistentDateParameterValidator.class)
@Target({ METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ConsistentDateParameters {
    String message() default "{org.hibernate.validator.referenceguide.chapter06." +
            "crossparameter.ConsistentDateParameters.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
