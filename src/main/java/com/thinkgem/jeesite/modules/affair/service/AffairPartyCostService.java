/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyCostDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairPartyMemberDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairPartyCost;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.warn.entity.Warning;
import com.thinkgem.jeesite.modules.warn.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 党费管理Service
 * @author eav.liu
 * @version 2019-11-11
 */
@Service
@Transactional(readOnly = true)
public class AffairPartyCostService extends CrudService<AffairPartyCostDao, AffairPartyCost> {

	@Autowired
	private AffairPartyMemberDao affairPartyMemberDao;

	@Autowired
	private AffairPartyCostDao affairPartyCostDao;

	@Autowired
	private WarningService warningService;

	@Autowired
	private UserDao userDao;

	public AffairPartyCost get(String id) {
		return super.get(id);
	}
	
	public List<AffairPartyCost> findList(AffairPartyCost affairPartyCost) {
		return super.findList(affairPartyCost);
	}
	
	public Page<AffairPartyCost> findPage(Page<AffairPartyCost> page, AffairPartyCost affairPartyCost) {
		affairPartyCost.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		return super.findPage(page, affairPartyCost);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairPartyCost affairPartyCost) {
		super.save(affairPartyCost);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairPartyCost affairPartyCost) {
		super.delete(affairPartyCost);
	}

