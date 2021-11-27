/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.exam.dao.*;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardBaseInfo;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflowDefine;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 考评流程定义Service
 * @author eav.liu
 * @version 2019-12-09
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowDefineService extends CrudService<ExamWorkflowDefineDao, ExamWorkflowDefine> {

	@Autowired
	private ExamWorkflowSegmentGuanlianService examWorkflowSegmentGuanlianService;

	@Autowired
    private ExamWorkflowDefineDao examWorkflowDefineDao;

	@Autowired
	private ExamStandardBaseInfoDao examStandardBaseInfoDao;

	@Autowired
	private ExamStandardTemplateDataDao examStandardTemplateDataDao;

	@Autowired
	private ExamWorkflowSegmentsTaskService examWorkflowSegmentsTaskService;

	@Autowired
    private ExamWorkflowSegmentsTaskDao examWorkflowSegmentsTaskDao;

	@Autowired
	private ExamWorkflowSegmentGuanlianDao examWorkflowSegmentGuanlianDao;

	@Autowired
	private ExamWorkflowSegmentsDefineDao examWorkflowSegmentsDefineDao;

	public ExamWorkflowDefine get(String id) {
		return super.get(id);
	}
	
	public List<ExamWorkflowDefine> findList(ExamWorkflowDefine examWorkflowDefine) {
		/*暂时不添加
		此方法目前在添加考评的时候调用，其他地方无使用
		创建者混乱，目前创建者都是四个绩效办账号，添加后组干看不到*/
		examWorkflowDefine.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findList(examWorkflowDefine);
	}
	
	public Page<ExamWorkflowDefine> findPage(Page<ExamWorkflowDefine> page, ExamWorkflowDefine examWorkflowDefine) {
		/*暂时不添加
		创建者混乱，目前创建者都是四个绩效办账号，添加后组干看不到*/
		examWorkflowDefine.getSqlMap().put("dsf",dataScopeFilter(UserUtils.getUser(),"o","a"));
		return super.findPage(page, examWorkflowDefine);
	}
	
	@Transactional(readOnly = false)
	public void save(ExamWorkflowDefine examWorkflowDefine) {
		//flag  true:添加记录  false:修改记录
		Boolean flag = true;
		ExamWorkflowDefine old = null;
		if (!examWorkflowDefine.getIsNewRecord()){
			old = get(examWorkflowDefine.getId());
			flag = false;
		}
		//过滤掉空字符串
		String[] filterArr= removeNullStringArray(examWorkflowDefine.getTemplatesIdsArr());
		if(filterArr != null && filterArr.length > 0){
			String arr = StringUtils.join(filterArr, ",");
			examWorkflowDefine.setTemplatesIds(arr);
		}else{
            examWorkflowDefine.setTemplatesIds(null);
        }
		//添加时默认不启用
        if(examWorkflowDefine.getIsNewRecord()){
            examWorkflowDefine.setIsUse("0");
        }
		super.save(examWorkflowDefine);

//		List<ExamWorkflowSegmentGuanlian> examWorkflowSegmentGuanlianList = new ArrayList<>();
//
//		/**
//		 * 前端修改数据，设置流程类型为禁用，流程类型不允许再修改
//		 */
//
//		List<String> segments = null;
//		//List<ExamWorkflowSegmentGuanlian> glList = examWorkflowSegmentGuanlianDao.findByWdIdAndType(examWorkflowDefine.getId(), examWorkflowDefine.getFlowType());
//		if(flag){
//			segments = examWorkflowDefine.getSegments();//环节名称
//			List<Integer> sorts = examWorkflowDefine.getSorts();//环节排序
//			//segments 和 sorts 不需要再做非空判断
//			for (int i=0 ; i < segments.size(); i++){
//				ExamWorkflowSegmentGuanlian gl = new ExamWorkflowSegmentGuanlian();
//				gl.setWorkflowDefineId(examWorkflowDefine.getId());
//				gl.setType(examWorkflowDefine.getFlowType());
//				gl.setName(segments.get(i));
//				gl.setSort(sorts.get(i));
//				gl.setStatus("未分配");
//				//gl.setSegDefId();
//				gl.setSegDefId(examWorkflowSegmentsDefineDao.findIdByTypeAndName(examWorkflowDefine.getFlowType(),segments.get(i)));
//				examWorkflowSegmentGuanlianService.save(gl);
//				examWorkflowSegmentGuanlianList.add(gl);
//			}
//		}
//		/**根据选择的考评流程模板向任务分配表插入数据
//		 * 如果是添加流程，直接向任务分配表插入数据
//		 * 如果是修改流程，则先判断考评流程模板是否改变，如果考评流程模板改变了，向任务分配表插入新数据，之前的数据删除作废，如果考评流程模板没改变，任务分配表不做额外数据处理
//		 */
//		if (flag){
//			//添加
//			if(filterArr != null && filterArr.length >0){
//				for (String str:filterArr) {
//					List<Map<String, Object>> list = examStandardTemplateDataDao.findWorkTypeByStandardId(str);
//					if (list != null && list.size() > 0){
//						for (Map<String, Object> m:list) {
//							for (int i=0 ; i < examWorkflowSegmentGuanlianList.size(); i++){
//								ExamWorkflowSegmentsTask task = new ExamWorkflowSegmentsTask();
//								task.setTagType(str);
//								task.setWorkType(m.get("item").toString());
//								task.setWorkflowId(examWorkflowDefine.getId());
//								//task.setSegmentId(examWorkflowSegmentGuanlianList.get(i).getId());
//								task.setSegmentId(examWorkflowSegmentGuanlianList.get(i).getSegDefId());
//								if(m.get("row") != null){
//									task.setRowNum(Integer.valueOf(m.get("row").toString()));
//								}
//
//								examWorkflowSegmentsTaskService.save(task);
//							}
//						}
//					}
//				}
//			}
//		}else{
//			//修改
//			if(!old.getTemplatesIds().equals(examWorkflowDefine.getTemplatesIds())){
//                if(examWorkflowDefine.getTemplatesIds() == null){
//                    //考评标准为空，删除该流程所有的任务分配
//                    examWorkflowSegmentsTaskDao.deleteAllByWorkflowId(examWorkflowDefine.getId());
//                }else{
//                    List<ExamWorkflowSegmentGuanlian> wfGLList = examWorkflowSegmentGuanlianDao.findByWdIdAndType(examWorkflowDefine.getId(), examWorkflowDefine.getFlowType());
//                    for (String  newStr: filterArr) {
//                        if (!old.getTemplatesIds().contains(newStr)){
//                            //插入
//							List<Map<String, Object>> list2 = examStandardTemplateDataDao.findWorkTypeByStandardId(newStr);
//							if (list2 != null && list2.size() > 0){
//								for (Map<String, Object> m:list2) {
//									for (int i=0 ; i < wfGLList.size(); i++){
//										ExamWorkflowSegmentsTask task = new ExamWorkflowSegmentsTask();
//										task.setTagType(newStr);
//										task.setWorkType(m.get("item").toString());
//										task.setWorkflowId(examWorkflowDefine.getId());
//										task.setSegmentId(wfGLList.get(i).getSegDefId());
//										if(m.get("row") != null){
//											task.setRowNum(Integer.valueOf(m.get("row").toString()));
//										}
//										examWorkflowSegmentsTaskService.save(task);
//									}
//								}
//							}
//                        }
//                    }
//                    //去掉的考评标准执行删除
//                    examWorkflowSegmentsTaskDao.deleteTask(examWorkflowDefine.getId(), Arrays.asList(filterArr));
//
//                    //更新每个环节,人员分配状态
//                    for (ExamWorkflowSegmentGuanlian wfSegment:wfGLList) {
//                        Integer sum = examWorkflowSegmentsTaskDao.findSumByWdIdAndSegId(examWorkflowDefine.getId(), wfSegment.getId());
//                        Integer num= examWorkflowSegmentsTaskDao.findNumByWdIdAndSegIdAndIds(examWorkflowDefine.getId(), wfSegment.getId());
//                        if(0 == num.intValue() ){
//                            wfSegment.setStatus("未分配");
//                            examWorkflowSegmentGuanlianService.save(wfSegment);
//                        }else if(sum.intValue() == num.intValue() ){
//                            wfSegment.setStatus("分配完成");
//                            examWorkflowSegmentGuanlianService.save(wfSegment);
//                        }else{
//                            wfSegment.setStatus("未全部分配完");
//                            examWorkflowSegmentGuanlianService.save(wfSegment);
//                        }
//                    }
//
//                }
//
//			}
//		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorkflowDefine examWorkflowDefine) {
		super.delete(examWorkflowDefine);
	}

    /**
     * 删除
     * @param id
     */
    @Transactional(readOnly = false)
    public void deleteById(String id) {
        //删除主表数据
		/*改为逻辑删除 防止误删时导致考评查询不出来*/
        examWorkflowDefineDao.deleteById(id);
        //删除流程环节关联表数据
        examWorkflowSegmentGuanlianDao.deleteAllByWdId(id);
        //删除任务分配表数据
        examWorkflowSegmentsTaskDao.deleteAllByWorkflowId(id);
    }

    /**
     * 是否启用考评流程
     * @param id
     */
    @Transactional(readOnly = false)
    public void isUsable(String id, String isUse){
        examWorkflowDefineDao.updateIsUse(id,isUse);
    }

	/**
	 * 考评流程模版下拉框（启用的标准)
	 * @return
	 */
	public List<Dict> templateFile(){
		//查询启用的考核标准
		List<ExamStandardBaseInfo> allList = examStandardBaseInfoDao.findStandardList();
		ArrayList<Dict> list = new ArrayList<>();
		if (allList != null && allList.size( )> 0){
			for (ExamStandardBaseInfo e: allList) {
				Dict dict = new Dict();
				dict.setLabel(e.getName());
				dict.setValue(e.getId());
				dict.setId(e.getId());
				list.add(dict);
			}
		}
		return list;
	}
	/** 进行数据过滤
	 * 考评流程模版下拉框（启用的标准)
	 * @return
	 */
	public List<Dict> templateFile(boolean isFilter){
		ExamStandardBaseInfo examStandardBaseInfo= new ExamStandardBaseInfo();
		if (isFilter){
			examStandardBaseInfo.getSqlMap().put("dsf",alterDataScopeFilter(UserUtils.getUser(),"o","a"));
		}
		//查询启用的考核标准
		List<ExamStandardBaseInfo> allList = examStandardBaseInfoDao.findStandardListFilter(examStandardBaseInfo);
		ArrayList<Dict> list = new ArrayList<>();
		if (allList != null && allList.size( )> 0){
			for (ExamStandardBaseInfo e: allList) {
				Dict dict = new Dict();
				dict.setLabel(e.getName());
				dict.setValue(e.getId());
				dict.setId(e.getId());
				list.add(dict);
			}
		}
		return list;
	}

	/**
	 * 过滤掉String数组里面的空字符串
	 * @param arrayString
	 * @return
	 */
	public String[] removeNullStringArray(String[] arrayString) {
		if(arrayString !=null && arrayString.length >0){
			List<String> list1 = new ArrayList<String>();
			for (int i=0 ;i<arrayString.length; i++) {
				if(arrayString[i]!=null && arrayString[i] !=""){
					list1.add(arrayString[i]);
				}
			}
			return list1.toArray(new String[list1.size()]);
		}
		return null;
	}

	/**
	 * 绩效自动考评
	 * @param examWorkflowDefine
	 * @return
	 */
	public List<ExamWorkflowDefine> findAllInfo(ExamWorkflowDefine examWorkflowDefine){
		return examWorkflowDefineDao.findAllInfo(examWorkflowDefine);
	}
}