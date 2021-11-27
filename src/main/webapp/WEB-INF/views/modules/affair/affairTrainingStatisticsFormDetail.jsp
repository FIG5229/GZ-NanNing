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
                            <span class="modal-custom-info2-key">培训班编号：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.number}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">培训班名称：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.className}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">主办部门：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.unit}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">培训方式：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.trainingMethod}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">开始时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairTrainingStatistics.beginTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">结束时间：</span>
                            <span class="modal-custom-info2-value"><fmt:formatDate  pattern="yyyy-MM-dd" value="${affairTrainingStatistics.endTime}"/></span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">课程总数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.classSum}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">开班状态：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.beginState}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">结项状态：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.endState}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">评估分数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.evaluationScore}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">应参训人数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.shouldJoin}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">实际参训人数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.trueJoin}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">参训率：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.participationNumber}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">已通过人数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.passedNumber}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">未通过人数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.notPassedNumber}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">通过率：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.passRatio}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">师资费(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.teacherCost}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">住宿费(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.accommodationCost}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">伙食费(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.foodCost}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">场地资料交通费(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.siteCost}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">其他费用(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.otherCost}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">费用总额(万元)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.costSum}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学习总时长(小时)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.learnTimeSum}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">平均学习时长(小时)：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.learnTimeAvg}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">学习总次数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.countSum}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">平均学习次数：</span>
                            <span class="modal-custom-info2-value">${affairTrainingStatistics.countAvg}</span>
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