	@Transactional(readOnly = false)
	public void insertPMDatas(String partBranchId,String year,String month) {//增加党组织id参数
		//获得今年的党员名册数据
		//List<String> list1 = affairPartyCostDao.findThisYearDatas();//身份证集合
		if(StringUtils.isBlank(year)){
			year = DateUtils.getYear();
		}
		if(StringUtils.isBlank(month)){
			SimpleDateFormat sdf = new SimpleDateFormat("M");
			Date date = new Date();
			month = sdf.format(date);
		}else{
			//转化为M格式
			month = Integer.valueOf(month).toString();
		}
		//获得当前党组织选择年月的党员名册数据
		List<String> list1 = affairPartyCostDao.findSelYearMonthDatas(year,month,partBranchId);//身份证集合
//		String year = DateUtils.getYear();
//		String month = DateUtils.getMonth();

		String lastYear = String.valueOf(Integer.valueOf(year) - 1);
//		List<Map<String, String>> baseList = affairPartyCostDao.findBaseMoney(year,month);//当前月份 工资信息  身份证号，基本工资-职务工资、试用期工资，基本工资-级别工资，基本养老保险，基本医疗保险，补充医疗保险，职业年金，公积金
//		List<Map<String, String>> baseList = affairPartyCostDao.findBaseMoney(year,"1");//获取本年度第一月 工资信息  身份证号，基本工资-职务工资、试用期工资，基本工资-级别工资，基本养老保险，基本医疗保险，补充医疗保险，职业年金，公积金
//		List<Map<String, String>> baseSumList = affairPartyCostDao.findBaseSumMoney(lastYear);  //上一年 工作性津贴，个人所得税 总计

		if(list1 != null && list1.size() == 0){
			List<Map<String, String>> list2=  affairPartyMemberDao.getPMDatas(partBranchId);//从党员花名册 获取所有党员
			for (Map<String, String> m : list2) {
				String idNumber = m.get("cardNum");//身份证号
				int count = affairPartyCostDao.countNumber(m.get("cardNum"),year,month);
				if (count > 0){
					affairPartyCostDao.deleteOldInfo(m.get("cardNum"),year,month);
				}
				//2021.3.24 吴卫兵:不应该取当月数据的，应该是每年1月份的数据
				List<Map<String, String>> baseList = affairPartyCostDao.findPersonBaseMoney(year,"1",idNumber);//获取本年度第一月 工资信息  身份证号，基本工资-职务工资、试用期工资，生活性补贴，基本工资-级别工资，基本养老保险，基本医疗保险，补充医疗保险，职业年金，公积金
				List<Map<String, String>> baseSumList = affairPartyCostDao.findPersonLastYearBaseSumMoney(lastYear,idNumber);  //上一年 工作性津贴，个人所得税 总计
				if(baseList.size() > 0 && baseList != null && baseSumList .size() > 0 && baseSumList != null){
					AffairPartyCost affairPartyCost = new AffairPartyCost();
					Double jiShu = null;
					for (int i = 0; i < baseList.size(); i++) {
						for (int j = 0; j < baseSumList.size(); j++) {
							if (baseList.get(i).get("id_number").equals(m.get("cardNum")) && baseSumList.get(j).get("id_number").equals(m.get("cardNum"))){
								Double jbSalary = Double.valueOf(String.valueOf(baseList.get(i).get("jb_salary")));// 基本工资-职务工资、试用期工资
								Double jbGradeSalary = Double.valueOf(String.valueOf(baseList.get(i).get("jb_grade_salary")));// 基本工资-级别工资
								//Double livingAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("living_allowance")));// 生活性补贴
								Double baseYanglaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("base_yanglao__allowance")));// 基本养老保险
								Double baseYiliaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("base_yiliao_allowance")));// 基本医疗保险
								Double buchongYiliaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("buchong_yiliao_allowance")));// 补充医疗保险
								Double zhiyeAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("zhiye_allowance")));// 职业年金
								Double gongJiJin = Double.valueOf(String.valueOf(baseList.get(i).get("gongjijin")));// 住房公积金
								Double workingAllowance = keepDecimal(Double.valueOf(String.valueOf(baseSumList.get(j).get("working_allowance"))) / 12.0,1);//工作性津贴
								Double personalIncomeTax = keepDecimal(Double.valueOf(String.valueOf(baseSumList.get(j).get("personal_income_tax"))) / 12.0,1);//个人所得税
								//项目建议书：公安局在职党员党费=（职务工资+级别工资+工作性津贴+生活性补贴-个人所得税-养老金-职业年金-个人医保-个人住房公积金）×交纳党费比例
								//代码：公安局在职党员党费=（职务工资+级别工资+工作性津贴-个人所得税-养老金-职业年金-个人医保-个人住房公积金）×交纳党费比例
								jiShu = jbSalary + jbGradeSalary + workingAllowance  - personalIncomeTax - baseYanglaoAllowance - zhiyeAllowance - baseYiliaoAllowance - buchongYiliaoAllowance -gongJiJin;
								jiShu = keepDecimal(jiShu,1);
								affairPartyCost.setZwMoney(jbSalary);//职务工资
								affairPartyCost.setJbMoney(jbGradeSalary);//级别工资
								affairPartyCost.setGzjtMoney(workingAllowance);//上年度工作性津贴平均数
								affairPartyCost.setSdsMoney(personalIncomeTax);//上年的个人所得税平均数
								affairPartyCost.setYlMoney(baseYanglaoAllowance);//本年度养老金
								affairPartyCost.setYbMoney(baseYiliaoAllowance+buchongYiliaoAllowance);//本年度医保金
								affairPartyCost.setZyMoney(zhiyeAllowance);//本年度职业年金
								affairPartyCost.setGjjMoney(gongJiJin);//本年度公积金
								affairPartyCost.setJishu(jiShu);//缴费基数
								if ("1".equals(m.get("personnelType")) && "1".equals(m.get("personnelCategory"))){
									if (jiShu <= 3000){
										affairPartyCost.setBili("0.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (3000 < jiShu && jiShu <= 5000){
										affairPartyCost.setBili("1.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (5000 < jiShu && jiShu <= 10000){
										affairPartyCost.setBili("1.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else {
										affairPartyCost.setBili("2.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}
								}else if ("1".equals(m.get("personnelType")) && "2".equals(m.get("personnelCategory"))){
									if (jiShu <= 3000){
										affairPartyCost.setBili("0.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (3000 < jiShu && jiShu <= 5000){
										affairPartyCost.setBili("1.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (5000 < jiShu && jiShu <= 10000){
										affairPartyCost.setBili("1.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else {
										affairPartyCost.setBili("2.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}
								}
							}
						}

					}
					if ("1".equals(m.get("personnelType"))){
						affairPartyCost.setType("1");
					}else {
						affairPartyCost.setType("2");
					}
					affairPartyCost.setName(m.get("name"));
					affairPartyCost.setIdNumber(m.get("cardNum"));
					affairPartyCost.setYear(year);
					affairPartyCost.setMonth(month);
					affairPartyCost.setBaseNum(jiShu);
					try {
						this.createPartyWarning(affairPartyCost);
					}catch (Exception e){
						e.printStackTrace();
						logger.error("生成党费预警，发生错误："+e.getMessage());
					}
					super.save(affairPartyCost);
				} else {
					AffairPartyCost affairPartyCost = new AffairPartyCost();
					affairPartyCost.setName(m.get("name"));
					affairPartyCost.setIdNumber(m.get("cardNum"));
					affairPartyCost.setYear(year);
					affairPartyCost.setMonth(month);
					try {
						this.createPartyWarning(affairPartyCost);
					}catch (Exception e){
						e.printStackTrace();
						logger.error("生成党费预警，发生错误："+e.getMessage());
					}
					super.save(affairPartyCost);
				}
			}
		}
		else if(list1 != null && list1.size() > 0){
			List<Map<String, String>> list2=  affairPartyMemberDao.getPMDatasByCardNum(list1,partBranchId);//增加党组织id参数
			for (Map<String, String> m : list2) {
				String idNumber = m.get("cardNum");
				int count = affairPartyCostDao.countNumber(m.get("cardNum"),year,month);
				if (count > 0){
					affairPartyCostDao.deleteOldInfo(m.get("cardNum"),year,month);
				}
				//2021.3.24 吴卫兵:不应该取当月数据的，应该是每年1月份的数据
				List<Map<String, String>> baseList = affairPartyCostDao.findPersonBaseMoney(year,"1",idNumber);//获取本年度第一月 工资信息  身份证号，基本工资-职务工资、试用期工资，基本工资-级别工资，生活性补贴，基本养老保险，基本医疗保险，补充医疗保险，职业年金，公积金
				List<Map<String, String>> baseSumList = affairPartyCostDao.findPersonLastYearBaseSumMoney(lastYear,idNumber);  //上一年 工作性津贴，个人所得税 总计
				if(baseList.size() > 0 && baseList != null && baseSumList .size() > 0 && baseSumList != null){
					AffairPartyCost affairPartyCost = new AffairPartyCost();
					Double jiShu = null;
					for (int i = 0; i < baseList.size(); i++) {
						for (int j = 0; j < baseSumList.size(); j++) {
							if (baseList.get(i).get("id_number").equals(m.get("cardNum")) && baseSumList.get(j).get("id_number").equals(m.get("cardNum"))){
								Double jbSalary = Double.valueOf(String.valueOf(baseList.get(i).get("jb_salary")));// 基本工资-职务工资、试用期工资
								Double jbGradeSalary = Double.valueOf(String.valueOf(baseList.get(i).get("jb_grade_salary")));// 基本工资-级别工资
//								Double livingAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("living_allowance")));// 生活性补贴
								Double baseYanglaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("base_yanglao__allowance")));// 基本养老保险
								Double baseYiliaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("base_yiliao_allowance")));// 基本医疗保险
								Double buchongYiliaoAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("buchong_yiliao_allowance")));// 补充医疗保险
								Double zhiyeAllowance = Double.valueOf(String.valueOf(baseList.get(i).get("zhiye_allowance")));// 职业年金
								Double gongJiJin = Double.valueOf(String.valueOf(baseList.get(i).get("gongjijin")));// 住房公积金
								Double workingAllowance = keepDecimal(Double.valueOf(String.valueOf(baseSumList.get(j).get("working_allowance"))) / 12,1);//工作性津贴
								Double personalIncomeTax = keepDecimal(Double.valueOf(String.valueOf(baseSumList.get(j).get("personal_income_tax"))) / 12,1);//个人所得税
								//项目建议书：公安局在职党员党费=（职务工资+级别工资+工作性津贴+生活性补贴-个人所得税-养老金-职业年金-个人医保-个人住房公积金）×交纳党费比例
								jiShu = jbSalary + jbGradeSalary + workingAllowance  - personalIncomeTax - baseYanglaoAllowance - baseYiliaoAllowance - buchongYiliaoAllowance - zhiyeAllowance-gongJiJin;
								jiShu = keepDecimal(jiShu,1);
								affairPartyCost.setZwMoney(jbSalary);//职务工资
								affairPartyCost.setJbMoney(jbGradeSalary);//级别工资
								affairPartyCost.setGzjtMoney(workingAllowance);//上年度工作性津贴平均数
								affairPartyCost.setSdsMoney(personalIncomeTax);//上年的个人所得税平均数
								affairPartyCost.setYlMoney(baseYanglaoAllowance);//本年度养老金
								affairPartyCost.setYbMoney(baseYiliaoAllowance+buchongYiliaoAllowance);
								affairPartyCost.setZyMoney(zhiyeAllowance);//本年度职业年金
								affairPartyCost.setGjjMoney(gongJiJin);//本年度公积金
								affairPartyCost.setJishu(jiShu);//缴费基数
								if ("1".equals(m.get("personnelType")) && "1".equals(m.get("personnelCategory"))){
									if (jiShu <= 3000){
										affairPartyCost.setBili("0.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (3000 < jiShu && jiShu <= 5000){
										affairPartyCost.setBili("1.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (5000 < jiShu && jiShu <= 10000){
										affairPartyCost.setBili("1.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else {
										affairPartyCost.setBili("2.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}
								}else if ("1".equals(m.get("personnelType")) && "2".equals(m.get("personnelCategory"))){
									if (jiShu <= 3000){
										affairPartyCost.setBili("0.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (3000 < jiShu && jiShu <= 5000){
										affairPartyCost.setBili("1.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else if (5000 < jiShu && jiShu <= 10000){
										affairPartyCost.setBili("1.5");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}else {
										affairPartyCost.setBili("2.0");
										affairPartyCost.setCost2(keepDecimal(jiShu * (Double.valueOf(String.valueOf(affairPartyCost.getBili())) / 100),1));
									}
								}
							}
						}

					}
					if ("1".equals(m.get("personnelType"))){
						affairPartyCost.setType("1");
					}else {
						affairPartyCost.setType("2");
					}
					affairPartyCost.setName(m.get("name"));
					affairPartyCost.setIdNumber(m.get("cardNum"));
					affairPartyCost.setYear(year);
					affairPartyCost.setMonth(month);
					affairPartyCost.setBaseNum(jiShu);
					try {
						this.createPartyWarning(affairPartyCost);
					}catch (Exception e){
						e.printStackTrace();
						logger.error("生成党费预警，发生错误："+e.getMessage());
					}
					super.save(affairPartyCost);
				}else {
					AffairPartyCost affairPartyCost = new AffairPartyCost();
					affairPartyCost.setName(m.get("name"));
					affairPartyCost.setIdNumber(m.get("cardNum"));
					affairPartyCost.setYear(year);
					affairPartyCost.setMonth(month);
					try {
						this.createPartyWarning(affairPartyCost);
					}catch (Exception e){
						e.printStackTrace();
						logger.error("生成党费预警，发生错误："+e.getMessage());
					}
					//super.save(affairPartyCost);
				}
				/*AffairPartyCost affairPartyCost = new AffairPartyCost();
				affairPartyCost.setName(m.get("name"));
				affairPartyCost.setIdNumber(m.get("cardNum"));
				affairPartyCost.setYear(year);
				super.save(affairPartyCost);*/
			}
		}
	}

	/**
	 * 删除党费表今年的相关信息
	 * @param idNumber
	 */
	public void deleteThisYearByIdNumber(String idNumber){
		affairPartyCostDao.deleteThisYearByIdNumber(idNumber);
	}

	public List<AffairPartyCost> findInfo(AffairPartyCost affairPartyCost){
		return affairPartyCostDao.findInfo(affairPartyCost);
	}

	//创建党费生成预警
	@Transactional(readOnly = false)
	public void createPartyWarning(AffairPartyCost affairPartyCost){
		Warning warning = new Warning();
		warning.setName("党费上缴预警");
		warning.setDate(Calendar.getInstance().getTime());//当前时间
		warning.setContinueDay("0");//持续时间
		warning.setRepeatCycle("4");//周期
		warning.setIsAlert("1");//使用弹窗
		warning.setAlertDegree("1");//紧急程度
		warning.setRemind("20");//重复提醒
		//设置创建人，修改人
		warning.setCreateBy(UserUtils.getUser());
		warning.setUpdateBy(UserUtils.getUser());
		String year = affairPartyCost.getYear();//缴费年度
		String month = affairPartyCost.getMonth();//缴费月度
		String partyCost;
		try {
			partyCost = String.valueOf((double)Math.round(affairPartyCost.getCost2()*100)/100);
		}catch (Exception e){
//			e.printStackTrace();
			partyCost = "0.0";
		}
		warning.setAlertContent(year+"年"+month+"月应缴党费已生成。应缴金额："+partyCost+"元");
		String name = affairPartyCost.getName();
		String userId  = userDao.getIdByNo(affairPartyCost.getIdNumber());
		if(StringUtils.isNotBlank(userId)){
			warning.setReceivePerName(name);
			warning.setReceivePerId(userId);
			warningService.save(warning);
		}
	}

	/**
	 * 保留小数
	 * @param num
	 * @param count
	 * num 需要保留小数的数据
	 * count 保留几位小数
	 */


	public double keepDecimal(double num,int count){
		int ten_num = 1;
		for (int i = 0; i < count; i++) {
			ten_num = ten_num*10;
		}
		try {
			return (double)Math.round(num*ten_num)/ten_num;
		}catch (Exception e){
			logger.error(getExceptionInfo(e));
			return num;
		}

	}

	//异常信息打印到日志
	public static String getExceptionInfo(Exception e){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			return baos.toString();
		}catch (Exception e2){
			return e.toString();
		}

	}
}