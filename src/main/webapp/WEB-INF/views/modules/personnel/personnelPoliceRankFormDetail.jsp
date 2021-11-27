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
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content">
                    <div id="contentTable" class="modal-custom-content">
                        <div class="modal-custom-info2">
                            <div class="modal-custom-info2-col modal-custom-info2-col1">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelPoliceRank.idNumber}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">衔称变动原因：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.changeReason, 'dict_change_reason', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起算日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceRank.startDate}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceRank.endDate}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授衔日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceRank.awardTitleDate}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授衔批准单位名称：</span><span class="modal-custom-info2-value">${personnelPoliceRank.approvalUnitName}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授衔令号：</span><span class="modal-custom-info2-value">${personnelPoliceRank.approvalNumber}</span></div>
                            </div>
                            <div class="modal-custom-info2-col modal-custom-info2-col2">
                               <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授衔状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.status, 'personnel_xctype', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授衔来源：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.source, 'dict_rank_source', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔取消原因：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.cancelReason, 'dict_cancel_reason', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔保留原因：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.retainReason, 'dict_retain_reason', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔不保留原因：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.noretainReason, 'dict_noretain_reason', '')}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔名称：</span><span class="modal-custom-info2-value">${personnelPoliceRank.name}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceRank.type,"personnel_jxtype","")}</span></div>
                            </div>
                        </div>
                   </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print" >打印</div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
