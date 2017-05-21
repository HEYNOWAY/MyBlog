package com.luos.web;

import com.luos.dao.UserDao;
import com.luos.model.User;
import com.luos.util.DbUtil;
import com.luos.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 * 登录的Servlet
 * <p>
 * Created by luos on 2016/10/29.
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest reqest, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(reqest, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //获取表单数据
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        String remember = request.getParameter("remember");

        //登陆验证
        User user = new User(userName, passWord);
        User currentUser = doLogin(user);

        if (currentUser != null) {
            //若成功，跳转到mainTemp.jsp页面
            System.out.print("success");
            if ("remember-me".equals(remember)) {
                rememberMe(userName, passWord, respone);
            } else {
                unRememberMe(request,respone);
            }

            String imageName = currentUser.getImageName().replaceFirst(PropertiesUtil.getValue("imageFile"), "");
            if(imageName.equals("null")||"".equals(imageName)||imageName==null){
                currentUser.setImageName(PropertiesUtil.getValue("imageFile")+"default.jpg");
                System.out.println("Login after:"+currentUser.getImageName());
            }
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", currentUser);

            System.out.println("Login image path:"+currentUser.getImageName());
            request.getRequestDispatcher("main").forward(request, respone);

        } else {
            //若不成，留在本页面，提示错误
            System.out.print("error");
            request.setAttribute("user", user);
            request.setAttribute("error", "用户名或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, respone);
        }
    }

    /**
     * 登陆验证
     *
     * @param user
     * @return
     */
    private User doLogin(User user) {
        Connection conn = null;
        User currentUser = null;
        try {
            conn = DbUtil.getConn();
            UserDao userDao = new UserDao();
            currentUser = userDao.login(conn, user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //用完数据库记得关闭
                DbUtil.closeConn(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return currentUser;
    }

    private void rememberMe(String userName, String passWord, HttpServletResponse respone) {
        Cookie userCookie = new Cookie("user", userName + "-" + passWord);
        //设置cookie保存7天
        userCookie.setMaxAge(1 * 60 * 60 * 24 * 7);
        respone.addCookie(userCookie);
    }

    private void unRememberMe(HttpServletRequest request, HttpServletResponse respone) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("user")) {
                cookies[i].setMaxAge(0);
                respone.addCookie(cookies[i]);
            }
        }
    }

}
