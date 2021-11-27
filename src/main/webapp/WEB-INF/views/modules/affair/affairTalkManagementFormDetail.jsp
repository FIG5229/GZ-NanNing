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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairTalkManagement.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairTalkManagement.personalNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairTalkManagement.siren}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairTalkManagement.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTalkManagement.mianmao, 'political_status', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">谈话人：</span><span class="modal-custom-info2-value">${affairTalkManagement.talker}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairTalkManagement.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职级：</span><span class="modal-custom-info2-value">${affairTalkManagement.jobLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办部门：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTalkManagement.zbUnit, 'affair_zb_unit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTalkManagement.time}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTalkManagement.letterCategory, 'affair_tanhua', '')}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要问题：</span><span class="modal-custom-info2-value">${affairTalkManagement.problem}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">谈话地点：</span><span class="modal-custom-info2-value">${affairTalkManagement.talkPlace}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">主要问题：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairTalkManagement.problem}</span></div>
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