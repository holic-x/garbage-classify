package com.rc.server.framework.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageHelper<T> {


    /**
     * 当前页数
     */
    private long currPage;
    /**
     * 每页记录数
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 列表数据
     */
    private List<T> list;

    public PageHelper(long pageNum, long pageSize, long totalPage, long totalCount, List<T> list) {
        this.currPage = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.list = list;
    }

    public PageHelper(long pageNum, long pageSize, List<T> list) {
        this.currPage = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }

    // 通过经mybatis分页后的信息封装数据
    public PageHelper(Page<T> pageData) {
        this.currPage = pageData.getCurrent();
        this.pageSize = pageData.getSize();
        this.totalPage = pageData.getPages();
        this.totalCount = pageData.getTotal();
        this.list = pageData.getRecords();
    }
}