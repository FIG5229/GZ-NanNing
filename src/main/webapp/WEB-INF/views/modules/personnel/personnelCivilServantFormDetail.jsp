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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记标识：</span><span class="modal-custom-info2-value">${personnelCivilServant.identification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCivilServant.registerDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记时所任职务：</span><span class="modal-custom-info2-value">${personnelCivilServant.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记编号：</span><span class="modal-custom-info2-value">${personnelCivilServant.registerNo}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">录用日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCivilServant.employDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记时所在单位：</span><span class="modal-custom-info2-value">${personnelCivilServant.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记批准单位：</span><span class="modal-custom-info2-value">${personnelCivilServant.approvalUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">登记文号：</span><span class="modal-custom-info2-value">${personnelCivilServant.fileNo}</span></div>
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