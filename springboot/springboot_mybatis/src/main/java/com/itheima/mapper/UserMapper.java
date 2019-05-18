package com.itheima.mapper;

import com.itheima.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liyuan
 * @date 2018/10/28.
 */
@Mapper
public interface UserMapper {
    public List<User> queryUserList();
}
