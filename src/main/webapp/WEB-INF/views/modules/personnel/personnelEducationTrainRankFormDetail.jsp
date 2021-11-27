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
                <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelEducationTrain.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班名称：</span><span class="modal-custom-info2-value">${personnelEducationTrain.className}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelEducationTrain.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">结束日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelEducationTrain.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训完成情况：</span><span class="modal-custom-info2-value">${personnelEducationTrain.situation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训时所在单位和职务：</span><span class="modal-custom-info2-value">${personnelEducationTrain.unitJob}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelEducationTrain.type, 'personnel_pxtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训机构名称：</span><span class="modal-custom-info2-value">${personnelEducationTrain.organizationName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训地点：</span><span class="modal-custom-info2-value">${personnelEducationTrain.place}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办单位名称：</span><span class="modal-custom-info2-value">${personnelEducationTrain.sponsorName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办单位级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelEducationTrain.sponsorLevel, 'personnel_level', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训离岗状态：</span><span class="modal-custom-info2-value">${personnelEducationTrain.leaveStatus}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训证书编号：</span><span class="modal-custom-info2-value">${personnelEducationTrain.certificateNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">免训原因：</span><span class="modal-custom-info2-value">${personnelEducationTrain.freeReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">免训标识：</span><span class="modal-custom-info2-value">${personnelEducationTrain.freeIdentification}</span></div>
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
</div>