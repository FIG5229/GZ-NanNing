/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairWenhua;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalent;
import com.thinkgem.jeesite.modules.affair.entity.AffairYouthTalent;
import com.thinkgem.jeesite.modules.affair.service.AffairWenhuaService;
import com.thinkgem.jeesite.modules.affair.service.AffairWentiTalentService;
import com.thinkgem.jeesite.modules.affair.service.AffairYouthTalentService;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelSkill;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelTalents;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelSkillService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人才库Controller
 * @author alan.wu
 * @version 2020-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/personnel/personnelTalents")
public class PersonnelTalentsController extends BaseController {

    @Autowired
    private PersonnelSkillService personnelSkillService;

    @Autowired
    private AffairWenhuaService affairWenhuaService;

    @Autowired
    private AffairWentiTalentService affairWentiTalentService;

    @Autowired
    private AffairYouthTalentService affairYouthTalentService;

    @RequiresPermissions("personnel:personnelTalents:view")
    @RequestMapping(value = {"list", ""})
    public String ailist(PersonnelTalents personnelTalents, HttpServletRequest request, HttpServletResponse response, Model model) {
       /* Page<PersonnelSkill> page = personnelSkillService.findPage(new Page<PersonnelSkill>(request, response), personnelSkill);
        model.addAttribute("page",page);
        return "modules/personnel/personnelTalentsList";*/

        Page<PersonnelTalents> page = personnelSkillService.findRenCaiList(new Page<PersonnelTalents>(request, response),personnelTalents);
        model.addAttribute("page",page);
        return "modules/personnel/personnelTalentsList";
    }

    /*
    * 查看详情
    *
    * */
    @RequiresPermissions("personnel:personnelTalents:view")
    @RequestMapping(value = {"formDetail"})
    public String formDetail(PersonnelSkill personnelSkill, HttpServletRequest request, HttpServletResponse response, Model model) {

        String id = personnelSkill.getId();

        PersonnelSkill ps = personnelSkillService.selectIdNumber(id);

        //宣传思想 -- 文化人才
        List<AffairWenhua> affairWenhuaList = affairWenhuaService.selectSpeciality(ps.getPersonnelName());

        //工会保障 -- 体育特长
        List<AffairWentiTalent> affairWentiTalentList = affairWentiTalentService.selectSpeciality(ps.getIdNumber());

        //青年团建 -- 新警特长
        List<AffairYouthTalent> affairYouthTalentList = affairYouthTalentService.selectSpeciality(ps.getIdNumber());


        Page<PersonnelSkill> page = personnelSkillService.findPage(new Page<PersonnelSkill>(request, response), personnelSkill);

        model.addAttribute("page",page.getList());
        model.addAttribute("wenHuaList",affairWenhuaList);
        model.addAttribute("tiyuList",affairWentiTalentList);
        model.addAttribute("xinjinList",affairYouthTalentList);


        return "modules/personnel/personnelTalentsFormDetail";
    }

    //统计分析-专长情况查看详情
    @RequestMapping(value = {"specialityListDetail"})
    public String SpecialityListDetail(PersonnelTalents personnelTalents,HttpServletRequest request, HttpServletResponse response, Model model){
        String specialityName = request.getParameter("specialityName");
        String unitId = request.getParameter("unitId");
        personnelTalents.setSkill(specialityName);
        personnelTalents.setUnitId(unitId);
        Page<PersonnelTalents> page = personnelSkillService.findSpecialityList(new Page<PersonnelTalents>(request, response),personnelTalents);
        model.addAttribute("specialityName",specialityName);
        model.addAttribute("unitId",unitId);
        model.addAttribute("page",page);
        return "modules/charts/personnelSpecialityListDetail";
    }

}