/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamOtherDataChildDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamOtherDataChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查发现情况录入子表Service
 *
 * @author eav.liu
 * @version 2020-03-11
 */
@Service
@Transactional(readOnly = true)
public class ExamOtherDataChildService extends CrudService<ExamOtherDataChildDao, ExamOtherDataChild> {

    @Autowired
    private ExamOtherDataChildDao examOtherDataChildDao;
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    public ExamOtherDataChild get(String id) {
        return super.get(id);
    }

    public List<ExamOtherDataChild> findList(ExamOtherDataChild examOtherDataChild) {
        return super.findList(examOtherDataChild);
    }

    public Page<ExamOtherDataChild> findPage(Page<ExamOtherDataChild> page, ExamOtherDataChild examOtherDataChild) {
        return super.findPage(page, examOtherDataChild);
    }

    @Transactional(readOnly = false)
    public void save(ExamOtherDataChild examOtherDataChild) {
        if (StringUtils.isBlank(examOtherDataChild.getRowNum())){
            Map<String,String> param = new HashMap<>();
            param.put("standardId",examOtherDataChild.getUseModel());
            param.put("itemValue",examOtherDataChild.getChooseOptions());
            List<Map<String, String>> resultList = examStandardTemplateDefineService.findTemplateDatasBeta(param);
            if (resultList!= null && resultList.size()>0){
                Map<String, String> map = resultList.get(0);
                examOtherDataChild.setRowNum(map.get("row_num"));
            }
        }
        super.save(examOtherDataChild);
    }

    @Transactional(readOnly = false)
    public void delete(ExamOtherDataChild examOtherDataChild) {
        super.delete(examOtherDataChild);
    }

    public List<ExamOtherDataChild> findSomeByExamCheckId(String examCheckId) {
        return examOtherDataChildDao.findSomeByExamCheckId(examCheckId);
    }

    /**
     * 绩效自动考评查询使用
     * @param examOtherDataChild
     * @return
     */
    public List<ExamOtherDataChild> findExamCheckChildList(ExamOtherDataChild examOtherDataChild){
        return examOtherDataChildDao.findExamCheckList(examOtherDataChild);
    }
}