/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPolicePersonalTrainingFile;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;

import java.util.List;
import java.util.Map;

/**
 * 民警个人训历档案报表DAO接口
 * @author kevin.jia
 * @version 2020-09-28
 */
@MyBatisDao
public interface AffairPolicePersonalTrainingFileDao extends CrudDao<AffairPolicePersonalTrainingFile> {
    List<PersonnelBase> findPersonnelBaseList(PersonnelBase personnelBase);

    int selByIdNumber(String idNumber);

    void deleteByIdNumber(String idNumber);

    AffairPolicePersonalTrainingFile findPersonnaInfoByIdNumber(String idNumber);

    Map<String, String> findPersonnaMapByIdNumber(String idNumber);
}