package com.luos.dao;

import com.luos.model.Diary;
import com.luos.model.PageBean;
import com.luos.util.DateFormatUtil;
import com.luos.util.DbUtil;
import com.luos.util.StringUtil;

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

    /**
     *     获取日志列表
     *
     * @param conn      数据库连接
     * @param pageBean  底部分页
     * @param s_diary   日志参数
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public List<Diary> diaryList(Connection conn, PageBean pageBean,Diary s_diary) throws SQLException, ParseException {
        List<Diary> dairyArrayList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from t_diary t1 , t_diarytype t2 where t1.typeId = t2.diarytypeId ");

        if(StringUtil.isNotEmpty(s_diary.getTitle())){
            sql.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
        }
        if(s_diary.getTypeId()!=-1){
            sql.append(" and t1.typeId="+s_diary.getTypeId());
        }
        if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
        }
        sql.append(" order by t1.releaseDate desc");
        if(pageBean!=null){
            sql.append(" limit "+pageBean.getStartpage()+","+pageBean.getPageSize());
        }
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

    /**
     * 获取日志的总条数
     *
     * @param conn
     * @param s_diary
     * @return
     * @throws SQLException
     */
    public int diaryCount(Connection conn,Diary s_diary) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as total " +
                "from t_diary t1 , t_diarytype t2 " +
                "where t1.typeId = t2.diarytypeId ");
        if(StringUtil.isNotEmpty(s_diary.getTitle())){
            sql.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
        }
        if(s_diary.getTypeId()!=-1){
            sql.append(" and t1.typeId="+s_diary.getTypeId());
        }
        if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
        }
        System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("total");
        } else {
            return 0;
        }
    }

    /**
     *
     * 获取根据日期分组的数据列表
     *
     * @param conn
     * @return
     * @throws SQLException
     */
    public List<Diary> diaryDateList(Connection conn) throws SQLException {
        List<Diary> dairiesArrayList = new ArrayList<>();
        String sql = "select DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr, count(*) as diaryCount " +
                "from t_diary " +
                "group by DATE_FORMAT(releaseDate,'%Y年%m月') " +
                "order by DATE_FORMAT(releaseDate,'%Y年%m月') desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            Diary diariesForDate = new Diary();
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            diariesForDate.setDiaryCount(resultSet.getInt("diaryCount"));
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            dairiesArrayList.add(diariesForDate);
        }
        return dairiesArrayList;
    }

//
//    public static void main(String[] args){
//        try {
//            Connection conn = DbUtil.getConn();
//            DiaryDao diaryDao = new DiaryDao();
//            int count = diaryDao.diaryCount(conn);
//            System.out.println("total is:"+count);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
