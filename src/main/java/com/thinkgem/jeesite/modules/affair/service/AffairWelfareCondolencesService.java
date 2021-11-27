/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWelfareCondolencesDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWelfareCondolences;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 福利慰问Service
 * @author daniel.liu
 * @version 2020-05-12
 */
@Service
@Transactional(readOnly = true)
public class AffairWelfareCondolencesService extends CrudService<AffairWelfareCondolencesDao, AffairWelfareCondolences> {

	@Autowired
	private AffairWelfareCondolencesDao affairWelfareCondolencesDao;

	public AffairWelfareCondolences get(String id) {
		return super.get(id);
	}
	
	public List<AffairWelfareCondolences> findList(AffairWelfareCondolences affairWelfareCondolences) {
		return super.findList(affairWelfareCondolences);
	}
	
	public Page<AffairWelfareCondolences> findPage(Page<AffairWelfareCondolences> page, AffairWelfareCondolences affairWelfareCondolences) {
		if (UserUtils.getUser().getId().equals("4c40b4dd2aee459286a37538978e6261") || UserUtils.getUser().getId().equals("cb97bf7e17d549f09b51375a98a8eb55") || UserUtils.getUser().getId().equals("bdab6b25fc694e74bf7c3d1363c0062e") || UserUtils.getUser().getId().equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairWelfareCondolences.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairWelfareCondolences.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairWelfareCondolences);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWelfareCondolences affairWelfareCondolences) {
		affairWelfareCondolences.setContent(StringEscapeUtils.unescapeHtml4(affairWelfareCondolences.getContent()));
		super.save(affairWelfareCondolences);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairWelfareCondolences affairWelfareCondolences) {
		super.delete(affairWelfareCondolences);
	}

	public List<AffairWelfareCondolences> allList(String year, String month, String startTime, String endTime){
		return affairWelfareCondolencesDao.allList(year, month, startTime, endTime);
	}

	public int unitCount(String startTime, String endTime, String unitId){
		return affairWelfareCondolencesDao.unitCount(startTime, endTime, unitId);
	}
	
}