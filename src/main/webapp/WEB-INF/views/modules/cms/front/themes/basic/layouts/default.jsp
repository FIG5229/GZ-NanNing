<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title>南宁铁路公安局智慧政工平台</title>
	<%@include file="/WEB-INF/views/modules/cms/front/include/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/swiper/css/swiper.min.css"/>
    <sitemesh:head/>
	<script src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" type="text/javascript"></script>
	<style>
		.list-commom{
			padding: 0 0 0 24px;
            text-align: left;
		}
	</style>
	<style type="text/css">
		html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
	</style>
	<style>
	.login-model{
		z-index: 999;
		/*position: fixed;*/
		vertical-align: center;
		top: 500px;
		/*left:100px;*/
		/*width:500px;*/
		/*height:500px;*/
		/*background: rgba(255,255,255,1);*/
	}
	.login-wrap{
		position:absolute;
		top:50%;
		left:50%;
		margin-top:-190px;
		margin-left:-210px;
		width:420px;
		/*height:365px;*/
		padding-bottom: 20px;
		background:rgba(255,255,255,1);
		box-shadow:0px 0px 20px 0px rgba(0, 0, 0, 0.3);
		border-radius:2px;
	}
	.login-top{
		position:relative;
		width:100%;
		height:64px;
		line-height: 64px;
		text-indent: 38px;
		background:rgba(249,36,0,1);
		font-size:20px;
		font-weight:500;
		color:rgba(255,255,255,1);
	}
	.login-top .close-block{
		width: 30px;
		height: 20px;
		line-height:20px;
		text-align: center;
		font-size: 16px;
		font-weight:bold;
		text-indent: 0;
		position: absolute;
		top:4px;
		right:1px;
		cursor: pointer;
	}
	.login-top img{
		position:absolute;
		left:37px;
		top:16px;
		width:32px;
		heigt:32px;
	}
	.form-signin{
		width:320px;
		margin: 32px auto 0;
	}
	.ipt-wrap{position:relative;padding-bottom: 8px;}
	.ipt-wrap:after{
		content:'';
		width:16px;
		height:16px;
		position:absolute;
		left:10px;
		top:11px;

	}
	.ipt-wrap-username:after{
		background: url('../../../../politics/static/images/username.png') no-repeat center;
	}
	.ipt-wrap-pwd:after{
		background: url('../../../../politics/static/images/pwd.png') no-repeat center;
	}
	.ipt-wrap-validate{overflow:hidden;}
	.ipt-wrap-validate:after{
		background: url('../../../../politics/static/images/validat.png') no-repeat center;
	}
	.form-signin #username,
	.form-signin #password{
		width:100%;
		height:38px;
		background:rgba(255,255,255,1);
		border: none;
		border:1px solid rgba(204,204,204,1);
		border-radius:2px;
		text-indent: 35px;
		color:rgba(153,153,153,1);
		padding: 0;
	}
	.form-signin #validateCode{
		float: left;
		padding: 0;
		width:154px;
		height:38px;
		background:rgba(255,255,255,1);
		border:1px solid rgba(204,204,204,1);
		border-radius:2px;
		text-indent: 35px;
	}
	.form-signin img{
		margin-top: 1px;
		float: left;
		display:block;
		width:108px;
		height:38px;
	}
	.form-signin a{
		display:inline-blick;
		float: right;
		display:block;
		height:38px;
		line-height:38px;
	}
	#loginForm .btn-login{
		float:left;
		width:150px;
		height:38px;
		background:rgba(249,36,0,1);
		border:1px solid rgba(249,36,0,1);
		border-radius:2px;
		font-size:14px;
		color:#fff;
	}
	#loginForm .btn-reset{
		float:right;
		width:150px;
		height:38px;
		background:rgba(255,255,255,1);
		border:1px solid rgba(255,0,0,1);
		border-radius:2px;
		font-size:14px;
		color:#F92400;
	}
	.login-close-btn {
		position: absolute;
		right: 10px;
		top: 0px;
		cursor:pointer
	}
	</style>
	<script type="text/javascript">
		jQuery(function() {
			$(".validateCode").hide();
			var v = jQuery("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				},
				submitHandler: function(form) {
					jQuery(form).ajaxSubmit({
						success: function(data){
							if(data.success) {
								window.location.reload();
							} else {
								$("#loginError").text(data.message);
								$("#loginError").css('display', 'block');
								$("#messageBox").css('display', 'block');
								$("#messageBox").removeClass("hide");
								if(data.isValidateCodeLogin) {
									$(".validateCode").show();
								} else {
									$(".validateCode").hide();
								}
							}
						},
						error: function(XmlHttpRequest, textStatus, errorThrown){
							alert(errorThrown);
						}
					});
				}
			});
		});

	</script>
	<script type="text/javascript">
		function closeLogin() {
			$('#loginDiv').hide();
		}
		function showLogin() {
			$('#loginDiv').show();
			// $.jBox.open('id:loginDiv', {
			// 	top: 200,
			// 	width: 500,
			// 	height: 600,
			// 	buttons: {}
			// });
		}
	</script>

