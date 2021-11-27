/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelEndowmentInsuranceDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelEndowmentInsurance;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 养老保险Service
 * @author alan.wu
 * @version 2020-06-29
 */
@Service
@Transactional(readOnly = true)
public class PersonnelEndowmentInsuranceService extends CrudService<PersonnelEndowmentInsuranceDao, PersonnelEndowmentInsurance> {

	public PersonnelEndowmentInsurance get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelEndowmentInsurance> findList(PersonnelEndowmentInsurance personnelEndowmentInsurance) {
		return super.findList(personnelEndowmentInsurance);
	}
	
	public Page<PersonnelEndowmentInsurance> findPage(Page<PersonnelEndowmentInsurance> page, PersonnelEndowmentInsurance personnelEndowmentInsurance) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			personnelEndowmentInsurance.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			personnelEndowmentInsurance.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			personnelEndowmentInsurance.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			personnelEndowmentInsurance.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			personnelEndowmentInsurance.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			personnelEndowmentInsurance.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			personnelEndowmentInsurance.setUserId("999");
		}
		else {
			personnelEndowmentInsurance.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, personnelEndowmentInsurance);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelEndowmentInsurance personnelEndowmentInsurance) {
		//养老保险个人缴费比例
		double ygrbl = personnelEndowmentInsurance.getIndividualProportion();
		double ygr = ygrbl / 100;
		//养老保险单位缴费比例
		double ydwjfbl = personnelEndowmentInsurance.getUnitProportion();
		double ydw = ydwjfbl / 100;
		//职业年金个人缴费比例
		double zgrbl = personnelEndowmentInsurance.getOaIndividualProportion();
		double zgr = zgrbl / 100;
		//职业年金单位缴费比例
		double zdwjfbl = personnelEndowmentInsurance.getOaUnitProportion();
		double zdw = zdwjfbl / 100;
		//缴费基数
		Double jfjs = personnelEndowmentInsurance.getCardinalNumber();
		//个人缴费数
		double gr = ygr * jfjs;
		//单位缴费数
		double dw = ydw * jfjs;
		//职业年金缴费数
		double zynj = zgr * jfjs;
		//职业年金单位缴费
		double zynjdw = zdw * jfjs;

		//添加养老保险缴费基数
		personnelEndowmentInsurance.setCardinalNumber(jfjs);
		//添加养老金个人缴费比例
		personnelEndowmentInsurance.setIndividualProportion(ygrbl);
		//添加养老金个人月缴费
		personnelEndowmentInsurance.setIndividualPayment(gr);
		//添加养老金单位缴费比例
		personnelEndowmentInsurance.setUnitProportion(ydwjfbl);
		//养老金单位单位月缴费
		personnelEndowmentInsurance.setUnitPayment(dw);
		//职业年金个人缴费比例
		personnelEndowmentInsurance.setOaIndividualProportion(zgrbl);
		//职业年金个人缴费数
		personnelEndowmentInsurance.setOaIndividualPayment(zynj);
		//职业年金单位缴费比例
		personnelEndowmentInsurance.setOaUnitProportion(zdwjfbl);
		//职业年金单位月缴费
		personnelEndowmentInsurance.setOaUnitPayment(zynjdw);
		super.save(personnelEndowmentInsurance);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelEndowmentInsurance personnelEndowmentInsurance) {
		super.delete(personnelEndowmentInsurance);
	}
	
}