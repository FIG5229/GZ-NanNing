<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>查看文档详情</title>
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
                        <c:choose>
                            <c:when test="${myType == 'down'}">
                                <div class="modal-custom-info2-col modal-custom-info2-col1">
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">文档名称：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.docName}</span></div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">是否可下载：</span><span
                                            class="modal-custom-info2-value">${fns:getDictLabel(affairDocManagement.isdownload, 'affair_isDownload', '')}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">下载资源数：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.resourcesNum}</span>
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
                            </c:when>
                            <c:otherwise>
                                <div class="modal-custom-info2-col modal-custom-info2-col1">
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">文档编码：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.docCode}</span></div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">文档名称：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.docName}</span></div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">关键字：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.keyword}</span></div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">是否公开：</span><span
                                            class="modal-custom-info2-value">${fns:getDictLabel(affairDocManagement.ispublic, 'docManage_isPublic', '')}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">文档语言：</span><span
                                            class="modal-custom-info2-value">${fns:getDictLabel(affairDocManagement.docLanguage, 'doc_manage_language', '')}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">适用对象：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.suitableObject}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">主要内容：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.mainContent}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">审批状态：</span><span
                                            class="modal-custom-info2-value">${fns:getDictLabel(affairDocManagement.checkType,'check_type','')}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">一级审核人：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.oneCheckMan}</span>
                                    </div>
                                    <c:if test="${affairDocManagement.twoCheckMan != null && affairDocManagement.twoCheckMan != ''}">
                                        <div class="modal-custom-info2-row"><span
                                                class="modal-custom-info2-key">二级审核人：</span><span
                                                class="modal-custom-info2-value">${affairDocManagement.twoCheckMan}</span>
                                        </div>
                                    </c:if>
                                        <%--<div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">文档总页数：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.remark}</span></div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">评分：</span><span
                                            class="modal-custom-info2-value">==</span></div>--%>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">是否可下载：</span><span
                                            class="modal-custom-info2-value">${fns:getDictLabel(affairDocManagement.isdownload, 'affair_isDownload', '')}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">下载资源数：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.resourcesNum}</span>
                                    </div>
                                    <div class="modal-custom-info2-row"><span
                                            class="modal-custom-info2-key">备注：</span><span
                                            class="modal-custom-info2-value">${affairDocManagement.remark}</span></div>
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
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="form-actions" style="text-align: right">
                        <%--<shiro:hasPermission name="affair:affairDocClassify:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
                        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>