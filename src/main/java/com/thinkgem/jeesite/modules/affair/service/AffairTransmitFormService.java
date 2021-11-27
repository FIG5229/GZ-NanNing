/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTransmitFormDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairTransmitPersonDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransmitForm;
import com.thinkgem.jeesite.modules.affair.entity.AffairTransmitPerson;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 传递单Service
 * @author mason.xv
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class AffairTransmitFormService extends CrudService<AffairTransmitFormDao, AffairTransmitForm> {

	@Autowired
	private AffairTransmitPersonDao affairTransmitPersonDao;
	
	public AffairTransmitForm get(String id) {
		AffairTransmitForm affairTransmitForm = super.get(id);
		affairTransmitForm.setAffairTransmitPersonList(affairTransmitPersonDao.findList(new AffairTransmitPerson(affairTransmitForm.getId())));
		return affairTransmitForm;
	}
	
	public List<AffairTransmitForm> findList(AffairTransmitForm affairTransmitForm) {
		return super.findList(affairTransmitForm);
	}
	
	public Page<AffairTransmitForm> findPage(Page<AffairTransmitForm> page, AffairTransmitForm affairTransmitForm) {
		affairTransmitForm.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairTransmitForm);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTransmitForm affairTransmitForm) {
		super.save(affairTransmitForm);
		for (AffairTransmitPerson affairTransmitPerson : affairTransmitForm.getAffairTransmitPersonList()){
			if (affairTransmitPerson.getId() == null){
				continue;
			}
			if (AffairTransmitPerson.DEL_FLAG_NORMAL.equals(affairTransmitPerson.getDelFlag())){
				if (StringUtils.isBlank(affairTransmitPerson.getId())){
					affairTransmitPerson.setFormId(affairTransmitForm.getId());
					affairTransmitPerson.preInsert();
					affairTransmitPersonDao.insert(affairTransmitPerson);
				}else{
					affairTransmitPerson.preUpdate();
					affairTransmitPersonDao.update(affairTransmitPerson);
				}
			}else{
				affairTransmitPersonDao.delete(affairTransmitPerson);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTransmitForm affairTransmitForm) {
		super.delete(affairTransmitForm);
		affairTransmitPersonDao.delete(new AffairTransmitPerson(affairTransmitForm.getId()));
	}
	
}