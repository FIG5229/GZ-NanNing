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
                        <div class="modal-custom-info2-col">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPoliceHelpEducate.time}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">帮教人：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.helpers}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">帮教对象：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.helpobject}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">对象存在问题：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.question}</span></div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">帮教措施：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.measures}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">帮教成效：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.results}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">简要内容：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.content}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPoliceHelpEducate.sex, 'sex', '')}</span></div>--%>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">表彰单位：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.commendationUnit}</span></div>--%>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPoliceHelpEducate.type, 'affair_org_reward_punish', '')}</span></div>--%>
                            <%--<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核意见：</span><span class="modal-custom-info2-value">${affairPoliceHelpEducate.opinion}</span></div>--%>
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