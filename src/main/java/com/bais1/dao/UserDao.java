package com.bais1.dao;

import com.bais1.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 通过用户名查找用户
     * @param name
     * @return
     */
    User findByUserAccount(String name);

    /**
     * 通过用户名和密码查询用户
     * @param userAccount
     * @param password
     * @return
     */
    User findByUserAccountAndPassword(String userAccount, String password);

    /**
     * 保存用户
     * @param user
     */
    boolean save(User user);

    /**
     * 激活用户
     * @param user
     */
    boolean enable(User user);

    /**
     * 通过Code找到用户
     * @param code
     * @return
     */
    User findByCode(String code);

    String getNameByAccount(String account);

    List<User> getAll();

    boolean set(User user);

    List<User> getEmployees();

    boolean delete(String account);
}
