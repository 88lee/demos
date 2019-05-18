package com.itheima;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.domain.User;
import com.itheima.repository.UserRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author LiYuan Created on 2018/10/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootJpaApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() throws JsonProcessingException {
        //1：从redis中获取数据 数据的形式为json
        String userListJson = redisTemplate.boundValueOps("user.findAll").get();
        //2：判断热第三种是否存在数据
        if (StringUtils.isEmpty(userListJson)) {
            //3：不存在数据 从数据库查询
            System.out.println("未从redis缓存中查询到数据");
            List<User> all = userRepository.findAll();
            if (CollectionUtils.isEmpty(all)) {
                System.out.println("未从数据库中查询到数据");
            } else {
                //4：将查询出的数据存到缓存中
                System.out.println("从数据库中查询到数据：" + all.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                userListJson = objectMapper.writeValueAsString(all);
                redisTemplate.boundValueOps("user.findAll").set(userListJson);
                System.out.println("写入缓存user.findAll：" + userListJson);
            }
        } else {
            System.out.println("从redis缓存中查询到数据user.findAll" + userListJson);
        }
    }
}
