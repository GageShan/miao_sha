package com.gageshan.miaosha.model.vo;

import com.gageshan.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Create by gageshan on 2020/5/14 22:16
 */
@Data
public class LoginVO {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
