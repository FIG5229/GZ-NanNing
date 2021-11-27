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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒名称：</span><span class="modal-custom-info2-value">${personnelPunish.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPunish.type, 'personnel_cjtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒代码：</span><span class="modal-custom-info2-value">${personnelPunish.code}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受惩戒时职务及职级：</span><span class="modal-custom-info2-value">${personnelPunish.jobLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒事件序号：</span><span class="modal-custom-info2-value">${personnelPunish.caseNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒原因：</span><span class="modal-custom-info2-value">${personnelPunish.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">惩戒事由：</span><span class="modal-custom-info2-value">${personnelPunish.caseReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPunish.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${personnelPunish.approvalOrg}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${personnelPunish.approvalOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称及文号：</span><span class="modal-custom-info2-value">${personnelPunish.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPunish.approvalOfficeType, 'personnel_jgtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否解除/超过有限期：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPunish.isCancelOver, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">解除变更惩戒日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPunish.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称及文号：</span><span class="modal-custom-info2-value">${personnelPunish.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">解除变更惩戒原因：</span><span class="modal-custom-info2-value">${personnelPunish.cancelReason}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>