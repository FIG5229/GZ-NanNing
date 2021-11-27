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
                <div class="modal-custom-content">
                    <div  class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelReward.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称代码：</span><span class="modal-custom-info2-value">${personnelReward.nameCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelReward.approcalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelReward.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件文号：</span><span class="modal-custom-info2-value">${personnelReward.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称：</span><span class="modal-custom-info2-value">${personnelReward.fileName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称：</span><span class="modal-custom-info2-value">${personnelReward.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖励时职务：</span><span class="modal-custom-info2-value">${personnelReward.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖励时职务层次：</span><span class="modal-custom-info2-value">${personnelReward.jobLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">荣誉称号级别：</span><span class="modal-custom-info2-value">${personnelReward.designationLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">追授标志：</span><span class="modal-custom-info2-value">${personnelReward.sign}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">享受待遇类别：</span><span class="modal-custom-info2-value">${personnelReward.treatmentType}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                               <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励原因：</span><span class="modal-custom-info2-value">${personnelReward.rewardReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${personnelReward.approvalOrgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${personnelReward.approvalOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关类别：</span><span class="modal-custom-info2-value">${personnelReward.approcalOrgType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关层次：</span><span class="modal-custom-info2-value">${personnelReward.approcalOrgLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料(500字以内)：</span><span class="modal-custom-info2-value">${personnelReward.achievement}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销标识：</span><span class="modal-custom-info2-value">${personnelReward.cancelIdentification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销原因：</span><span class="modal-custom-info2-value">${personnelReward.cancelReason}</span></div>
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

    </div>
