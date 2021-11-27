<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<!DOCTYPE html>
<html>
<head>
	<%--首页通知通报详情内容--%>
	<title>南宁铁路公安局智慧政工信息平台</title>
		<meta name="decorator" content="cms_default_${site.theme}"/>
		<meta name="description" content="${site.description}" />
		<meta name="keywords" content="${site.keywords}" />
	<style>
		.content h4,
		.content h5{
			color: #ffffff;
			background:linear-gradient(270deg,rgba(255,157,140,1) 0%,rgba(249,36,0,1) 100%);
			border-left:none;
			text-indent: 10px;
		}
	</style>
	<script type="text/javascript">

	</script>
</head>
<body>
<div class="row">
	<div class="span2">
		<h4>栏目列表</h4>
		<ol>
			通知通报
			<%--<cms:frontCategoryList categoryList="${categoryList}"/>--%>
		</ol>
		<h4>推荐阅读</h4>
		<ol>
			<%--<cms:frontArticleHitsTop category="${category}"/>--%>
		</ol>
	</div>
	<div class="span10">
		<ul class="breadcrumb">
			<%--<cms:frontCurrentPosition category="${category}"/>--%>
		</ul>
	</div>
	<div class="span10">
		<div class="row">
			<div class="span10">
				<h3 style="color:#555555;font-size:20px;text-align:center;">${notice.title}</h3>
				<p style="color:#555555;font-size:14px;text-align:left;border-bottom:1px solid #ddd;padding-bottom:15px;margin:25px 0;">发布部门：${notice.publishDep}</p>
				<div style="text-align: left">${notice.content}</div>
				<div class="modal-custom-info1-file">
					<div class="modal-custom-info1-file-l">附件:</div>
					<div class="modal-custom-info1-file-r">
						<c:forEach items="${filePathList}" var="m" varStatus="status">
							<div class="modal-custom-info1-file-item">
								<span>${m.fileName}</span>
								<a class="download" href="${pageContext.request.contextPath}/a/file/download?fileName=${m.path}">下载</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div id="comment" class="hide span10">
				正在加载评论...
			</div>
		</div>
		<div class="row">
			<div class="span10">
				<h5>相关文章</h5>
				<ol></ol>
			</div>
		</div>
	</div>
</div>
</body>
</html>