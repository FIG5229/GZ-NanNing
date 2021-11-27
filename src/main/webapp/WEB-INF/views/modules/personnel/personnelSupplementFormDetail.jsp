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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">曾用名：</span><span class="modal-custom-info2-value">${personnelSupplement.preName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">侨胞标识：</span><span class="modal-custom-info2-value">${personnelSupplement.identification3}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加第二党派名称：</span><span class="modal-custom-info2-value">${personnelSupplement.joinName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌变异历史：</span><span class="modal-custom-info2-value">$personnelSupplement.changeHistory}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈军属标识：</span><span class="modal-custom-info2-value">${personnelSupplement.identification1}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">夫妻两地分居起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSupplement.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加第二党派日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSupplement.joinDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">宗教信仰：</span><span class="modal-custom-info2-value">${personnelSupplement.religionBelief}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">逝世标识：</span><span class="modal-custom-info2-value">${personnelSupplement.identification4}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤残军人标识：</span><span class="modal-custom-info2-value">${personnelSupplement.identification2}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">夫妻两地分居终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSupplement.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈士记载：</span><span class="modal-custom-info2-value">${personnelSupplement.martyrRecord}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻历史：</span><span class="modal-custom-info2-value">${personnelSupplement.marriageHistory}</span></div>
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