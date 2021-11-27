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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelAward.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖项目名称：</span><span class="modal-custom-info2-value">${personnelAward.projectName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖名称：</span><span class="modal-custom-info2-value">${personnelAward.awardName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授奖单位：</span><span class="modal-custom-info2-value">${personnelAward.grantUnit}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">授奖单位级别：</span><span class="modal-custom-info2-value">${personnelAward.grantUnitLevel}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖顺序：</span><span class="modal-custom-info2-value">${personnelAward.awardSort}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖金额：</span><span class="modal-custom-info2-value">${personnelAward.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所获奖励等材料的图片资料：</span><span class="modal-custom-info2-value">${personnelAward.material}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">获奖备注：</span><span class="modal-custom-info2-value">${personnelAward.remark}</span></div>
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