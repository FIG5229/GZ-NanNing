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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">协管层级：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">纳入协管时单位：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任现职前职务及时间：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.nowJobTime}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">异地任职：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.differentPlace}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">兼任类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelXieguanCadre.type, 'personnel_jrlx', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">纳入协管日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelXieguanCadre.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任同级党政领导职务情况及时间：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.situationTime1}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">纳入协管时职务：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退出协管日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelXieguanCadre.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任公安机关主要领导职务情况及时间：</span><span class="modal-custom-info2-value">${personnelXieguanCadre.situationTime2}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom" id="print">
                    <div class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
