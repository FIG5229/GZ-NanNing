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
                afterPrint:function (){
                    $('.download').css('display', '');
                }
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">领导干部名称：</span><span class="modal-custom-info2-value">${affairLeaderCheck.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairLeaderCheck.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评时间：</span><span class="modal-custom-info2-value">${affairLeaderCheck.date}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参与测评人数：</span><span class="modal-custom-info2-value">${affairLeaderCheck.num}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">测评登记：</span><span class="modal-custom-info2-value">${affairLeaderCheck.grade}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">相关材料：</span><span class="modal-custom-info2-value">
                                <c:forEach items="${filePathList}" var="m" >
                            <div>
                                <span>${m.fileName}</span>

                            </div>
                                </c:forEach>
                            </span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairLeaderCheck.grade, 'assessment_level', '')}</span></div>
                        </div>
                    </div>
                    <div>
                        <span>附件：</span>
                        <c:forEach items="${filePathList}" var="m" >
                            <div>
                                <span>${m.fileName}</span>
                                <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>