package com.thinkgem.jeesite.modules.exam.web;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.AESUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Result;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exam.dao.ExamOfficeDao;
import com.thinkgem.jeesite.modules.exam.entity.*;
import com.thinkgem.jeesite.modules.exam.service.*;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.security.token.JwtUtil;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.apache.cxf.version.Version.getName;

/**
 *  只进行自评，所有APP端接口全在此
 *  后续若添加过多 请分controller
 */



@Controller
@RequestMapping(value = "${frontPath}/mobile")
public class ExamMobileController extends BaseController {

    @Autowired
    private ExamWorkflowDatasService examWorkflowDatasService;

    @Autowired
    private ExamWorkflowService examWorkflowService;

    @Autowired
    private ExamWorkflowDefineService examWorkflowDefineService;

    @Autowired
    private ExamStandardBaseInfoService examStandardBaseInfoService;

    @Autowired
    private ExamWorkflowSegmentsService examWorflowSegmentsService;

    @Autowired
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    @Autowired
    private ExamWeightsMainService examWeightsMainService;

    @Autowired
    private ExamUnitAllPublicService examUnitAllPublicService;

    @Autowired
    private ExamUnitAllPublicYearService examUnitAllPublicYearService;

    @Autowired
    private ExamLdScoreMonthService examLdScoreMonthService;

    @Autowired
    private ExamLdScoreService examLdScoreService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ExamOfficeDao examOfficeDao;

    @Autowired
    private ExamWeightsService examWeightsService;

    @Autowired
    private ExamStandardTemplateItemDefineService examStandardTemplateItemDefineService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ExamPushHistoryService examPushHistoryService;

    @Autowired
    private RoleDao roleDao;

    @ResponseBody
    @RequestMapping(value = {"login"})
    public Result login(HttpServletRequest request){
        Result result = new Result();
        JSONObject data = new JSONObject();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String isRememberMe = WebUtils.getCleanParam(request, "rememberMe");
        if (StringUtils.isBlank(isRememberMe)){
            isRememberMe = StringUtils.toString(request.getAttribute("rememberMe"), StringUtils.EMPTY);
        }

        boolean rememberMe =  StringUtils.toBoolean(isRememberMe);;
        String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
        String captcha = WebUtils.getCleanParam(request, "validateCode");
        boolean mobile = true;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);

        SecurityUtils.getSubject().login(token);

