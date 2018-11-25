package com.quality.system.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quality.common.dto.JsonResult;
import com.quality.common.dto.PageResult;
import com.quality.common.exception.BaseException;
import com.quality.common.util.Servlets;
import com.quality.common.util.Sort;
import com.quality.common.util.Tools;
import com.quality.common.controller.BaseController;
import com.quality.system.entity.MenuDto;
import com.quality.system.entity.QualityMenu;
import com.quality.system.service.IQualityMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @since 2018-11-21
 */

@Api(value = "QualityMenuController}", description = "菜单表具体API")
@Controller
@RequestMapping("/QualityMenu")
public class QualityMenuController extends BaseController<QualityMenu, IQualityMenuService> {

    private final Logger logger = LoggerFactory.getLogger(QualityMenuController.class);

    /**
     * 带分页的查询条件
     *
     * @return
     */
    @ApiOperation(value = "QualityMenu多条件查询", notes = "多条件查询")
    @RequestMapping(value = "/query.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public PageResult<QualityMenu> queryConditionPage(HttpServletRequest request) {

        PageResult<QualityMenu> QualityMenuListPage = null;
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            //如果需要按多个字段排序，请传多个参数,为了反射方便，数据库不使用下划线了
            Sort sort = new Sort(Sort.DESC, Tools.str2StrArray("crtTime"));
            QualityMenuListPage = (PageResult<QualityMenu>) queryContion(searchParams, sort);
            QualityMenuListPage.setMsg("查询成功");
            return QualityMenuListPage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

    @ApiOperation(value = "增加/修改QualityMenu信息",
            notes = "保存和修改QualityMenu信息")
    @RequestMapping(value = "/save.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object saveOrUpdate(@RequestBody QualityMenu QualityMenu) {
        boolean result = false;
        try {
            Integer parentId =  QualityMenu.getParentId();
            if (parentId == 0){
                QualityMenu.setParentIds(parentId+"");
            }else{
                QualityMenu  pMenu = this.defaultDAO.getById(parentId);
                QualityMenu.setParentIds(pMenu.getParentIds().concat("-").concat(parentId+""));
            }
            result = this.defaultDAO.saveOrUpdate(QualityMenu);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("保存失败", 500);
        }
        return super.jsonObjectResult(result, "保存成功");
    }

    @ApiOperation(value = "根据Id删除QualityMenu信息")
    @RequestMapping(value = "/removeById.do", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object deleteQualityMenuById(@ApiParam(value = "QualityMenuID") @RequestParam(name = "entityID") Integer entityID) {
        boolean result =false;
        int count = this.defaultDAO.selectByMenuId(entityID);
        if(count>0){
            return JsonResult.error("解除相关的引用");
        }else{
            //删除的ID 是否为父ID
            List<QualityMenu>  pMenuList = this.defaultDAO.selectListByParentId(entityID);
            if(pMenuList.size() > 0){
                List<Integer> ids = new ArrayList<>();
                pMenuList.forEach(item->{
                    ids.add(item.getId());
                });
                result = this.defaultDAO.removeByIds(ids);
            }

            result = this.defaultDAO.removeById(entityID);
            return super.jsonObjectResult(result, "删除成功");
        }

    }


    @ApiOperation(value = "根据ID获取QualityMenu的基本信息")
    @RequestMapping(value = "/queryById.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Object queryById(@ApiParam(value = "QualityMenu唯一标识") @RequestParam(name = "id") String id) {
        try {
            QualityMenu QualityMenu = this.defaultDAO.getById(id);
            return super.jsonObjectResult(QualityMenu, "查询成功");
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
    public Object getQualityMenuList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityMenu> list = super.queryContionNoPage(searchParams);
            return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


    @ApiOperation(value = "查询所有菜单")
    @RequestMapping(value = "/getQualityMenuTree.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getQualityMenuTreeList(HttpServletRequest request) {
        try {
            //把查询条件都写好了
           // Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
           // List<QualityMenu> list = super.queryContionNoPage(searchParams);
            List<String> MenuTypes = new ArrayList<String>(){{add("0"); add("1");}};
            List<QualityMenu>  list = this.defaultDAO.selectListByMenuType(MenuTypes);
            List<MenuDto> tree = new ArrayList<MenuDto>();
            list.forEach(item->{
                MenuDto dto = new MenuDto();
                dto.setPid(item.getParentId());
                dto.setId(item.getId());
                dto.setUrl(item.getMenuUrl());
                dto.setName(item.getMenuName());
                dto.setIcon(item.getMenuIcon());
                dto.setPath(item.getPath());
                tree.add(dto);
            });

            List<MenuDto>  treeList = listToTree(tree);
            return  JSON.toJSONString(treeList).toString();
            //return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }


    public  List<MenuDto> listToTree(List<MenuDto> list) {
        //用递归找子。
        List<MenuDto> treeList = new ArrayList<MenuDto>();
        for (MenuDto tree : list) {
            if (tree.getPid()==0) {

                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }

    private  MenuDto findChildren(MenuDto tree, List<MenuDto> list) {
        for (MenuDto node : list) {
            if (node.getPid() == tree.getId()) {
                if (tree.getSubMenus() == null) {
                    tree.setSubMenus(new ArrayList<MenuDto>());
                }
                tree.getSubMenus().add(findChildren(node, list));
            }
        }
        return tree;
    }


    @ApiOperation(value = "查询所有菜单")
    @RequestMapping(value = "/getQualityAllMenuList.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getQualityAllMenuList(HttpServletRequest request) {
        try {
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search-");
            List<QualityMenu> list = super.queryContionNoPage(searchParams);

            List<MenuDto> tree = new ArrayList<MenuDto>();
            list.forEach(item->{
                MenuDto dto = new MenuDto();
                dto.setPid(item.getParentId());
                dto.setId(item.getId());
                dto.setName(item.getMenuName());
                tree.add(dto);
            });

            return  JSON.toJSONString(tree).toString();
            //return super.jsonObjectResult(list, "查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException("查询失败", 500);
        }
    }

}

