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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">名称：</span><span class="modal-custom-info2-value">${examWeights.name}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span class="modal-custom-info2-value">${examWeights.department}</span></div>
                        </div>
                    </div>
                    <table id="contentTables" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>工作名称</th>
                            <th>权重分数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${examWeights.examWeightsMainList}" var="examWeightsMain">
                            <tr>
                                <td>
                                        ${fns:getDictLabel(examWeightsMain.workName, 'exam_weigths', '')}
                                </td>
                                <td>
                                        ${examWeightsMain.weights}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <form:form id="searchForm" method="post" class="breadcrumb form-search">
                <input id="id" name="id" type="hidden" value="${examWeights.id}"/>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                        <%-- <shiro:hasPermission name="affair:affairOneHelpOne:edit">
                             <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                         </shiro:hasPermission>--%>
                </div>
            </div>
            </form:form>
        </div>
    </div>
</div>