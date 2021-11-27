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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">进入本单位日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAddPerson.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位增员类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAddPerson.addType, 'personnel_zytype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">进入本单位原因：</span><span class="modal-custom-info2-value">${personnelAddPerson.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">本单位所在地区类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAddPerson.unitAreaType, 'personnel_dqtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">校正工资档次：</span><span class="modal-custom-info2-value">${personnelAddPerson.reviseGrade}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">校正日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAddPerson.reviseDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">起薪日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAddPerson.startSalaryDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位名称：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位代码：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位名称类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAddPerson.unitNameType, 'personnel_nametype', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                           <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位所在政区：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位隶属关系：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitRelationship}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位级别：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位性质类别：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitCharacterType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位所属行业：</span><span class="modal-custom-info2-value">${personnelAddPerson.unitIndustry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在地区类别：</span><span class="modal-custom-info2-value">${personnelAddPerson.areaType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">个人身份：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAddPerson.identity, 'personnel_grsf', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">在原单位职务：</span><span class="modal-custom-info2-value">${personnelAddPerson.oldJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">在原单位级别：</span><span class="modal-custom-info2-value">${personnelAddPerson.oldLevel}</span></div>
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