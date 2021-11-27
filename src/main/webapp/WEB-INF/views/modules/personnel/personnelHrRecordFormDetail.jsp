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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelHrRecord.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${personnelHrRecord.duty}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案来处：</span><span class="modal-custom-info2-value">${personnelHrRecord.source}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案转入日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelHrRecord.intoDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案来处：</span><span class="modal-custom-info2-value">${personnelHrRecord.source}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案编号：</span><span class="modal-custom-info2-value">${personnelHrRecord.recordNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案版本类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelHrRecord.type, 'personnel_banben', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案转出日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelHrRecord.outDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案去处：</span><span class="modal-custom-info2-value">${personnelHrRecord.toPlace}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">级别：</span><span class="modal-custom-info2-value">${personnelHrRecord.level}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门：</span><span class="modal-custom-info2-value">${personnelHrRecord.department}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案管理单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelHrRecord.unit, 'personnel_daunit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">正本卷数：</span><span class="modal-custom-info2-value">${personnelHrRecord.ZNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">副本卷数：</span><span class="modal-custom-info2-value">${personnelHrRecord.FNum2}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案位置：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelHrRecord.location, 'personnel_daunit', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案条形码号：</span><span class="modal-custom-info2-value">${personnelHrRecord.barCodeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">档案备注：</span><span class="modal-custom-info2-value">${personnelHrRecord.remark}</span></div>
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