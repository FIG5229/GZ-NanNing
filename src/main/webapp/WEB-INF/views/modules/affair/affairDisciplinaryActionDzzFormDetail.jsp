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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairDisciplinaryActionDzz.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党组织：</span><span class="modal-custom-info2-value">${affairDisciplinaryActionDzz.org}</span></div>
                          <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">问题性质：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryActionDzz.nature, 'affair_wenti', '')}</span></div>
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">方式：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryActionDzz.disciplinaryType, 'affair_dj_sub', '')}</span></div>
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairDisciplinaryActionDzz.unit, 'affair_cf_unit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">文号：</span><span class="modal-custom-info2-value">${affairDisciplinaryActionDzz.fileNum}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span class="modal-custom-info2-key" style="width: 110px;">简要问题：</span><span class="modal-custom-info2-value" style="padding: 0 110px;">${affairDisciplinaryActionDzz.violations}</span></div>
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

