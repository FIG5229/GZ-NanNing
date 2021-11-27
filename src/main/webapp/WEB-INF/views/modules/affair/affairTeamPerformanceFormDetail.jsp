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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">比武开始时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTeamPerformance.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">比武名称：</span><span class="modal-custom-info2-value">${affairTeamPerformance.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目一：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemOne}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目三：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemThree}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目五：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemFive}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">总成绩：</span><span class="modal-custom-info2-value">${affairTeamPerformance.score}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairTeamPerformance.remark}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">比武结束时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTeamPerformance.finishDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairTeamPerformance.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目二：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemTwo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目四：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemFour}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目六：</span><span class="modal-custom-info2-value">${affairTeamPerformance.itemSix}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">排名：</span><span class="modal-custom-info2-value">${affairTeamPerformance.rank}</span></div>
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