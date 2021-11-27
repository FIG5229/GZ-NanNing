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
                <div class="modal-custom-content">
                    <div class="modal-custom-info2" id="contentTable">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">教官类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelInstructor.type, 'personnel_instype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">聘用级别：</span><span class="modal-custom-info2-value">${personnelInstructor.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">聘用日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInstructor.employDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评审日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInstructor.reviewDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">拟解聘日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelInstructor.dismissDate}" pattern="yyyy-MM-dd"/></span></div>
                            </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评审人员：</span><span class="modal-custom-info2-value">${personnelInstructor.reviewPerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">聘用单位：</span><span class="modal-custom-info2-value">${personnelInstructor.employUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">教官特长：</span><span class="modal-custom-info2-value">${personnelInstructor.speciality}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否为终身制：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelInstructor.isLife, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评审理由：</span><span class="modal-custom-info2-value">${personnelInstructor.reason}</span></div>
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