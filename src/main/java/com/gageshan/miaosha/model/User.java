package com.gageshan.miaosha.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/14 22:17
 */
@Data
public class User {
    private long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerTime;
    private Date lastLoginDate;
    private int loginCount;
}
