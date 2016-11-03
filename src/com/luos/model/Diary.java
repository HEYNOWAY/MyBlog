package com.luos.model;

import java.util.Date;

/**
 * 日志类
 *
 * Created by luos on 2016/11/1.
 */
public class Diary {
    /**
     * 日志ID
     */
    private int dairyId;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志类别
     */
    private int typeId = -1;

    /**
     * 日志发布日期
     */
    private Date releaseDate;

    public int getDairyId() {
        return dairyId;
    }

    public void setDairyId(int dairyId) {
        this.dairyId = dairyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return typeId+" "+title;
    }
}
