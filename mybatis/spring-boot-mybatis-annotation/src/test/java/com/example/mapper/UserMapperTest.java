package com.example.mapper;

import com.example.enums.UserSexEnum;
import com.example.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() throws Exception {
        userMapper.insert(new User("aa1", "a123456", UserSexEnum.MAN));
        userMapper.insert(new User("bb1", "b123456", UserSexEnum.WOMAN));
        userMapper.insert(new User("cc1", "b123456", UserSexEnum.WOMAN));

        Assert.assertEquals(3, userMapper.getAll().size());
    }

    @Test
    public void testQuery() throws Exception {
        List<User> users = userMapper.getAll();
        System.out.println(users.toString());
    }


    @Test
    public void testUpdate() throws Exception {
        User user = userMapper.getOne(40l);
        Assert.assertNotNull(user);
        System.out.println(user.toString());
        user.setNickName("neo");
        userMapper.update(user);
        Assert.assertEquals("neo", userMapper.getOne(40l).getNickName());
    }
}