package com.gageshan.miaosha.config;

import com.gageshan.miaosha.model.User;
import com.gageshan.miaosha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by gageshan on 2020/5/15 18:34
 */
@Component
@Slf4j
public class UserResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;
    /**
     * 该方法返回值为true才调用 resolveArgument 方法
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    /**
     * 这个方法执行后，会给controller层注入参数
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String parameterToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,UserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isBlank(parameterToken) && StringUtils.isBlank(cookieToken)) {
            return null;
        }

        String token = StringUtils.isBlank(parameterToken) ? cookieToken : parameterToken;
//        log.info(token);
        User user = userService.getByToken(response, token);
//        log.info(user.toString());
        return user;
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
