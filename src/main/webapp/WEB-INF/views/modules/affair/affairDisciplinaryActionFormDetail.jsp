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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.siren}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.zhengzhiFace, 'affair_xzchufen', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职级：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.jobLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处分单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.cfUnit, 'affair_cf_unit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否立案：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.isLian, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">文号：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.fileNum}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处分及处理方式：</span>
                                <span class="modal-custom-info2-value">
                                   ${fns:getDictLabel(affairDisciplinaryAction.disciplinaryType, 'affair_xzchufen', '')}
                                    <c:choose>
                                        <c:when test="${affairDisciplinaryAction.disciplinaryType == '1'}">
                                            -${fns:getDictLabel(affairDisciplinaryAction.subOption, 'affair_xz_sub_option', '')}
                                        </c:when>
                                        <c:otherwise>
                                            -${fns:getDictLabel(affairDisciplinaryAction.subOption, 'affair_dj_type', '')}
                                            <c:choose>
                                                <c:when test="${affairDisciplinaryAction.djSubOption == '1'}">
                                                    -${fns:getDictLabel(affairDisciplinaryAction.zzSubOption, 'affair_xz_sub_option', '')}
                                                </c:when>
                                                <c:otherwise>
<%--                                                    -${fns:getDictLabel(affairDisciplinaryAction.zzSubOption, 'affair_dj_sub', '')}--%>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">问题性质：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.nature, 'affair_wenti', '')}</span></div>
                            <c:if test="${affairDisciplinaryAction.disciplinaryType == '2' && affairDisciplinaryAction.djSubOption == '2'}">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织处理：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryAction.chuli, 'affair_chuli', '')}</span></div>
                            </c:if>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">司法处理：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.sifa}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">组织处理：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.zuzhi}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">其他方式：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.other}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryAction.date}" pattern="yyyy-MM-dd"/></span></div>--%>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织名称：</span><span class="modal-custom-info2-value">${affairDisciplinaryAction.org}</span></div>--%>

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处分决定时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryAction.approvalDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处分期满时间 ：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryAction.influencePeriod}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发生时间 ：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryAction.lrDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受理时间 ：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryAction.chfDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 140px;">简要问题：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairDisciplinaryAction.violations}</span></div>
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

