package com.yangxiaochen.examples.bean.form;

import com.yangxiaochen.examples.bean.form.forms.ReportForm;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author yangxiaochen
 * @date 16/6/13 下午5:40
 */
@Log4j2
public class Volidator {

    public static void main(String[] args) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ReportForm reportForm = new ReportForm();
        reportForm.setName(null);
        try {
            Set<ConstraintViolation<ReportForm>> constraintViolations =
                    validator.validate(reportForm);

            reportForm.liandong("xxxx", 123);
            log.info(constraintViolations);
            for (ConstraintViolation<ReportForm> constraintViolation : constraintViolations) {
                log.info(constraintViolation.getMessage());
            }
            

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
