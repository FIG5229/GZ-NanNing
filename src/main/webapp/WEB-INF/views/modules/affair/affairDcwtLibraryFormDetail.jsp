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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">开始时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDcwtLibrary.time}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任单位：</span><span class="modal-custom-info2-value">${affairDcwtLibrary.responsibleUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">整改情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDcwtLibrary.rectification, 'affair_zhenggai', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">监督单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDcwtLibrary.supervisoryUnit, 'affair_jdunit', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">结束时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDcwtLibrary.finishDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">存在不足：</span><span class="modal-custom-info2-value">
                                <c:forEach items="${affairDcwtLibrary.problemCategory.split(',')}" var="arr" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index==0}">${fns:getDictLabel(arr, 'affair_wtlb', '')}</c:when>
                                        <c:otherwise>
                                            ,${fns:getDictLabel(arr, 'affair_wtlb', '')}
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">督察方式：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDcwtLibrary.dcType, 'affair_dc_type', '')}</span></div>
                        </div>
                    </div>
                    <div class="modal-custom-info2-col modal-custom-info2-col4">
                        <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">问题概述：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairDcwtLibrary.foundProblem}</span></div>
                        <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">整改说明：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairDcwtLibrary.processingSituation}</span></div>

                    </div>
                  <%--  <div class="modal-custom-info2-col modal-custom-info2-col4">
                        <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">整改说明：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairDcwtLibrary.processingSituation}</span></div>
                    </div>--%>
                    <div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <shiro:hasPermission name="affair:affairDcwtLibrary:delete">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                                </shiro:hasPermission>
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