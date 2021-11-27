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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主题名称：</span><span class="modal-custom-info2-value">${affairStudyEducation.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairStudyEducation.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairStudyEducation.type, 'affair_xuexiluru', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习地点：</span><span class="modal-custom-info2-value">${affairStudyEducation.place}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">应到人数：</span><span class="modal-custom-info2-value">${affairStudyEducation.ydNum}</span></div>
                         </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实到人数：</span><span class="modal-custom-info2-value">${affairStudyEducation.sdNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">缺席人员：</span><span class="modal-custom-info2-value">${affairStudyEducation.absentPerson}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">活动内容：</span><span class="modal-custom-info2-value">${affairStudyEducation.content}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">团组织名称：</span><span class="modal-custom-info2-value">${affairStudyEducation.organization}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主持人：</span><span class="modal-custom-info2-value">${affairStudyEducation.host}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录人：</span><span class="modal-custom-info2-value">${affairStudyEducation.recorder}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">活动内容：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairStudyEducation.content}</span></div>
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