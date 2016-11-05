package com.luos.dao;

import com.luos.model.User;
import com.luos.util.DbUtil;
import com.luos.util.Md5Util;
import com.luos.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 用户类操作
 *
 * @author Administrator
 */
public class UserDao {

    /**
     * 登录验证
     *
     * @param conn
     * @param user
     * @return
     * @throws Exception
     */
    public User login(Connection conn, User user) throws Exception {
        User resultUser = null;
        String sql = "select * from t_user where userName=? and password=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, Md5Util.EncoderPwdMd5(user.getPassWord()));
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            resultUser = new User();
            resultUser.setUserID(rs.getInt("id"));
            resultUser.setUserName(rs.getString("userName"));
            resultUser.setPassWord(rs.getString("password"));
            resultUser.setNickName(rs.getString("nickName"));
            resultUser.setImageName(PropertiesUtil.getValue("imageFile")+rs.getString("imageName"));
            resultUser.setMood(rs.getString("mood"));
        }
        return resultUser;
    }

//	public static void main(String[] args) throws Exception {
//		User user = new User("luos","luos");
//		UserDao userDao = new UserDao();
//		DbUtil dbUtil = new DbUtil();
//		Connection conn = dbUtil.getConn();
//		User currentUser = userDao.login(conn,user);
//		if(currentUser!=null){
//			System.out.print("success");
//		} else {
//			System.out.print("error");
//		}
//	}

}
