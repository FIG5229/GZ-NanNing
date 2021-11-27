package com.thinkgem.jeesite.modules.affair.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class AffairQjSum extends DataEntity<AffairQjSum> {
    private static final long serialVersionUID = 1L;

    private Integer nianXiu;
    private Integer tanQin;
    private Integer hun;
    private Integer bing;
    private Integer peiCan;
    private Integer can;
    private Integer sang;
    private Integer shi;
    private Integer gongShang;
    private Integer chucai;
    private Integer lianXi;
    private String unitName;
    private String unitId;

    public Integer getNianXiu() {
        return nianXiu;
    }

    public void setNianXiu(Integer nianXiu) {
        this.nianXiu = nianXiu;
    }

    public Integer getTanQin() {
        return tanQin;
    }

    public void setTanQin(Integer tanQin) {
        this.tanQin = tanQin;
    }

    public Integer getHun() {
        return hun;
    }

    public void setHun(Integer hun) {
        this.hun = hun;
    }

    public Integer getBing() {
        return bing;
    }

    public void setBing(Integer bing) {
        this.bing = bing;
    }

    public Integer getPeiCan() {
        return peiCan;
    }

    public void setPeiCan(Integer peiCan) {
        this.peiCan = peiCan;
    }

    public Integer getCan() {
        return can;
    }

    public void setCan(Integer can) {
        this.can = can;
    }

    public Integer getSang() {
        return sang;
    }

    public void setSang(Integer sang) {
        this.sang = sang;
    }

    public Integer getShi() {
        return shi;
    }

    public void setShi(Integer shi) {
        this.shi = shi;
    }

    public Integer getGongShang() {
        return gongShang;
    }

    public void setGongShang(Integer gongShang) {
        this.gongShang = gongShang;
    }

    public Integer getChucai() {
        return chucai;
    }

    public void setChucai(Integer chucai) {
        this.chucai = chucai;
    }

    public Integer getLianXi() {
        return lianXi;
    }

    public void setLianXi(Integer lianXi) {
        this.lianXi = lianXi;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
