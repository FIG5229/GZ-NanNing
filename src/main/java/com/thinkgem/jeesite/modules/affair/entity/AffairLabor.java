/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.affair.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 劳资Entity
 * @author cecil.li
 * @version 2020-01-19
 */
public class AffairLabor extends DataEntity<AffairLabor> {
	
	private static final long serialVersionUID = 1L;
    @ExcelField(title = "单位", type = 0, align = 2, sort = 0)
	private String unit;		// 单位
	private String unitId;		// 单位id
    @ExcelField(title = "年度", type = 0, align = 2, sort = 1)
	private String year;		// 年度
    @ExcelField(title = "月度", type = 0, align = 2, sort = 2)
	private String month;		// 月度
    @ExcelField(title = "姓名", type = 0, align = 2, sort = 3)
	private String name;		// 姓名
    @ExcelField(title = "身份证号", type = 0, align = 2, sort = 4)
	private String idNumber;		// 身份证号
    @ExcelField(title = "工资对应行政级别", type = 0, align = 2, sort = 5)
	private String level;		// 工资对应行政级别
    @ExcelField(title = "合计", type = 0, align = 2, sort = 6)
	private Double sum;		// 合计
    @ExcelField(title = "职务工资、试用期工资", type = 0, align = 2, sort = 7)
	private Double jbSalary;		// 基本工资-职务工资、试用期工资
    @ExcelField(title = "级别工资", type = 0, align = 2, sort = 8)
	private Double jbGradeSalary;		// 基本工资-级别工资
    @ExcelField(title = "国家统一的津贴补贴", type = 0, align = 2, sort = 9)
	private Double gjSum;		// 国家统一的津贴补贴
    @ExcelField(title = "加班补贴", type = 0, align = 2, sort = 10)
	private Double jiabanAllowance;		// 1、加班补贴
    @ExcelField(title = "警衔津贴", type = 0, align = 2, sort = 11)
	private Double jingxianAllowance;		// 2、警衔津贴
    @ExcelField(title = "执勤岗位津贴", type = 0, align = 2, sort = 12)
	private Double zhiqinAllowance;		// 3、执勤岗位津贴
    @ExcelField(title = "艰苦边远地区津贴", type = 0, align = 2, sort = 13)
	private Double jkbyAllowance;		// 4、艰苦边远地区津贴
    @ExcelField(title = "住宅公务电话费", type = 0, align = 2, sort = 14)
	private Double telephoneFee;		// 5、住宅公务电话费
    @ExcelField(title = "移动通讯费补贴", type = 0, align = 2, sort = 15)
	private Double mobileFee;		// 6、移动通讯费补贴
    @ExcelField(title = "禁食猪肉补贴", type = 0, align = 2, sort = 16)
	private Double jszrAllowance;		// 7、禁食猪肉补贴
    @ExcelField(title = "女同志卫生费", type = 0, align = 2, sort = 17)
	private Double nvHygieneFee;		// 8、女同志卫生费
    @ExcelField(title = "独生子女父母奖励", type = 0, align = 2, sort = 18)
	private Double onlyChildAllowance;		// 9、独生子女父母奖励
    @ExcelField(title = "防暑降温费", type = 0, align = 2, sort = 19)
	private Double fangshuAllowance;		// 10、防暑降温费
    @ExcelField(title = "信访工作人员岗位津贴", type = 0, align = 2, sort = 20)
	private Double xinfangAllowance;		// 11、信访工作人员岗位津贴
    @ExcelField(title = "1993年工改保留补贴", type = 0, align = 2, sort = 21)
	private Double gonggai1993Allowance;		// 12、1993年工改保留补贴
    @ExcelField(title = "西藏特殊津贴", type = 0, align = 2, sort = 22)
	private Double xizangAllowance;		// 13、西藏特殊津贴
    @ExcelField(title = "高海拔地区折算工龄补贴", type = 0, align = 2, sort = 23)
	private Double highAltitudeAllowance;		// 14、高海拔地区折算工龄补贴
    @ExcelField(title = "新疆保留地区补贴", type = 0, align = 2, sort = 24)
	private Double xinjiangAllowance;		// 15、新疆保留地区补贴
    @ExcelField(title = "特区津贴", type = 0, align = 2, sort = 25)
	private Double sarAllowance;		// 16、特区津贴
    @ExcelField(title = "乡镇工作补贴", type = 0, align = 2, sort = 26)
	private Double townshipAllowance;		// 17、乡镇工作补贴
    @ExcelField(title = "增不抵缴临时性补贴", type = 0, align = 2, sort = 27)
	private Double linshiAllowance;		// 18、增不抵缴临时性补贴
    @ExcelField(title = "", type = 0, align = 2, sort = 28)
	private Double guojia19;		// 19、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 29)
	private Double guojia20;		// 20、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 30)
	private Double guojia21;		// 21、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 31)
	private Double guojia22;		// 22、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 32)
	private Double guojia23;		// 23、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 33)
	private Double guojia24;		// 24、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 34)
	private Double guojia25;		// 25、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 35)
	private Double guojia26;		// 26、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 36)
	private Double guojia27;		// 27、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 37)
	private Double guojia28;		// 28、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 38)
	private Double guojia29;		// 29、国家补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 39)
	private Double guojia30;		// 30、国家补贴空白行
    @ExcelField(title = "规范津贴补贴", type = 0, align = 2, sort = 40)
	private Double guifanSum;		// 规范津贴补贴
    @ExcelField(title = "工作性津贴", type = 0, align = 2, sort = 41)
	private Double workingAllowance;		// 1、工作性津贴
    @ExcelField(title = "生活性津贴", type = 0, align = 2, sort = 42)
	private Double livingAllowance;		// 2、生活性津贴
    @ExcelField(title = "改革性补贴", type = 0, align = 2, sort = 43)
	private Double gaigeSum;		// 改革性补贴
    @ExcelField(title = "住宅取暖补贴", type = 0, align = 2, sort = 44)
	private Double zhuzhaiAllowance;		// 1、住宅取暖补贴
    @ExcelField(title = "住房提租补贴", type = 0, align = 2, sort = 45)
	private Double zhufangAllowance;		// 2、住房提租补贴
    @ExcelField(title = "物业服务补贴", type = 0, align = 2, sort = 46)
	private Double wuyeAllowance;		// 3、物业服务补贴
    @ExcelField(title = "上下班交通补贴", type = 0, align = 2, sort = 47)
	private Double jiaotongAllowance;		// 4、上下班交通补贴
    @ExcelField(title = "改革性补贴", type = 0, align = 2, sort = 48)
	private Double gaigeAllowance;		// 5、改革性补贴
    @ExcelField(title = "", type = 0, align = 2, sort = 49)
	private Double gaige6;		// 6、改革性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 50)
	private Double gaige7;		// 7、改革性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 51)
	private Double gaige8;		// 8、改革性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 52)
	private Double gaige9;		// 9、改革性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 53)
	private Double gaige10;		// 10、改革性补贴空白行
    @ExcelField(title = "奖励性补贴和其他", type = 0, align = 2, sort = 54)
	private Double rewardSum;		// 奖励性补贴和其他
    @ExcelField(title = "乘务补贴（含高原）", type = 0, align = 2, sort = 55)
	private Double chengwuAllowance;		// 1、乘务补贴（含高原）
    @ExcelField(title = "线路津贴", type = 0, align = 2, sort = 56)
	private Double xianluAllowance;		// 2、线路津贴
    @ExcelField(title = "安全治安挂钩考核", type = 0, align = 2, sort = 57)
	private Double anquanAllowance;		// 3、安全治安挂钩考核
    @ExcelField(title = "精神文明奖", type = 0, align = 2, sort = 58)
	private Double jingshenAllowance;		// 4、精神文明奖
    @ExcelField(title = "人民警察奖励", type = 0, align = 2, sort = 59)
	private Double jingchaAllowance;		// 5、人民警察奖励
    @ExcelField(title = "公务员奖励", type = 0, align = 2, sort = 60)
	private Double gongwuyuanAllowance;		// 6、公务员奖励
    @ExcelField(title = "讲课费", type = 0, align = 2, sort = 61)
	private Double jiangkeAllowance;		// 7、讲课费
    @ExcelField(title = "", type = 0, align = 2, sort = 62)
	private Double reward8;		// 8、奖励性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 63)
	private Double reward9;		// 9、奖励性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 64)
	private Double reward10;		// 10、奖励性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 65)
	private Double reward11;		// 11、奖励性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 66)
	private Double reward12;		// 12、奖励性补贴空白行
    @ExcelField(title = "", type = 0, align = 2, sort = 67)
	private Double reward13;		// 13、奖励性补贴空白行
    @ExcelField(title = "往年减员补发平均额", type = 0, align = 2, sort = 68)
	private Double jianyuanAllowance;		// 14、往年减员补发平均额
    @ExcelField(title = "补发往年工资及津补", type = 0, align = 2, sort = 69)
	private Double gongziAllowance;		// 15、补发往年工资及津补
    @ExcelField(title = "年终一次性将近", type = 0, align = 2, sort = 70)
	private Double yearEndAwards;		// 年终一次性将近
    @ExcelField(title = "社会保险合计", type = 0, align = 2, sort = 71)
	private Double baoxianSum;		// 社会保险合计
    @ExcelField(title = "基本养老保险", type = 0, align = 2, sort = 72)
	private Double baseYanglaoAllowance;		// 基本养老保险
    @ExcelField(title = "职业年金", type = 0, align = 2, sort = 73)
	private Double zhiyeAllowance;		// 职业年金
    @ExcelField(title = "基本医疗保险", type = 0, align = 2, sort = 74)
	private Double baseYiliaoAllowance;		// 基本医疗保险
    @ExcelField(title = "补充医疗保险", type = 0, align = 2, sort = 75)
	private Double buchongYiliaoAllowance;		// 补充医疗保险
    @ExcelField(title = "生育保险等", type = 0, align = 2, sort = 76)
	private Double shengyuAllowance;		// 生育保险等
    @ExcelField(title = "住房公积金", type = 0, align = 2, sort = 77)
	private Double gongjijin;		// 住房公积金
    @ExcelField(title = "个人所得税", type = 0, align = 2, sort = 78)
	private Double personalIncomeTax;		// 个人所得税
    @ExcelField(title = "所在铁路公安局", type = 0, align = 2, sort = 79)
	private String whereGonganju;		// 所在铁路公安局
    @ExcelField(title = "所在公安处（局机关、直属支队）", type = 0, align = 2, sort = 80)
	private String whereGonganchu;		// 所在公安处（局机关、直属支队）
    @ExcelField(title = "部门", type = 0, align = 2, sort = 81)
	private String department;		// 部门
    @ExcelField(title = "劳统人员分类", type = 0, align = 2, sort = 82)
	private String personnelType;		// 劳统人员分类
    @ExcelField(title = "当年接收毕业生、复转兵人标识", type = 0, align = 2, sort = 83, dictType="yes_no")
	private String isLogo;		// 当年接收毕业生、复转兵人标识
    @ExcelField(title = "备注", type = 0, align = 2, sort = 84)
	private String remark;		// 备注
	private String createOrgId;		// 创建者机构id
	private String createIdNo;		// 创建者身份证号
	private String updateOrgId;		// 更新者机构id
	private String updateIdNo;		// 更新者身份证号
    private String treeId;


