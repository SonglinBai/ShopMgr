package com.bais1.web.servlet;

import com.bais1.domain.*;
import com.bais1.service.UserService;
import com.bais1.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.jvm.Gen;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //2.封装对象
        User user = new User();
        user.setUserAccount(request.getParameter("userAccount"));
        user.setUserName(request.getParameter("userName"));
        user.setUserRole(UserRole.EMPLOYEE);
        user.setPasswd(request.getParameter("passwd"));
        user.setEmail(request.getParameter("email"));
        user.setAge(Integer.parseInt(request.getParameter("age")));
        user.setGender(request.getParameter("gender").equals("MALE")? Gender.MALE:Gender.FEMALE);

        //3.调用service完成注册
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("用户名已经存在!");
        }

        writeValue(info, response);

    }

    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码检验
        //验证校验
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能使用一次
        System.out.println("删除验证码" + checkcode_server);
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            //登录失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

//        //1.获取用户名和密码数据
//        Map<String, String[]> map = request.getParameterMap();
//        //2.封装User对象
//        User user = new User();
//        try {
//            BeanUtils.populate(user, map);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
        User user = new User();
        user.setUserAccount(request.getParameter("username"));
        user.setPasswd(request.getParameter("password"));

        //3.调用Service查询
        // UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if (u == null) {
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }
        //6.判断登录成功
        else if (u.getStatus().equals(UserStatus.ENABLE)) {
            request.getSession().setAttribute("user", u);//登录成功标记
            //登录成功
            info.setFlag(true);
        }
        //5.判断用户是否激活
        else if (!u.getStatus().equals(UserStatus.UNACTIVATED)) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您的账号尚未激活，请激活");
        }
        //判断用户是否被禁用
        else if (u.getStatus().equals(UserStatus.DISABLE)) {
            info.setFlag(false);
            info.setErrorMsg("你的账号被禁用,请联系你的账号管理员");
        }

        //响应数据
        this.writeValue(info, response);
    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    public void getFromSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端
        writeValue(user, response);
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getUserRole() != UserRole.ADMINISTRATOR) return;
        List<User> users = service.getAll();

        writeValue(users, response);
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用Service查询
        // UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if (u == null) {
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        } else {
            info.setFlag(true);
        }
        writeValue(info, response);
    }

    public void set(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        Map<String, String[]> map = request.getParameterMap();
        User u =(User)request.getSession().getAttribute("user");

        User user = new User();
        user.setUserAccount(request.getParameter("userAccount"));
        user.setUserName(request.getParameter("userName"));
        user.setPasswd(request.getParameter("passwd"));
        user.setGender(request.getParameter("gender").equals("MALE")?Gender.MALE:Gender.FEMALE);
        user.setEmail(request.getParameter("email"));
        user.setUserRole(u.getUserRole());
        user.setStatus(u.getStatus());
        user.setActiveCode(u.getActiveCode());
        ResultInfo info = new ResultInfo();
//        try {
//            BeanUtils.populate(user, map);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }

        if (!user.getUserName().equals(u.getUserName()) &&service.isUserAccountExist(user)) {
            info.setFlag(false);
            info.setErrorMsg("用户名已经存在");
        } else {
            if (service.set(user)) {
                request.getSession().setAttribute("user", user);
                info.setFlag(true);
            }else {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        writeValue(info,response);

    }

    public void getEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User u = (User) request.getSession().getAttribute("user");
        if (u.getUserRole() != UserRole.ADMINISTRATOR) return;

/*
        User user = (User)request.getSession().getAttribute("user");
        if (user==null ||user.getRole()!=1) return;
*/
        List<User> employees = service.getEmployees();

        writeValue(employees, response);
    }

    public void disable(HttpServletRequest request, HttpServletResponse response) {
        User u = (User) request.getSession().getAttribute("user");
        if (u.getUserRole() != UserRole.ADMINISTRATOR) return;

        String uidStr = request.getParameter("uid");
        service.disable(uidStr);
    }

    public void enable(HttpServletRequest request, HttpServletResponse response) {
        User u = (User) request.getSession().getAttribute("user");
        if (u.getUserRole()!=UserRole.ADMINISTRATOR) return;

        String uidStr = request.getParameter("uid");
        service.enable(uidStr);
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) {
        User u = (User) request.getSession().getAttribute("user");
        if (u.getUserRole()!=UserRole.ADMINISTRATOR) return;

        String uidStr = request.getParameter("uid");

        service.remove(uidStr);
    }

    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //2.调用service完成激活
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='http://localhost/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
