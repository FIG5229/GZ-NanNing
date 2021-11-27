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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairThreeBase.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证：</span><span class="modal-custom-info2-value">${affairThreeBase.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年龄：</span><span class="modal-custom-info2-value">${affairThreeBase.age}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基本知识：</span><span class="modal-custom-info2-value">${affairThreeBase.basicKnowledge}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基本技能：</span><span class="modal-custom-info2-value">${affairThreeBase.baseSkill}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成绩总评：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairThreeBase.overallScore, 'pass_status', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairThreeBase.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairThreeBase.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairThreeBase.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairThreeBase.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">基本体能：</span><span class="modal-custom-info2-value">${affairThreeBase.basicFitness}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairThreeBase.remark}</span></div>
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