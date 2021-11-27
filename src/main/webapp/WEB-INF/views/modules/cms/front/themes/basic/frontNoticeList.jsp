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
		 	通知通报
		 </ol>
		 <h4>推荐阅读</h4>
		 <ol>
		 </ol>
	   </div>
	   <div class="span10">
		 <ul class="breadcrumb" style="text-align: left">
			 <li><strong>当前位置：</strong><a href="${ctx}/index/">首页</a></li>
			 <li><span class="divider">/</span> <a href="${ctx}/index/list-notice">通知通报</a></li>
		 </ul>
	   </div>
       <div class="span10">
		  <h4>通知通报</h4>
		   <ul class="arcticle-list"><c:forEach items="${page.list}" var="notice">
			   <li><span class="pull-right"><fmt:formatDate value="${notice.updateDate}" pattern="yyyy.MM.dd"/></span><a href="${ctx}/noticeView?noticeId=${notice.id}" >${fns:abbr(notice.title,96)}</a></li>
		   </c:forEach></ul>
		   <div class="pagination">${page}</div>
		   <script type="text/javascript">
			   function page(n,s){
				   location="${ctx}/list-notice?pageNo="+n+"&pageSize="+s;
			   }
		   </script>
  	  </div>
   </div>
</body>
</html>