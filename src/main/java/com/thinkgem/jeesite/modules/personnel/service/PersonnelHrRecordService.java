/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelHrRecordDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelHrRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 人事档案管理信息集Service
 * @author cecil.li
 * @version 2019-11-12
 */
@Service
@Transactional(readOnly = true)
public class PersonnelHrRecordService extends CrudService<PersonnelHrRecordDao, PersonnelHrRecord> {

	public PersonnelHrRecord get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelHrRecord> findList(PersonnelHrRecord personnelHrRecord) {
		return super.findList(personnelHrRecord);
	}
	
	public Page<PersonnelHrRecord> findPage(Page<PersonnelHrRecord> page, PersonnelHrRecord personnelHrRecord) {
		if ("bd42300887ad417fa3f2fa9f554e6419".equals(UserUtils.getUser().getId()) || "96d26e10bd074eecbc6b8b3a619bec1d".equals(UserUtils.getUser().getId())){
			personnelHrRecord.setUserOffice("1");
		}else if ("69f857c3e1854021b5dee55c514026e3".equals(UserUtils.getUser().getId()) || "4103b50669e9422391fb70aa704266b1".equals(UserUtils.getUser().getId())){
			personnelHrRecord.setUserOffice("34");
		}else if ("3850cecf34be44188f94b0edc552aff3".equals(UserUtils.getUser().getId()) || "3404d05bf9054a51ae9afbe40e44a718".equals(UserUtils.getUser().getId())){
			personnelHrRecord.setUserOffice("95");
		}else if ("1c19f6cc935f430f9f27295b761b1236".equals(UserUtils.getUser().getId()) || "957de2956a384bad96adbaa35cb05520".equals(UserUtils.getUser().getId())){
			personnelHrRecord.setUserOffice("156");
		}
//		personnelHrRecord.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, personnelHrRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelHrRecord personnelHrRecord) {
		super.save(personnelHrRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelHrRecord personnelHrRecord) {
		super.delete(personnelHrRecord);
	}
	
	private static Integer isContain(String flag, List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			String label = String.valueOf(map.get("label"));
			if(flag.equals(label)) {
				return Integer.valueOf(String.valueOf(map.get("count")));
			}
		}
		return 0;
	}

	public Map<String, Object> findInfoByCreateOrgId(String id, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> zhuanru = dao.findZhuanruInfo(id, year ,startDate, endDate, month);
		List<Map<String, Object>> zhuanchu = dao.findZhuanchuInfo(id, year ,startDate, endDate, month);
		List<String> labelData =  new ArrayList<>();
		for(Map<String, Object> map : zhuanru) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		for(Map<String, Object> map : zhuanchu) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		List<Integer> zhuanruData = new ArrayList<>();
		List<Integer> zhuanchuData = new ArrayList<>();
		for(String label : labelData) {
			zhuanruData.add(isContain(label, zhuanru));
			zhuanchuData.add(isContain(label, zhuanchu));
		}
		Map<String, Object> result = new HashMap<>();
		result.put("labelData", labelData);
		result.put("zhuanruData", zhuanruData);
		result.put("zhuanchuData", zhuanchuData);
		return result;
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids, Integer year, Date startDate, Date endDate, String month) {
		List<Map<String, Object>> zhuanru = dao.findZhuanruInfos(ids, year, startDate, endDate, month);
		List<Map<String, Object>> zhuanchu = dao.findZhuanchuInfos(ids, year, startDate, endDate, month);
		List<String> labelData =  new ArrayList<>();
		for(Map<String, Object> map : zhuanru) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		for(Map<String, Object> map : zhuanchu) {
			String label = String.valueOf(map.get("label"));
			if(!labelData.contains(label)) {
				labelData.add(label);
			}
		}
		List<Integer> zhuanruData = new ArrayList<>();
		List<Integer> zhuanchuData = new ArrayList<>();
		for(String label : labelData) {
			zhuanruData.add(isContain(label, zhuanru));
			zhuanchuData.add(isContain(label, zhuanchu));
		}
		Map<String, Object> result = new HashMap<>();
		result.put("labelData", labelData);
		result.put("zhuanruData", zhuanruData);
		result.put("zhuanchuData", zhuanchuData);
		return result;
	}

	@Transactional(readOnly = false)
    public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
    }
}