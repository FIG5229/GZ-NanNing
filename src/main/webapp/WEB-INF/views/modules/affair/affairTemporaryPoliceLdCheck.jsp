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
    <%-- <ul class="ul-form" style="text-align: right">
         <font color="red">请选择送审对象：</font>
         <input type="hidden" name="ids" id="idsValue"/>
       &lt;%&ndash;  <sys:treeselect id="oneCheckMan" name="twoCheckId" value="${affairTemporaryPolice.twoCheckId}"
                         labelName="oneCheckMan" labelValue="${affairTemporaryPolice.oneCheckMan}"
                         title="部门" url="/sys/office/treeData?type=3" allowClear="true"
                         notAllowSelectParent="true" checked="true" isAll="true"/>&ndash;%&gt;
         <c:if test="${fns:getUser().company eq '1'}"></c:if>
         <form:select id="twoCheckId" path="twoCheckId" class="input-xlarge required">
             <form:option value="bfdf74f010c9466dba12c1589ecab7f3" label="南宁局政治部组织干部处"/>
         </form:select>

         <input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
     </ul>--%>
    <div class="control-group">
        <div class="controls">
            <font color="red">请选择送审对象：</font>
            <input type="hidden" name="ids" id="ids" value="${ids}"/>
                <%--  <sys:treeselect id="oneCheckMan" name="twoCheckId" value="${affairTemporaryPolice.twoCheckId}"
                                  labelName="oneCheckMan" labelValue="${affairTemporaryPolice.oneCheckMan}"
                                  title="部门" url="/sys/office/treeData?type=3" allowClear="true"
                                  notAllowSelectParent="true" checked="true" isAll="true"/>--%>
            <c:choose>
                <c:when test="${fns:getUser().company.id eq '1'}">
                    <form:select id="twoCheckId" path="twoCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('ju_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '34'}">
                    <form:select id="twoCheckId" path="twoCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('nnc_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '95'}">
                    <form:select id="twoCheckId" path="twoCheckId" class="input-xlarge required">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('lzc_ld')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </c:when>
                <c:when test="${fns:getUser().company.id eq '156'}">
                    <form:select id="twoCheckId" path="twoCheckId" class="input-xlarge required">
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
