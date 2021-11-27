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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">借阅人姓名：</span><span class="modal-custom-info2-value">${affairBorrow.jyrName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">借阅人身份证号：</span><span class="modal-custom-info2-value">${affairBorrow.jyrIdNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被借阅人姓名：</span><span class="modal-custom-info2-value">${affairBorrow.bjyrName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被借阅人身份证号：</span><span class="modal-custom-info2-value">${affairBorrow.bjyrIdNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">借阅时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairBorrow.time}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">借阅人单位：</span><span class="modal-custom-info2-value">${affairBorrow.jyrUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">借阅人政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBorrow.jyrFace, 'zzmm', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准人：</span><span class="modal-custom-info2-value">${affairBorrow.approver}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">归还日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairBorrow.situation}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">经手人：</span><span class="modal-custom-info2-value">${affairBorrow.handler}</span></div>
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