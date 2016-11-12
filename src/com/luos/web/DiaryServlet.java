package com.luos.web;

import com.luos.dao.DiaryDao;
import com.luos.model.Diary;
import com.luos.util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by luos on 2016/11/12.
 */
public class DiaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if("show".equals(action)){
            diaryShow(request, response);
        }
    }

    private void diaryShow(HttpServletRequest request, HttpServletResponse response) {
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            DiaryDao diaryDao = new DiaryDao();
            String  diaryId = request.getParameter("diaryId");
            Diary diary = diaryDao.diaryShow(conn,diaryId);
            request.setAttribute("diary",diary);
            request.setAttribute("mainPage", "diary/diaryShow.jsp");
            request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
