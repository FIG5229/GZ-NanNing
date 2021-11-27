<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script>
    $(document).ready(function() {
        //$("#name").focus();
        $("#inputForm").validate({
            submitHandler: function(form){
                loading('正在提交，请稍等...');
                form.submit();
            },
            errorContainer: "#messageBox",
            errorPlacement: function(error, element) {
                $("#messageBox").text("输入有误，请先更正。");
                if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                    error.appendTo(element.parent().parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });

    });
</script>
<div id="modalSysFirst">
    <div class="">
        <div class="modal-custom-main">
            <%@include file="/WEB-INF/views/modules/exam/step.jsp" %>
            <div class="modal-custom-content">
                <form:form id="inputForm" action="" method="post"
                           class="form-horizontal">
                    <table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>单位名称</th>
                            <th>自评状态</th>
                            <th>系统初核</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${objList}" var="obj">
                            <tr>
                                <c:choose>
                                    <c:when test="${obj.status<2}">
                                        <td style="color: #2429FF;">${obj.name}</td>
                                        <td style="font-size: 20px;">

                                            <i style="color: #D0282E;" class="icon-remove"></i>
                                        </td>
                                        <td style="font-size: 20px;"><i style="color: #D0282E;" class="icon-remove"></i>
                                        </td>
                                    </c:when>
                                    <c:when test="${obj.status==2}">
                                        <td style="color: #2429FF;"><a
                                                href="${ctx}/exam/examWorkflow/appraise/adjust?id=${workflowId}&objId=${obj.objId}&status=${obj.status}&processType=${processType}&personType=${personType}&history=${history}">${obj.name}</a>
                                        </td>
                                        <td style="font-size: 20px;">
                                            <i style="color: #6FAD47;" class="icon-check"></i>
                                            <a href="javascript:void(0)" onclick="alert(1)">
                                                <i style="color: #D0282E;" class="icon-lock"></i>
                                            </a>
                                        </td>
                                        <td style="font-size: 20px;"><i style="color: #D0282E;" class="icon-remove"></i>
                                        </td>
                                    </c:when>
                                    <c:when test="${obj.status==3||'true'.equals(history)}">
                                        <td style="color: #2429FF;"><a
                                                href="${ctx}/exam/examWorkflow/appraise/adjust?id=${workflowId}&objId=${obj.objId}&status=${obj.status}&processType=${processType}&personType=${personType}&history=${history}">${obj.name}</a>
                                        </td>
                                        <td style="font-size: 20px;">
                                            <i style="color: #6FAD47;" class="icon-check"></i>
                                            <i style="color: #D0282E;" class="icon-lock"></i>
                                        </td>
                                        <td style="font-size: 20px;"><i style="color: #6FAD47;" class="icon-check"></i>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="color: #2429FF;">${obj.name}</td>
                                        <td style="font-size: 20px;">
                                            <i style="color: #6FAD47;" class="icon-check"></i>
                                            <i style="color: #D0282E;" class="icon-lock"></i>
                                        </td>
                                        <td style="font-size: 20px;"><i style="color: #6FAD47;" class="icon-check"></i>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <!--
                    <div style="text-align: right;">
                        <input id="complete" class="btn btn-primary" type="submit" value="系统公示"/>
                    </div>
                    -->
                </form:form>
            </div>
        </div>
    </div>
</div>
