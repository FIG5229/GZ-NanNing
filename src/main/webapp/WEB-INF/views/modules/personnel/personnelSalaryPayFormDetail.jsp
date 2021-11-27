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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelSalaryPay.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelSalaryPay.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发放日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSalaryPay.payDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">计算日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSalaryPay.calculateDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">应发工资：</span><span class="modal-custom-info2-value">${personnelSalaryPay.salary1}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实发工资：</span><span class="modal-custom-info2-value">${personnelSalaryPay.salary2}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">个人所得税：</span><span class="modal-custom-info2-value">${personnelSalaryPay.tax}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">应扣工资：</span><span class="modal-custom-info2-value">${personnelSalaryPay.salary3}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补发工资：</span><span class="modal-custom-info2-value">${personnelSalaryPay.salary4}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年终一次性奖金：</span><span class="modal-custom-info2-value">${personnelSalaryPay.bonus}</span></div>
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