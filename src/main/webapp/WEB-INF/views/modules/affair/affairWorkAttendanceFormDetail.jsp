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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairWorkAttendance.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairWorkAttendance.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警种：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairWorkAttendance.policeType, 'affair_kaoqin_jingzhong', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">当月工时：</span><span class="modal-custom-info2-value">${affairWorkAttendance.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">缺勤：</span><span class="modal-custom-info2-value">${affairWorkAttendance.absence}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工伤：</span><span class="modal-custom-info2-value">${affairWorkAttendance.jobInjury}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年休：</span><span class="modal-custom-info2-value">${affairWorkAttendance.annualRest}</span></div>
                         </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出差：</span><span class="modal-custom-info2-value">${affairWorkAttendance.evection}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">执勤：</span><span class="modal-custom-info2-value">${affairWorkAttendance.onDuty}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加班：</span><span class="modal-custom-info2-value">${affairWorkAttendance.overtime}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">零星加班：</span><span class="modal-custom-info2-value">${affairWorkAttendance.lxOvertime}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairWorkAttendance.unit}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年：</span><span class="modal-custom-info2-value">${affairWorkAttendance.year}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairWorkAttendance.date}" pattern="yyyy-MM-dd"/></span></div>
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