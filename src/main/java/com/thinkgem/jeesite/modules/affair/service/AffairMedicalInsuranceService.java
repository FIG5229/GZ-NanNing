/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMedicalInsuranceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMedicalInsurance;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 医疗保险Service
 * @author jack.xu
 * @version 2020-07-03
 */
@Service
@Transactional(readOnly = true)
public class AffairMedicalInsuranceService extends CrudService<AffairMedicalInsuranceDao, AffairMedicalInsurance> {
	@Autowired
	private AffairMedicalInsuranceDao affairMedicalInsuranceDao;

	public AffairMedicalInsurance get(String id) {
		return super.get(id);
	}
	
	public List<AffairMedicalInsurance> findList(AffairMedicalInsurance affairMedicalInsurance) {
		return super.findList(affairMedicalInsurance);
	}
	
	public Page<AffairMedicalInsurance> findPage(Page<AffairMedicalInsurance> page, AffairMedicalInsurance affairMedicalInsurance) {
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairMedicalInsurance.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairMedicalInsurance.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairMedicalInsurance.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairMedicalInsurance.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairMedicalInsurance.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairMedicalInsurance.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairMedicalInsurance.setUserId("999");
		}
		else {
			affairMedicalInsurance.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairMedicalInsurance);
	}

	@Transactional(readOnly = false)
	public void saveGenerate(AffairMedicalInsurance affairMedicalInsurance) {
		super.save(affairMedicalInsurance);
	}

	@Transactional(readOnly = false)
	public void save(AffairMedicalInsurance affairMedicalInsurance) {
		Integer age = affairMedicalInsurance.getAge();
		Double cardinalNumber = affairMedicalInsurance.getCardinalNumber();
		Double averageSalary = affairMedicalInsurance.getAverageSalary();
		String sex = affairMedicalInsurance.getSex();
		Double individualProportion = affairMedicalInsurance.getIndividualProportion();
		Double unitProportion = affairMedicalInsurance.getUnitProportion();
		//月个人缴费
		Double individualPayment = cardinalNumber * individualProportion / 100.00;
		affairMedicalInsurance.setIndividualPayment(individualPayment);
		//月单位缴费
		Double unitPayment = cardinalNumber * unitProportion / 100.00;
		affairMedicalInsurance.setUnitPayment(unitPayment);
		//月个账划入
		affairMedicalInsurance.setMonthAccount(affairMedicalInsurance.getMonthAccount());
		Double monthAccountDelimit = Double.valueOf(cardinalNumber) * affairMedicalInsurance.getMonthAccount() / 100.00;
		affairMedicalInsurance.setMonthAccountDelimit(monthAccountDelimit);
		//补充资金月个账划入
			/*Double additionFunds = Double.valueOf(jishu) * 0.05;
			a.setAdditionFunds(additionFunds);*/
		affairMedicalInsurance.setAddition(affairMedicalInsurance.getAddition());
		Double additionFunds = Double.valueOf(cardinalNumber) * affairMedicalInsurance.getAddition() / 100.00;
		affairMedicalInsurance.setAdditionFunds(additionFunds);

		affairMedicalInsurance.setUnitPayment(unitPayment);
		affairMedicalInsurance.setAnnualProportion(affairMedicalInsurance.getAnnualProportion());
		Double annualAccountDelimit = Double.valueOf(cardinalNumber) * affairMedicalInsurance.getAnnualProportion() / 100.00;
		affairMedicalInsurance.setAnnualAccountDelimit(annualAccountDelimit);
		super.save(affairMedicalInsurance);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMedicalInsurance affairMedicalInsurance) {
		super.delete(affairMedicalInsurance);
	}

	public List<Map<String, String>> findBase(String year,String userOffice){
		return affairMedicalInsuranceDao.findBase(year, userOffice);
	}

}