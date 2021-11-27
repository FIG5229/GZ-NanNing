/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.dao.OrgJobNumberDao;
import com.thinkgem.jeesite.modules.org.entity.OrgJobNumber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位职数信息Service
 * @author eav.liu
 * @version 2019-11-22
 */
@Service
@Transactional(readOnly = true)
public class OrgJobNumberService extends CrudService<OrgJobNumberDao, OrgJobNumber> {

	public OrgJobNumber get(String id) {
		return super.get(id);
	}
	
	public List<OrgJobNumber> findList(OrgJobNumber orgJobNumber) {
		return super.findList(orgJobNumber);
	}
	
	public Page<OrgJobNumber> findPage(Page<OrgJobNumber> page, OrgJobNumber orgJobNumber) {
		return super.findPage(page, orgJobNumber);
	}
	
	@Transactional(readOnly = false)
	public void save(OrgJobNumber orgJobNumber) {
		super.save(orgJobNumber);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrgJobNumber orgJobNumber) {
		super.delete(orgJobNumber);
	}

	public List<Map<String, Object>> findInfoByOrgId(String id, Integer year) {
		Map<String, Object> result = dao.findInfoByOrgId(id, year);
		if (result != null) {
			List<Map<String, Object>> list = new ArrayList<>();
			result.forEach((k, v) -> {
				if(!String.valueOf(v).equals("0")) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("count", v);
					if("bfNum".equals(k)) {
						m.put("label", "部级副职领导职数");
					}
					if("tzNum".equals(k)) {
						m.put("label", "厅级正职领导职数");
					}
					if("tfNum".equals(k)) {
						m.put("label", "厅级副职领导职数");
					}
					if("tzfNum".equals(k)) {
						m.put("label", "厅级正职非领导职数");
					}
					if("tffNum".equals(k)) {
						m.put("label", "厅级副职非领导职数");
					}
					if("czNum".equals(k)) {
						m.put("label", "厅级副职非领导职数");
					}
					if("cfNum".equals(k)) {
						m.put("label", "处级副职领导职数");
					}
					if("czfNum".equals(k)) {
						m.put("label", "处级正职非领导职数");
					}
					if("cffNum".equals(k)) {
						m.put("label", "处级副职非领导职数");
					}
					if("kzNum".equals(k)) {
						m.put("label", "科级正职领导职数");
					}
					if("kfNum".equals(k)) {
						m.put("label", "科级副职领导职数");
					}
					if("kzfNum".equals(k)) {
						m.put("label", "科级正职非领导职数");
					}
					if("kffNum".equals(k)) {
						m.put("label", "科级副职非领导职数");
					}
					if("gzNum".equals(k)) {
						m.put("label", "股级正职领导职数");
					}
					if("gfNum".equals(k)) {
						m.put("label", "股级副职领导职数");
					}
					list.add(m);
				}
			});
			return list;
		}
		return null;
	}

	public List<Map<String, Object>> findInfoByOrgIds(List<String> ids, Integer year) {
		Map<String, Object> result = dao.findInfoByOrgIds(ids, year);
		if (result != null) {
			List<Map<String, Object>> list = new ArrayList<>();
			result.forEach((k, v) -> {
				if(!String.valueOf(v).equals("0")) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("count", v);
					if("bfNum".equals(k)) {
						m.put("label", "部级副职领导职数");
					}
					if("tzNum".equals(k)) {
						m.put("label", "厅级正职领导职数");
					}
					if("tfNum".equals(k)) {
						m.put("label", "厅级副职领导职数");
					}
					if("tzfNum".equals(k)) {
						m.put("label", "厅级正职非领导职数");
					}
					if("tffNum".equals(k)) {
						m.put("label", "厅级副职非领导职数");
					}
					if("czNum".equals(k)) {
						m.put("label", "厅级副职非领导职数");
					}
					if("cfNum".equals(k)) {
						m.put("label", "处级副职领导职数");
					}
					if("czfNum".equals(k)) {
						m.put("label", "处级正职非领导职数");
					}
					if("cffNum".equals(k)) {
						m.put("label", "处级副职非领导职数");
					}
					if("kzNum".equals(k)) {
						m.put("label", "科级正职领导职数");
					}
					if("kfNum".equals(k)) {
						m.put("label", "科级副职领导职数");
					}
					if("kzfNum".equals(k)) {
						m.put("label", "科级正职非领导职数");
					}
					if("kffNum".equals(k)) {
						m.put("label", "科级副职非领导职数");
					}
					if("gzNum".equals(k)) {
						m.put("label", "股级正职领导职数");
					}
					if("gfNum".equals(k)) {
						m.put("label", "股级副职领导职数");
					}
					list.add(m);
				}
			});
			return list;
		}
		return null;
	}
	
}