package com.thinkgem.jeesite.common.elasticsearch.bean;

public class AuthorityParam {
    private String[] createBy;
    private String[] officeIds;
    public AuthorityParam(){}

    public AuthorityParam(String[] createBy, String[] officeIds) {
        this.createBy = createBy;
        this.officeIds = officeIds;
    }

    public String[] getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String[] createBy) {
        this.createBy = createBy;
    }

    public String[] getOfficeIds() {
        return officeIds;
    }

    public void setOfficeIds(String[] officeIds) {
        this.officeIds = officeIds;
    }
}
