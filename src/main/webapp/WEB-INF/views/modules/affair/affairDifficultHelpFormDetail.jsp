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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairDifficultHelp.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDifficultHelp.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDifficultHelp.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入党时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDifficultHelp.joinPartyTime}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在单位：</span><span class="modal-custom-info2-value">${affairDifficultHelp.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairDifficultHelp.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">慰问对象类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDifficultHelp.wwType, 'comfort_category', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">慰问原因：</span><span class="modal-custom-info2-value">${affairDifficultHelp.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">慰问金额：</span><span class="modal-custom-info2-value">${affairDifficultHelp.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">慰问款源：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDifficultHelp.moneySource, 'source_condolence_payment', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">慰问时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDifficultHelp.time}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">${affairDifficultHelp.phoneNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDifficultHelp.checkType, 'declare_status', '')}</span></div>
                        </div>
                    </div>
                <%--    <div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>--%>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>

