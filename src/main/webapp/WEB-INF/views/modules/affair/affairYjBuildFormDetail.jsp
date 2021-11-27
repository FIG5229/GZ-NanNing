<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>硬件建设详情</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
    </script>
</head>
<body>
<br/>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">

                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">项目名称：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.proName}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">建设时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate value="${affairYjBuild.date}"
                                                                                       pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">建设单位：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.buildUnit}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">建设内容：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.content}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">审核状态：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairYjBuild.checkType, 'check_type', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">一级审核人：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.oneCheckMan}</span>
                            </div>
                            <c:if test="${affairYjBuild.twoCheckMan != null && affairYjBuild.twoCheckMan != ''}">
                                <div class="modal-custom-info2-row">
                                    <span class="modal-custom-info2-key">二级审核人：</span>
                                    <span class="modal-custom-info2-value">${affairYjBuild.twoCheckMan}</span>
                                </div>
                            </c:if>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">审核意见：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.opinion}</span>
                            </div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">申报人：</span>
                                <span class="modal-custom-info2-value">${affairYjBuild.submitMan}</span>
                            </div>

                            <div class="modal-custom-info1-file">
                                <div class="modal-custom-info1-file-1">附件：</div>
                                <div class="modal-custom-info1-file-r">
                                    <c:forEach items="${filePathList}" var="m" varStatus="status">
                                        <div class="modal-custom-info1-file-item">
                                            <span>${m.fileName}</span>
                                                <%--<a href="#">在线预览</a>--%>
                                            <a class="download"
                                               href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-actions" style="text-align: right">
                        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>