        // 校验用户名密码
        User user = UserUtils.getByLoginName(token.getUsername());
        if (user != null) {
            if (Global.NO.equals(user.getLoginFlag())){
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
            String isd = getName();
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(new SystemAuthorizingRealm.Principal(user, token.isMobileLogin()),
                    password, ByteSource.Util.bytes(salt), getName());
            result.setResult(simpleAuthenticationInfo);
            simpleAuthenticationInfo.getPrincipals();
            try {
                String newToken  = JwtUtil.sign(username,password);
                data.put("token",newToken);
                List<Role> roleList;
                Role role=new  Role();
                role.setUser(user);
                roleList =roleDao.findList(role);

                for(Role item :roleList) {
                    switch (item.getId()) {
                        case "230e916609a349e68f7186f784e11419":
                            data.put("examType", "5");
                            break;
                        case "13bac520ae4d4160b595384691b443fd":
                        case "62cf6aaea0184d3b8cfbce1700ad0e38":
                        case "ff24c3288860447d867d08d462a2a2b9":
                            data.put("examType", "6");
                            break;
                        case "2a7ea380b14f4870b6c44be21d2443c3":
                            data.put("examType", "7");
                            break;
                        default:
//                            data.put("examType", "-1");
                            break;
                    }
                    if (data.get("examType") !=null){
                        break;
                    }
                }
                result.setSuccess(true);
                result.setResult(data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = {"getToken"})
    public Result getToken(HttpServletRequest request,@RequestBody JSONObject params){
        logger.error("APP端请求token");
        Result result = new Result();

        String policeId = null;
        String cardId = null;
        try {
            byte[] staffCode =params.getString("staffCode").getBytes();
            policeId = new String(AESUtils.decrypt(staffCode,AESUtils.SECRET_KEY));
            cardId = new String(AESUtils.decrypt(params.getString("staffIdno").getBytes(),AESUtils.SECRET_KEY));
//            policeId = params.getString("staffCode");
//            cardId = params.getString("staffIdno");
            if (StringUtils.isBlank(cardId)){
                new RuntimeException("获取token时，警号不能为空");
            }
        User user = userDao.getInfoByIdNumber(cardId);
//        User user = UserUtils.getByLoginName(policeId.trim());
            if (user == null){
                logger.error("身份证号查询失败"+cardId);
                result.setSuccess(false);
                result.setMessage("身份证号查询失败");
                return result;
            }
        Role role=new  Role();
        role.setUser(user);
        JSONObject data =new JSONObject();
        List<Role> roleList =roleDao.findList(role);
        for(Role item :roleList) {
            switch (item.getId()) {
                case "230e916609a349e68f7186f784e11419":
                    data.put("examType", "5");
                    break;
                case "13bac520ae4d4160b595384691b443fd":
                case "62cf6aaea0184d3b8cfbce1700ad0e38":
                case "ff24c3288860447d867d08d462a2a2b9":
                    data.put("examType", "6");
                    break;
                case "2a7ea380b14f4870b6c44be21d2443c3":
                    data.put("examType", "7");
                    break;
                default:
//                            data.put("examType", "-1");
                    break;
            }
            if (data.get("examType") !=null){
                break;
            }
        }
        result.setSuccess(true);
        String token = JwtUtil.creatToken(user.getLoginName(),user.getNo());
        data.put("token",token);
        logger.error("token获取成功");
        result.setResult(data);
        } catch (Exception e) {
            logger.error("APP端token获取失败");
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("token获取失败");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("policeId",policeId);
            jsonObject.put("cardId",cardId);
            result.setResult(jsonObject);
            e.printStackTrace();
        }
        return result;
    }


    @ResponseBody
    @RequestMapping(value = {"flowList"})
    public Result selectFlowList(HttpServletRequest request,@RequestBody JSONObject params) {
        logger.error("APP端访问考评接口");
        Result result1 = new Result();
        String token =  request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        if (StringUtils.isBlank(token)){
            result1.setSuccess(false);
            result1.setMessage("token验证失败，请重新登录");
            return result1;
        }

        token = UserUtils.getByLoginName(token).getId();
        Result result = new Result();
        JSONObject data = new JSONObject();
        Map<String, String> param = new HashMap<String, String>();
        param.put("DEL_FLAG_NORMAL", "0");
        String examType = "";
        if (params.getString("examType") != null) {
            examType = params.getString("examType");
            if (examType.equals("1") || examType.equals("2") || examType.equals("3") || examType.equals("4")){
                result.setResult(null);
                result.setSuccess(true);
                result.setMessage("仅提供个人考评数据，单位考评无数据");
            }
            param.put("examType", examType);
            data.put("examType", examType);
        }else {
            result.setResult(null);
            result.setSuccess(true);
            result.setMessage("请求参数为空");
            logger.error("请求参数为空");
            return result;
        }
        if (params.getString("history") != null) {
            param.put("history", "true");
        }
        List<Map<String, String>> list = examWorkflowService.selectFlowList(param);
        List<Map<String, String>> resultList = this.getCurPersonFlowsInfo(list, examType, token, params);
        data.put("list", resultList);
        String url = "modules/exam/examFlowList";
        if (null != params.getString("history") && "true".equals(params.getString("history").toString())) {
            url = "modules/exam/examAlreadyFlowList";
        }


        result1.setResult(data);
        result1.setSuccess(true);
        result1.setMessage("200");

        logger.error("访问结束");
        return result1;
    }

    @ResponseBody
    @RequestMapping(value = "examBeta")
    public Result examBeta(HttpServletRequest request,@RequestBody JSONObject params) {
        String token = request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        Result result = new Result();
        if (StringUtils.isBlank(token)){
            result.setSuccess(false);
            result.setMessage("token验证失败，请重新登录");
            return result;
        }
        token = UserUtils.getByLoginName(token).getId();
        String uId = token;
        JSONObject data = new JSONObject();
        String fillPersonId = params.getString("fillPersonId");
        String workflowId = params.getString("examWorkflowId");
        /*全局公示时查看详细*/
        String publicDetail = params.getString("publicDetail");
        /*被考评对象查看时 noTree 为true*/
        String noTree =params.getString("noTree");
        data.put("noTree",noTree);
        String userId = UserUtils.get(token).getId();
        String examType =params.getString("examType");
        if (StringUtils.isBlank(examType)){
            examType = "";
        }

        ExamWorkflow examWorkflow = examWorkflowService.get(workflowId);

        Map<String, Object> segmentsParam = new HashMap<String, Object>();
        segmentsParam.put("flowId", examWorkflow.getId());
        segmentsParam.put("DEL_FLAG_NORMAL", "0");

        List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(segmentsParam);
        data.put("segmentsList", segmentsList);

        String targetUrl = "modules/exam/examFlowList";
        int status = examWorkflow.getStatus();
        ExamWorkflowDefine examWorkflowDefine = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String standardIds = examWorkflowDefine.getTemplatesIds();

//		List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoList(standardIds, examWorkflow, userId);
        /*自评时，无左侧树机构，fillPersonId为空，设置为登录用户的Id*/
        if (StringUtils.isBlank(fillPersonId)){
            fillPersonId = token;
        }
        List<ExamStandardBaseInfo> standardInfoList = this.getStandardInfoListBeta(standardIds, examWorkflow, fillPersonId);
        data.put("processType", "selfAppraise");
        data.put("standardIds", standardIds);
        data.put("workflowId", examWorkflow.getId());
        data.put("standardInfoList", standardInfoList);
        data.put("examType", examWorkflow.getExamType());

        targetUrl = "modules/exam/appraiseBeta";
        data.put("examWorkflow", examWorkflow);
        data.put("examWorkflowDefine", examWorkflowDefine);


//        data.put("fillPersonId",examWorkflowDatas.getFillPersonId());

//        standardInfoList.get(0).getObj();
        List<ExamWorkflowDatas> selectedList =new ArrayList<>();

        /*查询出所有的考评项*/
        Date startDate = new Date();
        String objId =params.getString("objId");
//		for (ExamStandardBaseInfo sd:standardInfoList){
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setWorkflowId(examWorkflow.getId());
//			examWorkflowDatas.setStandardId(sd.getId());
        /*局考处使用objId，其他使用fillPersonnelId*/
        if (StringUtils.isNotBlank(objId)) {
            examWorkflowDatas.setObjId(objId);
            noTree="true";
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            examWorkflowDatas.setFillPersonId(fillPersonId);
        } else {
            examWorkflowDatas.setFillPersonId(userId);
        }
        data.put("fillPersonId",examWorkflowDatas.getFillPersonId());

        /*再加上系统推送过来的考评项  条件 推送失败的*/

//        standardInfoList.get(0).getObj();
        List<ExamWorkflowDatas> tempDatasList;
        /*noTree为空时，查看当前用户审核的数据,不为空时，查看自己自评的所有数据*/
        if (StringUtils.isNotBlank(examWorkflowDatas.getFillPersonId()) && examWorkflowDatas.getFillPersonId().equals(userId)){
            noTree = "true";
        }
        /*绩效办看所有，不是自己相关的不能操作*/
        if (userId.equals("6af0e615e9b0477da82eff06ff532c8b")|| userId.equals("46c521bf67e24db28772b3eac52dc454")||
                userId.equals("978958003ea44a4bba3eed8ee6ceff3c")|| userId.equals("cca66e1339f14799b01f6db43ed16e16")){
//            noTree="true";
        }
        if (StringUtils.isBlank(noTree)){
            /*查询审核的考评项*/
            examWorkflowDatas.setExamPersonId(userId);
            examWorkflowDatas.setDepartSignId(userId);
            examWorkflowDatas.setPartBureausSignId(userId);
            examWorkflowDatas.setAdjustPersonId(userId);
            examWorkflowDatas.setMainBureausSignId(userId);
            tempDatasList = examWorkflowDatasService.findAboutMeList(examWorkflowDatas);
        }else {
            /*noTree 不为空时查看考评对象的所有数据*/
            tempDatasList = examWorkflowDatasService.findList(examWorkflowDatas);
        }
        /*过滤掉未选择的考评项 后续只对选择的考评进行处理(设置权重、显示分值列等)*/
        /*只显示自评选择的考评项*/
        selectedList.addAll(tempDatasList.stream().filter(item -> item.getIsSelected().equals("1")).collect(Collectors.toList()));
//		}
        double sdsd = DateUtils.getTwoDateSecond(startDate,new Date());
        logger.error("查询所有datas数据时间-------------------------------->"+sdsd);
        //获取考评项数据
        Map<String, String> paramStandard = new HashMap<String, String>();
        /*查询出此考评的所有相关标准 并设置valueType 未选择的考评项不设置valueType 和 权重应该没有影响 此处可以先进行过滤 加快计算速度 代码未改*/
        /*缺少扣分的列  其他领导等，待核对补充*/
        selectedList.stream().forEach(workflowItem -> {
            paramStandard.put("examStardardId", workflowItem.getStandardId());
            List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(paramStandard,new String[]{workflowItem.getRowId()});
            for (Map<String, String> item :rowList){
                String itemValue = item.get("item_value");
                String columnName = item.get("column_name");
                if (StringUtils.isBlank(columnName)){
                    columnName="";
                }
                switch (item.get("column_type")){
                    case "10":
                        /*项目*/
                        workflowItem.setS1(itemValue);
                        workflowItem.setProject(itemValue);
                        break;
                    case "4":
                        /*分类*/
                        workflowItem.setS2(itemValue);
                        workflowItem.setType(itemValue);
                        break;
                    case "1":
                        /*评分标准*/
                        workflowItem.setS3(itemValue);
                        break;
                    case "2":
                        /*分值（+）*/
                        workflowItem.setS4(itemValue);
                        workflowItem.setValueType(1);
                        break;
                    case "3":
                        /*分值（-）*/
                        workflowItem.setS5(itemValue);
                        workflowItem.setValueType(-1);
                        break;
                    case "6":
                        /*备注*/
                        workflowItem.setS6(itemValue);
                        break;

				/*	case "11":
						workflowItem.setReduceOne(itemValue);
						break;
					case "12":
						workflowItem.setReduceTwo(itemValue);
						break;*/
                }

                if (columnName.equals("本人")){
                    workflowItem.setBenRen(itemValue);
                    data.put("benRen","show");
                }
                if (columnName.equals("主任")){
                    workflowItem.setZhuRen(itemValue);
                    data.put("zhuRen","show");
                }
                if (columnName.equals("书记")){
                    workflowItem.setShuJi(itemValue);
                    data.put("shuJi","show");
                }
                if (columnName.contains("主管领导")){
                    workflowItem.setZhuGuanLingDao(itemValue);
                    data.put("zhuGuanLingDao","show");
                }
                if (columnName.equals("处长")){
                    workflowItem.setChuZhang(itemValue);
                    data.put("chuZhang","show");
                }
                if (columnName.equals("副处长")){
                    workflowItem.setFuChuZhang(itemValue);
                    data.put("fuChuZhang","show");
                }
                if (columnName.equals("副职")){
                    workflowItem.setFuZhi(itemValue);
                    data.put("fuZhi","show");
                }
                if (columnName.equals("副所长")){
                    workflowItem.setFuSuoZhang(itemValue);
                    data.put("fuSuoZhang","show");
                }
                if (columnName.equals("副支队长")){
                    workflowItem.setFuZhiDuiZhang(itemValue);
                    data.put("fuZhiDuiZhang","show");
                }
                if (columnName.equals("所长")){
                    workflowItem.setSuoZhang(itemValue);
                    data.put("suoZhang","show");
                }
                if (columnName.equals("教导员")){
                    workflowItem.setJiaoDaoYuan(itemValue);
                    data.put("jiaoDaoYuan","show");
                }
                if (columnName.equals("政委")){
                    workflowItem.setZhengWei(itemValue);
                    data.put("zhengWei","show");
                }
                if (columnName.equals("处长")){
                    workflowItem.setChuZhang(itemValue);
                    data.put("chuZhang","show");
                }
                if (columnName.equals("支队长")){
                    workflowItem.setZhiDuiZhang(itemValue);
                    data.put("zhiDuiZhang","show");
                }
                if (columnName.equals("大队长")){
                    workflowItem.setDaDuiZhang(itemValue);
                    data.put("daDuiZhang","show");
                }
                if (columnName.equals("主席")){
                    workflowItem.setZhuXi(itemValue);
                    data.put("zhuXi","show");
                }
                if (columnName.equals("分管领导")){
                    workflowItem.setFenGuanLingDao(itemValue);
                    data.put("fenGuanLingDao","show");
                }

                if (columnName.contains("公安处")){
                    workflowItem.setGongAnChu(itemValue);
                    data.put("gongAnChu","show");
                }
                if (columnName.contains("责任单位")){
                    workflowItem.setZeRenDanWei(itemValue);
                    data.put("zeRenDanWei","show");
                }
                if (columnName.equals("副主任")){
                    workflowItem.setFuZhuRen(itemValue);
                    data.put("fuZhuRen","show");
                }
                if (columnName.equals("主要领导")){
                    workflowItem.setZhuYaoLingDao(itemValue);
                    data.put("zhuYaoLingDao","show");
                }
                if (columnName.equals("其他副职")){
                    workflowItem.setQiTaFuZhi(itemValue);
                    data.put("qiTaFuZhi","show");
                }
                if (columnName.equals("副大队长")){
                    workflowItem.setFuDaDuiZhang(itemValue);
                    data.put("fuDaDuiZhang","show");
                }
                if (columnName.equals("包保支队领导")){
                    workflowItem.setBaoBaoZhiDui(itemValue);
                    data.put("baoBaoZhiDui","show");
                }
                if (columnName.equals("其他领导")){
                    workflowItem.setQiTaLingDao(itemValue);
                    data.put("qiTaLingDao","show");
                }
                if (StringUtils.isBlank(workflowItem.getSpecial())) {
                    if (columnName.equals("职务") || columnName.equals("加扣分") || columnName.equals("扣分") ||
                            columnName.equals("项目") || columnName.equals("相关业务") || columnName.equals("类别") || columnName.equals("备注") ||
                            columnName.equals("岗位")) {
                        workflowItem.setSpecial(null);
                    }
                    if (columnName.equals("本人") || columnName.equals("主任") || columnName.equals("书记") || columnName.contains("主管领导") || columnName.equals("处长") ||
                            columnName.equals("副处长") || columnName.equals("副职") || columnName.equals("副支队长") || columnName.equals("所长") ||
                            columnName.equals("所长") || columnName.equals("支队长") || columnName.equals("大队长") || columnName.equals("主席") ||
                            columnName.equals("分管领导") || columnName.contains("公安处") || columnName.contains("责任单位") || columnName.equals("副主任") ||
                            columnName.equals("主要领导") || columnName.equals("其他副职") || columnName.equals("副大队长") || columnName.equals("包保支队领导") ||
                            columnName.equals("其他领导")) {
                        workflowItem.setSpecial("no");
                    }
                }
                /*保存加扣分类别*/

            }
//			examWorkflowDatasService.save(examWorkflowDatas);
        });

        double sdfs = DateUtils.getTwoDateSecond(startDate,new Date());
        logger.error("查询所有考评标准时间-------------------------------->"+sdfs);
        List<ExamWorkflowDatas> valueTypeList = selectedList.stream().filter(item -> item.getValueType()==null).collect(Collectors.toList());
        if (valueTypeList != null && valueTypeList.size()>0){
            /*设置权重以便算分*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    selectedList.stream().forEach(item ->{
                        examWorkflowDatasService.save(item);
                    });
                }
            }).start();
        }
        List<ExamWorkflowDatas> weightList = selectedList.stream().filter(item -> item.getWeight()==null).collect(Collectors.toList());
        if (weightList != null && weightList.size()>0){
            /*设置权重以便算分*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    selectedList.stream().filter(item->item.getWeight()==null || StringUtils.isBlank(item.getWorkName())).forEach(item->setWeightBeta(item,examWorkflow.getExamType(),uId));
                }
            }).start();
        }

        /*		*//*查询推送过来的考评项*//*
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.getUser().getId());
        }
        selectedList.addAll(examWorkflowDatasService.findList(temp));*/

        /*推送失败的重新选择后进行过滤*/
        List<ExamWorkflowDatas> workflowDatasList=selectedList.stream().filter(workflowDatas -> !workflowDatas.getIsSelected().equals("0")&& StringUtils.isNotBlank(workflowDatas.getStandardId())).collect(Collectors.toList());

		/*workflowDatasList.stream().forEach(examWorkflowDatas -> {
			//获取考评项数据
			Map<String, String> param = new HashMap<String, String>();
			param.put("examStardardId", examWorkflowDatas.getStandardId());
			List<Map<String, String>> rowList=examStandardTemplateDefineService.findTemplateDatas(param,new String[]{examWorkflowDatas.getRowId()});
			for (Map<String, String> item :rowList){
				switch (item.get("column_type")){
					case "10":
						*//*项目*//*
						examWorkflowDatas.setS1(item.get("item_value"));
						examWorkflowDatas.setProject(item.get("item_value"));
						break;
					case "4":
						*//*分类*//*
						examWorkflowDatas.setS2(item.get("item_value"));
						examWorkflowDatas.setType(item.get("item_value"));
						break;
					case "1":
						*//*评分标准*//*
						examWorkflowDatas.setS3(item.get("item_value"));
						break;
					case "2":
						*//*分值（+）*//*
						examWorkflowDatas.setS4(item.get("item_value"));
						examWorkflowDatas.setValueType(1);
						break;
					case "3":
						*//*分值（-）*//*
						examWorkflowDatas.setS5(item.get("item_value"));
						examWorkflowDatas.setValueType(-1);
						break;
					case "6":
						*//*备注*//*
						examWorkflowDatas.setS6(item.get("item_value"));
						break;
				}

			}
		});*/


        String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
        data.put("status", examWorkflow.getStatus());
        if (examWorkflow.getStatus() < WorkFloatConstant.ALL_PUBLIC) {
            nextStatus = this.getNextStepStatus(segmentsList, examWorkflow.getStatus());
            data.put("nextStep", nextStatus);
        }
        String NNextStep = this.getNextStepStatus(segmentsList, Integer.parseInt(nextStatus));
        /*设置下一环节的推送人  不是系统公示就设置下一步，系统公示的时候初审人员修正，推送到下下一步*/
        if (nextStatus.equals(String.valueOf(WorkFloatConstant.EXAM_PUBLIC))) {
            this.setPersonModel(NNextStep, examWorkflow.getExamType(), workflowDatasList, data);
        } else {
            this.setPersonModel(nextStatus, examWorkflow.getExamType(), workflowDatasList, data);
        }
        data.put("nnextStep", NNextStep);
        String history =params.getString("history");

        /*当考评一个考评对象推动到下一个流程时 ， 使用history来控制 提交按钮是否显示*/
        if (selectedList != null && selectedList.size()>0 && String.valueOf(selectedList.get(0).getStatus()).equals(nextStatus) ){
            history ="true";
        }
        if (null != history) {
            data.put("history", history);
            Map<String, String> sParam = new HashMap<String, String>();
            String personId = UserUtils.get(token).getId();
            sParam.put("id", examWorkflow.getId());
            sParam.put("personId", personId);
            List<Map<String, String>> datasList = examWorkflowService.selectByPerson(sParam);
            if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC || examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN) {
                data.put("isSave", String.valueOf(this.isSave(examWorkflow, datasList, personId)));
            }
        }
        String[] columnList =new String[] {"9","4","1","2","3","6"};
        /*查询已经保存的所有的考评项 */
        data.put("columnList",columnList);

        /*查询推送失败，添加的考评项*/
        ExamWorkflowDatas temp = new ExamWorkflowDatas();
        temp.setWorkflowId(workflowId);
        temp.setIsAlreadySelected("99");
        if (StringUtils.isNotBlank(objId)) {
            temp.setObjId(objId);
        } else if (StringUtils.isNotBlank(fillPersonId)) {
            temp.setFillPersonId(fillPersonId);
        } else {
            temp.setFillPersonId(UserUtils.get(token).getId());
        }
        List<ExamWorkflowDatas> pushExamList = examWorkflowDatasService.findList(temp);
//        workflowDatasList.addAll(pushExam.getList().stream().filter(examWorkflowDatas -> examWorkflowDatas.getStandardId()!=null).collect(Collectors.toList()));
        workflowDatasList.addAll(pushExamList);
        /*查询初核人*/
        List<Map<String,String>> noExamList = examWorkflowDatasService.findNoExamList(workflowId,fillPersonId);
        /*初核人为空则显示 选择审核人*/
        if (workflowDatasList== null || workflowDatasList.size()==0){
            if (noExamList == null || noExamList.size() == 0){
            }else if (noExamList.size()==1) {
                /*选择项为空 并且初核人只有一个则显示初核人*/
                data.put("person",noExamList.get(0).get("examperson"));
                data.put("personId",noExamList.get(0).get("exampersonid"));
            }else {
                /*未选择 但是审核人有多个时无法显示*/
                data.put("noSelect",false);
            }
            data.put("noSelect",true);
        }else {
            data.put("noSelect",false);
        }
        data.put("workflowDatasList",workflowDatasList);
        /*根据状态跳转到不同的页面*/
        status = examWorkflowDatasService.getStatusById(fillPersonId,workflowId,status,UserUtils.get(token).getId());
        if (WorkFloatConstant.NO_START != status && WorkFloatConstant.EXAM_END != status) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("flowId", examWorkflow.getId());
            param.put("DEL_FLAG_NORMAL", "0");
            List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
            if (WorkFloatConstant.SELF_EXAM == status && this.isExamRole(UserUtils.get(token).getRoleList(), examWorkflow.getExamType())) {
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
//                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
//                data.put("objList", objList);
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
                List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
                data.put("objList", resultObjList);
                data.put("processType", "appraiseExam");



                targetUrl = "modules/exam/appraise_simple";
            } else if (WorkFloatConstant.SELF_EXAM == status) {
                data.put("processType", "selefAppraise");
                data.put("standardIds", standardIds);
                data.put("standardInfoList", standardInfoList);
                data.put("page","appraise");
                targetUrl = "modules/exam/appraiseBeta";
            } else if (WorkFloatConstant.SIMPLE_EXAM == status || (this.isExamRole(UserUtils.get(token).getRoleList(), examWorkflow.getExamType()) && WorkFloatConstant.EXAM_PUBLIC == status)) {
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
                //objParam.put("group", "personAndStandId");
                //List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjDataList(objParam);
                List<Map<String, Object>> resultObjList = this.checkObjectList(objList);
                data.put("objList", resultObjList);
                data.put("processType", "appraiseExam");
                targetUrl = "modules/exam/appraiseBeta";
                /*初步审核页面*/
                targetUrl = "modules/exam/appraiseExamBeta";
                data.put("page","appraiseExam");
//				targetUrl = "modules/exam/appraiseExamIndexBeta";
            } else if (WorkFloatConstant.EXAM_PUBLIC == status) {
               /* Map<String, Object> objParam = new HashMap<String, Object>();

                objParam.put("workflowId", examWorkflow.getId());
                //系统公示显示分数
                objParam.put("fillPersonId",fillPersonId);//自评人id
                List<Map<String, Object>> objDatasList = examWorkflowDatasService.getAppraiseObjDataList2(objParam);
                List<Map<String, Object>> objScoreList = examWorkflowDatasService.getAppraiseObjScoreList2(objDatasList,workflowId,fillPersonId,examWorkflow);
                data.put("objScoreList", this.getCurrentObjs(objScoreList));
                objParam.put("workflowId", examWorkflow.getId());
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
                List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
                String personId = UserUtils.get(token).getId();
                //绩效考评员
                Set<String> examSet = new HashSet<String>();
                examSet.add("cca66e1339f14799b01f6db43ed16e16");
                examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
                examSet.add("46c521bf67e24db28772b3eac52dc454");
                examSet.add("6af0e615e9b0477da82eff06ff532c8b");
                if (null != objList) {
                    for (Map<String, Object> obj : objList) {
                        if (personId.equals(obj.get("id")) || examSet.contains(personId)) {
                            resObjList.add(obj);
                        }
                    }
                }
                data.put("objList", resObjList);
                data.put("examTypePublic", examWorkflow.getExamType());//考评类型 1 局考处 2 局考局机关 3 处考队所 4 处考处机关 5 处领导 6 中基层 7民警
                *//*局考处的页面*//*
                if (examWorkflow.getExamType().equals("1")){
                    Map<String, Object> resultMap = examWorkflowDatasService.getPublicBetaScoreList(workflowId,fillPersonId);
                    data.put("unitList", resultMap.get("unitList"));
                    data.put("weigthsList", resultMap.get("weigthsList"));
                    data.put("conventionScoreList", resultMap.get("conventionScoreList"));
                    if (fillPersonId.equals(UserUtils.get(token).getId())){
                        *//*公示时 自评人员只能查看，显示列表*//*
                        data.put("isFillPerson", resultMap.get("isFillPerson"));
                        targetUrl = "modules/exam/publicListJuKaoChuBeta";
                        data.put("page","publicListJuKaoChu");
                    }else {

                        targetUrl = "modules/exam/publicJuKaoChuBeta";
                        data.put("page","publicJuKaoChu");
                    }
                }else {
                    *//*其他页面*//*
                    if (fillPersonId.equals(UserUtils.get(token).getId())){
                        *//*公示时 自评人员只能查看，显示列表*//*
                        targetUrl = "modules/exam/publicListBeta";
                        data.put("page","publicList");
                    }else {
                        targetUrl = "modules/exam/publicBeta";
                        data.put("page","public");
                    }
                }*/

            } else if (WorkFloatConstant.DEP_SIGN == status || WorkFloatConstant.PART_SIGN == status || WorkFloatConstant.COMPLET_SIGN == status) {
                data.put("standardInfoList", standardInfoList);
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
                objParam.put("group", "obj");
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
                data.put("objList", objList);
                if (null !=params.getString("objId")) {
                    data.put("objId",params.getString("objId"));
                } else if (null != objList) {
                    data.put("objId", objList.get(0).get("id"));
                }
                targetUrl = "modules/exam/signBeta";

                String processType = "";
                if (WorkFloatConstant.DEP_SIGN == status) {
                    processType = "departSign";
                    targetUrl = "modules/exam/signContent2Beta";
                    data.put("page","signContent2");
                } else if (WorkFloatConstant.PART_SIGN == status) {
                    processType = "partBureausSign";
                    targetUrl = "modules/exam/signContentPart2Beta";
                    data.put("page","signContentPart2");
                } else if (WorkFloatConstant.COMPLET_SIGN == status) {
                    processType = "mainBureausSign";
                    targetUrl = "modules/exam/signContentMain2Beta";
                    data.put("page","signContentMain2");
                }
                data.put("processType", processType);
            } else if (WorkFloatConstant.GROUP_ADJUST == status) {
                data.put("standardInfoList", standardInfoList);
                Map<String, Object> objParam = new HashMap<String, Object>();
                objParam.put("workflowId", examWorkflow.getId());
                objParam.put("group", "obj");
                List<Map<String, Object>> objList = examWorkflowDatasService.getAppraiseObjList(objParam);
                data.put("objList", objList);
                String objName = "";
                if (null !=params.getString("objId")) {
                    objId =params.getString("objId");
                    objName =params.getString("objName");
                } else {
                    User user = UserUtils.get(fillPersonId);
                    objId = user.getCompany().getId();
                    objName = user.getCompany().getName();
                }
                data.put("objId", objId);
                data.put("objName", objName);
                data.put("processType", "groupAdjust");
//				targetUrl = "modules/exam/appraise_adjust_leader";
                targetUrl = "modules/exam/appraiseAdjustLeaderBeta";
                data.put("page","appraiseAdjustLeader");
            } else if (WorkFloatConstant.ALL_PUBLIC == status) {
                if (StringUtils.isNotBlank(publicDetail)&& publicDetail.equals("true")){
                    targetUrl = "modules/exam/publicDetailBeta";
                    data.put("page","publicDetail");
                }else {
                    targetUrl = this.allPublic(examWorkflow, data, params);
                }
            } else {
                targetUrl = "modules/exam/examFlowList";
            }
            data.put("workflowId", examWorkflow.getId());
            data.put("status", status);
            data.put("segmentsList", list);
            data.put("personType",params.getString("personType"));
//			String nextStatus = String.valueOf(WorkFloatConstant.EXAM_END);
            if (status < WorkFloatConstant.ALL_PUBLIC) {
                nextStatus = this.getNextStepStatus(segmentsList, examWorkflow.getStatus());
            }
            data.put("nextStep", nextStatus);
            data.put("hisCurStatus", String.valueOf(status));
        }
        if (WorkFloatConstant.ALL_PUBLIC != status){
            //最终结果公示不显示
            //标题头部分  得分  11.18 kevin.jia
            Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore(examWorkflow,fillPersonId);
            if (headItemScoreMap  == null){
                headItemScoreMap = new HashMap<>();
            }
            if (headItemScoreMap.get("cgInfo") == null){
                headItemScoreMap.put("cgInfo",null);
            }
            if (headItemScoreMap.get("zdInfo") == null){
                headItemScoreMap.put("zdInfo",null);
            }
            if (headItemScoreMap.get("decInfo") == null){
                headItemScoreMap.put("decInfo",null);
            }
            if (headItemScoreMap.get("addInfo") == null){
                headItemScoreMap.put("addInfo",null);
            }

//			Map<String,Object> headItemScoreMap = examWorkflowDatasService.getHeadItemScore2(examWorkflow,fillPersonId);
            data.put("headItemScoreMap",headItemScoreMap);
        }

        /*初核人员*/
        if (workflowDatasList!= null && workflowDatasList.size()>0){
            String examPersonId = workflowDatasList.get(0).getExamPersonId();
            data.put("examPersonId",examPersonId);
        }
        result.setSuccess(true);
        result.setResult(data);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "selectTerm")
    public Result selectTerm(HttpServletResponse response, HttpServletRequest request, @RequestBody JSONObject params){
        String token = request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        Result result = new Result();
        if (StringUtils.isBlank(token)){
            result.setMessage("token过期，请重新登录");
            result.setSuccess(true);
            return result;
        }
        token = UserUtils.getByLoginName(token).getId();

        JSONObject data = new JSONObject();
        String standardId = params.getString("standardId");
        /*考评标准内容*/
        String standardText = params.getString("standardText");
        if (StringUtils.isBlank(standardId)){
            result.setSuccess(false);
            result.setMessage("标准不可为空");
            return result;
        }
        String workflowId = params.getString("workflowId");
        if (StringUtils.isBlank(workflowId)){
            result.setSuccess(false);
            result.setMessage("流程不可为空");
            return result;
        }
        ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standardId);
        ExamWorkflow examWorkflow=examWorkflowService.get(workflowId);

        String objId = params.getString("objId");
        String fillPersonId = params.getString("fillPersonId");
        if (StringUtils.isBlank(fillPersonId)){
            fillPersonId = UserUtils.get(token).getId();
        }
        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setStandardId(standardId);
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setObjId(objId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setStandardId(standardId);

        data.put("standardId",standardId);
        data.put("standardName",examStandardBaseInfo.getName());
        data.put("workflowId",workflowId);
        data.put("objId",objId);
        data.put("fillPersonId",fillPersonId);

        List<ExamWorkflowDatas> selectedList = examWorkflowDatasService.findList(examWorkflowDatas);
        ExamWorkflow query = new ExamWorkflow();
        query.setCondition(standardText);
        /*模板中的考评项处理*/
        setItems(query,examStandardBaseInfo,selectedList ,null,request,response,params,data);

        /*查询所有的考评标准*/
        ExamWorkflowDefine define = examWorkflowDefineService.get(examWorkflow.getFlowTemplateId());
        String s = define.getTemplatesIds();
        List<ExamStandardBaseInfo> infoList = this.getStandardInfoListBeta(s, examWorkflow, fillPersonId);
        /*代码中过滤查询条件  考评标准的内容*/
//        List<ExamStandardBaseInfo> resultList= infoList.stream().filter(item -> item.getName().contains(standardText)).collect(Collectors.toList());
        data.put("standardInfoList", infoList);
        result.setSuccess(true);
        result.setResult(data);
        result.setMessage("获取标准成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "selectItems")
    public Result selectItemDatas( HttpServletRequest request,@RequestBody JSONObject params) {
        String token = request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        Result result = new Result();
        if (StringUtils.isBlank(token)){
            result.setMessage("token过期，请重新登录");
            result.setSuccess(true);
            return result;
        }
        token = UserUtils.getByLoginName(token).getId();

        String standardId = params.getString("standardId");
        String workflowId = params.getString("workflowId");
        String fillPersonId = params.getString("fillPersonId");
        List<String> rowId = (List<String>) params.get("rowId");


        ExamWorkflowDatas examWorkflowDatas = new ExamWorkflowDatas();
        examWorkflowDatas.setStandardId(standardId);
        examWorkflowDatas.setFillPersonId(fillPersonId);
        examWorkflowDatas.setWorkflowId(workflowId);
        examWorkflowDatas.setSelectPersonId(token);
        examWorkflowDatasService.selectedWorkflowDatas(examWorkflowDatas,rowId);

        result.setSuccess(true);
        result.setMessage("成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "removeItems")
    public Result removeItemDatas(HttpServletRequest request,@RequestBody JSONObject params) {
        String token = request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        String userId = UserUtils.getByLoginName(token).getId();
        List<String> ids = (List<String>) params.get("ids");
            Result result = new Result();
            if(ids != null && ids.size() > 0){
                ids.stream().forEach(itemId -> {
                    ExamWorkflowDatas workflowDatas = examWorkflowDatasService.get(itemId);
                    if (StringUtils.isNotBlank(workflowDatas.getSelectPersonId()) && workflowDatas.getSelectPersonId().equals(userId)){
                        examWorkflowDatasService.selfRemoveById(itemId);
                    }else {
                        examWorkflowDatasService.othersRemoveById(itemId,userId);
                    }
                });
                result.setSuccess(true);
                result.setMessage("删除成功");
            }else{
                result.setSuccess(false);
                result.setMessage("请先选择要删除的内容");
            }
            return result;
        }


    @ResponseBody
    @RequestMapping(value = "/saveBeta")
    public Result appraiseSaveBeta(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request,@RequestBody ExamWorkflowDatas examWorkflowDatas) {
        Result result = new Result();
        JSONObject data = new JSONObject();
        String personId = examWorkflowDatas.getPersonId();
        String personName = examWorkflowDatas.getPersonName();
        String personType = examWorkflowDatas.getPersonType();
        String processType = examWorkflowDatas.getProcessType();
        String workflowId = examWorkflowDatas.getWorkflowId();
        /*左侧为树时，结束并推送到下一级时，重定向时使用*/
        String fillPersonId = examWorkflowDatas.getFillPersonId();
        String examType = examWorkflowDatas.getExamType();
        String noTree = request.getParameter("noTree");


        int  nextStatus = examWorkflowDatas.getNextStatus();
        int status = -1;
        if (examWorkflowDatas.getStatus() != null ) {
            status = examWorkflowDatas.getStatus();
        }
        //初核人员、绩效办改分
        String isUpdateSorce = "";
        if (null != request.getParameter("isUpdateSorce")) {
            isUpdateSorce = request.getParameter("isUpdateSorce").toString();
        }
        boolean isHashNoPass = false;
        if (WorkFloatConstant.SIMPLE_EXAM == status) {
            for (ExamWorkflowDatas examWorkflowData : examWorkflowDatas.getDatas()) {
                //不通过
                if (null != examWorkflowData.getOperationStatus() && "2".equals(examWorkflowData.getOperationStatus().toString())) {
                    isHashNoPass = true;
                    break;
                }
            }
        }


        int tempStatus =status;
        ArrayList<ExamWorkflowDatas> workflowDatasList= examWorkflowDatas.getDatas();
        for (ExamWorkflowDatas item :workflowDatasList) {
            /*设置权重使用*/
            item.setExamType(examType);

            if (examWorkflowDatas.getOperationStatus() != null){
                item.setOperationStatus(examWorkflowDatas.getOperationStatus());
            }
            if (examWorkflowDatas.getStatus() != null){
                item.setStatus(examWorkflowDatas.getStatus());
            }
//		    setPerson(personId,personName,item,"");
            if (beanValidator(model, item) && null != item.getRowId()) {
                if (null != request.getParameter("personId") && null != request.getParameter("personName")) {
                    this.setPerson(personId, personName, item, personType);
                }
                if ("selfAppraise".equals(processType)) {
                    /*自评人第一次查看时算分*/
//					this.setWeight(item);
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("appraiseExam".equals(processType)) {
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("departSign".equals(processType)) {
//                    examWorkflowData.setDepartSignId(UserUtils.getUser().getId());
//                    examWorkflowData.setDepartSign(UserUtils.getUser().getName());
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("partBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("mainBureausSign".equals(processType)) {
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("groupAdjust".equals(processType)) {
                    tempStatus = nextStatus;
                    item.setOperationStatus(1);
                } else if ("save".equals(processType)) {
                    /*未提交状态改为以保存，其他 是什么状态就什么状态*/
                    if (item.getOperationStatus() != null && item.getOperationStatus().equals("-1")){
                        item.setOperationStatus(0);
                    }
                    /*自评人第一次查看时算分*/
//					this.setWeight(item);
                }
                if ("true".equals(isUpdateSorce)) {
                    item.setStatus(null);
                    item.setStatus(null);
                } else {
                    item.setStatus(new Integer(tempStatus));
                }
            }
            examWorkflowDatasService.saveBeta(item);
        }


        //查询本阶段是否所有被考评对象的数据都已经处理，若已处理则推送到下一环节
        if ("selfAppraise".equals(processType) || "appraiseExam".equals(processType) || "departSign".equals(processType) ||
                "partBureausSign".equals(processType) || "groupAdjust".equals(processType) ||
                "mainBureausSign".equals(processType) || "mainBureausSignAll".equals(processType) &&
                WorkFloatConstant.ALL_PUBLIC != nextStatus) {
            /*1、被考评人员自评完，推送到系统自评时，把未自评的项推送到下一环节，当所有考评人员对象完成考评时，此考评流程进入下一环节*/
            /*2、					把未自评的项添加上考评人*/
            ExamWorkflowDatas datas = new ExamWorkflowDatas();
            datas.setWorkflowId(workflowId);
            datas.setFillPersonId(fillPersonId);
            /*更新未选择的考评项的状态*/
            datas.setStatus(nextStatus);
            examWorkflowDatasService.updateStatusBeta(datas);
            setPersonBeta(nextStatus,workflowId,fillPersonId,personId,personName);

            ExamWorkflowDatas param = new ExamWorkflowDatas();
            param.setWorkflowId(workflowId);
            //param.setStandardId(standardId);
            String condition = "a.status < " + nextStatus;
            //condition += " AND a.operation_status <> 3";
            param.setStatus(nextStatus);
            param.setCondition(condition);
            Map<String, Object> datasNumber = examWorkflowDatasService.selectDatasNumberBeta(param);
            /*所有的考评项所有被考聘那个对象全部结束当前环节 才推送到下一环节
             * 否则，一个被考评对象进入下一环节后，其他考评对象无法再自评*/
            if (null == datasNumber || "0".equals(datasNumber.get("number").toString())) {
                if (!"mainBureausSignAll".equals(processType)) {
                    Map<String, Object> uptParam = new HashMap<String, Object>();
                    uptParam.put("id", workflowId);
                    uptParam.put("status", nextStatus);
                    /*operationStatus为当前环节的操作状态，下一环节的操作状态是 未处理*/
                    uptParam.put("operationStatus", new Integer("-1"));
                    examWorkflowService.updateStatus(uptParam);
                }
                Map<String, Object> uptDatasParam = new HashMap<String, Object>();
                uptDatasParam.put("workflowId", workflowId);
                uptDatasParam.put("status", nextStatus);
                if (WorkFloatConstant.SELF_EXAM != Integer.valueOf(status)) {
                    uptDatasParam.put("operationStatus", new Integer("-1"));
                }

                /*更新到下一个流程*/
                examWorkflowDatasService.updateStatus(uptDatasParam);
            }
        }
        /*系统初核时，如果有不同过的考评项则回退至系统自评流程，被考评A通过 B不通过，此时A也要重新自评*/
        if (WorkFloatConstant.SIMPLE_EXAM == Integer.valueOf(status) && isHashNoPass) {
            Map<String, Object> uptParam = new HashMap<String, Object>();
            uptParam.put("id", workflowId);
            uptParam.put("status", WorkFloatConstant.SELF_EXAM);
            uptParam.put("operationStatus", new Integer("1"));
            examWorkflowService.updateStatus(uptParam);
        }

        /*自评完应，推送到下一个流程时，应重定向到历史查看*/

        if (processType.equals("save")){
            result.setMessage("保存成功");
            result.setSuccess(true);
            result.setResult(data);
            return result;
        }else {
            data.put("history",true);
            result.setMessage("提交成功");
            result.setSuccess(true);
            result.setResult(data);
            return result;
        }

    }

    @ResponseBody
    @RequestMapping(value = "office/treeData")
    public List<Map<String, Object>> treeDataOffice(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
                                              @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Office> list = officeService.findList(isAll);
        for (int i=0; i<list.size(); i++){
            Office e = list.get(i);
            if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
                    && (type == null || (type != null && (!type.equals("1") || type.equals(e.getType()))))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
                    && Global.YES.equals(e.getUseable())){
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("pIds", e.getParentIds());
                map.put("name", e.getName());
                if (type != null && "3".equals(type)){
                    map.put("isParent", true);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value = "user/treeData")
    public List<Map<String, Object>> treeDataUser(@RequestParam(required=false) String officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId);
        for (int i=0; i<list.size(); i++){
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_"+e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    @ResponseBody
    @RequestMapping(value = "/user/examTreeData")
    public List<Map<String, Object>> examTreeData(@RequestParam(required=false) String officeId, @RequestParam(required = false)String examTreeType, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = systemService.findUserByOfficeId(officeId,examTreeType);
        for (int i=0; i<list.size(); i++){
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_"+e.getId());
            map.put("pId", officeId);
            map.put("name", StringUtils.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 自评推送 的树
     * @param examWorkflowDatas
     * @param extId
     * @param type
     * @param examType
     * @param grade
     * @param isAll
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("office/pushTree")
    public List<Map<String,Object>> push(ExamWorkflowDatas examWorkflowDatas,@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
                                         @RequestParam(required=false) String examType,@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response){
        ExamOffice examOffice = new ExamOffice();
        List<Map<String, Object>> mapList = Lists.newArrayList();

        switch (examWorkflowDatas.getCondition()){
            /*局考处*/
            case "1":
                examOffice.setType("2");
                Map<String,Object> map = new HashMap<>();
                map.put("id","1");
                map.put("name","局考处");
                mapList.add(map);
                break;
            /*局考局机关*/
            case "2":
                examOffice.setType("1");
                Map<String,Object> jukaojujiguan = new HashMap<>();
                jukaojujiguan.put("id","1");
                jukaojujiguan.put("name","局考局机关");
                mapList.add(jukaojujiguan);
                break;
            /*处考所*/
            case "3":
                examOffice.setType("3");
                Map<String,Object> chukaosuo = new HashMap<>();
                chukaosuo.put("id","1");
                chukaosuo.put("name","处考所");
                mapList.add(chukaosuo);
                Map<String,Object> nanning = new HashMap<>();
                nanning.put("id","34");
                nanning.put("pId","1");
                nanning.put("name","南宁处");
                mapList.add(nanning);

                Map<String,Object> beihai = new HashMap<>();
                beihai.put("id","95");
                beihai.put("pId","1");
                beihai.put("name","北海处");
                mapList.add(beihai);

                Map<String,Object> liuzhou = new HashMap<>();
                liuzhou.put("id","156");
                liuzhou.put("pId","1");
                liuzhou.put("name","柳州处");
                mapList.add(liuzhou);
                break;
            /*处考处机关*/
            case "4":
                examOffice.setType("2");
                Map<String,Object> chukaojiguan = new HashMap<>();
                chukaojiguan.put("id","1");
                chukaojiguan.put("name","处考处机关");
                mapList.add(chukaojiguan);
                break;
            /*处考领导班子*/
            case "5":
                examOffice.setType("5");
				/*Map<String,Object> chulingdaobanzi = new HashMap<>();
				chulingdaobanzi.put("id","1");
				chulingdaobanzi.put("name","处领导班子");
				mapList.add(chulingdaobanzi);*/
                break;
            /*中基层领导*/
            case "6":
                examOffice.setType("6");
				/*Map<String,Object> zhongjicenglingdao = new HashMap<>();
				zhongjicenglingdao.put("id","1");
				zhongjicenglingdao.put("name","中基层领导");
				mapList.add(zhongjicenglingdao);*/
                break;
            /*民警*/
            case "7":
                examOffice.setType("7");
				/*Map<String,Object> minjing = new HashMap<>();
				minjing.put("id","1");
				minjing.put("name","民警");
				mapList.add(minjing);*/
                break;
        }
        List<ExamOffice> list = examOfficeDao.findList(examOffice);
        /*去重*/
        list = list.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<ExamOffice>(Comparator.comparing(element -> element.getId()))), ArrayList::new)
        );
        list.sort(Comparator.comparing(ExamOffice::getCode));
        for (int i=0; i<list.size(); i++){
            ExamOffice e = list.get(i);
            if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
                    && (type == null || (type != null && (!type.equals("1") || type.equals(e.getType()))))
                    && (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
                    && Global.YES.equals(e.getUseable())){
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                if (type != null && "3".equals(type)){
                    map.put("isParent", true);
                }
                mapList.add(map);
                if (examOffice.getType().equals("5") || examOffice.getType().equals("6") || examOffice.getType().equals("7")){
                    List<User> userList = systemService.findUserByOfficeId(e.getId(),examOffice.getType());
                    for (int j=0; j<userList.size(); j++){
                        User u = userList.get(j);
                        Map<String, Object> uMap = Maps.newHashMap();
                        uMap.put("id", "u_"+u.getId());
                        uMap.put("pId", e.getId());
                        uMap.put("name", StringUtils.replace(u.getName(), " ", ""));
                        mapList.add(uMap);
                    }
                }
            }
        }

        /*自评时不允许推送，推送树设置为空*/
        mapList = Lists.newArrayList();
        return mapList;
    }

    /**
     * 自评推送 进行保存
     * @param examPushHistory
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "pushSave")
    public Result pushSave(HttpServletRequest request, @RequestBody ExamPushHistory examPushHistory) {
        Result result = new Result();
        String token = request.getHeader("token");
        token=JwtUtil.getLoginName(token);
        User user= UserUtils.getByLoginName(token);
        result.setSuccess(false);
        result.setMessage("自评阶段不能推送");
        return result;
        /*if (user ==null){
            result.setSuccess(false);
            result.setMessage("token验证失败 请重新登录");
            return result;
        }
        String[] ObjNames = examPushHistory.getObjName().split(",");
        String[] ObjIds = examPushHistory.getObjId().split(",");
        examPushHistory.setFromName(user.getName());
        examPushHistory.setFromId(user.getId());
        if (StringUtils.isBlank(examPushHistory.getStatus())){
            examPushHistory.setStatus("1");
        }
        for (int i =0;i <ObjIds.length;i++){
            if (StringUtils.isNotBlank(ObjNames[i])){
                examPushHistory.setId(null);
                User u = userDao.getUnitUserByOfficeId(ObjIds[i]);
                *//*获取不到单位账号则可能是个人账号*//*
                if (u==null){
                    u = UserUtils.get(ObjIds[i]);
                }
                if (u!= null){
                    examPushHistory.setObjId(u.getId());
                    examPushHistory.setObjName(ObjNames[i]);
                    examPushHistoryService.save(examPushHistory);
                }
            }
        }
        result.setSuccess(true);
        result.setMessage("推送成功");
        return result;*/
    }












    private void setPersonBeta(int status,String workflowId,String fillPersonId,String personId,String personName) {
        if ( StringUtils.isBlank(fillPersonId)){
            return;

        }		switch (status){
            case 2:
                ExamWorkflowDatas params = new ExamWorkflowDatas();
                params.setWorkflowId(workflowId);
                params.setFillPersonId(fillPersonId);
                params.setExamPersonId(personId);
                params.setExamPerson(personName);
                params.setStatus(status);
                examWorkflowDatasService.updatePersonBeta(params);
                break;
            case 3:
                ;
                break;
            case 4:
                ExamWorkflowDatas depart = new ExamWorkflowDatas();
                depart.setWorkflowId(workflowId);
                depart.setFillPersonId(fillPersonId);
                depart.setDepartSignId(personId);
                depart.setDepartSign(personName);
                examWorkflowDatasService.updatePersonBeta(depart);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }
    }

    private void setPerson(String personId, String personName, ExamWorkflowDatas examWorkflowData, String personType) {
        if (null != personId && null != personName) {
            if ("selfAppraise".equals(personType)) {
                examWorkflowData.setFillPersonId(personId);
                examWorkflowData.setFillPerson(personName);
            } else if ("appraiseExam".equals(personType)) {
                examWorkflowData.setExamPersonId(personId);
                examWorkflowData.setExamPerson(personName);
            } else if ("departSign".equals(personType)) {
                examWorkflowData.setDepartSignId(personId);
                examWorkflowData.setDepartSign(personName);
            } else if ("partBureausSign".equals(personType)) {
                examWorkflowData.setPartBureausSignId(personId);
                examWorkflowData.setPartBureausSign(personName);
            } else if ("mainBureausSign".equals(personType)) {
                examWorkflowData.setMainBureausSignId(personId);
                examWorkflowData.setMainBureausSign(personName);
            } else if ("groupAdjust".equals(personType)) {
                examWorkflowData.setAdjustPersonId(personId);
                examWorkflowData.setAdjustPerson(personName);
            }
        }
    }

    /**
     * 设置考评项目格式
     *
     * @param examStandardBaseInfo
     * @param request
     * @param response
     */
    private void setItems(ExamWorkflow examWorkflow, ExamStandardBaseInfo examStandardBaseInfo, List<ExamWorkflowDatas> selectedList, String ids,
                          HttpServletRequest request,HttpServletResponse response, JSONObject params, JSONObject data) {
        Map<String, String> dparam = new HashMap<String, String>();
        dparam.put("examStardardId", examStandardBaseInfo.getId());
        ExamStandardTemplateDefine entity = examStandardTemplateDefineService.getNew(dparam);
        if (null != entity) {
            //获取考评项数据格式列
            ExamStandardTemplateItemDefine examStandardTemplateItemDefine = new ExamStandardTemplateItemDefine();
            examStandardTemplateItemDefine.setTemplateId(entity.getId());
            Page<ExamStandardTemplateItemDefine> page = null;
            Page<ExamStandardTemplateItemDefine> pageParam = new Page<ExamStandardTemplateItemDefine>(request, response, -1);
            pageParam.setOrderBy("column_order");
            page = examStandardTemplateItemDefineService.findPage(pageParam, examStandardTemplateItemDefine);
            List<ExamStandardTemplateItemDefine> columnList = page.getList();
            /*根据考评标准内容查询时使用*/

            Optional<ExamStandardTemplateItemDefine> temp= columnList.stream().filter(itemDefine -> itemDefine.getColumnType().equals("1")).findFirst();
            /*考评标准模板中的 考评标准 列 Id*/

            String standardId ;
            /*中基层领导 存在temp为空的情况*/
            if (!temp.isPresent()){
                standardId = "";
            }else {
                standardId = temp.get().getId();
            }
            data.put("columnList", columnList);

            //获取考评项数据
            Map<String, String> param = new HashMap<String, String>();
            param.put("examStardardId", examStandardBaseInfo.getId());
            List<Map<String, String>> list = examStandardTemplateDefineService.findTemplateDatas(param);
            List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
            Map<String, String> rowMap = null;
            String rowNum = "-1";
            for (Map<String, String> item : list) {
                if (!rowNum.equals(String.valueOf(item.get("row_num")))) {
                    rowNum = String.valueOf(item.get("row_num"));
                    rowMap = new HashMap<String, String>();
                    rowMap.put("row_num", rowNum);
                    rowList.add(rowMap);
                }
                rowMap.put(item.get("item_id"), item.get("item_value"));
            }

            List<Map<String, String>> taskRowlist = new ArrayList<Map<String, String>>();
            int selectedNumber = 0;
            if (null != selectedList) {
                for (ExamWorkflowDatas selectedItem : selectedList) {
                    if (null != rowList) {
                        for (Map<String, String> item : rowList) {
                            if (selectedItem.getRowId().equals(item.get("row_num").toString())) {
                                if ("1".equals(selectedItem.getIsSelected())) {
                                    selectedNumber++;
                                }
                                item.put("isSelected", selectedItem.getIsSelected());
                                item.remove("value");
                                item.put("value", selectedItem.getValue());
                                item.remove("detail");
                                item.put("detail", selectedItem.getDetail());
                                item.remove("path");
                                item.put("path", selectedItem.getPath());
                                item.remove("id");
                                item.put("id", selectedItem.getId());
                                item.remove("operationStatus");
                                item.put("operationStatus", String.valueOf(selectedItem.getOperationStatus()));
                                item.remove("fillPerson");
                                item.put("fillPerson", selectedItem.getFillPerson());
                                item.remove("examPerson");
                                item.put("examPerson", selectedItem.getExamPerson());
                                item.remove("departSign");
                                item.put("departSign", selectedItem.getDepartSign());
                                item.remove("partBureausSign");
                                item.put("partBureausSign", selectedItem.getPartBureausSign());
                                item.remove("adjustPerson");
                                item.put("adjustPerson", selectedItem.getAdjustPerson());
                                item.remove("mainBureausSign");
                                item.put("mainBureausSign", selectedItem.getMainBureausSign());
                                item.remove("fillPersonId");
                                item.put("fillPersonId", selectedItem.getFillPersonId());
                                item.remove("examPersonId");
                                item.put("examPersonId", selectedItem.getExamPersonId());
                                item.remove("departSignId");
                                item.put("departSignId", selectedItem.getDepartSignId());
                                item.remove("partBureausSignId");
                                item.put("partBureausSignID", selectedItem.getPartBureausSignId());
                                item.remove("adjustPersonId");
                                item.put("adjustPersonId", selectedItem.getAdjustPersonId());
                                item.remove("mainBureausSignId");
                                item.put("mainBureausSignId", selectedItem.getMainBureausSignId());
                                item.put("status", String.valueOf(selectedItem.getStatus()));
                                item.put("items", selectedItem.getItems());
                                taskRowlist.add(item);
                            }
                        }
                    }
                }
            }

            request.setAttribute("selectedNumber", selectedNumber);
//            JSONObject jsonObject = JSONObject.fromObject(obj);
            /*替换特殊字符串  临时 未优化*/
            taskRowlist.stream().forEach(map -> {
                map.forEach((key, value) -> {
                    if (StringUtils.isNotBlank(value) && (value.contains("\r") || value.contains("\n"))){
                        value=value.replaceAll("\r","");
                        value=value.replaceAll("\n","");
                        map.put(key,value);
                    }
                });
            });
            if (StringUtils.isNotBlank(examWorkflow.getCondition())) {

                taskRowlist = taskRowlist.stream().filter(map -> map.get(standardId).contains(examWorkflow.getCondition())).collect(Collectors.toList());
            }
            data.put("rowlist",taskRowlist);
            request.setAttribute("rowlist", taskRowlist);
            request.setAttribute("rowlistJson", JSONObject.toJSONString(taskRowlist));
        }
    }


    private List<ExamStandardBaseInfo> getStandardInfoListBeta(String standardIds, ExamWorkflow examWorkflow, String fillPersonId) {
        List<ExamStandardBaseInfo> standardInfoList = new ArrayList<ExamStandardBaseInfo>();
        Set<String> examTypeSet = new HashSet<String>();
        //examTypeSet.add("1");     //	局考处
        examTypeSet.add("2");       //局考局机关
        // examTypeSet.add("3");    //	处考队所
        examTypeSet.add("4");       //处考处机关
        //  examTypeSet.add("5");   //	民警考核
        examTypeSet.add("6");
        //examTypeSet.add("7");
        if (examTypeSet.contains(examWorkflow.getExamType())) {
            Map<String, String> param = new HashMap<String, String>();
            param.put("DEL_FLAG_NORMAL", "0");
            param.put("workflowId", examWorkflow.getId());
            param.put("fillPersonId", fillPersonId);
            /*流程状态*/
            /*根据考评流程Id和填写人Id查询考评模板*/
            List<Map<String, String>> standardList = examWorkflowDatasService.findStandardsDataNum(param);
            for (Map<String, String> standard : standardList) {
                if (null != standard.get("number") && !"0".equals(standard.get("number"))) {
                    ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(standard.get("standardid"));
                    standardInfoList.add(examStandardBaseInfo);
                }
            }
        } else {
            for (String id : standardIds.split(",")) {
                ExamStandardBaseInfo examStandardBaseInfo = examStandardBaseInfoService.get(id);
                standardInfoList.add(examStandardBaseInfo);
            }
        }
        return standardInfoList;
    }

    /**
     *  获取用户的考评数据
     *  存在问题 ，当多个环节为同一个人时，环节可能卡在第一个
     *
     * @param list
     * @param examType  考评类型
     * @param person    用户ID
     * @param params
     * @return
     */
    List<Map<String, String>> getCurPersonFlowsInfo(List<Map<String, String>> list, String examType, String person, JSONObject params) {
//        person = "450104196311112010";
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        //List<Map<String, Object>> taskList = examWorkflowSegmentsTaskService.getAllTaskSegmentList();
        Map<String, String> param = new HashMap<String, String>();
        param.put("personId", person);
        List<Map<String, String>> datasList = examWorkflowService.selectByPerson(param);
        //int personStatus = -1;
        if (null != list) {
            for (Map<String, String> flowItem : list) {
                //是否相关
                if (null != datasList && datasList.size() > 0) {
                    for (Map<String, String> data : datasList) {
                        if (data.get("id").equals(flowItem.get("id"))) {
                            /*被考评对象查看时没有树*/
                            if (person.equals(data.get("fill_person_id"))){
                                flowItem.put("noTree","true");
                            }else {
                                flowItem.put("noTree",null);
                            }
                            try {
                                int tempStatus = Integer.valueOf(String.valueOf(flowItem.get("status")));
                                int personnelStatus = examWorkflowDatasService.getStatusById(person,flowItem.get("id"),tempStatus,person);
//                                int personnelStatus = examWorkflowDatasService.getStatusById(UserUtils.getUser().getId(),flowItem.get("id"),tempStatus,UserUtils.getUser().getId());
                                /*当人员状态大于流程状态是，才显示个人状态*/
                                if (tempStatus<personnelStatus){
                                    flowItem.put("status",String.valueOf(personnelStatus));
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (params.getString("history") != null) {
                                if (null != data.get("fill_person_id") && person.equals(data.get("fill_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SELF_EXAM));
                                } else if (null != data.get("exam_person_id") && person.equals(data.get("exam_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SIMPLE_EXAM));
                                } else if (null != data.get("depart_sign_id") && person.equals(data.get("depart_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.DEP_SIGN));
                                } else if (null != data.get("adjust_person_id") && person.equals(data.get("adjust_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.GROUP_ADJUST));
                                } else if (null != data.get("part_bureaus_sign_id") && person.equals(data.get("part_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.PART_SIGN));
                                } else if (null != data.get("main_bureaus_sign_id") && person.equals(data.get("main_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.COMPLET_SIGN));
                                }
                                String personStatus = String.valueOf(flowItem.get("personStatus"));
                                String status = String.valueOf(flowItem.get("status"));
                                /*存在人员流程状态小于考评流程状态时 就跳出此循环*/
                                if (Integer.valueOf(personStatus)<Integer.valueOf(status)){
                                    resultList.add(flowItem);
                                    break;
                                }
                            } else {
                                if (null != data.get("main_bureaus_sign_id") && person.equals(data.get("main_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.COMPLET_SIGN));
                                } else if (null != data.get("part_bureaus_sign_id") && person.equals(data.get("part_bureaus_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.PART_SIGN));
                                } else if (null != data.get("adjust_person_id") && person.equals(data.get("adjust_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.GROUP_ADJUST));
                                } else if (null != data.get("depart_sign_id") && person.equals(data.get("depart_sign_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.DEP_SIGN));
                                } else if (null != data.get("exam_person_id") && person.equals(data.get("exam_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SIMPLE_EXAM));
                                } else if (null != data.get("fill_person_id") && person.equals(data.get("fill_person_id").toString())) {
                                    flowItem.put("personStatus", String.valueOf(WorkFloatConstant.SELF_EXAM));
                                }
                                try{
                                    String personStatus = String.valueOf(flowItem.get("personStatus"));
                                    String status = String.valueOf(flowItem.get("status"));
                                    /*存在人员流程状态大于等于待办时 跳出此循环  系统公示时，自评人查询也是带待办中*/
                                    if (Integer.valueOf(personStatus)>=Integer.valueOf(status) || (personStatus.equals("1") && status.equals("3"))){
                                        resultList.add(flowItem);
                                        break;
                                    }
                                }catch (Exception e){

                                }
                            }


                        }
                    }
                }
            }
        }
        resultList.stream().forEach(item -> {
            item.put("exam_type",DictUtils.getDictLabel(item.get("exam_type"),"kp_type",""));
            item.put("exam_cycle",DictUtils.getDictLabel(item.get("exam_cycle"),"cycle",""));
            item.put("exam_object_type",DictUtils.getDictLabel(item.get("exam_object_type"),"exam_object_type",""));
            Object updateDate = item.get("update_date");
//            SimpleTimeZone
//            String date = DateUtils.formatDateTime(Long.valueOf(updateDate.toString()));
//            new Date(Long.parseLong(updateDate.toString()));
//            item.put("update_date",DateUtils.formatDateTime(Long.valueOf(updateDate.toString())));
            item.put("showStatus",DictUtils.getDictLabel(String.valueOf(item.get("status")),"workflow_status",""));
//            item.put("exam_type",DictUtils.getDictLabel(item.get("exam_type"),"kp_type",""));
        });
        return resultList;
    }

    private void setWeightBeta(ExamWorkflowDatas examWorkflowData,String examType,String userId) {
        ExamWeightsMain examWeightsMain = new ExamWeightsMain();
        String companyId = UserUtils.get(userId).getCompany().getId();
        ExamWeights examWeights = new ExamWeights();
        examWeights.setKpType(examType);
        String officeId = UserUtils.get(userId).getOffice().getId();
        if(StringUtils.isNotBlank(examType) && (examType.equals("3") || examType.equals("4"))){
            try {
                /*根据自评对象设置所属处，防止局绩效办/admin查看导致权重设置错误*/
                User fillPersonUser = UserUtils.get(examWorkflowData.getFillPersonId());
                if(fillPersonUser!=null){
                    if(fillPersonUser.getOffice().getParentIds().contains(",34,")){
                        companyId = "34";
                    }
                    if(fillPersonUser.getOffice().getParentIds().contains(",95,")){
                        companyId = "95";
                    }
                    if(fillPersonUser.getOffice().getParentIds().contains(",156,")){
                        companyId = "156";
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        String officeId = UserUtils.getUser().getOffice().getId();
        if (StringUtils.isNotBlank(examType) && (examType.equals("1") || examType.equals("2"))) {
            examWeights.setKpType(examType);
//            examWeights.setKpChu(companyId);
            examWeights.setKpChu(null);
            examWeights.setDepartmentId(null);
        }
        if (StringUtils.isNotBlank(examType) && examType.equals("3") && !companyId.equals("156")) {
            examWeights.setKpType(examType);
            examWeights.setKpChu(companyId);
            examWeights.setDepartmentId(officeId);
        }
        if (StringUtils.isNotBlank(examType) && examType.equals("3") && companyId.equals("156")) {
            examWeights.setKpType(examType);
            examWeights.setKpChu(companyId);
            examWeights.setDepartmentId(officeId);//21年7月19，北海处绩效办、局绩效办负责人提出，北海处开启两类派出所考评（普铁、高铁）
        }
        if (StringUtils.isNotBlank(examType) && examType.equals("4")){
            //处考处机关
            examWeights.setKpType(examType);
            examWeights.setKpChu(companyId);
            examWeights.setDepartmentId(null);
        }
        examWeights = examWeightsService.getWeightByUnit(examWeights);
        if (examWeights == null){
            examWorkflowData.setWorkName("99");
            examWorkflowData.setWeight(100.0);
            examWorkflowDatasService.save(examWorkflowData);
            return;
        }
        examWeightsMain.setEwId(examWeights.getId());
        //examWeightsMain.setEwId(weightsId);
        List<ExamWeightsMain> datasList = examWeightsMainService.findWeightsDataList(examWeightsMain);
        Double weight = 100.0;
        String workName = "32";
        for (ExamWeightsMain weights : datasList) {
            String name = weights.getWorkName();
            name = DictUtils.getDictLabel(name, "exam_weigths", "");
            if (null != examWorkflowData.getType()) {
                String type = examWorkflowData.getType();
                String project = examWorkflowData.getProject();
                type = type.replaceAll("[\\t\\n\\r]", "");
                if (StringUtils.isNotBlank(project)){
                    project = project.replaceAll("[\\t\\n\\r]", "");
                }
                if (type.indexOf(name) > -1) {
                    weight = weights.getWeights();
                    workName = weights.getWorkName().toString();
                    break;
                }else{
                    if(StringUtils.isNotBlank(project) && project.indexOf(name)>-1){
                        weight = weights.getWeights();
                        workName = weights.getWorkName().toString();
                        break;
                    }else {
                        weight = 100.0;
                        workName = "32";
                    }
                }
            }
        }
        examWorkflowData.setWorkName(workName);
        examWorkflowData.setWeight(weight);
        examWorkflowDatasService.save(examWorkflowData);
    }

    /**
     * 全局公示，相关分值计算的逻辑
     * @param examWorkflow
     * @param data
     * @param params
     * @return
     */
    private String allPublic(ExamWorkflow examWorkflow, JSONObject data,JSONObject params) {
        String targetUrl = "";
        /**
         * 局考处、处考队所一套
         * 局考局机关、处考处机关一套
         * 民警考评一套；处领导、中基层领导、民警
         */
        //月度局考处、处考队所考评
        //if ("1".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
        if ("1".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamType())) {
            Map<String, Object> map = examUnitAllPublicService.getScoreList1(examWorkflow.getId());
            data.put("conventionScoreList", map.get("conventionScoreList"));
            data.put("commonSorceList", map.get("commonSorceList"));
            data.put("weightSorceList", map.get("weightSorceList"));
            data.put("sumSorceList", map.get("sumSorceList"));
            targetUrl = "modules/exam/all_jukaochu";
        }
        //年度局考处、处考队所考评
        //else if ("2".equals(examWorkflow.getExamCycle()) && ("1".equals(examWorkflow.getExamType()) || "3".equals(examWorkflow.getExamType()))) {
        //年度局考处、
        else if ("2".equals(examWorkflow.getExamCycle()) && "1".equals(examWorkflow.getExamType())) {
            Map<String, Object> map = examUnitAllPublicYearService.getScoreList1(examWorkflow.getId());
            data.put("conventionScoreList", map.get("conventionScoreList"));
            data.put("commonSorceList", map.get("commonSorceList"));
            data.put("weightSorceList", map.get("weightSorceList"));
            data.put("sumSorceList", map.get("sumSorceList"));
            targetUrl = "modules/exam/all_jukaochu";
        }
        //月度局考局机关、处考处机关考评
        //else if ("1".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType())  || "4".equals(examWorkflow.getExamType()))) {
        // 月度局考局机关、处考处机关考评
        else if ("1".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType())  || "4".equals(examWorkflow.getExamType()))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreListJG(examWorkflow.getId());
            data.put("list", list);
            data.put("workflowName", examWorkflow.getName());
            if(list!=null && list.size()>0 && "isWeightCalculate".equals(list.get(0).get("isWeightCalculate"))){
                data.put("isWeightCalculate","isWeightCalculate");
            }
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //月度处考队所
        else if("1".equals(examWorkflow.getExamCycle()) &&  "3".equals(examWorkflow.getExamType())){
            ArrayList<Map<String, String>> list = examUnitAllPublicService.getScoreList2(examWorkflow.getId());
            data.put("list", list);
            data.put("workflowName", examWorkflow.getName());
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //年度局考局机关、处考处机关考评
        //else if ("2".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
        //年度局考局机关、处考处机关考评
        else if ("2".equals(examWorkflow.getExamCycle()) && ("2".equals(examWorkflow.getExamType()) || "4".equals(examWorkflow.getExamType()))) {
            ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreListJG(examWorkflow.getId());
            data.put("list", list);
            data.put("workflowName", examWorkflow.getName());
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //年度处考队所
        else if("2".equals(examWorkflow.getExamCycle()) && "3".equals(examWorkflow.getExamType())){
            ArrayList<Map<String, String>> list = examUnitAllPublicYearService.getScoreList2(examWorkflow.getId());
            data.put("list", list);
            data.put("workflowName", examWorkflow.getName());
            targetUrl = "modules/exam/all_jukaojujiguan";
        }
        //月度处领导、中基层领导、民警
        else if ("1".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
            examLdScoreMonth.setWorkflowId(examWorkflow.getId());
            List<ExamLdScoreMonth> page = examLdScoreMonthService.findList(examLdScoreMonth);
            data.put("list", page);
            targetUrl = "modules/exam/examLdScoreMonthListBeta";
            //年度处领导、中基层领导、民警
        } else if ("2".equals(examWorkflow.getExamCycle()) && "2".equals(examWorkflow.getExamObjectType())) {
            ExamLdScore examLdScore = new ExamLdScore();
            examLdScore.setWorkflowId(examWorkflow.getId());
            List<ExamLdScore> page = examLdScoreService.findList( examLdScore);
            data.put("page", page);
            targetUrl = "modules/exam/examLdScoreListBeta";
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", examWorkflow.getId());
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> segmentsList = examWorflowSegmentsService.findSegmentsList(param);
        data.put("hisCurStatus", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
        data.put("status", String.valueOf(WorkFloatConstant.ALL_PUBLIC));
        data.put("segmentsList", segmentsList);
        data.put("workflowId", examWorkflow.getId());
        data.put("workflowId", examWorkflow.getId());
        return targetUrl;
    }

    /**
     * 下一环节状态
     * @param workflowId
     * @param curStatus
     * @return
     */
    private String getNextStepStatus(String workflowId, Integer curStatus) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flowId", workflowId);
        param.put("DEL_FLAG_NORMAL", "0");
        List<Map<String, Object>> list = examWorflowSegmentsService.findSegmentsList(param);
        String nextStepStatus = "99";
        for (int i = 0; i < list.size() - 1; i++) {
            Map<String, Object> segment = list.get(i);
            String sort = segment.get("sort").toString();
            if (String.valueOf(curStatus).equals(sort)) {
                nextStepStatus = list.get(i + 1).get("sort").toString();
                break;
            }
        }
        return nextStepStatus;
    }
    /**
     * 下一步状态
     *
     * @param segmentList
     * @param curStatus
     */
    private String getNextStepStatus(List<Map<String,Object>> segmentList, Integer curStatus) {

        String nextStepStatus = "99";
        for (int i = 0; i < segmentList.size() - 1; i++) {
            Map<String, Object> segment = segmentList.get(i);
            String sort = segment.get("sort").toString();
            if (String.valueOf(curStatus).equals(sort)) {
                nextStepStatus = segmentList.get(i + 1).get("sort").toString();
                break;
            }
        }
        return nextStepStatus;
    }
    private List<Map<String, Object>> getCurrentObjs(List<Map<String, Object>> objScoreList) {
        List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
        String personId = UserUtils.getUser().getId();
        //绩效考评员
        Set<String> examSet = new HashSet<String>();
        examSet.add("cca66e1339f14799b01f6db43ed16e16");
        examSet.add("978958003ea44a4bba3eed8ee6ceff3c");
        examSet.add("46c521bf67e24db28772b3eac52dc454");
        examSet.add("6af0e615e9b0477da82eff06ff532c8b");
        if (null != objScoreList) {
            for (Map<String, Object> obj : objScoreList) {
                if (personId.equals(obj.get("objId")) || examSet.contains(personId)) {
                    resObjList.add(obj);
                }
            }
        }
        return resObjList;
    }

    /**
     * 判断是否绩效考评组
     *
     * @param roleList
     * @return
     */
    private boolean isExamRole(List<Role> roleList, String examType) {
        boolean result = false;
        if (null != roleList) {
            for (Role role : roleList) {
//                if ("绩效考核办".equals(role.getName())) {
//                    result = true;
//                    break;
//                }
                /*目前没有角色含有 绩效考核的*/
                if (null != role.getName() && role.getName().indexOf("绩效考核") > -1) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 设置推送人
     * @param nextStatus
     * @param examType
     * @param selectedList
     * @param data
     */
    private void setPersonModel(String nextStatus, String examType, List<ExamWorkflowDatas> selectedList, JSONObject data) {
        if (null != selectedList && selectedList.size() > 0 && selectedList.get(0) != null) {
            if ("1".equals(nextStatus)) {
                data.put("person", selectedList.get(0).getFillPerson());
                data.put("personId", selectedList.get(0).getFillPersonId());
            } else if ("2".equals(nextStatus)) {
                data.put("person", selectedList.get(0).getExamPerson());
                data.put("personId", selectedList.get(0).getExamPersonId());
            } else if ("3".equals(nextStatus) || "4".equals(nextStatus)) {
                data.put("person", selectedList.get(0).getDepartSign());
                data.put("personId", selectedList.get(0).getDepartSignId());
            } else if ("5".equals(nextStatus)) {
                data.put("person", selectedList.get(0).getPartBureausSign());
                data.put("personId", selectedList.get(0).getPartBureausSignId());
            } else if ("7".equals(nextStatus)) {
                data.put("person", selectedList.get(0).getMainBureausSign());
                data.put("personId", selectedList.get(0).getMainBureausSignId());
            } else if ("6".equals(nextStatus)) {
                if (null != selectedList.get(0).getAdjustPersonId()) {
                    data.put("person", selectedList.get(0).getAdjustPerson());
                    data.put("personId", selectedList.get(0).getAdjustPersonId());
                } else {
                    Map<String, String> personMap = this.getDefaultPerson(examType);
                    data.put("person", personMap.get("person"));
                    data.put("personId", personMap.get("personId"));
                }
            }
        }
    }
    /**
     * 设置默认考评员
     * @param examType
     * @return
     */
    private Map<String, String> getDefaultPerson(String examType) {
        Map<String, String> resultMap = new HashMap<String, String>();
        //局考处，局考局机关
        if ("1".equals(examType) || "2".equals(examType)) {
            resultMap.put("person", "南宁局绩效办");
            resultMap.put("personId", "cca66e1339f14799b01f6db43ed16e16");
        } else{
            String company = UserUtils.getUser().getCompany().getId();
            switch (company) {
                case "1":
                    resultMap.put("person", "南宁局绩效办");
                    resultMap.put("personId", "cca66e1339f14799b01f6db43ed16e16");
                    break;
                case "34":
                    resultMap.put("person", "南宁处绩效办");
                    resultMap.put("personId", "978958003ea44a4bba3eed8ee6ceff3c");
                    break;
                case "95":
                    resultMap.put("person", "柳州处绩效办");
                    resultMap.put("personId", "6af0e615e9b0477da82eff06ff532c8b");
                    break;
                case "156":
                    resultMap.put("person", "北海处绩效办");
                    resultMap.put("personId", "46c521bf67e24db28772b3eac52dc454");
                    break;
            }
        }
        return resultMap;
    }

    /**
     * 是否可以保存
     *
     * @param examWorkflow
     * @param datasList
     * @return
     */
    private boolean isSave(ExamWorkflow examWorkflow, List<Map<String, String>> datasList, String personId) {
        boolean isSave = false;
        if (null != datasList) {
            if (examWorkflow.getStatus() == WorkFloatConstant.EXAM_PUBLIC && personId.equals(datasList.get(0).get("exam_person_id"))) {
                isSave = true;
            } else if (examWorkflow.getStatus() == WorkFloatConstant.COMPLET_SIGN && personId.equals(datasList.get(0).get("adjust_person_id"))) {
                isSave = true;
            }
        }
        return isSave;
    }

    private List<Map<String, Object>> checkObjectList(List<Map<String, Object>> objList) {
        List<Map<String, Object>> resObjectList = new ArrayList<Map<String, Object>>();
        if (null != objList) {
            String objId = "";
            String standardId = "";
            int status = -1;
            Map<String, Object> tmpObj = null;
            for (Map<String, Object> obj : objList) {
                if (null != obj.get("objId") && !objId.equals(obj.get("objId").toString()) && null != obj.get("status")) {
                    objId = obj.get("objId").toString();
                    status = Integer.parseInt(obj.get("status").toString());
                    resObjectList.add(obj);
                    tmpObj = obj;
                }
                if (null != obj.get("standardId") && !objId.equals(obj.get("standardId").toString())) {
                    standardId = obj.get("standardId").toString();
                }
                if (null != obj.get("objId") && objId.equals(obj.get("objId").toString()) && null != obj.get("standardId") && !objId.equals(obj.get("standardId").toString()) && null != obj.get("status")) {
                    int objStatus = Integer.parseInt(obj.get("status").toString());
                    if (status > objStatus) {
                        tmpObj.remove("status");
                        tmpObj.put("status", String.valueOf(objStatus));
                    }
                }
            }
        }
        return resObjectList;
    }

}
