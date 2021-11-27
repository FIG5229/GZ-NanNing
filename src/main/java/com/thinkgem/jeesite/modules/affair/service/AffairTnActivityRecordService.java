/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.affair.dao.AffairTnActivityRecordDao;
import com.thinkgem.jeesite.modules.affair.entity.AffairTnActivityRecord;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 团内活动记录Service
 * @author cecil.li
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class AffairTnActivityRecordService extends CrudService<AffairTnActivityRecordDao, AffairTnActivityRecord> {

	@Autowired
	private AffairTnActivityRecordDao affairTnActivityRecordDao;
	public AffairTnActivityRecord get(String id) {
		return super.get(id);
	}
	
	public List<AffairTnActivityRecord> findList(AffairTnActivityRecord affairTnActivityRecord) {
		affairTnActivityRecord.setUserId(UserUtils.getUser().getId());
        affairTnActivityRecord.setOfficeId(UserUtils.getUser().getOffice().getId());
        affairTnActivityRecord.setCreateBy(UserUtils.getUser());
		return super.findList(affairTnActivityRecord);
	}
	/**
	 * 批量提交
	 * @param ids
	 */
	public List<AffairTnActivityRecord> findByIds(List<String> ids){
		List<AffairTnActivityRecord> list = affairTnActivityRecordDao.findByIds(ids);
		return list;
	}
	public Page<AffairTnActivityRecord> findPage(Page<AffairTnActivityRecord> page, AffairTnActivityRecord affairTnActivityRecord) {
		affairTnActivityRecord.setUserId(UserUtils.getUser().getId());
        affairTnActivityRecord.setOfficeId(UserUtils.getUser().getOffice().getId());
        affairTnActivityRecord.setCreateBy(UserUtils.getUser());
		return super.findPage(page, affairTnActivityRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AffairTnActivityRecord affairTnActivityRecord) {
		affairTnActivityRecord.setContent(StringEscapeUtils.unescapeHtml4(affairTnActivityRecord.getContent()));
		if (affairTnActivityRecord.getCheckType()==null||"".equals(affairTnActivityRecord.getCheckType())){
			affairTnActivityRecord.setCheckType("1");
		}
		super.save(affairTnActivityRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(AffairTnActivityRecord affairTnActivityRecord) {
		super.delete(affairTnActivityRecord);
	}

	public Map<String, Object> findInfoByCreateOrgId(String id) {
		return dao.findInfoByCreateOrgId(id);
	}

	public Map<String, Object> findInfoByCreateOrgIds(List<String> ids) {
		return dao.findInfoByCreateOrgIds(ids);
	}

	public List<Map<String, String>> countActivity(String dateType, Integer year, String dateStart, String dateEnd, String month) {
		String officeId =UserUtils.getUser().getOffice().getId();

		Date startDate = DateUtils.parseDate(dateStart);
		Date endDate = DateUtils.parseDate(dateEnd);
		if (dateType.equals("1")) {    //月度
			year = null;
			startDate = null;
			endDate = null;
		} else if (dateType.equals("3")) {    //时间段
			year = null;
			month = null;
		} else {    //年度
			month = null;
			startDate = null;
			endDate = null;
		}
		return dao.countActivity(officeId,year,startDate,endDate,month);
	}

    public List<Map<String, String>> countTopActivity(String dateType, Integer year, String dateStart, String dateEnd, String month) {
        Date startDate = DateUtils.parseDate(dateStart);
        Date endDate = DateUtils.parseDate(dateEnd);
        if (dateType.equals("1")) {    //月度
            year = null;
            startDate = null;
            endDate = null;
        } else if (dateType.equals("3")) {    //时间段
            year = null;
            month = null;
        } else {    //年度
            month = null;
            startDate = null;
            endDate = null;
        }
        return dao.countTopActivity(year,startDate,endDate,month);
    }

    /**
     * 统计分析 公司及以下团内活动明细
     * @param affairTnActivityRecordPage
     * @param affairTnActivityRecord
     * @return
     */
    public Page<AffairTnActivityRecord> findActivityDetailPage(Page<AffairTnActivityRecord> affairTnActivityRecordPage, AffairTnActivityRecord affairTnActivityRecord) {
        String dateType = Optional.ofNullable(affairTnActivityRecord.getDateType()).orElseGet(() -> {return "";});
        if (dateType.equals("1")){	//月度查询
            affairTnActivityRecord.setYear(null);
            affairTnActivityRecord.setBeginDate(null);
            affairTnActivityRecord.setEndDate(null);
        }else if (dateType.equals("3")){	//时间段查询
            affairTnActivityRecord.setBeginDate(DateUtils.parseDate(affairTnActivityRecord.getDateStart()));
            affairTnActivityRecord.setEndDate(DateUtils.parseDate(affairTnActivityRecord.getDateEnd()));
        }else {	//年度查询
            affairTnActivityRecord.setMonth(null);
            affairTnActivityRecord.setBeginDate(null);
            affairTnActivityRecord.setEndDate(null);
        }
        affairTnActivityRecord.setPage(affairTnActivityRecordPage);
        affairTnActivityRecordPage.setList(dao.findActivityList(affairTnActivityRecord));
        return affairTnActivityRecordPage;
    }

    /**
     * 统计分析 公司级团内活动
     * @param page
     * @param affairTnActivityRecord
     * @return
     */
    public Page<AffairTnActivityRecord> findTopActivityDetailPage(Page<AffairTnActivityRecord> page, AffairTnActivityRecord affairTnActivityRecord) {

        String dateType = Optional.ofNullable(affairTnActivityRecord.getDateType()).orElseGet(() -> {return "";});
        if (dateType.equals("1")){	//月度查询
            affairTnActivityRecord.setYear(null);
            affairTnActivityRecord.setBeginDate(null);
            affairTnActivityRecord.setEndDate(null);
        }else if (dateType.equals("3")){	//时间段查询
            affairTnActivityRecord.setBeginDate(DateUtils.parseDate(affairTnActivityRecord.getDateStart()));
            affairTnActivityRecord.setEndDate(DateUtils.parseDate(affairTnActivityRecord.getDateEnd()));
        }else {	//年度查询
            affairTnActivityRecord.setMonth(null);
            affairTnActivityRecord.setBeginDate(null);
            affairTnActivityRecord.setEndDate(null);
        }
        switch (affairTnActivityRecord.getLabel()){
            case "南宁处":
                affairTnActivityRecord.setLabel("34");
                break;
            case "北海处":
                affairTnActivityRecord.setLabel("156");
                break;
            case "柳州处":
                affairTnActivityRecord.setLabel("95");
                break;
            case "局机关":
                affairTnActivityRecord.setLabel("top");
                break;
            default:

                break;
        }

        affairTnActivityRecord.setPage(page);
        page.setList(dao.findTopActivityList(affairTnActivityRecord));
        return page;
    }

    public  List<String> selectAllYear(){
        return affairTnActivityRecordDao.selectAllYear();
    }

    public  List<String> selectAllMonth(){
        return affairTnActivityRecordDao.selectAllMonth();
    }

    public Integer selectNumber(String time,String unitId){
        return affairTnActivityRecordDao.selectNumber(time,unitId);
    }
    public Integer selectNumberPass(String time,String unitId){
        return affairTnActivityRecordDao.selectNumberPass(time,unitId);
    }

    public int selectTpJkcNumber(String yearL, String yearT, String id) {
        return affairTnActivityRecordDao.selectTpJkcNumber(yearL,yearT,id);
    }
    public int selectTpJkcNumberYear(String yearL, String yearT, String id) {
        return affairTnActivityRecordDao.selectTpJkcNumberYear(yearL,yearT,id);
    }

    public int selectTpNumber(String yearL, String yearT, String id) {
        return affairTnActivityRecordDao.selectTpNumber(yearL,yearT,id);
    }

    public int selectTpsbNumber(String yearL, String yearT, String id) {
        return affairTnActivityRecordDao.selectTpsbNumber(yearL,yearT,id);
    }

    public int selectJkcNum(String s, String yearL, String yearT) {
        return affairTnActivityRecordDao.selectJkcNum(s,yearL,yearT);
    }

    public int selectJkcNumYear(String chuId, String yearL, String yearT) {
        return affairTnActivityRecordDao.selectJkcNumYear(chuId,yearL,yearT);
    }

    //被上级团组织采用24条（北海处10条）
    public int selectJkcIsPushNumYear(String chuId, String yearL, String yearT) {
        return affairTnActivityRecordDao.selectJkcIsPushNumYear(chuId,yearL,yearT);
    }
}