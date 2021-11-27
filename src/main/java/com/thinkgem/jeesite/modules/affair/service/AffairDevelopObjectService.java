/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairDevelopObjectDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDevelopObject;
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
 * 发展党员Service
 * @author eva.liu
 * @version 2019-11-01
 */
@Service
@Transactional(readOnly = true)
public class AffairDevelopObjectService extends CrudService<AffairDevelopObjectDao, AffairDevelopObject> {

	@Autowired
	private AffairDevelopObjectDao affairDevelopObjectDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WarningService warningService;

	public AffairDevelopObject get(String id) {
		return super.get(id);
	}
	
	public List<AffairDevelopObject> findList(AffairDevelopObject affairDevelopObject) {
		return super.findList(affairDevelopObject);
	}
	
	public Page<AffairDevelopObject> findPage(Page<AffairDevelopObject> page, AffairDevelopObject affairDevelopObject) {
		affairDevelopObject.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairDevelopObject);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairDevelopObject affairDevelopObject) {
		super.save(affairDevelopObject);
		//成为发展对象后，提醒单位账号对其进行政审考察、公示等工作添加预警
		Warning warning = new Warning();
		warning.setName("成为发展对象预警");
		warning.setDate(new Date());
		warning.setContinueDay("0");//持续时间
		warning.setRepeatCycle("4");//周期
		warning.setIsAlert("1");//使用弹窗
		warning.setAlertDegree("1");//紧急程度
		warning.setRemind("20");//重复提醒
		warning.setAlertContent(affairDevelopObject.getPartyBranch()+"下"+affairDevelopObject.getName()+"(警号为:"+affairDevelopObject.getPoliceNo()+")已于"+DateUtils.getDate()+"成为发展对象，可以开始对其进行政审考察、公示等工作");
		warning.setCreateBy(UserUtils.getUser());
		warning.setUpdateBy(UserUtils.getUser());
		List<User> userList = userDao.getUserByPartyId(affairDevelopObject.getPartyBranchId());StringBuffer receivePerName =null;
		StringBuffer receivePerId = null;
		for(User user : userList){
			if(receivePerName==null){
				receivePerName= new StringBuffer(user.getName());
			}else{
				receivePerName.append(","+user.getName());
			}
			if(receivePerId==null){
				receivePerId= new StringBuffer(user.getId());
			}else{
				receivePerId.append(","+user.getId());
			}
		}
		if(receivePerId!=null && receivePerName!=null){
			warning.setReceivePerName(receivePerName.toString());//接受人员名称
			warning.setReceivePerId(receivePerId.toString());//接受人员id
			warningService.save(warning);
		}

	}
	
	@Transactional(readOnly = false)
	public void delete(AffairDevelopObject affairDevelopObject) {
		super.delete(affairDevelopObject);
	}

	public List<String> findListByIdNo(String idNo) {
		return affairDevelopObjectDao.findListByIdNo(idNo);
	}

	/**
	 * 根据身份证号删除数据
	 * @param idNo
	 */
	@Transactional(readOnly = false)
	public void deleteByIdNo(String idNo) {
		affairDevelopObjectDao.deleteByIdNo(idNo);
	}

	public Page<AffairDevelopObject> findDevelopPage(Page<AffairDevelopObject> affairDevelopObjectPage, AffairDevelopObject developObject) {
		//按照月度查询
		if (developObject.getDateType().equals("1")){
			developObject.setDateEnd(null);
			developObject.setDateStart(null);
			developObject.setYear(null);
		}else if (developObject.getDateType().equals("3")){//按照时间段查询
			developObject.setYear(null);
			developObject.setMonth(null);
		}else {	//按照年度查询  按照年度查询
			developObject.setDateEnd(null);
			developObject.setDateStart(null);
			developObject.setMonth(null);

		}
		developObject.setPage(affairDevelopObjectPage);
		affairDevelopObjectPage.setList(dao.findDevelopList(developObject));
		return affairDevelopObjectPage;
	}
}