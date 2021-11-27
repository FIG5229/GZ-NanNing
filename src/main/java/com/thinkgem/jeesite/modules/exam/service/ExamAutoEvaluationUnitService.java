package com.thinkgem.jeesite.modules.exam.service;


import com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.exam.dao.ExamAutoEvaluationDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentsDefineDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/*
* 自动考评 获取相关单位
* */
@Service
@Transactional(readOnly = false)
public class ExamAutoEvaluationUnitService {

    @Autowired
    private OfficeDao officeDao;

    @Autowired
    private ExamAutoEvaluationDao examAutoEvaluationDao;

    @Autowired
    private AffairGeneralSituationDao affairGeneralSituationDao;

    @Autowired
    private ExamWorkflowSegmentsDefineDao examWorkflowSegmentsDefineDao;
    /*
     * 局考处单位获取
     * */
    public Map<String, Object> jkcUnit(){


        List<Office> nncPartyList = officeDao.selectChuUnitList("34");
        List<Office> lzcPartyList = officeDao.selectChuUnitList("95");
        List<Office> bhcPartyList = officeDao.selectChuUnitList("156");

        Map<String,Object> jkcUnitMap = new HashMap<>();
        jkcUnitMap.put("nnc",nncPartyList);
        jkcUnitMap.put("lzc",lzcPartyList);
        jkcUnitMap.put("bhc",bhcPartyList);

        return jkcUnitMap;
    }


    /*
    * 局考局单位获取
    * */
    public List<Office> jkjUnit(){

        List<Office> jjgPartyList = examAutoEvaluationDao.selectJjgUnit();
        return jjgPartyList;
    }


    /*
    * 处考处单位获取
    * */
    public Map<String, Object> ckcUnit(){

        List<Office> nncPartyList = officeDao.getChuUnit("34", "1");
        List<Office> lzcPartyList = officeDao.getChuUnit("95", "1");
        List<Office> bhcPartyList = officeDao.getChuUnit("156", "1");

        Map<String,Object> ckcUnitMap = new HashMap<>();
        ckcUnitMap.put("nnc",nncPartyList);
        ckcUnitMap.put("lzc",lzcPartyList);
        ckcUnitMap.put("bhc",bhcPartyList);
        return ckcUnitMap;

    }


    /*
    * 处考派出所单位获取
    * */
    public Map<String, Object> ckpcsUnit(){

        List<AffairGeneralSituation> nncPartyList = affairGeneralSituationDao.getChuPartyBranch("34", "2");
        List<AffairGeneralSituation> lzcPartyList = affairGeneralSituationDao.getChuPartyBranch("95", "2");
        List<AffairGeneralSituation> bhcPartyList = affairGeneralSituationDao.getChuPartyBranch("156", "2");


        Map<String,Object> ckcUnitMap = new HashMap<>();
        ckcUnitMap.put("nnc",nncPartyList);
        ckcUnitMap.put("lzc",lzcPartyList);
        ckcUnitMap.put("bhc",bhcPartyList);
        return ckcUnitMap;

    }


    /*
    * 公安处领导信息
    * */
    public List<User> gacList(){

        //所有的公安处领导身份证

        String chuRoleId = "230e916609a349e68f7186f784e11419";

        ExamWorkflowDefine define = new ExamWorkflowDefine();
        define.setName(chuRoleId);
        List<User> chuList = examWorkflowSegmentsDefineDao.queryPoliceDatas(define);

        return chuList;
    }

    /*
    * 中基层领导信息
    * */
    public List<User> zjcList(){
        //所有的中基层领导班子身份证
        String juJiGuanRoleId = "13bac520ae4d4160b595384691b443fd";
        String chuJiGuanRoleId = "62cf6aaea0184d3b8cfbce1700ad0e38";
        String suoduiJiGuanRoleId = "ff24c3288860447d867d08d462a2a2b9";

        String[] zhongJiCeng = new String[3];
        zhongJiCeng[0] = chuJiGuanRoleId;
        zhongJiCeng[1] = juJiGuanRoleId;
        zhongJiCeng[2] = suoduiJiGuanRoleId;
        List<User> zjcList = new ArrayList<>();

        Arrays.stream(zhongJiCeng).forEach(s -> {
            ExamWorkflowDefine zhongDefine = new ExamWorkflowDefine();
            zhongDefine.setName(s);
            zjcList.addAll(examWorkflowSegmentsDefineDao.queryPoliceDatas(zhongDefine));
        });
        return zjcList;
    }

    /*
    * 民警个人信息
    * */
    public List<User> mjList(){
        //所有的民警身份证
        String policeRoleId = "2a7ea380b14f4870b6c44be21d2443c3";

        StringBuffer policeIds = new StringBuffer();
        StringBuffer policePersonNames = new StringBuffer();
        ExamWorkflowDefine policeDefine = new ExamWorkflowDefine();
        policeDefine.setName(policeRoleId);

        List<User> mjList = examWorkflowSegmentsDefineDao.queryPoliceDatas(policeDefine);
        return mjList;
    }

}
