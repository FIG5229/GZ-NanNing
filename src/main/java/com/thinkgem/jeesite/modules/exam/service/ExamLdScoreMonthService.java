/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamLdScoreMonthDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWorkflowDatasDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScore;
import com.thinkgem.jeesite.modules.exam.entity.ExamLdScoreMonth;
import com.thinkgem.jeesite.modules.exam.entity.ExamWorkflow;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelBaseDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 月度领导考核评分Service
 * @author cecil.li
 * @version 2020-02-12
 */
@Service
@Transactional(readOnly = true)
public class ExamLdScoreMonthService extends CrudService<ExamLdScoreMonthDao, ExamLdScoreMonth> {

	@Autowired
	private ExamLdScoreMonthDao examLdScoreMonthDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PersonnelBaseDao personnelBaseDao;

	@Autowired
	private ExamWorkflowDatasDao examWorkflowDatasDao;

	public ExamLdScoreMonth get(String id) {
		return super.get(id);
	}

	public List<ExamLdScoreMonth> findList(ExamLdScoreMonth examLdScoreMonth) {
		return super.findList(examLdScoreMonth);
	}

	public Page<ExamLdScoreMonth> findPage(Page<ExamLdScoreMonth> page, ExamLdScoreMonth examLdScoreMonth) {
		String userId = UserUtils.getUser().getId();
		examLdScoreMonth.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "b"));
		/*其他人查看时不再查看自己的*/
		/*if (userId.equals("cca66e1339f14799b01f6db43ed16e16") || userId.equals("978958003ea44a4bba3eed8ee6ceff3c")||
				userId.equals("46c521bf67e24db28772b3eac52dc454")|| userId.equals("6af0e615e9b0477da82eff06ff532c8b")){
			examLdScoreMonth.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "b"));
		}else {
			examLdScoreMonth.getSqlMap().put("dsf", "and b.id = '"+userId+"'");
		}*/
		return super.findPage(page, examLdScoreMonth);
	}

	public List<ExamLdScoreMonth> findListInfo(ExamLdScoreMonth examLdScoreMonth) {
		return examLdScoreMonthDao.findListInfo(examLdScoreMonth);
	}

	/*public List<String> findAllChildIdById(String id) {
		return examLdScoreMonthDao.findAllChildIdById(id);
	}*/

	/*public Page<ExamLdScoreMonth> findPageInfo(Page<ExamLdScoreMonth> page, ExamLdScoreMonth examLdScoreMonth) {
		return examLdScoreMonthDao.findPageInfo(page, examLdScoreMonth);
	}*/

	@Transactional(readOnly = false)
	public void save(ExamLdScoreMonth examLdScoreMonth) {
		super.save(examLdScoreMonth);
	}

	@Transactional(readOnly = false)
	public void delete(ExamLdScoreMonth examLdScoreMonth) {
		super.delete(examLdScoreMonth);
	}

	/**
	 * 民警月度考评数据保存
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void monthPersonDatasSave(List<Map<String,String>> list, ExamWorkflow examWorkflow) {
		if (null != list) {
			for (Map<String, String> data : list) {
				ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
				examLdScoreMonth.setWorkflowId(examWorkflow.getId());
				examLdScoreMonth.setPersonId(data.get("objId"));
				examLdScoreMonth.setName(data.get("objName"));
				String finalValue = "0";
				/*没有finalValue，只有origalValue*/
				if(null != data.get("origalValue")){
					Object obj=data.get("origalValue");
					finalValue = String.valueOf(Double.parseDouble(obj.toString())+100);
				}else{
					finalValue = "100";
				}
				examLdScoreMonth.setScore(finalValue);
				super.save(examLdScoreMonth);
			}
		}
	}


	/**
	 * 民警月度考评数据保存
	 * @param list
	 */
	@Transactional(readOnly = false)
	public void monthPersonDatasSaveBeta(List<Map<String,String>> list,ExamWorkflow examWorkflow) {
		String basicScore = DictUtils.getDictValue(examWorkflow.getExamType(),"exam_basicScore","100");
		double baseSum = 100.0;//总得分
		try {
			baseSum = Double.valueOf(basicScore);
		}catch (Exception e){
			e.printStackTrace();
			baseSum = 100.0;
		}
		if (null != list) {
			for (Map<String, String> data : list) {
				String userId = data.get("objId");
				User user = UserUtils.get(userId);
				PersonnelBase personnelBase =personnelBaseDao.findInfoByIdNumber(user.getNo());
				ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
				examLdScoreMonth.setWorkflowId(examWorkflow.getId());
				examLdScoreMonth.setPersonId(data.get("objId"));
				examLdScoreMonth.setName(data.get("objName"));
				if(personnelBase!=null){
					examLdScoreMonth.setJob(personnelBase.getJobAbbreviation());
					examLdScoreMonth.setUnitId(personnelBase.getWorkunitId());
					examLdScoreMonth.setUnit(personnelBase.getWorkunitName());
				}

				String finalValue = "0";
				/*没有finalValue，只有origalValue*/
				if(null != data.get("origalValue")){
					Object obj=data.get("origalValue");
					finalValue = String.valueOf(Double.parseDouble(obj.toString())+baseSum);
				}else{
					finalValue = String.valueOf(baseSum);
				}
				examLdScoreMonth.setScore(finalValue);
				List<String> itemList = examWorkflowDatasDao.selPersonItems(examWorkflow.getId(),userId);
				StringBuffer items = null;
				for(String item : itemList){
					if(item!=null){
						if(items == null){
							items = new StringBuffer(item);
						}else{
							items.append(";       "+item);
						}
					}
				}
				if(items!=null){
					examLdScoreMonth.setMatter(items.toString());
				}
				super.save(examLdScoreMonth);
			}
		}
	}

	/**
	 * 获取年度所有月份分数
	 * @param param
	 * @return
	 */
	public List<Map<String,String>> findYearMonthScores(Map<String,String> param){
		return examLdScoreMonthDao.findYearMonthScores(param);
	}

	//普通民警
	public List<Map<String, Object>> findInfoByUnitId(String id, String month) {
		return dao.findInfoByUnitId(id, month);
	}

	public List<Map<String, Object>> findInfoByUnitIds(List<String> ids, String month) {
		return dao.findInfoByUnitIds(ids, month);
	}
	//统计分析 - 人员   -  月度
	public  Map<String,Object> findPersonnelMonthExamList(Page<ExamLdScoreMonth> tPage, String month, String reasonType, String selUnitId, String unitId, String jz, String zw, String ageStart, String ageEnd) {
		Map<String,Object> resultMap = new HashMap<>();
		ExamLdScoreMonth examLdScoreMonth = new ExamLdScoreMonth();
		examLdScoreMonth.setPage(tPage);
		User user = UserUtils.getUser();
		List<Map<String,String>> unitList ;
		examLdScoreMonth.setUserId(user.getId());
		examLdScoreMonth.setTime(month);
		examLdScoreMonth.setJob(zw);//职务
		examLdScoreMonth.setJz(jz);//警种
		examLdScoreMonth.setOfficeId(unitId);//单位id
		examLdScoreMonth.setUnitId(unitId);//单位id,根据左侧树显示相应人员信息
		Integer ageStartInt;
		try {
			ageStartInt = Integer.valueOf(ageStart);
		}catch (Exception e){
			System.out.println(e.getCause());
			ageStartInt = null;
		}
		Integer ageEndInt;
		try {
			ageEndInt = Integer.valueOf(ageEnd);
		}catch (Exception e){
			System.out.println(e.getCause());
			ageEndInt = null;
		}
		examLdScoreMonth.setAgeStart(ageStartInt);//年龄1
		examLdScoreMonth.setAgeEnd(ageEndInt);//年龄2
		List<ExamLdScoreMonth> examLdScoreMonthList = new ArrayList<>();
		if("1".equals(reasonType)){
			unitList = new ArrayList<>();  //显示在页面tab标签
			//处领导
			if("1".equals(user.getOffice().getId())){
				Map<String,String> nncMap = new HashMap<>();
				nncMap.put("unitName","南宁处领导班子");
				nncMap.put("unitId","34");
				Map<String,String> lzcMap = new HashMap<>();
				lzcMap.put("unitName","柳州处领导班子");
				lzcMap.put("unitId","95");
				Map<String,String> bhcMap = new HashMap<>();
				bhcMap.put("unitName","北海处领导班子");
				bhcMap.put("unitId","156");
				Map<String,String> zMap = new HashMap<>();
				zMap.put("unitName","公安处领导班子考评情况（总）");
				zMap.put("unitId","1");
				unitList.add(nncMap);
				unitList.add(lzcMap);
				unitList.add(bhcMap);
				unitList.add(zMap);
			}else{
				Office office = user.getOffice();
				Map<String,String> map = new HashMap<>();
				if(office.getId().equals("34")||office.getParentIds().indexOf(",34,")!=-1){
					map.put("unitName","南宁处领导班子");
					map.put("unitId","34");
				}else if(office.getId().equals("95")||office.getParentIds().indexOf(",95,")!=-1){
					map.put("unitName","柳州处领导班子");
					map.put("unitId","95");
				}else if(office.getId().equals("156")||office.getParentIds().indexOf(",156,")!=-1){
					map.put("unitName","北海处领导班子");
					map.put("unitId","156");
				}
				unitList.add(map);
			}
			if(StringUtils.isBlank(selUnitId)){
				examLdScoreMonth.setOfficeId(unitList.get(0).get("unitId"));
				selUnitId = unitList.get(0).get("unitId");
			}else{
				examLdScoreMonth.setOfficeId(selUnitId);
			}
			resultMap.put("unitList",unitList);
			resultMap.put("selUnitId",selUnitId);
			examLdScoreMonth.setExamType("5");
			examLdScoreMonthList = dao.findPersonnelMonthExamList(examLdScoreMonth);
		}else if("2".equals(reasonType)){
			//中基层领导
			unitList = new ArrayList<>();
			if("1".equals(user.getOffice().getId())){
				Map<String,String> zMap = new HashMap<>();
				zMap.put("unitName","局机关及直属支队领导干部");
				zMap.put("unitId","1");
				Map<String,String> nncMap = new HashMap<>();
				nncMap.put("unitName","南宁处所队领导干部");
				nncMap.put("unitId","34_1");
				Map<String,String> lzcMap = new HashMap<>();
				lzcMap.put("unitName","柳州处所队领导干部");
				lzcMap.put("unitId","95_1");
				Map<String,String> bhcMap = new HashMap<>();
				bhcMap.put("unitName","北海处所队领导干部");
				bhcMap.put("unitId","156_1");
				Map<String,String> nncjgMap = new HashMap<>();
				nncjgMap.put("unitName","南宁处机关及职能支队领导干部");
				nncjgMap.put("unitId","34_2");
				Map<String,String> lzcjgMap = new HashMap<>();
				lzcjgMap.put("unitName","柳州处机关及职能支队领导干部");
				lzcjgMap.put("unitId","95_2");
				Map<String,String> bhcjgMap = new HashMap<>();
				bhcjgMap.put("unitName","北海处机关及职能支队领导干部");
				bhcjgMap.put("unitId","156_2");
				unitList.add(zMap);
				unitList.add(nncMap);
				unitList.add(lzcMap);
				unitList.add(bhcMap);
				unitList.add(nncjgMap);
				unitList.add(lzcjgMap);
				unitList.add(bhcjgMap);
			}
			else{
				Office office = user.getOffice();
				Map<String,String> map = new HashMap<>();
				Map<String,String> jgmap = new HashMap<>();
				if(office.getId().equals("34")||office.getParentIds().indexOf(",34,")!=-1){
					map.put("unitName","南宁处所队领导干部");
					map.put("unitId","34_1");
					jgmap.put("unitName","南宁处机关及职能支队领导干部");
					jgmap.put("unitId","34_2");
				}else if(office.getId().equals("95")||office.getParentIds().indexOf(",95,")!=-1){
					map.put("unitName","柳州处领导班子");
					map.put("unitId","95_1");
					jgmap.put("unitName","柳州处机关及职能支队领导干部");
					jgmap.put("unitId","95_2");
				}else if(office.getId().equals("156")||office.getParentIds().indexOf(",156,")!=-1){
					map.put("unitName","北海处领导班子");
					map.put("unitId","156_1");
					jgmap.put("unitName","北海处机关及职能支队领导干部");
					jgmap.put("unitId","156_2");
				}
				unitList.add(map);
				unitList.add(jgmap);
			}
			if(StringUtils.isBlank(selUnitId)){
				examLdScoreMonth.setOfficeId(unitList.get(0).get("unitId"));
				selUnitId = unitList.get(0).get("unitId");
			}
			resultMap.put("unitList",unitList);
			resultMap.put("selUnitId",selUnitId);
			examLdScoreMonth.setExamType("6");
			if("1".equals(selUnitId)){
				//局机关及直属支队领导干部考评情况
				examLdScoreMonth.setOfficeId("1");
				examLdScoreMonthList = dao.findPersonnelZJCJJGMonthExamList(examLdScoreMonth);
			}else{
				String[] selUnitIdSplit = selUnitId.split("\\_");
				if("1".equals(selUnitIdSplit[1])){
					//队所领导
					examLdScoreMonth.setOfficeId(selUnitIdSplit[0]);
					examLdScoreMonthList = dao.findPersonnelZJCDSMonthExamList(examLdScoreMonth);
				}else if("2".equals(selUnitIdSplit[1])){
					//处机关领导
					examLdScoreMonth.setOfficeId(selUnitIdSplit[0]);
					examLdScoreMonthList = dao.findPersonnelZJCCJGMonthExamList(examLdScoreMonth);
				}
			}
		}else{
			//民警
			examLdScoreMonth.setExamType("7");
			examLdScoreMonthList = dao.findPersonnelMonthExamList(examLdScoreMonth);
		}
		tPage.setList(examLdScoreMonthList);
		resultMap.put("page",tPage);
		return resultMap;
	}
	public List<ExamLdScoreMonth> findMonthList(ExamLdScoreMonth examLdScoreMonth) {
		return dao.findMonthList(examLdScoreMonth);
	}
	@Transactional(readOnly = false)
	public void deleteByWorkflowId(String workflowId) {
		dao.deleteByWorkflowId(workflowId);
	}
}