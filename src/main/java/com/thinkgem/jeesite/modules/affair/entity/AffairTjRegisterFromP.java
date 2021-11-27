package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 团籍注册过渡实体类
 * @author mason.xv
 * @version 2020-03-22
 */
public class AffairTjRegisterFromP extends DataEntity<AffairTjRegisterFromP> {

    private static final long serialVersionUID = 1L;
    private String name;  //团籍注册过渡姓名
    private String idNumber; //团籍注册过渡身份证号
    private String birthday;   //团籍注册过渡出生日期
    private String age;   //年龄

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
