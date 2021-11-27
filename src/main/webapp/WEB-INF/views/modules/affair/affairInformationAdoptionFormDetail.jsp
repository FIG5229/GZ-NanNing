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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairInformationAdoption.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安处信息动态（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xxChu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部局信息动态（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xxBuJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安处工作简报（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.gzChu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部局工作简报（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.gzBuJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安处调研文章（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.dyChu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部局调研文章（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.dyBuJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安处领导批示：</span><span class="modal-custom-info2-value">${affairInformationAdoption.psChu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部局领导批示：</span><span class="modal-custom-info2-value">${affairInformationAdoption.psBuJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">地市级宣传报道：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xcDs}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">中央级宣传报道：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xcZy}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安局其他材料（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.otherJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部其他材料（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.otherBu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">排名：</span><span class="modal-custom-info2-value">${affairInformationAdoption.rank}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairInformationAdoption.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安局信息动态（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xxJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部信息动态（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xxBu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安局工作简报（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.gzJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部工作简报（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.gzBu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安局调研文章（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.dyJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部调研文章（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.dyBu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安局领导批示：</span><span class="modal-custom-info2-value">${affairInformationAdoption.psJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安部领导批示：</span><span class="modal-custom-info2-value">${affairInformationAdoption.psBu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">省部级宣传报道：</span><span class="modal-custom-info2-value">${affairInformationAdoption.xcSb}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">公安处其他材料（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.otherChu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">部局其他材料（篇）：</span><span class="modal-custom-info2-value">${affairInformationAdoption.otherBuJu}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">得分：</span><span class="modal-custom-info2-value">${affairInformationAdoption.score}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairInformationAdoption.remark}</span></div>
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