/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceChildDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairAttendanceDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendance;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceChild;
import com.thinkgem.jeesite.modules.affair.entity.AffairAttendanceFlag;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 考勤记录Service
 * @author mason.xv
 * @version 2020-01-19
 */
@Service
@Transactional(readOnly = true)
public class AffairAttendanceService extends CrudService<AffairAttendanceDao, AffairAttendance> {

	@Autowired
	private AffairAttendanceDao affairAttendanceDao;

	@Autowired
	private AffairAttendanceFlagService affairAttendanceFlagService;

	@Autowired
	private AffairAttendanceChildDao affairAttendanceChildDao;

	Integer queQin = 0;
	Integer gongShang = 0;
	Integer nianXiu = 0;
	Integer chuChai = 0;
	Double zhiQin = 0.0;
	Integer jiaBan = 0;
	Double lingXingJB = 0.0;
	Integer kuang = 0;
	Double realZhiQin = 0.0;
	private AffairAttendanceFlag affairAttendanceFlag;

	public AffairAttendance get(String id) {
		AffairAttendance affairAttendance = super.get(id);
		affairAttendance.setAffairAttendanceChildList(affairAttendanceChildDao.findChildListByAttId(id));
		return super.get(id);
	}

	public List<AffairAttendance> findList(AffairAttendance affairAttendance) {
		return super.findList(affairAttendance);
	}

