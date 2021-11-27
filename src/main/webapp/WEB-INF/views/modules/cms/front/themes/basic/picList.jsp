<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>${category.name}</title>
	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="${category.description}" />
	<meta name="keywords" content="${category.keywords}" />
	<style>
		.arcticle-list{
			overflow: hidden;
		    padding-top: 15px;
		}
		.arcticle-list li{
			float: left;
			width: 230px;
			height: 185px;
			margin-left: 10px;
			padding: 5px 0 5px 13px;
		}
		.arcticle-list li a{
			width: 100%;
			display: block;
			height: 30px;
			line-height:30px;
			text-align: center;
		}
		.arcticle-list li .pic-info{
			width:100%;
			height:150px;
			background-position: center;
			 background-size: cover;
			 background-repeat: no-repeat;
		}
		.row h4{
			color: #ffffff;
			background:linear-gradient(270deg,rgba(255,157,140,1) 0%,rgba(249,36,0,1) 100%);
			border-left:none;
		}
		.row .span10 h4{
			margin: 0 0 5px;
		}
		.row .span2 ol li a{
			padding-left:20px
		}
	</style>
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
		 <ul class="breadcrumb">
		    <cms:frontCurrentPosition category="${category}"/>
		 </ul>
	   </div>
       <div class="span10">
		  <h4>${category.name}</h4>
		  <c:if test="${category.module eq 'article'}">
			<ul class="arcticle-list"><c:forEach items="${page.list}" var="article">
				<li>
					<div class="pic-info" style="background-image: url(../../../../politics/static/images/pic1.png);"></div>
					<a class="line1Hidden" href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,96)}</a>
				</li>
			</c:forEach></ul>
			<div class="pagination">${page}</div>
			<script type="text/javascript">
				function page(n,s){
					location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+n+"&pageSize="+s;
				}
			</script>
		  </c:if>
		  <c:if test="${category.module eq 'link'}">
			<ul class="arcticle-list"><c:forEach items="${page.list}" var="link">
				<li><a href="${link.href}" target="_blank" style="color:${link.color}"><c:out value="${link.title}" /></a></li>
			</c:forEach></ul>
		  </c:if>
  	  </div>
   </div>
</body>
</html>