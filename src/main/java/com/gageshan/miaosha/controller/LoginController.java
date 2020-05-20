package com.gageshan.miaosha.controller;

import com.gageshan.miaosha.model.vo.LoginVO;
import com.gageshan.miaosha.result.Result;
import com.gageshan.miaosha.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Create by gageshan on 2020/5/14 22:36
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response,@Valid LoginVO loginVO) {
        log.info(loginVO.toString());
        String token = userService.login(response,loginVO);
        return Result.success(token);
    }
}
