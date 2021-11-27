package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairNewsCountDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairNewsCount;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AffairNewsCountService  extends CrudService<AffairNewsCountDao, AffairNewsCount> {

    @Autowired
    private AffairNewsCountDao affairNewsCountDao;

    public Page<AffairNewsCount> countByUnit(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        String officeId = UserUtils.getUser().getCompany().getId();
        affairNewsCount.setOfficeId(officeId);
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.countByUnit(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> countByJuUnit(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        String officeId = UserUtils.getUser().getCompany().getId();
        affairNewsCount.setOfficeId(officeId);
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.countByJuUnit(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> countByAuthor(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        String officeId = UserUtils.getUser().getCompany().getId();
        affairNewsCount.setOfficeId(officeId);
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.countByAuthor(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> detailCountByUnit(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.detailCountByUnit(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> detailCountByAuthor(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.detailCountByAuthor(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> detailCountByName(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.detailCountByName(affairNewsCount);
        page.setList(list);
        return page;
    }

    public Page<AffairNewsCount> countByName(Page<AffairNewsCount> page, AffairNewsCount affairNewsCount){
        String officeId = UserUtils.getUser().getCompany().getId();
        affairNewsCount.setOfficeId(officeId);
        affairNewsCount.setPage(page);
        List<AffairNewsCount> list = affairNewsCountDao.countByName(affairNewsCount);
        page.setList(list);
        return page;
    }
    public List<Map<String,String>> findAllUnit(String officeId){
        List<Map<String, String>> allUnit = affairNewsCountDao.findAllUnit(officeId);
        return allUnit;
    }
}
