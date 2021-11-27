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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelInsurance.personName}</span></div>

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险种类：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelInsurance.type, 'personnel_baoxian', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险名称：</span><span class="modal-custom-info2-value">${personnelInsurance.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险机构名称：</span><span class="modal-custom-info2-value">${personnelInsurance.orgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险费：</span><span class="modal-custom-info2-value">${personnelInsurance.cost}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险金额：</span><span class="modal-custom-info2-value">${personnelInsurance.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInsurance.startDate}" pattern="yyyy-MM-dd"/></span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelInsurance.idNumber}</span></div>

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInsurance.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险赔付日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInsurance.payDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险赔付事由：</span><span class="modal-custom-info2-value">${personnelInsurance.payReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险终止事由：</span><span class="modal-custom-info2-value">${personnelInsurance.endReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保险备注：</span><span class="modal-custom-info2-value">${personnelInsurance.remark}}</span></div>
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