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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目名称：</span><span class="modal-custom-info2-value">${personnelAchievement.projectName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目编号：</span><span class="modal-custom-info2-value">${personnelAchievement.projectNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAchievement.type, 'personnel_xmtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目领域：</span><span class="modal-custom-info2-value">${personnelAchievement.filed}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目下达单位名称：</span><span class="modal-custom-info2-value">${personnelAchievement.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目密级：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAchievement.secretLevel, 'personnel_xmmj', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAchievement.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAchievement.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参与该项目起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAchievement.joinStartDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参与该项目截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAchievement.joinEndDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">在项目中担任角色：</span><span class="modal-custom-info2-value">${personnelAchievement.role}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">在项目中担任角色的排序：</span><span class="modal-custom-info2-value">${personnelAchievement.roleSort}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                           <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">项目支持单位：</span><span class="modal-custom-info2-value">${personnelAchievement.supportUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果名称：</span><span class="modal-custom-info2-value">${personnelAchievement.achievementName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果编号：</span><span class="modal-custom-info2-value">${personnelAchievement.achievementNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAchievement.achievementType, 'personnel_cgtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果水平：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAchievement.achievementLevel, 'personnel_cgsp', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果鉴定单位名称：</span><span class="modal-custom-info2-value">${personnelAchievement.appraiseUnitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果鉴定单位代码：</span><span class="modal-custom-info2-value">${personnelAchievement.appraiseUnitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果评述：</span><span class="modal-custom-info2-value">${personnelAchievement.comment}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">成果创新性标识：</span><span class="modal-custom-info2-value">${personnelAchievement.identification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所需上报材料的图片资料：</span><span class="modal-custom-info2-value">${personnelAchievement.material}</span></div>
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