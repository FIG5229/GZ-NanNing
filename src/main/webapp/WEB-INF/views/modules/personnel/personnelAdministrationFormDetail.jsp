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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务名称代码：</span><span class="modal-custom-info2-value">${personnelAdministration.code}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务名称：</span><span class="modal-custom-info2-value">${personnelAdministration.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.status, 'personnel_rztype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.jobType, 'personnel_zwlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务层次：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.level, 'personnel_zwcc', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多职务主次序号：</span><span class="modal-custom-info2-value">${personnelAdministration.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">连续任该职起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAdministration.startDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">班子成员类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.memberType, 'personnel_bzcylb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否破格提拔：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.isBreakRule, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">分管工作：</span><span class="modal-custom-info2-value">${personnelAdministration.fgWork}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">岗位类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.jobCategory, 'personnel_gwlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">集体内排列顺序：</span><span class="modal-custom-info2-value">${personnelAdministration.setOrder}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">股级任职时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAdministration.gjrzDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">股级：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.stockLevel, 'personnel_guji', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务说明：</span><span class="modal-custom-info2-value">${personnelAdministration.explain}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${personnelAdministration.approvalOrgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${personnelAdministration.approvalOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${personnelAdministration.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAdministration.workDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职变动类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.changeType, 'personnel_rzbdlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职方式：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.method, 'personnel_rzfs', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否班子成员：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.isMember, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构名称：</span><span class="modal-custom-info2-value">${personnelAdministration.rzjgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构代码：</span><span class="modal-custom-info2-value">${personnelAdministration.rzjgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构所在政区：</span><span class="modal-custom-info2-value">${personnelAdministration.rzjgSteer}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构隶属关系：</span><span class="modal-custom-info2-value">${personnelAdministration.rzjgAffiliation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构名称类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.rzjgNameType, 'personnel_rzjgmclb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构性质类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.rzjgNatureType, 'personnel_rzjgxzlb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构所属行业：</span><span class="modal-custom-info2-value">${personnelAdministration.rzjgIndustry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">任职机构级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.rzjgLevel, 'personnel_rzjgjb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准免职机关名称：</span><span class="modal-custom-info2-value">${personnelAdministration.pzmzjgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准免职机关代码：</span><span class="modal-custom-info2-value">${personnelAdministration.pzmzjgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准免职文号：</span><span class="modal-custom-info2-value">${personnelAdministration.pzmzNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">免职原因类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAdministration.mzyyType, 'personnel_mzyylb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准免职日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAdministration.pzmzDate}" pattern="yyyy-MM-dd"/></span></div>

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