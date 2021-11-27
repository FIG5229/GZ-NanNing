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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学位名称：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授予日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAcademicDegree.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授予单位：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授予国家：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAcademicDegree.country, 'personnel_guojia', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最高学位说明：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.explain}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">多学位序号：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.sequenceNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证书编号：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">记录状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelAcademicDegree.status, 'personnel_jltype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学位名称代码：</span><span class="modal-custom-info2-value">${personnelAcademicDegree.code}</span></div>
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