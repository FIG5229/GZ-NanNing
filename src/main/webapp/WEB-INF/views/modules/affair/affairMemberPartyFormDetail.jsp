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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党委委员：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.partyOrganization}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairMemberPartyCommittee.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairMemberPartyCommittee.nation,"nation","")}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党委委员上党课：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.partyClass}</span></div>
                       <%--     <div class="modal-custom-info1-file">
                                <div class="modal-custom-info1-file-1">附件：</div>
                                <div class="modal-custom-info1-file-r">
                                    <c:forEach items="${pcFilePathList}" var="m" varStatus="status">
                                        <div class="modal-custom-info1-file-item">
                                            <span>${m.fileName}</span>
                                                &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                            <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">落实联系点制度情况：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.associatedPiont}</span></div>
                   <%--         <div class="modal-custom-info1-file">
                                <div class="modal-custom-info1-file-1">附件：</div>
                                <div class="modal-custom-info1-file-r">
                                    <c:forEach items="${apFilePathList}" var="m" varStatus="status">
                                        <div class="modal-custom-info1-file-item">
                                            <span>${m.fileName}</span>
                                                &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                            <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">落实党建工作责任报告录入：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.responsibilityReport}</span></div>--%>
                            <div class="modal-custom-info1-file">
                                <div class="modal-custom-info1-file-1">相关附件：</div>
                                <div class="modal-custom-info1-file-r">
                                    <c:forEach items="${rrFilePathList}" var="m" varStatus="status">
                                        <div class="modal-custom-info1-file-item">
                                            <span>${m.fileName}</span>
                                                <%--<a href="#">在线预览</a>--%>
                                            <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内容：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.content}</span></div>--%>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
<%--                            <div class="modal-custom-info2-row" style="width: 100%;float: left"><span class="modal-custom-info2-key" style="width: 140px;">内容：</span><span class="modal-custom-info2-value">${affairMemberPartyCommittee.content}</span></div>--%>
                        </div>
                    </div>
<%--                    <div class="modal-custom-info1-file">--%>
<%--                        <div class="modal-custom-info1-file-1">附件：</div>--%>
<%--                        <div class="modal-custom-info1-file-r">--%>
<%--                            <c:forEach items="${filePathList}" var="m" varStatus="status">--%>
<%--                                <div class="modal-custom-info1-file-item">--%>
<%--                                    <span>${m.fileName}</span>--%>
<%--                                        &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;--%>
<%--                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>--%>
<%--                                </div>--%>
<%--                            </c:forEach>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>