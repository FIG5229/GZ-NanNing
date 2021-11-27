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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2" style="margin-left: 100px;">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间 ：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairRightsProtection.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">标题：</span><span class="modal-custom-info2-value">${affairRightsProtection.title}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要内容：</span><span class="modal-custom-info2-value">${affairRightsProtection.mainContent}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">监督单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairRightsProtection.unit, 'affair_mjwq', '')}</div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任单位：</span><span class="modal-custom-info2-value">${affairRightsProtection.responsibleUnit}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">相关文件：</span>
                                <c:forEach items="${filePathList}" var="m" varStatus="status">
                                    <div>
                                        <span class="modal-custom-info2-key">${m.fileName}</span>
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