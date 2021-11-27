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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退出现役类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelTcxyjr.retiredType, 'personnel_tcxylb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入伍日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelTcxyjr.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">入伍地：</span><span class="modal-custom-info2-value">${personnelTcxyjr.place}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准退伍日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelTcxyjr.approveDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时工作单位名称：</span><span class="modal-custom-info2-value">	${personnelTcxyjr.tyUnitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时工作单位所在政区：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyUnitSteer}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时职务代码：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时职务名称：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyName}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时军衔：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyRank}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时职务层次：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时职务层次起算日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelTcxyjr.tyJobDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时技术职称：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyTitles}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时工资类别档次：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelTcxyjr.tyMoneyLevel, 'personnel_tygzlevel', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">退役时职务工资金额：</span><span class="modal-custom-info2-value">${personnelTcxyjr.tyMoney}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否服预备役：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelTcxyjr.isreserve, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">93年工改时职务：</span><span class="modal-custom-info2-value">${personnelTcxyjr.job93}</span></div>

                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
