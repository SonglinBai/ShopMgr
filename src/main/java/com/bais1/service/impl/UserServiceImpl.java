package com.bais1.service.impl;

import com.bais1.dao.UserDao;
import com.bais1.dao.impl.UserDaoImpl;
import com.bais1.domain.User;
import com.bais1.service.UserService;
import com.bais1.util.MailUtils;
import com.bais1.util.UuidUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUserAccount(user.getUserAccount());
        //判断u是否为null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setActiveCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setEnable(false);
        userDao.save(user);

        //3.激活邮件发送，邮件正文

        String content="<a href='http://localhost/user/activeUser?code="+user.getActiveCode()+"'>点击激活</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            userDao.enable(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean disable(String account) {
//        User user = new User();
//        user.setUid(uid);
//        userDao.updateStatus(user,200);
//        return true;
        return true;
    }

    @Override
    public boolean enable(String account) {
//        User user = new User();
//        user.setUid(uid);
//        userDao.updateStatus(user,100);
//        return true;
        return true;
    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUserAccountAndPassword(user.getUserAccount(),user.getPasswd());
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public boolean set(User user) {
        return userDao.set(user);
    }

    @Override
    public List<User> getEmployees() {
        return userDao.getEmployees();
    }

    @Override
    public boolean remove(String account) {
        return userDao.delete(account);
    }

    @Override
    public boolean isUserAccountExist(User user) {
        User u = userDao.findByUserAccount(user.getUserAccount());
        if (u!=null) return true;
        else return false;
    }

}
