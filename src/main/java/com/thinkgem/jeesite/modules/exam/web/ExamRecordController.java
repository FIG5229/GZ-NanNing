package com.thinkgem.jeesite.modules.exam.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.entity.ExamRecord;
import com.thinkgem.jeesite.modules.exam.service.ExamRecordService;
import com.thinkgem.jeesite.modules.exam.service.ExamUnitAllPublicService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 考评档案 Controller
 * @author kevin.jia,
 * @version 2020/9/16
 */
@Controller
@RequestMapping(value = "${adminPath}/exam/examRecord")
public class ExamRecordController extends BaseController {
    @Autowired
    private ExamUnitAllPublicService examUnitAllPublicService;

    @Autowired
    private ExamRecordService examRecordService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OfficeService officeService;

    //@RequiresPermissions("exam:examRecord:view")
    @RequestMapping(value = {""})
    public String index(HttpServletRequest request, Model model) {
//        model.addAttribute("list", officeService.findAll());
        String type = request.getParameter("type");
        if(type!=null && !type.isEmpty()){
            if("unit".equals(type)){
                return "modules/exam/examRecordUnitIndex";
            }
            if("person".equals(type)){
                return "modules/exam/examRecordPersonIndex";
            }
        }
        return "modules/exam/examRecordUnitIndex";
    }

    //@RequiresPermissions("exam:examRecord:view")
    @RequestMapping(value = {"list"})
    public String list(ExamRecord examRecord , HttpServletRequest request, HttpServletResponse response ,Model model) {
        Page<ExamRecord> page = new Page<ExamRecord>(request, response);
        String type = request.getParameter("type");
        if(type!=null && !type.isEmpty()&&"person".equals(type)){
            String name = request.getParameter("name");
            String personId = request.getParameter("personId");
            String idNumber = request.getParameter("idNumber");
            if("人员基本信息".equals(personId)){
                personId = userDao.getIdByNo(idNumber);
            }
            model.addAttribute("name",name);
            model.addAttribute("personId",personId);
            model.addAttribute("examRecord",examRecord);
            model.addAttribute("idNumber",idNumber);
            page = examRecordService.findPersonScore(page,name,idNumber);
            model.addAttribute("page",page);
            return "modules/exam/examRecordPersonList";
        }else{
            String unitId = request.getParameter("unitId");
            String name = request.getParameter("name");
            String unitType = request.getParameter("unitType");
            model.addAttribute("name",name);
            model.addAttribute("unitId",unitId);
            model.addAttribute("unitType",unitType);
            model.addAttribute("examRecord",examRecord);
            //page = examUnitAllPublicService.findUnitYear(page,unitId);
            page = examRecordService.findUnitScore2(page,name,unitId,unitType);
            model.addAttribute("page",page);
            return "modules/exam/examRecordUnitList";
        }

    }
    @ResponseBody
    @RequestMapping(value = {"condition"})
    public Result condition(@RequestParam("year")String year,
                            @RequestParam(value = "startMonth",required = false)String startMonth ,
                            @RequestParam(value = "endMonth", required=false)String endMonth,
                            @RequestParam(value = "unitId", required=false)String unitId,
                            @RequestParam(value = "type")String type,
                            @RequestParam(value = "idNumber", required=false)String idNumber){
        Result result = new Result();
        //examCheck 检查情况
        int yearNum = Integer.parseInt(year);//年
        int startMonthNum = 0;
        try {
            startMonthNum = Integer.parseInt(startMonth);//开始月
        }catch (Exception e){
            startMonthNum = 0;
            logger.error(e.toString());
        }
        int endMonthNum = 0;
        try {
            endMonthNum = Integer.parseInt(endMonth);//结束月
        }catch (Exception e){
            endMonthNum = 0;
            logger.error(e.toString());
        }
        if("unit".equals(type)){
            //Map<String,Object> map = examRecordService.findScoreInfoListByYearMonth(yearNum,startMonthNum,endMonthNum,unitId);
            String userId ;
            if("34".equals(unitId)||"95".equals(unitId)||"156".equals(unitId)){
                userId = unitId;
            }else{
                userId = userDao.selectUserId(officeService.get(unitId).getCode());//根据单位代码userId
            }
            Map<String,Object> map = examRecordService.findScoreInfoList(yearNum,startMonthNum,endMonthNum,userId);
            result.setResult(map);
            result.setSuccess(true);
        }else if("person".equals(type)){
            //Map<String,Object> map = examRecordService.findScoreInfoListByYearMonth(yearNum,startMonthNum,endMonthNum,idNumber);
            Map<String,Object> map = examRecordService.findScoreInfoList(yearNum,startMonthNum,endMonthNum,idNumber);
            result.setResult(map);
            result.setSuccess(true);
        }
        return result;
    }
}
