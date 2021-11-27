/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamWeightsMainDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeightsMain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权重Service
 * @author cecil.li
 * @version 2020-01-17
 */
@Service
@Transactional(readOnly = true)
public class ExamWeightsMainService extends CrudService<ExamWeightsMainDao, ExamWeightsMain> {

	public ExamWeightsMain get(String id) {
		return super.get(id);
	}
	
	public List<ExamWeightsMain> findList(ExamWeightsMain examWeightsMain) {
		return super.findList(examWeightsMain);
	}

	public List<ExamWeightsMain> findWeightsDataList(ExamWeightsMain examWeightsMain) {
		List<ExamWeightsMain> list =null;
//		if(null !=  CacheUtils.get("weights_data_"+examWeightsMain.getEwId())){
//			list = (List<ExamWeightsMain>)CacheUtils.get("weights_data_"+examWeightsMain.getEwId());
//		}else{
			list = super.findList(examWeightsMain);
//			CacheUtils.put("weights_data_"+examWeightsMain.getEwId(),list);
//		}
		return list;
	}
	
	public Page<ExamWeightsMain> findPage(Page<ExamWeightsMain> page, ExamWeightsMain examWeightsMain) {
		return super.findPage(page, examWeightsMain);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWeightsMain examWeightsMain) {
		super.save(examWeightsMain);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWeightsMain examWeightsMain) {
		super.delete(examWeightsMain);
	}

	@Transactional(readOnly = false)
	public void deleteByMainId(String mainId){
		this.dao.deleteByMainId(mainId);
	}

	@Transactional(readOnly = false)
	public void deleteByMainIds(List<String> mainIds){
		dao.deleteByMainIds(mainIds);
	};
	
}