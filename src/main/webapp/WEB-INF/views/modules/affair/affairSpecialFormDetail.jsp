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
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairSpecial.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairSpecial.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairSpecial.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔：</span><span class="modal-custom-info2-value">${affairSpecial.policeRank}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">种类：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairSpecial.type, 'affair_special_type', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申报金额：</span><span class="modal-custom-info2-value">${affairSpecial.money}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairSpecial.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否是党、团员：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairSpecial.personnelFlag, 'affair_is_dang', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">级别：</span><span class="modal-custom-info2-value">${affairSpecial.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairSpecial.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主要事迹：</span><span class="modal-custom-info2-value">${affairSpecial.mainDeeds}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairSpecial.aprovalDate}" pattern="yyyy-MM-dd"/></span></div>

                        </div>
                    </div>
                    <div>
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