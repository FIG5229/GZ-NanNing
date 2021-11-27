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
                            <%--${fns:getDictLabel(personnelSkill.gradeDegree, 'personnel_djcd', '')}--%>
                            <div class="modal-custom-info2-row"><span style="margin-left: 142px">单位：</span><span>${personnelSkill.unit}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 142px">姓名：</span><span >${personnelSkill.personnelName}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 112px">身份证号：</span><span >${personnelSkill.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 142px">性别：</span><span >${fns:getDictLabel(personnelSkill.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 112px">出生年月：</span><span ><fmt:formatDate value="${personnelSkill.birthday}" pattern="yyyy-MM-dd HH:mm"/></span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 142px">特长：</span><span >${personnelSkill.speciality}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 112px">人才类别：</span><span >${fns:getDictLabel(personnelSkill.category, 'talents_category', '')}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 112px">人才名称：</span><span >${personnelSkill.talentsName}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 112px">享受待遇：</span><span >${personnelSkill.talentsWelfare}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 28px">人才用久居留权地名称：</span><span >${personnelSkill.resideAddress}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 85px">专业类别描述：</span><span >${personnelSkill.specialtyCategoryDescribe}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 57px">人才称号批准日期：</span><span ><fmt:formatDate value="${personnelSkill.titleRatifyDate}" pattern="yyyy-MM-dd HH:mm"/></span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 55px">人才称号批准单位：</span><span >${personnelSkill.titleRatifyUnit}</span></div>
                            <div class="modal-custom-info2-row"><span >人才称号批准单位隶属关系：</span><span >${personnelSkill.titleRatifyUnitGrade}</span></div>
                            <div class="modal-custom-info2-row"><span style="margin-left: 28px">人才称号批准单位级别：</span><span>${fns:getDictLabel(personnelSkill.titleRatifyUnitGrade, 'unit_rank', '')}</span></div>
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