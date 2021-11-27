/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.warn.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.dao.WarnHistoryDao;
import com.thinkgem.jeesite.modules.warn.dao.WarnReceiveDao;
import com.thinkgem.jeesite.modules.warn.dao.WarningDao;
import com.thinkgem.jeesite.modules.warn.entity.WarnHistory;
import com.thinkgem.jeesite.modules.warn.entity.WarnReceive;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预警信息Service
 * @author eav.liu
 * @version 2019-11-28
 */
@Service
@Transactional(readOnly = true)
public class WarningService extends CrudService<WarningDao, Warning> {

	private static final Logger log = LoggerFactory.getLogger(WarningTask.class);

	@Autowired
	private WarnReceiveDao warnReceiveDao;

	@Autowired
	private WarnReceiveService warnReceiveService;

	@Autowired
	private WarnHistoryService warnHistoryService;

	@Autowired
	private WarnHistoryDao warnHistoryDao;

	public Warning get(String id) {
		return super.get(id);
	}

	public List<Warning> findList(Warning warning) {
		return super.findList(warning);
	}

	public Page<Warning> findPage(Page<Warning> page, Warning warning) {
		warning.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		String userId = UserUtils.getUser().getId();
		if("1".equals(userId)){
			userId = null;
		}
		warning.setUserId(userId);
		return super.findPage(page, warning);
	}

