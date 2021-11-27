/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceCertificateDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceCertificate;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 人民警察证信息集Service
 * @author cecil.li
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceCertificateService extends CrudService<PersonnelPoliceCertificateDao, PersonnelPoliceCertificate> {

	@Autowired
	PersonnelPoliceCertificateDao personnelPoliceCertificateDao;


	public PersonnelPoliceCertificate get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceCertificate> findList(PersonnelPoliceCertificate personnelPoliceCertificate) {
		return super.findList(personnelPoliceCertificate);
	}
	
	public Page<PersonnelPoliceCertificate> findPage(Page<PersonnelPoliceCertificate> page, PersonnelPoliceCertificate personnelPoliceCertificate) {
//		personnelPoliceCertificate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		personnelPoliceCertificate.setCreateBy(UserUtils.getUser());
		if ("bd42300887ad417fa3f2fa9f554e6419".equals(UserUtils.getUser().getId()) || "69f857c3e1854021b5dee55c514026e3".equals(UserUtils.getUser().getId()) || "3850cecf34be44188f94b0edc552aff3".equals(UserUtils.getUser().getId()) || "1c19f6cc935f430f9f27295b761b1236".equals(UserUtils.getUser().getId())
		|| "96d26e10bd074eecbc6b8b3a619bec1d".equals(UserUtils.getUser().getId()) || "4103b50669e9422391fb70aa704266b1".equals(UserUtils.getUser().getId()) || "3404d05bf9054a51ae9afbe40e44a718".equals(UserUtils.getUser().getId()) || "957de2956a384bad96adbaa35cb05520".equals(UserUtils.getUser().getId()))
		{
			personnelPoliceCertificate.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			personnelPoliceCertificate.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, personnelPoliceCertificate);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceCertificate personnelPoliceCertificate) {
		super.save(personnelPoliceCertificate);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceCertificate personnelPoliceCertificate) {
		super.delete(personnelPoliceCertificate);
	}

	@Transactional(readOnly = false)
	public void updateByIdNumbers(List<String> idNumbers, Date date) {
		super.dao.updateByIdNumbers(idNumbers,date);
	}

	@Transactional(readOnly = false)
	public List<PersonnelPoliceCertificate> selectHistoryByIdNumber(String idNumber){

		List<PersonnelPoliceCertificate> list = personnelPoliceCertificateDao.selectHistoryByIdNumber(idNumber);

		return list;
	}

	public String selectBeanByIdNumber(String idNumber){
		return personnelPoliceCertificateDao.selectBeanByIdNumber(idNumber);
	}

	public  PersonnelPoliceCertificate findNewBean(PersonnelPoliceCertificate personnelPoliceCertificate){
		return personnelPoliceCertificateDao.findNewBean(personnelPoliceCertificate);
	}
	//查询三个月到期的警察证
    public List<PersonnelPoliceCertificate> selTreeMonthPoliceCertificateInfo(String treeMonthDate) {
		return personnelPoliceCertificateDao.selTreeMonthPoliceCertificateInfo(treeMonthDate);
    }

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}