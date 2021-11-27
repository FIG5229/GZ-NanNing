<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>留言板</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="JeeSite ${site.description}" />
	<meta name="keywords" content="JeeSite ${site.keywords}" />
	<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<style>
		.content h4{
			color: #ffffff;
			background:linear-gradient(270deg,rgba(255,157,140,1) 0%,rgba(249,36,0,1) 100%);
			border-left:none;
			text-indent: 10px;
		}
		.content .form-horizontal{
			margin: 0;
			background:#ffffff;
		}
		.controls #validateCode2{
			float: left;
			padding: 0;
			width:154px;
			height:25px;
			background:rgba(255,255,255,1);
			border:1px solid rgba(204,204,204,1);
			border-radius:2px;
			text-indent: 25px;
		}
		.controls img{
			margin-top: 1px;
			float: left;
			display:block;
			width:108px;
			height:25px;
		}
		.controls a{
			display:inline-blick;
			float: left;
			display:block;
			height:25px;
			line-height:25px;
		}
	</style>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty message}">alert("${message}");</c:if>
			$("#inputForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					content: {required: "请填写留言内容"},
					validateCode: {remote: "验证码不正确"}
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#main_nav li").each(function(){
				$(this).toggleClass("active", $(this).text().indexOf('公共留言')>=0);
			});
		});
		function page(n,s){
			location="${ctx}/guestbook?pageNo="+n+"&pageSize="+s;;
		}
	</script>
</head>
<body>
	<div style="padding:0 0 20px;">
		<h4>公共留言</h4>
		<ul>
			<c:forEach items="${page.list}" var="guestbook">
				<li style="text-align: left;margin-left: 50px;margin-top: 30px;">
					<div>姓名: ${guestbook.name} &nbsp;时间：<fmt:formatDate value="${guestbook.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
					<div>内容：${guestbook.content}</div>
					<div>回复人：${guestbook.reUser.name} 时间：<fmt:formatDate value="${guestbook.reDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
					<div>回复内容：${guestbook.reContent}</div>
				</li>
			</c:forEach>
			<c:if test="${fn:length(page.list) eq 0}">
				<li>暂时还没有人留言！</li>
			</c:if>
		</ul>
		<div class="pagination">${page}</div>
		<h4>我要留言</h4>
		<form:form id="inputForm" action="${ctx}/guestbook" method="post" class="form-horizontal">
			<div class="control-group">
				<label class="control-label">名称:</label>
				<div class="controls">
					<input type="text" name="name" maxlength="11" class="required" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">邮箱:</label>
				<div class="controls">
					<input type="text" name="email" maxlength="50" class="required email" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">电话:</label>
				<div class="controls">
					<input type="text" name="phone" maxlength="50" class="required phone" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">单位:</label>
				<div class="controls">
					<input type="text" name="workunit" maxlength="50" class="required" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">留言分类:</label>
				<div class="controls">
					<select name="type" class="txt required" style="width:100px;">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('cms_guestbook')}" var="type">
							<option value="${type.value}">${type.label}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">留言内容:</label>
				<div class="controls">
					<textarea name="content" rows="4" maxlength="200" class="required" style="width:400px;"></textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">验证码:</label>
				<div class="controls">
					<div class="ipt-wrap ipt-wrap-validate">
						<input type="text" id="validateCode2" name="validateCode2" maxlength="5" class="txt required" style="" placeholder="请输入验证码" />
						<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('.validateCode2Refresh').click();" class="mid validateCode2" style="display:block"/>
						<a href="javascript:" onclick="$('.validateCode2').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid validateCode2Refresh" style="">刷新</a>
					</div>
				</div>
			</div>
			<div class="form-actions">
				<input class="btn btn-primary" type="submit" value="提 交"/>&nbsp;
			</div>
			<div id="messageBox" class="alert alert-error" style="display:none">输入有误，请先更正。</div>
		</form:form>
	</div>
</body>
</html>