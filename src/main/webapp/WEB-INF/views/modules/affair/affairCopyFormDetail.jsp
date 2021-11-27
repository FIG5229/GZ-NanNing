<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复印时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairCopy.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被复印人姓名：</span><span class="modal-custom-info2-value">${affairCopy.bCopyName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">办理人员姓名：</span><span class="modal-custom-info2-value">${affairCopy.handleName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">办理人员单位：</span><span class="modal-custom-info2-value">${affairCopy.unit}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2" style="width: 440px;">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">办理人员政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairCopy.face, 'zzmm', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">审批人：</span><span class="modal-custom-info2-value">${affairCopy.approver}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">复印原因：</span><span class="modal-custom-info2-value">${affairCopy.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">备注：</span><span class="modal-custom-info2-value">${affairCopy.remark}</span></div>
                        </div>
                    </div>
                    <div class="modal-custom-info2-col modal-custom-info2-col4">
                        <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">复印内容：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairCopy.content}</span></div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>