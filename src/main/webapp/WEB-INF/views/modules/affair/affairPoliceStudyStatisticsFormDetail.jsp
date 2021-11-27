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
                    <c:if test="${list== null || fn:length(list) == 0}">
                        <span>未检索到该用户课程信息</span>
                    </c:if>
                    <c:forEach items="${list}" var="affairPoliceStatistics" varStatus="status">
                        <br>
                        <span>第${status.index+1}门课程</span>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户名：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.nickName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">课程编号：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.classNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">第一次学习时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPoliceStatistics.firstTime}" pattern="yyyy-MM-dd" /></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总时长：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.learnTimeLong}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习进度：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.schedule}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">课程名称：</span><span class="modal-custom-info2-value">${affairPoliceStatistics.className}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最后一次学习时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPoliceStatistics.lastTime}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>