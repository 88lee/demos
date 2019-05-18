package com.itheima.repository;

import com.itheima.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LiYuan
 * Created on 2018/10/28.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findAll();
}
