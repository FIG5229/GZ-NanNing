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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelSalary.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelSalary.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务工资：</span><span class="modal-custom-info2-value">${personnelSalary.jobSalary}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">级别工资：</span><span class="modal-custom-info2-value">${personnelSalary.levelSalary}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">年终一次性奖金：</span><span class="modal-custom-info2-value">${personnelSalary.bonus}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">变动日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelSalary.changeDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance1}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作性津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance2}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">生活性津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance3}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">改革性补贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance4}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">执勤岗位津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance5}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">法定工作日外加班补贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance6}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">艰苦边远地区津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance7}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">国家统一规定的其他津贴：</span><span class="modal-custom-info2-value">${personnelSalary.allowance8}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>