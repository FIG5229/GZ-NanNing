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
                formValues: false
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">机构名称：</span><span class="modal-custom-info2-value">${affairOnlineLearning.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户总数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.totalUsers}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习率：</span><span class="modal-custom-info2-value">${affairOnlineLearning.learnRate}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加学习用户平均学习时长（小时）：</span><span class="modal-custom-info2-value">${affairOnlineLearning.cjxxyhpjxxsc}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总次数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.totalNumbers}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">全体用户平均学习次数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.qtyhpjxxcs}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人均通过课程：</span><span class="modal-custom-info2-value">${affairOnlineLearning.rjtgkc}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairOnlineLearning.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总人数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.totalLearn}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总时长（小时）：</span><span class="modal-custom-info2-value">${affairOnlineLearning.totalStudyTime}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">全体用户平均学习时长：</span><span class="modal-custom-info2-value">${affairOnlineLearning.qtyhpjxxsc}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加学习用户平均学习次数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.cjxxyhpjxxcs}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">课程通过总数：</span><span class="modal-custom-info2-value">${affairOnlineLearning.kctgzs}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairOnlineLearning.remark}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>