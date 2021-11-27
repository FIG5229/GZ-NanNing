<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">材料名称：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.materialName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">材料形成时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDestroyMeterial.formDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">份数：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.num}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">领导审批：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.approval}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                             <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">材料销毁时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDestroyMeterial.destroyDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">销毁原因：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairDestroyMeterial.remark}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>