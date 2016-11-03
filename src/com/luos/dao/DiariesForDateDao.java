package com.luos.dao;

import com.luos.model.DiariesForDate;
import com.luos.model.Diary;
import com.luos.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luos on 2016/11/4.
 */
public class DiariesForDateDao {

    //获取根据日期分组的数据列表
    public List<DiariesForDate> diaryDateList(Connection conn) throws SQLException {
        List<DiariesForDate> dairiesArrayList = new ArrayList<>();
        String sql = "select DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr, count(*) as diaryCount " +
                "from t_diary " +
                "group by DATE_FORMAT(releaseDate,'%Y年%m月') " +
                "order by DATE_FORMAT(releaseDate,'%Y年%m月') desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            DiariesForDate diariesForDate = new DiariesForDate();
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            diariesForDate.setDiaryCount(resultSet.getInt("diaryCount"));
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            dairiesArrayList.add(diariesForDate);
        }
        return dairiesArrayList;
    }

//    public static void main(String[] args){
//        try {
//            Connection conn = DbUtil.getConn();
//            DiariesForDateDao diariesForDateDao = new DiariesForDateDao();
//            List<DiariesForDate> dairiesArrayList = diariesForDateDao.diaryDateList(conn);
//            for(DiariesForDate diariesForDate:dairiesArrayList)
//                System.out.println("total is:"+diariesForDate);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
