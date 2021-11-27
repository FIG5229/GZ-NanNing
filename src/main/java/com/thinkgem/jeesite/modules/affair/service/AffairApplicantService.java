/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairApplicantDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairApplicant;
import com.thinkgem.jeesite.modules.affair.entity.AffairGeneralSituation;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 入党申请人表Service
 *
 * @author eav.liu
 * @version 2020-03-12
 */
@Service
@Transactional(readOnly = true)
public class AffairApplicantService extends CrudService<AffairApplicantDao, AffairApplicant> {
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private WarningService warningService;

    public AffairApplicant get(String id) {
        return super.get(id);
    }

    public List<AffairApplicant> findList(AffairApplicant affairApplicant) {
        return super.findList(affairApplicant);
    }

    public Page<AffairApplicant> findPage(Page<AffairApplicant> page, AffairApplicant affairApplicant) {
        affairApplicant.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
        return super.findPage(page, affairApplicant);
    }

    @Transactional(readOnly = false)
    public void save(AffairApplicant affairApplicant) {
        if(StringUtils.isBlank(affairApplicant.getId())){
            Warning warning = new Warning();
            warning.setName("入党申请谈话预警");
            warning.setDate(new Date());
            warning.setContinueDay("0");//持续时间
            warning.setRepeatCycle("4");//周期
            warning.setIsAlert("1");//使用弹窗
            warning.setAlertDegree("1");//紧急程度
            warning.setRemind("20");//重复提醒
            warning.setAlertContent(affairApplicant.getPartyBranch()+"下"+affairApplicant.getName()+"(警号为:"+affairApplicant.getPoliceNum()+")已于"+ DateUtils.getDate()+"添加入党申请");
            User creatUser = new User();
            creatUser.setId("1");
            Office office = new Office();
            office.setId("1");
            creatUser.setOffice(office);
            warning.setCreateBy(creatUser);
            warning.setUpdateBy(creatUser);
            User user = userDao.getPartyShujiUser(affairApplicant.getPartyBranchId());
            if(user!=null){
                warning.setReceivePerName(user.getName());
                warning.setReceivePerId(user.getId());
                warningService.save(warning);
            }
        }
        super.save(affairApplicant);

    }

    @Transactional(readOnly = false)
    public void delete(AffairApplicant affairApplicant) {
        super.delete(affairApplicant);
    }

}