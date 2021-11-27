/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.tw.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自动考评-人员组织情况Entity
 * @author alan.wu
 * @version 2020-11-19
 */
public class UserPView extends DataEntity<UserPView> {
	
	private static final long serialVersionUID = 1L;
	private String guid;		// guid
	private String department;		// department
	private String name;		// name
	private String sex;		// sex
	private String idnumber;		// idnumber
	private String post;		// post
	private String rank;		// rank
	private String organizationcode;		// organizationcode
	private String tel;		// tel
	private String type;		// type
	private String username;		// username
	private String equipmentid;		// equipmentid
	private String fullunitname;		// fullunitname
	
	public UserPView() {
		super();
	}

	public UserPView(String id){
		super(id);
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getOrganizationcode() {
		return organizationcode;
	}

	public void setOrganizationcode(String organizationcode) {
		this.organizationcode = organizationcode;
	}
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(String equipmentid) {
		this.equipmentid = equipmentid;
	}
	
	public String getFullunitname() {
		return fullunitname;
	}

	public void setFullunitname(String fullunitname) {
		this.fullunitname = fullunitname;
	}
	
}