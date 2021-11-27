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
                    $('.download').css('display', 'table-cell');
                }
            });
        });
    });

</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">用户名：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.nickName}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">姓名：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.name}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">警号：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.alarm}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">所属机构：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.unit}</span>
                        </div>
                    </div>
                </div>

                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学习途径：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.way}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">项目名称：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.projectName}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">课程编号：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.classNumber}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">课程名称：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.className}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">课程类别：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.classify}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学时：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.learnTime}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">播放时长：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.longTime}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">第一次学习时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairPoliceStatistics.firstTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">最后一次学习时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairPoliceStatistics.lastTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学习进度：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.schedule}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学习时长：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.learnTimeLong}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">是否通过：</span>
                            <span class="modal-custom-info2-value">${affairPoliceStatistics.isPass}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-custom-info1-bottom">
                <div id="print" class="modal-custom-info1-btn red">打印</div>
            </div>
        </div>
    </div>
</div>
