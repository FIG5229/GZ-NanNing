package com.thinkgem.jeesite.common.elasticsearch.bean;

import com.thinkgem.jeesite.common.elasticsearch.annotation.EsId;
import com.thinkgem.jeesite.common.elasticsearch.annotation.HighLight;

import java.util.Date;

public class EsAttachment {
    @EsId
    private String id;
    private String srcIndex;
    private String srcDocId;
    private String srcFieldName;
    @HighLight
    private String fileName;
    private String filePath;
    private String fileType;
    @HighLight
    private String fileText;
    private String createBy;
    private Date createDate;
    private String office;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcIndex() {
        return srcIndex;
    }

    public void setSrcIndex(String srcIndex) {
        this.srcIndex = srcIndex;
    }

    public String getSrcDocId() {
        return srcDocId;
    }

    public void setSrcDocId(String srcDocId) {
        this.srcDocId = srcDocId;
    }

    public String getSrcFieldName() {
        return srcFieldName;
    }

    public void setSrcFieldName(String srcFieldName) {
        this.srcFieldName = srcFieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileText() {
        return fileText;
    }

    public void setFileText(String fileText) {
        this.fileText = fileText;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
