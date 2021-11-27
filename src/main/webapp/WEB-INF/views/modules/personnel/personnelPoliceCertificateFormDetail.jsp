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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceCertificate.birthday}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceCertificate.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警察证号：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">制发原因：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceCertificate.createReason, 'certification_type', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警察证状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(personnelPoliceCertificate.status, 'personnel_jcztype', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发证日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceCertificate.sendDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">有效期起始日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceCertificate.startState}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部门：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.department}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">级别：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.jobLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">有效期截至日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceCertificate.endDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">发证机关：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.orgName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">收回销毁日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPoliceCertificate.destroyDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">收回原因：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.backReason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核人：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.examinePerson}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${personnelPoliceCertificate.remark}</span></div>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div  class="modal-custom-info1-btn red" id="print">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>