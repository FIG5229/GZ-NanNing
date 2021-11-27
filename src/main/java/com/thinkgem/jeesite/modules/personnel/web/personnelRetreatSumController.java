package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreat;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelRetreatSum;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelRetreatService;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
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


@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelRetreatSum")
public class personnelRetreatSumController extends BaseController {

    @Autowired
    private OfficeDao officeDao;
    @Autowired
    private PersonnelRetreatService personnelRetreatService;

    @ModelAttribute
    public PersonnelRetreat get(@RequestParam(required=false) String id) {
        PersonnelRetreat entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = personnelRetreatService.get(id);
        }
        if (entity == null){
            entity = new PersonnelRetreat();
        }
        return entity;
    }

    @RequiresPermissions("personnel:personnelRetreat:view")
    @RequestMapping(value = {"list", ""})
    public String list(PersonnelRetreat personnelRetreat,HttpServletRequest request, HttpServletResponse response, Model model) {
        if("1".equals(request.getParameter("modelType"))){

        }else if ("2".equals(request.getParameter("modelType"))){
            personnelRetreat.setSex("1");

        }else if ("3".equals(request.getParameter("modelType"))){
            personnelRetreat.setSex("2");

        }else if ("5".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(55);
            personnelRetreat.setMaxAge(60);

        }else if ("6".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(61);
            personnelRetreat.setMaxAge(65);

        }else if ("7".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(66);
            personnelRetreat.setMaxAge(70);

        }else if ("8".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(71);
            personnelRetreat.setMaxAge(75);

        }else if ("9".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(76);
            personnelRetreat.setMaxAge(80);

        }else if ("10".equals(request.getParameter("modelType"))){
            personnelRetreat.setMinAge(81);

        }else if ("11".equals(request.getParameter("modelType"))){
            personnelRetreat.setType("1");

        }else if ("12".equals(request.getParameter("modelType"))){
            personnelRetreat.setType("2");
        }
        personnelRetreat.setNowUnitNameId(request.getParameter("unitId"));
        Page<PersonnelRetreat> page = personnelRetreatService.findPage(new Page<PersonnelRetreat>(request, response), personnelRetreat);
        model.addAttribute("page", page);
        return "modules/personnel/personnelRetreatSum";
    }

    @RequiresPermissions("personnel:personnelRetreat:view")
    @RequestMapping(value = "form")
    public String form(PersonnelRetreat personnelRetreat, Model model,HttpServletRequest request) {

        model.addAttribute("personnelRetreat", personnelRetreat);
        return "modules/personnel/personnelRetreatForm";
    }
    /**
     * 离退休查询一级级页面
     * @param personnelRetreat
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("personnel:personnelRetreat:view")
    @RequestMapping(value = {"statistic"})
    public String statistic(PersonnelRetreat personnelRetreat, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<PersonnelRetreatSum> statistic = personnelRetreatService.statistic(personnelRetreat);
        model.addAttribute("statistic", statistic);
        return "modules/personnel/personnelRetreatSum1";
    }

    @RequiresPermissions("personnel:personnelRetreat:view")
    @RequestMapping(value = {"findByUnitId"})
    public String findByUnitId(PersonnelRetreatSum personnelRetreatSum,PersonnelRetreat personnelRetreat, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<PersonnelRetreatSum> statistic = personnelRetreatService.findByUnitId(personnelRetreatSum,personnelRetreat);
        model.addAttribute("statistic", statistic);
        return "modules/personnel/personnelRetreatSum2";
    }

}
