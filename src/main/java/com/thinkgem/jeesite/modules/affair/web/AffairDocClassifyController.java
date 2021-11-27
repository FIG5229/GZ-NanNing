/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassifyRange;
import com.thinkgem.jeesite.modules.affair.service.AffairDocClassifyRangeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassify;
import com.thinkgem.jeesite.modules.affair.service.AffairDocClassifyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文档分类Controller
 *
 * @author kevin.jia
 * @version 2020-07-29
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairDocClassify")
public class AffairDocClassifyController extends BaseController {

    @Autowired
    private AffairDocClassifyService affairDocClassifyService;

    @Autowired
    private AffairDocClassifyRangeService affairDocClassifyRangeService;

    Map<String, String> tempIdMap = new HashMap<>();
    String tempId = "";

    @ModelAttribute
    public AffairDocClassify get(@RequestParam(required = false) String id) {
        AffairDocClassify entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = affairDocClassifyService.get(id);
        }
        if (entity == null) {
            entity = new AffairDocClassify();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = {""})
    public String index() {
        return "modules/affair/affairDocClassifyIndex";
    }

    @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = {"list"})
    public String list(AffairDocClassify affairDocClassify, HttpServletRequest request, HttpServletResponse response, Model model) {
        AffairDocClassify paramObj;
        model.addAttribute("treeId", affairDocClassify.getTreeId());
        if (request.getParameter("isTree") != null) {
            paramObj = new AffairDocClassify();
            paramObj.setTreeId(affairDocClassify.getTreeId());
        } else {
            paramObj = affairDocClassify;
        }
        Page<AffairDocClassify> page = affairDocClassifyService.findPage(new Page<AffairDocClassify>(request, response), paramObj);
        model.addAttribute("page", page);
        return "modules/affair/affairDocClassifyList";
    }

    @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = "form")
    public String form(AffairDocClassify affairDocClassify, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (affairDocClassify.getTempId() != null && affairDocClassify.getTempId().length() != 0) {
            tempId = affairDocClassify.getTempId();
        }else if(tempId != null && tempId.length() != 0 && affairDocClassifyService.getByTempId(tempId) == null){
            tempId = tempId;
        }else {
            tempId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        model.addAttribute("affairDocClassify", affairDocClassify);
        List<AffairDocClassify> affairDocClassifyList = affairDocClassifyService.findTreeData();
        model.addAttribute("affairDocClassifyList", affairDocClassifyList);
        model.addAttribute("tempId", tempId);
        //添加/修改页面，查询使用范围设置表
        AffairDocClassifyRange affairDocClassifyRange = new AffairDocClassifyRange();
        affairDocClassifyRange.setClassifyId(affairDocClassify.getId());
        affairDocClassifyRange.setTempId(tempId);
        Page<AffairDocClassifyRange> page = affairDocClassifyRangeService.findPage(new Page<AffairDocClassifyRange>(request, response), affairDocClassifyRange);
        model.addAttribute("page", page);
        return "modules/affair/affairDocClassifyForm";
    }

    @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = "formDetail")
    public String formDetail(AffairDocClassify affairDocClassify, Model model) {
        model.addAttribute("affairDocClassify", affairDocClassify);
        return "modules/affair/affairDocClassifyFormDetail";
    }

    @RequiresPermissions("affair:affairDocClassify:edit")
    @RequestMapping(value = "save")
    public String save(AffairDocClassify affairDocClassify, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        if (!beanValidator(model, affairDocClassify)) {
            return form(affairDocClassify, model, request, response);
        }
        affairDocClassifyService.save(affairDocClassify);
        affairDocClassifyRangeService.updateClassifyId(affairDocClassify.getTempId());
        addMessage(redirectAttributes, "保存文档分类成功");
        request.setAttribute("saveResult", "success");
        return "modules/affair/affairDocClassifyForm";
    }

    @RequiresPermissions("affair:affairDocClassify:edit")
    @RequestMapping(value = "delete")
    public String delete(AffairDocClassify affairDocClassify, RedirectAttributes redirectAttributes) {
        affairDocClassifyService.delete(affairDocClassify);
        addMessage(redirectAttributes, "删除文档分类成功");
        return "redirect:" + Global.getAdminPath() + "/affair/affairDocClassify/list?repage";
    }

    /**
     * 获得文档分类树形选择器 未放入缓存中
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(HttpServletRequest request) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<AffairDocClassify> list = affairDocClassifyService.findTreeData();
        if (list.isEmpty()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "");
            map.put("pId", "");
            map.put("name", "");
            mapList.add(map);
            if ("tym".equals(request.getParameter("flag"))) {
                map.put("isParent", true);
            }
            return mapList;
        }
        for (int i = 0; i < list.size(); i++) {
            AffairDocClassify b = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", b.getId());
            map.put("pId", b.getParentId());
            map.put("name", b.getClassifyName());
            mapList.add(map);
            if ("tym".equals(request.getParameter("flag"))) {
                map.put("isParent", true);
            }
        }
        return mapList;
    }

    @ResponseBody
    @RequiresPermissions("affair:affairDocClassify:edit")
    @RequestMapping(value = {"deleteByIds"})
    public Result deleteByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairDocClassifyRangeService.deleteByClassifyIds(ids);
            affairDocClassifyService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;

    }

    /*
     * 根据id查找该分类的parentIds
     *
     * */
    @RequestMapping(value = {"getParentIdsById"})
    @ResponseBody
    public Result getParentIdsById(String id) {
        AffairDocClassify affairDocClassify = affairDocClassifyService.getParentIdsById(id);
        Result result = new Result();
        if (affairDocClassify != null) {
            result.setResult(affairDocClassify);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /*
     * 展示使用范围设置页面
     * 弃用
     * */
   /* @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = {"usableRangeList"})
    public String usableRangeList(AffairDocClassify affairDocClassify, Model model, HttpServletRequest request, HttpServletResponse response) {
        AffairDocClassifyRange affairDocClassifyRange = new AffairDocClassifyRange();
        model.addAttribute("affairDocClassify", affairDocClassify);
        affairDocClassifyRange.setClassifyId(affairDocClassify.getId());
        Page<AffairDocClassifyRange> page = affairDocClassifyRangeService.findPage(new Page<AffairDocClassifyRange>(request, response), affairDocClassifyRange);
        model.addAttribute("tempId", tempId);
        model.addAttribute("page", page);
        return "modules/affair/affairDocClassifyUsableRangeList";
    }*/


    /*
     * 使用范围设置
     * 添加部门/人员
     * */
    @RequiresPermissions("affair:affairDocClassify:edit")
    @RequestMapping(value = {"saveUsableRangeList"})
    @ResponseBody
    public Result saveUsableRangeList(AffairDocClassifyRange affairDocClassifyRange, Model model, HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            affairDocClassifyRangeService.save(affairDocClassifyRange);
            Page<AffairDocClassifyRange> page = affairDocClassifyRangeService.findPage(new Page<AffairDocClassifyRange>(request, response), affairDocClassifyRange);
            model.addAttribute("page", page);
            result.setResult(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;

    }

    @RequiresPermissions("affair:affairDocClassify:edit")
    @RequestMapping(value = {"deleteUsableRangeByIds"})
    @ResponseBody
    public Result deleteUsableRangeByIds(@RequestParam("ids[]") List<String> ids) {
        Result result = new Result();
        if (ids != null && ids.size() > 0) {
            affairDocClassifyRangeService.deleteByIds(ids);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } else {
            result.setSuccess(false);
            result.setMessage("请先选择要删除的内容");
        }
        return result;

    }

    @RequiresPermissions("affair:affairDocClassify:view")
    @RequestMapping(value = {"changePage"})
    @ResponseBody
    public Result changePage(Model model, String pageNo, String pageSize, String tempId, AffairDocClassifyRange affairDocClassifyRange) {
        Result result = new Result();
        affairDocClassifyRange.setTempId(tempId);
        Page<AffairDocClassifyRange> page = affairDocClassifyRangeService.findPage(new Page<AffairDocClassifyRange>(Integer.valueOf(pageNo), Integer.valueOf(pageSize)), affairDocClassifyRange);
        result.setSuccess(true);
        model.addAttribute("page", page);
        result.setResult(page);
        return result;
    }
}