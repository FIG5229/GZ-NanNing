/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairCasualtyReportDao;
import com.thinkgem.jeesite.modules.affair.dao.AffairMjxyReportDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairCasualtyReportStatistic;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 抚恤申报Service
 * @author mason.xv
 * @version 2019-11-15
 */
@Service
@Transactional(readOnly = true)
public class AffairCasualtyReportService extends CrudService<AffairCasualtyReportDao, AffairCasualtyReport> {

	@Autowired
	AffairCasualtyReportDao affairCasualtyReportDao;

	@Autowired
	OfficeDao officeDao;

	public AffairCasualtyReport get(String id) {
		return super.get(id);
	}
	
	public List<AffairCasualtyReport> findList(AffairCasualtyReport affairCasualtyReport) {
		return super.findList(affairCasualtyReport);
	}
	
	public Page<AffairCasualtyReport> findPage(Page<AffairCasualtyReport> page, AffairCasualtyReport affairCasualtyReport) {
		return super.findPage(page, affairCasualtyReport);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairCasualtyReport affairCasualtyReport) {
		//3:未审核
		affairCasualtyReport.setShStatus("3");
		super.save(affairCasualtyReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairCasualtyReport affairCasualtyReport) {
		super.delete(affairCasualtyReport);
	}

	@Transactional(readOnly = false)
	public void shenHe(AffairCasualtyReport affairCasualtyReport) {
		affairCasualtyReport.setUpdateDate(new Date());
		affairCasualtyReport.setShPerson(UserUtils.getUser().getName());
		affairCasualtyReportDao.shenHe(affairCasualtyReport);
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void submitByIds(List<String> ids){
		List<AffairCasualtyReport> list = affairCasualtyReportDao.findByIds(ids);
		for (AffairCasualtyReport affairCasualtyReport: list) {
			//过滤掉已经提交的数据
			if ("1".equals(affairCasualtyReport.getShStatus())){
				//提交
				affairCasualtyReport.setShStatus("2");//未审核
				super.save(affairCasualtyReport);
			}
		}
	}
	@Autowired
	private AffairMjxyReportDao affairMjxyReportDao;
	/**
	 * 伤亡信息查询初始页面
	 * @param affairCasualtyReport
	 * @return
	 */
	public List<AffairCasualtyReportStatistic> statistic(AffairCasualtyReport affairCasualtyReport) {
        List<AffairCasualtyReportStatistic> list = new ArrayList<AffairCasualtyReportStatistic>();
        if(affairCasualtyReport.getAffirmDepId() == null || "".equals(affairCasualtyReport.getAffirmDepId())){
			getStatisticList(affairCasualtyReport, list);
		}else{
			AffairCasualtyReportStatistic statistic = affairCasualtyReportDao.findByUnitId(affairCasualtyReport.getAffirmDepId(),affairCasualtyReport);
			statistic.setSum(statistic.getXiSheng()+statistic.getBingGu()+statistic.getShangCan()+statistic.getShangWang());
			statistic.setUnitName(affairCasualtyReport.getAffirmDep());
			statistic.setUnitId(affairCasualtyReport.getAffirmDepId());
			list.add(statistic);
		}
		return list;
	}

	private void getStatisticList(AffairCasualtyReport affairCasualtyReport, List<AffairCasualtyReportStatistic> list) {
		String id = UserUtils.getUser().getCompany().getId();
		if ("1".equals(id)){
			//局机关
			AffairCasualtyReportStatistic juJiGuan = affairCasualtyReportDao.findNumsByParentId("1",affairCasualtyReport);
			juJiGuan.setUnitName("局机关");
			juJiGuan.setUnitId("1");
			//合计
			juJiGuan.setSum(juJiGuan.getXiSheng()+juJiGuan.getBingGu()+juJiGuan.getShangCan()+juJiGuan.getShangWang());
			list.add(juJiGuan);
			//南宁公安处
			AffairCasualtyReportStatistic nanNingChu = affairCasualtyReportDao.findNumsByParentId("34",affairCasualtyReport);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			//合计
			nanNingChu.setSum(nanNingChu.getXiSheng()+nanNingChu.getBingGu()+nanNingChu.getShangCan()+nanNingChu.getShangWang());
			list.add(nanNingChu);
			//柳州公安处
			AffairCasualtyReportStatistic liuZhouChu = affairCasualtyReportDao.findNumsByParentId("95",affairCasualtyReport);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			//合计
			liuZhouChu.setSum(liuZhouChu.getXiSheng()+liuZhouChu.getBingGu()+liuZhouChu.getShangCan()+liuZhouChu.getShangWang());
			list.add(liuZhouChu);
			//北海公安处
			AffairCasualtyReportStatistic beihaiChu = affairCasualtyReportDao.findNumsByParentId("156",affairCasualtyReport);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			//合计
			beihaiChu.setSum(beihaiChu.getXiSheng()+beihaiChu.getBingGu()+beihaiChu.getShangCan()+beihaiChu.getShangWang());
			list.add(beihaiChu);
		}else if("34".equals(id)){
			//南宁公安处
			AffairCasualtyReportStatistic nanNingChu = affairCasualtyReportDao.findNumsByParentId("34",affairCasualtyReport);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			//合计
			nanNingChu.setSum(nanNingChu.getXiSheng()+nanNingChu.getBingGu()+nanNingChu.getShangCan()+nanNingChu.getShangWang());
			list.add(nanNingChu);
		}else if("95".equals(id)){
			//柳州公安处
			AffairCasualtyReportStatistic liuZhouChu = affairCasualtyReportDao.findNumsByParentId("95",affairCasualtyReport);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			//合计
			liuZhouChu.setSum(liuZhouChu.getXiSheng()+liuZhouChu.getBingGu()+liuZhouChu.getShangCan()+liuZhouChu.getShangWang());
			list.add(liuZhouChu);
		}else{
			//北海公安处
			AffairCasualtyReportStatistic beihaiChu = affairCasualtyReportDao.findNumsByParentId("156",affairCasualtyReport);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			//合计
			beihaiChu.setSum(beihaiChu.getXiSheng()+beihaiChu.getBingGu()+beihaiChu.getShangCan()+beihaiChu.getShangWang());
			list.add(beihaiChu);
		}
	}

	/**
	 * 伤亡信息第二层统计汇总  点单位弹窗/条件查询
	 * @param affairCasualtyReportStatistic
	 * @param affairCasualtyReport
	 * @return
	 */
	public List<AffairCasualtyReportStatistic> findByUnitId(AffairCasualtyReportStatistic affairCasualtyReportStatistic, AffairCasualtyReport affairCasualtyReport) {
		List<AffairCasualtyReportStatistic> list = new ArrayList<>();
		if(affairCasualtyReport.getAffirmDepId() == null || "".equals(affairCasualtyReport.getAffirmDepId())){
			List<Office> child = officeDao.findChildById(affairCasualtyReportStatistic.getUnitId());
			for (Office office:child) {
				AffairCasualtyReportStatistic statistic = affairCasualtyReportDao.findByUnitId(office.getId(),affairCasualtyReport);
				statistic.setSum(statistic.getXiSheng()+statistic.getBingGu()+statistic.getShangCan()+statistic.getShangWang());
				statistic.setUnitName(office.getName());
				statistic.setUnitId(office.getId());
				list.add(statistic);
			}
		}else{
			AffairCasualtyReportStatistic statistic = affairCasualtyReportDao.findByUnitId(affairCasualtyReport.getAffirmDepId(),affairCasualtyReport);
			statistic.setSum(statistic.getXiSheng()+statistic.getBingGu()+statistic.getShangCan()+statistic.getShangWang());
			statistic.setUnitName(affairCasualtyReport.getAffirmDep());
			statistic.setUnitId(affairCasualtyReport.getAffirmDepId());
			list.add(statistic);
		}
		return list;
	}

	/**
	 * 伤亡信息第二层统计汇总   点种类数量出现的弹窗
	 * @param affairCasualtyReportStatistic
	 * @param affairCasualtyReport
	 * @return
	 */
	public List<AffairCasualtyReportStatistic> findOneTypeByUnitId(AffairCasualtyReportStatistic affairCasualtyReportStatistic, AffairCasualtyReport affairCasualtyReport) {
		List<AffairCasualtyReportStatistic> list = new ArrayList<>();
		if(affairCasualtyReport.getAffirmDepId() == null || "".equals(affairCasualtyReport.getAffirmDepId())){
			List<Office> child = officeDao.findChildById(affairCasualtyReportStatistic.getUnitId());
			for (Office office:child) {
				AffairCasualtyReportStatistic statistic = affairCasualtyReportDao.findOneTypeByUnitId(office.getId(),affairCasualtyReport);
				statistic.setUnitName(office.getName());
				statistic.setUnitId(office.getId());
				list.add(statistic);
			}
		}else{
			AffairCasualtyReportStatistic statistic = affairCasualtyReportDao.findOneTypeByUnitId(affairCasualtyReport.getAffirmDepId(),affairCasualtyReport);
			statistic.setUnitName(affairCasualtyReport.getAffirmDep());
			statistic.setUnitId(affairCasualtyReport.getAffirmDepId());
			list.add(statistic);
		}
		return list;
	}

	public Integer sum(List<AffairCasualtyReportStatistic> statistic){
		Integer sum = 0;
		for (AffairCasualtyReportStatistic s:statistic) {
			sum+=s.getOneTypeNum();
		}
		return sum;
	}
}