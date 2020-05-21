package com.gageshan.miaosha.access;

import com.alibaba.fastjson.JSON;
import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.redis.AccessKey;
import com.gageshan.miaosha.redis.RedisService;
import com.gageshan.miaosha.result.CodeMsg;
import com.gageshan.miaosha.result.Result;
import com.gageshan.miaosha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Handler;

/**
 * Create by gageshan on 2020/5/21 21:08
 */
@Service
@Slf4j
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod)handler;

            User user = getUser(request, response);
            UserContext.setUser(user);

            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);

            if(accessLimit == null) {
                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin) {
                if(user == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            }
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer integer = redisService.get(ak, key, Integer.class);
            if(integer == null) {
                redisService.set(ak,key,1);
            } else if(integer < maxCount) {
                redisService.incr(ak,key);
            } else {
                render(response,CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }

        }

        return true;
    }

    private void render(HttpServletResponse response, CodeMsg sessionError) throws Exception{
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String toJSONString = JSON.toJSONString(Result.error(sessionError));
        out.write(toJSONString.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    public User getUser(HttpServletRequest request, HttpServletResponse response) {

        String parameterToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,UserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isBlank(parameterToken) && StringUtils.isBlank(cookieToken)) {
            return null;
        }

        String token = StringUtils.isBlank(parameterToken) ? cookieToken : parameterToken;
        log.info(token);
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
