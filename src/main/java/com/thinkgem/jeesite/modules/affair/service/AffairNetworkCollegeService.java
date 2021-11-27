/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairNetworkCollegeDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPower;
import com.thinkgem.jeesite.modules.affair.entity.AffairNetworkCollege;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 中国干部网络学院Service
 * @author alan.wu
 * @version 2020-08-03
 */
@Service
@Transactional(readOnly = true)
public class AffairNetworkCollegeService extends CrudService<AffairNetworkCollegeDao, AffairNetworkCollege> {

	@Autowired
	private AffairNetworkCollegeDao affairNetworkCollegeDao;

	public AffairNetworkCollege get(String id) {
		return super.get(id);
	}
	
	public List<AffairNetworkCollege> findList(AffairNetworkCollege affairNetworkCollege) {
		return super.findList(affairNetworkCollege);
	}
	
	public Page<AffairNetworkCollege> findPage(Page<AffairNetworkCollege> page, AffairNetworkCollege affairNetworkCollege) {
		affairNetworkCollege.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairNetworkCollege.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairNetworkCollege.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairNetworkCollege);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairNetworkCollege affairNetworkCollege) {
		super.save(affairNetworkCollege);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairNetworkCollege affairNetworkCollege) {
		super.delete(affairNetworkCollege);
	}


	public Double selectLastYearIntegeralByUserId(String ye,String userId){
		Double score = affairNetworkCollegeDao.selectLastYearIntegeralByUserId(ye,userId);
		return score;
	}

	public Double selectThisYearCodeByUserId(String year,String userId){
		Double code = affairNetworkCollegeDao.selectThisYearCodeByUserId(year,userId);
		return code;
	}

	public Double selectLastMonthById(String year,String month,String userId){
		Double score = affairNetworkCollegeDao.selectLastMonthById(year,month,userId);
		return score;
	}


	public String selectSumYearById(String year,String userId){
		String sum = affairNetworkCollegeDao.selectSumYearById(year,userId);
		return sum;
	}

	public Double selectLastScore(String idNumber,String time){
		return affairNetworkCollegeDao.selectLastScore(idNumber,time);
	}

	public List<String> selectAllId(String userId){
		return affairNetworkCollegeDao.selectAllId(userId);
	}

	public String selectBean(String id,String year){
		return affairNetworkCollegeDao.selectBean(id,year);
	}

	public String selectUnit(String id){
		return affairNetworkCollegeDao.selectUnit(id);
	}

	public String selectName(String id){
		return affairNetworkCollegeDao.selectName(id);
	}

	public String selectIdNumber(String name){
		return affairNetworkCollegeDao.selectIdNumber(name);
	}

	public List<AffairNetworkCollege> selectAllBean(AffairNetworkCollege affairNetworkCollege){

		affairNetworkCollege.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairNetworkCollege.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairNetworkCollege.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return affairNetworkCollegeDao.selectAllBean(affairNetworkCollege);
	}

    public Page<AffairNetworkCollege> findProblemDataList(Page<AffairNetworkCollege> page, AffairNetworkCollege affairNetworkCollege) {
		affairNetworkCollege.setPage(page);
		affairNetworkCollege.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairNetworkCollege.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairNetworkCollege.setUserId(UserUtils.getUser().getOffice().getId());
		}
		List<AffairNetworkCollege> list = affairNetworkCollegeDao.findProblemDataList(affairNetworkCollege);
		page.setList(list);
		return page;
    }
}