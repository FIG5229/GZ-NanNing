/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowSegmentsDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorflowSegments;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 考评管理Service
 * @author bradley.zhao
 * @version 2019-12-20
 */
@Service
@Transactional(readOnly = true)
public class ExamWorkflowService extends CrudService<ExamWorkflowDao, ExamWorkflow> {

	@Autowired
	private ExamWorkflowSegmentsDao examWorkflowSegmentsDao;

	@Autowired
	private ExamWorkflowDao examWorkflowDao;
	
	public ExamWorkflow get(String id) {
		ExamWorkflow examWorkflow = super.get(id);
//		ExamWorflowSegments examWorflowSegments= new ExamWorflowSegments();
//		examWorflowSegments.setFlowId(id);
//		List<ExamWorflowSegments> examWorflowSegmentsList = examWorkflowSegmentsDao.findList(examWorflowSegments);
//		examWorkflow.setExamWorflowSegmentsList(examWorflowSegmentsList);
		return examWorkflow;
	}
	
	public List<ExamWorkflow> findList(ExamWorkflow examWorkflow) {
		return super.findList(examWorkflow);
	}
	
	public Page<ExamWorkflow> findPage(Page<ExamWorkflow> page, ExamWorkflow examWorkflow) {
		examWorkflow.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		String userId = UserUtils.getUser().getId();
		try {
			User user = UserUtils.getUser();
			//处理非绩效办、admin账号，拥有查看全局权限导致查看全局考评数据，例如南宁局办公室  可查看全局 民警考评
			if(StringUtils.isBlank(examWorkflow.getSqlMap().get("dsf"))&& !user.isAdmin() ){
				List<Role> roles = user.getRoleList();
				//判断当前用户是否含有绩效办角色  16f2556e4a4147e0ada71632cf0adfce 局绩效办  838ee78212f746249fe1b9cffd7a4457 处绩效办-绩效   a3c1b3cb60594968b5de214e95d248b7 局绩效办-绩效
				long count = roles.stream().filter(role ->
						"16f2556e4a4147e0ada71632cf0adfce".equals(role.getId()) || "838ee78212f746249fe1b9cffd7a4457".equals(role.getId()) ||"a3c1b3cb60594968b5de214e95d248b7".equals(role.getId())
								|| "bureau_exam_org".equals(role.getEnname()) || "cjxb-jx".equals(role.getEnname()) || "jjxb-jx".equals(role.getEnname())
				).count();
				if(count==0){
					//dsf ->  AND (o.id = '9' OR o.id = '9' OR o.parent_ids LIKE '0,1,9,%' OR u.id = 'ff7f9fe2597b40429ded58f8b76a2f65')
					String officeId = user.getOffice().getId();
					String parentIds = user.getOffice().getParentIds() + user.getOffice().getId();
					examWorkflow.getSqlMap().put("dsf","AND (o.id = '"+officeId+"' OR o.parent_id = '"+officeId+"' OR o.parent_ids LIKE '"+parentIds+",%' OR u.id = '"+userId+"')");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		/*局考处时 处绩效办查看本处 处领导考评时处绩效办也要看*/
		if (examWorkflow.getExamType().equals("1") || examWorkflow.getExamType().equals("5")){
			if (userId.equals("6af0e615e9b0477da82eff06ff532c8b") || userId.equals("46c521bf67e24db28772b3eac52dc454") || userId.equals("978958003ea44a4bba3eed8ee6ceff3c")){
				examWorkflow.getSqlMap().put("dsf", null);
			}
		}
		return super.findPage(page, examWorkflow);
	}

	/*查询考评和考评流程类型*/
	public List<Map<String,String>> selectFlowList(Map<String,String> param){
		return examWorkflowDao.selectFlowList(param);
	}

	/**
	 * 查询和用户有关的考评数据
	 * @param param
	 * @return
	 */
	public List<Map<String,String>> selectByPerson(Map<String,String> param){
		return examWorkflowDao.selectByPerson(param);
	}

	@Transactional(readOnly = false)
	public void updateStatus(Map<String,Object> param){
		examWorkflowDao.updateStatus(param);
	}

	@Transactional(readOnly = false)
	public void save(ExamWorkflow examWorkflow) {
		super.save(examWorkflow);
		ExamWorflowSegments examWorflowSegment = new ExamWorflowSegments();
		examWorflowSegment.setFlowId(examWorkflow.getId());
		examWorkflowSegmentsDao.deleteByFlowId(examWorflowSegment);
		for (ExamWorflowSegments examWorflowSegments : examWorkflow.getExamWorflowSegmentsList()){
//			if (examWorflowSegments.getId() == null){
//				continue;
//			}
//			if (ExamWorflowSegments.DEL_FLAG_NORMAL.equals(examWorflowSegments.getDelFlag())){
//				if (StringUtils.isBlank(examWorflowSegments.getId())){
//					examWorflowSegments.setFlowId(examWorkflow.getId());
//					examWorflowSegments.preInsert();
//					examWorkflowSegmentsDao.insert(examWorflowSegments);
//				}else{
//					examWorflowSegments.preUpdate();
//					examWorkflowSegmentsDao.update(examWorflowSegments);
//				}
//			}else{
//				examWorkflowSegmentsDao.delete(examWorflowSegments);
//			}
			examWorflowSegments.setFlowId(examWorkflow.getId());
			examWorflowSegments.preInsert();
			examWorkflowSegmentsDao.insert(examWorflowSegments);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExamWorkflow examWorkflow) {
		super.delete(examWorkflow);
		ExamWorflowSegments examWorflowSegments= new ExamWorflowSegments();
		examWorflowSegments.setFlowId(examWorkflow.getId());
		examWorkflowSegmentsDao.delete(examWorflowSegments);
	}

	// 绩效自动考评
	public List<Map<String, String>> findAllInfo(String id){
		return examWorkflowDao.findAllInfo(id);
	}

	/**
	 * 批量退回 结束
	 * @param examWorkflow
	 */
	@Transactional(readOnly = false)
	public void saveExamBeta(ExamWorkflow examWorkflow) {
		dao.saveExamBeta(examWorkflow);
	}
	// 预警 获取未结束考评
	public List<ExamWorkflow> getNoEndExamList() {
		return  dao.getNoEndExamList();
	}
}