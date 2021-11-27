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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖单位：</span><span class="modal-custom-info2-value">${affairXcUnitReward.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">受奖单位代码：</span><span class="modal-custom-info2-value">${affairXcUnitReward.unitCode}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">建制标志：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.flag, 'yes_no', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.nameCode, 'affair_reward_code', '')}&nbsp&nbsp&nbsp${affairXcUnitReward.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">荣誉级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.level, 'affair_approval_unitLevel', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位警种：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.unitPolice, 'affair_unit_police', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准机关类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.approvalUnitType, 'affair_approval_unitType', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span><span class="modal-custom-info2-value">${affairXcUnitReward.deedsMaterial}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
<%--
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">奖励名称：</span><span class="modal-custom-info2-value">${affairXcUnitReward.name}</span></div>
--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.approvalUnit, 'affair_approval_unit', '')}</span></div>
<%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准单位级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXcUnitReward.approvalUnitLevel, 'affair_approval_unitLevel', '')}</span></div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairXcUnitReward.date}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文号：</span><span class="modal-custom-info2-value">${affairXcUnitReward.fileNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">批准文件名称：</span><span class="modal-custom-info2-value">${affairXcUnitReward.fileName}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销奖励日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairXcUnitReward.reDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">备注：</span><span class="modal-custom-info2-value">${affairXcUnitReward.remark}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">撤销奖励批准单位：</span><span class="modal-custom-info2-value">${affairXcUnitReward.reUnit}</span></div>

                        </div>
                    </div>
                   <%-- <div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>--%>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>