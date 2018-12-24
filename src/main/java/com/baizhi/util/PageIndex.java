package com.baizhi.util;

import com.baizhi.entity.Poetry;

import java.util.List;

public class PageIndex {
    private List<Poetry> poetries;
    private Integer total;

    public PageIndex() {
        super();
    }

    public List<Poetry> getPoetries() {
        return poetries;
    }

    public void setPoetries(List<Poetry> poetries) {
        this.poetries = poetries;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public PageIndex(List<Poetry> poetries, Integer total) {
        this.poetries = poetries;
        this.total = total;
    }
}
