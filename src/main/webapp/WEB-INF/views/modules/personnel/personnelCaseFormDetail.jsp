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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">案件名称：</span><span class="modal-custom-info2-value">${personnelCase.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发案日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCase.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鉴定结果是否为侦察提供方向或者直接认定犯罪：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCase.isDirection, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">涉案金额：</span><span class="modal-custom-info2-value">${personnelCase.money}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">案件级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCase.level, 'personnel_anjian', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">涉案人伤亡情况：</span><span class="modal-custom-info2-value">${personnelCase.situation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鉴定书是否被法庭采用为审判证据：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCase.isEvidence, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证明人：</span><span class="modal-custom-info2-value">${personnelCase.witness}</span></div>
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