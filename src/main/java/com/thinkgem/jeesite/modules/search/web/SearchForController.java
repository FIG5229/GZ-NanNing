package com.thinkgem.jeesite.modules.search.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.service.PersonnelBaseService;
import com.thinkgem.jeesite.modules.search.service.SearchForService;
import com.thinkgem.jeesite.modules.search.service.SearchForsService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/search")
public class SearchForController extends BaseController {
    @Autowired
    private SearchForService searchForService;

    @Autowired
    private SearchForsService searchForsService;

    @Autowired
    private PersonnelBaseService personnelBaseService;

    /*@Autowired
    private SearchFor searchFor;*/

    @RequestMapping(value = "searchFor")
    public String searchFor(Model model){
        return "modules/search/searchFor";
    }

    @RequestMapping(value = "findList")
    public ModelAndView findList(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
        String searchContent = request.getParameter("search-content");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (searchContent == null || searchContent.equals("") ) {
            String sex = request.getParameter("sexhidden");
            String nation = request.getParameter("nahidden");
            String mianmao = request.getParameter("mmhidden");
            String unitId = request.getParameter("workunitId");
            String approcalOrgType = request.getParameter("orghidden");
            String rewardName = request.getParameter("rewardName");
            String cjType = request.getParameter("cjtypehidden");
            String cjName = request.getParameter("cjName");
            String cjOrgType = request.getParameter("cothidden");
            String year = request.getParameter("year");
            String conclusion = request.getParameter("conclusion");
            String xlName = request.getParameter("xlName");
            String schoolName = request.getParameter("schoolName");
            request.setAttribute("sex",sex);
            request.setAttribute("nation", nation);
            request.setAttribute("mianmao", mianmao);
            request.setAttribute("approcalOrgType",approcalOrgType);
            request.setAttribute("rewardName",rewardName);
            request.setAttribute("cjType",cjType);
            request.setAttribute("cjName",cjName);
            request.setAttribute("cjOrgType",cjOrgType);
            request.setAttribute("year",year);
            request.setAttribute("conclusion",conclusion);
            request.setAttribute("xlName",xlName);
            request.setAttribute("schoolName",schoolName);
            if("".equals(sex) && "".equals(nation) && "".equals(mianmao) && "".equals(unitId) && "".equals(approcalOrgType) && "".equals(rewardName) && "".equals(cjType) && "".equals(cjName) && "".equals(cjOrgType) &&"".equals(year) && "".equals(conclusion) && "".equals(xlName) && "".equals(schoolName)){
                ModelAndView view = new ModelAndView("modules/search/searchFor");
                return view;
            }
            else if("".equals(approcalOrgType) && "".equals(rewardName) && "".equals(cjType) && "".equals(cjName) && "".equals(cjOrgType) &&"".equals(year) && "".equals(conclusion) && "".equals(xlName) && "".equals(schoolName)){
                list = searchForsService.findBaseList(sex, nation, mianmao, unitId);
            }else {
                list = searchForsService.findListinfo(sex, nation, mianmao, unitId, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
            }
            if(null != list){
                for(Map<String,Object> obj:list){
                    StringBuffer result = new StringBuffer();
                    String sexName = null;
                    String mianMao = null;
                    String nationName = null;
                    String orgType = null;
                    String cjTypes = null;
                    String cjOrgTypes = null;
                    String temp = null;
                    String workName = null;
                    if (null != obj.get("idNumber") && !"".equals(obj.get("idNumber"))){
                        result.append("??????????????????"+obj.get("idNumber"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("policeIdNumber") && !"".equals(obj.get("policeIdNumber"))){
                        result.append("??????????????????"+obj.get("policeIdNumber"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("sex") && !"".equals(obj.get("sex"))){
                        sexName = DictUtils.getDictLabel(obj.get("sex").toString(), "sex", "");
                        result.append("????????????"+sexName);
                    }else {
                        result.append("????????????"+"");
                    }
                    if (null != obj.get("nation") && !"".equals(obj.get("nation"))){
                        nationName = DictUtils.getDictLabel(obj.get("nation").toString(), "nation", "");
                        result.append("????????????"+nationName);
                    }else {
                        result.append("????????????"+"");
                    }
                    if (null != obj.get("politicsFace") && !"".equals(obj.get("politicsFace"))){
                        mianMao = DictUtils.getDictLabel(obj.get("politicsFace").toString(), "political_status", "");
                        result.append("??????????????????"+mianMao);
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("workunitId") && !"".equals(obj.get("workunitId"))){
                        workName = obj.get("workunitName").toString();
                        result.append("??????????????????"+workName);
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("approcalOrgType") && !"".equals(obj.get("approcalOrgType"))){
                        orgType = DictUtils.getDictLabel(obj.get("approcalOrgType").toString(), "personnel_jgtype", "");
                        result.append("????????????????????????"+orgType);
                    }else {
                        result.append("????????????????????????"+"");
                    }
                    if (null != obj.get("rewardName") && !"".equals(obj.get("rewardName"))){
                        result.append("??????????????????"+obj.get("rewardName"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("cjType") && !"".equals(obj.get("cjType"))){
                        cjTypes = DictUtils.getDictLabel(obj.get("cjType").toString(), "personnel_cjtype", "");
                        result.append("??????????????????"+cjTypes);
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("cjName") && !"".equals(obj.get("cjName"))){
                        result.append("??????????????????"+obj.get("cjName"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("cjOrgType") && !"".equals(obj.get("cjOrgType"))){
                        cjOrgTypes = DictUtils.getDictLabel(obj.get("cjOrgType").toString(), "personnel_jgtype", "");
                        result.append("??????????????????????????????"+cjOrgTypes);
                    }else {
                        result.append("??????????????????????????????"+"");
                    }
                    if (null != obj.get("year") && !"".equals(obj.get("year"))){
                        result.append("??????????????????"+obj.get("year"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("conclusion") && !"".equals(obj.get("conclusion"))){
                        result.append("??????????????????"+obj.get("conclusion"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("xlName") && !"".equals(obj.get("xlName"))){
                        result.append("??????????????????"+obj.get("xlName"));
                    }else {
                        result.append("??????????????????"+"");
                    }
                    if (null != obj.get("schoolName") && !"".equals(obj.get("schoolName"))){
                        result.append("????????????????????????"+obj.get("schoolName"));
                    }else {
                        result.append("????????????????????????"+"");
                    }

                    String resultStr = result.toString();
                    if (null != sex && !"".equals(sex)){
                        temp= resultStr.replaceAll(sexName, "<em style= 'color:red;'>"+sexName+"</em>");
                    }else {
                        temp = resultStr;
                    }
                    if (null != nation && !"".equals(nation)){
                        temp= temp.replaceAll(nationName, "<em style= 'color:red;'>"+nationName+"</em>");
                    }
                    if (null != mianmao && !"".equals(mianmao)) {
                        temp = temp.replaceAll(mianMao, "<em style= 'color:red;'>" + mianMao + "</em>");
                    }
                    if (null != unitId && !"".equals(unitId)) {
                         temp = temp.replaceAll(workName, "<em style= 'color:red;'>" + workName + "</em>");
                    }
                    if (null != approcalOrgType && !"".equals(approcalOrgType)) {
                        temp = temp.replaceAll(orgType, "<em style= 'color:red;'>" + orgType + "</em>");
                    }
                    if (null != rewardName && !"".equals(rewardName)) {
                        temp = temp.replaceAll(rewardName, "<em style= 'color:red;'>" + rewardName + "</em>");
                    }
                    if (null != cjType && !"".equals(cjType)) {
                        temp = temp.replaceAll(cjTypes, "<em style= 'color:red;'>" + cjTypes + "</em>");
                    }
                    if (null != cjOrgType && !"".equals(cjOrgType)) {
                        temp = temp.replaceAll(cjOrgTypes, "<em style= 'color:red;'>" + cjOrgTypes + "</em>");
                    }
                    if (null != cjName && !"".equals(cjName)) {
                        temp = temp.replaceAll(cjName, "<em style= 'color:red;'>" + cjName + "</em>");
                    }
                    if (null != year && !"".equals(year)) {
                        temp = temp.replaceAll(year, "<em style= 'color:red;'>" + year + "</em>");
                    }
                    if (null != conclusion && !"".equals(conclusion)) {
                        temp = temp.replaceAll(conclusion, "<em style= 'color:red;'>" + conclusion + "</em>");
                    }
                    if (null != xlName && !"".equals(xlName)) {
                        temp = temp.replaceAll(xlName, "<em style= 'color:red;'>" + xlName + "</em>");
                    }
                    if (null != schoolName && !"".equals(schoolName)) {
                        temp = temp.replaceAll(schoolName, "<em style= 'color:red;'>" + schoolName + "</em>");
                    }
                    obj.put("result", temp);
                }
            }
        }else {
             request.setAttribute("searchContent", searchContent);
            String sex = null;
            String[] nation = null;
            String[] mianmao = null;
            Date age = null;
            Integer Age = null;
            String birth = null;
            String dateBirth = null;
            String[] approcalOrgType = null;
            String rewardName = null;
            String[] cjType = null;
            String cjName = null;
            String[] cjOrgType = null;
            String year = null;
            String conclusion = null;
            String xlName = null;
            String schoolName = null;
            String[] str = null;
            String kpYear = null;
            Integer score = null;
            if(searchContent.indexOf("???")>-1){
                str = searchContent.split("???");
            }else if (searchContent.indexOf(";")>-1){
                str = searchContent.split(";");
            }
            String[] keyArrays = new String[str.length];
            List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < str.length; i++) {
                Map<String, Object> content = new HashMap<String, Object>();
                String str1 = null;
                String str2 = null;
                if (str[i].indexOf("???")> -1){
                    str1 = str[i].substring(0, str[i].indexOf("???"));
                    if ("??????".equals(str1)){
                        Age = Integer.parseInt(str[i].substring(str[i].indexOf("???") + 1));
                        str2 = str[i].substring(str[i].indexOf("???") + 1);
                        Calendar now = Calendar.getInstance();
                        int years  =  now.get(Calendar.YEAR);
                        birth = String.valueOf(years - Age);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                        try {
                            age = formatter.parse(birth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        str2 = str[i].substring(str[i].indexOf("???") + 1);
                    }
                    keyArrays[i] = str[i].substring(str[i].indexOf("???") + 1);
                }else if (str[i].indexOf(":")> -1){
                    str1 = str[i].substring(0, str[i].indexOf("???"));
                    if ("??????".equals(str1)){
                        Age = Integer.parseInt(str[i].substring(str[i].indexOf(":") + 1));
                        str2 = str[i].substring(str[i].indexOf(":") + 1);
                        Calendar now = Calendar.getInstance();
                        int years  =  now.get(Calendar.YEAR);
                        birth = String.valueOf(years - Age);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                        try {
                            age = formatter.parse(birth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        str2 = str[i].substring(str[i].indexOf(":") + 1);
                    }
                    keyArrays[i] = str[i].substring(str[i].indexOf(":") + 1);
                }
                content.put(str1, str2);
                infoList.add(content);
            }
            if (null != infoList) {
                for (Map<String, Object> obj : infoList) {
                    for (String s : obj.keySet()) {
                        if (null != s && "??????".equals(s)) {
                            String sexinfo = obj.get("??????").toString();
                            sex = DictUtils.getDictValue(sexinfo, "sex", "");
                        }
                        if (null != s && "??????".equals(s)) {
                            String nationinfo = obj.get("??????").toString();
                            nation = changeDict(nationinfo, "nation");
//                        DictUtils.getDictValue(nationinfo, "nation", "");
                        }
                        if (null != s && "????????????".equals(s)) {
                            String mianmaoinfo = obj.get("????????????").toString();
                            mianmao = changeDict(mianmaoinfo, "political_status");
//                        DictUtils.getDictValue(mianmaoinfo, "political_status", "");
                        }
                        if (null != s && "??????".equals(s)) {
                            dateBirth = obj.get("??????").toString();
                        }
                        if (null != s && "??????????????????".equals(s)) {
                            String orginfo = obj.get("??????????????????").toString();
                            approcalOrgType = changeDict(orginfo, "personnel_jgtype");
//                        DictUtils.getDictValue(orginfo, "personnel_jgtype", "");
                        }
                        if (null != s && "????????????".equals(s)) {
                            rewardName = obj.get("????????????").toString();
                        }
                        if (null != s && "????????????".equals(s)) {
                            String cjinfo = obj.get("????????????").toString();
                            cjType = changeDict(cjinfo, "personnel_cjtype");
//                        DictUtils.getDictValue(cjinfo, "personnel_cjtype", "");
                        }
                        if (null != s && "????????????".equals(s)) {
                            cjName = obj.get("????????????").toString();
                        }
                        if (null != s && "????????????????????????".equals(s)) {
                            String cjorginfo = obj.get("????????????????????????").toString();
                            cjOrgType = changeDict(cjorginfo, "personnel_jgtype");
//                        DictUtils.getDictValue(cjorginfo, "personnel_jgtype", "");
                        }
                        if (null != s && "????????????".equals(s)) {
                            year = obj.get("????????????").toString();
                        }
                        if (null != s && "????????????".equals(s)) {
                            conclusion = obj.get("????????????").toString();
                        }
                        if (null != s && "????????????".equals(s)) {
                            xlName = obj.get("????????????").toString();
                        }
                        if (null != s && "??????????????????".equals(s)) {
                            schoolName = obj.get("??????????????????").toString();
                        }
                        if (null != s && "????????????".equals(s)) {
                            kpYear = obj.get("????????????").toString();
                        }
                        if (null != s && "???????????????????????????".equals(s)) {
                            score = Integer.valueOf(String.valueOf(obj.get("???????????????????????????"))).intValue();
//                            score = obj.get("??????????????????");
                        }
                    }
                }
                if (null == approcalOrgType && null == rewardName && null == cjType && null == cjName && null == cjOrgType && null == year && null == conclusion && null == xlName && null == schoolName) {
                    if ( null != kpYear && null != score){
                        List<Map<String, Object>> allInfo = new ArrayList<Map<String, Object>>();
                        List<Map<String, Object>> baseListInfo = new ArrayList<Map<String, Object>>();
                        baseListInfo = searchForService.findBaseLists(sex, nation, mianmao, age);
                        List<Map<String, Object>> listInfo = new ArrayList<Map<String, Object>>();
                        listInfo = searchForService.findInfo(kpYear,score);
                        for (Map<String, Object> map : baseListInfo){
                            allInfo.add(map);
                        }
                        for (Map<String, Object> map : allInfo){
                            for (Map<String, Object> map1 : listInfo){
                                if (map.get("name").toString().equals(map1.get("name").toString())){
                                    map.putAll(map1);
                                }
                            }
                        }
                        for (Map<String, Object> m : allInfo){
                            list.add(m);
                        }
                    }else {
                        list = searchForService.findBaseLists(sex, nation, mianmao, age);
                    }

//                    list = searchForService.findBaseLists(sex, nation, mianmao, age);
//                    list = searchForService.findInfo(kpYear,score);
                }else if (null == sex && null == nation && null == mianmao && null == rewardName &&null == approcalOrgType && null == rewardName && null == cjType && null == cjName && null == cjOrgType && null == year && null == conclusion && null == xlName && null == schoolName) {
                    list = searchForService.findInfo(kpYear,score);
                } else {
                    list = searchForService.findLists(sex, nation, mianmao, rewardName, approcalOrgType, cjType, cjName, cjOrgType, year, conclusion, xlName, schoolName);
                }
                if (null != list) {
                    for (Map<String, Object> obj : list) {
                        StringBuffer result = new StringBuffer();
                        String sexName = null;
                        String mianMao = null;
                        String nationName = null;
                        String orgType = null;
                        String cjTypes = null;
                        String cjOrgTypes = null;
                        if (null != obj.get("idNumber") && !"".equals(obj.get("idNumber"))) {
                            result.append("??????????????????" + obj.get("idNumber"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("policeIdNumber") && !"".equals(obj.get("policeIdNumber"))) {
                            result.append("??????????????????" + obj.get("policeIdNumber"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("sex") && !"".equals(obj.get("sex"))) {
                            sexName = DictUtils.getDictLabel(obj.get("sex").toString(), "sex", "");
                            result.append("????????????" + sexName);
                        } else {
                            result.append("????????????" + "");
                        }
                        if (null != obj.get("nation") && !"".equals(obj.get("nation"))) {
                            nationName = DictUtils.getDictLabel(obj.get("nation").toString(), "nation", "");
                            result.append("????????????" + nationName);
                        } else {
                            result.append("????????????" + "");
                        }
                        if (null != obj.get("politicsFace") && !"".equals(obj.get("politicsFace"))) {
                            mianMao = DictUtils.getDictLabel(obj.get("politicsFace").toString(), "political_status", "");
                            result.append("??????????????????" + mianMao);
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("birthday") && !"".equals(obj.get("birthday"))) {
                            result.append("????????????" + sumAge(obj.get("birthday").toString()));
                        } else {
                            result.append("????????????" + "");
                        }
                        if (null != obj.get("approcalOrgType") && !"".equals(obj.get("approcalOrgType"))) {
                            orgType = DictUtils.getDictLabel(obj.get("approcalOrgType").toString(), "personnel_jgtype", "");
                            result.append("????????????????????????" + orgType);
                        } else {
                            result.append("????????????????????????" + "");
                        }
                        if (null != obj.get("rewardName") && !"".equals(obj.get("rewardName"))) {
                            result.append("??????????????????" + obj.get("rewardName"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("cjType") && !"".equals(obj.get("cjType"))) {
                            cjTypes = DictUtils.getDictLabel(obj.get("cjType").toString(), "personnel_cjtype", "");
                            result.append("??????????????????" + cjTypes);
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("cjName") && !"".equals(obj.get("cjName"))) {
                            result.append("??????????????????" + obj.get("cjName"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("cjOrgType") && !"".equals(obj.get("cjOrgType"))) {
                            cjOrgTypes = DictUtils.getDictLabel(obj.get("cjOrgType").toString(), "personnel_jgtype", "");
                            result.append("??????????????????????????????" + cjOrgTypes);
                        } else {
                            result.append("??????????????????????????????" + "");
                        }
                        if (null != obj.get("year") && !"".equals(obj.get("year"))) {
                            result.append("??????????????????" + obj.get("year"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("conclusion") && !"".equals(obj.get("conclusion"))) {
                            result.append("??????????????????" + obj.get("conclusion"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("xlName") && !"".equals(obj.get("xlName"))) {
                            result.append("??????????????????" + obj.get("xlName"));
                        } else {
                            result.append("??????????????????" + "");
                        }
                        if (null != obj.get("schoolName") && !"".equals(obj.get("schoolName"))) {
                            result.append("????????????????????????" + obj.get("schoolName"));
                        } else {
                            result.append("????????????????????????" + "");
                        }
                        if (null != obj.get("avg") && !"".equals(obj.get("avg"))) {
                            String avg = obj.get("avg").toString();
                            result.append("??????????????????" + avg.substring(0, 5));
                        } else {
                            result.append("??????????????????" + "");
                        }

                        String resultStr = result.toString();
                        String temp = resultStr;
                        for(String key:keyArrays){
                           temp = temp.replaceAll(key, "<em style= 'color:red;'>" + key + "</em>");
//                           if (null != sexName && !"".equals(sexName)&&isContains(keyArrays,sexName)) {
//                               temp = resultStr.replaceAll(key, "<em style= 'color:red;'>" + key + "</em>");
//                           }
//                           if (null != nationName && !"".equals(nationName)&&isContains(keyArrays,nationName)) {
//                               temp = resultStr.replaceAll(nationName, "<em style= 'color:red;'>" + nationName + "</em>");
//                           }
//                           if (null != mianMao && !"".equals(mianMao)&&isContains(keyArrays,mianMao)) {
//                               temp = temp.replaceAll(mianMao, "<em style= 'color:red;'>" + mianMao + "</em>");
//                           }
//                           if (null != orgType && !"".equals(orgType)&&isContains(keyArrays,orgType)) {
//                               temp = temp.replaceAll(orgType, "<em style= 'color:red;'>" + orgType + "</em>");
//                           }
//                           if (null != rewardName && !"".equals(rewardName)&&isContains(keyArrays,rewardName)) {
//                               temp = temp.replaceAll(rewardName, "<em style= 'color:red;'>" + rewardName + "</em>");
//                           }
//                           if (null != cjTypes && !"".equals(cjTypes)&&isContains(keyArrays,cjTypes)) {
//                               temp = temp.replaceAll(cjTypes, "<em style= 'color:red;'>" + cjTypes + "</em>");
//                           }
//                           if (null != cjOrgTypes && !"".equals(cjOrgTypes)&&isContains(keyArrays,cjOrgTypes)) {
//                               temp = temp.replaceAll(cjOrgTypes, "<em style= 'color:red;'>" + cjOrgTypes + "</em>");
//                           }
//                           if (null != cjName && !"".equals(cjName)&&isContains(keyArrays,cjName)) {
//                               temp = temp.replaceAll(cjName, "<em style= 'color:red;'>" + cjName + "</em>");
//                           }
//                           if (null != year && !"".equals(year)&&isContains(keyArrays,year)) {
//                               temp = temp.replaceAll(year, "<em style= 'color:red;'>" + year + "</em>");
//                           }
//                           if (null != conclusion && !"".equals(conclusion)&&isContains(keyArrays,conclusion)) {
//                               temp = temp.replaceAll(conclusion, "<em style= 'color:red;'>" + conclusion + "</em>");
//                           }
//                           if (null != xlName && !"".equals(xlName)&&isContains(keyArrays,xlName)) {
//                               temp = temp.replaceAll(xlName, "<em style= 'color:red;'>" + xlName + "</em>");
//                           }
//                           if (null != schoolName && !"".equals(schoolName)&&isContains(keyArrays,schoolName)) {
//                               temp = temp.replaceAll(schoolName, "<em style= 'color:red;'>" + schoolName + "</em>");
//                           }
                       }
                        obj.put("result", temp);
                    }
                }
            }
        }
        ModelAndView view = new ModelAndView("modules/search/searchFor");
        view.addObject("list", list);
        return view;
    }

    private String[] changeDict(String strDicts, String map){
        String[] changeInfo = new String[DictUtils.getDictList(map).size()];

        for (int i = 0; i < DictUtils.getDictList(map).size(); i ++){
            Dict dict = DictUtils.getDictList(map).get(i);
            if (dict.getLabel().indexOf(strDicts)> -1){
                changeInfo[i] = dict.getValue();
            }
        }
        return changeInfo;
    }
    private boolean isContains(String[] strArray,String curStr){
        boolean result =false;
        for(String item:strArray){
            if(curStr.indexOf(item)> -1){
                result = true;
                break;
            }
        }
        return result;
    }

    private  int sumAge(String csny) {
        String[] split = csny.split("-");
        if(split.length != 3){
            //???????????????????????????
            return -1;
        }
        //??????????????????
        Calendar now = Calendar.getInstance();
        int year  =  now.get(Calendar.YEAR);
        int age = year - Integer.parseInt(split[0]);
        if(age <0){
            return -1;
        }else {
            return age;
        }
    }

    @RequestMapping(value = "nav")
    public String personNav(PersonnelBase personnelBase, HttpServletRequest request) {
        if(request.getParameter("idNumber")!=null && !"".equals(request.getParameter("idNumber"))){
            personnelBase.setIdNumber(request.getParameter("idNumber").toString());
            request.setAttribute("mType","2");//??????????????????
        }
        List<Map<String, String>> list=personnelBaseService.findNavList();
        StringBuffer sqlBuffer= new StringBuffer();
        for(int i=0;i<list.size();i++){
            if(i==0){
                sqlBuffer.append("SELECT count(1) totalnum,'");
            }
            else{
                sqlBuffer.append("union all SELECT count(1) totalnum,'");
            }
            Map<String,String> item= list.get(i);
            sqlBuffer.append(item.get("table_name").toString());
            sqlBuffer.append("' tableName FROM ");
            sqlBuffer.append(item.get("table_name").toString());
            sqlBuffer.append(" t where t.id_number='");
            sqlBuffer.append(personnelBase.getIdNumber().toString());
            sqlBuffer.append("' ");
            sqlBuffer.append(" and del_flag='0' ");
        }
        Map<String,String> param = new HashMap<String, String>();
        param.put("sql",sqlBuffer.toString());
        List<Map<String, String>> totalNumlist = personnelBaseService.findTotalNum(param);
        for(int i=0;i<list.size();i++){
            Map<String,String> item= list.get(i);
            for(Map<String,String> numItem:totalNumlist){
                if(item.get("table_name").equals(numItem.get("tablename"))){
                    item.put("totalnum",numItem.get("totalnum"));
                }
            }
        }
        request.setAttribute("list", list);
        request.setAttribute("personnelBase", personnelBase);
        return "modules/personnel/nav";
    }


    @RequestMapping(value = {"keySearch"})
    public String keySearch(){
        return "modules/search/searchJump";
    }





}
