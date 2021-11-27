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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${personnelVacation.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">衔称变动原因：</span><span class="modal-custom-info2-value">${personnelVacation.department}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelVacation.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelVacation.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">休假起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelVacation.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">休假结束日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelVacation.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">休假种类：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelVacation.vacationType, 'personnel_xjtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${personnelVacation.position}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实际休假天数：</span><span class="modal-custom-info2-value">${personnelVacation.day}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">病假类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelVacation.illnessType, 'personnel_xjtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${personnelVacation.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人事意见：</span><span class="modal-custom-info2-value">${personnelVacation.rsopinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门意见：</span><span class="modal-custom-info2-value">${personnelVacation.bmopinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">销假日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelVacation.fakeDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">恢复工作时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelVacation.reworkDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">销假说明：</span><span class="modal-custom-info2-value">${personnelVacation.fakeDescription}</span></div>
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