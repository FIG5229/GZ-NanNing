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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境时单位及职务：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.unitJob}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所至国别(地区)：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.toPlace}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国(境)团组名称：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.groupName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国(境)审批单位名称：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.approvalUnitName}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国(境)审批单位文号：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.approvalFileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">派出单位：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.assignUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所至单位：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.toUnit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国(境)经费来源：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.fundsSource}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">本次使用护照(通行证)编号：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.passportNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">附件：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.annex}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境身份：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.identity}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境性质：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.character}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境事由：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.reason}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">持有护照(通行证)上交日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelGoAbroadTwo.handinDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">持有护照(通行证)领用日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelGoAbroadTwo.receiveDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelGoAbroadTwo.goAbroadDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">回国日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelGoAbroadTwo.returnDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出国境性质：</span><span class="modal-custom-info2-value">${personnelGoAbroadTwo.beizhu}</span></div>
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