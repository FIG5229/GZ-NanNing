<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查单位：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.checkUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查时间：</span><span
                                    class="modal-custom-info2-value"><fmt:formatDate value="${examCheckChild.checkDate}"
                                                                                     pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查人：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.checkPerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用模板：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.useModelName}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">选择项：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.chooseOptionsName}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">整改情况：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.reorganizeInfo}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.remark}</span></div>
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
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">绩效考评标准：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.testStandart}</span></div>--%>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任单位：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyUnit}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任单位扣分：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyUnitScore}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任人：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyPerson}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任人扣分：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyPersonScore}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任领导：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyLeader}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任领导扣分：</span><span
                                        class="modal-custom-info2-value">${examCheckChild.dutyLeaderScore}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">详情情况：</span><span
                                    class="modal-custom-info2-value">${examCheckChild.scortSituation}</span></div>
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