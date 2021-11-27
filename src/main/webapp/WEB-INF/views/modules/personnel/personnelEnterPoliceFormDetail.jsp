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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelEnterPolice.reportDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报单位代码：</span><span class="modal-custom-info2-value">${personnelEnterPolice.reportUnitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报请示名称（文号)：</span><span class="modal-custom-info2-value">${personnelEnterPolice.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位名称：</span><span class="modal-custom-info2-value">${personnelEnterPolice.approvalUnit}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报单位名称：</span><span class="modal-custom-info2-value">${personnelEnterPolice.reportUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">进入公安方式：</span><span class="modal-custom-info2-value">${personnelEnterPolice.method}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelEnterPolice.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位代码：</span><span class="modal-custom-info2-value">${personnelEnterPolice.approvalFileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${personnelEnterPolice.approvalFileNo}</span></div>
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