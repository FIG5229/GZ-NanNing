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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查时间：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${examCheck.checkDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2" style="width: 410px;">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查单位：</span><span
                                    class="modal-custom-info2-value" style="width: 285px;">${examCheck.checkUnit}</span>
                            </div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col3">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">检查人：</span><span
                                    class="modal-custom-info2-value">${examCheck.checkPerson}</span></div>
                        </div>
                    </div>
                    <table id="contentTables" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>层次</th>
                            <th>使用模板</th>
                            <th>选择项</th>
                            <th>责任单位</th>
                            <th>责任领导</th>
                            <th>责任人</th>
                            <th>责任单位扣分</th>
                            <th>责任领导扣分</th>
                            <th>责任人扣分</th>
<%--                            <th>绩效考评标准</th>--%>
                            <th>扣分情况</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${examCheck.examCheckChildList}" var="examCheckChild">
                            <tr>
                                <td>
                                        ${fns:getDictLabel(examCheckChild.type, "kp_type","" )}
                                </td>
                                <td>
                                        ${examCheckChild.useModelName}
                                </td>
                                <td>
                                        ${examCheckChild.chooseOptionsName}
                                </td>
                                <td>
                                        ${examCheckChild.dutyUnit}
                                </td>
                                <td>
                                        ${examCheckChild.dutyLeader}
                                </td>
                                <td>
                                        ${examCheckChild.dutyPerson}
                                </td>
                                <td>
                                        ${examCheckChild.dutyUnitScore}
                                </td>
                                <td>
                                        ${examCheckChild.dutyLeaderScore}
                                </td>
                                <td>
                                        ${examCheckChild.dutyPersonScore}
                                </td>
                                <%--<td>
                                        ${examCheckChild.testStandart}
                                </td>--%>
                                <td>
                                        ${examCheckChild.scortSituation}
                                </td>
                                <td>
                                        ${examCheckChild.remark}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>