<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<style></style>
<!-- 绩效考评-系统公示 -->
<div id="modalSysPublic" class="">
    <div class="">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>环节名称</th>
                        <th>计划结束时间</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${segmentsList}" var="segment">
                        <c:choose>
                            <c:when test="${segment.sort <= status}">
                                <tr>
                                    <td><i style="color: #6FAD47;" class="icon-check">${segment.name}</i></td>
                                    <td>${segment.endTime}</td>
                                    <td>${segment.comment}</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td><i style="color: #D0282E;" class="icon-remove">${segment.name}</i></td>
                                    <td>${segment.endTime}</td>
                                    <td>${segment.comment}</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>