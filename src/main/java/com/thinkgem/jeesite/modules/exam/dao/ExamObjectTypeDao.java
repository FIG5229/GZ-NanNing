/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.exam.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.exam.entity.ExamObjectType;

import java.util.List;

/**
 * 被考评对象类别关系表DAO接口
 * @author kevin.jia
 * @version 2021-02-22
 */
@MyBatisDao
public interface ExamObjectTypeDao extends CrudDao<ExamObjectType> {

    List<ExamObjectType> getAllNameId(ExamObjectType examObjectType);
}