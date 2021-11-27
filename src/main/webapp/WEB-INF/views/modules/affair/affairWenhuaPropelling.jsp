<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文化人才推送管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		if ("success" == "${result}") {
			top.$.jBox.close();
		}
		$(document).ready(function() {
			if($("#link").val()){
				$('#linkBody').show();
				$('#url').attr("checked", true);
			}
			$("#title").focus();
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if ($("#categoryId").val()==""){
						$("#categoryName").focus();
						top.$.jBox.tip('请选择归属栏目','warning');
					}else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
						top.$.jBox.tip('请填写正文','warning');
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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

		function checkForm() {
			var ids = [];
			$("input:checkbox[type=checkbox]:checked").each(function () {
				if ($(this).val() != "on"){
					ids.push($(this).val());
				}
			});
			if (ids.length > 0) {
				return true;
			}else {
				top.$.jBox.tip('请选择归属栏目','warning');
				return false;
			}
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="articles" action="${ctx}/cms/article/saves?type=affairTnActivityRecord" method="post" class="form-horizontal">
		<%--<form:hidden path="id"/--%>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属栏目:</label>
			<div class="controls"><%--
				<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}" checked="true"
								title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>&nbsp;
				<span>
					<input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()"><label for="url">外部链接</label>
				</span>
				<span>
				    <input id="category1" name="category" type="checkbox" value="335c69c108d243ca9fded21331dcad52"/>
				    <label for="category1">工会保障</label>
				</span>
				<span>
				    <input id="category2" name="category" type="checkbox" value="f0db6af079714a65a8ec11e3b08329c6"/>
				    <label for="category2">警务督察</label>
				</span>
				<span>
				    <input id="category3" name="category" type="checkbox" value="44c5b3f7d9e94c9897af22ef67d02292"/>
				    <label for="category3">纪检监察</label>
				</span>
				<span>
				    <input id="category4" name="category" type="checkbox" value="220c0482c9a5442b9c6317ffdefc7381"/>
				    <label for="category4">青年阵地</label>
				</span>
				<span>
				    <input id="category5" name="category" type="checkbox" value="2"/>
				    <label for="category5">政工概况</label>
				</span>
				<input type="hidden" name="_category" value="on"/>--%>
<%--                <form:checkboxes path="category" items="${fns:getDictList('cms_category')}" itemLabel="label" itemValue="value"/>--%>
				<input id="category2" type="checkbox" name="categoryArr" value="2"><label>政工概要</label>
				<input id="category3" type="checkbox" name="categoryArr" value="220c0482c9a5442b9c6317ffdefc7381"><label>青年阵地</label>
				<br>
				<input id="category4" type="checkbox" name="categoryArr" value="44c5b3f7d9e94c9897af22ef67d02292"><label>纪检监察</label>
				<input id="category5" type="checkbox" name="categoryArr" value="f0db6af079714a65a8ec11e3b08329c6"><label>警务督察</label>
				<br>
				<input id="category6" type="checkbox" name="categoryArr" value="335c69c108d243ca9fded21331dcad52"><label>工会保障</label>
				<input id="category7" type="checkbox" name="categoryArr" value="5d7f5d61b84243ddb53ce5c359e6c2f2"><label>政工要闻</label>
				<br>
				<input id="category8" type="checkbox" name="categoryArr" value="c55f7521049a4a1bbaf9031c929fc142"><label>廉政教育</label>
<%--				<form:select path="categoryArr" class="input-medium input-xlarge required" multiple="multiple" >--%>
<%--					<form:options items="${fns:getDictList('cms_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--				</form:select>--%>
			</div>
		</div>
		<div class="control-group" hidden="true">
			<label class="control-label">标题:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
				&nbsp;<label>颜色:</label>
				<form:select path="color" class="input-mini">
					<form:option value="" label="默认"/>
					<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group" hidden="true">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group" hidden="true">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea id="joinType" htmlEscape="true" path="articleData.content" rows="4" maxlength="100" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group" hidden="true">
			<label class="control-label">附件:</label>
			<div class="controls">
				<input type="hidden" id="appendfile" name="appendfile" value="${article.appendfile}" />
				<c:choose>
					<c:when test="${'f2fa306bb4f446fc9c1d6f38e7033488'.equals(article.category.id)}">
						<sys:ckfinder input="appendfile"  type="files" uploadPath="/cms/article" selectMultiple="true"/>
					</c:when>
					<c:otherwise>
						<sys:ckfinder input="appendfile"  type="files" uploadPath="/cms/article" selectMultiple="false"/>
					</c:otherwise>
				</c:choose>
				<!--
					<form:hidden id="filesName" path="appendfile" value="${article.appendfile}"  htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:webuploader input="filesName" type="files" uploadPath="cms/files" selectMultiple="true"/>
					-->
			</div>
		</div>
		<div class="form-actions">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return checkForm();"/>&nbsp;

		</div>

	</form:form>
</body>
</html>