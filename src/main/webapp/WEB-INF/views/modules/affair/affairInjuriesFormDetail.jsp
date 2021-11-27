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
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairInjuries.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairInjuries.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职级：</span><span class="modal-custom-info2-value">${affairInjuries.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公伤认定时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairInjuries.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定情况：</span><span class="modal-custom-info2-value">${affairInjuries.confirmation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定材料：</span><span class="modal-custom-info2-value">${affairInjuries.material}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">抚恤待遇：</span><span class="modal-custom-info2-value">${affairInjuries.pension}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairInjuries.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairInjuries.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发生时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairInjuries.happenDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定单位：</span><span class="modal-custom-info2-value">${affairInjuries.rdUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定情况说明：</span><span class="modal-custom-info2-value">${affairInjuries.explanation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批复情况：</span><span class="modal-custom-info2-value">${affairInjuries.approval}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairInjuries.remark}</span></div>

                        </div>
                    </div>
                    <div>
                        <span>附件：</span>
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
                            <div>
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