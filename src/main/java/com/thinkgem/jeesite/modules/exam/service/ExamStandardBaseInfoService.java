/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardBaseInfoDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 考评标准管理Service
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Service
@Transactional(readOnly = true)
public class ExamStandardBaseInfoService extends CrudService<ExamStandardBaseInfoDao, ExamStandardBaseInfo> {

	@Autowired
	private ExamStandardBaseInfoDao examStandardBaseInfoDao;

	public ExamStandardBaseInfo get(String id) {
		return super.get(id);
	}
	
	public List<ExamStandardBaseInfo> findList(ExamStandardBaseInfo examStandardBaseInfo) {
		return super.findList(examStandardBaseInfo);
	}
	
	public Page<ExamStandardBaseInfo> findPage(Page<ExamStandardBaseInfo> page, ExamStandardBaseInfo examStandardBaseInfo) {
		/*王俊宇：标准管理，所有单位可看到所有的标准，但不可修改，只可以修改自己创建的，增加创建者查询项*/
		/*examStandardBaseInfo.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		String officeId = UserUtils.getUser().getOffice().getId();
		if (StringUtils.isNotBlank(examStandardBaseInfo.getUnitId())){
			String[] unitId = examStandardBaseInfo.getUnitId().split(",");
			StringBuffer condition = new StringBuffer();
			condition.append("'"+officeId +"'"+ "in (");
			Arrays.stream(unitId).forEach(item ->{
				condition.append("'"+item+"'"+",");
			});
			condition.append("'-1')");	//多出来一个逗号 加个 -1
		}
			examStandardBaseInfo.setOfficeId(officeId);*/
		return super.findPage(page, examStandardBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamStandardBaseInfo examStandardBaseInfo) {
		super.save(examStandardBaseInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamStandardBaseInfo examStandardBaseInfo) {
		super.delete(examStandardBaseInfo);
	}

	public String findIdByName(String name) {
		return examStandardBaseInfoDao.findIdByName(name);
	}

	public List<ExamStandardBaseInfo> findPublicInList(ExamStandardBaseInfo examStandardBaseInfo) {
		return dao.findPublicInList(examStandardBaseInfo);
	}
}