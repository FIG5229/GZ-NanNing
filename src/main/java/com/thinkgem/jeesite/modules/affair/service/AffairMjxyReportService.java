/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.affair.dao.AffairMjxyReportDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReport;
import com.thinkgem.jeesite.modules.affair.entity.AffairMjxyReportStatistic;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 民警休养申报Service
 * @author cecil.li
 * @version 2019-11-05
 */
@Service
@Transactional(readOnly = true)
public class AffairMjxyReportService extends CrudService<AffairMjxyReportDao, AffairMjxyReport> {

	@Autowired
	private SystemService systemService;
	@Autowired
	private AffairMjxyReportDao affairMjxyReportDao;

	@Autowired
	private OfficeDao officeDao;

	public AffairMjxyReport get(String id) {
		return super.get(id);
	}
	
	public List<AffairMjxyReport> findList(AffairMjxyReport affairMjxyReport) {
		affairMjxyReport.setUserId(UserUtils.getUser().getId());
		return super.findList(affairMjxyReport);
	}

	/**
	 * 公安局只需看到各公安处和局机关单位上报休养审核人员名单，
	 * 公安处只需看到所属各所队和处机关单位上报审核人员名单，
	 * 各所队看到本所队民警上报休养审核名单
	 * @param page 分页对象
	 * @param affairMjxyReport
	 * @return
	 */
	public Page<AffairMjxyReport> findPage(Page<AffairMjxyReport> page, AffairMjxyReport affairMjxyReport) {
		String userId= UserUtils.getUser().getId();
		affairMjxyReport.setUserId(userId);
		String officeId = UserUtils.getUser().getOffice().getId();
		/*使用setRemark 存放officeId, 只能查看下级直属单位*/
		affairMjxyReport.setRemark(officeId);
//		南宁局工会
		if (userId.equals("b91d9ac0c32847c4ab6f21e910959198") || userId.equals("4c40b4dd2aee459286a37538978e6261")){
			affairMjxyReport.setRemark("1");
		}
//		柳州处
		if (userId.equals("ca07f09482154be0b10136171b19b90e") || userId.equals("bdab6b25fc694e74bf7c3d1363c0062e")){
			affairMjxyReport.setRemark("95");
		}
//		北海处
		if (userId.equals("73607645d2e040359dbcb66640f92e07") || userId.equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairMjxyReport.setRemark("156");
		}
//		南宁处
		if (userId.equals("1d6572cc99604313b93905e5ff3f47a4") || userId.equals("cb97bf7e17d549f09b51375a98a8eb55")){
			affairMjxyReport.setRemark("34");
		}
		return super.findPage(page, affairMjxyReport);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairMjxyReport affairMjxyReport) {
		if(affairMjxyReport.getCheckType()== null || "".equals(affairMjxyReport.getCheckType())){
			affairMjxyReport.setCheckType("1");
		}
		super.save(affairMjxyReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairMjxyReport affairMjxyReport) {
		super.delete(affairMjxyReport);
	}
	@Transactional(readOnly = false)

	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairMjxyReport> findByIds(List<String> ids){
		List<AffairMjxyReport> list = affairMjxyReportDao.findByIds(ids);
		return list;
	}
	/**
	 *	统计汇总的初始页面
	 * @return
	 */
	public List<AffairMjxyReportStatistic> statistic(AffairMjxyReport affairMjxyReport) {
		List<AffairMjxyReportStatistic> list = new ArrayList<AffairMjxyReportStatistic>();
		String id = UserUtils.getUser().getCompany().getId();
		if ("1".equals(id)){

			//局机关
			AffairMjxyReportStatistic juJiGuan = affairMjxyReportDao.findNumsByParentId("1",affairMjxyReport);
			juJiGuan.setUnitName("南宁铁路公安局");
			juJiGuan.setUnitId("1");
			//合计
			juJiGuan.setSum(juJiGuan.getJuGuanNei()+juJiGuan.getJuGuanWai()+juJiGuan.getLaoMo());
			list.add(juJiGuan);
			//南宁公安处
			AffairMjxyReportStatistic nanNingChu = affairMjxyReportDao.findNumsByParentId("34",affairMjxyReport);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			//合计
			nanNingChu.setSum(nanNingChu.getJuGuanNei()+nanNingChu.getJuGuanWai()+nanNingChu.getLaoMo());
			list.add(nanNingChu);
			//柳州公安处
			AffairMjxyReportStatistic liuZhouChu = affairMjxyReportDao.findNumsByParentId("95",affairMjxyReport);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			//合计
			liuZhouChu.setSum(liuZhouChu.getJuGuanNei()+liuZhouChu.getJuGuanWai()+liuZhouChu.getLaoMo());
			list.add(liuZhouChu);
			//北海公安处
			AffairMjxyReportStatistic beihaiChu = affairMjxyReportDao.findNumsByParentId("156",affairMjxyReport);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			//合计
			beihaiChu.setSum(beihaiChu.getJuGuanNei()+beihaiChu.getJuGuanWai()+beihaiChu.getLaoMo());
			list.add(beihaiChu);
		}else if("34".equals(id)){
			//南宁公安处
			AffairMjxyReportStatistic nanNingChu = affairMjxyReportDao.findNumsByParentId("34",affairMjxyReport);
			nanNingChu.setUnitName("南宁公安处");
			nanNingChu.setUnitId("34");
			//合计
			nanNingChu.setSum(nanNingChu.getJuGuanNei()+nanNingChu.getJuGuanWai()+nanNingChu.getLaoMo());
			list.add(nanNingChu);
		}else if("95".equals(id)){
			//柳州公安处
			AffairMjxyReportStatistic liuZhouChu = affairMjxyReportDao.findNumsByParentId("95",affairMjxyReport);
			liuZhouChu.setUnitName("柳州公安处");
			liuZhouChu.setUnitId("95");
			//合计
			liuZhouChu.setSum(liuZhouChu.getJuGuanNei()+liuZhouChu.getJuGuanWai()+liuZhouChu.getLaoMo());
			list.add(liuZhouChu);
		}else{
			//北海公安处
			AffairMjxyReportStatistic beihaiChu = affairMjxyReportDao.findNumsByParentId("156",affairMjxyReport);
			beihaiChu.setUnitName("北海公安处");
			beihaiChu.setUnitId("156");
			//合计
			beihaiChu.setSum(beihaiChu.getJuGuanNei()+beihaiChu.getJuGuanWai()+beihaiChu.getLaoMo());
			list.add(beihaiChu);
		}
		//集合排序
		List<AffairMjxyReportStatistic> newList = null;
		if("1".equals(affairMjxyReport.getSort()) || "3".equals(affairMjxyReport.getSort())){//升序
			if("1".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanNei)).collect(Collectors.toList());
			}else if("2".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanWai)).collect(Collectors.toList());
			}else  if("3".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getLaoMo)).collect(Collectors.toList());
			}else{
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getOther)).collect(Collectors.toList());
			}
		}else{//逆序
			if("1".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanNei).reversed()).collect(Collectors.toList());
			}else if("2".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanWai).reversed()).collect(Collectors.toList());
			}else if("3".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getLaoMo).reversed()).collect(Collectors.toList());
			}else{
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getOther).reversed()).collect(Collectors.toList());
			}
		}
		return newList;
	}

	/**
	 * 第二层统计汇总  点单位弹窗
	 * @param affairMjxyReportStatistic
	 * @param affairMjxyReport
	 * @return
	 */
	public List<AffairMjxyReportStatistic> findByUnitId(AffairMjxyReportStatistic affairMjxyReportStatistic,AffairMjxyReport affairMjxyReport) {
		List<AffairMjxyReportStatistic> list = new ArrayList<AffairMjxyReportStatistic>();
		List<Office> child = officeDao.findChildById(affairMjxyReportStatistic.getUnitId());
		for (Office office:child) {
			AffairMjxyReportStatistic statistic = affairMjxyReportDao.findByUnitId(office.getId(),affairMjxyReport);
            statistic.setSum(statistic.getJuGuanNei()+statistic.getJuGuanWai()+statistic.getLaoMo());
            statistic.setUnitName(office.getName());
            statistic.setUnitId(office.getId());
			list.add(statistic);
		}
		//集合排序
		List<AffairMjxyReportStatistic> newList = null;
		if("1".equals(affairMjxyReport.getSort()) || "3".equals(affairMjxyReport.getSort())){//升序
			if("1".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanNei)).collect(Collectors.toList());
			}else if("2".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanWai)).collect(Collectors.toList());
			}else if("3".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getLaoMo)).collect(Collectors.toList());
			}else{
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getOther)).collect(Collectors.toList());
			}
		}else{//逆序
			if("1".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanNei).reversed()).collect(Collectors.toList());
			}else if("2".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getJuGuanWai).reversed()).collect(Collectors.toList());
			}else if("3".equals(affairMjxyReport.getType())){
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getLaoMo).reversed()).collect(Collectors.toList());
			}else {
				newList = list.stream().sorted(Comparator.comparing(AffairMjxyReportStatistic::getOther).reversed()).collect(Collectors.toList());
			}
		}
		return newList;
	}

	public List<Map<String, Object>> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	/**
	 * 休养明细页面
	 * @param page
	 * @param affairMjxyReport
	 * @return
	 */
	public Page<AffairMjxyReport> tongJiMingXi(Page<AffairMjxyReport> page, AffairMjxyReport affairMjxyReport) {
		affairMjxyReport.setPage(page);
		List<AffairMjxyReport> list = affairMjxyReportDao.tongJiMingXi(affairMjxyReport);
		page.setList(list);
		return page;
	}

	/**
	 * 公安局只需看到各公安处和局机关单位上报休养审核人员名单，
	 * 公安处只需看到所属各所队和处机关单位上报审核人员名单，
	 * 各所队看到本所队民警上报休养审核名单
	 * @param affairMjxyReportPage
	 * @param affairMjxyReport
	 * @return
	 */
	public Page<AffairMjxyReport> getList(Page<AffairMjxyReport> affairMjxyReportPage, AffairMjxyReport affairMjxyReport) {
		String userId=UserUtils.getUser().getId();
		affairMjxyReport.setUserId(userId);
		String officeId = UserUtils.getUser().getOffice().getId();
		/*使用remark 存放officeId, 只能查看下级直属单位*/
		affairMjxyReport.setRemark(officeId);
//		南宁局工会
		if (userId.equals("b91d9ac0c32847c4ab6f21e910959198") || userId.equals("4c40b4dd2aee459286a37538978e6261")){
			affairMjxyReport.setRemark("1");
		}
//		柳州处
		if (userId.equals("ca07f09482154be0b10136171b19b90e") || userId.equals("bdab6b25fc694e74bf7c3d1363c0062e")){
			affairMjxyReport.setRemark("95");
		}
//		北海处
		if (userId.equals("73607645d2e040359dbcb66640f92e07") || userId.equals("abb70c88ca9d42758c489838fdd0e33b")){
			affairMjxyReport.setRemark("156");
		}
//		南宁处
		if (userId.equals("1d6572cc99604313b93905e5ff3f47a4") || userId.equals("cb97bf7e17d549f09b51375a98a8eb55")){
			affairMjxyReport.setRemark("34");
		}
		affairMjxyReportPage.setList(super.dao.getList(affairMjxyReport));
		return affairMjxyReportPage;
	}

	/*按照类型 单位 修养时间删除*/
	@Transactional(readOnly = false)
	public void deleteByType(String type, String unit, Date startDate, Date endDate,String place) {
		super.dao.deleteByType(type,unit,startDate,endDate,place);
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}