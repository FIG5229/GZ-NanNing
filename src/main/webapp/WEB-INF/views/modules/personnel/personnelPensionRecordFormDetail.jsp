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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelPensionRecord.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金发放日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPensionRecord.provideDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金发放对象：</span><span class="modal-custom-info2-value">${personnelPensionRecord.providePerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPensionRecord.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">终止发放原因：</span><span class="modal-custom-info2-value">${personnelPensionRecord.endReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金说明：</span><span class="modal-custom-info2-value">${personnelPensionRecord.explain}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2" style="width: 400px;">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value" style="width: 250px;">${personnelPensionRecord.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金审批日期：</span><span class="modal-custom-info2-value" style="width: 250px;"><fmt:formatDate value="${personnelPensionRecord.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤金性质：</span><span class="modal-custom-info2-value" style="width: 250px;"></span>${fns:getDictLabel(personnelPensionRecord.character, 'pension_nature', '')}</div>
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