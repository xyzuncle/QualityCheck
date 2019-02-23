package com.quality.store.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.fastdfs.FastDFSClient;
import com.quality.common.util.CustomerBarcodeUtil;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.delegate.entity.QualitySample;
import com.quality.store.entity.QualityBarcode;
import com.quality.store.service.IQualityBarcodeService;
import com.quality.store.service.impl.QualityFastdfsIndexServiceImpl;
import com.quality.system.entity.QualityUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *   条形码控制类
 * </p>
 *
 * @author yerui
 * @since 2019-01-23
 */

@Api(value = "QualityBarcodeController}",description = "条形码样品关系表具体API")
@Controller
@RequestMapping("/QualityBarcode")
public class QualityBarcodeController extends BaseController<QualityBarcode,IQualityBarcodeService>{

private final Logger logger=LoggerFactory.getLogger(QualityBarcodeController.class);



/**
*
* 带分页的查询条件
* @return
*/
@ApiOperation(value = "QualityBarcode多条件查询", notes = "多条件查询")
@RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public PageResult queryConditionPage(HttpServletRequest request){
    PageResult<QualityBarcode> QualityBarcodeListPage=null;
    try{
        //把查询条件都写好了
        Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
        //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
        Sort sort = new Sort(Sort.DESC,Tools.str2StrArray("crtTime") );
        QualityBarcodeListPage=(PageResult<QualityBarcode>) queryContion(searchParams,sort);
        QualityBarcodeListPage.setMsg("查询成功");
        return QualityBarcodeListPage;
    }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("查询失败",500);
    }
}

@ApiOperation(value = "增加/修改QualityBarcode信息",
        notes = "保存和修改QualityBarcode信息")
@RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object saveOrUpdate(@RequestBody QualityBarcode QualityBarcode){
    boolean result=false;
        try{
            //这里需要做处理，看看是否可以直接把流直接放到fastdfs上
            result=this.defaultDAO.SaveBarCodeAndImg(QualityBarcode);
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException("保存失败",500);
        }
            return super.jsonObjectResult(result,"保存成功");
    }

@ApiOperation(value = "根据Id删除QualityBarcode信息")
@RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object deleteQualityBarcodeById(@ApiParam(value = "QualityBarcodeID") @RequestParam(name = "entityID")  String entityID){
        boolean result=this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result,"删除成功");
}


@ApiOperation(value = "根据ID获取QualityBarcode的基本信息")
@RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object queryById(@ApiParam(value = "QualityBarcode唯一标识") @RequestParam(name = "id") String id){
    try{
            QualityBarcode QualityBarcode =this.defaultDAO.getById(id);
            return super.jsonObjectResult(QualityBarcode,"查询成功");
        }catch(Exception e){
            e.printStackTrace();
            throw new BaseException("查询失败",500);
        }
}

@ApiOperation(value = "根据多个ID，批量删除")
@RequestMapping(value = "/removeMore.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object deleteByIds(@ApiParam(value = "多个元素ID") @RequestParam(name = "entityIDS") String entityIDS){
    boolean result=false;
    try{
        String[]str=Tools.str2StrArray(entityIDS);
        List<String> strArr1=Arrays.asList(str);
        result=this.defaultDAO.removeByIds(strArr1);
    }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("删除失败",500);
    }
    return super.jsonObjectResult(result,"删除成功");

}


@RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@ResponseBody
public Object getQualityBarcodeList(HttpServletRequest request){
    try{
    //把查询条件都写好了
        Map<String, Object> searchParams=Servlets.getParametersStartingWith(request,"search-");
        List<QualityBarcode> list=super.queryContionNoPage(searchParams);
        return super.jsonObjectResult(list,"查询成功");
    }catch(Exception e){
        e.printStackTrace();
        throw new BaseException("查询失败",500);
    }
}

    /**
     * 判断条形码是否唯一
     * @return true 存在
     *         false 不存在
     */
    @RequestMapping(value = "/isExist.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public boolean isExistBarCode(String barCode){
        QueryWrapper<QualityBarcode> qw = new QueryWrapper<>();
        qw.eq("barCode", StringUtils.trimToEmpty(barCode));
        QualityBarcode qualityBarcode =  this.defaultDAO.getOne(qw);
        if(qualityBarcode!=null){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getInfoByBarCode.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getSampleInfoByBarCode(@RequestParam("barcode") String barcode){
        QualitySample qs = this.defaultDAO.getInfoByBarCode(barcode);
        if(qs==null){
             throw new BaseException("条形码不存在",500);
        }else{
            return super.jsonObjectResult(qs,"查询成功");
        }

    }



}

