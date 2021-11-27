<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>测试管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/test/test/">硕正列表</a></li>
	<li class="active"><a href="${ctx}/test/test/form?id=${test.id}">组件<shiro:hasPermission name="test:test:edit">${not empty test.id?'修改':'表单'}</shiro:hasPermission><shiro:lacksPermission name="test:test:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="test" action="${ctx}/test/test/save" method="post" class="form-horizontal">
	<%--<form:hidden path="id"/>
	<sys:message content="${message}"/>--%>
	<div class="control-group">
		<label class="control-label">上传图片:</label>
		<div class="controls">
				<%--<div id="wrapper">
                    <div id="container">
                        <!--头部，相册选择和格式选择-->
                        <div id="uploader">
                            <div class="queueList">
                                <div id="dndArea" class="placeholder">
                                    <div id="filePicker"></div>
                                    <p>或将照片拖到这里，单次最多可选300张</p>
                                </div>
                            </div>
                            <div class="statusBar" style="display:none;">
                                <div class="progress">
                                    <span class="text">0%</span>
                                    <span class="percentage"></span>
                                </div><div class="info"></div>
                                <div class="btns">
                                    <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>--%>
			<form:hidden id="nameFile" path="name" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:webuploader input="nameFile" type="files" uploadPath="test/images" selectMultiple="true"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">上传图片New:</label>
		<div class="controls">
			<form:hidden id="nameImage" path="name" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:webuploader input="nameImage" type="images" uploadPath="test/images" selectMultiple="false"/>
		</div>
	</div>
</form:form>
</body>
</html>
