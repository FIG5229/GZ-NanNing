/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairFileNoticeSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 党建文件通知通报签收DAO接口
 * @author eav.liu
 * @version 2019-11-01
 */
@MyBatisDao
public interface AffairFileNoticeSignDao extends CrudDao<AffairFileNoticeSign> {

    List<AffairFileNoticeSign> findSign(@Param(value = "affairFileNoticeSign") AffairFileNoticeSign affairFileNoticeSign);

    List<AffairFileNoticeSign> findNotSign(@Param(value = "affairFileNoticeSign")AffairFileNoticeSign affairFileNoticeSign);

    void deleteByNoticeId(String noticeId);
}