/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 预警接收部门DAO接口
 * @author eav.liu
 * @version 2019-11-28
 */
@MyBatisDao
public interface WarnReceiveDao extends CrudDao<WarnReceive> {

    void deleteByWarnId(@Param(value = "warnId") String warnId);

    List<WarnReceive> findListByUserId(String userId);

    void updateNextRepeatTimeById(@Param(value = "id") String id, @Param(value = "nextRepeatTime") Date nextRepeatTime);

    void handelByIds(@Param(value = "list") List<String> str);

    void updateIsHandleById(@Param("id") String id, @Param("isHandle") String isHandle);

    void taskHandelById(String id);

    List<WarnReceive> findAll();

    List<WarnReceive> findUnHandel();

    //批量设置当天不再提醒点击时间字段
    void updateNoRemindTimeByIds(@Param(value = "ids") String[] ids, @Param(value = "nowDate") Date nowDate);
    //根据预警id及用户id更新不再提醒点击时间字段
    void updateNoRemindTimeById(@Param(value = "warnId") String warnId,@Param(value = "userId") String userId, @Param(value = "nowDate") Date nowDate);
    //根据预警id及用户id将是否处理状态调整为 1 ：已处理;并将下次提醒时间字段设置为null
    void handelByWarnId(@Param(value = "warnId") String warnId, @Param(value = "userId")String userId, @Param("handleTime") Date handleTime);
    //根据预警id及用户id查出接受人员表数据id
    String findIdByWUId(@Param(value = "warnId") String warnId, @Param(value = "userId")String userId);
    //根据userId将del_flag=0的相关预警信息查出
    List<WarnReceive> findAllByUserId(String userId);
}