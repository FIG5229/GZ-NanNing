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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairRetire.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairRetire.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">原任单位：</span><span class="modal-custom-info2-value">${affairRetire.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">原任职务：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairRetire.job, 'affair_yrzw', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">原任级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairRetire.level, 'affair_yrjb', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                              <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">正本数量：</span><span class="modal-custom-info2-value">${affairRetire.ZNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">副本数量：</span><span class="modal-custom-info2-value">${affairRetire.FNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案号：</span><span class="modal-custom-info2-value">${affairRetire.archiveNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退休时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairRetire.outDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairRetire.remark}</span></div>
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

