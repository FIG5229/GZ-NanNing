package com.thinkgem.jeesite.modules.personnel.entity;


import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * @author kevin.jia,
 * @version 2020/9/24
 * 批量查询通过该类获取表格内容
 */
public class PersonnelBatchSel  {
    @ExcelField(title = "内容", type = 0, align = 2, sort = 0)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
