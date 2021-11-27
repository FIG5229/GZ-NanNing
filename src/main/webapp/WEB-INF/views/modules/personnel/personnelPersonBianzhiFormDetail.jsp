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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用编制种类：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPersonBianzhi.type, 'personnel_bztype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入编日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPersonBianzhi.intoDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入编前身份类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPersonBianzhi.preIdentityType, 'personnel_shenfen', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入编前单位：</span><span class="modal-custom-info2-value">${personnelPersonBianzhi.preUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入编方式：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPersonBianzhi.method, 'personnel_rubian', '')}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入编批准单位：</span><span class="modal-custom-info2-value">${personnelPersonBianzhi.intoApprovalUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出编日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPersonBianzhi.outDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出编原因：</span><span class="modal-custom-info2-value">${personnelPersonBianzhi.outReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出编批准单位：</span><span class="modal-custom-info2-value">${personnelPersonBianzhi.outApprovalUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">经费来源种类：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPersonBianzhi.fundsType, 'personnel_jingfei', '')}</span></div>
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