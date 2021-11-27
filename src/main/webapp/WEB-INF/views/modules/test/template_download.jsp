<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
</head>
<body>
<div id="importBox">
    <form id="importForm" action="${ctx}${url}" method="post" enctype="multipart/form-data"
          class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
        <a href="${ctx}/file/template/download?fileName=${template}">下载模板</a>
    </form>
</div>
<c:if test="${not empty message}">
    <c:if test="${not empty message}"><c:set var="ctype" value="${fn:indexOf(message,'失败') eq -1?'success':'error'}"/></c:if>
    <div id="messageBox" class="alert alert-${ctype} hide"><button data-dismiss="alert" class="close">×</button>${message}</div>
    <script type="text/javascript">
        if(!$.jBox.tip.mess){
            $.jBox.tip.mess=1;
            $("#messageBox").show();
        }
    </script>
</c:if>
<script>
    if("success"=="${result}"){
        parent.$.jBox.close();
    }
    if(${not empty message}){
        closeLoading();
    }
</script>
</body>
</html>