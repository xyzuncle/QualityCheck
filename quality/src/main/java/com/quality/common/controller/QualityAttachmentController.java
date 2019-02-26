package com.quality.common.controller;

import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.common.entity.QualityAttachment;
import com.quality.common.service.IQualityAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 通用的附件表
 * </p>
 *
 * @author yerui
 * @since 2019-02-22
 */

@Api(value = "QualityAttachmentController}", description = "具体API")
@Controller
@RequestMapping("/QualityAttachment")
public class QualityAttachmentController extends BaseController<QualityAttachment, IQualityAttachmentService> {

    private final Logger logger = LoggerFactory.getLogger(QualityAttachmentController.class);
    private FastDFSClient fastDFSClient = new FastDFSClient();
    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityAttachment多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult queryConditionPage(HttpServletRequest request) {
        PageResult<QualityAttachment> QualityAttachmentListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualityAttachmentListPage = (PageResult<QualityAttachment>) queryContion(searchParams, sort);
            QualityAttachmentListPage.setMsg("查询成功");
            return QualityAttachmentListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualityAttachment信息",
            notes = "保存和修改QualityAttachment信息")
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public Object saveOrUpdate(MultipartFile file, HttpServletRequest request,
                               @RequestParam("attid") String attid) {
        String id = "";
        try {

             QualityAttachment qualityAttachment = new QualityAttachment();
             if(StringUtils.isNotBlank(attid)){
                 qualityAttachment.setId(attid);
             }
             id = this.defaultDAO.custumSave(qualityAttachment,file,request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(id, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualityAttachment信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityAttachmentById(@ApiParam(value = "QualityAttachmentID") @RequestParam(name = "entityID") String entityID) {
        boolean result = this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualityAttachment的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityAttachment唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityAttachment qualityAttachment = this.defaultDAO.getById(id);
            return super.jsonObjectResult(qualityAttachment, "查询成功");
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
    public Object getQualityAttachmentList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityAttachment> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


    /**
     * 点击下载成文件
     */
    @RequestMapping(value = "/download/file.do", method = RequestMethod.GET)
    public void downFileById(String attachmentId, HttpServletResponse response){
        try {
            QualityAttachment qualityAttachment = this.defaultDAO.getById(attachmentId);
            if(attachmentId!=null){
                String path = qualityAttachment.getPath();
                fastDFSClient.downloadFile(path, response);
            }

        } catch (BaseException e) {
            e.printStackTrace();
            throw e;
        }
    }





    /**
     * 形成预览图
     * @return
     */
    @RequestMapping(value = "/download/image.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void downImage(String attachmentId, HttpServletResponse response){
        try {

            QualityAttachment qualityAttachment = this.defaultDAO.getById(attachmentId);

            if(attachmentId!=null){
                String fileDir = "fstdfssm/";
                String filePath = qualityAttachment.getSmpath();
                outputFile(fileDir+ filePath, response);
                //fastDFSClient.downloadFile(filePath, response.getOutputStream());
            }

        } catch (BaseException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 输出文件流
    private void outputFile(String file, HttpServletResponse response) {
        // 判断文件是否存在
        File inFile = new File(File.listRoots()[0], file);
        if (!inFile.exists()) {
            PrintWriter writer = null;
            try {
                response.setContentType("text/html;charset=UTF-8");
                writer = response.getWriter();
                writer.write("<!doctype html><title>404 Not Found</title><h1 style=\"text-align: center\">404 Not Found</h1><hr/><p style=\"text-align: center\">Easy File Server</p>");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // 获取文件类型
        String contentType = null;
        try {
            // Path path = Paths.get(inFile.getName());
            // contentType = Files.probeContentType(path);
            contentType = new Tika().detect(inFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentType != null) {
            response.setContentType(contentType);
        } else {
            response.setContentType("application/force-download");
            String newName;
            try {
                newName = URLEncoder.encode(inFile.getName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                newName = inFile.getName();
            }
            response.setHeader("Content-Disposition", "attachment;fileName=" + newName);
        }
        // 输出文件流
        OutputStream os = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(inFile);
            os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

