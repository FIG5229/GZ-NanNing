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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">名称：</span><span class="modal-custom-info2-value">${personnelSocialGroup.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">挂靠单位名称：</span><span class="modal-custom-info2-value">${personnelSocialGroup.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">挂靠单位代码：</span><span class="modal-custom-info2-value">${personnelSocialGroup.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">代码：</span><span class="modal-custom-info2-value">${personnelSocialGroup.code}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSocialGroup.workDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在政区：</span><span class="modal-custom-info2-value">${personnelSocialGroup.area}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">层次：</span><span class="modal-custom-info2-value">${personnelSocialGroup.level}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性质类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelSocialGroup.type, 'personnel_xzlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属行业：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelSocialGroup.industry, 'personnel_sshy', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">统计标识：</span><span class="modal-custom-info2-value">${personnelSocialGroup.identification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">社会团体职务名称：</span><span class="modal-custom-info2-value">${personnelSocialGroup.jobName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelSocialGroup.status, 'personnel_rztype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多职务主次序号：</span><span class="modal-custom-info2-value">${personnelSocialGroup.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">集体内排列顺序：</span><span class="modal-custom-info2-value">${personnelSocialGroup.sort}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主管工作：</span><span class="modal-custom-info2-value">${personnelSocialGroup.majorJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${personnelSocialGroup.approvalOrgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${personnelSocialGroup.approvalOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">连续任该职起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSocialGroup.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">免职日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSocialGroup.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div  class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>