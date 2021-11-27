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
                                <span class="modal-custom-info2-key">机构：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.unit}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习开始时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairOrganzationStatistics.beginTime}"/></span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习结束时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairOrganzationStatistics.endTime}"/></span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">用户总数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.peopleSum}</span>
                            </div>
                        </div>
                    </div>　
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习总人数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studySum}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习率：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studyRatio}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习时间总数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studyTimeSum}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">参加学习用户平均学习时长：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studyTimeAvg}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">全体用户平均学习时长：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studyTimeAvgAll}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">学习总次数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.studyNumber}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">参加用户平均次数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.peopleAvg}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">全体用户平均学习次数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.peopleAvgAll}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">课程通过总数：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.coursePassNumber}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row">
                                <span class="modal-custom-info2-key">人均通过课程：</span>
                                <span class="modal-custom-info2-value">${affairOrganzationStatistics.coursePassPeople}</span>
                            </div>
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