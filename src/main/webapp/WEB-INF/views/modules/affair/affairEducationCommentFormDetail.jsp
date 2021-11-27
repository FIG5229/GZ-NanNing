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
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairEducationComment.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">籍贯：</span><span class="modal-custom-info2-value">${affairEducationComment.nativePlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入团时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairEducationComment.rtDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评议时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairEducationComment.pyDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairEducationComment.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属团组织：</span><span class="modal-custom-info2-value">${affairEducationComment.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairEducationComment.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">自评等级：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairEducationComment.level, 'affair_comment_grade', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生年月：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairEducationComment.birthday}" pattern="yyyy-MM-dd"/></span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">个人自评内容：</span><span class="modal-custom-info2-value">${affairEducationComment.content}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">团支部意见：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairEducationComment.opinion, 'affair_comment_grade', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处团委意见：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairEducationComment.ctwOpinion, 'affair_comment_grade', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">个人自评内容：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairEducationComment.content}</span></div>
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