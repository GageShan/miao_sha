package com.gageshan.miaosha.validator;

import com.gageshan.miaosha.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Create by gageshan on 2020/5/14 22:22
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required) {
            return ValidatorUtil.isMobile(s);
        } else {
            if(StringUtils.isBlank(s)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
