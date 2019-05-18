package com.itheima.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author LiYuan
 * Created on 2018/10/28.
 */
@Entity
public class User {

    /**
     * GenerationType:
     *  TABLE：使用一个特定的数据库表格来保存主键。
     *  SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
     *  IDENTITY：主键由数据库自动生成（主要是自动增长型）。
     *  AUTO：主键由程序控制。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", name='" + name + '\'' + '}';
    }
}
