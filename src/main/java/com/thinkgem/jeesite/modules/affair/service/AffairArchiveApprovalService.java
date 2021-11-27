/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairArchiveApprovalDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCdObjectDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairCdPersonDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairArchiveApproval;
import com.thinkgem.jeesite.modules.affair.entity.AffairCdObject;
import com.thinkgem.jeesite.modules.affair.entity.AffairCdPerson;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 查(借)阅审批Service
 * @author mason.xv
 * @version 2019-11-30
 */
@Service
@Transactional(readOnly = true)
public class AffairArchiveApprovalService extends CrudService<AffairArchiveApprovalDao, AffairArchiveApproval> {

	@Autowired
	private AffairCdObjectDao affairCdObjectDao;
	@Autowired
	private AffairCdPersonDao affairCdPersonDao;
	
	public AffairArchiveApproval get(String id) {
		AffairArchiveApproval affairArchiveApproval = super.get(id);
		affairArchiveApproval.setAffairCdObjectList(affairCdObjectDao.findList(new AffairCdObject(affairArchiveApproval.getId())));
		affairArchiveApproval.setAffairCdPersonList(affairCdPersonDao.findList(new AffairCdPerson(affairArchiveApproval.getId())));
		return affairArchiveApproval;
	}
	
	public List<AffairArchiveApproval> findList(AffairArchiveApproval affairArchiveApproval) {
		return super.findList(affairArchiveApproval);
	}
	
	public Page<AffairArchiveApproval> findPage(Page<AffairArchiveApproval> page, AffairArchiveApproval affairArchiveApproval) {
		affairArchiveApproval.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairArchiveApproval);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairArchiveApproval affairArchiveApproval) {
		super.save(affairArchiveApproval);
		for (AffairCdObject affairCdObject : affairArchiveApproval.getAffairCdObjectList()){
			if (affairCdObject.getId() == null){
				continue;
			}
			if (AffairCdObject.DEL_FLAG_NORMAL.equals(affairCdObject.getDelFlag())){
				if (StringUtils.isBlank(affairCdObject.getId())){
					affairCdObject.setApprovalId(affairArchiveApproval.getId());
					affairCdObject.preInsert();
					affairCdObjectDao.insert(affairCdObject);
				}else{
					affairCdObject.preUpdate();
					affairCdObjectDao.update(affairCdObject);
				}
			}else{
				affairCdObjectDao.delete(affairCdObject);
			}
		}
		for (AffairCdPerson affairCdPerson : affairArchiveApproval.getAffairCdPersonList()){
			if (affairCdPerson.getId() == null){
				continue;
			}
			if (AffairCdPerson.DEL_FLAG_NORMAL.equals(affairCdPerson.getDelFlag())){
				if (StringUtils.isBlank(affairCdPerson.getId())){
					affairCdPerson.setApprovalId(affairArchiveApproval.getId());
					affairCdPerson.preInsert();
					affairCdPersonDao.insert(affairCdPerson);
				}else{
					affairCdPerson.preUpdate();
					affairCdPersonDao.update(affairCdPerson);
				}
			}else{
				affairCdPersonDao.delete(affairCdPerson);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairArchiveApproval affairArchiveApproval) {
		super.delete(affairArchiveApproval);
		affairCdObjectDao.delete(new AffairCdObject(affairArchiveApproval.getId()));
		affairCdPersonDao.delete(new AffairCdPerson(affairArchiveApproval.getId()));
	}
	
}