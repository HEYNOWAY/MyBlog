package com.luos.model;

/**
 * 日志类别
 *
 * Created by luos on 2016/11/2.
 */
public class DiaryType {
    /**
     * 日志类别ID
     */
    private int typeId;

    /**
     * 日志类别的名称
     */
    private String typeName;

    /**
     * 该类别下的日志数目
     */
    private int count;

    public DiaryType(){}

    public DiaryType(String typeName){
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return typeName+" "+count;
    }
}
