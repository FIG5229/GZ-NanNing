<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairSubmitForm.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案卷数：</span><span class="modal-custom-info2-value">${affairSubmitForm.num}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">团组织名称：</span><span class="modal-custom-info2-value">${affairSubmitForm.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairSubmitForm.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职开始时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairSubmitForm.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职结束时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairSubmitForm.endDate}" pattern="yyyy-MM-dd"/></span></div>
                         </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案整理人：</span><span class="modal-custom-info2-value">${affairSubmitForm.arranger}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案审核人：</span><span class="modal-custom-info2-value">${affairSubmitForm.checker}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" >干部档案遗留问题或需要说明的情况：</span><span style="width: 200px" class="modal-custom-info2-value">${affairSubmitForm.situation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">报送单位意见内容：</span><span class="modal-custom-info2-value">${affairSubmitForm.content}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">报送单位签字领导：</span><span class="modal-custom-info2-value">${affairSubmitForm.leader}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">签字日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairSubmitForm.signDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>