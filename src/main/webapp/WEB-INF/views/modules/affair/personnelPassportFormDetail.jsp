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

                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号/证件编号：</span><span class="modal-custom-info2-value">${personnelPassport.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证照名称：</span><span class="modal-custom-info2-value">${personnelPassport.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证照姓名：</span><span class="modal-custom-info2-value">${personnelPassport.personName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">签发地：</span><span class="modal-custom-info2-value">${personnelPassport.place}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">签发日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPassport.issueDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证照有效截止日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPassport.toDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件编号：</span><span class="modal-custom-info2-value">${personnelPassport.certificateNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">办理证件事由：</span><span class="modal-custom-info2-value">${personnelPassport.reason}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                           <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件状态：</span><span class="modal-custom-info2-value">${personnelPassport.status}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件领用日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPassport.receiveDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件交还日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPassport.returnDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件存档编号：</span><span class="modal-custom-info2-value">${personnelPassport.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销备案日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelPassport.cancelDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件多媒体信息：</span><span class="modal-custom-info2-value">${personnelPassport.media}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">签发机关：</span><span class="modal-custom-info2-value">${personnelPassport.issueOrg}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">保管机关名称及人员：</span><span class="modal-custom-info2-value">${personnelPassport.saveOrgPer}</span></div>
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