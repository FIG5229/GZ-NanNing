<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="modal-custom-main-step">
    <div class="step-start active">开始</div>
    <c:choose>
        <c:when test="${'true'.equals(history)}">
            <c:forEach items="${segmentsList}" var="segment">
                <div class="modal-step-col
					<c:if test="${segment.sort <= hisCurStatus}">
						active
					</c:if>
					">${segment.name}</div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach items="${segmentsList}" var="segment">
                <div class="modal-step-col
					<c:if test="${segment.sort <= status}">
						active
					</c:if>
					">${segment.name}</div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <div class="step-start step-end
        <c:if test="${99 == status}">
            active
        </c:if>
    ">结束</div>
</div>