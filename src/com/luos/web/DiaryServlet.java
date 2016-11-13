package com.luos.web;

import com.luos.dao.DiaryDao;
import com.luos.model.Diary;
import com.luos.util.DbUtil;
import com.luos.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by luos on 2016/11/12.
 */
public class DiaryServlet extends HttpServlet {

    DiaryDao diaryDao = new DiaryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("show".equals(action)) {
            diaryShow(request, response);
        } else if ("preSave".equals(action)) {
            diaryPreSave(request, response);
        } else if ("save".equals(action)) {
            diarySave(request, response);
        } else if ("delete".equals(action)) {
            diaryDelete(request, response);
        }
    }


    /**
     * 显示日志详细信息
     *
     * @param request
     * @param response
     */
    private void diaryShow(HttpServletRequest request, HttpServletResponse response) {
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            String diaryId = request.getParameter("diaryId");
            Diary diary = diaryDao.diaryShow(conn, diaryId);
            request.setAttribute("diary", diary);
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

    private void diaryPreSave(HttpServletRequest request, HttpServletResponse response) {
        String diaryId = request.getParameter("diaryId");
        Connection conn = null;
        try {
            if (StringUtil.isNotEmpty(diaryId)) {
                conn = DbUtil.getConn();
                Diary diary = diaryDao.diaryShow(conn, diaryId);
                request.setAttribute("diary",diary);
            }
            request.setAttribute("mainPage", "diary/diarySave.jsp");
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


    private void diarySave(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String typeId = request.getParameter("typeId");
        String diaryId = request.getParameter("diaryId");

        Diary diary = new Diary(title, content, Integer.parseInt(typeId));


        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            int saveNums;
            if(StringUtil.isNotEmpty(diaryId)){
                diary.setDiaryId(Integer.parseInt(diaryId));
                saveNums = diaryDao.updateDiary(conn,diary);
            } else {
                saveNums = diaryDao.addDiary(conn, diary);
            }
            if (saveNums > 0) {
                request.getRequestDispatcher("main?all=true").forward(request, response);
            } else {
                request.setAttribute("diary", diary);
                request.setAttribute("error", "保存失败！请重新保存");
                request.setAttribute("mainPage", "diary/diarySave.jsp");
                request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
            }
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

    private void diaryDelete(HttpServletRequest request, HttpServletResponse response) {
        String diaryId = request.getParameter("diaryId");
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            diaryDao.deleteDiary(conn, diaryId);
            request.getRequestDispatcher("main?all=true").forward(request, response);
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
