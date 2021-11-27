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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务层次：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelJob.jobLevel, 'personnel_zwcc', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelJob.status, 'personnel_zwtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起算日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelJob.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否高配：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelJob.isHigh, 'yes_no', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">变动原因：</span><span class="modal-custom-info2-value">${personnelJob.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${personnelJob.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${personnelJob.approvalOrgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${personnelJob.approvalOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelJob.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelJob.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务层次备注：</span><span class="modal-custom-info2-value">${personnelJob.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">享受县以下机关职级：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelJob.enjoyJob, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">享受县以下机关职级批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelJob.enjoyDate}" pattern="yyyy-MM-dd"/></span></div>
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