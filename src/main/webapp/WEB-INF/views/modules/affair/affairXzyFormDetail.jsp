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
                formValues: false,
                afterPrint:function (){
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairXzy.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">种养点名称：</span><span class="modal-custom-info2-value">${affairXzy.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">小菜园面积（㎡）：</span><span class="modal-custom-info2-value">${affairXzy.vgArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">小果园面积（㎡）：</span><span class="modal-custom-info2-value">${affairXzy.orArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">果树数量（棵）：</span><span class="modal-custom-info2-value">${affairXzy.orNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">小养殖园面积（㎡）：</span><span class="modal-custom-info2-value">${affairXzy.yzArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鱼塘面积（㎡）：</span><span class="modal-custom-info2-value">${affairXzy.fpArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">经办人：</span><span class="modal-custom-info2-value">${affairXzy.jingBan}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申报单位审核人：</span><span class="modal-custom-info2-value">${affairXzy.unitShRen}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处工会审核意见：</span><span class="modal-custom-info2-value">${affairXzy.chuShOpinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">局工会审核意见：</span><span class="modal-custom-info2-value">${affairXzy.juShOpinion}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鸡（只）：</span><span class="modal-custom-info2-value">${affairXzy.ckNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鸭（只）：</span><span class="modal-custom-info2-value">${affairXzy.dkNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鹅（只）：</span><span class="modal-custom-info2-value">${affairXzy.geNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">猪（头）：</span><span class="modal-custom-info2-value">${affairXzy.pgNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">其他家禽家畜：</span><span class="modal-custom-info2-value">${affairXzy.otherNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">鱼（尾）：</span><span class="modal-custom-info2-value">${affairXzy.fhNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内容 ：</span><span class="modal-custom-info2-value">${affairXzy.content}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-file">
                    <div class="modal-custom-info1-file-1">附件：</div>
                    <div class="modal-custom-info1-file-r">
                        <c:forEach items="${filePathList}" var="m">
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