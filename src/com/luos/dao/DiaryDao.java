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
     * 获取日志列表
     *
     * @param conn     数据库连接
     * @param pageBean 底部分页
     * @param s_diary  日志参数
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public List<Diary> diaryList(Connection conn, PageBean pageBean, Diary s_diary, int userId) throws SQLException, ParseException {
        List<Diary> dairyArrayList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from t_diary t1 , t_diaryType t2 where t1.typeId = t2.diaryTypeId and t1.ownerId = "+userId);
        System.out.println("DiaryList:"+sql.toString());
        if (StringUtil.isNotEmpty(s_diary.getTitle())) {
            sql.append(" and t1.title like '%" + s_diary.getTitle() + "%'");
        }
        if (s_diary.getTypeId() != -1) {
            sql.append(" and t1.typeId=" + s_diary.getTypeId());
        }
        if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='" + s_diary.getReleaseDateStr() + "'");
        }
        sql.append(" order by t1.releaseDate desc");
        if (pageBean != null) {
            sql.append(" limit " + pageBean.getStartpage() + "," + pageBean.getPageSize());
        }
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            Diary diary = new Diary();
            diary.setDiaryId(resultSet.getInt("diaryId"));
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
    public int diaryCount(Connection conn, Diary s_diary, int userId) throws SQLException {
        StringBuffer sql = new StringBuffer("select count(*) as total " +
                "from t_diary t1 , t_diaryType t2 " +
                "where t1.typeId = t2.diaryTypeId and t1.ownerId = "+userId);
        if (StringUtil.isNotEmpty(s_diary.getTitle())) {
            sql.append(" and t1.title like '%" + s_diary.getTitle() + "%'");
        }
        if (s_diary.getTypeId() != -1) {
            sql.append(" and t1.typeId=" + s_diary.getTypeId());
        }
        if (StringUtil.isNotEmpty(s_diary.getReleaseDateStr())) {
            sql.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='" + s_diary.getReleaseDateStr() + "'");
        }
        System.out.println(sql);
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("total");
        } else {
            return 0;
        }
    }

    /**
     * 获取根据日期分组的数据列表
     *
     * @param conn
     * @return
     * @throws SQLException
     */
    public List<Diary> diaryDateList(Connection conn, int userId) throws SQLException {
        List<Diary> dairiesArrayList = new ArrayList<>();
        String sql = "select DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr, count(*) as diaryCount " +
                "from t_diary " +
                "where ownerId = "+userId+
                " group by DATE_FORMAT(releaseDate,'%Y年%m月') " +
                "order by DATE_FORMAT(releaseDate,'%Y年%m月') desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            Diary diariesForDate = new Diary();
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            diariesForDate.setDiaryCount(resultSet.getInt("diaryCount"));
            diariesForDate.setReleaseDateStr(resultSet.getString("releaseDateStr"));
            dairiesArrayList.add(diariesForDate);
        }
        return dairiesArrayList;
    }

    /**
     * 根据diaryid获取日志详细信息
     *
     * @param conn
     * @param diaryId
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public Diary diaryShow(Connection conn, String diaryId) throws SQLException, ParseException {
        Diary diary = new Diary();
        String sql = "select * from t_diary t1 , t_diaryType t2 where t1.typeId = t2.diaryTypeId and t1.diaryId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,diaryId);
        ResultSet resultSet = pstmt.executeQuery();
        if(resultSet.next()){
            diary.setDiaryId(resultSet.getInt("diaryId"));
            diary.setTitle(resultSet.getString("title"));
            diary.setContent(resultSet.getString("content"));
            diary.setTypeId(resultSet.getInt("typeId"));
            diary.setTypeName(resultSet.getString("typeName"));
            diary.setReleaseDate(DateFormatUtil.StringToDate(resultSet.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss"));
        }
        return diary;
    }

    /**
     * 添加日志
     *
     * @param conn
     * @param diary
     * @return
     * @throws SQLException
     */
    public int addDiary(Connection conn, Diary diary) throws SQLException {
        String sql = "INSERT INTO t_diary values(null,?,?,?,?,now())";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,diary.getOwnerId());
        pstmt.setString(2,diary.getTitle());
        pstmt.setString(3,diary.getContent());
        pstmt.setInt(4,diary.getTypeId());
        return pstmt.executeUpdate();
    }

    /**
     * 删除日志
     *
     * @param conn
     * @param diary
     * @return
     * @throws SQLException
     */
    public int deleteDiary(Connection conn, String diary) throws SQLException {
        String sql = "Delete from t_diary where diaryId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,diary);
        return  pstmt.executeUpdate();
    }

    /**
     * 修改日志
     *
     * @param conn
     * @param diary
     * @return
     * @throws SQLException
     */
    public int updateDiary(Connection conn, Diary diary) throws SQLException {
        String sql = "Update t_diary set title=?,content=?,typeId=? where diaryId=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,diary.getTitle());
        pstmt.setString(2,diary.getContent());
        pstmt.setInt(3,diary.getTypeId());
        pstmt.setInt(4,diary.getDiaryId());
        return pstmt.executeUpdate();
    }

    /**
     * 该类别下是否存在日志
     *
     * @param conn
     * @param typeId
     * @return
     * @throws SQLException
     */
    public boolean isExistDiary(Connection conn, String typeId) throws SQLException {
        String sql = "select * from t_diary where typeId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,typeId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }
}
