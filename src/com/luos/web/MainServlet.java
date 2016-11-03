package com.luos.web;

import com.luos.dao.DiariesForDateDao;
import com.luos.dao.DiaryDao;
import com.luos.dao.DiaryTypeDao;
import com.luos.model.DiariesForDate;
import com.luos.model.Diary;
import com.luos.model.DiaryType;
import com.luos.model.PageBean;
import com.luos.util.DbUtil;
import com.luos.util.PropertiesUtil;

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
 * 主页面Servle
 *
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

        int total = 0;  //数据的总条数
        String page = request.getParameter("page"); //当前页面
        if(page==null){
            page = "1";
        }
        String pageSize = PropertiesUtil.getValue("pageSize"); //每页的页数多少
        List<Diary> diaryList = new ArrayList<>(); //日志列表
        List<DiaryType> diaryTypeList = new ArrayList<>(); //日志类型列表
        List<DiariesForDate> diariesForDateList = new ArrayList<>();//根据日期分组的日志组类别

        //从数据库获取各列表数据
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            DiaryDao diaryDao = new DiaryDao();
            DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
            DiariesForDateDao diariesForDateDao = new DiariesForDateDao();
            PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(pageSize));
            diaryList = diaryDao.diaryList(conn,pageBean);
            diaryTypeList = diaryTypeDao.diaryTypeList(conn);
            diariesForDateList = diariesForDateDao.diaryDateList(conn);
            total = diaryDao.diaryCount(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //获取底栏的分页
        String pageCode = getPagation(total,Integer.parseInt(page),Integer.parseInt(pageSize));

        request.setAttribute("diariesForDateList",diariesForDateList);
        request.setAttribute("diaryList", diaryList);
        request.setAttribute("diaryTypeList",diaryTypeList);
        request.setAttribute("pageCode",pageCode);
        request.setAttribute("mainPage", "dairy/diarylist.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, respone);
    }


    /**
     * //拼接底栏的分页
     *
     * @param totalNum  数据的总条数
     * @param currentPage  当前页面
     * @param pageSize 每页的页数多少
     * @return
     */
    private String getPagation(int totalNum,int currentPage,int pageSize){
        int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode=new StringBuffer();
        pageCode.append("<li><a href='main?page=1'>首页</a></li>");
        if(currentPage==1){
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++){
            if(i<1||i>totalPage){
                continue;
            }
            if(i==currentPage){
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            }else{
                pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage){
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");

        return pageCode.toString();
    }


}
