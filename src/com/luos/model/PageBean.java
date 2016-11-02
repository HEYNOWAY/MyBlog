package com.luos.model;

/**
 * Created by luos on 2016/11/2.
 */
public class PageBean {

    private int page;
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
