package com.quality.system.controller;


import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.system.entity.QualityRole;
import com.quality.system.entity.QualityUser;
import com.quality.system.entity.RoleMenu;
import com.quality.system.service.IQualityRoleService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yerui
 * @since 2018-11-15
 */

@Api(value = "QualityRoleController}", description = "角色表具体API")
@Controller
@RequestMapping("/QualityRole")
public class QualityRoleController extends BaseController<QualityRole, IQualityRoleService> {

    private final Logger logger = LoggerFactory.getLogger(QualityRoleController.class);

    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityRole多条件查询", notes = "多条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
    })
    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualityRole> queryConditionPage(Integer page, Integer limit, HttpServletRequest request) {
        PageResult<QualityRole> QualityUserListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualityUserListPage = (PageResult<QualityRole>) queryContion(searchParams, sort);
            QualityUserListPage.setMsg("查询成功");
            QualityUserListPage.setCode(200);
            return QualityUserListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualityRole信息",
            notes = "保存和修改QualityRole信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualityRole QualityRole, HttpServletRequest request) {
        boolean result = false;
        try {
            List<RoleMenu> list = new ArrayList<>();
            List<Integer> menuids = QualityRole.getMenuids();
            String roleId = QualityRole.getId();
            if(StringUtils.isBlank(roleId)){
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                QualityRole.setId(uuid);
                menuids.forEach(menu->{
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(menu);
                    roleMenu.setRoleID(uuid);
                    list.add(roleMenu);
                });

                result = this.defaultDAO.save(QualityRole);

            }else{

                menuids.forEach(menu->{
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(menu);
                    roleMenu.setRoleID(roleId);
                    list.add(roleMenu);
                });
                result = this.defaultDAO.updateById(QualityRole);

                List<Integer>  mids= this.defaultDAO.selectByRoleId(roleId);
                if(mids.size()>0){
                    this.defaultDAO.deleteByRoleId(roleId);
                }
            }

            this.defaultDAO.insertRoleMenu(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualityRole信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityRoleById(@ApiParam(value = "QualityRoleID") @RequestParam(name = "entityID") String entityID) {
        boolean result = this.defaultDAO.removeById(entityID);
        //删除关联表信息
        if(result){
            result = SqlHelper.delBool(this.defaultDAO.deleteByRoleId(entityID));
        }
        return super.jsonObjectResult(result, "删除成功");
    }


    @ApiOperation(value = "根据ID获取QualityRole的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityRole唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityRole QualityRole = this.defaultDAO.getById(id);
            List<Integer> mids = this.defaultDAO.selectByRoleId(id);
            QualityRole.setMenuids(mids);
            return super.jsonObjectResult(QualityRole, "查询成功");
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
    public Object getQualityRoleList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityRole> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


}

