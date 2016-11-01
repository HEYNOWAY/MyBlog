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
import java.util.ArrayList;
import java.util.List;

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
        List<Diary> diaryList = getDiaryList();
        request.setAttribute("diaryList", diaryList);
        request.setAttribute("mainPage", "dairy/diarylist.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, respone);
    }

    private List<Diary> getDiaryList() {
        DiaryDao diaryDao = new DiaryDao();
        List<Diary> diaryList = null;
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            diaryList = diaryDao.dariyList(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return diaryList;
    }
}
