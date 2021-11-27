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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">有效期限：</span><span class="modal-custom-info2-value">${personnelUseGun.validPeriod}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发证机关：</span><span class="modal-custom-info2-value">${personnelUseGun.issueOrg}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">换证日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelUseGun.replaceDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用枪支情况：</span><span class="modal-custom-info2-value">${personnelUseGun.situation}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件有效状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelUseGun.status, 'personnel_zjyxzt', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发证日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelUseGun.issueDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录序号：</span><span class="modal-custom-info2-value">${personnelUseGun.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">持枪证说明：</span><span class="modal-custom-info2-value">${personnelUseGun.explain}</span></div>
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