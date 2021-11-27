/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.affair.entity.AffairActivityMien;
import com.thinkgem.jeesite.modules.affair.entity.AffairTjRegister;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairYjGoOutReport;
import com.thinkgem.jeesite.modules.affair.dao.AffairYjGoOutReportDao;

import javax.servlet.http.HttpServletRequest;

/**
 * 民警因私外出报备Service
 * @author kevin.jia
 * @version 2020-11-10
 */
@Service
@Transactional(readOnly = true)
public class AffairYjGoOutReportService extends CrudService<AffairYjGoOutReportDao, AffairYjGoOutReport> {
	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	@Autowired
	private AffairYjGoOutReportDao affairYjGoOutReportDao;

	public AffairYjGoOutReport get(String id) {
		return super.get(id);
	}
	
	public List<AffairYjGoOutReport> findList(AffairYjGoOutReport affairYjGoOutReport) {
		return super.findList(affairYjGoOutReport);
	}
	
	public Page<AffairYjGoOutReport> findPage(Page<AffairYjGoOutReport> page, AffairYjGoOutReport affairYjGoOutReport) {
		User user = UserUtils.getUser();
		if(isNumeric(user.getLoginName())){
			//如果loginName为数字，则该用户为民警
			affairYjGoOutReport.setIsPerson(true);
		}else{
			affairYjGoOutReport.setIsPerson(false);
			//0df72fc41ba847678e7729a127ff3a2d 公安局组织干部处信息管理  c5869e138911485cb80b172567e64789  南宁处组织干部处信息管理  0921d686251848d5911e8a753cd50090  柳州处组织干部处信息管理 a417f6a0d4b948398413d82448b77b86北海处组织干部处信息管理
			if ("0df72fc41ba847678e7729a127ff3a2d".equals(UserUtils.getUser().getId()) || "c5869e138911485cb80b172567e64789".equals(UserUtils.getUser().getId()) || "0921d686251848d5911e8a753cd50090".equals(UserUtils.getUser().getId()) || "0921d686251848d5911e8a753cd50090".equals(UserUtils.getUser().getId())){
				affairYjGoOutReport.setOfficeId(UserUtils.getUser().getCompany().getId());
			}else {
				affairYjGoOutReport.setOfficeId(UserUtils.getUser().getOffice().getId());
			}
		}
		affairYjGoOutReport.setUserId(user.getId());
		return super.findPage(page, affairYjGoOutReport);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairYjGoOutReport affairYjGoOutReport) {
		if (affairYjGoOutReport.getIsNewRecord()){
			//保存
			affairYjGoOutReport.setUpdateOrgId(UserUtils.getUser().getOffice().getId());
			affairYjGoOutReport.setCreateOrgId(UserUtils.getUser().getOffice().getId());
		}else{
			//更新
			affairYjGoOutReport.setUpdateOrgId(UserUtils.getUser().getOffice().getId());
		}
		super.save(affairYjGoOutReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairYjGoOutReport affairYjGoOutReport) {
		super.delete(affairYjGoOutReport);
	}

    public List<AffairYjGoOutReport> findByIds(List<String> idsArray) {
		return dao.findByIds(idsArray);
    }

	@Transactional(readOnly = false)
	public boolean submitByIds(HttpServletRequest request,String checkId,String checkName) {
		boolean b =false;
		User user = UserUtils.getUser();
		//接受前台传过来的ids
		String idsStr = request.getParameter("ids");
		//字符串转数组
		String[] idsArray = idsStr.split(",");
		//数组转集合
		List<String> userList = new ArrayList<String>();
		/*String chuCheckId = request.getParameter("chuCheckId");
		String chuCheckMan = request.getParameter("chuCheckMan");*/
		try {
			Collections.addAll(userList,idsArray);
			List<AffairYjGoOutReport> list = dao.findByIds(userList);
			for (AffairYjGoOutReport affairYjGoOutReport: list){
				//0	退回整改	 1 未提交 2	审核中 3	转送上一级 4 通过
				if("1".equals(affairYjGoOutReport.getCheckType())||"0".equals(affairYjGoOutReport.getCheckType())){
					affairYjGoOutReport.setCheckType("2");
					affairYjGoOutReport.setChuCheckMan(checkName);
					affairYjGoOutReport.setChuCheckId(checkId);
					affairYjGoOutReport.setSubmitId(user.getId());
					affairYjGoOutReport.setSubmitMan(user.getName());
					this.save(affairYjGoOutReport);
				}
			}
			b = true;
		}catch (Exception e){
			e.printStackTrace();
			b = false;
		}
		return b;
	}



	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}

	@Transactional(readOnly = false)
	public void plshByIds(List<String> ids){
		affairYjGoOutReportDao.plshByIds(ids);
	};
	@Transactional(readOnly = false)
	public void plshthByIds(List<String> ids){
		affairYjGoOutReportDao.plshthByIds(ids);
	};

	public List<AffairYjGoOutReport> selectAllBeanTjfx() {
		return affairYjGoOutReportDao.selectAllBeanTjfx();
	}


	public Page<AffairYjGoOutReport> selectBeanDetails(Page<AffairYjGoOutReport> page, AffairYjGoOutReport affairYjGoOutReport) {
		affairYjGoOutReport.setPage(page);
		page.setList(affairYjGoOutReportDao.selectBeanDetails(affairYjGoOutReport));
		/*return affairYjGoOutReportDao.selectBeanDetails(lastSize,newSize);*/
		return page;
	}
	@Transactional(readOnly = false)
	public void revocation(AffairYjGoOutReport affairYjGoOutReport) {
		affairYjGoOutReportDao.revocation(affairYjGoOutReport);
	}
}