<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${article.title} - ${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${article.description} ${category.description}" />
	<meta name="keywords" content="${article.keywords} ${category.keywords}" />
	<style>
		.content h4,
		.content h5{
			color: #ffffff;
			background:linear-gradient(270deg,rgba(255,157,140,1) 0%,rgba(249,36,0,1) 100%);
			border-left:none;
			text-indent: 10px;
		}
		a{color: #0000ee}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			if ("${category.allowComment}"=="1" && "${article.articleData.allowComment}"=="1"){
				$("#comment").show();
				page(1);
			}
		});
		function page(n,s){
			$.get("${ctx}/comment",{theme: '${site.theme}', 'category.id': '${category.id}',
				contentId: '${article.id}', title: '${article.title}', pageNo: n, pageSize: s, date: new Date().getTime()
			},function(data){
				$("#comment").html(data);
			});
		}
	</script>
</head>
<body>
	<div class="row">
	   <div class="span2">
	   	 <h4>栏目列表</h4>
		 <ol>
		 	<cms:frontCategoryList categoryList="${categoryList}"/>
		 </ol>
		 <h4>推荐阅读</h4>
		 <ol>
		 	<cms:frontArticleHitsTop category="${category}"/>
		 </ol>
	   </div>
	   <div class="span10">
		 <ul class="breadcrumb" style="text-align: left">
		    <cms:frontCurrentPosition category="${category}"/>
		 </ul>
	   </div>
	   <div class="span10">
	     <div class="row">
	       <div class="span10">
			<h3 style="color:#555555;font-size:24px;text-align:center;border-bottom:1px solid #ddd;padding-bottom:15px;margin:25px 0;">${article.title}</h3>
			<c:if test="${not empty article.description}"><div style="font-family: 仿宋; font-size: 12pt">摘要：${article.description}</div></c:if>
				<div style="text-align: left;line-height: 35px;font-size: 22px;">${article.articleData.content}</div>
			   	<div style="text-align: left;margin-top: 30px;">
					   <label style="font-weight: bolder">相关附件:</label>
					   <div style="font-size:12px;">
						   <input type="hidden" id="appendfile" name="appendfile" value="${article.appendfile}" />
						   <sys:ckfinder input="appendfile"  type="files" uploadPath="/cms/article" selectMultiple="false" readonly="true"/>
					   </div>
				</div>
			<div style="border-top:1px solid #ddd;padding:10px;margin:25px 0;">发布者：${article.user.name} &nbsp; 点击数：${article.hits} &nbsp; 发布时间：<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> &nbsp; 更新时间：<fmt:formatDate value="${article.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
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
			<ol><c:forEach items="${relationList}" var="relation">
				<li style="float:left;width:230px;"><a href="${ctx}/view-${relation[0]}-${relation[1]}${urlSuffix}">${fns:abbr(relation[2],30)}</a></li>
			</c:forEach></ol>
	  	  </div>
  	    </div>
  	  </div>
   </div>
</body>
</html>