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
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">单位：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.unit}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训名称：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.name}</span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训目的：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.goal}</span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训对象：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.target}</span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训内容：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.content}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">年度：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.year}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairTrainPlan.time}"/></span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">天数：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.day}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训地点：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.place}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">培训费（万元）：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.trainExpense}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">师资费（万元）：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.teacherExpense}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">列支渠道：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.trench}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">填报机构：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.organ}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">实施状态：</span>
                                <span class="modal-custom-info2-value">${affairTrainPlan.state}</span>
                            </div>
                        </div>
                    </div>
                    <div>
                        <span>附件：</span>
                        <c:forEach items="${filePathList}" var="m" varStatus="status">
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