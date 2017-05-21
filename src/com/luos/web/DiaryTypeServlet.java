package com.luos.web;

import com.luos.dao.DiaryDao;
import com.luos.dao.DiaryTypeDao;
import com.luos.model.DiaryType;
import com.luos.model.User;
import com.luos.util.DbUtil;
import com.luos.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by luos on 2016/11/14.
 */
public class DiaryTypeServlet extends HttpServlet {

    private DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            diaryTypeShow(request, response);
        } else if ("preSave".equals(action)) {
            diaryTypePreSave(request, response);
        } else if("Save".equals(action)){
            diaryTypeSave(request,response);
        } else if ("delete".equals(action)) {
            diaryTypeDelete(request, response);
        }
    }


    /**
     * 显示日志类型列表
     *
     * @param request
     * @param response
     */
    private void diaryTypeShow(HttpServletRequest request, HttpServletResponse response) {
        Connection conn = null;
        HttpSession session = request.getSession();

        try {
            conn = DbUtil.getConn();
            int userId = ((User)session.getAttribute("currentUser")).getUserID();
            List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypeList(conn,userId);
            request.setAttribute("diaryTypeList",diaryTypeList);
            request.setAttribute("mainPage","diaryType/diaryTypeList.jsp");
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

    /**
     * 保存前准备数据
     * 修改和添加共用一个jsp页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void diaryTypePreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String diaryTypeId = request.getParameter("typeId");
        if (StringUtil.isNotEmpty(diaryTypeId)) {
            Connection conn = null;
            try {
                conn = DbUtil.getConn();
                DiaryType diaryType = diaryTypeDao.showDiaryType(conn, diaryTypeId);
                request.setAttribute("diaryType", diaryType);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       }
        request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
    }

    /**
     * 保存数据
     *
     * @param request
     * @param response
     */
    private void diaryTypeSave(HttpServletRequest request, HttpServletResponse response) {
        String diaryTypeId = request.getParameter("typeId");
        String diaryTypeName = request.getParameter("typeName");
        HttpSession session = request.getSession();
        int userId = ((User)session.getAttribute("currentUser")).getUserID();
        DiaryType diaryType = new DiaryType(diaryTypeName,userId);
        Connection conn = null;
        int sumNums;
        try {
            conn = DbUtil.getConn();
            if(StringUtil.isNotEmpty(diaryTypeId)){
                diaryType.setTypeId(Integer.parseInt(diaryTypeId));
                sumNums = diaryTypeDao.updatediaryType(conn,diaryType);
                System.out.println("更新日志类型。。。。。"+sumNums);
            } else {
                sumNums = diaryTypeDao.addDiaryType(conn,diaryType);
                System.out.println("添加日志类型。。。。。"+sumNums);
            }
            if(sumNums>0){
                System.out.println("更新页面");
                request.getRequestDispatcher("diaryType?action=list").forward(request, response);
            } else {
                request.setAttribute("diaryType",diaryType);
                request.setAttribute("error", "保存失败！请重新保存");
                request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
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

    /**
     * 删除日志类别
     *
     * @param request
     * @param response
     */
    private void diaryTypeDelete(HttpServletRequest request, HttpServletResponse response) {
        String diaryTypeId = request.getParameter("typeId");
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            DiaryDao diaryDao = new DiaryDao();
            if(diaryDao.isExistDiary(conn,diaryTypeId)){
                request.setAttribute("error","该类别下有日志内容，不能删除！");
            } else {
                diaryTypeDao.deleteDiaryType(conn,diaryTypeId);
            }
            request.getRequestDispatcher("diaryType?action=list").forward(request, response);
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
