/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardTemplateDefineDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamStandardTemplateItemDefineDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateDefine;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 考评模板Service
 * @author bradley.zhao
 * @version 2019-12-12
 */
@Service
@Transactional(readOnly = true)
public class ExamStandardTemplateDefineService extends CrudService<ExamStandardTemplateDefineDao, ExamStandardTemplateDefine> {

	@Autowired
	private ExamStandardTemplateItemDefineDao examStandardTemplateItemDefineDao;

	@Autowired
	private ExamStandardTemplateDefineDao examStandardTemplateDefineDao;

	public ExamStandardTemplateDefine get(String id) {
		ExamStandardTemplateDefine examStandardTemplateDefine = super.get(id);
		ExamStandardTemplateItemDefine examStandardTemplateItemDefine=new ExamStandardTemplateItemDefine(examStandardTemplateDefine.getId());
		examStandardTemplateItemDefine.setTemplateId(examStandardTemplateItemDefine.getId());
		/*获取模板的列*/
		examStandardTemplateDefine.setExamStandardTemplateItemDefineList(examStandardTemplateItemDefineDao.findList(examStandardTemplateItemDefine));
		return examStandardTemplateDefine;
	}

	public ExamStandardTemplateDefine getNew(Map<String,String> param){

		/*根据考评标准查询考评模板(一个考评标准可能会有多个考评模板)*/
		 List<ExamStandardTemplateDefine> list = examStandardTemplateDefineDao.getNew(param);
		ExamStandardTemplateDefine examStandardTemplateDefine = null;
		 if (list!=null && list.size()>0){
		 	examStandardTemplateDefine = list.get(0);
		 }
		if(null != examStandardTemplateDefine) {
			/*查询到考评模板后查询子表数据（考评模板的列）*/
			examStandardTemplateDefine.setExamStandardTemplateItemDefineList(examStandardTemplateItemDefineDao.findList(new ExamStandardTemplateItemDefine(examStandardTemplateDefine.getId())));
		}
		return examStandardTemplateDefine;
	}

	@Transactional(readOnly = false)
	public void updateExamStardardId(String id,String examStardardid){
		examStandardTemplateDefineDao.updateExamStardardId(id,examStardardid);
	}

	@Transactional(readOnly = false)
	public void clearExamStardardId(String examStardardid){
		examStandardTemplateDefineDao.clearExamStardardId(examStardardid);
	}
	public List<ExamStandardTemplateDefine> findList(ExamStandardTemplateDefine examStandardTemplateDefine) {
		return super.findList(examStandardTemplateDefine);
	}
	
	public Page<ExamStandardTemplateDefine> findPage(Page<ExamStandardTemplateDefine> page, ExamStandardTemplateDefine examStandardTemplateDefine) {
		return super.findPage(page, examStandardTemplateDefine);
	}

	/**
	 * 获取考评标准详细
	 * @param param
	 * @return
	 */
	public List<Map<String,String>> findTemplateDatas(Map<String,String> param){
		/*添加缓存，提高查询速度*/
		List<Map<String, String>> list = (List<Map<String, String>>)CacheUtils.get("template_"+param.get("examStardardId"));
		if(null == list){
			list = examStandardTemplateDefineDao.selectTemplateDatas(param);
			CacheUtils.put("template_"+param.get("examStardardId"), list);
		}
		return list;
	}

	@Transactional(readOnly = false)
	public void save(ExamStandardTemplateDefine examStandardTemplateDefine) {
		super.save(examStandardTemplateDefine);

		for (ExamStandardTemplateItemDefine examStandardTemplateItemDefine : examStandardTemplateDefine.getExamStandardTemplateItemDefineList()){
			if (examStandardTemplateItemDefine.getId() == null){
				continue;
			}
			if (ExamStandardTemplateItemDefine.DEL_FLAG_NORMAL.equals(examStandardTemplateItemDefine.getDelFlag())){
				if (StringUtils.isBlank(examStandardTemplateItemDefine.getId())){
					examStandardTemplateItemDefine.setTemplateId(examStandardTemplateDefine.getId());
					examStandardTemplateItemDefine.preInsert();
					examStandardTemplateItemDefineDao.insert(examStandardTemplateItemDefine);
				}else{
					examStandardTemplateItemDefine.preUpdate();
					examStandardTemplateItemDefineDao.update(examStandardTemplateItemDefine);
				}
			}else{
				examStandardTemplateItemDefineDao.delete(examStandardTemplateItemDefine);
			}
		}
		Map<String, String> param = new HashMap<String, String>();
		if(!examStandardTemplateDefine.getIsNewRecord()){
			//更新缓存格式信息
			String examStardardId = examStandardTemplateDefine.getExamStardardId();
			if(StringUtils.isNotBlank(examStardardId)){
				param.put("examStardardId", examStardardId);
//				List<Map<String, String>> list = (List<Map<String, String>>)CacheUtils.get("template_"+param.get("examStardardId"));
//				if(list!=null && list.size()>0){
//					list = examStandardTemplateDefineDao.selectTemplateDatas(param);
				CacheUtils.put("template_"+param.get("examStardardId"), examStandardTemplateDefineDao.selectTemplateDatas(param));
//				}
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamStandardTemplateDefine examStandardTemplateDefine) {
		super.delete(examStandardTemplateDefine);
		examStandardTemplateItemDefineDao.delete(new ExamStandardTemplateItemDefine(examStandardTemplateDefine.getId()));
	}

	public List<Map<String, String>> findTemplateDatas(Map<String, String> param , String[] rowNums) {
		/*添加缓存，提高查询速度*/
		List<Map<String, String>> list = (List<Map<String, String>>)CacheUtils.get("template_"+param.get("examStardardId"));
		if(null == list){
			list = examStandardTemplateDefineDao.selectTemplateDatas(param);
			CacheUtils.put("template_"+param.get("examStardardId"), list);
		}
		List<Map<String, String>> finalList = new ArrayList<>();
//		CollectionUtils.addAll(finalList,new Object[list.size()]);
//		Collections.copy(finalList,list);

		/*筛选查询到的数据，是否为选择的*/
		Iterator<Map<String, String>> iterator = list.iterator();
		for (String x : rowNums) {
			list.stream().forEach(item -> {
				Object sd = item.get("row_num");
				/*推送失败的考评 行号（X）为空*/
				if (sd != null && StringUtils.isNotBlank(x)){
					try {
						if (x.equals(String.valueOf(sd))) {
							finalList.add(item);
						}
					}catch (Exception e){
						logger.error(sd+"");
						e.printStackTrace();
					}

				}
			});
		}
		return finalList;
	}

/*一直未用 废弃*/
/*	public List<Map<String, String>> findTemplateDatas(ExamWorkflowDatas examWorkflowDatas) {
		List<Map<String, String>> finalList =examStandardTemplateDefineDao.findTemplateDatas(examWorkflowDatas);
		return finalList;
	}*/

	/**
	 * 通过standardBaseInfoId 查询模板中的数据
	 * @param param
	 * @return
	 */
	public List<Map<String, String>> findTemplateDatasBeta(Map<String, String> param ) {
		List<Map<String, String>> finalList =examStandardTemplateDefineDao.findTemplateDatasBeta(param);
		return finalList;
	}


	public List<Map<String, String>> findTemplateDatasByOption(String optionId, String standardId) {
		List<Map<String, String>> list = examStandardTemplateDefineDao.findTemplateDatasByOption(optionId,standardId);
		return list;
	}
}