/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamWeightsDao;
import com.thinkgem.jeesite.modules.exam.dao.ExamWeightsMainDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeights;
import com.thinkgem.jeesite.modules.exam.entity.ExamWeightsMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权重Service
 *
 * @author cecil.li
 * @version 2020-01-17
 */
@Service
@Transactional(readOnly = true)
public class ExamWeightsService extends CrudService<ExamWeightsDao, ExamWeights> {

    @Autowired
    private ExamWeightsMainDao examWeightsMainDao;

    public ExamWeights get(String id) {
        ExamWeights examWeights = super.get(id);
        examWeights.setExamWeightsMainList(examWeightsMainDao.findList(new ExamWeightsMain(examWeights.getId())));
        return examWeights;
    }

    public List<ExamWeights> findList(ExamWeights examWeights) {
        return super.findList(examWeights);
    }

    public List<ExamWeights> findWeightsList(ExamWeights examWeights) {
        List<ExamWeights> list = null;
        if (null != CacheUtils.get("weights")) {
            list = (List<ExamWeights>) CacheUtils.get("weights");
        } else {
            list = super.findList(examWeights);
            CacheUtils.put("weights",list);
        }
        return list;
    }

    public Page<ExamWeights> findPage(Page<ExamWeights> page, ExamWeights examWeights) {
        return super.findPage(page, examWeights);
    }

    @Transactional(readOnly = false)
    public void save(ExamWeights examWeights) {
        super.save(examWeights);
        for (ExamWeightsMain examWeightsMain : examWeights.getExamWeightsMainList()) {
            if (examWeightsMain.getId() == null) {
                continue;
            }
            if (ExamWeightsMain.DEL_FLAG_NORMAL.equals(examWeightsMain.getDelFlag())) {
                if (StringUtils.isBlank(examWeightsMain.getId())) {
                    examWeightsMain.setEwId(examWeights.getId());
                    examWeightsMain.preInsert();
                    examWeightsMainDao.insert(examWeightsMain);
                } else {
                    examWeightsMain.preUpdate();
                    examWeightsMainDao.update(examWeightsMain);
                }
            } else {
                examWeightsMainDao.delete(examWeightsMain);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(ExamWeights examWeights) {
        super.delete(examWeights);
        examWeightsMainDao.delete(new ExamWeightsMain(examWeights.getId()));
    }

    public ExamWeights getWeightByUnit(ExamWeights examWeights) {
        return dao.getWeightByUnit(examWeights);
    }
}