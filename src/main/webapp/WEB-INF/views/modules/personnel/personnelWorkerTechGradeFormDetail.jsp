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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工人技术等级：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelWorkerTechGrade.grade, 'personnel_grjsdj', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">当前状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelWorkerTechGrade.status, 'personnel_grdqzt', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评定单位名称：</span><span class="modal-custom-info2-value">${personnelWorkerTechGrade.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评定依据：</span><span class="modal-custom-info2-value">${personnelWorkerTechGrade.basis}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">技术等级变动类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelWorkerTechGrade.changeType, 'personnel_jsdjbdlb', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评定日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelWorkerTechGrade.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评定单位：</span><span class="modal-custom-info2-value">${personnelWorkerTechGrade.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评定单位代码：</span><span class="modal-custom-info2-value">${personnelWorkerTechGrade.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">技术等级截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelWorkerTechGrade.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${personnelWorkerTechGrade.certificateNo}</span></div>
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