/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairLearnPowerDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLearnPower;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学习强国Service
 * @author Alan
 * @version 2020-06-04
 */
@Service
@Transactional(readOnly = true)
public class AffairLearnPowerService extends CrudService<AffairLearnPowerDao, AffairLearnPower> {

	@Autowired
	private AffairLearnPowerDao affairLearnPowerDao;

	public AffairLearnPower get(String id) {
		return super.get(id);
	}
	
	public List<AffairLearnPower> findList(AffairLearnPower affairLearnPower) {
		return super.findList(affairLearnPower);
	}
	
	public Page<AffairLearnPower> findPage(Page<AffairLearnPower> page, AffairLearnPower affairLearnPower) {
		affairLearnPower.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairLearnPower.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLearnPower.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairLearnPower);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLearnPower affairLearnPower) {
		if(StringUtils.isNotBlank(affairLearnPower.getIdNumber())){
			AffairLearnPower old_affairLearnPower = affairLearnPowerDao.findByIdNumberTime(affairLearnPower.getIdNumber().trim(),affairLearnPower.getTime());
			affairLearnPower.setIdNumber(affairLearnPower.getIdNumber().trim());
			if(old_affairLearnPower!=null){
				affairLearnPower.setId(old_affairLearnPower.getId());
			}
		}
		super.save(affairLearnPower);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLearnPower affairLearnPower) {
		super.delete(affairLearnPower);
	}


	public Double selectLastYearIntegeralByUserId(String ye,String userId){
		Double score = affairLearnPowerDao.selectLastYearIntegeralByUserId(ye,userId);
		return score;
	}

	public Double selectThisYearCodeByUserId(String year,String userId){
		Double code = affairLearnPowerDao.selectThisYearCodeByUserId(year,userId);
		return code;
	}

	public Double selectLastMonthById(String year,String month,String userId){
		Double score = affairLearnPowerDao.selectLastMonthById(year,month,userId);
		return score;
	}

	public Double selectSumYearById(String year,String userId,String unitId){
		Double sum = affairLearnPowerDao.selectSumYearById(year,userId,unitId);
		return sum;
	}

	public Double selectLastScore(String idNumber,String time){
		return affairLearnPowerDao.selectLastScore(idNumber,time);
	}

	public List<String> selectAllId(String idN){
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			idN=UserUtils.getUser().getCompany().getId();
		}else {
			idN=UserUtils.getUser().getOffice().getId();
		}
		return affairLearnPowerDao.selectAllId(idN);
	}

	public String selectBean(String id,String year){
		return affairLearnPowerDao.selectBean(id,year);
	}

	public String selectUnit(String id){
		return affairLearnPowerDao.selectUnit(id);
	}

	public String selectName(String id){
		return affairLearnPowerDao.selectName(id);
	}

	public List<String> selectAllTime(){
		return affairLearnPowerDao.selectAllTime();
	}

	public Integer selectNumber(String yearL,String yearT,String idNumber){
		return affairLearnPowerDao.selectNumber(yearL,yearT,idNumber);
	}

	public String selectUnitId(String unit){
		return affairLearnPowerDao.selectUnitId(unit);
	}

	public List<String> selectAllName(){
		return affairLearnPowerDao.selectAllName();
	}

	public String selectIdNumber(String name){
		return affairLearnPowerDao.selectIdNumber(name);
	}

	public List<AffairLearnPower> selectAllBean(AffairLearnPower affairLearnPower){

		affairLearnPower.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairLearnPower.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLearnPower.setUserId(UserUtils.getUser().getOffice().getId());
		}

		return affairLearnPowerDao.selectAllBean(affairLearnPower);
	}

    public Page<AffairLearnPower> findProblemDataList(Page<AffairLearnPower> page, AffairLearnPower affairLearnPower) {
		affairLearnPower.setPage(page);
		affairLearnPower.setCreateBy(UserUtils.getUser());
		if ("d30e324c8f73492d9b74103374fbc689".equals(UserUtils.getUser().getId()) || "d154234ecb35470e84fb95e53726866b".equals(UserUtils.getUser().getId()) || "e3ac8381fb3247e0b64fd6e3c48bddc1".equals(UserUtils.getUser().getId()) || "66937439b2124f328d1521968fab06db".equals(UserUtils.getUser().getId())){
			affairLearnPower.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairLearnPower.setUserId(UserUtils.getUser().getOffice().getId());
		}
		List<AffairLearnPower> list = affairLearnPowerDao.findProblemDataList(affairLearnPower);
		page.setList(list);
		return page;
    }

    public Integer selectPeoNumber(String yearL,String yearT,String idNumber,Integer score){
		return affairLearnPowerDao.selectPeoNumber(yearL,yearT,idNumber,score);
	}


	public Integer selectMjNumber(String time, String id, int score) {
		return affairLearnPowerDao.selectMjNumber(time,id,score);
	}

	public int selectPeoNumberYear(String time,String idNumber1, int score) {

		Integer scNum = affairLearnPowerDao.selectPNumber(time,idNumber1);
		if (null == scNum){
			return 0;
		}else if (score > scNum){
			return 1;
		}else{
			return 0;
		}
	}

	public Integer selectPeopleNumber(String time, String idNumber1, int score) {
		return affairLearnPowerDao.selectPeopleNumber(time,idNumber1,score);
	}
}