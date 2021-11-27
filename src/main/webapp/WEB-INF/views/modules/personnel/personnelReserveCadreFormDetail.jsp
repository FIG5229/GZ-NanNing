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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报单位名称：</span><span class="modal-custom-info2-value">${personnelReserveCadre.unitName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelReserveCadre.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培养目标任职机构类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelReserveCadre.targetOrgType, 'personnel_pymbrzjglb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培养目标职务类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelReserveCadre.targetJobTypr, 'personnel_pymbzwlb', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">呈报单位代码：</span><span class="modal-custom-info2-value">${personnelReserveCadre.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培养目标（方向）：</span><span class="modal-custom-info2-value">${personnelReserveCadre.target}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培养目标职务级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelReserveCadre.targetJobLevel, 'personnel_pymbzwjb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">后备类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelReserveCadre.reserveType, 'personnel_hblb', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">当前状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelReserveCadre.status, 'personnel_dqzt', '')}</span></div>
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