package com.quality.delegate.controller;

import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.delegate.dto.QualityReportDto;
import com.quality.delegate.entity.QualityReport;
import com.quality.delegate.service.IQualityReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2019-03-02
 */

@Api(value = "QualityReportController}", description = "具体API")
@Controller
@RequestMapping("/QualityReport")
public class QualityReportController extends BaseController<QualityReport, IQualityReportService> {

    private final Logger logger = LoggerFactory.getLogger(QualityReportController.class);

    private static final String REPORT_NAME = "生成报告.docx";

    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityStandard多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualityReport> queryConditionPage(HttpServletRequest request) {

        PageResult<QualityReportDto> qualityReportDtoPage = null;
        PageResult<QualityReport> qualityReportPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            qualityReportPage = (PageResult<QualityReport>) queryContion(searchParams, sort);

           // qualityReportDtoPage = this.defaultDAO.converQualityReportDto(qualityReportPage);
           // qualityReportDtoPage.setMsg("查询成功");
            return qualityReportPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualityReport信息",
            notes = "保存和修改QualityReport信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualityReport QualityReport, HttpServletRequest request,
                               HttpServletResponse response) {
        boolean result = false;
        try {
            result = this.defaultDAO.saveOrUpdate(QualityReport);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "增加/修改计算模板信息",
            notes = "增加/修改计算模板信息")
    @RequestMapping(value = "/calculate.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object uploadCalculateTemplate(@RequestBody QualityReport QualityReport, HttpServletRequest request,
                               HttpServletResponse response) {
        boolean result = false;
        try {
            result = this.defaultDAO.customSaveReport(QualityReport
                   ,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }





    @ApiOperation(value = "根据Id删除QualityReport信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityReportById(@ApiParam(value = "QualityReportID") @RequestParam(name = "entityID") String entityID) {
        boolean result = this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualityReport的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityReport唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityReport QualityReport = this.defaultDAO.getById(id);
            return super.jsonObjectResult(QualityReport, "查询成功");
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
    public Object getQualityReportList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityReport> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


    @RequestMapping(value = "/genReport.do", method = RequestMethod.GET)
    @ResponseBody
    public void genReport(@RequestParam("reportId") String reportId, HttpServletRequest request,
                               HttpServletResponse response) {
        boolean result = false;
        FileInputStream in = null;
        OutputStream os = null;
        ByteArrayOutputStream baos = null;
        try {
            String encoderName = URLEncoder.encode(REPORT_NAME, "UTF-8").replace("+", "%20").replace("%2B", "+");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + encoderName + "\"");
            response.setContentType("application/octet-stream" + ";charset=UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            XWPFDocument doc = this.defaultDAO.genReport(reportId,request,response);
            os = response.getOutputStream();
            doc.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        finally {
            // 关闭流
            try {
                if(in != null){
                    in.close();
                }
                if(os != null){
                    os.close();
                }if(baos!=null){
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}

