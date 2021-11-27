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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核年度：</span><span class="modal-custom-info2-value">${personnelYearCheck.year}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核结论：</span><span class="modal-custom-info2-value">${personnelYearCheck.conclusion}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${personnelYearCheck.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核单位名称：</span><span class="modal-custom-info2-value">${personnelYearCheck.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核单位代码：</span><span class="modal-custom-info2-value">${personnelYearCheck.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加考核的应到人数：</span><span class="modal-custom-info2-value">${personnelYearCheck.shouldNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实到考核人数：</span><span class="modal-custom-info2-value">${personnelYearCheck.realNum}</span></div>

                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核优秀得票数：</span><span class="modal-custom-info2-value">${personnelYearCheck.yxVoteNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核称职(合格)得票数：</span><span class="modal-custom-info2-value">${personnelYearCheck.czVoteNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核基本称职(基本合格)得票数：</span><span class="modal-custom-info2-value">${personnelYearCheck.jbczVoteNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核不称职(不合格)得票数：</span><span class="modal-custom-info2-value">${personnelYearCheck.bczVoteNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">弃权票数：</span><span class="modal-custom-info2-value">${personnelYearCheck.qqVoteNum}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核组成员：</span><span class="modal-custom-info2-value">${personnelYearCheck.member}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">考核备注：</span><span class="modal-custom-info2-value">${personnelYearCheck.remark}</span></div>

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