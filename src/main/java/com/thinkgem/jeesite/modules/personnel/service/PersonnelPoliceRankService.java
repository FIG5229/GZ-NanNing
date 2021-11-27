/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.personnel.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.TimeUtils;
import com.thinkgem.jeesite.modules.personnel.dao.PersonnelPoliceRankDao;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelBase;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelJob;
import com.thinkgem.jeesite.modules.personnel.entity.PersonnelPoliceRank;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 警衔信息管理Service
 * @author cecil.li
 * @version 2019-11-09
 */
@Service
@Transactional(readOnly = true)
public class PersonnelPoliceRankService extends CrudService<PersonnelPoliceRankDao, PersonnelPoliceRank> {

	@Autowired
	private PersonnelBaseService personnelBaseService;

	@Autowired
	private PersonnelJobService personnelJobService;

	@Autowired
	private PersonnelPoliceRankDao personnelPoliceRankDao;


	public PersonnelPoliceRank get(String id) {
		return super.get(id);
	}
	
	public List<PersonnelPoliceRank> findList(PersonnelPoliceRank personnelPoliceRank) {
		return super.findList(personnelPoliceRank);
	}
	
	public Page<PersonnelPoliceRank> findPage(Page<PersonnelPoliceRank> page, PersonnelPoliceRank personnelPoliceRank) {
		personnelPoliceRank.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
		Page<PersonnelPoliceRank> policeRankPage = super.findPage(page, personnelPoliceRank);
		List<PersonnelPoliceRank> policeRankList = policeRankPage.getList();
		List<PersonnelPoliceRank> newList = new ArrayList<>();
		for(PersonnelPoliceRank policeRank : policeRankList){
			PersonnelJob personnelJob = personnelJobService.findNewJobByIdNumber(policeRank.getIdNumber());
			PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(policeRank.getIdNumber());
			if (personnelJob!=null){
				policeRank.setJobLevel(personnelJob.getJobLevel());
				policeRank.setJobStartDate(personnelJob.getStartDate());
			}
			if (personnelBase != null) {
				policeRank.setUnit(personnelBase.getWorkunitName());
				policeRank.setPeopleName(personnelBase.getName());
				policeRank.setSex(personnelBase.getSex());
				policeRank.setBirthdayTime(personnelBase.getBirthday());
				policeRank.setWorkTime(personnelBase.getWorkDate());
			}
//			newList.add(policeRank);
		}
		policeRankPage.setList(policeRankList);
		return policeRankPage;
	}
	
	@Transactional(readOnly = false)
	public void save(PersonnelPoliceRank personnelPoliceRank) {
		super.save(personnelPoliceRank);
	}
	
	@Transactional(readOnly = false)
	public void delete(PersonnelPoliceRank personnelPoliceRank) {
		super.delete(personnelPoliceRank);
	}

