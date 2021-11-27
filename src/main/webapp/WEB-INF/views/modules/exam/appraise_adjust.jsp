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
        <%--var url="${ctx}/exam/examWorkflow/appaise/content?fillPersonId="+$("#objId").val()+"&objName="+$("#objName").val()+"&standardId="+standardId+"&workflowId="+$("#workflowId").val()+"&status="+$("#status").val()+"&processType=${processType}&personType=${personType}&history=${history}";--%>
        /*新加考评树*/
        var url="${ctx}/exam/examWorkflow/appaise/indexBeta?fillPersonId="+$("#objId").val()+"&objName="+$("#objName").val()+"&standardId="+standardId+"&workflowId="+$("#workflowId").val()+"&status="+$("#status").val()+"&processType=${processType}&personType=${personType}&history=${history}";
        $("#iframe").attr("src",url);
    }
    function back(){
        window.location.href = "${ctx}/exam/examWorkflow/exam?id=${workflowId}";
    }
</script>
<input id="workflowId" name="workflowId" value="${workflowId}" type="hidden"/>
<input id="status" name="status" value="${status}" type="hidden"/>
<input id="objId" name="objId" value="${objId}" type="hidden"/>
<input id="objName" name="objName" value="${objName}" type="hidden"/>
<div id="modalSysDepartment" class="">
    <div class="">
        <div class="modal-custom-main">
            <%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
            <!--
            <div class="modal-custom-tab">
                <c:forEach items="${objList}" var="obj">
                    <div class="modal-custom-tab-item"><a href="${ctx}/exam/examWorkflow/exam?id=${workflowId}&objId=${obj.id}&status=${status}&processType=${processType}">${obj.name}(90)</a></div>
                </c:forEach>
            </div>
            -->
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
                <div>
                    <input id="check" class="btn btn-primary" type="button" value="检查情况" onclick=""/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="reward" class="btn btn-primary" type="button" value="奖惩情况" onclick=""/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="back" class="btn btn-primary" type="button" value="返回" onclick="back()"/>
                </div>
            </div>
            <div class="modal-custom-content">
                <iframe id="iframe" src="" width="100%" height="500" style="border:0;" noresize="noresize"></iframe>
            </div>
        </div>
    </div>
</div>
