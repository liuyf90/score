package com.example.demo.entity;

import java.util.List;

/**
 * Created by liuyf90 on 2018/6/13.
 */
public class PageInfo<E> {
    int page ;
    int limit ;
    int start;
    List<E> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<E> getDatas() {
        return data;
    }

    public void setDatas(List<E> data) {
        this.data = data;
    }
}
