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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">新闻标题：</span><span class="modal-custom-info2-value">${affairNews.title}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">栏目：</span><span class="modal-custom-info2-value">${affairNews.lm}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">作者：</span>
                                <span class="modal-custom-info2-value">
                                   <c:forEach items="${affairNews.affairNewsAuthorList}" var="m" varStatus="status">
                                       <span>${m.newsAuthor}&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                   </c:forEach>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">刊发时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairNews.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div>
                                <div class="modal-custom-info2-row">
                                    <span class="modal-custom-info2-key">所属单位：</span>
                                    <span class="modal-custom-info2-value">
                                        <c:forEach items="${affairNews.affairNewsUnitList}" var="m" varStatus="status">
                                            <span>&nbsp;&nbsp;${m.newsUnit}<br></span>
                                        </c:forEach>
                                    </span>
                                </div>

                                <div class="modal-custom-info2-row" style="margin: 40px 2px"><span class="modal-custom-info2-key">内容：</span><span class="modal-custom-info2-value">${affairNews.content}</span></div>
                            </div>

                        </div>

                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">媒体名称：</span><span class="modal-custom-info2-value">${affairNews.mediaName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">篇幅字数：</span><span class="modal-custom-info2-value">${affairNews.wordNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">刊发类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairNews.typr, 'affair_news', '')}</span></div>
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">所属人：</span>
                                <span class="modal-custom-info2-value">
                                    <c:forEach items="${affairNews.affairNewsNameList}" var="m" varStatus="status">
                                        <span>${m.newsName}&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                    </c:forEach>
                                </span>
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
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>