package com.quality.delegate.controller;


import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.delegate.entity.QualityAssignmentStatement;
import com.quality.delegate.entity.QualityAssignmentStatementDto;
import com.quality.delegate.entity.QualityDelegateunit;
import com.quality.delegate.service.IQualityAssignmentStatementService;
import com.quality.delegate.service.IQualityDelegateunitService;
import com.quality.delegate.service.IQualitySampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2019-01-05
 */

@Api(value = "QualityAssignmentStatementController}", description = "委托协议书具体API")
@Controller
@RequestMapping("/QualityAssignmentStatement")
public class QualityAssignmentStatementController extends BaseController<QualityAssignmentStatement, IQualityAssignmentStatementService> {

    private final Logger logger = LoggerFactory.getLogger(QualityAssignmentStatementController.class);


    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityAssignmentStatement多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualityAssignmentStatementDto> queryConditionPage(HttpServletRequest request) {

        PageResult<QualityAssignmentStatementDto> QualityAssignmentStatemenDtotListPage = null;
        PageResult<QualityAssignmentStatement> QualityAssignmentStatementListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualityAssignmentStatementListPage = (PageResult<QualityAssignmentStatement>) queryContion(searchParams, sort);
            QualityAssignmentStatemenDtotListPage = this.defaultDAO.converQualityAssignmentStatementDto(QualityAssignmentStatementListPage);
            QualityAssignmentStatemenDtotListPage.setMsg("查询成功");
            return QualityAssignmentStatemenDtotListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }



    @ApiOperation(value = "增加/修改QualityAssignmentStatement信息",
            notes = "保存和修改QualityAssignmentStatement信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualityAssignmentStatement QualityAssignmentStatement) {
        boolean result = false;
        try {
            result = this.defaultDAO.saveOrUpdate(QualityAssignmentStatement);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualityAssignmentStatement信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityAssignmentStatementById(@ApiParam(value = "QualityAssignmentStatementID") @RequestParam(name = "entityID") String entityID) {
        boolean result = this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualityAssignmentStatement的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityAssignmentStatement唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityAssignmentStatement QualityAssignmentStatement = this.defaultDAO.getById(id);
            return super.jsonObjectResult(QualityAssignmentStatement, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "根据多个ID，批量删除")
    @RequestMapping(value = "/removeMore.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteByIds(@ApiParam(value = "多个元素ID") @RequestParam(name = "entityIDS") String entityIDS) {
        boolean result = false;
        try {
            String[] str = Tools.str2StrArray(entityIDS);
            List<String> strArr1 = Arrays.asList(str);
            result = this.defaultDAO.removeByIds(strArr1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("删除失败", 500);
        }
        return super.jsonObjectResult(result, "删除成功");

    }


    @RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getQualityAssignmentStatementList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityAssignmentStatement> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


}

