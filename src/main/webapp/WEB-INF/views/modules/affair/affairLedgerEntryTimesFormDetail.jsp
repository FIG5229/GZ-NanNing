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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">二月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.february}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">四月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.april}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">六月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.june}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">八月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.august}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">十月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.october}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">十二月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.december}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">时间：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.yearMonth}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">一月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.january}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">三月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.march}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">五月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.may}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">七月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.july}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">九月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.september}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">十一月：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.november}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairLedgerEntryTimes.remark}</span></div>
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