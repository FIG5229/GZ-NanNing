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
		.arcticle-list li{
			margin-left: 10px;
			padding: 5px 0 5px 13px;
			background: url(../../../../../politics/static/images/bot-red.png) no-repeat left center;
			background-size: 4px 4px;
			text-align: left;
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
		 <ul class="breadcrumb" style="text-align: left">
		    <cms:frontCurrentPosition category="${category}"/>
		 </ul>
	   </div>
       <div class="span10">
		  <h4>${category.name}</h4>
		  <c:if test="${category.module eq 'article'}">
			<ul class="arcticle-list">
				<c:choose>
					<c:when test="${'两微一端'.equals(category.name)||'宁铁警视'.equals(category.name)}">
						<c:forEach items="${page.list}" var="article">
							<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${pageContext.request.contextPath}/a/cms/article/video?path=${article.appendfile}" target="_blank">${fns:abbr(article.title,96)}</a></li>
						</c:forEach>
					</c:when>
					<c:when test="${'青橄榄'.equals(category.name)}">
						<c:forEach items="${page.list}" var="article">
							<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${pageContext.request.contextPath}/a/cms/article/pic?paths=${article.appendfile.replaceAll('\\|',';')}" target="_blank">${fns:abbr(article.title,96)}</a></li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${page.list}" var="article">
							<li><span class="pull-right"><fmt:formatDate value="${article.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${article.url}" style="color:${article.color}">${fns:abbr(article.title,96)}</a></li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</ul>
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