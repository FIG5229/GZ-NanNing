/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairLaborOfficeDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairQjDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLaborOffice;
import com.thinkgem.jeesite.modules.affair.entity.AffairQj;
import com.thinkgem.jeesite.modules.affair.entity.AffairQjSum;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请假信息Service
 * @author mason.xv
 * @version 2019-11-27
 */
@Service
@Transactional(readOnly = true)
public class AffairQjService extends CrudService<AffairQjDao, AffairQj> {
	@Autowired
    AffairQjDao affairQjDao;

	@Autowired
	OfficeDao officeDao;

	@Autowired
	private AffairLaborOfficeDao affairLaborOfficeDao;

	public AffairQj get(String id) {
		return super.get(id);
	}
	
	public List<AffairQj> findList(AffairQj affairQj) {
		return super.findList(affairQj);
	}
	
	public Page<AffairQj> findPage(Page<AffairQj> page, AffairQj affairQj) {
		/*affairQj.setUserId(UserUtils.getUser().getCompany().getId());*/
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairQj.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairQj.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairQj.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairQj.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairQj.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairQj.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairQj.setUserId("999");
		}
		else {
			affairQj.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairQj);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairQj affairQj) {
		if (affairQj.getStatus()==null||"".equals(affairQj.getStatus())){
			affairQj.setStatus("0");
		}
		super.save(affairQj);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairQj affairQj) {
		super.delete(affairQj);
	}


	@Transactional(readOnly = false)
	public void leaderShenhe(AffairQj affairQj) {
		affairQj.setUpdateDate(new Date());
		affairQj.setShPerson(UserUtils.getUser().getName());
		affairQjDao.leaderShenHe(affairQj);
	}


	@Transactional(readOnly = false)
	public void depShenHe(AffairQj affairQj) {
		affairQj.setUpdateDate(new Date());
		affairQj.setShPerson(UserUtils.getUser().getName());
		affairQjDao.depShenHe(affairQj);
	}

	@Transactional(readOnly = false)
	public void hrShenHe(AffairQj affairQj) {
		affairQj.setUpdateDate(new Date());
		affairQj.setShPerson(UserUtils.getUser().getName());
		affairQjDao.hrShenHe(affairQj);
	}

	/**
	 * 请假查询初始页面
	 * @param affairQj
	 * @return
	 */
	public List<AffairQjSum> statistic(AffairQj affairQj) {
		List<AffairQjSum> list = new ArrayList<AffairQjSum>();
		String id = UserUtils.getUser().getCompany().getId();
		if ("1".equals(id)){
			//局机关
			affairQj.setUnitId("1");
			affairQj.setFlag("1");
			AffairQjSum juJiGuan = affairQjDao.findByNowNameUnitId(affairQj);
			juJiGuan.setUnitName("局机关");
			juJiGuan.setUnitId("1");
			list.add(juJiGuan);
			//南宁公安处
			affairQj.setUnitId("777");
			affairQj.setFlag("");
			AffairQjSum nanNingChu = affairQjDao.findByNowNameUnitId(affairQj);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("777");
			list.add(nanNingChu);
			//柳州公安处
			affairQj.setUnitId("888");
			affairQj.setFlag("");
			AffairQjSum liuZhouChu = affairQjDao.findByNowNameUnitId(affairQj);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("888");
			list.add(liuZhouChu);
			//北海公安处
			affairQj.setUnitId("999");
			affairQj.setFlag("");
			AffairQjSum beihaiChu = affairQjDao.findByNowNameUnitId(affairQj);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("999");
			list.add(beihaiChu);
		}else if("34".equals(id)){
			//南宁公安处
			affairQj.setUnitId("777");
			AffairQjSum nanNingChu = affairQjDao.findByNowNameUnitId(affairQj);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("777");
			list.add(nanNingChu);
		}else if("95".equals(id)){
			//柳州公安处
			affairQj.setUnitId("888");
			AffairQjSum liuZhouChu = affairQjDao.findByNowNameUnitId(affairQj);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("888");
			list.add(liuZhouChu);
		}else{
			//北海公安处
			affairQj.setUnitId("999");
			AffairQjSum beihaiChu = affairQjDao.findByNowNameUnitId(affairQj);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("999");
			list.add(beihaiChu);
		}
		return list;
	}

	public List<AffairQjSum> findByUnitId(AffairQjSum AffairQjSum, AffairQj affairQj) {
		List<AffairQjSum> list = new ArrayList<AffairQjSum>();
		List<AffairLaborOffice> child = affairLaborOfficeDao.findChildById(AffairQjSum.getUnitId());
		for (AffairLaborOffice office : child) {
			affairQj.setUnitId(office.getId());
			AffairQjSum statistic = affairQjDao.findByNowNameUnitId(affairQj);
			statistic.setUnitName(office.getName());
			statistic.setUnitId(office.getId());
			list.add(statistic);
		}
		return list;
	}

	//请假汇总
	public Page<AffairQj> countList(Page<AffairQj> page, AffairQj affairQj) {
	   /* affairQj.setUserId(UserUtils.getUser().getCompany().getId());*/
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832")){
			affairQj.setUserId(UserUtils.getUser().getCompany().getId());
		}else if ( UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79")){
			affairQj.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8")){
			affairQj.setUserId("888");
		}else if ( UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairQj.setUserId("999");
		}else if (UserUtils.getUser().getId().equals("73d8a1dc64874b9a8dca494db37488af")){
			affairQj.setUserId("777");
		}else if (UserUtils.getUser().getId().equals("3e0a7e6b415a40fc976735050c253362")){
			affairQj.setUserId("888");
		}else if (UserUtils.getUser().getId().equals("13be1cd2f87046909e9a835ae52ec3d4")){
			affairQj.setUserId("999");
		}
		else {
			affairQj.setUserId(UserUtils.getUser().getOffice().getId());
		}
		affairQj.setPage(page);
		page.setList(affairQjDao.countList(affairQj));
		return page;
	}

}