	//定时任务WarningTask使用，查询距离晋升还差三个月人员信息
	//查询三月后达到 警衔晋级人员   与controller层警衔测算相比 测试工龄/在职年限  方法getCurrentAge()调整为getDiffThreeMonthCurrentAge()
	public PersonnelBase promoteBeforeTrimester(PersonnelPoliceRank personnelPoliceRank, String calculateType) {
		//根据身份证号查询相关信息 判断是否满足条件
		PersonnelBase personnelBase = personnelBaseService.findInfoByIdNumber(personnelPoliceRank.getIdNumber());
		PersonnelJob personnelJob = new PersonnelJob();
		personnelJob.setIdNumber(personnelPoliceRank.getIdNumber());
		List<PersonnelJob> personnelJobList = personnelJobService.findList(personnelJob);
		PersonnelJob job = null;
		if (personnelJobList!=null && personnelJobList.size()>0){
			job=personnelJobList.get(0);
		}else {
			job = new PersonnelJob();
		}
		String jobLevel=job.getJobLevel()==null?"":job.getJobLevel();
		//在职级年限
		int appoinment= TimeUtils.getDiffThreeMonthCurrentAge(job.getStartDate());
		//工龄
		int workingYears= TimeUtils.getDiffThreeMonthCurrentAge(personnelBase.getPublicSecurityDate());
		switch (calculateType){
			case "1":
//				首评警督
				if ("6".equals(jobLevel)){
					//副科级
					if (appoinment >= 2 && workingYears >= 16 || workingYears >= 20) {
						return personnelBase;
					}
				}
				if ("7".equals(jobLevel)){
					//正科级
					if (appoinment >= 2 && workingYears >= 10 || workingYears >= 14) {
						return personnelBase;
					}
				}
				break;
			case "2":
//				首评警司
				if ("2".equals(jobLevel) || "3".equals(jobLevel)){
					//办事员及警员
					if (workingYears >= 8){
						return  personnelBase;
					}
				}
				break;
			case "3":
//				选升警监
				boolean isOne = personnelPoliceRank.getPoliceRankLevel().equals("8");
				boolean isTwo= personnelPoliceRank.getPoliceRankLevel().equals("7");
				boolean isThree = personnelPoliceRank.getPoliceRankLevel().equals("6");
				//正处
				boolean isZhengChu = "9".equals(jobLevel);
				boolean isFuTing = "10".equals(jobLevel);
				boolean isZhengTing = "11".equals(jobLevel);
				int rankTime = TimeUtils.getDiffThreeMonthCurrentAge(personnelPoliceRank.getStartDate());

				if (isThree){
					//选升三级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengChu && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengChu && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 7) {
							return personnelBase;
						}
					}
				}
				if (isTwo){
					//选升二级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isFuTing && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isFuTing && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelBase;
						}
					}
				}
				if (isOne){
					//选升一级警监
					if ("2".equals(personnelBase.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengTing && isOne && rankTime > 2 && appoinment> 4 && workingYears > 25 || rankTime > 6) {
							return personnelBase;
						}
					}
					if ("3".equals(personnelBase.getStatus())) {
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengTing && isOne && rankTime > 4 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelBase;
						}
					}
				}
				break;
			case "4":
//				晋升警督
				if (personnelPoliceRank.getPoliceRankLevel().equals("6")){
					//三级升为二级
					if ("6".equals(jobLevel)){
						//副科
						if (appoinment>2&& workingYears>26 || workingYears>30){
							return personnelBase;
						}
					}
					if ("7".equals(jobLevel)){
						//正科
						//// FIXME: 2020/7/22 县(市)公安局和地级市下设的公安分局的局长、政委达不到一督的(为添加)

						if (appoinment>2&& workingYears>20 || workingYears>24){
							return personnelBase;
						}
					}
					if ("8".equals(jobLevel)){
						//副处
						if (appoinment>3&& workingYears>16 || workingYears>20){
							return personnelBase;
						}
					}

				}
				if (personnelPoliceRank.getPoliceRankLevel().equals("7")){
					//二级晋升为一级
					if ("7".equals(jobLevel)){
						//正科
						if (appoinment>5&& workingYears>32 || workingYears>38){
							return personnelBase;
						}
					}
					if ("8".equals(jobLevel)){
						//副处
						if (appoinment>3&& workingYears>22 || workingYears>26){
							return personnelBase;
						}
					}
					if ("9".equals(jobLevel)){
						//正处
						if (appoinment>3&& workingYears>18 || workingYears>22){
							return personnelBase;
						}
					}
				}
				break;
			case "5":
//				晋升警司
				//三级晋升为二级
				if (personnelPoliceRank.getPoliceRankLevel().equals("3")){
					//办事员及警员
					if (jobLevel.equals("2") || jobLevel.equals("3")){
						if (workingYears>12){
							return personnelBase;
						}
					}
					if (jobLevel.equals("4") || jobLevel.equals("5")){
						//科员及警长
						if (workingYears>12){
							return personnelBase;
						}
						//现任县(市)公安局股所、队长不够一司的(此条件未筛选)
					}
				}
				//二级晋升为一级
				if (personnelPoliceRank.getPoliceRankLevel().equals("2")){
					if (jobLevel.equals("4") || jobLevel.equals("5")){
						//科员及警长
						if (workingYears>18){
							return personnelBase;
						}
						//现任县(市)公安局股所、队长工龄满14年的(此条件未筛选)
					}
					if (jobLevel.equals("6")){
						//副科
						if (appoinment >2 && workingYears>8 || workingYears>12){
							return personnelBase;
						}
					}
				}
				break;
			case "6":
