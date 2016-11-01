package com.luos.dao;

import com.luos.model.Diary;
import com.luos.util.DateFormatUtil;
import com.luos.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luos on 2016/11/1.
 */
public class DiaryDao {
    public List<Diary> dariyList(Connection conn) throws SQLException, ParseException {
        List<Diary> dairyArrayList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from t_diary t1 , t_diarytype t2 where t1.typeId = t2.diarytypeId ");
        sql.append("order by releaseDate desc");
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            Diary diary = new Diary();
            diary.setDairyId(resultSet.getInt("diaryId"));
            diary.setTitle(resultSet.getString("title"));
            diary.setContent(resultSet.getString("content"));
            diary.setTypeId(resultSet.getInt("typeId"));
            diary.setReleaseDate(DateFormatUtil.StringToDate(resultSet.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
            dairyArrayList.add(diary);
        }
        return dairyArrayList;
    }

    public static void main(String[] args){
        try {
            Connection conn = DbUtil.getConn();
            DiaryDao diaryDao = new DiaryDao();
            List<Diary> diaryList = diaryDao.dariyList(conn);
            for (Diary diary : diaryList){
                System.out.println(diary.getReleaseDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
