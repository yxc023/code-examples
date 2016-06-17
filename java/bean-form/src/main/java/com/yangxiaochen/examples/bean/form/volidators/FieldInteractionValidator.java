package com.yangxiaochen.examples.bean.form.volidators;

import com.yangxiaochen.examples.bean.form.annotations.FieldInteraction;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yangxiaochen
 * @date 16/6/16 下午6:36
 */
public class FieldInteractionValidator implements ConstraintValidator<FieldInteraction, Object> {
    private FieldInteraction fieldInteraction;
    @Override
    public void initialize(FieldInteraction constraintAnnotation) {
        this.fieldInteraction = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ExpressionParser parser = new SpelExpressionParser();

        boolean condition = (Boolean) parser.parseExpression(fieldInteraction.expressionCondition()).getValue(value);
        if (condition) {
            boolean result = (Boolean) parser.parseExpression(fieldInteraction.expressionResult()).getValue(value);
            if (result) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
