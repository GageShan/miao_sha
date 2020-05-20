package com.gageshan.miaosha.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Create by gageshan on 2020/5/14 16:27
 */
@Slf4j
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String src = "" + salt.charAt(1) + salt.charAt(4) + salt.charAt(6) + inputPass + salt.charAt(0);
        return md5(src);
    }
    public static String formPassToDBPass(String formPass, String userSalt) {
        String src = userSalt + formPass;
//        log.info(userSalt + " : " + formPass);
        return md5(src);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        return formPassToDBPass(formPass,saltDB);
    }
}
