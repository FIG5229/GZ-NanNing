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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">岗位名称：</span><span class="modal-custom-info2-value">${personnelPostChange.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否在保密岗位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPostChange.isSecretPost, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPostChange.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">岗位A角色：</span><span class="modal-custom-info2-value">${personnelPostChange.postA}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">岗位类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPostChange.type, 'personnel_gwlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位名称：</span><span class="modal-custom-info2-value">${personnelPostChange.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">终止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPostChange.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">岗位B角色：</span><span class="modal-custom-info2-value">${personnelPostChange.postB}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多岗位描述：</span><span class="modal-custom-info2-value">${personnelPostChange.describe}</span></div>
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