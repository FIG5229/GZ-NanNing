package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairQj;
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
import java.util.Calendar;

@Controller
@RequestMapping(value = "${adminPath}/affair/affairQjCount")
public class AffairQjCountController extends BaseController {

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
    @RequestMapping(value = {"count"})
    public String countList (AffairQj affairQj, HttpServletRequest request, HttpServletResponse response, Model model) {

        Calendar calendar = Calendar.getInstance();
        Integer ye = calendar.get(Calendar.YEAR);
        Integer mon = calendar.get(Calendar.MONTH) + 1;

        Integer year = affairQj.getYear();
        Integer month = affairQj.getMonth();

        if ("".equals(year) || null == year){
            affairQj.setYear(ye);
            if ("".equals(month) || null == month){
                affairQj.setMonth(mon);
            }
        }else if ("".equals(month) || null == month){
            affairQj.setMonth(mon);
            if("".equals(year) || null == year){
                affairQj.setYear(year);
            }
        }

        Page<AffairQj> pageCount = affairQjService.countList(new Page<AffairQj>(request, response,-1), affairQj);
        model.addAttribute("page", pageCount);
        return "modules/affair/affairQjCount";
    }
}
