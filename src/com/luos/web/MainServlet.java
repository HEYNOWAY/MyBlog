package com.luos.web;

import com.luos.dao.DiaryDao;
import com.luos.dao.DiaryTypeDao;
import com.luos.model.Diary;
import com.luos.model.DiaryType;
import com.luos.model.PageBean;
import com.luos.util.DbUtil;
import com.luos.util.PropertiesUtil;
import com.luos.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 日志主页面Servlet
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
        HttpSession session = request.getSession();
        int total = 0;  //数据的总条数
        String page = request.getParameter("page"); //当前页面
        if(page==null){
            page = "1";
        }
        String pageSize = PropertiesUtil.getValue("pageSize"); //每页的页数多少
        List<Diary> diaryList = new ArrayList<>(); //日志列表
        List<DiaryType> diaryTypeList = new ArrayList<>(); //日志类型列表
        List<Diary> diariesForDateList = new ArrayList<>();//根据日期分组的日志组列表
        Diary diary = getDiaryFormSession(request,session); //从session中获取Diary的值

        //从数据库获取各列表数据
        Connection conn = null;
        try {
            conn = DbUtil.getConn();
            DiaryDao diaryDao = new DiaryDao();
            DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
            PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(pageSize));
            diaryList = diaryDao.diaryList(conn,pageBean,diary);
            diaryTypeList = diaryTypeDao.diaryTypeList(conn);
            diariesForDateList = diaryDao.diaryDateList(conn);
            total = diaryDao.diaryCount(conn,diary);
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

        //request,session 设置属性
        request.setAttribute("diaryList", diaryList);
        session.setAttribute("diariesForDateList",diariesForDateList);
        session.setAttribute("diaryTypeList",diaryTypeList);
        request.setAttribute("pageCode",pageCode);
        request.setAttribute("mainPage", "diary/diarylist.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, respone);
    }

    /**
     * 从session中获取Diary的值
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private Diary getDiaryFormSession(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String s_typeId=request.getParameter("s_typeId");
        String s_releaseDateStr=request.getParameter("s_releaseDateStr");

        String s_title=request.getParameter("s_title");
        String all=request.getParameter("all");
        Diary diary = new Diary();
        if("true".equals(all)){
            if(StringUtil.isNotEmpty(s_title)){
                diary.setTitle(s_title);
            }
            session.removeAttribute("s_releaseDateStr");
            session.removeAttribute("s_typeId");
            session.setAttribute("s_title", s_title);
        }else{
            if(StringUtil.isNotEmpty(s_typeId)){
                diary.setTypeId(Integer.parseInt(s_typeId));
                session.setAttribute("s_typeId", s_typeId);
                session.removeAttribute("s_releaseDateStr");
                session.removeAttribute("s_title");
            }

            if(StringUtil.isNotEmpty(s_releaseDateStr)){
                diary.setReleaseDateStr(s_releaseDateStr);
                session.setAttribute("s_releaseDateStr", s_releaseDateStr);
                session.removeAttribute("s_typeId");
                session.removeAttribute("s_title");
            }

            if(StringUtil.isEmpty(s_typeId)){
                Object o=session.getAttribute("s_typeId");
                if(o!=null){
                    diary.setTypeId(Integer.parseInt((String)o));
                }
            }

            if(StringUtil.isEmpty(s_releaseDateStr)){
                Object o=session.getAttribute("s_releaseDateStr");
                if(o!=null){
                    diary.setReleaseDateStr((String)o);
                }
            }
            if(StringUtil.isEmpty(s_title)){
                Object o=session.getAttribute("s_title");
                if(o!=null){
                    diary.setTitle((String)o);
                }
            }
        }
        return diary;
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
