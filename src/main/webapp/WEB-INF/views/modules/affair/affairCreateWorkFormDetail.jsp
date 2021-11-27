<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            $('.download').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint: function () {
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评项目：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.title}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">层级：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairCreateWork.level, 'evaluation_level', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年度：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.year}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评标准：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.standard}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评方法：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairCreateWork.method, 'evaluation_method', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评分值：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.score}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">状态：</span><span
                                    class="modal-custom-info2-value">${fns:getDictLabel(affairCreateWork.status, 'declare_status', '')}</span>
                            </div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评内容：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.content}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作开展情况：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.workingConditions}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核人：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.checkMan}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提交人：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.submitMan}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">整改意见：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.shOpinion}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">自评分：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.selfRating}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核分：</span><span
                                    class="modal-custom-info2-value">${affairCreateWork.assessmentScore}</span>
                            </div>
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