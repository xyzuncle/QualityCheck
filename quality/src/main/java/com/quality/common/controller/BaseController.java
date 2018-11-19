package com.quality.common.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import com.quality.common.dto.ObjectRestResponse;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.specifications.DynamicSpecifications;
import com.quality.common.specifications.SearchFilter;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseController<T,S extends IService<T>> {

    protected Class<T> entityClass;


    @Autowired
    protected S defaultDAO;

    @Autowired
    public HttpServletRequest request;

    public IPage<T> queryContion(Map<String,Object> searchParams, Sort sort) throws Exception {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        int page=0;
        int limit=10;
        if(request.getParameter("page")==null || "".equals(request.getParameter("page"))){
            page=1;
        }else{
            page = Integer.parseInt(request.getParameter("page"))>1?
                    Integer.parseInt(request.getParameter("page")):1;

        }
        if(request.getParameter("limit")==null || "".equals(request.getParameter("limit"))){
            limit =10;
        }else{
            limit = Integer.parseInt(request.getParameter("limit"));
        }

        QueryWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);

        if(StringUtils.isNotBlank(sort.getDirection())){
            if(sort.getDirection().equals("ASC")){
                spec.orderByAsc(Tools.StrArr2String(sort.getColNames()));

            } else if (sort.getDirection().equals("DESC")) {
                spec.orderByDesc(Tools.StrArr2String(sort.getColNames()));
            }else {
                throw new Exception("sort parame is error");
            }
        }
        IPage<T> pagelist = null;
        pagelist = defaultDAO.page(new PageResult<T>(page,limit),spec);
        return pagelist;
    }

    public IPage<T> queryContion(Map<String,Object> searchParams){
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        int page=0;
        int limit=10;
        if(request.getParameter("page")==null || "".equals(request.getParameter("page"))){
            page=0;
        }else{
            page = Integer.parseInt(request.getParameter("page"))>1?
                    Integer.parseInt(request.getParameter("page")):1;

        }
        if(request.getParameter("limit")==null || "".equals(request.getParameter("limit"))){
            limit =10;
        }else{
            limit = Integer.parseInt(request.getParameter("limit"));
        }

        QueryWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);

        IPage<T> pagelist = null;
        pagelist = defaultDAO.page(new Page<T>(page,limit),spec);
        return pagelist;
    }


    /**
     * 没有分页的List多条件查询,带排序
     * @param searchParams
     * @return
     */
    public List<T> queryContionNoPage(Map<String,Object> searchParams,Sort sort){
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        QueryWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);

        if(StringUtils.isNotBlank(sort.getDirection())){
            if(sort.getDirection().equals("ASC")){
                spec.orderByAsc(Tools.StrArr2String(sort.getColNames()));

            } else if (sort.getDirection().equals("DESC")) {
                spec.orderByDesc(Tools.StrArr2String(sort.getColNames()));
            }else {
                throw new BaseException("sort parame is error",500);
            }
        }
        List<T> resultList = this.defaultDAO.list(spec);
        return resultList;
    }

    /**
     * 没有分页的List多条件查询
     * @param searchParams
     * @return
     */
    public List<T> queryContionNoPage(Map<String,Object> searchParams){
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        QueryWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);
        List<T> resultList = this.defaultDAO.list(spec);
        return resultList;
    }


    /**
     * 判断一个实体是否存在
     * @param colName 字段名称
     * @param colValue 字段的值
     * @return  false 表示数据不存在   true 表示存在
     */
    public boolean exists(String colName,String colValue){
        boolean resulut = false;
        QueryWrapper<T> spec = new QueryWrapper<T>();
        spec.eq(colName,colValue);
         int count = this.defaultDAO.count(spec);
        if(count <0){
            resulut = false;
        }else  if(count > 0) {
            resulut = true;

        }
        return resulut;
    }


    /**
     * 统一的向前端突出一个包装体，方便前端调用
     * @param o
     * @param message
     * @return
     */
    public Object jsonObjectResult(Object o,String message){
        if(o==null){
            return JSON.toJSON(new ObjectRestResponse().data("").message("未查询到数据！"));
        }else{
            return JSON.toJSON(new ObjectRestResponse().data(o).message(message).rel(true));
        }

    }






}