    public AffairLabor() {
		super();
	}

	public AffairLabor(String id){
		super(id);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getIsLogo() {
		return isLogo;
	}

	public void setIsLogo(String isLogo) {
		this.isLogo = isLogo;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}
	
	public String getCreateIdNo() {
		return createIdNo;
	}

	public void setCreateIdNo(String createIdNo) {
		this.createIdNo = createIdNo;
	}
	
	public String getUpdateOrgId() {
		return updateOrgId;
	}

	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	
	public String getUpdateIdNo() {
		return updateIdNo;
	}

	public void setUpdateIdNo(String updateIdNo) {
		this.updateIdNo = updateIdNo;
	}

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getJbSalary() {
        return jbSalary;
    }

    public void setJbSalary(Double jbSalary) {
        this.jbSalary = jbSalary;
    }

    public Double getJbGradeSalary() {
        return jbGradeSalary;
    }

    public void setJbGradeSalary(Double jbGradeSalary) {
        this.jbGradeSalary = jbGradeSalary;
    }

    public Double getGjSum() {
        return gjSum;
    }

    public void setGjSum(Double gjSum) {
        this.gjSum = gjSum;
    }

    public Double getJiabanAllowance() {
        return jiabanAllowance;
    }

    public void setJiabanAllowance(Double jiabanAllowance) {
        this.jiabanAllowance = jiabanAllowance;
    }

    public Double getJingxianAllowance() {
        return jingxianAllowance;
    }

    public void setJingxianAllowance(Double jingxianAllowance) {
        this.jingxianAllowance = jingxianAllowance;
    }

    public Double getZhiqinAllowance() {
        return zhiqinAllowance;
    }

    public void setZhiqinAllowance(Double zhiqinAllowance) {
        this.zhiqinAllowance = zhiqinAllowance;
    }

    public Double getJkbyAllowance() {
        return jkbyAllowance;
    }

    public void setJkbyAllowance(Double jkbyAllowance) {
        this.jkbyAllowance = jkbyAllowance;
    }

    public Double getTelephoneFee() {
        return telephoneFee;
    }

    public void setTelephoneFee(Double telephoneFee) {
        this.telephoneFee = telephoneFee;
    }

    public Double getMobileFee() {
        return mobileFee;
    }

    public void setMobileFee(Double mobileFee) {
        this.mobileFee = mobileFee;
    }

    public Double getJszrAllowance() {
        return jszrAllowance;
    }

    public void setJszrAllowance(Double jszrAllowance) {
        this.jszrAllowance = jszrAllowance;
    }

    public Double getNvHygieneFee() {
        return nvHygieneFee;
    }

    public void setNvHygieneFee(Double nvHygieneFee) {
        this.nvHygieneFee = nvHygieneFee;
    }

    public Double getOnlyChildAllowance() {
        return onlyChildAllowance;
    }

    public void setOnlyChildAllowance(Double onlyChildAllowance) {
        this.onlyChildAllowance = onlyChildAllowance;
    }

    public Double getFangshuAllowance() {
        return fangshuAllowance;
    }

    public void setFangshuAllowance(Double fangshuAllowance) {
        this.fangshuAllowance = fangshuAllowance;
    }

    public Double getXinfangAllowance() {
        return xinfangAllowance;
    }

    public void setXinfangAllowance(Double xinfangAllowance) {
        this.xinfangAllowance = xinfangAllowance;
    }

    public Double getGonggai1993Allowance() {
        return gonggai1993Allowance;
    }

    public void setGonggai1993Allowance(Double gonggai1993Allowance) {
        this.gonggai1993Allowance = gonggai1993Allowance;
    }

    public Double getXizangAllowance() {
        return xizangAllowance;
    }

    public void setXizangAllowance(Double xizangAllowance) {
        this.xizangAllowance = xizangAllowance;
    }

    public Double getHighAltitudeAllowance() {
        return highAltitudeAllowance;
    }

    public void setHighAltitudeAllowance(Double highAltitudeAllowance) {
        this.highAltitudeAllowance = highAltitudeAllowance;
    }

    public Double getXinjiangAllowance() {
        return xinjiangAllowance;
    }

    public void setXinjiangAllowance(Double xinjiangAllowance) {
        this.xinjiangAllowance = xinjiangAllowance;
    }

    public Double getSarAllowance() {
        return sarAllowance;
    }

    public void setSarAllowance(Double sarAllowance) {
        this.sarAllowance = sarAllowance;
    }

    public Double getTownshipAllowance() {
        return townshipAllowance;
    }

    public void setTownshipAllowance(Double townshipAllowance) {
        this.townshipAllowance = townshipAllowance;
    }

    public Double getLinshiAllowance() {
        return linshiAllowance;
    }

    public void setLinshiAllowance(Double linshiAllowance) {
        this.linshiAllowance = linshiAllowance;
    }

    public Double getGuojia19() {
        return guojia19;
    }

    public void setGuojia19(Double guojia19) {
        this.guojia19 = guojia19;
    }

    public Double getGuojia20() {
        return guojia20;
    }

    public void setGuojia20(Double guojia20) {
        this.guojia20 = guojia20;
    }

    public Double getGuojia21() {
        return guojia21;
    }

    public void setGuojia21(Double guojia21) {
        this.guojia21 = guojia21;
    }

    public Double getGuojia22() {
        return guojia22;
    }

    public void setGuojia22(Double guojia22) {
        this.guojia22 = guojia22;
    }

    public Double getGuojia23() {
        return guojia23;
    }

    public void setGuojia23(Double guojia23) {
        this.guojia23 = guojia23;
    }

    public Double getGuojia24() {
        return guojia24;
    }

    public void setGuojia24(Double guojia24) {
        this.guojia24 = guojia24;
    }

    public Double getGuojia25() {
        return guojia25;
    }

    public void setGuojia25(Double guojia25) {
        this.guojia25 = guojia25;
    }

    public Double getGuojia26() {
        return guojia26;
    }

    public void setGuojia26(Double guojia26) {
        this.guojia26 = guojia26;
    }

    public Double getGuojia27() {
        return guojia27;
    }

    public void setGuojia27(Double guojia27) {
        this.guojia27 = guojia27;
    }

    public Double getGuojia28() {
        return guojia28;
    }

    public void setGuojia28(Double guojia28) {
        this.guojia28 = guojia28;
    }

    public Double getGuojia29() {
        return guojia29;
    }

    public void setGuojia29(Double guojia29) {
        this.guojia29 = guojia29;
    }

    public Double getGuojia30() {
        return guojia30;
    }

    public void setGuojia30(Double guojia30) {
        this.guojia30 = guojia30;
    }

    public Double getGuifanSum() {
        return guifanSum;
    }

    public void setGuifanSum(Double guifanSum) {
        this.guifanSum = guifanSum;
    }

    public Double getWorkingAllowance() {
        return workingAllowance;
    }

    public void setWorkingAllowance(Double workingAllowance) {
        this.workingAllowance = workingAllowance;
    }

    public Double getLivingAllowance() {
        return livingAllowance;
    }

    public void setLivingAllowance(Double livingAllowance) {
        this.livingAllowance = livingAllowance;
    }

    public Double getGaigeSum() {
        return gaigeSum;
    }

    public void setGaigeSum(Double gaigeSum) {
        this.gaigeSum = gaigeSum;
    }

    public Double getZhuzhaiAllowance() {
        return zhuzhaiAllowance;
    }

    public void setZhuzhaiAllowance(Double zhuzhaiAllowance) {
        this.zhuzhaiAllowance = zhuzhaiAllowance;
    }

    public Double getZhufangAllowance() {
        return zhufangAllowance;
    }

    public void setZhufangAllowance(Double zhufangAllowance) {
        this.zhufangAllowance = zhufangAllowance;
    }

    public Double getWuyeAllowance() {
        return wuyeAllowance;
    }

    public void setWuyeAllowance(Double wuyeAllowance) {
        this.wuyeAllowance = wuyeAllowance;
    }

    public Double getJiaotongAllowance() {
        return jiaotongAllowance;
    }

    public void setJiaotongAllowance(Double jiaotongAllowance) {
        this.jiaotongAllowance = jiaotongAllowance;
    }

    public Double getGaigeAllowance() {
        return gaigeAllowance;
    }

    public void setGaigeAllowance(Double gaigeAllowance) {
        this.gaigeAllowance = gaigeAllowance;
    }

    public Double getGaige6() {
        return gaige6;
    }

    public void setGaige6(Double gaige6) {
        this.gaige6 = gaige6;
    }

    public Double getGaige7() {
        return gaige7;
    }

    public void setGaige7(Double gaige7) {
        this.gaige7 = gaige7;
    }

    public Double getGaige8() {
        return gaige8;
    }

    public void setGaige8(Double gaige8) {
        this.gaige8 = gaige8;
    }

    public Double getGaige9() {
        return gaige9;
    }

    public void setGaige9(Double gaige9) {
        this.gaige9 = gaige9;
    }

    public Double getGaige10() {
        return gaige10;
    }

    public void setGaige10(Double gaige10) {
        this.gaige10 = gaige10;
    }

    public Double getRewardSum() {
        return rewardSum;
    }

    public void setRewardSum(Double rewardSum) {
        this.rewardSum = rewardSum;
    }

    public Double getChengwuAllowance() {
        return chengwuAllowance;
    }

    public void setChengwuAllowance(Double chengwuAllowance) {
        this.chengwuAllowance = chengwuAllowance;
    }

    public Double getXianluAllowance() {
        return xianluAllowance;
    }

    public void setXianluAllowance(Double xianluAllowance) {
        this.xianluAllowance = xianluAllowance;
    }

    public Double getAnquanAllowance() {
        return anquanAllowance;
    }

    public void setAnquanAllowance(Double anquanAllowance) {
        this.anquanAllowance = anquanAllowance;
    }

    public Double getJingshenAllowance() {
        return jingshenAllowance;
    }

    public void setJingshenAllowance(Double jingshenAllowance) {
        this.jingshenAllowance = jingshenAllowance;
    }

    public Double getJingchaAllowance() {
        return jingchaAllowance;
    }

    public void setJingchaAllowance(Double jingchaAllowance) {
        this.jingchaAllowance = jingchaAllowance;
    }

    public Double getGongwuyuanAllowance() {
        return gongwuyuanAllowance;
    }

    public void setGongwuyuanAllowance(Double gongwuyuanAllowance) {
        this.gongwuyuanAllowance = gongwuyuanAllowance;
    }

    public Double getJiangkeAllowance() {
        return jiangkeAllowance;
    }

    public void setJiangkeAllowance(Double jiangkeAllowance) {
        this.jiangkeAllowance = jiangkeAllowance;
    }

    public Double getReward8() {
        return reward8;
    }

    public void setReward8(Double reward8) {
        this.reward8 = reward8;
    }

    public Double getReward9() {
        return reward9;
    }

    public void setReward9(Double reward9) {
        this.reward9 = reward9;
    }

    public Double getReward10() {
        return reward10;
    }

    public void setReward10(Double reward10) {
        this.reward10 = reward10;
    }

    public Double getReward11() {
        return reward11;
    }

    public void setReward11(Double reward11) {
        this.reward11 = reward11;
    }

    public Double getReward12() {
        return reward12;
    }

    public void setReward12(Double reward12) {
        this.reward12 = reward12;
    }

    public Double getReward13() {
        return reward13;
    }

    public void setReward13(Double reward13) {
        this.reward13 = reward13;
    }

    public Double getJianyuanAllowance() {
        return jianyuanAllowance;
    }

    public void setJianyuanAllowance(Double jianyuanAllowance) {
        this.jianyuanAllowance = jianyuanAllowance;
    }

    public Double getGongziAllowance() {
        return gongziAllowance;
    }

    public void setGongziAllowance(Double gongziAllowance) {
        this.gongziAllowance = gongziAllowance;
    }

    public Double getYearEndAwards() {
        return yearEndAwards;
    }

    public void setYearEndAwards(Double yearEndAwards) {
        this.yearEndAwards = yearEndAwards;
    }

    public Double getBaoxianSum() {
        return baoxianSum;
    }

    public void setBaoxianSum(Double baoxianSum) {
        this.baoxianSum = baoxianSum;
    }

    public Double getBaseYanglaoAllowance() {
        return baseYanglaoAllowance;
    }

    public void setBaseYanglaoAllowance(Double baseYanglaoAllowance) {
        this.baseYanglaoAllowance = baseYanglaoAllowance;
    }

    public Double getZhiyeAllowance() {
        return zhiyeAllowance;
    }

    public void setZhiyeAllowance(Double zhiyeAllowance) {
        this.zhiyeAllowance = zhiyeAllowance;
    }

    public Double getBaseYiliaoAllowance() {
        return baseYiliaoAllowance;
    }

    public void setBaseYiliaoAllowance(Double baseYiliaoAllowance) {
        this.baseYiliaoAllowance = baseYiliaoAllowance;
    }

    public Double getBuchongYiliaoAllowance() {
        return buchongYiliaoAllowance;
    }

    public void setBuchongYiliaoAllowance(Double buchongYiliaoAllowance) {
        this.buchongYiliaoAllowance = buchongYiliaoAllowance;
    }

    public Double getShengyuAllowance() {
        return shengyuAllowance;
    }

    public void setShengyuAllowance(Double shengyuAllowance) {
        this.shengyuAllowance = shengyuAllowance;
    }

    public Double getGongjijin() {
        return gongjijin;
    }

    public void setGongjijin(Double gongjijin) {
        this.gongjijin = gongjijin;
    }

    public Double getPersonalIncomeTax() {
        return personalIncomeTax;
    }

    public void setPersonalIncomeTax(Double personalIncomeTax) {
        this.personalIncomeTax = personalIncomeTax;
    }

    public String getWhereGonganju() {
        return whereGonganju;
    }

    public void setWhereGonganju(String whereGonganju) {
        this.whereGonganju = whereGonganju;
    }

    public String getWhereGonganchu() {
        return whereGonganchu;
    }

    public void setWhereGonganchu(String whereGonganchu) {
        this.whereGonganchu = whereGonganchu;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }
}