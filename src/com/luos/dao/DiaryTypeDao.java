package com.luos.dao;

import com.luos.model.Diary;
import com.luos.model.DiaryType;
import com.luos.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luos on 2016/11/3.
 */
public class DiaryTypeDao {

    /**
     * 获取日志类型的数据列表
     *
     * @param conn
     * @return
     * @throws SQLException
     */
    public List<DiaryType> diaryTypeList(Connection conn) throws SQLException {
        List<DiaryType> list = new ArrayList<>();
        String sql = "Select diaryTypeId,typeName, count(diaryId) as diaryCount " +
                "from t_diary Right join t_diaryType " +
                "on t_diary.typeId = t_diaryType.diaryTypeId " +
                "GROUP BY typeName";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet resultset = pstmt.executeQuery();
        while (resultset.next()){
            DiaryType diaryType = new DiaryType();
            diaryType.setTypeId(resultset.getInt("diaryTypeId"));
            diaryType.setTypeName(resultset.getString("typeName"));
            diaryType.setCount(resultset.getInt("diaryCount"));
            list.add(diaryType);
        }
        return list;
    }

//    public static void main(String args[]){
//        try {
//            Connection conn = DbUtil.getConn();
//            DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
//            List<DiaryType> diaryTypeList = diaryTypeDao.diaryTypeList(conn);
//            for(DiaryType diaryType:diaryTypeList){
//                System.out.println(diaryType);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