	@Transactional(readOnly = false)
	public void save(Warning warning) {
		super.save(warning);

		if(!"4".equals(warning.getRepeatCycle())){
			warning.setDate(null);
		}

		String receivePerIds = warning.getReceivePerId();
		String receivePerNames = warning.getReceivePerName();
		//先删除关联表原来的数据
		warnReceiveDao.deleteByWarnId(warning.getId());
		//关联表插入新的数据
		List<String> idList = Arrays.asList(receivePerIds.split(","));
		if (idList != null && idList.size() > 0) {
			List<String> nameList = Arrays.asList(receivePerNames.split(","));
			for (int i = 0; i < idList.size(); i++) {
				WarnReceive warnReceive = new WarnReceive();
				warnReceive.setPersonId(idList.get(i));
				warnReceive.setWarnId(warning.getId());
				//人员名称
				warnReceive.setPersonName(nameList.get(i));
				//每次添加或者修改时，处理状态置0，下次预警提示时间置空,不再提醒点击时间置空,处理时间置空
				warnReceive.setIsHandle("0");
				warnReceive.setNextRepeatTime(null);
				warnReceive.setNoRemindTime(null);
				warnReceive.setHandleTime(null);
				//设置创建人及更新人信息
				warnReceive.setCreateBy(warning.getCreateBy());
				warnReceive.setCreateOrgId(warning.getCreateOrgId());
				warnReceive.setUpdateBy(warning.getUpdateBy());
				warnReceive.setUpdateOrgId(warning.getUpdateOrgId());
				warnReceiveService.save(warnReceive);
			}
		}
	}
	@Transactional(readOnly = false)
	public void delete(Warning warning) {
		super.delete(warning);
		//删除关联表
		warnReceiveDao.deleteByWarnId(warning.getId());
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteByIds(List<String> ids) {
		//删除主表
		super.deleteByIds(ids);
		//删除关联表
		for (String id:ids) {
			warnReceiveDao.deleteByWarnId(id);
		}
	}

	/**
	 * 获得预警通知的数量和对应的id
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> findNumByUserId(String userId) {
		//log.info("预警提醒开始执行");
		Map<String, Object> map = new HashMap<>();
		try{
		map = newFindNumByUserId(userId);
		}catch (Exception e){
			e.printStackTrace();
			log.error("预警发生错误"+e.getMessage());
			map.put("num", 0);
		}
		return map;
	}

	// 新逻辑
	@Transactional(readOnly = false)
	public Map<String, Object> newFindNumByUserId(String userId) {
		log.info("新预警提醒开始执行");
		//根据用户id将del_flag = 0的相关预警信息查出
		List<WarnReceive> list = warnReceiveDao.findAllByUserId(userId);
		Map<String, Object> map = new HashMap<>();
		if(list != null && list.size() >0){
			StringBuilder idStr = new StringBuilder();
			Integer num = 0;
			// 由上面查出的list集合中，存在两种处理状态的warnReceive对象：处理与未处理
			for(WarnReceive wr : list){
				/*
				仅在wr.getIsHandle()=0条件下，对持续时间不为空的预警信息进行处理
				对持续时间不为空的处理方式分为  wr.getContinueDay() = 0  与 不等于0两种大致情况，注：仅对每周，每月，每年，永不重复周期进行处理
				为 0（收到后停止）：
				具体方式，开始预警后，都未点击已完成/收到按钮，
				每周：连续每天进行提醒直到下次预警开始，每月：提醒当月剩余天数，每年：提醒当年剩余天数，永不就是提醒至点击完成/收到为止
				具体代码，在天数判断处增加  || (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())
				不为 0：
				持续多少天处理， differYearDay  differMonthDay  differWeek 由这三个方法 分别对各个周期进行判断，
				当前时间的是否在预警开始时间要求的持续时间之内
				 */

				// 根据getIsHandle 是否为0/1 ：未处理/已处理 分别进行处理数据
				// 未处理过的预警信息
				if("0".equals(wr.getIsHandle())){//isHandle=0  未处理
					// 再通过下次重复提醒时间字段是否为空，判断为第一次提示，还是第多次提示
					if(wr.getNextRepeatTime()==null){
						//下次重复提醒时间字段为空，
						//根据重复周期分别处理数据
						if("0".equals(wr.getRepeatCycle())){
							//重复周期：每天
							Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//nowDate.setSeconds(0);
							String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
							String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
							Date handleDate = DateUtils.parseDate(handleStr);
							if(isLessThanFive(handleDate,nowDate)||handleDate.before(nowDate)){
								//数据库存储的开始时间在当前时间前后五分钟内 ，或者数据库存储的开始时间在当前时间更之前
								num++;  //记录符合规则的预警个数
								idStr.append(wr.getId() + ",");//存储符合规则的预警id
								Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
								updateNextRepeatTime(nextDate,nowDate,wr);//更新数据库中，下次提醒字段值
								//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
							}

						}
						else if("1".equals(wr.getRepeatCycle())){
							//重复周期：每周
							Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							Calendar c = Calendar.getInstance();
							c.setTime(nowDate);
							//周日：1 周一：2 周三：2 ......周六：7
							Integer weekDay = c.get(Calendar.DAY_OF_WEEK);   //获取当前周几
							if(weekDay.toString().equals(wr.getWeek())||differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay())){
								//同样是周几或者当前时间在数据库开始持续时间范围内或者持续时间字段为0 收到后停止
								String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
								String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
								Date handleDate = DateUtils.parseDate(handleStr);
								//nowDate.setSeconds(0);
								if (isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate)) {
									//如果数据库存储的开始时间在当前时间前后五分钟内
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
								}
							}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
								//超时提醒：每周
								Date nextStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"next");
								Date prevStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"prev");
								Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
								Date nextRepeatTime = wr.getNextRepeatTime();
								if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate))
								{
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate,nowDate,wr);
								}
							}
						}
						else if("2".equals(wr.getRepeatCycle())){
							//重复周期：每月
							String nowDateStr = DateUtils.getDate("yyyy-MM");//获取当前年月
							String handleStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();//与数据库存储的日，时，分拼装
							Date handleDate = DateUtils.parseDate(handleStr);//转换为日期格式
							Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//nowDate.setSeconds(0);

							if (nowDate.getDate() == Integer.valueOf(wr.getDay())
									|| differMonthDay(handleDate, nowDate, wr.getContinueDay())
									|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay()))) {
								//将日期调整为同一天
								handleDate.setDate(nowDate.getDate());
								if (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate)) {
									//如果数据库存储的开始时间在当前时间前后五分钟内
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate, nowDate, wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
								}
							}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
								//超时提醒：每月
								Date nextStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"next");
								Date prevStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"prev");
								Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
								if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate))
								{
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate,nowDate,wr);
								}
							}
						}
						else if("3".equals(wr.getRepeatCycle())){
							//重复周期：每年
							String handleStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
							Date handleDate = DateUtils.parseDate(handleStr);
							Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//nowDate.setSeconds(0);
							int nowMonth = nowDate.getMonth()+1;
							int sqlMonth = Integer.parseInt(wr.getMonth());
							int nowDay =  nowDate.getDate();
							int sqlDay = Integer.parseInt(wr.getDay());
							if(((nowMonth==sqlMonth && nowDay==sqlDay)
									||differYearDay(handleDate,nowDate,wr.getContinueDay())
									|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))
									&&(isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate))){
								num++;
								idStr.append(wr.getId() + ",");
								Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
								updateNextRepeatTime(nextDate,nowDate,wr);
								//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
							}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
								//超时提醒：每年
								int prevYear = Integer.parseInt(DateUtils.getYear())-1;
								String  prevStartDateStr = prevYear+ "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
								Date prevStartDate = DateUtils.parseDate(prevStartDateStr);
								Date latestPrevStartDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
								Date latestNowStartDate = selLatestPrevStartDate(handleDate,wr.getContinueDay());
								if((nowDate.before(handleDate)&&latestPrevStartDate.before(nowDate))
										|| latestNowStartDate.before(nowDate)){
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
									updateNextRepeatTime(nextDate,nowDate,wr);
								}
							}
						}
						else{
							//正常情况下：wr.getRepeatCycle()=4，重复周期：永不
							Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//nowDate.setSeconds(0);
							if(wr.getDate()!=null
									&& (((nowDate.getYear()==wr.getDate().getYear()) && (nowDate.getMonth()==wr.getDate().getMonth()) && (nowDate.getDate()==wr.getDate().getDate()))
									|| differYearDay(wr.getDate(),nowDate,wr.getContinueDay())
									|| (nowDate.after(wr.getDate()) && "0".equals(wr.getContinueDay())))
									&&(isLessThanFive(wr.getDate(),nowDate)||wr.getDate().before(nowDate))){
								num++;
								idStr.append(wr.getId() + ",");
								Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
								updateNextRepeatTime(nextDate,nowDate,wr);
								//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
							}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
								//超时提醒：永不
								Date startDate = wr.getDate();
								Date latestStartDate = selLatestPrevStartDate(startDate,wr.getContinueDay());
								if(latestStartDate.before(nowDate)){
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate,nowDate,wr);
								}
							}
						}

					}
					else{
						//下次重复提醒时间字段非空  NextRepeatTime!=null   is_handle = 0
						//不再提醒点击时间字段是否为空
						//未点击不再提醒，不再提醒点击时间字段为空no_remind_time ==null
						if(wr.getNoRemindTime()==null){
							if("0".equals(wr.getRepeatCycle())){
								//重复周期:每天
								Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								if (isLessThanFive(wr.getNextRepeatTime(),nowDate)) {
									//下次提醒时间与当前时间相差不超过5分钟
									num++;
									idStr.append(wr.getId() + ",");

									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								} else {
									//下次提醒时间与当前时间相差超过5分钟
									if (wr.getNextRepeatTime().before(nowDate)) {
										String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
										String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
										Date handleDate = DateUtils.parseDate(handleStr);
										if (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate)) {
											//如果数据库存储的开始时间在当前时间前后五分钟内
											num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
											updateNextRepeatTime(nextDate, nowDate, wr);//更新下次重复提醒时间
											//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
										}
									}
								}
							}
							else if ("1".equals(wr.getRepeatCycle())) {
								//重复周期：每周
								Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);

								Calendar c = Calendar.getInstance();
								c.setTime(nowDate);
								//周日：1 周一：2 周三：2 ......周六：7
								Integer weekDay = c.get(Calendar.DAY_OF_WEEK);
								if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && (weekDay.toString().equals(wr.getWeek()) || differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay()))) {
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								} else {
									if (wr.getNextRepeatTime().before(nowDate)) {

										if (weekDay.toString().equals(wr.getWeek()) || differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay()) ) {
											//如果是同样是周几的话
											String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
											String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
											Date handleDate = DateUtils.parseDate(handleStr);
											//nowDate.setSeconds(0);
											if (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate)) {
												//如果数据库存储的开始时间在当前时间前后五分钟内
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate, nowDate, wr);
												//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
											}
										}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
											//超时提醒：每周
											//该处参数-值主要用于超时提醒判断
											Date nextStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"next");
											Date prevStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"prev");
											Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
											Date nextRepeatTime = wr.getNextRepeatTime();
											if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate)
													&& nextRepeatTime.before(prevStartDate))
											{
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}
									}
								}
							}
							else if ("2".equals(wr.getRepeatCycle())) {
								//重复周期：每月

								Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								//获取当前开始提醒时间
								String nowDateStr = DateUtils.getDate("yyyy-MM");//获取当前年月
								String handleStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();//与数据库存储的日，时，分拼装
								Date handleDate = DateUtils.parseDate(handleStr);//转换为日期格式
								if (isLessThanFive(wr.getNextRepeatTime(),nowDate)&& (nowDate.getDate()==Integer.valueOf(wr.getDay())
										|| differMonthDay(handleDate,nowDate,wr.getContinueDay())
										|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))) {
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								} else {
									if (wr.getNextRepeatTime().before(nowDate)) {
										if(nowDate.getDate()==Integer.valueOf(wr.getDay())
												|| differMonthDay(handleDate,nowDate,wr.getContinueDay())
												|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay()))){
												//将日期调整为同一天
												handleDate.setDate(nowDate.getDate());
												if(isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate)){
												/*if ((nowDate.getDate() == Integer.parseInt(wr.getDay()) || differMonthDay(handleDate,nowDate,wr.getContinueDay())) && (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate))) {*/
												//如果数据库存储的开始时间在当前时间前后五分钟内
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate, nowDate, wr);
												//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
											}
										} else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
											//超时提醒：每月
											//该处参数-值主要用于超时提醒判断
											Date nextStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"next");
											Date prevStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"prev");
											Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
											Date nextRepeatTime = wr.getNextRepeatTime();
											if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate)&& nextRepeatTime.before(prevStartDate))
											{
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}
									}
								}

							}
							else if ("3".equals(wr.getRepeatCycle())) {
								//重复周期：每年
								Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								//获取当前 年度开始时间
								String handleStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
								Date handleDate = DateUtils.parseDate(handleStr);
								int nowMonth = nowDate.getMonth() + 1;
								int sqlMonth = Integer.parseInt(wr.getMonth());
								int nowDay = nowDate.getDate();
								int sqlDay = Integer.parseInt(wr.getDay());
								if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && ((nowMonth==sqlMonth && nowDay==sqlDay)
										||differYearDay(handleDate,nowDate,wr.getContinueDay())
										|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))){
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								}
								else {
									if (wr.getNextRepeatTime().before(nowDate)) {

										if(((nowMonth==sqlMonth && nowDay==sqlDay)
												||differYearDay(handleDate,nowDate,wr.getContinueDay())
												|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))
												&&(isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate))){
											/*if (((nowMonth == sqlMonth && nowDay == sqlDay) || differYearDay(handleDate,nowDate,wr.getContinueDay())) && (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate))) {
											 */	num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
											updateNextRepeatTime(nextDate, nowDate, wr);
										}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
											//超时提醒：每年
											//该处参数-值主要用于超时提醒判断
											int prevYear = Integer.parseInt(DateUtils.getYear())-1;
											String  prevStartDateStr = prevYear+ "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
											Date prevStartDate = DateUtils.parseDate(prevStartDateStr);
											Date latestPrevStartDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
											Date latestNowStartDate = selLatestPrevStartDate(handleDate,wr.getContinueDay());
											Date nextRepeatTime = wr.getNextRepeatTime();
											if((nowDate.before(handleDate)&&latestPrevStartDate.before(nowDate) && nextRepeatTime.before(prevStartDate))
													|| (latestNowStartDate.before(nowDate) && nextRepeatTime.before(handleDate))) {
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}
									}
								}
							}
							else{
								//正常情况下：wr.getRepeatCycle()=4，重复周期：永不
								Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && (((nowDate.getYear()==wr.getDate().getYear()) && (nowDate.getMonth()==wr.getDate().getMonth()) && (nowDate.getDate()==wr.getDate().getDate()))
										|| differYearDay(wr.getDate(),nowDate,wr.getContinueDay())
										|| (nowDate.after(wr.getDate()) && "0".equals(wr.getContinueDay())))) {
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
									updateNextRepeatTime(nextDate,nowDate,wr);
									//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								}else {
									if (wr.getNextRepeatTime().before(nowDate)) {
										if(wr.getDate()!=null
												&& (((nowDate.getYear()==wr.getDate().getYear()) && (nowDate.getMonth()==wr.getDate().getMonth()) && (nowDate.getDate()==wr.getDate().getDate()))
												|| differYearDay(wr.getDate(),nowDate,wr.getContinueDay())
												|| (nowDate.after(wr.getDate()) && "0".equals(wr.getContinueDay())))
												&&(isLessThanFive(wr.getDate(),nowDate)||wr.getDate().before(nowDate))){
											num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
											updateNextRepeatTime(nextDate, nowDate, wr);
										}
									}
								}
							}
						}
						else {
							//不再提醒点击时间字段不为空
							//判断不再提醒时间，与当前时间是否为同一天
							if (compareDay(wr.getNoRemindTime(), new Date()) != 0) {
								//不是同一天
								if("0".equals(wr.getRepeatCycle())){
									//重复周期:每天
									long time = wr.getNextRepeatTime().getTime();
									Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);
									if (isLessThanFive(wr.getNextRepeatTime(),nowDate)) {
										//下次提醒时间与当前时间相差不超过5分钟
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
										updateNextRepeatTime(nextDate,nowDate,wr);
									}
									else {
										if (wr.getNextRepeatTime().before(nowDate)) {
											//下次提醒时间与当前时间相差超过5分钟
											String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
											String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
											Date handleDate = DateUtils.parseDate(handleStr);
											if (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate)) {
												//如果数据库存储的开始时间在当前时间前后五分钟内
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate, nowDate, wr);//更新下次重复提醒时间
												//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
											}
										}
									}
								}
								else if ("1".equals(wr.getRepeatCycle())) {
									//重复周期：每周
									Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);
									Calendar c = Calendar.getInstance();
									c.setTime(nowDate);
									//周日：1 周一：2 周三：2 ......周六：7
									Integer weekDay = c.get(Calendar.DAY_OF_WEEK);
									if (isLessThanFive(wr.getNextRepeatTime(),nowDate) &&(weekDay.toString().equals(wr.getWeek()) || differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay()))) {
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
										updateNextRepeatTime(nextDate,nowDate,wr);
										//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
									}
									else {
										if (wr.getNextRepeatTime().before(nowDate)) {

											if (weekDay.toString().equals(wr.getWeek()) || differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay())) {
												//如果是同样是周几的话
												String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
												String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
												Date handleDate = DateUtils.parseDate(handleStr);
												//nowDate.setSeconds(0);
												if (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate)) {
													//如果数据库存储的开始时间在当前时间前后五分钟内
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
													updateNextRepeatTime(nextDate, nowDate, wr);
													//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
												}
											}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
												//超时提醒：每周
												//该处参数-值主要用于超时提醒判断
												Date nextStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"next");
												Date prevStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"prev");
												Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
												Date nextRepeatTime = wr.getNextRepeatTime();
												if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate)
														&& nextRepeatTime.before(prevStartDate))
												{
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
													updateNextRepeatTime(nextDate,nowDate,wr);
												}
											}
										}
									}
								}
								else if ("2".equals(wr.getRepeatCycle())) {
									//重复周期：每月
									Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);

									String nowDateStr = DateUtils.getDate("yyyy-MM");//获取当前年月
									String handleStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();//与数据库存储的日，时，分拼装
									Date handleDate = DateUtils.parseDate(handleStr);//转换为日期格式
									if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && (nowDate.getDate()==Integer.valueOf(wr.getDay())
											|| differMonthDay(handleDate,nowDate,wr.getContinueDay())
											|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))) {
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
										updateNextRepeatTime(nextDate,nowDate,wr);
										//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
									}
									else {
										if (wr.getNextRepeatTime().before(nowDate)) {

											if(nowDate.getDate()==Integer.valueOf(wr.getDay())
													|| differMonthDay(handleDate,nowDate,wr.getContinueDay())
													|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay()))){
													//将日期调整为同一天
													handleDate.setDate(nowDate.getDate());
													if (isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate)) {
													//如果数据库存储的开始时间在当前时间前后五分钟内
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
													updateNextRepeatTime(nextDate, nowDate, wr);
													//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
												}
											} else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
												//超时提醒：每月
												//该处参数-值主要用于超时提醒判断
												Date nextStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"next");
												Date prevStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"prev");
												Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
												Date nextRepeatTime = wr.getNextRepeatTime();
												if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate)&& nextRepeatTime.before(prevStartDate))
												{
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
													updateNextRepeatTime(nextDate,nowDate,wr);
												}
											}
										}
									}

								}
								else if ("3".equals(wr.getRepeatCycle())) {
									//重复周期：每年
									long time = wr.getNextRepeatTime().getTime();
									Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);

									//获取当前年开始时间
									String handleStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
									Date handleDate = DateUtils.parseDate(handleStr);
									int nowMonth = nowDate.getMonth() + 1;
									int sqlMonth = Integer.parseInt(wr.getMonth());
									int nowDay = nowDate.getDate();
									int sqlDay = Integer.parseInt(wr.getDay());

									if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && ((nowMonth==sqlMonth && nowDay==sqlDay)
											||differYearDay(handleDate,nowDate,wr.getContinueDay())
											|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))){
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
										updateNextRepeatTime(nextDate,nowDate,wr);
										//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
									}
									else {
										if (wr.getNextRepeatTime().before(nowDate)) {
											if(((nowMonth==sqlMonth && nowDay==sqlDay)
													||differYearDay(handleDate,nowDate,wr.getContinueDay())
													|| (nowDate.after(handleDate) && "0".equals(wr.getContinueDay())))
													&&(isLessThanFive(handleDate, nowDate)||handleDate.before(nowDate))){
												/*if (((nowMonth == sqlMonth && nowDay == sqlDay) || differYearDay(handleDate,nowDate,wr.getContinueDay())) && (isLessThanFive(handleDate, nowDate) || handleDate.before(nowDate))) {
												 */	num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
												updateNextRepeatTime(nextDate, nowDate, wr);
											}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
												//超时提醒：每年
												//该处参数-值主要用于超时提醒判断
												int prevYear = Integer.parseInt(DateUtils.getYear())-1;
												String  prevStartDateStr = prevYear+ "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
												Date prevStartDate = DateUtils.parseDate(prevStartDateStr);
												Date latestPrevStartDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
												Date latestNowStartDate = selLatestPrevStartDate(handleDate,wr.getContinueDay());
												Date nextRepeatTime = wr.getNextRepeatTime();
												if((nowDate.before(handleDate)&&latestPrevStartDate.before(nowDate) && nextRepeatTime.before(prevStartDate))
														|| (latestNowStartDate.before(nowDate) && nextRepeatTime.before(handleDate))) {
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
													updateNextRepeatTime(nextDate,nowDate,wr);
												}
											}
										}
									}
								}
								else{
									//正常情况下：wr.getRepeatCycle()=4，重复周期：永不
									long time = wr.getDate().getTime();
									Date nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);
									if (isLessThanFive(wr.getNextRepeatTime(),nowDate) && wr.getDate() != null && (((nowDate.getYear()==wr.getDate().getYear()) && (nowDate.getMonth()==wr.getDate().getMonth()) && (nowDate.getDate()==wr.getDate().getDate()))
											|| differYearDay(wr.getDate(),nowDate,wr.getContinueDay())
											|| (nowDate.after(wr.getDate()) && "0".equals(wr.getContinueDay())))) {
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
										updateNextRepeatTime(nextDate,nowDate,wr);
										//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
									}else {
										if (wr.getNextRepeatTime().before(nowDate)) {
											if (wr.getDate() != null
													&& (((nowDate.getYear() == wr.getDate().getYear()) && (nowDate.getMonth() == wr.getDate().getMonth()) && (nowDate.getDate() == wr.getDate().getDate()))
													|| differYearDay(wr.getDate(), nowDate, wr.getContinueDay())
													|| (nowDate.after(wr.getDate()) && "0".equals(wr.getContinueDay())))
													&& (isLessThanFive(wr.getDate(), nowDate) || wr.getDate().before(nowDate))) {
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate, nowDate, wr);
												//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);//更新下次重复提醒时间
											}
										}
									}
								}
							}
						}
					}
				}
				else{
					//is_Handle = 1,已处理
					//根据nextRepeatTime下次重复提醒时间字段是否为空，分别处理
					//nextRepeatTime下次重复提醒时间字段为空
					//收到/完成后，并不按照持续时间提醒
					if(wr.getNextRepeatTime()==null){//该情况为上次重复周期期间已处理过
						//将处理状态设置为0，未处理
						//warnReceiveDao.updateIsHandleById(wr.getId(),"0");
						Date handleTime = wr.getHandleTime();
						Date nowDate = new Date();
						if(handleTime == null || compareDay(handleTime,nowDate)!=0) {
							nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							if ("0".equals(wr.getRepeatCycle())) {
								//重复周期:每天
								//nowDate.setSeconds(0);
								String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
								String startStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
								Date startDate = DateUtils.parseDate(startStr);
								if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
									//如果数据库存储的开始时间在当前时间前后五分钟内,或者开始时间再当前时间之前
									warnReceiveDao.updateIsHandleById(wr.getId(), "0");
									num++;
									idStr.append(wr.getId() + ",");
									Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
									updateNextRepeatTime(nextDate, nowDate, wr);
								}

							}
							else if ("1".equals(wr.getRepeatCycle())) {
								//重复周期：每周
								Calendar c = Calendar.getInstance();
								c.setTime(nowDate);
								//周日：1 周一：2 周三：2 ......周六：7
								Integer weekDay = c.get(Calendar.DAY_OF_WEEK);
								if(weekDay.toString().equals(wr.getWeek())){
									//如果是同样是周几的话
									String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
									String startStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
									Date startDate = DateUtils.parseDate(startStr);
									nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
									//nowDate.setSeconds(0);
									if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
										//如果数据库存储的开始时间在当前时间前后五分钟内，或者开始时间比当前时间早
										warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
										updateNextRepeatTime(nextDate,nowDate,wr);
									}
								}else{
									Date handleDate = wr.getHandleTime();
									if(handleDate==null){
										//处理时间为空
										Date nowNextStartDate = selPOrNDateWeek(nowDate, wr.getWeek(), "next");
										if(differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || ("0".equals(wr.getContinueDay())&&nowDate.before(nowNextStartDate))){
											//当前时间在 开始时间及持续时间之内 持续时间不为0及null情况
											// 若持续时间 = 0 ，收到停止状态，当前时间小于下次开始时间
											String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
											String startStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
											Date startDate = DateUtils.parseDate(startStr);
											nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
											//nowDate.setSeconds(0);
											if (isLessThanFive(startDate, nowDate)||startDate.before(nowDate)) {
												//如果数据库存储的开始时间在当前时间前后五分钟内，或者开始时间比当前时间早
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}
									}else{
										Calendar handleC = Calendar.getInstance();
										handleC.setTime(handleDate);
										Date nextStartDate = selPOrNDateWeek(handleDate,wr.getWeek(),"next"); //下次开始时间
										if(handleDate.equals(nextStartDate)){
											handleC.add(Calendar.DATE, Integer.parseInt(wr.getContinueDay()));
											nextStartDate = handleC.getTime();
										}
										if(nowDate.after(nextStartDate)&& (differWeek(wr.getWeek(),weekDay,wr.getContinueDay()) || "0".equals(wr.getContinueDay()))){
											//当前时间大于处理时间，且当前周大于数据库存储的周几
											String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
											String startStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
											Date startDate = DateUtils.parseDate(startStr);
											nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
											//nowDate.setSeconds(0);
											if (isLessThanFive(startDate, nowDate)||startDate.before(nowDate)) {
												//如果数据库存储的开始时间在当前时间前后五分钟内，或者开始时间比当前时间早
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}else if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
											//超时提醒：每周
											Date prevStartDate = selPOrNDateWeek(nowDate,wr.getWeek(),"prev");
											Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
											if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate)
													&& (handleDate==null || compareDay(prevStartDate,handleDate)>0))
											{
												//wr.getHandleTime().before(prevStartDate)
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate,nowDate,wr);
											}
										}
									}
								}
							}
							else if ("2".equals(wr.getRepeatCycle())) {
								//重复周期：每月
								String nowDateStr = DateUtils.getDate("yyyy-MM");//获取当前年月
								String startStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();//与数据库存储的日，时，分拼装
								Date startDate = DateUtils.parseDate(startStr);//转换为日期格式
								nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								if (nowDate.getDate() == Integer.parseInt(wr.getDay())) {
									//当前时间的 日 等于数据库存储的 日
									if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
										//当前日与数据库存储的相等，且数据库存储的开始时间在当前时间前后五分钟内或者开始时间在当前时间之前
										warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
										updateNextRepeatTime(nextDate, nowDate, wr);
									}
								}else {
									// nowDate与数据库存储的 日不一样
									//1.判断是否为同一年
									Date handleDate = wr.getHandleTime();
									if(wr.getContinueDay()!=null && !"0".equals(wr.getContinueDay())){
										//超时提醒：每月
										Date nextStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"next");
										Date prevStartDate = selPOrNDateMonth(nowDate,wr.getDay(),"prev");
										Date latestPrevDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
										Date nextRepeatTime = wr.getNextRepeatTime();
										if(nowDate.before(nextStartDate)&&latestPrevDate.before(nowDate) && (handleDate==null || compareDay(prevStartDate,handleDate)>0)) {
											warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
											num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
											updateNextRepeatTime(nextDate,nowDate,wr);
											continue;
										}
									}
									if (handleDate == null) {
										if (differMonthDay(startDate, nowDate, wr.getContinueDay()) || ("0".equals(wr.getContinueDay()) && nowDate.after(startDate))) {
											warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
											num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
											updateNextRepeatTime(nextDate, nowDate, wr);
										}
									} else {
										int nowYear = nowDate.getYear();
										int handleYear = handleDate.getYear();
										if (handleYear == nowYear) {
											//同一年
											int handleMonth = handleDate.getMonth();
											int nowMonth = nowDate.getMonth();
											if(handleMonth == nowMonth){
												if(handleDate.getDate()<startDate.getDate()){
													if (differMonthDay(startDate, nowDate, wr.getContinueDay()) || ("0".equals(wr.getContinueDay()) && nowDate.after(startDate))) {
														warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
														num++;
														idStr.append(wr.getId() + ",");
														Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
														updateNextRepeatTime(nextDate, nowDate, wr);
													}
												}
											}
											else if(handleMonth<nowMonth){
												if (differMonthDay(startDate, nowDate, wr.getContinueDay()) || ("0".equals(wr.getContinueDay()) && nowDate.after(startDate))) {
													warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
													num++;
													idStr.append(wr.getId() + ",");
													Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
													updateNextRepeatTime(nextDate, nowDate, wr);
												}
											}
										}
										else if (handleYear < nowYear) {
											if (differMonthDay(startDate, nowDate, wr.getContinueDay()) || ("0".equals(wr.getContinueDay()) && nowDate.after(startDate))) {
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间值
												updateNextRepeatTime(nextDate, nowDate, wr);
											}
										}
									}
								}
							}
							else if ("3".equals(wr.getRepeatCycle())) {
								//重复周期：每年
								String startStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
								Date startDate = DateUtils.parseDate(startStr);
								nowDate = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
								//nowDate.setSeconds(0);
								int nowMonth = nowDate.getMonth()+1;
								int sqlMonth = Integer.parseInt(wr.getMonth());
								int nowDay =  nowDate.getDate();
								int sqlDay = Integer.parseInt(wr.getDay());
								if(nowMonth==sqlMonth && nowDay==sqlDay) {
									//该情况为nowDate的月日 正好与数据库存储月日相等
									if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
										//当前月、日与数据库存储的相等，且数据库存储的开始时间在当前时间前后五分钟内或者开始时间在当前时间之前
										warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
										num++;
										idStr.append(wr.getId() + ",");
										Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
										updateNextRepeatTime(nextDate, nowDate, wr);
									}
								}else{
									// nowDate与数据库存储的 月日不一样
									// 需要判断nowDate是否符合持续时间内要求的时间，还需要判断当前的处理时间是否为本次周期
									// 持续时间是否为0
									if("0".equals(wr.getContinueDay())){
										//持续时间：0 收到后停止
										//获取上一年开始时间
										Calendar lastC = Calendar.getInstance();
										lastC.setTime(startDate);
										lastC.add(Calendar.YEAR,-1);
										Date lastYearStartDate = lastC.getTime();
										if(wr.getHandleTime() == null || (wr.getHandleTime().before(startDate)&&nowDate.after(startDate)) || (wr.getHandleTime().before(lastYearStartDate)&&nowDate.before(startDate)) ){
											//处理时间字段为空  或者 处理事件不为空,处理时间在当年开始时间之前，且当前时间在开始时间之后  或者  处理时间比上一年开始时间还小，且当前时间在今年开始时间前
											if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
												//当前月、日与数据库存储的相等，且数据库存储的开始时间在当前时间前后五分钟内或者开始时间在当前时间之前
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
												updateNextRepeatTime(nextDate, nowDate, wr);
											}
										}
									}
									else{
										//持续时间不为 0
										//此处参数主要用于判断当前时间是否已超过正常提醒时间的参数-值
										int prevYear = Integer.parseInt(DateUtils.getYear())-1;
										String  prevStartDateStr = prevYear+ "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
										Date prevStartDate = DateUtils.parseDate(prevStartDateStr);
										Date latestPrevStartDate = selLatestPrevStartDate(prevStartDate,wr.getContinueDay());
										Date latestNowStartDate = selLatestPrevStartDate(startDate,wr.getContinueDay());

										if(differYearDay(startDate,nowDate,wr.getContinueDay()) && (wr.getHandleTime() == null || !differYearDay(startDate,wr.getHandleTime(),wr.getContinueDay()))){
											//当前月、日与数据库存储的不相等，判断当前时间在不在当前时间＋持续时间范围内 且  处理时间为空  或者  处理时间不在当前持续周期内
											if (isLessThanFive(startDate, nowDate) || startDate.before(nowDate)) {
												//且数据库存储的开始时间在当前时间前后五分钟内或者开始时间在当前时间之前
												warnReceiveDao.updateIsHandleById(wr.getId(), "0");//如果符合重复周期约束的时间，将处理状态调整为0
												num++;
												idStr.append(wr.getId() + ",");
												Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
												updateNextRepeatTime(nextDate, nowDate, wr);
											}
										}else if((nowDate.before(startDate)&&latestPrevStartDate.before(nowDate) && (wr.getHandleTime()==null || compareDay(prevStartDate,wr.getHandleTime())>0))
												|| (latestNowStartDate.before(nowDate) && (wr.getHandleTime()==null || compareDay(startDate,wr.getHandleTime())>0))) {
											warnReceiveDao.updateIsHandleById(wr.getId(), "0"); //如果符合重复周期约束的时间，将处理状态调整为0
											num++;
											idStr.append(wr.getId() + ",");
											Date nextDate = new Date(nowDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);//根据数据库存储的重复提醒时间，设置下次提醒时间字段
											updateNextRepeatTime(nextDate,nowDate,wr);
										}
									}
								}
							}
							/*else {
							//正常情况下：wr.getRepeatCycle()=4，重复周期：永不
							//重复周期为永不，因此不参与 is_handle=1且nextRepeatTime=nul 该情况下此处理
							}*/
						}
					}else{
						//nextRepeatTime下次重复提醒时间字段不为空
						//正常情况下，不存在is_handle=1且nextRepeatTime！=nul  该情况
						log.warn("正常情况下，不存在is_handle=1且nextRepeatTime！=nul,出现该信息说明预警模块发生错误，发出错误的对象：",wr);
					}

				}
			}
			map.put("num",num);
			map.put("idStr", idStr);
			return map;
		}
		map.put("num", 0);
		return map;
	}

	// 旧逻辑
	@Transactional(readOnly = false)
	public Map<String, Object> oldFindNumByUserId(String userId) {
		log.info("旧预警提醒开始执行");
		List<WarnReceive> list = warnReceiveDao.findListByUserId(userId);
		Map<String, Object> map = new HashMap<>();
		if(list != null && list.size() >0){
			StringBuilder idStr = new StringBuilder();
			Integer num = 0;
			for (WarnReceive wr : list) {
				//超时提醒的预警、因轮询间隔误差导致的没提醒的预警、挂机等问题导致没提醒的预警：
				String dateStr = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm");
				Date parseDate = DateUtils.parseDate(dateStr);
				if(wr.getNextRepeatTime() != null && wr.getNextRepeatTime().before(parseDate) && "0".equals(wr.getIsHandle())){
					idStr.append(wr.getId() + ",");
					num ++;
				}
				//重复周期1:每周 2:每月 3:每年 4:永不
				/**
				 * 预警创建时下次提醒时间（nextRepeatTime）字段为null，如果下次提醒时间为空，则为“第一次”提示(点击预警处理该字段又变为null)
				 * 不为空则是一直没处理的第N次提示，则用nextRepeatTime字段判断时间范围
				 * 目前需求是每隔五分钟查询一次，计算时间会有误差，结果不准确
				 */
				if (wr.getNextRepeatTime() == null) {
					if ("1".equals(wr.getRepeatCycle())) {
						Date today = new Date();
						Calendar c = Calendar.getInstance();
						c.setTime(today);
						//周日：1 周一：2 周三：2 ......周六：7
						Integer weekday = c.get(Calendar.DAY_OF_WEEK);
						if (weekday.toString().equals(wr.getWeek())) {
							//处理提醒时间
							String nowDateStr = DateUtils.getDate("yyyy-MM-dd");
							String handleStr = nowDateStr + " " + wr.getHour() + ":" + wr.getMinute();
							Date handleDate = DateUtils.parseDate(handleStr);
							Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//date.setSeconds(0);
							if (date.toString().equals(handleDate.toString())) {
								num++;
								idStr.append(wr.getId() + ",");
								Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
								warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								//warnReceiveDao.updateIsHandleById(wr.getId());
							}
						}
					} else if ("2".equals(wr.getRepeatCycle())) {
						if (Integer.valueOf(wr.getDay()) <= 28) {//29 30 31号无效
							//处理提醒时间
							String nowDateStr = DateUtils.getDate("yyyy-MM");
							String handleStr = nowDateStr + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
							Date handleDate = DateUtils.parseDate(handleStr);
							Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//date.setSeconds(0);
							if (date.toString().equals(handleDate.toString())) {
								num++;
								idStr.append(wr.getId() + ",");
								Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
								warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
								//warnReceiveDao.updateIsHandleById(wr.getId());
							}
						}
					} else if ("3".equals(wr.getRepeatCycle())) {
						String handleStr = DateUtils.getYear() + "-" + wr.getMonth() + "-" + wr.getDay() + " " + wr.getHour() + ":" + wr.getMinute();
						Date handleDate = DateUtils.parseDate(handleStr);
						Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
						//date.setSeconds(0);
						if (date.toString().equals(handleDate.toString())) {
							num++;
							idStr.append(wr.getId() + ",");
							Date nextDate = new Date(handleDate.getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
							warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
							//warnReceiveDao.updateIsHandleById(wr.getId());
						}
					}else{
						Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
						//date.setSeconds(0);
						if (date.toString().equals(wr.getDate().toString())) {
							num++;
							idStr.append(wr.getId() + ",");
							Date nextDate = new Date(wr.getDate().getTime() + Long.parseLong(wr.getRemind()) * 1000 * 60);
							warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
							//warnReceiveDao.updateIsHandleById(wr.getId());
						}
					}
				}
				else{
					if ("1".equals(wr.getRepeatCycle())) {
						long time = wr.getNextRepeatTime().getTime();
						Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
						//date.setSeconds(0);
						if (date.toString().equals(wr.getNextRepeatTime().toString())) {
							num++;
							idStr.append(wr.getId() + ",");
							Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
							//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
						}
					} else if ("2".equals(wr.getRepeatCycle())) {
						if (Integer.valueOf(wr.getDay()) <= 28) {
							long time = wr.getNextRepeatTime().getTime();
							Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
							//date.setSeconds(0);
							if (date.toString().equals(wr.getNextRepeatTime().toString())) {
								num++;
								idStr.append(wr.getId() + ",");
								Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
								//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
							}
						}
					} else if ("3".equals(wr.getRepeatCycle())) {
						long time = wr.getNextRepeatTime().getTime();
						Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
						//date.setSeconds(0);
						if (date.toString().equals(wr.getNextRepeatTime().toString())) {
							num++;
							idStr.append(wr.getId() + ",");
							Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
							//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
						}
					}else{
						long time = wr.getDate().getTime();
						Date date = TimeUtils.setSecondMILLISECOND(new Date());//获取当前时间并将秒及毫秒设置为0
						//date.setSeconds(0);
						if (date.toString().equals(wr.getDate().toString())) {
							num++;
							idStr.append(wr.getId() + ",");
							Date nextDate = new Date(time + Long.parseLong(wr.getRemind()) * 1000 * 60);
							//warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
						}
					}
				}
			}
			map.put("num",num);
			map.put("idStr", idStr);
			return map;
		}
		map.put("num", 0);
		return map;
	}


	/**
	 * 处理预警通知
	 * 展示该用户的预警信息列表
	 * @param idStr
	 * @param page
	 * @param warning
	 * @return
	 */
	@Transactional(readOnly = false)
	public Page<Warning> showWarnInfoByIds(String idStr, Page<Warning> page, Warning warning){
		if(idStr != null && !idStr.isEmpty()){
			String substring = idStr.substring(0, idStr.length() - 1);
			String[] split = substring.split(",");
			List<String> str = Arrays.asList(split);
			//warnReceiveDao.handelByIds(str);//处理
			warning.setIdStrList(str);
			return super.findPage(page, warning);
		}
		return null;
	}



	/**
	 * 处理预警 已完成/收到   或者  今日不再提醒
	 * type = "isHandel" 将预警信息处理状态 改为已处理
	 * 并将下次重复提醒时间字段设置为null
	 * type = "noRemind" 更新不再提醒点击时间字段
	 * 通过预警id  ： warn_id 及 userId
	 */
	@Transactional(readOnly = false)
	public String handelByWarnId(String warnId, String userId,String idStr,String type) {
		if(warnId!=null && !warnId.isEmpty()){
			String id = warnReceiveDao.findIdByWUId(warnId,userId);
			if("isHandel".equals(type)){
				//每次预警处理都要把next_repeat_time字段置null，为了下次的预警提示方便判断
				warnReceiveDao.handelByWarnId(warnId,userId,new Date());
				idStr = idStr.replaceAll(id+",","");
			}
			if("noRemind".equals(type)){
				Date nowDate = new Date();
				warnReceiveDao.updateNoRemindTimeById(warnId,userId,nowDate);
				idStr = idStr.replaceAll(id+",","");
			}
		}else{
			throw new NullPointerException("warnId 为 null");
		}
		return idStr;
	}

	//更新下次重复提醒时间
	private void updateNextRepeatTime(Date nextDate,Date nowDate,WarnReceive wr){
		Calendar nextC = Calendar.getInstance();
		Calendar nowC = Calendar.getInstance();
		nextC.setTime(nextDate);
		nowC.setTime(nowDate);
		Integer diff = nextC.get(nextC.DAY_OF_MONTH)-nowC.get(nowC.DAY_OF_MONTH);
		if(diff>0){
			warnReceiveDao.updateNextRepeatTimeById(wr.getId(), null);
		}else{
			warnReceiveDao.updateNextRepeatTimeById(wr.getId(), nextDate);
		}
	}

	//判断startDate与nowDate时间之差是否不超过+-五分钟 5*60*1000
	public static boolean isLessThanFive(Date startDate,Date nowDate){
		long diff = 0;
		long standard = 5*60*1000;
		long startTime = startDate.getTime();
		long nowTime = nowDate.getTime();
		diff = startTime-nowTime;
		if(diff>=0 && diff<standard){
			//前者时间大于后者，但与后者时间差不超过五分钟
			return true;
		}else if(diff<0 && diff>(-standard)){
			//前者时间小于后者，但与后者时间差不超过五分钟
			return true;
		}else {
			return false;
		}
	}

	/**
	* 判断两个日期是否再同一天
	 * @param nowDate
	 * @param sqlDate
	 * @return long
	 * */
	public static long compareDay(Date sqlDate,Date nowDate){
		//将sqlDate时分秒设置为00:00:00
		String sqlDateStr  = DateUtils.formatDate(sqlDate,"yyyy-MM-dd");
		//将nowDate时分秒设置为00:00:00
		String nowDateStr  = DateUtils.formatDate(nowDate,"yyyy-MM-dd");
		long sqlDateTime = DateUtils.parseDate(sqlDateStr).getTime();
		long nowDateTime = DateUtils.parseDate(nowDateStr).getTime();
		return (sqlDateTime-nowDateTime);
	}

	/**
	 * 比较相差多少天 周期为每年
	 * @param sqlDate 预警信息开始时间
	 * @param nowDate 当前系统时间
	 * @param continueDayStr 数据库存储持续时间
	 * @return
	 * */
	public static boolean differYearDay(Date sqlDate , Date nowDate, String continueDayStr){
		int day = 0;
		if(continueDayStr==null||continueDayStr.isEmpty()){
			return false;
		}
		//重复周期为每年：数据库存储为 xx月xx日 xx:xx
		//重复周期为永不：数据库存储为 xxxx年xx月xx日  xx:xx
		int continueDay = Integer.parseInt(continueDayStr)-1;//包含当天
		Calendar sqlC = Calendar.getInstance();
		Calendar nowC = Calendar.getInstance();
		sqlC.setTime(sqlDate);
		nowC.setTime(nowDate);
		/*if(nowDate.getYear() == sqlDate.getYear()){*/
		if(nowC.get(Calendar.YEAR) == sqlC.get(Calendar.YEAR)){
			//同一年
			day = nowC.get(Calendar.DAY_OF_YEAR)-sqlC.get(Calendar.DAY_OF_YEAR);
		}else{
			//非同一年
			long nowTime = nowDate.getTime();
			long sqlTime = sqlDate.getTime();
			day = (int) ((nowTime-sqlTime)/(1000*60*60*24));
		}
		return (0 <= day && day <= continueDay);
	}

	/**
	 * 比较相差多少天 周期为每月
	 * @param sqlDate 预警信息开始时间
	 * @param nowDate 当前系统时间
	 * @param continueDayStr 数据库存储持续时间
	 * @return
	 * */
	public static boolean differMonthDay(Date sqlDate , Date nowDate, String continueDayStr){
		int day = 0;
		if(continueDayStr==null||continueDayStr.isEmpty()||Integer.parseInt(continueDayStr)==0){
			return false;
		}
		//重复周期为每月：数据库存储为 xx日 xx:xx
		int continueDay = Integer.parseInt(continueDayStr);//持续时间，包含当天
		Calendar sqlC = Calendar.getInstance();
		Calendar nowC = Calendar.getInstance();
		sqlC.setTime(sqlDate);
		nowC.setTime(nowDate);
		day = nowC.get(Calendar.DAY_OF_YEAR)-sqlC.get(Calendar.DAY_OF_YEAR);//两者相差天数
		if(day>0&&day<continueDay){
			return true;
		}else if(day<0){
			Calendar a =Calendar.getInstance();
			a.setTime(sqlDate);
			a.set(Calendar.DATE,1);
			a.add(Calendar.DATE,-1);//减1天，变为上月最后一天
			int maxDate =a.get(Calendar.DATE);//获取上月最后一天
			if((maxDate-sqlC.get(Calendar.DAY_OF_MONTH)+1)<continueDay){
				if(nowC.get(Calendar.DAY_OF_MONTH)<=continueDay-(maxDate-sqlC.get(Calendar.DAY_OF_MONTH)+1)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}

	}

	/**
	 * 比较相差多少天 周期为每周
	 * @param sqlWeekDayStr 预警信息开始时间
	 * @param nowWeekDay 当前系统时间
	 * @param continueDayStr 数据库存储持续时间
	 * @return
	 * */
	public static boolean differWeek(String sqlWeekDayStr, int nowWeekDay, String continueDayStr) {
		if (continueDayStr == null || continueDayStr.isEmpty()||Integer.parseInt(continueDayStr)==0) {
			return false;
		}
		int sqlWeekDay = Integer.parseInt(sqlWeekDayStr);
		//当循环周期为每周时，sqlDate为当前年月日+数据库存储时分组装而成无需考虑是否同年、同周
		//周日 1 周一 2 ...周六 7
		// 例子 开始时间  周六 7    当前时间 周四 5
		int continueDay = Integer.parseInt(continueDayStr);// 6
		int diffWeekDay = nowWeekDay - sqlWeekDay + 1;// 已持续多少天，包含开始当天 -1
		int lasted = 7-sqlWeekDay+1;//开始时间距离本周结束还差多少天 包含开始当天  1
		if(continueDay<7){//3 < 7
			int residueDay = continueDay - lasted;//数据库规定的持续时间  -  本周剩余天数 6 - 1 = 5
			if((diffWeekDay>0 && diffWeekDay<=continueDay)||(diffWeekDay<0 && residueDay>0 &&(nowWeekDay<=residueDay))){
				return true;
			}else{
				return false;
			}
		}else{
			int residueDay = 7 - lasted;//持续时间还剩余多少天
			if((diffWeekDay>0 && diffWeekDay<=6)||(diffWeekDay<0 && residueDay>0 && nowWeekDay<=residueDay)){
				return true;
			}else{
				return false;
			}
		}
	}

	/**
	 * 周期每周，查询下次/上次开始时间
	 * @param handleDate 上次处理时间/当前时间
	 * @param weekDayStr 周几开始
	 * @param type 查询下次/上次  next/prev
	 *  周日 1 周一 2 ...周六 7
	 * @return
	 * */
	public static Date selPOrNDateWeek(Date handleDate, String weekDayStr,String type){

		Date startDate = handleDate;
		int weekDay = Integer.parseInt(weekDayStr);
		Calendar handleC = Calendar.getInstance();
		handleC.setTime(handleDate);
		int lastWeek = handleC.get(Calendar.DAY_OF_WEEK);
		while (lastWeek!=weekDay){
			if("next".equals(type)){
				handleC.add(Calendar.DATE,1);
			}else{
				handleC.add(Calendar.DATE,-1);
			}
			lastWeek = handleC.get(Calendar.DAY_OF_WEEK);
		}
		startDate = handleC.getTime();
		return startDate;

	}

	/**
	 * 周期每月，查询下次/上次开始时间
	 * @param nowDate    当前时间
	 * @param dayStr 几号开始
	 * @param type 查询下次/上次  next/prev
	 * @return
	 * */
	public static Date selPOrNDateMonth(Date nowDate, String dayStr,String type){

		Date startDate = nowDate;
		int day = Integer.parseInt(dayStr);
		Calendar handleC = Calendar.getInstance();
		handleC.setTime(nowDate);
		int lastDay = handleC.get(Calendar.DAY_OF_MONTH);
		while (lastDay!=day){
			if("next".equals(type)){
				handleC.add(Calendar.DAY_OF_MONTH,1);
			}else{
				handleC.add(Calendar.DAY_OF_MONTH,-1);
			}
			lastDay = handleC.get(Calendar.DAY_OF_MONTH);
		}
		startDate = handleC.getTime();
		return startDate;

	}



	/**
	 * 开始时间 加  持续时间  获取最晚开始时间
	 * @param prevStartDate 上次开始时间
	 * @param continueDayStr  持续时间
	 * @return
	 * */
	public static Date selLatestPrevStartDate(Date prevStartDate, String continueDayStr) {
		Date latestPrevDate = prevStartDate;
		Calendar c = Calendar.getInstance();
		c.setTime(prevStartDate);
		if (continueDayStr != null && !continueDayStr.isEmpty()) {
			int continueDay = Integer.parseInt(continueDayStr) - 1;
			c.add(Calendar.DATE, continueDay);
			c.set(Calendar.HOUR_OF_DAY, 23); //时
			c.set(Calendar.MINUTE, 59); //分
			c.set(Calendar.SECOND, 59); //秒
			c.set(Calendar.MILLISECOND, 999); //毫秒
			latestPrevDate = c.getTime();
		}
		return latestPrevDate;
	}

	//存入历史记录表
	@Transactional(readOnly = false)
	public boolean saveHistory(String idStr) {
		if(StringUtils.isNotBlank(idStr)){
			Warning warning = new Warning();
			String substring = idStr.substring(0, idStr.length() - 1);
			String[] split = substring.split(",");
			List<String> warnIds = Arrays.asList(split);
			warning.setIdStrList(warnIds);
			List<Warning> warnList = dao.findList(warning);
			String receivePerId = UserUtils.getUser().getId();
			String receivePerName = UserUtils.getUser().getName();
			for(Warning warn : warnList){
				WarnHistory warnHistory = warnHistoryService.selByWarnIdReceiveId(warn.getId(),receivePerId);
				if(warnHistory==null || warnHistory.getId()==null){
					warnHistory = new WarnHistory();
					warnHistory.setWarnName(warn.getName());
					warnHistory.setWarnId(warn.getId());
					warnHistory.setReceivePerName(receivePerName);
					warnHistory.setReceivePerId(receivePerId);
				}
				warnHistory.setLastRemindTime(new Date());
				warnHistoryService.save(warnHistory);
			}
			return true;
		}
		return false;
	}

	//根据预警名称获取考勤累计病事假达5个月预警
	public Warning findKQOldWarnByName(String name) {
		Warning warning = dao.findKQOldWarnByName(name);
		return warning;
	}

	//根据预警内容获取考勤累计病事假达5个月预警
	public Warning findKQOldWarnByAlertContent(String alertContent) {
		Warning warning = dao.findKQOldWarnByAlertContent(alertContent);
		return warning;
	}

	//根据新预警对象查询已存在的预警信息
	public Warning findDFOldWarnByNewWarning(Warning warning) {
		Warning oldWarning = dao.findDFOldWarnByNewWarning(warning);
		return oldWarning;
	}
}