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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流方式：</span><span class="modal-custom-info2-value">${personnelCommunication.method}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流去向：</span><span class="modal-custom-info2-value">${personnelCommunication.toPlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流任职情况：</span><span class="modal-custom-info2-value">${personnelCommunication.situation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流开始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCommunication.startDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流原因：</span><span class="modal-custom-info2-value">${personnelCommunication.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCommunication.type, 'personnel_jiaoliu', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流前职务级别：</span><span class="modal-custom-info2-value">${personnelCommunication.preLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流结束日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCommunication.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交流目的单位名称：</span><span class="modal-custom-info2-value">${personnelCommunication.unitName}</span></div>
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