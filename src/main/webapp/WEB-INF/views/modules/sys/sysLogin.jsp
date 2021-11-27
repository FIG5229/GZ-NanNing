<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')}${fns:getConfig('productName2')}登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
	  body::-webkit-scrollbar{
		  display: none;
	  }
    </style>
    <style>
	    .login-model{
	    	z-index: 999;
	    	position: fixed;
	    	top:0;
	    	left:0;
	    	width:100%;
	    	height:100%;
	    	background: rgba(255,255,255,1);
	    }
    	.login-wrap{
	    	position:absolute;
	    	top:50%;
	    	left:50%;
	    	margin-top:-190px;
	    	margin-left:-210px;
    		width:420px;
			height:365px;
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
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
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
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<div class="login-model">
    	<div class="login-wrap">
    		<div class="login-top">
    			<div class="close-block" id="closeLogin"></div>
    			<img src="../../../../politics/static/images/logo.png">
    			南宁铁路公安局智慧政工平台
    		</div>
    		<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
				<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
				<div class="header">
					<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
						<label id="loginError" class="error">${message}</label>
					</div>
				</div>				<div class="ipt-wrap ipt-wrap-username">
					<input type="text" id="username" name="username" class="input-block-level required" value="${username}" placeholder="请输入用户名">
				</div>
				<div class="ipt-wrap ipt-wrap-pwd">
					<input type="password" id="password" name="password" class="input-block-level required" placeholder="请输入密码">
				</div>
				<c:if test="${isValidateCodeLogin}">
					<div class="validateCode">
						<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;" />
					</div>
				</c:if>
				<%--
				<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
				<input class="btn btn-large btn-primary btn-login" type="submit" value="登 录"/>
				<input class="btn btn-large btn-primary btn-reset" type="button" value="重置"/>
				<label style="padding-top: 10px;" for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我</label>				
			</form>
    	</div>
    </div>
	<div class="footer">
	<%-- Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')}</a>  --%>	
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>