package com.luos.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import com.luos.dao.UserDao;
import com.luos.model.User;
import com.luos.util.DateFormatUtil;
import com.luos.util.DbUtil;
import com.luos.util.PropertiesUtil;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Created by luos on 2016/11/14.
 */
public class UserServelet extends HttpServlet{

    private UserDao userDao = new UserDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if("preSave".equals(action)){
            userPreSave(request,response);
        } else if("Save".equals(action)){
            userSave(request,response);
        }
    }

    private void userPreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mainPage","user/userSave.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
    }

    private void userSave(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;

        try {
            items = upload.parseRequest(new ServletRequestContext(request));
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        Iterator<FileItem> itr = items.iterator();
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("currentUser");
        boolean imageChange = false;
        while (itr.hasNext()){
            FileItem item = itr.next();
            if(item.isFormField()){
                String fieldName=item.getFieldName();
                if("nickName".equals(fieldName)){
                    user.setNickName(item.getString("utf-8"));
                }
                if("mood".equals(fieldName)){
                    user.setMood(item.getString("utf-8"));
                }
            }else if(!"".equals(item.getName())){
                try{
                    imageChange=true;
                    String imageName= DateFormatUtil.CurrentDateStr();
                    user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
                    String filePath= PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
                    item.write(new File(filePath));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(!imageChange){
            user.setImageName(user.getImageName().replaceFirst(PropertiesUtil.getValue("imageFile"), ""));
        }

        Connection conn=null;
        try {
            conn= DbUtil.getConn();
            int saveNums=userDao.userUpdate(conn, user);
            if(saveNums>0){
                user.setImageName(PropertiesUtil.getValue("imageFile")+user.getImageName());
                session.setAttribute("currentUser", user);
                request.getRequestDispatcher("main?all=true").forward(request, response);
            }else{
                request.setAttribute("currentUser", user);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "user/userSave.jsp");
                request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
