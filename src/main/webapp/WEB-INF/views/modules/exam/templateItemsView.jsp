<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#importForm").validate({
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



        if ("success" == "${saveResult}"){
            parent.$.jBox.close();
        }

    </script>
</head>
<body>
<div id="importBox">

    <sys:message content="${message}"/>
    <div class="control-group"></div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                <th>${examStandardTemplateItemDefine.columnName}</th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <script>
        </script>
        <c:forEach items="${rowlist}" var="row" varStatus="s">
            <tr>
                <c:forEach items="${columnList}" var="examStandardTemplateItemDefine" varStatus="status">
                    <td>
                        <%--获取到的数据与columnList的id对应不上为空--%>
                            ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                    </td>
                </c:forEach>
                <%--使用主表权限就可以--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>