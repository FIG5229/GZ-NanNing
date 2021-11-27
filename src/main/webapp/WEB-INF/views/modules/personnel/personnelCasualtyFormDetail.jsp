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
                    <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelCasualty.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelCasualty.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCasualty.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCasualty.affirmDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复核时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCasualty.checkDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">种类与性质：</span><span class="modal-custom-info2-value">${personnelCasualty.type}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门与警种：</span><span class="modal-custom-info2-value">${personnelCasualty.departmentPolice}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">负伤程度：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCasualty.disabilityLevel, 'personnel_zclevel', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定部门：</span><span class="modal-custom-info2-value">${personnelCasualty.affirmDepartment}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门名称：</span><span class="modal-custom-info2-value">${personnelCasualty.department}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">复核部门：</span><span class="modal-custom-info2-value">${personnelCasualty.checkDepartment}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">牺牲/病故证明书编号：</span><span class="modal-custom-info2-value">${personnelCasualty.certificateNo1}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">负伤程度：</span><span class="modal-custom-info2-value">${personnelCasualty.injuredDegree}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤残评定机构：</span><span class="modal-custom-info2-value">${personnelCasualty.evaluateOrg}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否自杀：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelCasualty.isKill, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">负伤程度：</span><span class="modal-custom-info2-value">${personnelCasualty.injuredDegree}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡原因：</span><span class="modal-custom-info2-value">${personnelCasualty.reason}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">被伤害方式：</span><span class="modal-custom-info2-value">${personnelCasualty.method}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">执行勤务情况：</span><span class="modal-custom-info2-value">${personnelCasualty.dutySituation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡事件实力对比：</span><span class="modal-custom-info2-value">${personnelCasualty.comparison}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">使用武器警械：</span><span class="modal-custom-info2-value">${personnelCasualty.weaponry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伤亡情节：</span><span class="modal-custom-info2-value">${personnelCasualty.plot}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCasualty.occurDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事故性质：</span><span class="modal-custom-info2-value">${personnelCasualty.character}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">责任认定：</span><span class="modal-custom-info2-value">${personnelCasualty.decide}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">违章情况：</span><span class="modal-custom-info2-value">${personnelCasualty.situation}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈士标识：</span><span class="modal-custom-info2-value">${personnelCasualty.identification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${personnelCasualty.certificate2No}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelCasualty.approvalDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准部门：</span><span class="modal-custom-info2-value">${personnelCasualty.approvalDep}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">认定因公牺牲文件：</span><span class="modal-custom-info2-value">${personnelCasualty.sacrificeFile}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民政(人社)部门复核认定文件：</span><span class="modal-custom-info2-value">${personnelCasualty.checkFile}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">烈士批复文件：</span><span class="modal-custom-info2-value">${personnelCasualty.replyFile}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${personnelCasualty.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人民警察牺牲/病故证明书：</span><span class="modal-custom-info2-value">${personnelCasualty.proveBook}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核状态：</span><span class="modal-custom-info2-value">${personnelCasualty.status}</span></div>
                        </div>
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