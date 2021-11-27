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
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairChildSubsidy.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairChildSubsidy.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警姓名：</span><span class="modal-custom-info2-value">${affairChildSubsidy.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民警性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairChildSubsidy.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">补助金额：</span><span class="modal-custom-info2-value">${affairChildSubsidy.money}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${affairChildSubsidy.job}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairChildSubsidy.job, 'affair_job', '')}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">审核意见：</span><span class="modal-custom-info2-value">${affairChildSubsidy.opinion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女姓名：</span><span class="modal-custom-info2-value">${affairChildSubsidy.childName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairChildSubsidy.childSex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairChildSubsidy.childBirthDay}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">子女现状：</span><span class="modal-custom-info2-value">${affairChildSubsidy.childNow}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairChildSubsidy.remarks}</span></div>--%>
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