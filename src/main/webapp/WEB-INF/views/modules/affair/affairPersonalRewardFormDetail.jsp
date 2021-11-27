<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
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
                formValues: false,
                afterPrint:function(){
                    $('.download').css('display', '');
                }
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
<%--
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称代码：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.nameCode, 'affair_personnel_rewardCode', '')}</span></div>
--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件文号：</span><span class="modal-custom-info2-value">${affairPersonalReward.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称：</span><span class="modal-custom-info2-value">${affairPersonalReward.fileName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称：</span><span class="modal-custom-info2-value">${affairPersonalReward.rewardName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖励时职务：</span><span class="modal-custom-info2-value">${affairPersonalReward.job}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖励时职务层次：</span><span class="modal-custom-info2-value">${affairPersonalReward.jobLevel}</span></div>
<%--
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">荣誉称号级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.level, 'affair_chenghao_level', '')}</span></div>
--%>                        <%--12.18 调整为与批准机关层次字典一样--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">荣誉级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.level, 'affair_approval_unitLevel', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">追授标志：</span><span class="modal-custom-info2-value">${affairPersonalReward.flag}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">享受待遇类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.daiyuType, 'affair_daiyu_type', '')}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励原因：</span><span class="modal-custom-info2-value">${affairPersonalReward.yuanyin}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关名称：</span><span class="modal-custom-info2-value">${affairPersonalReward.unit}</span></div>
<%--
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关代码：</span><span class="modal-custom-info2-value">${affairPersonalReward.unitCode}</span></div>
--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.unitType, 'affair_approval_unitType', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关层次：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPersonalReward.unitLevel, 'affair_approval_unitLevel', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonalReward.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span><span class="modal-custom-info2-value">${affairPersonalReward.deedsMaterial}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销标志：</span><span class="modal-custom-info2-value">${affairPersonalReward.chexiaoFlag}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPersonalReward.reDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销原因：</span><span class="modal-custom-info2-value">${affairPersonalReward.cxYuanyin}</span></div>
                        </div>
                    </div>
                    <div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
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