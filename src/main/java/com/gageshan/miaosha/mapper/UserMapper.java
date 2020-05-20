package com.gageshan.miaosha.mapper;

import com.gageshan.miaosha.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Create by gageshan on 2020/5/15 16:44
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{mobile}")
    User getById(long mobile);

    @Delete("delete from user where id != #{mobile}")
    void delectUser(long mobile);
    @Select("select count(*) from user")
    int countUser();
}
