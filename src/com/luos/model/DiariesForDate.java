package com.luos.model;

/**
 * 根据日期分组的日志组
 *
 * Created by luos on 2016/11/4.
 */
public class DiariesForDate {
    /**
     * 分组的日期
     */
    private String releaseDateStr;

    /**
     * 该日志组的日志数目
     */
    private int diaryCount;

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }

    @Override
    public String toString() {
        return releaseDateStr+" "+diaryCount;
    }
}
