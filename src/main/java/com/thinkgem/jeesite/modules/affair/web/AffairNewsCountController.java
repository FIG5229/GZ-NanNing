package com.thinkgem.jeesite.modules.affair.web;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.excel.ExportExcelNew;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsCount;
import com.thinkgem.jeesite.modules.affair.service.AffairNewsCountService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/affair/affairNewsCount")
public class AffairNewsCountController extends BaseController {

    @Autowired
    private AffairNewsCountService affairNewsCountService;

    @RequestMapping(value = {"countByUnit",""})
    public String list(AffairNewsCount affairNewsCount, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
        Page<AffairNewsCount> page = null;
        String officeId = UserUtils.getUser().getCompany().getId();
//        String date = request.getParameter("date");

            if (affairNewsCount.getDate() == "" || affairNewsCount.getDate() == null){
                String year = DateUtils.getYear();
                model.addAttribute("date",year);
            }else {
                model.addAttribute("date",affairNewsCount.getDate());
            }

        if ("1".equals(affairNewsCount.getFlag()) || null == affairNewsCount.getFlag()){
            if (officeId.equals("1")){
                affairNewsCount.setFlag("1");
                model.addAttribute("flagOne",affairNewsCount.getFlag());
                model.addAttribute("affairNewsCount",affairNewsCount);
                page = affairNewsCountService.countByJuUnit(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                List<AffairNewsCount> list = page.getList();
                List<AffairNewsCount> nncList = new ArrayList<>();
                List<AffairNewsCount> lzcList = new ArrayList<>();
                List<AffairNewsCount> bhcList = new ArrayList<>();
                List<AffairNewsCount> allList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    AffairNewsCount affairNewsCount1 = new AffairNewsCount();
                    if (list.get(i).getUnit().contains("南宁处")){
                        affairNewsCount1.setUnit(list.get(i).getUnit());
                        affairNewsCount1.setUnitId(list.get(i).getUnitId());
                        affairNewsCount1.setSum(list.get(i).getSum());
                        affairNewsCount1.setSum1(list.get(i).getSum1());
                        affairNewsCount1.setSum2(list.get(i).getSum2());
                        affairNewsCount1.setSum3(list.get(i).getSum3());
                        affairNewsCount1.setSum4(list.get(i).getSum4());
                        affairNewsCount1.setSum5(list.get(i).getSum5());
                        //nncList.add(i,affairNewsCount1);
                        nncList.add(affairNewsCount1);
                    }else if (list.get(i).getUnit().contains("柳州处")){
                        affairNewsCount1.setUnit(list.get(i).getUnit());
                        affairNewsCount1.setUnitId(list.get(i).getUnitId());
                        affairNewsCount1.setSum(list.get(i).getSum());
                        affairNewsCount1.setSum1(list.get(i).getSum1());
                        affairNewsCount1.setSum2(list.get(i).getSum2());
                        affairNewsCount1.setSum3(list.get(i).getSum3());
                        affairNewsCount1.setSum4(list.get(i).getSum4());
                        affairNewsCount1.setSum5(list.get(i).getSum5());
                        //lzcList.add(i,affairNewsCount1);
                        lzcList.add(affairNewsCount1);
                    }else if (list.get(i).getUnit().contains("北海处")){
                        affairNewsCount1.setUnit(list.get(i).getUnit());
                        affairNewsCount1.setUnitId(list.get(i).getUnitId());
                        affairNewsCount1.setSum(list.get(i).getSum());
                        affairNewsCount1.setSum1(list.get(i).getSum1());
                        affairNewsCount1.setSum2(list.get(i).getSum2());
                        affairNewsCount1.setSum3(list.get(i).getSum3());
                        affairNewsCount1.setSum4(list.get(i).getSum4());
                        affairNewsCount1.setSum5(list.get(i).getSum5());
                        //bhcList.add(i,affairNewsCount1);
                        bhcList.add(affairNewsCount1);
                    }
                }
                int nncSum = 0;
                int nncSum1 = 0;
                int nncSum2 = 0;
                int nncSum3 = 0;
                int nncSum4 = 0;
                int nncSum5 = 0;
                AffairNewsCount affairNewsCount2 = new AffairNewsCount();
                for (int j = 0; j < nncList.size(); j++) {
                    nncSum = nncSum + nncList.get(j).getSum();
                    nncSum1 = nncSum1 + Integer.valueOf(nncList.get(j).getSum1());
                    nncSum2 = nncSum2 + Integer.valueOf(nncList.get(j).getSum2());
                    nncSum3 = nncSum3 + Integer.valueOf(nncList.get(j).getSum3());
                    nncSum4 = nncSum4 + Integer.valueOf(nncList.get(j).getSum4());
                    nncSum5 = nncSum5 + Integer.valueOf(nncList.get(j).getSum5());
                }
                affairNewsCount2.setUnit("南宁公安处");
                affairNewsCount2.setSum(nncSum);
                affairNewsCount2.setSum1(String.valueOf(nncSum1));
                affairNewsCount2.setSum2(String.valueOf(nncSum2));
                affairNewsCount2.setSum3(String.valueOf(nncSum3));
                affairNewsCount2.setSum4(String.valueOf(nncSum4));
                affairNewsCount2.setSum5(String.valueOf(nncSum5));
                allList.add(0,affairNewsCount2);

                int lzcSum = 0;
                int lzcSum1 = 0;
                int lzcSum2 = 0;
                int lzcSum3 = 0;
                int lzcSum4 = 0;
                int lzcSum5 = 0;
                AffairNewsCount affairNewsCount3 = new AffairNewsCount();
                for (int j = 0; j < lzcList.size(); j++) {
                    lzcSum = lzcSum + lzcList.get(j).getSum();
                    lzcSum1 = lzcSum1 + Integer.valueOf(lzcList.get(j).getSum1());
                    lzcSum2 = lzcSum2 + Integer.valueOf(lzcList.get(j).getSum2());
                    lzcSum3 =lzcSum3 + Integer.valueOf(lzcList.get(j).getSum3());
                    lzcSum4 = lzcSum4 + Integer.valueOf(lzcList.get(j).getSum4());
                    lzcSum5 = lzcSum5 + Integer.valueOf(lzcList.get(j).getSum5());
                }
                affairNewsCount3.setUnit("柳州公安处");
                affairNewsCount3.setSum(lzcSum);
                affairNewsCount3.setSum1(String.valueOf(lzcSum1));
                affairNewsCount3.setSum2(String.valueOf(lzcSum2));
                affairNewsCount3.setSum3(String.valueOf(lzcSum3));
                affairNewsCount3.setSum4(String.valueOf(lzcSum4));
                affairNewsCount3.setSum5(String.valueOf(lzcSum5));
                allList.add(1,affairNewsCount3);

                int bhcSum = 0;
                int bhcSum1 = 0;
                int bhcSum2 = 0;
                int bhcSum3 = 0;
                int bhcSum4 = 0;
                int bhcSum5 = 0;
                AffairNewsCount affairNewsCount4 = new AffairNewsCount();
                for (int j = 0; j < bhcList.size(); j++) {
                    bhcSum = bhcSum + bhcList.get(j).getSum();
                    bhcSum1 = bhcSum1 + Integer.valueOf(bhcList.get(j).getSum1());
                    bhcSum2 = bhcSum2 + Integer.valueOf(bhcList.get(j).getSum2());
                    bhcSum3 = bhcSum3 + Integer.valueOf(bhcList.get(j).getSum3());
                    bhcSum4 = bhcSum4 + Integer.valueOf(bhcList.get(j).getSum4());
                    bhcSum5 = bhcSum5 + Integer.valueOf(bhcList.get(j).getSum5());
                }
                affairNewsCount4.setUnit("北海公安处");
                affairNewsCount4.setSum(bhcSum);
                affairNewsCount4.setSum1(String.valueOf(bhcSum1));
                affairNewsCount4.setSum2(String.valueOf(bhcSum2));
                affairNewsCount4.setSum3(String.valueOf(bhcSum3));
                affairNewsCount4.setSum4(String.valueOf(bhcSum4));
                affairNewsCount4.setSum5(String.valueOf(bhcSum5));
                allList.add(2,affairNewsCount4);
                Collections.sort(allList , (AffairNewsCount b1, AffairNewsCount b2) -> b1.getSum().compareTo(b2.getSum()));
                Collections.reverse(allList);
                page.setList(allList);
            }else{
                affairNewsCount.setFlag("1");

                model.addAttribute("flagOne",affairNewsCount.getFlag());
                model.addAttribute("affairNewsCount",affairNewsCount);
                page = affairNewsCountService.countByUnit(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                List<Map<String,String>> allUnit = affairNewsCountService.findAllUnit(officeId);
                List<AffairNewsCount> list = page.getList();
                Set<String> dataSet = new HashSet<String>();
                for(AffairNewsCount news: list){
                    dataSet.add(news.getUnitId());
                }
                for (int i = 0; i < allUnit.size(); i++) {
                    Map<String,String> map = allUnit.get(i);
                    if(!dataSet.contains(map.get("unitid").toString())){
                        AffairNewsCount affairNewsCount1 = new AffairNewsCount();
                        affairNewsCount1.setUnit(map.get("unit"));
                        affairNewsCount1.setSum(0);
                        affairNewsCount1.setSum1("0");
                        affairNewsCount1.setSum2("0");
                        affairNewsCount1.setSum3("0");
                        affairNewsCount1.setSum4("0");
                        affairNewsCount1.setSum5("0");

                        list.add(affairNewsCount1);
                    }
                }

                Collections.sort(list , (AffairNewsCount b1, AffairNewsCount b2) -> b1.getSum().compareTo(b2.getSum()));
                Collections.reverse(list);
                page.setList(list);
            }

        }else if ("2".equals(affairNewsCount.getFlag())){
            affairNewsCount.setFlag("2");

            model.addAttribute("flagOne",affairNewsCount.getFlag());
            model.addAttribute("affairNewsCount",affairNewsCount);
            page = affairNewsCountService.countByAuthor(new Page<AffairNewsCount>(request, response), affairNewsCount);
            List<AffairNewsCount> list = page.getList();
            Collections.sort(list , (AffairNewsCount b1, AffairNewsCount b2) -> b1.getSum().compareTo(b2.getSum()));
            Collections.reverse(list);
            page.setList(list);
        }else {
            affairNewsCount.setFlag("3");

            model.addAttribute("flagOne",affairNewsCount.getFlag());
            model.addAttribute("affairNewsCount",affairNewsCount);
            page = affairNewsCountService.countByName(new Page<AffairNewsCount>(request, response), affairNewsCount);
            List<AffairNewsCount> list = page.getList();
            Collections.sort(list , (AffairNewsCount b1, AffairNewsCount b2) -> b1.getSum().compareTo(b2.getSum()));
            Collections.reverse(list);
            page.setList(list);
        }
        model.addAttribute("page", page);
        return "modules/affair/affairNewsCountList";
    }

    @RequestMapping(value = {"detailCountByUnit"})
    public String detailCountByUnit(AffairNewsCount affairNewsCount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairNewsCount> page;
        page = affairNewsCountService.detailCountByUnit(new Page<AffairNewsCount>(request, response), affairNewsCount);
        model.addAttribute("page", page);
        return "modules/affair/affairNewsCountList";
    }

    @RequestMapping(value = {"detailCountByAuthor"})
    public String detailCountByAuthor(AffairNewsCount affairNewsCount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairNewsCount> page;
        page = affairNewsCountService.detailCountByAuthor(new Page<AffairNewsCount>(request, response), affairNewsCount);
        model.addAttribute("page", page);
        return "modules/affair/affairNewsCountDetailList";
    }

    @RequestMapping(value = {"detailCountByName"})
    public String detailCountByName(AffairNewsCount affairNewsCount, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<AffairNewsCount> page;
        page = affairNewsCountService.detailCountByName(new Page<AffairNewsCount>(request, response), affairNewsCount);
        model.addAttribute("page", page);
        return "modules/affair/affairNewsCountDetailList";
    }


    /**
     * 导出excel格式数据
     * @param affairNewsCount
     * @param request
     * @param response
     * @param redirectAttributes
     * @param flags
     * @return
     */
    @RequestMapping(value = "export", method= RequestMethod.POST)
    public String exportExcelByTemplate(AffairNewsCount affairNewsCount, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,boolean flags, @RequestParam(value = "flagOne") String flagOne) {
        XSSFWorkbook wb = null;
        try
        {
            String fileName = "";
            if(request.getParameter("fileName")!=null && !"".equals(request.getParameter("fileName"))){
                fileName= request.getParameter("fileName").toString();
            }
            String officeId = UserUtils.getUser().getCompany().getId();
            Page<AffairNewsCount> page = null;
            if(flags == true){
                if ("1".equals(affairNewsCount.getFlag()) || null == affairNewsCount.getFlag()){
                    if (officeId.equals("1")){
                        affairNewsCount.setFlag("1");
                        page = affairNewsCountService.countByJuUnit(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                        List<AffairNewsCount> list = page.getList();
                        List<AffairNewsCount> nncList = new ArrayList<>();
                        List<AffairNewsCount> lzcList = new ArrayList<>();
                        List<AffairNewsCount> bhcList = new ArrayList<>();
                        List<AffairNewsCount> allList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            AffairNewsCount affairNewsCount1 = new AffairNewsCount();
                            if (list.get(i).getUnit().contains("南宁处")){
                                affairNewsCount1.setUnit(list.get(i).getUnit());
                                affairNewsCount1.setUnitId(list.get(i).getUnitId());
                                affairNewsCount1.setSum(list.get(i).getSum());
                                affairNewsCount1.setSum1(list.get(i).getSum1());
                                affairNewsCount1.setSum2(list.get(i).getSum2());
                                affairNewsCount1.setSum3(list.get(i).getSum3());
                                affairNewsCount1.setSum4(list.get(i).getSum4());
                                affairNewsCount1.setSum5(list.get(i).getSum5());
                                nncList.add(i,affairNewsCount1);
                            }else if (list.get(i).getUnit().contains("柳州处")){
                                affairNewsCount1.setUnit(list.get(i).getUnit());
                                affairNewsCount1.setUnitId(list.get(i).getUnitId());
                                affairNewsCount1.setSum(list.get(i).getSum());
                                affairNewsCount1.setSum1(list.get(i).getSum1());
                                affairNewsCount1.setSum2(list.get(i).getSum2());
                                affairNewsCount1.setSum3(list.get(i).getSum3());
                                affairNewsCount1.setSum4(list.get(i).getSum4());
                                affairNewsCount1.setSum5(list.get(i).getSum5());
                                lzcList.add(i,affairNewsCount1);
                            }else if (list.get(i).getUnit().contains("北海处")){
                                affairNewsCount1.setUnit(list.get(i).getUnit());
                                affairNewsCount1.setUnitId(list.get(i).getUnitId());
                                affairNewsCount1.setSum(list.get(i).getSum());
                                affairNewsCount1.setSum1(list.get(i).getSum1());
                                affairNewsCount1.setSum2(list.get(i).getSum2());
                                affairNewsCount1.setSum3(list.get(i).getSum3());
                                affairNewsCount1.setSum4(list.get(i).getSum4());
                                affairNewsCount1.setSum5(list.get(i).getSum5());
                                lzcList.add(i,affairNewsCount1);
                            }
                        }
                        int nncSum = 0;
                        int nncSum1 = 0;
                        int nncSum2 = 0;
                        int nncSum3 = 0;
                        int nncSum4 = 0;
                        int nncSum5 = 0;
                        AffairNewsCount affairNewsCount2 = new AffairNewsCount();
                        for (int j = 0; j < nncList.size(); j++) {
                            nncSum = nncSum + nncList.get(j).getSum();
                            nncSum1 = nncSum1 + Integer.valueOf(nncList.get(j).getSum1());
                            nncSum2 = nncSum2 + Integer.valueOf(nncList.get(j).getSum2());
                            nncSum3 = nncSum3 + Integer.valueOf(nncList.get(j).getSum3());
                            nncSum4 = nncSum4 + Integer.valueOf(nncList.get(j).getSum4());
                            nncSum5 = nncSum5 + Integer.valueOf(nncList.get(j).getSum5());
                        }
                        affairNewsCount2.setUnit("南宁公安处");
                        affairNewsCount2.setSum(nncSum);
                        affairNewsCount2.setSum1(String.valueOf(nncSum1));
                        affairNewsCount2.setSum2(String.valueOf(nncSum2));
                        affairNewsCount2.setSum3(String.valueOf(nncSum3));
                        affairNewsCount2.setSum4(String.valueOf(nncSum4));
                        affairNewsCount2.setSum5(String.valueOf(nncSum5));
                        allList.add(0,affairNewsCount2);

                        int lzcSum = 0;
                        int lzcSum1 = 0;
                        int lzcSum2 = 0;
                        int lzcSum3 = 0;
                        int lzcSum4 = 0;
                        int lzcSum5 = 0;
                        AffairNewsCount affairNewsCount3 = new AffairNewsCount();
                        for (int j = 0; j < lzcList.size(); j++) {
                            lzcSum = lzcSum + lzcList.get(j).getSum();
                            lzcSum1 = lzcSum1 + Integer.valueOf(lzcList.get(j).getSum1());
                            lzcSum2 = lzcSum2 + Integer.valueOf(lzcList.get(j).getSum2());
                            lzcSum3 =lzcSum3 + Integer.valueOf(lzcList.get(j).getSum3());
                            lzcSum4 = lzcSum4 + Integer.valueOf(lzcList.get(j).getSum4());
                            lzcSum5 = lzcSum5 + Integer.valueOf(lzcList.get(j).getSum5());
                        }
                        affairNewsCount3.setUnit("柳州公安处");
                        affairNewsCount3.setSum(lzcSum);
                        affairNewsCount3.setSum1(String.valueOf(lzcSum1));
                        affairNewsCount3.setSum2(String.valueOf(lzcSum2));
                        affairNewsCount3.setSum3(String.valueOf(lzcSum3));
                        affairNewsCount3.setSum4(String.valueOf(lzcSum4));
                        affairNewsCount3.setSum5(String.valueOf(lzcSum5));
                        allList.add(1,affairNewsCount3);

                        int bhcSum = 0;
                        int bhcSum1 = 0;
                        int bhcSum2 = 0;
                        int bhcSum3 = 0;
                        int bhcSum4 = 0;
                        int bhcSum5 = 0;
                        AffairNewsCount affairNewsCount4 = new AffairNewsCount();
                        for (int j = 0; j < bhcList.size(); j++) {
                            bhcSum = bhcSum + bhcList.get(j).getSum();
                            bhcSum1 = bhcSum1 + Integer.valueOf(bhcList.get(j).getSum1());
                            bhcSum2 = bhcSum2 + Integer.valueOf(bhcList.get(j).getSum2());
                            bhcSum3 = bhcSum3 + Integer.valueOf(bhcList.get(j).getSum3());
                            bhcSum4 = bhcSum4 + Integer.valueOf(bhcList.get(j).getSum4());
                            bhcSum5 = bhcSum5 + Integer.valueOf(bhcList.get(j).getSum5());
                        }
                        affairNewsCount4.setUnit("北海公安处");
                        affairNewsCount4.setSum(bhcSum);
                        affairNewsCount4.setSum1(String.valueOf(bhcSum1));
                        affairNewsCount4.setSum2(String.valueOf(bhcSum2));
                        affairNewsCount4.setSum3(String.valueOf(bhcSum3));
                        affairNewsCount4.setSum4(String.valueOf(bhcSum4));
                        affairNewsCount4.setSum5(String.valueOf(bhcSum5));
                        allList.add(2,affairNewsCount4);
                        Collections.sort(allList , (AffairNewsCount b1, AffairNewsCount b2) -> b1.getSum().compareTo(b2.getSum()));
                        Collections.reverse(allList);
                        page.setList(allList);
                    }else{
                        affairNewsCount.setFlag("1");
                        page = affairNewsCountService.countByUnit(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                        List<Map<String,String>> allUnit = affairNewsCountService.findAllUnit(officeId);
                        List<AffairNewsCount> list = page.getList();
                        Set<String> dataSet = new HashSet<String>();
                        for(AffairNewsCount news: list){
                            dataSet.add(news.getUnitId());
                        }
                        for (int i = 0; i < allUnit.size(); i++) {
                            Map<String,String> map = allUnit.get(i);
                            if(!dataSet.contains(map.get("unitid").toString())){
                                AffairNewsCount affairNewsCount1 = new AffairNewsCount();
                                affairNewsCount1.setUnit(map.get("unit"));
                                affairNewsCount1.setSum(0);
                                affairNewsCount1.setSum1("0");
                                affairNewsCount1.setSum2("0");
                                affairNewsCount1.setSum3("0");
                                affairNewsCount1.setSum4("0");
                                affairNewsCount1.setSum5("0");

                                list.add(affairNewsCount1);
                            }
                        }
                        page.setList(list);
                    }

                }else if ("2".equals(affairNewsCount.getFlag())){
                    page = affairNewsCountService.countByAuthor(new Page<AffairNewsCount>(request, response), affairNewsCount);
                    List<AffairNewsCount> list = page.getList();
                    page.setList(list);
                }else {

                    page = affairNewsCountService.countByName(new Page<AffairNewsCount>(request, response), affairNewsCount);
                    List<AffairNewsCount> list = page.getList();

                    page.setList(list);
                }


            }else{

            }



            /*if(flags == true){
                //page = pages;
                if ("1".equals(flagOne) || null == flagOne){
                    page = affairNewsCountService.countByUnit(new Page<AffairNewsCount>(request, response), affairNewsCount);

                }else if ("2".equals(flagOne)){
                    page = affairNewsCountService.countByAuthor(new Page<AffairNewsCount>(request, response), affairNewsCount);
                }else {
                    page = affairNewsCountService.countByName(new Page<AffairNewsCount>(request, response), affairNewsCount);
                }
            }else{
                if ("1".equals(flagOne) || null == flagOne){
                    page = affairNewsCountService.countByUnit(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);

                }else if ("2".equals(flagOne)){
                    page = affairNewsCountService.countByAuthor(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                }else {
                    page = affairNewsCountService.countByName(new Page<AffairNewsCount>(request, response,-1), affairNewsCount);
                }
            }*/
            String fileSeperator = File.separator;
            String filePath= Global.getUserfilesBaseDir()+fileSeperator+"userfiles"+fileSeperator+"template"+fileSeperator;
            InputStream inputStream = new FileInputStream(filePath+fileName);
            if (null != inputStream)
            {
                try
                {
                    wb = new  XSSFWorkbook(inputStream);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ExportExcelNew exportExcelNew = new ExportExcelNew(0, AffairNewsCount.class);
            exportExcelNew.setWb(wb);
            List<AffairNewsCount> list =page.getList();
            for (AffairNewsCount newsCount : list) {
                String unit = newsCount.getUnit();
                String author = newsCount.getAuthor();
                String name = newsCount.getName();
                if (null != unit) {
                    newsCount.setJoinThree(unit);
                }
                if(null != author){
                    newsCount.setJoinThree(author);
                }
                if(null != name){
                    newsCount.setJoinThree(name);
                }
            }
            exportExcelNew.setDataList(list);
            HSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
            ServletOutputStream fout = response.getOutputStream();
            wb.write(fout);
            fout.close();
            return null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            addMessage(redirectAttributes, "导出刊稿排名列表失败！失败信息："+ex);
        }
        return "redirect:" + adminPath + "/affair/AffairNewsCount?repage";
        //return "redirect:" + adminPath + "/affair/AffairNewsCount/countByUnit?repage";
    }


}
