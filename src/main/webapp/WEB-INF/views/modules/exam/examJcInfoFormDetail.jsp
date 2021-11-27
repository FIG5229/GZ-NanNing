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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examJcInfo.jcType, 'jc_types', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩名称：</span><span class="modal-custom-info2-value">${examJcInfo.jcTypeName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加减分情况：</span><span class="modal-custom-info2-value">${examJcInfo.jcCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人：</span><span class="modal-custom-info2-value">${examJcInfo.jcObjectPersonnel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">选择项：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examJcInfo.changeType, 'choose_type', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${examJcInfo.jcDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${examJcInfo.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${examJcInfo.jcObjectUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用模板：</span><span class="modal-custom-info2-value">${fns:getDictLabel(examJcInfo.myUseModel, 'use_models', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">绩效考评标准：</span><span class="modal-custom-info2-value">${examJcInfo.examStandart}</span></div>
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