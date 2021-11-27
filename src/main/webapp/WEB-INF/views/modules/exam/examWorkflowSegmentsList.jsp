<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
        <th>环节名称</th>
        <th>计划结束时间</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${null != segmentsList}">
            <c:forEach items="${segmentsList}" var="examWorflowSegments" varStatus="status">
                <tr>
                    <td>
                        <input style='margin-left:12px' type='checkbox' name="checkedSegment"  value="${examWorflowSegments.segmentId}"  <c:if test="${'1'.equals(examWorflowSegments.isMust)}"> onclick="return false"</c:if>  checked/>
                        <input type="hidden"  name="examWorflowSegmentsList[${status.index}].segmentId" value="${examWorflowSegments.segmentId}" />
                    </td>
                    <td>
                            ${examWorflowSegments.name}
                    </td>
                    <td>
                        <input name="examWorflowSegmentsList[${status.index}].endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                               value="<fmt:formatDate value="${examWorflowSegments.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </td>
                    <td>
                        <input  name="examWorflowSegmentsList[${status.index}].comment" value="${examWorflowSegments.comment}" type="text"/>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach items="${list}" var="examWorkflowSegmentsDefine" varStatus="status">
                <tr>
                    <td>
                        <input style='margin-left:12px' type='checkbox' name="checkedSegment"  value="${examWorkflowSegmentsDefine.id}"  <c:if test="${'1'.equals(examWorkflowSegmentsDefine.isMust)}"> onclick="return false"  checked</c:if>/>
                        <input type="hidden"  name="examWorflowSegmentsList[${status.index}].segmentId" value="${examWorkflowSegmentsDefine.id}" />
                    </td>
                    <td>
                            ${examWorkflowSegmentsDefine.name}
                    </td>
                    <td>
                        <input name="examWorflowSegmentsList[${status.index}].endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                    </td>
                    <td>
                        <input  name="examWorflowSegmentsList[${status.index}].comment"  type="text"/>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
