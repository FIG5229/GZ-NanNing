/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassInformation;
import com.thinkgem.jeesite.modules.affair.entity.AffairClassManage;
import com.thinkgem.jeesite.modules.affair.entity.AffairPersonnelMessage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 培训班管理DAO接口
 * @author jack.xu
 * @version 2020-07-08
 */
@MyBatisDao
public interface AffairClassManageDao extends CrudDao<AffairClassManage> {

    List<AffairClassManage> findByIds(@Param(value = "ids") List<String> ids);

    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    List<AffairClassInformation> idList(@Param(value = "id") String id,@Param(value = "affairClassInformation") AffairClassInformation affairClassInformation);

    /**
     * 根据id人员信息
     * @param id
     * @return
     */
    List<AffairPersonnelMessage> idListRenYuan(@Param(value = "id") String id,@Param(value = "affairPersonnelMessage") AffairPersonnelMessage affairPersonnelMessage);

    /**
     * 课程信息单条数据删除
     * @param affairClassInformation
     */
    void deleteClassInfo(AffairClassInformation affairClassInformation);
}