	public Page<AffairAttendance> findPage(Page<AffairAttendance> page, AffairAttendance affairAttendance) {
//		affairAttendance.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		if (UserUtils.getUser().getId().equals("a58a91c7d4db4cd4b639c863c0e48832") || UserUtils.getUser().getId().equals("e25def61e95f4864b15d203d17fcce79") || UserUtils.getUser().getId().equals("8478b98cb7e249a2afe133bed5b5e5d8") || UserUtils.getUser().getId().equals("76722f985c9e4ee7968fbd2a5d2feb2b")){
			affairAttendance.setUserId(UserUtils.getUser().getCompany().getId());
		}else {
			affairAttendance.setUserId(UserUtils.getUser().getOffice().getId());
		}
		return super.findPage(page, affairAttendance);
	}

	@Transactional(readOnly = false)
	public void save(AffairAttendance affairAttendance) {
		AffairAttendance attendance = affairAttendanceDao.findInfoByUnitIdYM(affairAttendance.getUnitId(), affairAttendance.getYear(), affairAttendance.getMouth());
		Integer ye = affairAttendance.getYear();
		Integer mo = affairAttendance.getMouth();
		if(attendance!=null){
			affairAttendance.setId(attendance.getId());
			affairAttendance.setIsNewRecord(false);
		}
		super.save(affairAttendance);

		for (AffairAttendanceChild affairAttendanceChild : affairAttendance.getAffairAttendanceChildList()) {
			if (StringUtils.isNotEmpty(affairAttendance.getIdNumber())){
				if (affairAttendance.getIdNumber().equals(affairAttendanceChild.getIdNumber())){
					if (affairAttendanceChild.getId() == null) {
						continue;
					}
					if (AffairAttendanceChild.DEL_FLAG_NORMAL.equals(affairAttendanceChild.getDelFlag())) {
						if (StringUtils.isBlank(affairAttendanceChild.getId())) {
							affairAttendanceChild.setAttId(affairAttendance.getId());
							affairAttendanceChild.preInsert();
							autoCalculate(affairAttendanceChild,ye,mo);
							affairAttendanceChildDao.insert(affairAttendanceChild);
						} else {
							affairAttendanceChild.preUpdate();
							autoCalculate(affairAttendanceChild,ye,mo);
							affairAttendanceChild.setAttId(affairAttendance.getId());
							affairAttendanceChildDao.update(affairAttendanceChild);
						}
					} else {
						affairAttendanceChildDao.delete(affairAttendanceChild);
					}
				}
			}else {
				if (affairAttendanceChild.getId() == null) {
					continue;
				}
				if (AffairAttendanceChild.DEL_FLAG_NORMAL.equals(affairAttendanceChild.getDelFlag())) {
					if (StringUtils.isBlank(affairAttendanceChild.getId())) {
						affairAttendanceChild.setAttId(affairAttendance.getId());
						affairAttendanceChild.preInsert();
						autoCalculate(affairAttendanceChild,ye,mo);
						affairAttendanceChildDao.insert(affairAttendanceChild);
					} else {
						affairAttendanceChild.preUpdate();
						autoCalculate(affairAttendanceChild,ye,mo);
						affairAttendanceChild.setAttId(affairAttendance.getId());
						affairAttendanceChildDao.update(affairAttendanceChild);
					}
				} else {
					affairAttendanceChildDao.delete(affairAttendanceChild);
				}
			}

		}
	}

	@Transactional(readOnly = false)
	public void saveOne(AffairAttendance affairAttendance) {
		AffairAttendance attendance = affairAttendanceDao.findInfoByUnitIdYM(affairAttendance.getUnitId(), affairAttendance.getYear(), affairAttendance.getMouth());
		Integer ye = affairAttendance.getYear();
		Integer mo = affairAttendance.getMouth();
		if(attendance!=null){
			affairAttendance.setId(attendance.getId());
			affairAttendance.setIsNewRecord(false);
		}
		super.save(affairAttendance);

		for (int i = 0; i < 1; i++) {
			List<AffairAttendanceChild> affairAttendanceChildList = affairAttendance.getAffairAttendanceChildList();
			AffairAttendanceChild affairAttendanceChild = affairAttendanceChildList.get(i);
			if (StringUtils.isNotEmpty(affairAttendance.getIdNumber())){
				if (affairAttendance.getIdNumber().equals(affairAttendanceChild.getIdNumber())){
					if (affairAttendanceChild.getId() == null) {
						continue;
					}
					if (AffairAttendanceChild.DEL_FLAG_NORMAL.equals(affairAttendanceChild.getDelFlag())) {
						if (StringUtils.isBlank(affairAttendanceChild.getId())) {
							affairAttendanceChild.setAttId(affairAttendance.getId());
							affairAttendanceChild.preInsert();
							autoCalculate(affairAttendanceChild,ye,mo);
							affairAttendanceChildDao.insert(affairAttendanceChild);
						} else {
							affairAttendanceChild.preUpdate();
							autoCalculate(affairAttendanceChild,ye,mo);
							affairAttendanceChild.setAttId(affairAttendance.getId());
							affairAttendanceChildDao.update(affairAttendanceChild);
						}
					} else {
						affairAttendanceChildDao.delete(affairAttendanceChild);
					}
				}
			}else {
				if (affairAttendanceChild.getId() == null) {
					continue;
				}
				if (AffairAttendanceChild.DEL_FLAG_NORMAL.equals(affairAttendanceChild.getDelFlag())) {
					if (StringUtils.isBlank(affairAttendanceChild.getId())) {
						affairAttendanceChild.setAttId(affairAttendance.getId());
						affairAttendanceChild.preInsert();
						autoCalculate(affairAttendanceChild,ye,mo);
						affairAttendanceChildDao.insert(affairAttendanceChild);
					} else {
						affairAttendanceChild.preUpdate();
						autoCalculate(affairAttendanceChild,ye,mo);
						affairAttendanceChild.setAttId(affairAttendance.getId());
						affairAttendanceChildDao.update(affairAttendanceChild);
					}
				} else {
					affairAttendanceChildDao.delete(affairAttendanceChild);
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(AffairAttendance affairAttendance) {
		super.delete(affairAttendance);
		affairAttendanceChildDao.deleteByAttId(affairAttendance.getId());
	}

	@Transactional(readOnly = false)
	public void importSave(AffairAttendance affairAttendance, AffairAttendanceChild affairAttendanceChild) {
		super.save(affairAttendance);
		//获得刚刚插入数据的id
	}

	@Transactional(readOnly = false)
	public void importSave(AffairAttendance affairAttendance) {
		super.save(affairAttendance);
	}

	public String getId(String unitId, Integer year, Integer mouth) {
		return affairAttendanceDao.getId(unitId, year, mouth);
	}

	/**
	 * 缺勤、工伤、年休、出差、执勤、加班、零星加班自动计算
	 * @param affairAttendanceChild
	 */
	private void autoCalculate(AffairAttendanceChild affairAttendanceChild,Integer ye, Integer mo){
		queQin = 0;
		gongShang = 0;
		nianXiu = 0;
		chuChai = 0;
		zhiQin = 0.0;
		jiaBan = 0;
		lingXingJB = 0.0;
		String weekday = null;
		String day = null;
		//获取今天是几号
		String nowDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		AffairAttendance affairAttendance = new AffairAttendance();
		/*Calendar calendar = Calendar.getInstance();
		if (affairAttendance.getYear() == null || "".equals(affairAttendance.getYear())) {
			affairAttendance.setYear(calendar.get(Calendar.YEAR));
			affairAttendance.setMouth(calendar.get(Calendar.MONTH) + 1);
		}
		String year = String.valueOf(affairAttendance.getYear());
		String month = String.valueOf(affairAttendance.getMouth());*/
		AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
		/*affairAttendanceFlag.setMonth(calendar.get(Calendar.MONTH) + 1);
		affairAttendanceFlag.setYear(calendar.get(Calendar.YEAR));*/
		affairAttendanceFlag.setMonth(mo);
		affairAttendanceFlag.setYear(ye);
		// 获得某年某月实际出勤天数
		int allDay = affairAttendanceFlagService.findAllDay(ye, mo);
		//查询该年月下工、节假日情况
		List<AffairAttendanceFlag> flagList = affairAttendanceDao.findAll(affairAttendanceFlag);
		/*String date = year + "-" + month + "-" + "1";
		List<Map<String,String>> tlist = TimeUtils.getDayListOfOneMonth(date);*/
		ArrayList<String> list = new ArrayList<>();
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay1())){
			list.add(affairAttendanceChild.getDay1());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay2())){
			list.add(affairAttendanceChild.getDay2());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay3())){
			list.add(affairAttendanceChild.getDay3());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay4())){
			list.add(affairAttendanceChild.getDay4());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay5())){
			list.add(affairAttendanceChild.getDay5());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay6())){
			list.add(affairAttendanceChild.getDay6());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay7())){
			list.add(affairAttendanceChild.getDay7());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay8())){
			list.add(affairAttendanceChild.getDay8());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay9())){
			list.add(affairAttendanceChild.getDay9());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay10())){
			list.add(affairAttendanceChild.getDay10());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay11())){
			list.add(affairAttendanceChild.getDay11());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay12())){
			list.add(affairAttendanceChild.getDay12());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay13())){
			list.add(affairAttendanceChild.getDay13());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay14())){
			list.add(affairAttendanceChild.getDay14());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay15())){
			list.add(affairAttendanceChild.getDay15());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay16())){
			list.add(affairAttendanceChild.getDay16());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay17())){
			list.add(affairAttendanceChild.getDay17());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay18())){
			list.add(affairAttendanceChild.getDay18());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay19())){
			list.add(affairAttendanceChild.getDay19());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay20())){
			list.add(affairAttendanceChild.getDay20());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay21())){
			list.add(affairAttendanceChild.getDay21());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay22())){
			list.add(affairAttendanceChild.getDay22());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay23())){
			list.add(affairAttendanceChild.getDay23());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay24())){
			list.add(affairAttendanceChild.getDay24());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay25())){
			list.add(affairAttendanceChild.getDay25());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay26())){
			list.add(affairAttendanceChild.getDay26());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay27())){
			list.add(affairAttendanceChild.getDay27());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay28())){
			list.add(affairAttendanceChild.getDay28());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay29())){
			list.add(affairAttendanceChild.getDay29());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay30())){
			list.add(affairAttendanceChild.getDay30());
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay31())){
			list.add(affairAttendanceChild.getDay31());
		}

		/**
		 * 需求：公式如下：
		 *  缺勤=年+病+探+婚+事+旷+丧+产+哺+学+护+长+公
		 *  工伤=公
		 *  年休=年
		 *  出差=差7
		 *  执勤=√+加+差
		 *  加班=加 加班只允许有4天
		 *  零星加班=半/2 只允许有1.5天
		 *
		 *  字典值对应如下：
		 *  √  ○  年  病  探  婚  加  半  事  旷  丧  产  差  哺  学  护  长  公
		 *  0   1   2   3   4   5   6  7   8  9   10  11  12  13  14  15  16  17
		 */
		//如果今天是周六周天或者节假日，出差不算天数
		for (int i = 0; i < flagList.size(); i++) {
//			Map<String, String> m = flagList.get(i);
			String week = flagList.get(i).getWeek();//周几
			String date = flagList.get(i).getDate().substring(8,10);//几号
//			weekday = m.get("weekday");
//			day = m.get("date");
			//用工形式为日勤制
			if ("0".equals(affairAttendanceChild.getWorkType())){
				//情况1：当月没有缺勤的值勤天数
				if (-1 == list.indexOf("2") && (-1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))){
					//当月有出差没有缺勤的值勤天数
					if (-1 != list.indexOf("12") && (-1 == list.indexOf("2") && -1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))){

						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if (jiaBan > 4){
								jiaBan = 4;
							}
							if (lingXingJB > 1.5){
								lingXingJB = 1.5;
							}
							if(22  - (chuChai - jiaBan ) >= 22){
								zhiQin = 22.0;
							}else if (22 - (chuChai - jiaBan ) >= 0 && 22 - (chuChai - jiaBan) <22){
								zhiQin = Double.valueOf(22 - (chuChai - jiaBan));
							}else {
								zhiQin = 0.0;
							}
						}else {
							zhiQin = 0.0;
						}

					}
					else {
						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if (zhiQin >= 22.0){
								zhiQin = 22.0;
							}else{
								zhiQin = Double.valueOf(22);
							}
							if (jiaBan > 4){
								jiaBan = 4;
							}
							if (lingXingJB > 1.5){
								lingXingJB = 1.5;
							}
						}else {
							zhiQin = 0.0;
						}

					}
				}
				//当月有缺勤没有出差
				else if ( -1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17")){
					//当月有缺勤有出差
					if (-1 != list.indexOf("12")){
						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if (queQin <= 22){
								if (jiaBan  > chuChai){
									if (22 - queQin > 0){
										zhiQin = Double.valueOf(22 -queQin);
									}else {
										zhiQin = realZhiQin;
									}
									if (jiaBan > 4){
										jiaBan = 4;
									}
									if (lingXingJB > 1.5){
										lingXingJB = 1.5;
									}
								}
								else if (jiaBan <= chuChai) {
									if (queQin <= 22) {
										if (22 - (chuChai - jiaBan ) - queQin > 0 && 22 - (chuChai - jiaBan ) - queQin < 23) {
											zhiQin = Double.valueOf(22 - (chuChai - jiaBan) - queQin);
										}
										if (jiaBan > 4) {
											jiaBan = 4;
										}
										if (lingXingJB > 1.5) {
											lingXingJB = 1.5;
										}
									} else {
										zhiQin = realZhiQin;
									}
								}
							}else{
								zhiQin = 0.0;
							}
						}else {
							zhiQin = 0.0;
						}
					}
					//当月有缺勤没有出差
					else {
						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if (queQin <= 22){
								if (22 - queQin >= 0){
									zhiQin = Double.valueOf(22 - queQin);
								}else {
									zhiQin = zhiQin;
								}
								if (jiaBan > 4){
									jiaBan = 4;
								}
								if (lingXingJB > 1.5){
									lingXingJB = 1.5;
								}
							}else {
								zhiQin = 0.0;
							}
						}else {
							zhiQin = 0.0;
						}
					}

				}
				/*//当月有缺勤，有出差
				else if ( list.contains("12") && -1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17")){

				}*/
			}
			//用工形式为工时制
			else {
				if (affairAttendanceChild.getMonthHours() != null && !"".equals(affairAttendanceChild.getMonthHours())) {
					//当月没有缺勤的值勤天数
					Double hours = Double.valueOf(affairAttendanceChild.getMonthHours());
					if (-1 == list.indexOf("2") && (-1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))) {
						//当月有出差没有缺勤的值勤天数
						if (-1 != list.indexOf("12") && (-1 == list.indexOf("2") && -1 == list.indexOf("3") && -1 == list.indexOf("4") && -1 == list.indexOf("5") && -1 == list.indexOf("8") && -1 == list.indexOf("9") && -1 == list.indexOf("10") && -1 == list.indexOf("11") && -1 == list.indexOf("13") && -1 == list.indexOf("14") && -1 == list.indexOf("15") && -1 == list.indexOf("16") && -1 == list.indexOf("17"))) {
							sumDay(affairAttendanceChild,week,i,date,ye,mo);
							//超过工时166.6小时代表有加班，满勤22天工时是174小时
							if (queQin < allDay && chuChai < allDay){
								if (hours > 166.6) {
									Double jiaBanHours = hours - 166.6;
									int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
									if (sumJiaBan >= 4){
										jiaBan = 4;
									}else {
										jiaBan = sumJiaBan;
									}
									if (hours >= 174.0){
										Double sumZhiQin = 22.0 + Double.valueOf(jiaBan) - chuChai;
										if (sumZhiQin >= 22.0){
											zhiQin = 22.0;
										}else {
											zhiQin = sumZhiQin;
										}
									}else {
										Double sumZhiQin = Math.round(hours / 8.0) + Double.valueOf(jiaBan) - chuChai;
										if (sumZhiQin >= 22.0){
											zhiQin = 22.0;
										}else {
											zhiQin = sumZhiQin;
										}
									}
								}
								//不到166.6小时说明没有加班
								else {
									Double sumZhiQin = Math.round(hours / 8.0) + Double.valueOf(jiaBan) - chuChai;
									if (sumZhiQin >= 22.0){
										zhiQin = 22.0;
									}else {
										zhiQin = sumZhiQin;
									}
								}
							}else {
								zhiQin = 0.0;
							}
						}
						//没有缺勤没有出差
						else {
							sumDay(affairAttendanceChild,week,i,date,ye,mo);
							if (queQin < allDay && chuChai < allDay){
								if (hours > 166.6){
									Double sumZhiQin = Double.valueOf(Math.round((hours / 8)));
									if (sumZhiQin >= 22.0){
										zhiQin = 22.0;
									}else {
										zhiQin = sumZhiQin;
									}
									Double jiaBanHours = hours - 166.6;
									int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
									if (sumJiaBan >= 4){
										jiaBan = 4;
									}else {
										jiaBan = sumJiaBan;
									}
								}else {
									Double sumZhiQin = Double.valueOf(Math.round((hours / 8)));
									if (sumZhiQin >= 22.0){
										zhiQin = 22.0;
									}else {
										zhiQin = sumZhiQin;
									}
								}
							}else {
								zhiQin = 0.0;
							}

						}
					}
					//当月有缺勤没有出差
					else if (-1 == list.indexOf("12") && (-1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17"))) {
						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if(queQin <= 22){
								if (hours >= 166.6){
									Double jiaBanHours = hours - 166.6;
									int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
									if (sumJiaBan >= 4){
										jiaBan = 4;
									}else {
										jiaBan = sumJiaBan;
									}
									zhiQin = 22.0 - queQin;
								}else {
									zhiQin = 22.0 - queQin;
								}

							}else {
								zhiQin = 0.0;
							}
						}else {
							zhiQin = 0.0;
						}

					}
					//当月有缺勤，有出差
					else if (-1 != list.indexOf("2") || -1 != list.indexOf("3") || -1 != list.indexOf("4") || -1 != list.indexOf("5") || -1 != list.indexOf("8") || -1 != list.indexOf("9") || -1 != list.indexOf("10") || -1 != list.indexOf("11") || -1 != list.indexOf("13") || -1 != list.indexOf("14") || -1 != list.indexOf("15") || -1 != list.indexOf("16") || -1 != list.indexOf("17") || -1 != list.indexOf("12")) {
						sumDay(affairAttendanceChild,week,i,date,ye,mo);
						if (queQin < allDay && chuChai < allDay){
							if (queQin <= 22){
								if (hours > 166.6){
									Double jiaBanHours = hours - 166.6;
									int sumJiaBan = (int) Math.floor((jiaBanHours / 4));
									if (sumJiaBan >= 4){
										jiaBan = 4;
									}else {
										jiaBan = sumJiaBan;
									}
									if (jiaBan > chuChai){
										zhiQin = 22.0 - queQin;
									}else {
										if (hours >= 174.0){
											Double sumZhiQin = 22 + Double.valueOf(jiaBan) - queQin - chuChai;
											if (sumZhiQin >= 22.0){
												zhiQin = 22.0;
											}else {
												zhiQin = sumZhiQin;
											}
										}else {
											Double sumZhiQin = Double.valueOf(Math.round((hours / 8))) + Double.valueOf(jiaBan) - queQin - chuChai;
											if (sumZhiQin >= 22.0){
												zhiQin = 22.0;
											}else {
												zhiQin = sumZhiQin;
											}
										}
									}
								}
								//没有加班
								else {
									Double sumZhiQin = Double.valueOf(Math.round((hours / 8)))  - queQin - chuChai;
									if (sumZhiQin >= 22.0){
										zhiQin = 22.0;
									}else {
										zhiQin = sumZhiQin;
									}
								}
							}else {
								zhiQin = 0.0;
							}
						}else {
							zhiQin = 0.0;
						}
					}
				}
			}
		}
		//
		affairAttendanceChild.setLackWork(queQin);
		affairAttendanceChild.setWorkInjury(gongShang.toString());
		affairAttendanceChild.setYearWeak(nianXiu.toString());
		affairAttendanceChild.setGoOut(chuChai.toString());
		affairAttendanceChild.setInWork(zhiQin.toString());
		affairAttendanceChild.setOvertime(jiaBan.toString());
		affairAttendanceChild.setOvertimeChip(lingXingJB.toString());
		affairAttendanceChild.setKuang(kuang);
	}
	/**
	 * 计算各种天数
	 * @param affairAttendanceChild 考勤记录子表实体类
	 * @param weekDay 周几
	 * @param i	  当前为当月第几天
	 * @param day 几号
	 * @param year  年份
	 * @param month 月份
	 */
	private void sumDay(AffairAttendanceChild affairAttendanceChild,String weekDay,int i,String day,Integer year, Integer month) {
		//获得到所有的节假日
		Calendar calendar = Calendar.getInstance();
		AffairAttendanceFlag affairAttendanceFlag = new AffairAttendanceFlag();
		affairAttendanceFlag.setYear(year);
		affairAttendanceFlag.setMonth(month);
		List<AffairAttendanceFlag> flagList = affairAttendanceDao.findAll(affairAttendanceFlag);
		/*List <String> holiday = new ArrayList<>();
		for (AffairAttendanceFlag a : flagList){
			holiday.add(a.getDate());
		}*/
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay1()) && i == 0){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay1());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay1());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay1());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay1());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay2()) && i == 1){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay2());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay2());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay2());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay2());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay3()) && i == 2){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay3());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay3());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay3());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay3());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay4()) && i == 3){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay4());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay4());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay4());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay4());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay5()) && i == 4){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay5());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay5());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay5());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay5());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay6()) && i == 5){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay6());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay6());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay6());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay6());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay7()) && i == 6){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay7());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay7());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay7());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay7());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay8()) && i == 7){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay8());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay8());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay8());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay8());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay9()) && i == 8){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay9());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay9());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay9());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay9());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay10()) && i == 9){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay10());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay10());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay10());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay10());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay11()) && i == 10){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay11());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay11());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay11());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay11());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay12()) && i == 11){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay12());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay12());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay12());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay12());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay13()) && i == 12){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay13());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay13());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay13());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay13());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay14()) && i == 13){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay14());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay14());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay14());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay14());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay15()) && i == 14){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay15());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay15());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay15());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay15());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay16()) && i == 15){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay16());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay16());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay16());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay16());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay17()) && i == 16){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay17());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay17());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay17());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay17());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay18()) && i == 17){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay18());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay18());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay18());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay18());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay19()) && i == 18){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay19());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay19());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay19());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay19());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay20()) && i == 19){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay20());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay20());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay20());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay20());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay21()) && i == 20){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay21());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay21());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay21());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay21());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay22()) && i == 21){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay22());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay22());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay22());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay22());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay23()) && i == 22){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay23());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay23());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay23());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay23());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay24()) && i == 23){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay24());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay24());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay24());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay24());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay25()) && i == 24){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay25());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay25());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay25());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay25());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay26()) && i == 25){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay26());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay26());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay26());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay26());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay27()) && i == 26){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay27());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay27());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay27());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay27());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay28()) && i == 27){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay28());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay28());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay28());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay28());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay29()) && i == 28){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay29());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay29());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay29());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay29());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay30()) && i == 29){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay30());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay30());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay30());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay30());
			}
		}
		if(StringUtils.isNotBlank(affairAttendanceChild.getDay31()) && i == 30){
			if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName()))) {
				zhouMo(affairAttendanceChild.getDay31());
			}else if (("六".equals(weekDay) || "日".equals(weekDay)) && (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName()))){
				zhouMoG(affairAttendanceChild.getDay31());
			}
			else if (flagList.get(i).getDate().substring(8,10).equals(day) && "1".equals(flagList.get(i).getName())){
				normolDay(affairAttendanceChild.getDay31());
			}else if (flagList.get(i).getDate().substring(8,10).equals(day) && "2".equals(flagList.get(i).getName())){
				normolDayX(affairAttendanceChild.getDay31());
			}
		}
	}


	//周一到周五条件(工)
	private void normolDay(String str) {
		switch (str) {
			case "0":
				realZhiQin += 1;
				break;
			case "2":
				queQin += 1;
				nianXiu += 1;
				break;
			case "3":
				queQin += 1;
				break;
			case "4":
				queQin += 1;
				break;
			case "5":
				queQin += 1;
				break;
			case "6":
				break;
			case "7":
				break;
			case "8":
				queQin += 1;
				break;
			case "9":
				queQin += 1;
				kuang += 1;
				break;
			case "10":
				queQin += 1;
				break;
			case "11":
				queQin += 1;
				break;
			case "12":
				chuChai += 1;
				break;
			case "13":
				queQin += 1;
				break;
			case "14":
				queQin += 1;
				break;
			case "15":
				queQin += 1;
				break;
			case "16":
				queQin += 1;
				break;
			case "17":
				queQin += 1;
				gongShang += 1;
				break;
			default:
				break;
		}
	}

	//周一到周五（休）
	public void normolDayX(String str) {
		switch (str) {
			case "0":
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			case "6":
				jiaBan += 1;
				break;
			case "7":
				lingXingJB += 0.5;
				break;
			case "8":
				break;
			case "9":
				break;
			case "10":
				break;
			case "11":
				break;
			case "12":
				break;
			case "13":
				break;
			case "14":
				break;
			case "15":
				break;
			case "16":
				break;
			case "17":
				break;
			default:
				break;
		}
	}

	//周六周日条件（工）
	public void zhouMoG(String str) {
		switch (str) {
			case "0":
				realZhiQin += 1;
				break;
			case "2":
				queQin += 1;
				nianXiu += 1;
				break;
			case "3":
				queQin += 1;
				break;
			case "4":
				queQin += 1;
				break;
			case "5":
				queQin += 1;
				break;
			case "6":
				break;
			case "7":
				break;
			case "8":
				queQin += 1;
				break;
			case "9":
				queQin += 1;
				kuang += 1;
				break;
			case "10":
				queQin += 1;
				break;
			case "11":
				queQin += 1;
				break;
			case "12":
				chuChai += 1;
				break;
			case "13":
				queQin += 1;
				break;
			case "14":
				queQin += 1;
				break;
			case "15":
				queQin += 1;
				break;
			case "16":
				queQin += 1;
				break;
			case "17":
				queQin += 1;
				gongShang += 1;
				break;
			default:
				break;
		}
	}

	//周六周日条件（休）
	public void zhouMo(String str) {
		switch (str) {
			case "0":
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				break;
			case "5":
				break;
			case "6":
				jiaBan += 1;
				break;
			case "7":
				lingXingJB += 0.5;
				break;
			case "8":
				break;
			case "9":
				break;
			case "10":
				break;
			case "11":
				break;
			case "12":
				break;
			case "13":
				break;
			case "14":
				break;
			case "15":
				break;
			case "16":
				break;
			case "17":
				break;
			default:
				break;
		}
	}

	public List<PersonnelBase> findPerson(String unitId){
		return affairAttendanceDao.findPerson(unitId);
	}

	public List<AffairAttendanceFlag> findAll(AffairAttendanceFlag affairAttendanceFlag){
		return affairAttendanceDao.findAll(affairAttendanceFlag);
	}

	public List<String> selectAllYear(){
		return affairAttendanceDao.selectAllYear();
	}

	public List<String> selectAllMonth(){
		return affairAttendanceDao.selectAllMonth();
	}

	public String selectId(Integer year,Integer month,String idNumber){
		return affairAttendanceDao.selectId(year,month,idNumber);
	}
	public String selectUnitById(String idNumber){
		return affairAttendanceDao.selectUnitById(idNumber);
	}

	public String selectUnitIdById(String idNumber){
		return affairAttendanceDao.selectUnitIdById(idNumber);
	}

	public List<String> selectAllId(String year,String idNumber){
		return affairAttendanceDao.selectAllId(year,idNumber);
	}

	//单独增加一条
	@Transactional(readOnly = false)
	public void addOne(AffairAttendance affairAttendance) {
		affairAttendance.preInsert();
		affairAttendanceDao.addOne(affairAttendance);
	}

	public AffairAttendance findInfoByUnitIdYM(String unitId, int year, int month) {
		return affairAttendanceDao.findInfoByUnitIdYM(unitId,year,month);
	}
}