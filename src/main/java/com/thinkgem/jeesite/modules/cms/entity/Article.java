/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.cms.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;

/**
 * 文章Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Article extends DataEntity<Article> {

    public static final String DEFAULT_TEMPLATE = "frontViewArticle";
	
	private static final long serialVersionUID = 1L;
	private Category category;// 分类编号
	private String title;	// 标题
    private String link;	// 外部链接
	private String color;	// 标题颜色（red：红色；green：绿色；blue：蓝色；yellow：黄色；orange：橙色）
	private String image;	// 文章图片
	private String keywords;// 关键字
	private String description;// 描述、摘要
	private Integer weight;	// 权重，越大越靠前
	private Date weightDate;// 权重期限，超过期限，将weight设置为0
	private Integer hits;	// 点击数
	private String posid;	// 推荐位，多选（1：首页焦点图；2：栏目页文章推荐；）
    private String customContentView;	// 自定义内容视图
   	private String viewConfig;	// 视图参数
	private String appendfile; //附件

	private ArticleData articleData;	//文章副表
	
	private Date beginDate;	// 开始时间
	private Date endDate;	// 结束时间
	
	private User user;

	//推送的栏目分类
	private String[] categoryArr;

	/*统计分析使用*/
	private String dateType;
	private Integer year;
	private String dateStart;
	private String dateEnd;
	private String month;
	private String label;
	private String company;

	private String unitExamine;			//单位领导
	private String officeExamine;		//办公室	单位领导审核完后给办公室
	private String officeDirectorExamine;		//办公室主任	办公室审核后给办公室主任
	private String finalExamine;		//局领导	办公室审核完成 局领导最终审核发布
	private String unitExamineId;
	private String officeExamineId;
	private String officeDirectorExamineId;
	private String finalExamineId;
	private String checkType;

	private String isColumnAudi;		//是否信息审核里修改


	public String[] getCategoryArr() {
		return categoryArr;
	}

	public void setCategoryArr(String[] categoryArr) {
		this.categoryArr = categoryArr;
	}

	public Article() {
		super();
		this.weight = 0;
		this.hits = 0;
		this.posid = "";
	}

	public Article(String id){
		this();
		this.id = id;
	}
	
	public Article(Category category){
		this();
		this.category = category;
	}

	public void prePersist(){
		//TODO 后续处理，暂不知有何用处
		//super.prePersist();
		articleData.setId(this.id);
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    @Length(min=0, max=255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

	@Length(min=0, max=50)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Length(min=0, max=255)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
        this.image = image;//CmsUtils.formatImageSrcToDb(image);
	}

	@Length(min=0, max=255)
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Length(min=0, max=255)
	public String getDescription() {
		return description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Date getWeightDate() {
		return weightDate;
	}

	public void setWeightDate(Date weightDate) {
		this.weightDate = weightDate;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	@Length(min=0, max=10)
	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

    public String getCustomContentView() {
        return customContentView;
    }

    public void setCustomContentView(String customContentView) {
        this.customContentView = customContentView;
    }

    public String getViewConfig() {
        return viewConfig;
    }

    public void setViewConfig(String viewConfig) {
        this.viewConfig = viewConfig;
    }

	public ArticleData getArticleData() {
		return articleData;
	}

	public void setArticleData(ArticleData articleData) {
		this.articleData = articleData;
	}

	public List<String> getPosidList() {
		List<String> list = Lists.newArrayList();
		if (posid != null){
			for (String s : StringUtils.split(posid, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public String getAppendfile() {
		return appendfile;
	}

	public void setAppendfile(String appendfile) {
		this.appendfile = appendfile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPosidList(List<String> list) {
		posid = ","+StringUtils.join(list, ",")+",";
	}

   	public String getUrl() {
        return CmsUtils.getUrlDynamic(this);
   	}

   	public String getImageSrc() {
        return CmsUtils.formatImageSrcToWeb(this.image);
   	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUnitExamine() {
		return unitExamine;
	}

	public void setUnitExamine(String unitExamine) {
		this.unitExamine = unitExamine;
	}

	public String getOfficeExamine() {
		return officeExamine;
	}

	public void setOfficeExamine(String officeExamine) {
		this.officeExamine = officeExamine;
	}

	public String getOfficeDirectorExamine() {
		return officeDirectorExamine;
	}

	public void setOfficeDirectorExamine(String officeDirectorExamine) {
		this.officeDirectorExamine = officeDirectorExamine;
	}

	public String getFinalExamine() {
		return finalExamine;
	}

	public void setFinalExamine(String finalExamine) {
		this.finalExamine = finalExamine;
	}

	public String getUnitExamineId() {
		return unitExamineId;
	}

	public void setUnitExamineId(String unitExamineId) {
		this.unitExamineId = unitExamineId;
	}

	public String getOfficeExamineId() {
		return officeExamineId;
	}

	public void setOfficeExamineId(String officeExamineId) {
		this.officeExamineId = officeExamineId;
	}

	public String getOfficeDirectorExamineId() {
		return officeDirectorExamineId;
	}

	public void setOfficeDirectorExamineId(String officeDirectorExamineId) {
		this.officeDirectorExamineId = officeDirectorExamineId;
	}

	public String getFinalExamineId() {
		return finalExamineId;
	}

	public void setFinalExamineId(String finalExamineId) {
		this.finalExamineId = finalExamineId;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getIsColumnAudi() {
		return isColumnAudi;
	}

	public void setIsColumnAudi(String isColumnAudi) {
		this.isColumnAudi = isColumnAudi;
	}
}


