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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">媒介类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTousujubaoguanli.complaintWay, 'affair_tousu', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">反映人姓名：</span><span class="modal-custom-info2-value">${affairTousujubaoguanli.informer}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被反映人姓名：</span><span class="modal-custom-info2-value">${affairTousujubaoguanli.repoter}</span></div>
                        <%--    <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批转单位：</span><span class="modal-custom-info2-value">
                                <c:choose>
                                    <c:when test="${affairTousujubaoguanli.forwardType == '0'}">
                                        ${fns:getDictLabel(affairTousujubaoguanli.forwardType, 'approval_unit', '')}
                                    </c:when>
                                    <c:otherwise>
                                        ${affairTousujubaoguanli.forwardUnit}
                                    </c:otherwise>
                                </c:choose>
                            </span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">收到单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTousujubaoguanli.sdUnit, 'affair_xfjb_unit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">收到时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTousujubaoguanli.receiveTime}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">查处单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTousujubaoguanli.ccUnit, 'affair_xfjb_unit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否查实：</span>
                                <span class="modal-custom-info2-value">
                                    ${fns:getDictLabel(affairTousujubaoguanli.ischeck, 'affair_cbtype', '')}
                                    <c:if test="${affairTousujubaoguanli.ischeck == '2'}">
                                        -${fns:getDictLabel(affairTousujubaoguanli.subOption, 'affair_bjtype_sub', '')}
                                        <c:if test="${not empty affairTousujubaoguanli.result}">
                                            -${affairTousujubaoguanli.result}
                                        </c:if>
                                    </c:if>
                                </span>
                            </div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">反映人单位：</span><span class="modal-custom-info2-value">${affairTousujubaoguanli.informerUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被反映人单位：</span>
                                <span class="modal-custom-info2-value">
                                <c:choose>
                                    <c:when test="${not empty affairTousujubaoguanli.repoterUnit}">
                                        ${affairTousujubaoguanli.repoterUnit}
                                    </c:when>
                                    <c:otherwise>
                                        ${affairTousujubaoguanli.otherRepoterUnit}
                                    </c:otherwise>
                                </c:choose>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">问题类型：</span><span class="modal-custom-info2-value">
                                ${fns:getDictLabel(affairTousujubaoguanli.questionType, 'affair_qtType', '')}
                                 <c:if test="${affairTousujubaoguanli.questionType == '1'}">
                                     ${fns:getDictLabel(affairTousujubaoguanli.zjType, 'affair_zjType', '')}
                                 </c:if>
                                <c:if test="${affairTousujubaoguanli.questionType == '2'}">
                                    ${fns:getDictLabel(affairTousujubaoguanli.sfType, 'affair_sfType', '')}
                                </c:if>
                                <c:if test="${affairTousujubaoguanli.questionType == '3'}">
                                    ${fns:getDictLabel(affairTousujubaoguanli.jjType, 'affair_jjType', '')}
                                </c:if>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">录入时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTousujubaoguanli.createDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否纪律处分：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTousujubaoguanli.isdispose, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否重复：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairTousujubaoguanli.isrepeat, 'yes_no', '')}</span></div>
                        </div>
                    </div>
                   <div class="modal-custom-info2-col modal-custom-info2-col4">
                        <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">简要情况：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairTousujubaoguanli.situation}</span></div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>