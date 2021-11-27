<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <title>Title</title>
    <script type="text/javascript">
        function submitByIds() {
            $("#searchForm2").submit();
        }
    </script>
</head>
<body>
<form:form id="searchForm2" modelAttribute="affairTemporaryPolice" action="${ctx}/affair/affairTemporaryPolice/submitByIds" method="post" class="breadcrumb form-search">
    <div class="control-group">
        <div class="controls">
            <font color="red">请选择送审对象：</font>
            <input type="hidden" name="ids" id="ids" value="${ids}"/>
            <c:choose>
                <c:when test="${fns:getUser().company.id eq '1'}">
                    <form:select id="threeCheckId" path="threeCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('ju_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '34'}">
                    <form:select id="threeCheckId" path="threeCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('nnc_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '95'}">
                    <form:select id="threeCheckId" path="threeCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('lzc_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '156'}">
                    <form:select id="threeCheckId" path="threeCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('bhc_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
            </c:choose>

        </div>
    </div>
    <input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
</form:form>
<script>
    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
</body>
</html>
