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
                afterPrint:function(){
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairGz.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">固资名称：</span><span class="modal-custom-info2-value">${affairGz.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">编号：</span><span
                                    class="modal-custom-info2-value">${affairGz.serialNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">条形码：</span><span
                                    class="modal-custom-info2-value">${affairGz.barCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">预计使用年限：</span><span class="modal-custom-info2-value">${affairGz.userYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保管人：</span><span class="modal-custom-info2-value">${affairGz.bgPerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">验收意见：</span><span class="modal-custom-info2-value">${affairGz.ysOpinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核意见：</span><span class="modal-custom-info2-value">${affairGz.shOpinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairGz.remark}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用单位：</span><span class="modal-custom-info2-value">${affairGz.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">规格：</span><span class="modal-custom-info2-value">${affairGz.specification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">型号：</span><span class="modal-custom-info2-value">${affairGz.model}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">数量：</span><span class="modal-custom-info2-value">${affairGz.num}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单价：</span><span class="modal-custom-info2-value">${affairGz.price}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">合计总价：</span><span class="modal-custom-info2-value">${affairGz.totalPrice}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">验收人：</span><span class="modal-custom-info2-value">${affairGz.ysPerson}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-file">
                    <div class="modal-custom-info1-file-1">附件：</div>
                    <div class="modal-custom-info1-file-r">
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
                            <div class="modal-custom-info1-file-item">
                                <span>${m.fileName}</span>
                                    <%--<a href="#">在线预览</a>--%>
                                <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>