</head>
<body>
	<div class="top-banner">
		<div class="top-banner-center">
			<div class="top-banner-logo"><img src="../../../../politics/static/images/top-banner-logo.png" alt=""></div>
			<div class="top-banner-text">
				<p class="p1">南宁铁路公安局智慧政工平台</p>
				<p class="p2">Nan  Ning  Railway  Security  Bureau  Smart  Political  Work  Platform</p>
			</div>
		</div>
	</div>
	<div class="top-wrap">
			<div class="top">
				<h1 class="logo"><img src="./../../../../politics/static/images/logo-index.png" alt=""></h1>
				<ul class="nav">
				<c:forEach items="${fnc:getMainNavList(site.id)}" var="category" varStatus="status"><c:if test="${status.index lt 6}">
                    <c:set var="menuCategoryId" value=",${category.id},"/>
		    		<li class="${requestScope.category.id eq category.id||fn:indexOf(requestScope.category.parentIds,menuCategoryId) ge 1?'active':''}"><a href="${category.url}" target="${category.target}">${category.name}</a></li>
		    		</c:if>
		    	</c:forEach>
				</ul>
				<shiro:notAuthenticated>
					<a href="javascript:void(0)" onclick="showLogin();"><div class="top-right">用户登录</div></a>
				</shiro:notAuthenticated>
				<shiro:authenticated>
					<a href="${pageContext.request.contextPath}${fns:getAdminPath()}/logout" title="退出登录"><div class="top-right">退出</div></a>
					<a href="${pageContext.request.contextPath}${fns:getAdminPath()}"><div class="top-right">后台管理</div></a>
					<%--<div class="top-right">
						<a href="${pageContext.request.contextPath}${fns:getAdminPath()}" target="_blank">后台管理</a>
						<a href="${ctx}/logout" title="退出登录">退出</a>
					</div>--%>
				</shiro:authenticated>
			</div>
		</div>
	<div class="top-slogan"><img src="../../../../politics/static/images/index-slogan.png" alt=""></div>
	<div class="container">
		<div class="content">
			<sitemesh:body/>
		</div>
    </div> <!-- /container -->
	<div id="loginDiv" style="display:none;">
		<div class="login-model">
			<div class="login-wrap"  style="z-index: 99; position: fixed">
				<div class="login-top">
					<div class="close-block" id="closeLogin"></div>
					<img src="../../../../politics/static/images/logo.png">
					南宁铁路公安局智慧政工平台
					<span class="login-close-btn" onclick="closeLogin()" >X</span>
				</div>
				<form id="loginForm" class="form-signin" action="${pageContext.request.contextPath}${fns:getAdminPath()}/login" method="post">
					<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
					<div class="header">
						<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
							<label id="loginError" class="error">${message}</label>
						</div>
					</div>
					<div class="ipt-wrap ipt-wrap-username">
						<input type="text" id="username" name="username" class="input-block-level required" value="${username}" placeholder="请输入用户名">
					</div>
					<div class="ipt-wrap ipt-wrap-pwd">
						<input type="password" id="password" name="password" class="input-block-level required" placeholder="请输入密码">
					</div>
					<div class="validateCode">
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;" />
					</div>
                    <%--<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label>--%>
					<input class="btn btn-large btn-primary btn-login" type="submit" value="登 录"/>
					<input class="btn btn-large btn-primary btn-reset" type="button" value="重置"/>
					<label style="padding-top: 10px;color:black" for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我</label>
				</form>
			</div>
		</div>
	</div>
    <div class="footer"></div>
		<div class="block" style="width: 470px;margin: 30px auto 10px;">
			<div class="f-link-title">友情链接</div>
			<select class="select-link" onChange="window.open(this.options[this.selectedIndex].value,'','')" style="">
				<option >区内公安政工导航</option>
				<c:forEach items="${fnc:getArticleList(site.id, '6704e6ae3bdb4476896cbd918e6f6de3', 2, '')}" var="article">
					<option value="${article.url}">${article.title}</option>
				</c:forEach>
			</select>
			<select class="select-link" onChange="window.open(this.options[this.selectedIndex].value,'','')" style="">
				<option >全国公安政工导航</option>
				<c:forEach items="${fnc:getArticleList(site.id, '9d37467ed63a4e959705e2685ec44fc8', 2, '')}" var="article">
					<option value="${article.url}">${article.title}</option>
				</c:forEach>
			</select>
		</div>
		<div class="footer-info">
			<div class="footer-img"><img src="../../../../politics/static/images/footer-img.png" ></div>
			<div class="footer-text-wrap">
				<p class="footer-text">版权所有：南宁铁路公安局</p>
				<p class="footer-text">地址：广西壮族自治区南宁市青秀区枫林路32号</p>
			</div>
		</div>
</body>
</html>
