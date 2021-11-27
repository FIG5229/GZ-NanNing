/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamStandardTemplateItemDefine;

import java.util.List;
import java.util.Map;

/**
 * 考评模板DAO接口
 * @author bradley.zhao
 * @version 2019-12-17
 */
@MyBatisDao
public interface ExamStandardTemplateItemDefineDao extends CrudDao<ExamStandardTemplateItemDefine> {

    List<Map<String, String>> findByTemplateId(String templateId);
}