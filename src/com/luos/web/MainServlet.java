package com.luos.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by luos on 2016/10/31.
 */
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.setAttribute("mainPage", "dairy/dairylist.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, respone);
    }
}