//				晋督培训
				break;
			case "7":
//				晋司培训
				break;

		}
		return null;
	}

	public PersonnelBase promoteBeforeTrimester2(PersonnelPoliceRank personnelPoliceRank, String calculateType,Date endDate) {
		PersonnelPoliceRank policeRank = this.calculateRank(personnelPoliceRank, calculateType, endDate);
		if(policeRank==null){
			return null;
		}else{
			//根据身份证号查询相关信息
			return personnelBaseService.findInfoByIdNumber(personnelPoliceRank.getIdNumber());
		}
	}
	public List<PersonnelPoliceRank> findInfoByIdNumber(String idNumber) {
		return personnelPoliceRankDao.findInfoByIdNumber(idNumber);
	}

	/*根据身份证号查询最新的一条数据*/
	public PersonnelPoliceRank findLastOneByIdNumber(PersonnelPoliceRank personnelPoliceRank) {
		personnelPoliceRank = personnelPoliceRankDao.findLastOneByIdNumber(personnelPoliceRank);
		if (personnelPoliceRank == null){
			personnelPoliceRank = new PersonnelPoliceRank();
		}
		return personnelPoliceRank;
	}

	/*根据身份证号查询当前记录*/
	public PersonnelPoliceRank findNowPoliceRankByIdNumber(PersonnelPoliceRank personnelPoliceRank) {
		personnelPoliceRank = personnelPoliceRankDao.findNowPoliceRankByIdNumber(personnelPoliceRank);
		if (personnelPoliceRank == null){
			personnelPoliceRank = new PersonnelPoliceRank();
		}
		return personnelPoliceRank;
	}

	/*统计警衔年统报表*/
	public List<Map<String, String>> countYearReport(PersonnelPoliceRank personnelPoliceRank) {
		return dao.countYearReport(personnelPoliceRank);
	}

	/*统计警衔变动情况*/
    public List<Map<String, String>> countRankChange(PersonnelPoliceRank personnelPoliceRank) {
		List<Dict> dicts = DictUtils.getDictList("dict_change_reason");
		List<Map<String,String>> changeList = new ArrayList<>();
		dicts.stream().forEach(dict -> {
			String changeReason = dict.getValue();
			personnelPoliceRank.setChangeReason(changeReason);
			List<Map<String,String>> map = super.dao.countRankChange(personnelPoliceRank);
			if (map==null){
				map = new ArrayList<>();
			}
			changeList.addAll(map);
		});
		return changeList;
    }

    /*晋升警衔人员名单*/
	public List<Map<String,String>> findPromotionList(PersonnelPoliceRank personnelPoliceRank) {
		/*首评警督
		if ("6".equals(jobLevel)){
			//副科级
			if (appoinment >= 2 && workingYears >= 16 || workingYears >= 20) {
				return personnelBase;
			}
		}
		if ("7".equals(jobLevel)){
			//正科级
			if (appoinment >= 2 && workingYears >= 10 || workingYears >= 14) {
				return personnelBase;
			}
		}*/
		Map<String,Date> map = new HashMap<>();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR,-2);
		Date y = c.getTime();
		map.put("fuAppoinment",y);
		map.put("zhengAppoinment",y);

		Calendar cc = Calendar.getInstance();
		cc.setTime(new Date());
		cc.add(Calendar.YEAR,-16);
		Date yy = cc.getTime();
		map.put("fuWorkingYear",yy);

		Calendar ccc = Calendar.getInstance();
		ccc.setTime(new Date());
		ccc.add(Calendar.YEAR,-20);
		Date yyy = ccc.getTime();
		map.put("fuWorkingYearOnly",yyy);

		Calendar cccc = Calendar.getInstance();
		cccc.setTime(new Date());
		cccc.add(Calendar.YEAR,-10);
		Date yyyy = cccc.getTime();
		map.put("zhengWorkingYear",yyyy);

		Calendar ccccc = Calendar.getInstance();
		ccccc.setTime(new Date());
		ccccc.add(Calendar.YEAR,-14);
		Date yyyyy = ccccc.getTime();
		map.put("zhengWorkingYearOnly",yyyyy);
		return super.dao.findPromotionList(map,personnelPoliceRank.getYears());
	}

	/**
	 * 警衔测算优化查询
	 * @param page
	 * @param personnelPoliceRank
	 * @return
	 */
	public Page<PersonnelPoliceRank> findLastPage(Page<PersonnelPoliceRank> page, PersonnelPoliceRank personnelPoliceRank) {
		personnelPoliceRank.getSqlMap().put("dsf", alterDataScopeFilter(UserUtils.getUser(), "o", "u"));
		personnelPoliceRank.setPage(page);
		List<PersonnelPoliceRank> list = dao.findLastList( personnelPoliceRank);
		List<PersonnelPoliceRank> resultList = new ArrayList<>();
		list.stream().forEach(item -> {
			if (StringUtils.isBlank(personnelPoliceRank.getCalculateType())){
				PersonnelPoliceRank rank  = new PersonnelPoliceRank();
				for (int i =1;i<=7;i++){
					rank = calculateRank(item,i+"",personnelPoliceRank.getEndFinishDate());
					if (rank!=null){
						switch (i+""){
							case "1":
							case "2":
								rank.setChangeReason("首授");
								break;
							case "3":
								rank.setChangeReason("选升");
								break;
							case "4":
							case "5":
								rank.setChangeReason("按期晋升");
								break;
							case "6":
							case "7":
								rank.setChangeReason("");
								break;
						}
						break;
					}
				}
				/*rank  = calculateRank(item,"1",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"2",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"3",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"4",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"5",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"6",personnelPoliceRank.getEndFinishDate());
				rank  = calculateRank(item,"7",personnelPoliceRank.getEndFinishDate());

				rank.setChangeReason("首授");*/
				resultList.add(rank);
			}else {
				resultList.add(calculateRank(item,personnelPoliceRank.getCalculateType(),personnelPoliceRank.getEndFinishDate()));
			}
		});
		resultList.removeAll(Collections.singleton(null));
		CacheUtils.put("policeRankList",resultList);
		page.setList(resultList);
		return page;
	}


	/**
	 * 办事员级		科员级		副科级				正科级				副处级				正处级				副厅级				正厅级
	 * 二级警员		一级警员	四级警长			二级警长			四级高级警长		二级高级警长		二级警务专员		一级警务专员
	 *      		警务技术员	三级警长			一级警长			三级高级警长		一级高级警长		警务技术二级总监	警务技术一级总监
	 *    						警务技术四级主管	警务技术二级主管	警务技术四级主任	警务技术二级主任
	 * 							警务技术三级主管	警务技术一级主管	警务技术三级主任	警务技术一级主任
	 * @param personnelPoliceRank
	 * @param calculateType
	 * @param endDate
	 * @return
	 */
	private PersonnelPoliceRank calculateRank(PersonnelPoliceRank personnelPoliceRank,String calculateType,Date endDate) {

		if (StringUtils.isBlank(personnelPoliceRank.getBaseStatus())){
			return null;
		}
		String jobLevel=personnelPoliceRank.getJobLevel();
		String policeRank = personnelPoliceRank.getPoliceRankLevel();
		if (StringUtils.isBlank(policeRank)){
			return null;
		}
		String baseStatus = personnelPoliceRank.getBaseStatus();
		//在职级年限
		int appoinment=TimeUtils.getCurrentTime(personnelPoliceRank.getJobStartDate(),endDate);
		/*当前警衔年限*/
		int jobTime = TimeUtils.getCurrentTime(personnelPoliceRank.getStartDate(),endDate);
		//工龄
		int workingYears= TimeUtils.getCurrentTime(personnelPoliceRank.getPublicSecurityDate(),endDate);
		boolean isFuKe = "24".equals(jobLevel) || "27".equals(jobLevel) || "18".equals(jobLevel) || "17".equals(jobLevel);
		boolean isZhengKe = "14".equals(jobLevel) || "34".equals(jobLevel) || "20".equals(jobLevel) || "15".equals(jobLevel);
		boolean isFuChu = "22".equals(jobLevel) || "26".equals(jobLevel) || "16".equals(jobLevel) || "19".equals(jobLevel);
		boolean isZhengChu = "32".equals(jobLevel) ||"12".equals(jobLevel) ||"警务技术一级主任".equals(jobLevel) ||"警务技术二级主任".equals(jobLevel);
		switch (calculateType){
			case "1":
				if (policeRank.equals("5")){
					//				首评警督
					if (isFuKe){
						//副科级
						if (appoinment >= 2 && workingYears >= 16 || workingYears >= 20) {
							return personnelPoliceRank;
						}
					}
					if (isZhengKe ){
						//正科级
						if (appoinment >= 2 && workingYears >= 10 || workingYears >= 14) {
							return personnelPoliceRank;
						}
					}
				}
				break;
			case "2":
				if (policeRank.equals("2")){
					//				首评警司
					if ("14".equals(jobLevel)){
						//办事员及警员
						if (workingYears >= 8){
							return  personnelPoliceRank;
						}
					}
				}
				break;
			case "3":
//				选升警监
				boolean isOne = policeRank.equals("10");
				boolean isTwo= policeRank.equals("9");
				boolean isThree = policeRank.equals("8");
				//正处
//				boolean isZhengChu = "32".equals(jobLevel) || "12".equals(jobLevel) || "20".equals(jobLevel) || "15".equals(jobLevel);
				boolean isFuTing = "二级警务专员".equals(jobLevel) || "警务技术二级总监".equals(jobLevel) ;
				boolean isZhengTing = "一级警务专员".equals(jobLevel)|| "警务技术一级总监".equals(jobLevel);
				int rankTime = TimeUtils.getCurrentTime(personnelPoliceRank.getStartDate(),endDate);

				if (isThree){
					//选升三级警监
					if ("2".equals(personnelPoliceRank.getStatus())) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengChu && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelPoliceRank;
						}
					}
					if ("3".equals(baseStatus)){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengChu && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 7) {
							return personnelPoliceRank;
						}
					}
				}
				if (isTwo){
					//选升二级警监
					if ("2".equals(baseStatus)) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isFuTing && isOne && rankTime > 3 && appoinment> 2 && workingYears > 25 || rankTime > 6) {
							return personnelPoliceRank;
						}
					}
					if ("3".equals(baseStatus)){
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isFuTing && isOne && rankTime > 5 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelPoliceRank;
						}
					}
				}
				if (isOne){
					//选升一级警监
					if ("2".equals(baseStatus)) {
						//领导	一督满3年,正处满2年,工龄满25年的；或一督满6年的。
						if (isZhengTing && isOne && rankTime > 2 && appoinment> 4 && workingYears > 25 || rankTime > 6) {
							return personnelPoliceRank;
						}
					}
					if ("3".equals(baseStatus)) {
						//非领导	一督满5年，正处满3年，工龄满35年的；或一督满7年的。
						if (isZhengTing && isOne && rankTime > 4 && appoinment> 3 && workingYears > 35 || rankTime > 6) {
							return personnelPoliceRank;
						}
					}
				}
				break;
			case "6":
			case "4":

