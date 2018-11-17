package com.quality.common.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果对象,这里以layui框架的table为标准
 *
 * @author wangfan
 * @date 2017-7-24 下午4:28:59
 */
public class PageResult<T> implements IPage<T> {

    private int code; //状态码, 0表示成功

    private String msg;  //提示信息

    private long count; // 总数量, bootstrapTable是total

    private List<T> data; // 当前数据, bootstrapTable是rows

    private long pageSize; // 一页多少条

    private long pageNumber; //当前页

    private boolean optimizeCountSql;

    public PageResult() {
        this.data = Collections.emptyList();
        this.count = 0L;
        this.pageSize = 10L;
        this.pageNumber = 1L;
    }

    public PageResult(List<T> rows) {
        this.data = rows;
        this.count = rows.size();
        this.code = 0;
        this.msg = "";
    }

    public PageResult(long total, List<T> rows) {
        this.count = total;
        this.data = rows;
        this.code = 0;
        this.msg = "";
    }

    public PageResult(long current, long size) {
        this(current, size, 0L);
    }

    public PageResult(long current, long size, long total) {
        this.data = Collections.emptyList();
        this.count = 0L;
        this.pageSize = 10L;
        this.pageNumber = 1L;
        this.optimizeCountSql = true;
        if (current > 1L) {
            this.pageNumber = current;
        }

        this.pageSize = size;
        this.count = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.count = data.size();
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public List<T> getRecords() {

        return this.data;
    }

    @Override
    public IPage<T> setRecords(List<T> list) {
        this.data = list;
        return this;
    }

    @Override
    public long getTotal() {
        return this.count;
    }

    @Override
    public IPage<T> setTotal(long count) {
        this.count = count;
        return this;
    }

    @Override
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public IPage<T> setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return pageNumber;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        this.pageNumber = current;
        return this;
    }
}
