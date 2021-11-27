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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考试：</span><span class="modal-custom-info2-value">${affairGunTheory.exam}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairGunTheory.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">机构：</span><span class="modal-custom-info2-value">${affairGunTheory.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交卷时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairGunTheory.handoverTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主观成绩：</span><span class="modal-custom-info2-value">${affairGunTheory.subjectivePerformance}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">通过状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairGunTheory.status, 'pass_status', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairGunTheory.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairGunTheory.policeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件号码：</span><span class="modal-custom-info2-value">${affairGunTheory.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">进入考试时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairGunTheory.enterTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">客观成绩：</span><span class="modal-custom-info2-value">${affairGunTheory.objectiveResults}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最终成绩：</span><span class="modal-custom-info2-value">${affairGunTheory.finalResult}</span></div>
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