//				晋升警督
				if (policeRank.equals("6")){

					//三级升为二级
					if (isFuKe){
						if (jobTime >= 4) {
							return personnelPoliceRank;
						}
						//副科
						if (appoinment>2&& workingYears>26 || workingYears>30){
							return personnelPoliceRank;
						}
					}
					if (isZhengKe){
						if (jobTime >=4) {
							return personnelPoliceRank;
						}
						//正科
						//// FIXME: 2020/7/22 县(市)公安局和地级市下设的公安分局的局长、政委达不到一督的(为添加)

						if (appoinment>2&& workingYears>20 || workingYears>24){
							return personnelPoliceRank;
						}
					}
					if (isFuChu  ){
						if (jobTime >= 4) {
							return personnelPoliceRank;
						}
						//副处
						if (appoinment>3&& workingYears>16 || workingYears>20){
							return personnelPoliceRank;
						}
					}

				}
				if (policeRank.equals("7")){

					//二级晋升为一级
					if (isZhengKe){
						if (jobTime>=4){
							return personnelPoliceRank;
						}
						//正科
						if (appoinment>5&& workingYears>32 || workingYears>38){
							return personnelPoliceRank;
						}
					}
					if (isFuChu){
						if (jobTime>=4){
							return personnelPoliceRank;
						}
						//副处
						if (appoinment>3&& workingYears>22 || workingYears>26){
							return personnelPoliceRank;
						}
					}
					if ("32".equals(jobLevel) ||"12".equals(jobLevel) ||"警务技术一级主任".equals(jobLevel) ||"警务技术二级主任".equals(jobLevel) ){
						if (jobTime>=4){
							return personnelPoliceRank;
						}
						//正处
						if (appoinment>3&& workingYears>18 || workingYears>22){
							return personnelPoliceRank;
						}
					}
				}
				break;
			case "7":
			case "5":
				personnelPoliceRank.setRetainReason("按期晋升");
