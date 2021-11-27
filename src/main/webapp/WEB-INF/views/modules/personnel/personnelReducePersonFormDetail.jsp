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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离开本单位类别：</span><span class="modal-custom-info2-value">${personnelReducePerson.oldUnitType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位名称：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位所在政区：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitArea}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位级别：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位所属行业：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitIndustry}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">文号：</span><span class="modal-custom-info2-value">${personnelReducePerson.fileNo}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">离开本单位日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelReducePerson.oldUnitDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位代码：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位隶属关系：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitRelationship}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调往单位性质类别：</span><span class="modal-custom-info2-value">${personnelReducePerson.toUnitType}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调动原因：</span><span class="modal-custom-info2-value">${personnelReducePerson.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">调出备注：</span><span class="modal-custom-info2-value">${personnelReducePerson.remark}</span></div>

                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
