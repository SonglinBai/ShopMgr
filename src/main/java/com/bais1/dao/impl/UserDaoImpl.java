package com.bais1.dao.impl;

import com.bais1.dao.UserDao;
import com.bais1.domain.Gender;
import com.bais1.domain.User;
import com.bais1.domain.UserRole;
import com.bais1.domain.UserStatus;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

// 用于操作数据库中的用户表
public class UserDaoImpl implements UserDao {
    //获取jdbcTemplate
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 通过账户名查找用户
    @Override
    public User findByUserAccount(String account) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tb_user where userAccount=?";
            //2.执行sql,并封装user
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), account);
        } catch (Exception e) {
            System.out.println("didn't find user by username"+account);;
        }

        return user;
    }

    // 通过账户名和密码查询用户信息
    @Override
    public User findByUserAccountAndPassword(String userAccount, String password) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tb_user where userAccount=? and passwd=?";
            //2.执行sql,并封装user
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userAccount, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(userAccount + "登录失败");
            return null;
        }
        System.out.println(user);
        return user;
    }

    // 新建用户
    @Override
    public boolean save(User user) {
        String userAccount = user.getUserAccount();
        String username = user.getUserName();
        String password = user.getPasswd();
        int age = user.getAge();
        String role = "EMPLOYEE";
        String gender = "MALE";
        String status = "ENABLE";
        if (user.getUserRole()!=null)
            role = user.getUserRole().equals(UserRole.ADMINISTRATOR)?"ADMINISTRATOR":"EMPLOYEE";
        if(user.getGender()!=null)
            gender = user.getGender().equals(Gender.MALE)?"MALE":"FEMALE";
        String email = user.getEmail();
        String activerCode = user.getActiveCode();
        if(user.getStatus()!=null)
            status = user.getStatus().equals(UserStatus.DISABLE)?"DISABLE":user.getStatus().equals(UserStatus.UNACTIVATED)?"UNACTIVATED":"ENABLE";

        try {
            //1.定义sql
            String sql = "insert into tb_user(userAccount, passwd, userName ,gender, email, userRole,status, activeCode) VALUES (?,?,?,?,?,?,?,?)";
            template.update(sql,
                    userAccount,
                    password,
                    username,
                    gender,
                    email,
                    role,
                    status,
                    activerCode
            );
        }catch (Exception e) {
            System.out.println(user.getUserAccount()+ "保存失败");
            return false;
        }
        return true;
    }

    // 启用用户
    @Override
    public boolean enable(User user) {
        try {
            String sql = "update tb_user set status='ENABLE' where userAccount=?";
            template.update(sql, user.getUserAccount());
        } catch (Exception e) {
            System.out.println(user.getUserAccount()+ "激活失败");
            return false;
        }
        return true;
    }

    // 通过激活码查找用户
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tb_user where activeCode=?";

            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // 通过绑好查找用户名
    @Override
    public String getNameByAccount(String account) {
        String sql = "select username from tb_user where userAccount=?";

        return template.queryForObject(sql,String.class,account);
    }

    // 获取所有用户信息
    @Override
    public List<User> getAll() {
        String sql = "select userAccount,username from tb_user";

        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    // 更改用户信息
    @Override
    public boolean set(User user) {
        String userAccount = user.getUserAccount();
        String username = user.getUserName();
        String password = user.getPasswd();
        int age = user.getAge();
        String role = "EMPLOYEE";
        String gender = "MALE";
        String status = "ENABLE";
        if (user.getUserRole()!=null)
            role = user.getUserRole().equals(UserRole.ADMINISTRATOR)?"ADMINISTRATOR":"EMPLOYEE";
        if(user.getGender()!=null)
            gender = user.getGender().equals(Gender.MALE)?"MALE":"FEMALE";
        String email = user.getEmail();
        String activerCode = user.getActiveCode();
        if(user.getStatus()!=null)
            status = user.getStatus().equals(UserStatus.DISABLE)?"DISABLE":user.getStatus().equals(UserStatus.UNACTIVATED)?"UNACTIVATED":"ENABLE";

        String sql = "update tb_user set passwd=?,userName=?,gender=?,age=?,userRole=?,status=?,email=?,activeCode=? where userAccount=?";

        try {
            template.update(sql, password,username,gender,age,role,status,email,activerCode, userAccount);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    // 获取所有员工信息
    @Override
    public List<User> getEmployees() {
        String sql = "select * from tb_user where userRole='EMPLOYEE'";
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class));
    }

    // 通过账号删除用户
    @Override
    public boolean delete(String account) {
        String sql = "delete from tb_user where userAccount=?";

        try {
            template.update(sql,account);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

}
