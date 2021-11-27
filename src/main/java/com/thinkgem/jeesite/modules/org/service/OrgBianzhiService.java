/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.org.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.org.dao.OrgBianzhiDao;
import com.thinkgem.jeesite.modules.org.entity.OrgBianzhi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位编制Service
 * @author eav.liu
 * @version 2019-11-22
 */
@Service
@Transactional(readOnly = true)
public class OrgBianzhiService extends CrudService<OrgBianzhiDao, OrgBianzhi> {

	public OrgBianzhi get(String id) {
		return super.get(id);
	}
	
	public List<OrgBianzhi> findList(OrgBianzhi orgBianzhi) {
		return super.findList(orgBianzhi);
	}
	
	public Page<OrgBianzhi> findPage(Page<OrgBianzhi> page, OrgBianzhi orgBianzhi) {
		return super.findPage(page, orgBianzhi);
	}
	
	@Transactional(readOnly = false)
	public void save(OrgBianzhi orgBianzhi) {
		super.save(orgBianzhi);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrgBianzhi orgBianzhi) {
		super.delete(orgBianzhi);
	}

	public List<Map<String, Object>> findInfoByOrgId(String id, Integer year) {
		Map<String, Object> result = dao.findInfoByOrgId(id, year);
		if (result != null) {
			List<Map<String, Object>> list = new ArrayList<>();
			result.forEach((k, v) -> {
				if(!String.valueOf(v).equals("0")) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("count", v);
					if("zfzxb".equals(k)) {
						m.put("label", "政府专项编");
					}
					else if("cgsyb".equals(k)) {
						m.put("label", "参公事业编");
					}
					else if("syb".equals(k)) {
						m.put("label", "事业编");
					}else if("qe_num".equals(k)) {
						m.put("label", "全额拨款编制");
					}else if("ce_num".equals(k)) {
						m.put("label", "差额拨款编制");
					}else if("jfzl_num".equals(k)) {
						m.put("label", "经费自理编制");
					}else {
						m.put("label", "其他编制");
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
					if("zfzxb".equals(k)) {
						m.put("label", "政府专项编");
					}else if("cgsyb".equals(k)) {
						m.put("label", "参公事业编");
					}else if("syb".equals(k)) {
						m.put("label", "事业编");
					}else if("qe_num".equals(k)) {
						m.put("label", "全额拨款编制");
					}else if("ce_num".equals(k)) {
						m.put("label", "差额拨款编制");
					}else if("jfzl_num".equals(k)) {
						m.put("label", "经费自理编制");
					}else {
						m.put("label", "其他编制");
					}
					list.add(m);
				}
			});
			return list;
		}
		return null;
	}
	
}