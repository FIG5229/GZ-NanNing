<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>民警思想动态分析管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
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
</head>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-row">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key"
                                                                      style="width: 110px;">标题：</span><span
                                    class="modal-custom-info2-value">民警思想动态分析</span></div>
                        </div>
                     <%--   <div class="modal-custom-info-row-wrap" style="overflow: hidden;">
                            <div class="modal-custom-info2-col modal-custom-info2-col2">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key"
                                                                          style="width: 110px;">报送人：</span><span
                                        class="modal-custom-info2-value">${affairPoliceThoughtAnalysis.personName}</span>
                                </div>
                            </div>

                        </div>--%>
                        <div class="modal-custom-info2-col modal-custom-info2-col4">
                            <div class="modal-custom-info2-row" style="width: 100%;"><span
                                    class="modal-custom-info2-key" style="width: 110px;">部门：</span><span
                                    class="modal-custom-info2-value"
                                    >${affairPoliceThoughtAnalysis.unit}</span></div>
                            <div class="modal-custom-info2-row" style="width: 100%;"><span
                                    class="modal-custom-info2-key" style="width: 110px;">主要内容：</span><br><span
                                    class="modal-custom-info2-value"
                                    style="padding: 0 110px;">${affairPoliceThoughtAnalysis.content}</span></div>
                        </div>
                    </div>
                    <div style="padding: 0 110px;">
                        <span>附件：</span>
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
                            <div>
                                <span>${m.fileName}</span>
                                    <%--<a href="#">在线预览</a>--%>
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
</html>