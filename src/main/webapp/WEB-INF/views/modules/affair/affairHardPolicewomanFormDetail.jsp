<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
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
                formValues: false,
                afterPrint:function (){
                    $('.download').css('display', '');
                }
            });
        });
    });
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairHardPolicewoman.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairHardPolicewoman.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">困难原因：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.remark}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairHardPolicewoman.job, 'affair_njzhiwu', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补助类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairHardPolicewoman.subsidyType, 'subsidy_types', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补助金额：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核意见：</span><span class="modal-custom-info2-value">${affairHardPolicewoman.opinion}</span></div>
                            <div>
                                <span>附件：</span>
                                <c:forEach items="${filePathList}" var="m" >
                                    <div>
                                        <span>${m.fileName}</span>
                                        <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>