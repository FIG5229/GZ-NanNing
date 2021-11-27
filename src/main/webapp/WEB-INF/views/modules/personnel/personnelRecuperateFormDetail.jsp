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
                    <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelRecuperate.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelRecuperate.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养结束日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelRecuperate.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否带家属：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRecuperate.isBringMember, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养名称：</span><span class="modal-custom-info2-value">${personnelRecuperate.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养去处：</span><span class="modal-custom-info2-value">${personnelRecuperate.place}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养去处(补充)：</span><span class="modal-custom-info2-value">${personnelRecuperate.placeSupplement}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养备注：</span><span class="modal-custom-info2-value">${personnelRecuperate.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养组织单位名称：</span><span class="modal-custom-info2-value">${personnelRecuperate.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养组织单位代码：</span><span class="modal-custom-info2-value">${personnelRecuperate.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养组织单位级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRecuperate.unitLevel, 'personnel_lyzzl', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">疗(休)养组织单位性质类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRecuperate.unitType, 'personnel_lyzzdw', '')}</span></div>
                        </div>
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
