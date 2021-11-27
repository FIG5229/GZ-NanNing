package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairQj;
import com.thinkgem.jeesite.modules.affair.entity.AffairQjSum;
import com.thinkgem.jeesite.modules.affair.service.AffairQjService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请假信息汇总Controller
 * @author mason.xv
 * @version 2019-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/affair/affairQjSum")
public class AffairQjSumController extends BaseController {

    @Autowired
    private AffairQjService affairQjService;

    @ModelAttribute
    public AffairQj get(@RequestParam(required=false) String id) {
        AffairQj entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = affairQjService.get(id);
        }
        if (entity == null){
            entity = new AffairQj();
        }
        return entity;
    }

    @RequiresPermissions("affair:affairQj:view")
    @RequestMapping(value = {"list", ""})
    public String list(AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, Model model) {
        if("1".equals(request.getParameter("modelType"))){
            affairQj.setType("1");
        }else if ("2".equals(request.getParameter("modelType"))){
            affairQj.setType("2");

        }else if ("3".equals(request.getParameter("modelType"))){
            affairQj.setType("3");

        }else if ("4".equals(request.getParameter("modelType"))){
            affairQj.setType("4");

        }else if ("5".equals(request.getParameter("modelType"))){
            affairQj.setType("5");

        }else if ("6".equals(request.getParameter("modelType"))){
            affairQj.setType("6");

        }else if ("7".equals(request.getParameter("modelType"))){
            affairQj.setType("7");

        }else if ("8".equals(request.getParameter("modelType"))){
            affairQj.setType("8");

        }else if ("9".equals(request.getParameter("modelType"))){
            affairQj.setType("9");
        }else if ("10".equals(request.getParameter("modelType"))){
            affairQj.setType("10");

        }else if ("11".equals(request.getParameter("modelType"))){
            affairQj.setType("11");
        }
        affairQj.setUnitId(request.getParameter("unitId"));
        Page<AffairQj> page = affairQjService.findPage(new Page<AffairQj>(request, response,-1), affairQj);
        model.addAttribute("page", page);
        return "modules/affair/affairQjSum";
    }

    @RequiresPermissions("affair:affairQj:view")
    @RequestMapping(value = {"statistic"})
    public String statistic(AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<AffairQjSum> statistic = affairQjService.statistic(affairQj);
        model.addAttribute("statistic", statistic);
        return "modules/affair/affairQjSum1";
    }

    @RequiresPermissions("affair:affairQj:view")
    @RequestMapping(value = {"findByUnitId"})
    public String findByUnitId(AffairQjSum affairQjSum,AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, Model model) {
        String unitId = request.getParameter("unitId");
        if ("1".equals(unitId)){
            affairQj.setFlag("1");
        }
        List<AffairQjSum> statistic = affairQjService.findByUnitId(affairQjSum,affairQj);
        model.addAttribute("statistic", statistic);
        return "modules/affair/affairQjSum2";
    }
}
