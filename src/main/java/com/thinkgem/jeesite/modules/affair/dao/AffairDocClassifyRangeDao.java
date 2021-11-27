/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairDocClassifyRange;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 使用范围设置DAO接口
 * @author kevin.jia
 * @version 2020-07-31
 */
@MyBatisDao
public interface AffairDocClassifyRangeDao extends CrudDao<AffairDocClassifyRange> {
    /*
    * 根据临时id获取使用范围设置列表
    * */
    List<AffairDocClassifyRange> getByTempId(String tempId);
    /*
    * 更新分类id字段
    * */
    void updateClassifyId(@Param(value = "classifyId")String classifyId, @Param(value = "classifyRanges")List<AffairDocClassifyRange> classifyRanges);

    void deleteByClassifyIds(@Param(value = "ids") List<String> ids);
}