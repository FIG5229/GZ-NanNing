/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairGeneralSituationDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairLifeMeetDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairLifeMeet;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 民主（组织）生活会Service
 * @author eav.liu
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class AffairLifeMeetService extends CrudService<AffairLifeMeetDao, AffairLifeMeet> {

	@Autowired
	private AffairLifeMeetDao affairLifeMeetDao;

	@Autowired
	private AffairGeneralSituationDao affairGeneralSituationDao;

	public AffairLifeMeet get(String id) {
		return super.get(id);
	}
	
	public List<AffairLifeMeet> findList(AffairLifeMeet affairLifeMeet) {
		return super.findList(affairLifeMeet);
	}
	
	public Page<AffairLifeMeet> findPage(Page<AffairLifeMeet> page, AffairLifeMeet affairLifeMeet) {
		affairLifeMeet.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairLifeMeet);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairLifeMeet affairLifeMeet) {
		affairLifeMeet.setStatus("3");//审核状态为未审核
		super.save(affairLifeMeet);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairLifeMeet affairLifeMeet) {
		super.delete(affairLifeMeet);
	}

	@Transactional(readOnly = false)
	public void shenHeSave(AffairLifeMeet affairLifeMeet) {
		affairLifeMeet.setUpdateDate(new Date() );
		affairLifeMeet.setShPerson(UserUtils.getUser().getName());
		affairLifeMeetDao.shenHeSave(affairLifeMeet);
	}

	//查询完成的组织树 个全部组织树 计算为完成的组织树

	/**
	 *
	 * @param id	未删除 保留
	 * @param year	年份 不为空则按年查询
	 * @param startDate		时间段 不为空则按时间段查询
	 * @param endDate
	 * @param month		月份 不为空则按时间段查询
	 * @return
	 */
	public Map<String, Long> findInfoByPartyBranchId(String id, Integer year, Date startDate, Date endDate, String month) {
		id = dataScopeFilter(UserUtils.getUser(), "o", "u");
		Map<String, Long> map = affairLifeMeetDao.findFinishSumByPId(id, year, startDate, endDate, month);
		/*看不懂 都给注释了  组织树也不需要了
		* 总数在添加查询语句查询
		* */
		/*Integer unFinishSum = null;
		Map<String, Integer> map = new HashMap<>();
		if(id != null){
			if(finishSum == 1){
				map.put("finishSum", 1);
				map.put("unFinishSum", 0);
			}else{
				map.put("finishSum", 0);
				map.put("unFinishSum", 1);
			}
		}if(id == null){
			unFinishSum = affairGeneralSituationDao.findALLPartyBranch()-5-finishSum;
			map.put("finishSum", finishSum);
			map.put("unFinishSum", unFinishSum);
		}*/
		return map;
	}

	public Map<String, Integer> findInfoByPartyBranchIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		Integer finishSum = affairLifeMeetDao.findFinishSumByPIds(ids, year, startDate, endDate, month);
		Integer unFinishSum = ids.size()-finishSum;
		Map<String, Integer> map = new HashMap<>();
		map.put("finishSum", finishSum);
		map.put("unFinishSum", unFinishSum);
		return map;
	}
}