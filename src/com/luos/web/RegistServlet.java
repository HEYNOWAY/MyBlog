package com.luos.web;

import com.luos.dao.UserDao;
import com.luos.model.User;
import com.luos.util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by luos on 17-5-16.
 */
public class RegistServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        this.doPost(request, respone);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean isFirst = true;

        //获取表单数据
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        //用户注册
        User user = new User(userName, passWord);
        int result = doRegist(user);

        if (result > 0) {
            //若成功，跳转到mainTemp.jsp页面
            request.setAttribute("error", "注册成功！继续登录");
            HttpSession session = request.getSession();
            request.getRequestDispatcher("login.jsp").forward(request, respone);
            System.out.print("success....");
        } else {
            //若不成，留在本页面，提示错误
            if(!isFirst){
                System.out.print("error");
                request.setAttribute("user", user);
                request.setAttribute("error", "保存失败！");
            }
            isFirst = false;
            request.getRequestDispatcher("regist.jsp").forward(request, respone);
        }

    }

    private int doRegist(User user) {
        Connection conn = null;
        User currentUser = null;
        int result = 0;
        try {
            conn = DbUtil.getConn();
            UserDao userDao = new UserDao();
            result = userDao.regist(conn,user);
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
        System.out.println("result:"+result);
        return result;
    }
}
