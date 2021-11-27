/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.Date;
import java.util.List;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNoticeSign;
import com.thinkgem.jeesite.modules.affair.dao.AffairFileNoticeSignDao;

/**
 * 党建文件通知通报签收Service
 * @author eav.liu
 * @version 2019-11-01
 */
@Service
@Transactional(readOnly = true)
public class AffairFileNoticeSignService extends CrudService<AffairFileNoticeSignDao, AffairFileNoticeSign> {

    @Autowired
    private AffairFileNoticeSignDao affairFileNoticeSignDao;

	public AffairFileNoticeSign get(String id) {
		return super.get(id);
	}
	
	public List<AffairFileNoticeSign> findList(AffairFileNoticeSign affairFileNoticeSign) {
		return super.findList(affairFileNoticeSign);
	}
	
	public Page<AffairFileNoticeSign> findPage(Page<AffairFileNoticeSign> page, AffairFileNoticeSign affairFileNoticeSign, int flag) {
		page.setPageSize(1);
		if(flag == 1){
			affairFileNoticeSign.setFlag("1");
		}else {
			affairFileNoticeSign.setFlag("2");
		}
		return super.findPage(page, affairFileNoticeSign);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairFileNoticeSign affairFileNoticeSign) {
		super.save(affairFileNoticeSign);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairFileNoticeSign affairFileNoticeSign) {
		super.delete(affairFileNoticeSign);
	}

    @Transactional(readOnly = false)
    public List<AffairFileNoticeSign> findSign(AffairFileNoticeSign affairFileNoticeSign) {
        return affairFileNoticeSignDao.findSign(affairFileNoticeSign);
    }

    @Transactional(readOnly = false)
    public List<AffairFileNoticeSign> findNotSign(AffairFileNoticeSign affairFileNoticeSign) {
        return affairFileNoticeSignDao.findNotSign(affairFileNoticeSign);
    }

    @Transactional(readOnly = false)
    public void urge(AffairFileNoticeSign affairFileNoticeSign) {
        AffairFileNoticeSign old = super.get(affairFileNoticeSign.getId());
        Integer urgeNUm = old.getUrge();
        if (urgeNUm == null){
            urgeNUm = 0;
        }
        old.setUrge(urgeNUm+1);
        affairFileNoticeSignDao.update(old);
    }

	/**
	 * 批量签收
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void sign(List<String> ids) {
		for (String id : ids){
			signOne(id);
		}
	}

	@Transactional(readOnly = false)
	public void signOne(String id) {
		AffairFileNoticeSign affairFileNoticeSign = new AffairFileNoticeSign();
		affairFileNoticeSign.setNoticeId(id);
		affairFileNoticeSign.setOrgId(UserUtils.getUser().getOffice().getId());
		List<AffairFileNoticeSign> list = affairFileNoticeSignDao.findList(affairFileNoticeSign);
		if (list != null && list.size() > 0){
			AffairFileNoticeSign affairFileNoticeSign1 = list.get(0);
			affairFileNoticeSign1.setSign("1");
			affairFileNoticeSign1.setDate(new Date());
			affairFileNoticeSignDao.update(affairFileNoticeSign1);
		}
	}

}