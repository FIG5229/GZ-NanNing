<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>

<!-- 绩效考评-系统公示 -->
<div id="modalSysPublic" class="">
    <div class="">
        <div class="modal-custom-main">
            <%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
            <div class="modal-custom-content">
                <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>单位名称</th>
                        <th>总得分</th>
                        <th>基础分</th>
                        <th>常规业务扣分</th>
                        <th>重点业务扣分</th>
                        <th>得分制工作项得分</th>
                        <th>公共扣分</th>
                        <th>公共加分</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%--<c:forEach items="${resultList}" var="obj">
                        <tr>
                            <td>${obj.objName}</td>
                            <td style="color: #2429FF;">
                                <a href="${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${examWorkflowId}&goExamIndex=goExamIndex">
                                        ${obj.totaltScore}
                                </a>
                            </td>
                            <td>${obj.totalPublicAddScore}</td>
                            <td>${obj.totalPublicDeductScore}</td>
                            <td>${obj.totalWeightScore}</td>
                        </tr>
                    </c:forEach>--%>
                   <c:forEach items="${resultList}" var="obj">
                        <c:if test="${obj.objName == '南宁公安处'}">
                            <tr>
                                <td>${obj.objName}</td>
                                <td style="color: #2429FF;">
                                    <c:choose>
                                        <c:when test="${fns:getUser().id eq 'cca66e1339f14799b01f6db43ed16e16'}">
                                            <a href="${ctx}/exam/examWorkflow/createTable?workflowId=${examWorkflowId}">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${examWorkflowId}&goExamIndex=goExamIndex">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                        <%--<a href="${ctx}/exam/examWorkflow/appraise/public?id=${workflowId}&fillPersonId=${obj.objId}&status=1">${100-obj.conventionWorkScore-obj.importWorkScore-obj.commonSubstractScore+obj.commonAddScore}</a>--%>
                                </td>
                                <td>${obj.baseSumJKC}</td>
                                <c:choose>
                                    <c:when test="${not empty obj.conventionWorkScore}">
                                        <td><fmt:formatNumber type="number" value="${obj.conventionWorkScore}" pattern="#.####"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${obj.conventionWorkScore }</td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${obj.importWorkScore}</td>
                                <td>${obj.addWorkItemScore}</td>
                                <td>${obj.commonSubstractScore}</td>
                                <td>${obj.commonAddScore}</td>
                            </tr>
                        </c:if>

                    </c:forEach>
                    <c:forEach items="${resultList}" var="obj">
                        <c:if test="${obj.objName == '柳州公安处'}">
                            <tr>
                                <td>${obj.objName}</td>
                                <td style="color: #2429FF;">
                                    <c:choose>
                                        <c:when test="${fns:getUser().id eq 'cca66e1339f14799b01f6db43ed16e16'}">
                                            <a href="${ctx}/exam/examWorkflow/createTable?workflowId=${examWorkflowId}">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${examWorkflowId}&goExamIndex=goExamIndex">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                        <%--<a href="${ctx}/exam/examWorkflow/appraise/public?id=${workflowId}&fillPersonId=${obj.objId}&status=1">${100-obj.conventionWorkScore-obj.importWorkScore-obj.commonSubstractScore+obj.commonAddScore}</a>--%>
                                </td>
                                <td>${obj.baseSumJKC}</td>
                                <c:choose>
                                    <c:when test="${not empty obj.conventionWorkScore}">
                                        <td><fmt:formatNumber type="number" value="${obj.conventionWorkScore}" pattern="#.####"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${obj.conventionWorkScore }</td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${obj.importWorkScore}</td>
                                <td>${obj.addWorkItemScore}</td>
                                <td>${obj.commonSubstractScore}</td>
                                <td>${obj.commonAddScore}</td>
                            </tr>
                        </c:if>

                    </c:forEach>
                    <c:forEach items="${resultList}" var="obj">
                        <c:if test="${obj.objName == '北海公安处'}">
                            <tr>
                                <td>${obj.objName}</td>
                                <td style="color: #2429FF;">
                                    <c:choose>
                                        <c:when test="${fns:getUser().id eq 'cca66e1339f14799b01f6db43ed16e16'}">
                                            <a href="${ctx}/exam/examWorkflow/createTable?workflowId=${examWorkflowId}">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${ctx}/exam/examWorkflowDatas/appraise/examIndex?examWorkflowId=${examWorkflowId}&goExamIndex=goExamIndex">
                                                    ${obj.sumWorkScore}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                        <%--<a href="${ctx}/exam/examWorkflow/appraise/public?id=${workflowId}&fillPersonId=${obj.objId}&status=1">${100-obj.conventionWorkScore-obj.importWorkScore-obj.commonSubstractScore+obj.commonAddScore}</a>--%>
                                </td>
                                <td>${obj.baseSumJKC}</td>
                                <c:choose>
                                    <c:when test="${not empty obj.conventionWorkScore}">
                                        <td><fmt:formatNumber type="number" value="${obj.conventionWorkScore}" pattern="#.####"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${obj.conventionWorkScore }</td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${obj.importWorkScore}</td>
                                <td>${obj.addWorkItemScore}</td>
                                <td>${obj.commonSubstractScore}</td>
                                <td>${obj.commonAddScore}</td>
                            </tr>
                        </c:if>

                    </c:forEach>

                    </tbody>
                </table>
                <%--根据工作项汇总--%>
               <%-- <font color="red">
                    业务权重合计得分=
                    <c:forEach items="${weigthsList}" var="weightInfo" varStatus="status">
                        <c:choose>
                            <c:when test="${status.first}">
                                ${weightInfo.label}(权重${weightInfo.weight}分)
                            </c:when>
                            <c:otherwise>
                                +${weightInfo.label}(权重${weightInfo.weight}分)
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </font>--%>
            </div>
        </div>
    </div>
</div>