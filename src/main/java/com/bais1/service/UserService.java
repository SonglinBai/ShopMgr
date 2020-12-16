package com.bais1.service;

import com.bais1.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean active(String code);

    boolean disable(String account);

    boolean enable(String account);

    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    List<User> getAll();

    boolean set(User user);

    List<User> getEmployees();

    boolean remove(String account);

    boolean isUserAccountExist(User user);
}
