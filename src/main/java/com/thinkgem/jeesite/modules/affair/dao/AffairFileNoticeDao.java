/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党建文件通知通报DAO接口
 * @author eav.liu
 * @version 2019-11-01
 */
@MyBatisDao
public interface AffairFileNoticeDao extends CrudDao<AffairFileNotice> {

    //List<AffairFileNotice> findListByAuth(@Param(value = "affairFileNotice") AffairFileNotice affairFileNotice, @Param("hasAuth") boolean hasAuth);

    int findSignNum(@Param(value = "affairFileNotice") AffairFileNotice affairFileNotice);

    int findSumNum(@Param(value = "affairFileNotice") AffairFileNotice affairFileNotice);

    List<AffairFileNotice> findByIds(@Param(value = "ids") List<String> ids);

    List<AffairFileNotice> indexNoticeList();
}