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
                <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelRetreat.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.type, 'personnel_lttype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelRetreat.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退前级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.type, 'personnel_ltqtype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退后现管理单位名称：</span><span class="modal-custom-info2-value">${personnelRetreat.nowUnitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">曾任最高职务：</span><span class="modal-custom-info2-value">${personnelRetreat.highestJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退休费支付单位名称：</span><span class="modal-custom-info2-value">${personnelRetreat.payOrgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退干部现享受待遇：</span><span class="modal-custom-info2-value">${personnelRetreat.treatment}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退干部现享受待遇类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.type, 'personnel_ltdytype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退后现管理单位代码：</span><span class="modal-custom-info2-value">${personnelRetreat.nowUnitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退后现居住政区：</span><span class="modal-custom-info2-value">${personnelRetreat.liveArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退休费标准（%）：</span><span class="modal-custom-info2-value">${personnelRetreat.standard}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退休费支付单位代码：</span><span class="modal-custom-info2-value">${personnelRetreat.payOrgCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退后管理类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.type, 'personnel_ltgltype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退备注：</span><span class="modal-custom-info2-value">${personnelRetreat.remarks}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退休批准文号：</span><span class="modal-custom-info2-value">${personnelRetreat.approvalFileNo}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                           <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否参加93工改：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.is93, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否红军：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.isRedArmy, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否孤寡：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.isLonely, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">生活是否能自理：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.isSelfCare, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否无子女：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelRetreat.isHasChild, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离休干部1-2月生活补贴：</span><span class="modal-custom-info2-value">${personnelRetreat.subsidy}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">老粮贴：</span><span class="modal-custom-info2-value">${personnelRetreat.lnt}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退时职务工资：</span><span class="modal-custom-info2-value">${personnelRetreat.jobSalary}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离退时级别工资：</span><span class="modal-custom-info2-value">${personnelRetreat.levelSalary}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系方式：</span><span class="modal-custom-info2-value">${personnelRetreat.contactMethod}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提前退休标识：</span><span class="modal-custom-info2-value">${personnelRetreat.identification}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提前退休原因：</span><span class="modal-custom-info2-value">${personnelRetreat.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提前离岗日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelRetreat.leaveDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提前离岗前工作单位名称：</span><span class="modal-custom-info2-value">${personnelRetreat.preUnitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提前离岗前职务层次：</span><span class="modal-custom-info2-value">${personnelRetreat.preJobLevel}</span></div>
                        </div>
                    </div>
                    <div>
                        <span>离退休材料：</span>
                        <c:forEach items="${filePathList}" var="m" >
                            <div>
                                <span>${m.fileName}</span>
                                <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                            </div>
                        </c:forEach>
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