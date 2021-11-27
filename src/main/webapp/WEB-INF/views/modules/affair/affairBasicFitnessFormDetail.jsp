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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairBasicFitness.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairBasicFitness.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairBasicFitness.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">体重（公斤）：</span><span class="modal-custom-info2-value">${affairBasicFitness.weight}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身体质量指数（BMI）评定：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.assess, 'fitness_assess', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">50米跑成绩（秒）：</span><span class="modal-custom-info2-value">${affairBasicFitness.fiftyRunScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">仰卧起坐成绩（个）：</span><span class="modal-custom-info2-value">${affairBasicFitness.nvSitUpsScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">引体向上成绩（个）：</span><span class="modal-custom-info2-value">${affairBasicFitness.nanPullUpsScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">俯卧撑成绩（个）：</span><span class="modal-custom-info2-value">${affairBasicFitness.nanPushUpsScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">握力成绩（公斤）：</span><span class="modal-custom-info2-value">${affairBasicFitness.gripScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">立定跳远成绩（米）：</span><span class="modal-custom-info2-value">${affairBasicFitness.jumpScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">2000米跑成绩（分+秒）：</span><span class="modal-custom-info2-value">${affairBasicFitness.twokmRunScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">坐位体前屈成绩（厘米）：</span><span class="modal-custom-info2-value">${affairBasicFitness.sittingForwardBendingScore}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">综合评定：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.comprehensiveAssessment, 'passs_status', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairBasicFitness.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件号：</span><span class="modal-custom-info2-value">${affairBasicFitness.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身高（米）：</span><span class="modal-custom-info2-value">${affairBasicFitness.height}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身体质量指数（BMI）数值：</span><span class="modal-custom-info2-value">${affairBasicFitness.value}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">50米跑达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.fiftyRunStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">仰卧起坐达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.nvSitUpsStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">引体向上达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.nanPullUpsStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">俯卧撑达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.nanPushIpsStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">握力达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.gripStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">立定跳远达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.jumpStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">2000米跑达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.twokmRunStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">坐位体前屈达标情况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairBasicFitness.sittingForwardBendingStatus, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairBasicFitness.remark}</span></div>
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