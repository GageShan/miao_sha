package com.gageshan.miaosha.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by gageshan on 2020/5/14 22:27
 */
public class ValidatorUtil {
    private  static final Pattern mobile_patten = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile) {
        if(StringUtils.isBlank(mobile)) {
            return false;
        }
        Matcher matcher = mobile_patten.matcher(mobile);
        return matcher.matches();
    }
}
