<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            if(3== checkType && (null == $("#juChuCheckId").val() || "" ==  $("#juChuCheckId").val())){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            $("#checkType").val(checkType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="personnelGoAbroadTwo" action="${ctx}/affair/personnelGoAbroadTwo/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
    <div class="control-group">
        <label class="control-label">审核意见：</label>
        <div class="controls">
            <form:textarea path="opinion" htmlEscape="false" rows="6" class="input-xxlarge "/>
        </div>
    </div>
    <br>
   <%-- <div class="controls" type="hidden">
        <label class="control-label">推送（选填）：</label>
        &lt;%&ndash;<sys:treeselect id="juCheckMan" name="juCheckId" value="${affairActivityMien.juCheckId}" labelName="juCheckMan" labelValue="${affairActivityMien.juCheckMan}"
                        title="送审单位" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>&ndash;%&gt;
        <form:select id="juChuCheckId" path="juChuCheckId"   class="input-xlarge required">
            <form:option value="${idValue}" label="${lable}"/>
&lt;%&ndash;            <form:options items="${fns:getDictList('affair_mein_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>&ndash;%&gt;
        </form:select>


       &lt;%&ndash; <input id="btnSubmit1" class="btn btn-primary" type="button" value="转送上一级" onclick="setCheckType(6)"/>&nbsp;&ndash;%&gt;


    </div>--%>

    </div>
    <input id="checkType" name="checkType"  type="hidden"/>
    <div style="margin-left: 500px;margin-top: 70px;">

            <input id="btnSubmit" class="btn btn-primary" type="button" value="审核通过" onclick="setCheckType(4)"/>&nbsp;
        <%--<input id="btnSubmit3" class="btn btn-primary" type="button" value="退回整改" onclick="setcheckType(3)"/>&nbsp;--%>
        <input id="btnSubmit2" class="btn btn-default" type="button" value="退回整改" onclick="setCheckType(0)"/>&nbsp;
    </div>
</form:form>
</body>
</html>