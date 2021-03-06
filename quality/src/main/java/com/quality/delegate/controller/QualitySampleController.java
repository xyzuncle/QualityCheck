package com.quality.delegate.controller;


import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.service.ICommonTicketService;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.delegate.entity.QualityDelegateunit;
import com.quality.delegate.entity.QualitySample;
import com.quality.delegate.entity.QualitySampleAbility;
import com.quality.delegate.service.IQualityDelegateunitService;
import com.quality.delegate.service.IQualitySampleAbilityService;
import com.quality.delegate.service.IQualitySampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
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

@Api(value = "QualitySampleController}", description = "样机信息具体API")
@Controller
@RequestMapping("/QualitySample")
public class QualitySampleController extends BaseController<QualitySample, IQualitySampleService> {

    private final Logger logger = LoggerFactory.getLogger(QualitySampleController.class);

    @Autowired
    private ICommonTicketService commonTicketService;

    @Autowired
    private IQualityDelegateunitService delegateunitService;

    @Autowired
    private IQualitySampleAbilityService sampleAbilityService;


    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualitySample多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualitySample> queryConditionPage(HttpServletRequest request) {

        PageResult<QualitySample> QualitySampleListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualitySampleListPage = (PageResult<QualitySample>) queryContion(searchParams, sort);
            if(QualitySampleListPage!=null){
                List<QualitySample> list = QualitySampleListPage.getData();
                for(int i=0;i<list.size();i++){
                    QualitySample qualitySample = list.get(i);
                    List<QualitySampleAbility> checkAbilityList = sampleAbilityService.queryBySampleId(qualitySample.getId());
                    List<String> checkAbilityIds = new ArrayList<>();
                    if(checkAbilityList!=null &&checkAbilityList.size()>0){
                        checkAbilityList.forEach(item->{
                            checkAbilityIds.add(item.getCheckAbilityId());
                        });
                        qualitySample.setCheckAbilityId(Tools.listToString(checkAbilityIds));
                    }

                }
            }
            QualitySampleListPage.setMsg("查询成功");
            return QualitySampleListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualitySample信息",
            notes = "保存和修改QualitySample信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualitySample qualitySample) {
        boolean result = false;
        try {

            String id = qualitySample.getId();
            String checkAbilityId = qualitySample.getCheckAbilityId();
            String[] checkAbilityIds = Tools.str2StrArray(checkAbilityId);

            if(StringUtils.isBlank(id)){
                String unitId = qualitySample.getDelegateUnitID();
                if(StringUtils.isNotBlank(unitId)){
                    QualityDelegateunit delegateunit = delegateunitService.getById(unitId);
                    String unitCode = delegateunit.getUnitCode();
                    String sampleCode = commonTicketService.selectSampleCode(unitCode);
                    qualitySample.setSampleCode(sampleCode);
                }
            }
            result = this.defaultDAO.saveOrUpdate(qualitySample);
            //TODO 更新关系
            if(result){
                //先删除关系
                sampleAbilityService.deleteBySampleId(id);
                for(int i=0;i<checkAbilityIds.length;i++){
                    //在插入关系
                    QualitySampleAbility sampleAbility = new QualitySampleAbility();
                    sampleAbility.setSampleId(id);
                    sampleAbility.setCheckAbilityId(checkAbilityIds[i]);
                    result =  sampleAbilityService.save(sampleAbility);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualitySample信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualitySampleById(@ApiParam(value = "QualitySampleID") @RequestParam(name = "entityID") String entityID) {
        boolean result = this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualitySample的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualitySample唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualitySample qualitySample = this.defaultDAO.getById(id);
            List<QualitySampleAbility> checkAbilityList = sampleAbilityService.queryBySampleId(id);
            List<String> checkAbilityIds = new ArrayList<>();
            if(checkAbilityList!=null &&checkAbilityList.size()>0){
                checkAbilityList.forEach(item->{
                    checkAbilityIds.add(item.getCheckAbilityId());
                });
            }
            qualitySample.setCheckAbilityId(Tools.listToString(checkAbilityIds));
            return super.jsonObjectResult(qualitySample, "查询成功");
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
    public PageResult<QualitySample> getQualitySampleList(HttpServletRequest request) {

        PageResult<QualitySample> QualitySampleListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualitySampleListPage = (PageResult<QualitySample>) queryContion(searchParams, sort);
            QualitySampleListPage.setMsg("查询成功");
            return QualitySampleListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @RequestMapping(value = "/exists.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object exists(HttpServletRequest request,String sampleCode) {

        try {
            boolean flag =  super.exists("sampleCode",sampleCode);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

}

