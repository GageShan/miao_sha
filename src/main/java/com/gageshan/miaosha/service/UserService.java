package com.gageshan.miaosha.service;

import com.gageshan.miaosha.exception.GlobalException;
import com.gageshan.miaosha.mapper.UserMapper;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.model.vo.LoginVO;
import com.gageshan.miaosha.redis.RedisService;
import com.gageshan.miaosha.redis.UserKey;
import com.gageshan.miaosha.result.CodeMsg;
import com.gageshan.miaosha.utils.MD5Util;
import com.gageshan.miaosha.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * Create by gageshan on 2020/5/15 1:33
 */
@Service
@Slf4j
public class UserService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;

    public static final String COOKIE_NAME_TOKEN = "token";


    public String login(HttpServletResponse response, LoginVO loginVO) {
        if(loginVO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        User user = getById(Long.parseLong(mobile));

        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String saltDB = user.getSalt();
        String dbPassword = user.getPassword();
        String cacl = MD5Util.formPassToDBPass(password,saltDB);
//        log.info("caclPassword:" + cacl);
        if(!cacl.equals(dbPassword)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.getToken,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(UserKey.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private User getById(long mobile) {
        User user = redisService.get(UserKey.getById, mobile + "", User.class);

        if(user != null) {
            return user;
        }
        user = userMapper.getById(mobile);

        if(user != null) {
            redisService.set(UserKey.getById,mobile + "", user);
        }
        return user;
    }

    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isBlank(token)) {
            return null;
        }
        User user = redisService.get(UserKey.getToken, token, User.class);
        if(user != null) {
            addCookie(response,token,user);
        }
        return user;
    }
}
