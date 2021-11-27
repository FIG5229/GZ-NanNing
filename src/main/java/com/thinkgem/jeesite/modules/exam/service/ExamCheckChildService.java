/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.exam.dao.ExamCheckChildDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamCheckChild;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
public class ExamCheckChildService extends CrudService<ExamCheckChildDao, ExamCheckChild> {

    @Autowired
    private ExamCheckChildDao examCheckChildDao;
    private ExamStandardTemplateDefineService examStandardTemplateDefineService;

    public ExamCheckChild get(String id) {
        return super.get(id);
    }

    public List<ExamCheckChild> findList(ExamCheckChild examCheckChild) {
        return super.findList(examCheckChild);
    }

    public Page<ExamCheckChild> findPage(Page<ExamCheckChild> page, ExamCheckChild examCheckChild) {
        if ("cca66e1339f14799b01f6db43ed16e16".equals(UserUtils.getUser().getId()) || "978958003ea44a4bba3eed8ee6ceff3c".equals(UserUtils.getUser().getId()) || "6af0e615e9b0477da82eff06ff532c8b".equals(UserUtils.getUser().getId()) || "46c521bf67e24db28772b3eac52dc454".equals(UserUtils.getUser().getId())){
            examCheckChild.setUserOffice(UserUtils.getUser().getCompany().getId());
        }else {
            examCheckChild.setUserOffice(UserUtils.getUser().getOffice().getId());
        }
        return super.findPage(page, examCheckChild);
    }

    @Transactional(readOnly = false)
    public void save(ExamCheckChild examCheckChild) {
        if (StringUtils.isBlank(examCheckChild.getRowNum())){
            Map<String,String> param = new HashMap<>();
            param.put("standardId",examCheckChild.getUseModel());
            param.put("itemValue",examCheckChild.getChooseOptions());
            List<Map<String, String>> resultList = examStandardTemplateDefineService.findTemplateDatasBeta(param);
            if (resultList!= null && resultList.size()>0){
                Map<String, String> map = resultList.get(0);
                examCheckChild.setRowNum(map.get("row_num"));
            }
        }
        super.save(examCheckChild);
    }

    @Transactional(readOnly = false)
    public void delete(ExamCheckChild examCheckChild) {
        super.delete(examCheckChild);
    }

    public List<ExamCheckChild> findSomeByExamCheckId(String examCheckId) {
        return examCheckChildDao.findSomeByExamCheckId(examCheckId);
    }

    /**
     * 绩效自动考评查询使用
     * @param examCheckChild
     * @return
     */
    public List<ExamCheckChild> findExamCheckChildList(ExamCheckChild examCheckChild){
        return examCheckChildDao.findExamCheckList(examCheckChild);
    }
}