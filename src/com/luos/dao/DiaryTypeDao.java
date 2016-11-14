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
     * 获取日志类型分组的日志数目的列表
     *
     * @param conn
     * @return
     * @throws SQLException
     */
    public List<DiaryType> diaryTypeCountList(Connection conn) throws SQLException {
        List<DiaryType> list = new ArrayList<>();
        String sql = "Select diaryTypeId,typeName, count(diaryId) as diaryCount " +
                "from t_diary Right join t_diaryType " +
                "on t_diary.typeId = t_diaryType.diaryTypeId " +
                "GROUP BY typeId ";
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

    /**
     * 获取日志类别列表
     *
     * @param con
     * @return
     * @throws Exception
     */
    public List<DiaryType> diaryTypeList(Connection con)throws Exception{
        List<DiaryType> diaryTypeList=new ArrayList<DiaryType>();
        String sql="select * from t_diaryType";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while(rs.next()){
            DiaryType diaryType=new DiaryType();
            diaryType.setTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
            diaryTypeList.add(diaryType);
        }
        return diaryTypeList;
    }

    /**
     * 添加日志类别
     *
     * @param conn
     * @param diaryType
     * @return
     * @throws Exception
     */
    public int addDiaryType(Connection conn,DiaryType diaryType)throws Exception{
        String sql="insert into t_diaryType values(null,?)";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, diaryType.getTypeName());
        return pstmt.executeUpdate();
    }

    /**
     *  修改日志类别
     *
     * @param conn
     * @param diaryType
     * @return
     * @throws Exception
     */
    public int updatediaryType(Connection conn,DiaryType diaryType)throws Exception{
        String sql="update t_diaryType set typeName=? where diaryTypeId=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, diaryType.getTypeName());
        pstmt.setInt(2, diaryType.getTypeId());
        return pstmt.executeUpdate();
    }

    /**
     * 根据id显示日志类别名称
     *
     * @param conn
     * @param diaryTypeId
     * @return
     * @throws Exception
     */
    public DiaryType showDiaryType(Connection conn,String diaryTypeId)throws Exception{
        String sql="SELECT * from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, diaryTypeId);
        ResultSet rs=pstmt.executeQuery();
        DiaryType diaryType=new DiaryType();
        if(rs.next()){
            diaryType.setTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
        }
        return diaryType;
    }

    /**
     * 删除该日志类别
     *
     * @param conn
     * @param diaryTypeId
     * @return
     * @throws SQLException
     */
    public int deleteDiaryType(Connection conn,String diaryTypeId) throws SQLException {
        String sql = "delete from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, diaryTypeId);
        return pstmt.executeUpdate();
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
