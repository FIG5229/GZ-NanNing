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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairRewardInquire.policeNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairRewardInquire.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairRewardInquire.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩名称：</span><span class="modal-custom-info2-value">${affairRewardInquire.title}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩文号：</span><span class="modal-custom-info2-value">${affairRewardInquire.fileNo}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairRewardInquire.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位：</span><span class="modal-custom-info2-value">${affairRewardInquire.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairRewardInquire.type, 'affair_org_reward_punish', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖惩说明：</span><span class="modal-custom-info2-value">${affairRewardInquire.opinion}</span></div>

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
