/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import java.util.List;

import com.thinkgem.jeesite.modules.affair.dao.AffairDocClassifyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassifyRange;
import com.thinkgem.jeesite.modules.affair.dao.AffairDocClassifyRangeDao;

/**
 * 使用范围设置Service
 *
 * @author kevin.jia
 * @version 2020-07-31
 */
@Service
@Transactional(readOnly = true)
public class AffairDocClassifyRangeService extends CrudService<AffairDocClassifyRangeDao, AffairDocClassifyRange> {

    @Autowired
    AffairDocClassifyRangeDao affairDocClassifyRangeDao;

    @Autowired
    AffairDocClassifyDao affairDocClassifyDao;

    public AffairDocClassifyRange get(String id) {
        return super.get(id);
    }

    public List<AffairDocClassifyRange> findList(AffairDocClassifyRange affairDocClassifyRange) {
        return super.findList(affairDocClassifyRange);
    }

    public Page<AffairDocClassifyRange> findPage(Page<AffairDocClassifyRange> page, AffairDocClassifyRange affairDocClassifyRange) {
        return super.findPage(page, affairDocClassifyRange);
    }

    @Transactional(readOnly = false)
    public void save(AffairDocClassifyRange affairDocClassifyRange) {
        try {
            String id = affairDocClassifyDao.getByTempId(affairDocClassifyRange.getTempId());
            if (id != null) {
                if (!id.isEmpty()) {
                    affairDocClassifyRange.setClassifyId(id);
                }
            }
            super.save(affairDocClassifyRange);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = false)
    public void delete(AffairDocClassifyRange affairDocClassifyRange) {
        super.delete(affairDocClassifyRange);
    }

    @Transactional(readOnly = false)
    public void updateClassifyId(String tempId){
        //根据临时id得到相应使用范围设置列表
        List<AffairDocClassifyRange> classifyRanges = affairDocClassifyRangeDao.getByTempId(tempId);
        if(classifyRanges.size()>0){
            String classifyId = affairDocClassifyDao.getByTempId(tempId);
            affairDocClassifyRangeDao.updateClassifyId(classifyId,classifyRanges);
        }
    }
    @Transactional(readOnly = false)
    public void deleteByClassifyIds(List<String> ids) {
        affairDocClassifyRangeDao.deleteByClassifyIds(ids);
    }
}