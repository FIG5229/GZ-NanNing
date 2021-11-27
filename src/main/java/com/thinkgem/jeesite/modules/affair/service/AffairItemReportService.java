/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairItemReportDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairItemReport;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 个人事项报告表Service
 * @author mason.xv
 * @version 2019-11-06
 */
@Service
@Transactional(readOnly = true)
public class AffairItemReportService extends CrudService<AffairItemReportDao, AffairItemReport> {

	@Autowired
	AffairItemReportDao affairItemReportDao;

	public AffairItemReport get(String id) {
		return super.get(id);
	}
	
	public List<AffairItemReport> findList(AffairItemReport affairItemReport) {
		return super.findList(affairItemReport);
	}
	
	public Page<AffairItemReport> findPage(Page<AffairItemReport> page, AffairItemReport affairItemReport) {
		return super.findPage(page, affairItemReport);
	}

	//在service层添加数据查重功能
	@Transactional(readOnly = false)
	public void save(AffairItemReport affairItemReport) {
        if (!StringUtils.isNotBlank(affairItemReport.getStatus())){
			affairItemReport.setStatus("2");
		}
		AffairItemReport affairItemReport0 = new AffairItemReport();
		List<AffairItemReport> list= affairItemReportDao.findAllList(affairItemReport0);

		if (list.size()== 0 ){
			super.save(affairItemReport);
		}else{
			AffairItemReport affairItemReportSame= findSame(affairItemReport, list);
			if(affairItemReportSame != null){
				affairItemReport.setId(affairItemReportSame.getId());
				super.save(affairItemReport);
			}
			else {
				//这个地方要加友好提示
				super.save(affairItemReport);
			}
		}

	}
	private AffairItemReport findSame(AffairItemReport affairItemReport, List<AffairItemReport> list){
		AffairItemReport result = null;
		for(AffairItemReport affairItemReport1 : list) {
			if (affairItemReport1.getIdNumber().equals(affairItemReport.getIdNumber())) {
				result= affairItemReport1;
				break;
			}
		}
		return  result;
	}


	@Transactional(readOnly = false)
	public void delete(AffairItemReport affairItemReport) {
		super.delete(affairItemReport);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairItemReport affairItemReport) {
		affairItemReport.setUpdateDate(new Date());
		affairItemReport.setShPerson(UserUtils.getUser().getName());
		affairItemReportDao.shenHe(affairItemReport);
	}
	
}