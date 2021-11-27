<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        var children= $("#tabs").children()[0].click();
    });
    function changeStandard(standardId) {
        $("#tabs").children().each(function () {
            if($(this).attr('id')==standardId){
                $(this).addClass("red");
            }
            else{
                $(this).removeClass("red");
            }
        });
        var url="${ctx}/exam/examWorkflow/appaise/content?standardId="+standardId+"&workflowId="+$("#workflowId").val()+"&fillPersonId=${fillPersonId}"+"&status="+$("#status").val()+"&processType=appraisePublic";
        $("#iframe").attr("src",url);
    }
</script>
<input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
<input id="status" name="status" value="${status}" type="hidden"/>
<!-- 绩效考评-系统自评 -->
<div id="modalSysSelf">
    <div class="">
        <div class="modal-custom-main">
            <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                <tbody>
                <tr>
                    <c:forEach items="${objList}" var="obj">
                        <td><a href="${ctx}/exam/examWorkflow/appraise/public?id=${workflowId}&fillPersonId=${obj.id}&status=${status}">${obj.name}</a></td>
                    </c:forEach>
                </tr>
                </tbody>
            </table>
            <div class="modal-custom-bar" id="tabs">
                <c:forEach items="${standardInfoList}" var="info" varStatus="status">
                    <c:choose>
                        <c:when test="${status.index==0}">
                            <div class="modal-custom-btn red"  onclick="changeStandard('${info.id}')" id="${info.id}">${info.abbreviation}</div>
                        </c:when>
                        <c:otherwise>
                            <div class="modal-custom-btn" onclick="changeStandard('${info.id}')" id="${info.id}">${info.abbreviation}</div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="modal-custom-content">
                <iframe id="iframe" src="" width="100%" height="500" style="border:0;" noresize="noresize"></iframe>
            </div>
        </div>
    </div>
</div>
