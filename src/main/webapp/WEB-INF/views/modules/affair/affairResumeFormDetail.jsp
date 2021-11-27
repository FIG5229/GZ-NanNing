<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<div id="modalInfo1">
    <div class="text" style=" text-align:center;"><h3>履历信息管理</h3></div>
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairResume.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairResume.policeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairResume.idNumber}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairResume.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairResume.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在单位：</span><span class="modal-custom-info2-value">${affairResume.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份或职务说明：</span><span class="modal-custom-info2-value">${affairResume.identityJobExplain}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">履历类别：</span><span class="modal-custom-info2-value">${fns:getDictList('personnel_lltype')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基层工作的标志：</span><span class="modal-custom-info2-value">${fns:getDictList('yes_no')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">履历说明：</span><span class="modal-custom-info2-value">${affairResume.explain}</span></div>
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
