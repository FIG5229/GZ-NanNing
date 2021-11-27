/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairWentiTalentDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairWentiTalent;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文体人才库Service
 * @author cecil.li
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class AffairWentiTalentService extends CrudService<AffairWentiTalentDao, AffairWentiTalent> {

	@Autowired
	private AffairWentiTalentDao affairWentiTalentDao;

	public AffairWentiTalent get(String id) {
		return super.get(id);
	}
	
	public List<AffairWentiTalent> findList(AffairWentiTalent affairWentiTalent) {
		return super.findList(affairWentiTalent);
	}
	
	public Page<AffairWentiTalent> findPage(Page<AffairWentiTalent> page, AffairWentiTalent affairWentiTalent) {
		affairWentiTalent.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairWentiTalent);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairWentiTalent affairWentiTalent) {
		/*//创建一个不带任何信息的容器
		AffairWentiTalent affairWentiTalentFromDb = new AffairWentiTalent();
		//根据身份证号查重,一旦重复就修改原来的数据
		List<AffairWentiTalent> list= affairWentiTalentDao.findAllList(affairWentiTalent0);
		if (list.size() == 0 ){
			super.save(affairWentiTalent);
		}else{
			AffairWentiTalent affairWentiTalentSame= findSame(affairWentiTalent, list);
			if(affairWentiTalentSame != null){
				affairWentiTalent.setId(affairWentiTalentSame.getId());
				super.save(affairWentiTalent);
			}
			else {
				//这个地方要加友好提示
				super.save(affairWentiTalent);
			}
		}*/
		AffairWentiTalent affairWentiTalentFromDb = affairWentiTalentDao.findInfoByIdNumber(affairWentiTalent.getIdNumber());
		if(affairWentiTalentFromDb != null){
			affairWentiTalent.setId(affairWentiTalentFromDb.getId());
			super.save(affairWentiTalent);
		}else{
			super.save(affairWentiTalent);
		}

	}
	/*private AffairWentiTalent findSame(AffairWentiTalent affairWentiTalent,List<AffairWentiTalent> list){
		AffairWentiTalent result = null;
		for(AffairWentiTalent affairWentiTalent1 : list) {
			if (affairWentiTalent1.getIdNumber().equals(affairWentiTalent.getIdNumber())) {
				result= affairWentiTalent1;
				break;
			}
		}
		return  result;
	}*/
	
	@Transactional(readOnly = false)
	public void delete(AffairWentiTalent affairWentiTalent) {
		super.delete(affairWentiTalent);
	}

	public List<AffairWentiTalent> selectSpeciality(String idNumber){
		return affairWentiTalentDao.selectSpeciality(idNumber);
	}
}