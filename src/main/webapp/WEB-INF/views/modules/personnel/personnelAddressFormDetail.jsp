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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位通信地址：</span><span class="modal-custom-info2-value">${personnelAddress.unitAdress}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作单位邮政编码：</span><span class="modal-custom-info2-value">${personnelAddress.unitPostCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作电话：</span><span class="modal-custom-info2-value">${personnelAddress.workTel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭住址：</span><span class="modal-custom-info2-value">${personnelAddress.familyAddress}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">个人电子信箱：</span><span class="modal-custom-info2-value">${personnelAddress.email}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内部电话（小号或专线）：</span><span class="modal-custom-info2-value">${personnelAddress.innerTel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">移动电话号码：</span><span class="modal-custom-info2-value">${personnelAddress.mobilePhone}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭电话：</span><span class="modal-custom-info2-value">${personnelAddress.homeTel}</span></div>
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