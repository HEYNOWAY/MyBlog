package com.luos.model;

/**
 * 分页类
 *
 * Created by luos on 2016/11/2.
 */
public class PageBean {

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页多少条日志
     */
    private int pageSize;

    public PageBean(int page, int pageSize){
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartpage(){
        return (page-1)*pageSize;
    }


}
