package com.bais1.dao.impl;

import com.bais1.dao.UserDao;
import com.bais1.domain.Gender;
import com.bais1.domain.User;
import com.bais1.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    //获取jdbcTemplate
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

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
        return user;
    }

    @Override
    public boolean save(User user) {
        try {
            //1.定义sql
            String sql = "insert into tb_user(userAccount, passwd, userName ,gender, email, userRole,isEnable, activeCode) VALUES (?,?,?,?,?,?,?,?)";
            template.update(sql,
                    user.getUserAccount(),
                    user.getPasswd(),
                    user.getUserName(),
                    user.getGender(),
                    user.getEmail(),
                    2,
                    0,
                    user.getActiveCode()
            );
        }catch (Exception e) {
            System.out.println(user.getUserAccount()+ "保存失败");
            return false;
        }
        return true;
    }

    @Override
    public boolean enable(User user) {
        try {
            String sql = "update tb_user set isEnable=1 where userAccount=?";
            template.update(sql, user.getUserAccount());
        } catch (Exception e) {
            System.out.println(user.getUserAccount()+ "激活失败");
            return false;
        }
        return true;
    }

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

    @Override
    public String getNameByAccount(String account) {
        String sql = "select username from tb_user where userAccount=?";

        return template.queryForObject(sql,String.class,account);
    }

    @Override
    public List<User> getAll() {
        String sql = "select userAccount,username from tb_user";

        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public boolean set(User user) {
        String userAccount = user.getUserAccount();
        String username = user.getUserName();
        String password = user.getPasswd();
        String name = user.getUserName();
        Gender sex = user.getGender();
        String email = user.getEmail();

        String sql = "update tb_user set userAccount=?,passwd=?,userName=?,gender=?,email=? where userAccount=?";

        try {
            template.update(sql,username,password,name,sex,email,userAccount);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<User> getEmployees() {
        String sql = "select * from tb_user where userRole=2";
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class));
    }

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
