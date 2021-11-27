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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
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
    function signOne(noticeId) {
        $.ajax({
            type:"post",
            url:"${ctx}/affair/affairFileNoticeSign/signOne",
            data:{id:noticeId},
            //dataType:"json",
            success:function(res){
                if (res.success == true){
                    $.jBox.tip("签收成功");
                    $("#signDiv").css('display', 'none');
                }
            }
        });
    }
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-info1">
                    <p style="text-indent: 0;">标题：${affairEmpiricalPractice.title}</p>
                    <p style="text-indent: 0;">单位：${affairEmpiricalPractice.unit}</p>
                    <p style="text-indent: 0;">发布时间：<fmt:formatDate value="${affairEmpiricalPractice.releaseDate}" pattern="yyyy-MM-dd"/></p>
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
         <%--   <div class="modal-custom-info1-bottom">
                &lt;%&ndash;已签收过不显示&ndash;%&gt;
                <c:if test="${affairFileNotice.signStatus != '1'}">
                    <div id="signDiv" class="modal-custom-info1-btn red" onclick="signOne('${affairFileNotice.id}')">签收</div>
                </c:if>
                <div id="print" class="modal-custom-info1-btn red">打印</div>
            </div>--%>
        </div>
    </div>
</div>