//				晋升警司
				//三级晋升为二级
				if (policeRank.equals("3")){

					//办事员及警员
					if (jobLevel.equals("13") ){
						if (jobTime>=3){
							return personnelPoliceRank;
						}
						if (workingYears>12){
							return personnelPoliceRank;
						}
					}
					if (jobLevel.equals("33") || jobLevel.equals("21")){
						if (jobTime>=3){
							return personnelPoliceRank;
						}
						//科员及警长
						if (workingYears>12){
							return personnelPoliceRank;
						}
						//现任县(市)公安局股所、队长不够一司的(此条件未筛选)
					}
				}
				//二级晋升为一级
				if (policeRank.equals("2")){

					if (jobLevel.equals("33") || jobLevel.equals("21")){
						if (jobTime>=3){
							return personnelPoliceRank;
						}
						//科员及警长
						if (workingYears>18){
							return personnelPoliceRank;
						}
						//现任县(市)公安局股所、队长工龄满14年的(此条件未筛选)
					}
					if (isFuKe){
						if (jobTime>=3){
							return personnelPoliceRank;
						}
						//副科
						if (appoinment >2 && workingYears>8 || workingYears>12){
							return personnelPoliceRank;
						}
					}
				}
				break;
				/*同晋升一样*/
//			case "6":
//				晋督培训
//				break;
//			case "7":
//				晋司培训
//				break;

		}
		return null;
	}

	//统计分析
	public List<Map<String, Object>> findInfoByCreateOrgId(String id, Integer year , Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgId(id, year ,startDate, endDate, month);
	}

	public List<Map<String, Object>> findInfoByCreateOrgIds(List<String> ids, Integer year ,Date startDate, Date endDate, String month) {
		return dao.findInfoByCreateOrgIds(ids, year ,startDate, endDate, month);
	}

	/**
	 * 警衔统计分析
	 * @param id
	 * @param dateType
	 * @param year
	 * @param dateStart
	 * @param dateEnd
	 * @param month
	 * @return
	 */
    public List<Map<String, String>> countPoliceRank(String id, String dateType, Integer year, String dateStart, String dateEnd, String month) {
		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
    	if (dateType!=null &&dateType.equals("1")){	//月份查询
			year=null;
			startDate=null;
			endDate=null;
		}else if (dateType!=null &&dateType.equals("3")){	//时间段查询
			year=null;
			month=null;
		}else {	//年度查询
			month=null;
			startDate=null;
			endDate=null;
		}
    	return dao.countPoliceRank(id,year,startDate,endDate,month);
    }

	public Page<PersonnelPoliceRank> findChartsPoliceRankPage(Page<PersonnelPoliceRank> page, PersonnelPoliceRank personnelPoliceRank) {
		personnelPoliceRank.setPage(page);
		page.setList(dao.findChartsPoliceRankList(personnelPoliceRank));
    	return page;
	}

	//根据身份证号删除相应信息
	@Transactional(readOnly = false)
	public void deleteByIdNumbers(List<String> idNumbers) {
		dao.deleteByIdNumbers(idNumbers);
	}
}