package com.quality.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.quality.common.controller.BaseController;
import com.quality.common.dto.JsonResult;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.util.MD5;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.system.entity.QualityUser;
import com.quality.system.service.IQualityUserService;
import io.swagger.annotations.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */

@Api(value = "QualityUserController}", description = "用户表具体API")
@Controller
@RequestMapping("/QualityUser")
public class QualityUserController extends BaseController<QualityUser, IQualityUserService> {

    private final Logger logger = LoggerFactory.getLogger(QualityUserController.class);

    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityUser多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualityUser> queryConditionPage(HttpServletRequest request) {
        PageResult<QualityUser> QualityUserListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualityUserListPage = (PageResult<QualityUser>) queryContion(searchParams, sort);
            QualityUserListPage.setMsg("查询成功");
            return QualityUserListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualityUser信息",
            notes = "保存和修改QualityUser信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualityUser qualityUser) {
        boolean result = false;
        try {
            if(!"".equals(qualityUser) || qualityUser!=null){

                //增加判断用户的逻辑

                //ID为空证明新增
                if("".equals(qualityUser.getId()) || qualityUser.getId()==null){
                   boolean result1 = this.defaultDAO.getExistUser(qualityUser.getLoginName());
                    //true标识已经存在
                    if(result1 == true){
                        throw new BaseException("用户已经存才,请更换登录名",500);
                    }else {
                        String password = qualityUser.getPassword();
                        if(!"".equals(password) && password!=null){
                            String pd = MD5.encryptPassword(password);
                            qualityUser.setPassword(pd);
                        }
                        //增加默认值1正常，用户状态(0:禁用，1:正常)
                       // qualityUser.setStatus("1");
                    }
                }else if(!"".equals(qualityUser.getId()) || qualityUser.getId()!=null){
                    //证明修改
                    String password = qualityUser.getPassword();
                    if(!"".equals(password) && password!=null){
                        if(password.length()<50 ){
                            String pd = MD5.encryptPassword(password);
                            qualityUser.setPassword(pd);
                            //由于资源卫星老用户的原因,
                            // 这里需要修改老用户的状态来表明被修改过
                           // qualityUser.setSatelliteType("0");
                        }
                    }
                }
            }
            result = this.defaultDAO.saveOrUpdate(qualityUser);
        }
        catch (BaseException e) {
            e.printStackTrace();
            throw new BaseException(e.getMessage(), 500);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualityUser信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityUserById(@ApiParam(value = "QualityUserID") @RequestParam(name = "entityID") String entityID) {
        if (entityID != null && !entityID.equals("")) {
            if (entityID.equals("1")) {
                throw new BaseException("超级管理员无法删除", 500);
            }
        }
        boolean result = this.defaultDAO.removeById(entityID);
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualityUser的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityUser唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityUser QualityUser = this.defaultDAO.getById(id);
            return super.jsonObjectResult(QualityUser, "查询成功");
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
            if(StringUtils.isNotBlank(entityIDS)){
                String[] str = Tools.str2StrArray(entityIDS);
                List<String> strArr1 = Arrays.asList(str);
                boolean reslut = strArr1.contains("1");
                if(reslut == true){
                    throw new BaseException("管理员不能被删除",500);
                }else{
                    result = this.defaultDAO.removeByIds(strArr1);
                }
            }
        return super.jsonObjectResult(result, "删除成功");

    }


    @RequestMapping(value = "/list.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object getQualityUserList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityUser> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @RequestMapping("/test")
    @ResponseBody
    public Object testRole() {
        System.out.println("i'm coning......");
        return super.jsonObjectResult("", "查询成功");
    }


    @PreAuthorize("@permissionValidator.hasSomePermission(#request)")
    @RequestMapping("/testTwo")
    @ResponseBody
    public Object testPerimission(HttpServletRequest request) {
        System.out.println("i'm coning......");
        return super.jsonObjectResult("", "查询成功");
    }

    @ApiOperation(value = "获取个人信息")
    @RequestMapping(value = "/userInfo.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public JsonResult userInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object object = authentication.getPrincipal();
            if (object != null) {
                return JsonResult.ok().put("user", (QualityUser) object);
            }
        }
        return null;

    }

    @ApiOperation(value = "根据ID修改密码")
    @RequestMapping(value = "/updatepwd.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object modifyPwd(@RequestParam("userId") String userId,
                             @RequestParam("newPsw") String newPsw,
                             @RequestParam("oldPsw") String oldPsw) {

        boolean result = false;
        try {
            result = this.CompliePass(userId, oldPsw);
            if(result == false){
                throw new BaseException("原始密码错误", 500);
            }else{
                QualityUser user = new QualityUser();
                user.setId(userId);
                if(StringUtils.isNotBlank(newPsw)){
                    String pd = MD5.encryptPassword(newPsw);
                    user.setPassword(pd);
                }
                result = this.defaultDAO.saveOrUpdate(user);
            }

        }
        catch (BaseException e){
            e.printStackTrace();
            throw new BaseException(e.getMessage(), 500);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("修改密码错误", 500);
        }

        return super.jsonObjectResult(result, "修改密码成功");
    }


    public boolean CompliePass(@RequestParam(name = "userId") String userId,
                              @RequestParam(name = "oldPass") String oldPass) {
        try {
            boolean result = this.defaultDAO.compliePass(userId, oldPass);
            String message = "";
            if(result==false){
                message = "原始密码不正确！";
            }else{
                message = "";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("原始密码错误", 500);
        }
    }

    @ApiOperation(value = "根据请求清除session")
    @RequestMapping(value = "/me.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void cleanSession(){
            throw new BaseException("我是自定异常的抛出", 500);
    }


    @ApiOperation(value = "根据ID重置密码")
    @RequestMapping(value = "/resetPD.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object resetPD(@RequestParam("userId") String userId,
                            @RequestParam("newPsw") String newPsw) {

        boolean result = false;
        try {
                QualityUser user = new QualityUser();
                user.setId(userId);
                if(StringUtils.isNotBlank(newPsw)){
                    String pd = MD5.encryptPassword(newPsw);
                    user.setPassword(pd);
                }
                result = this.defaultDAO.saveOrUpdate(user);

        }
        catch (BaseException e){
            e.printStackTrace();
            throw new BaseException(e.getMessage(), 500);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("重置密码失败", 500);
        }

        return super.jsonObjectResult(true, "重置密码成功！");
    }

}

