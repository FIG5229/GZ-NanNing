<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<shiro:hasPermission name="cms:article:edit">
			<c:choose>
				<%--管理员管理所有--%>
				<c:when test="${fns:getUser().admin}">
					<li>
						<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
					</li>
				</c:when>
				<%--制度规定都能看得见，办公室审核--%>
				<c:when test="${article.category.id eq '18'}">

					<c:if test="${fns:getUser().id eq '柳州处办公室（政治处办公室、信访办、保密办）' || fns:getUser().id eq '北海处办公室（政治处办公室、信访办、保密办）' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '南宁处办公室（政治处办公室、信访办、保密办）'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>

					</c:if>
				</c:when>
				<%--通知通报--%>
				<c:when test="${article.category.id eq 'f72a5b908848442cb5606c32c65d4632'}">
					<c:if test="${fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0' ||
						fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb' ||fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' ||fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc' ||
						fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' || fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' ||
						fns:getUser().id eq '4103b50669e9422391fb70aa704266b1' || fns:getUser().id eq '96d26e10bd074eecbc6b8b3a619bec1d' || fns:getUser().id eq '3404d05bf9054a51ae9afbe40e44a718' || fns:getUser().id eq 'bd42300887ad417fa3f2fa9f554e6419' ||
						fns:getUser().id eq '1c19f6cc935f430f9f27295b761b1236' || fns:getUser().id eq '3850cecf34be44188f94b0edc552aff3' || fns:getUser().id eq '957de2956a384bad96adbaa35cb05520' || fns:getUser().id eq '69f857c3e1854021b5dee55c514026e3' ||
						fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' }">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<c:when test="${article.category.id eq '220c0482c9a5442b9c6317ffdefc7381'|| article.category.id eq 'f2cbb2c8063b45b7bab02420777dcc1d'}">
					<%--团委信息管理  团委--%>
					<c:if test="${fns:getUser().id eq '南宁局团委信息管理' || fns:getUser().id eq '南宁处团委信息管理' || fns:getUser().id eq '北海处团委信息管理' || fns:getUser().id eq '柳州处团委信息管理'
										|| fns:getUser().id eq '柳州处团委' || fns:getUser().id eq '南宁处团委' || fns:getUser().id eq 'ff7f9fe2597b40429ded58f8b76a2f65' || fns:getUser().id eq '北海处团委'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--魅力警营--%>
				<c:when test="${article.category.id eq '0b452d0382cb458d81af3e5bf99f93b5'}">
					<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
								|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'
								|| fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<c:when test="${article.category.id eq '335c69c108d243ca9fded21331dcad52' }">
					<%--工会信息管理  工会--%>
					<c:if test="${fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--宣传教育--%>
				<c:when test="${article.category.id eq 'c749a29cdfef44279339b3bdee2a5bff'}">
					<c:if test="${fns:getUser().id eq '94cef1a01d0a47f482dbce5e393d2291' || fns:getUser().id eq '93acf89191bd45bfaeac38aad9aee4e4' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '4e5e56b9fd8f4d3a80e97e59e07d8c5a' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'
					|| fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--警钟长鸣--%>
				<c:when test="${article.category.id eq article.category.id eq '96c02b11a0814f258210ee2d7f128d5f' }">
					<c:if test="${fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--办公室--%>
				<c:when test="${article.category.id eq '1' || article.category.id eq '6' || article.category.id eq '5d7f5d61b84243ddb53ce5c359e6c2f2' || article.category.id eq 'd82d42b9d0ab4c34b5405dbb8e398020' || article.category.id eq '127650291835482991a8b19ddd29029c' ||article.category.id eq '2' ||article.category.id eq '27' ||article.category.id eq '3cac8ac633dd43e2845f6c45b0c9c4ea' ||
							 article.category.id eq '96c02b11a0814f258210ee2d7f128d5f' || article.category.id eq 'c0a6b0c5c94b406abcb86a76d11a5470'|| article.category.id eq '88f067bce5e84ca8986879873aed0719'  }">
					<c:choose>
						<c:when test="${fns:getUser().id eq '柳州处办公室（政治处办公室、信访办、保密办）' || fns:getUser().id eq '南宁处办公室（政治处办公室、信访办、保密办）' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '北海处办公室（政治处办公室、信访办、保密办）' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
							<li>
								<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
							</li>
						</c:when>
						<c:when test="${fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c'}">
							<c:if test="${article.category.id eq '3cac8ac633dd43e2845f6c45b0c9c4ea' || article.category.id eq 'd82d42b9d0ab4c34b5405dbb8e398020' || article.category.id eq '127650291835482991a8b19ddd29029c' }">

							<li>
								<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
							</li>
							</c:if>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:when>
				<%--纪检监察--%>
				<c:when test="${article.category.id eq '44c5b3f7d9e94c9897af22ef67d02292'|| article.category.id eq 'c55f7521049a4a1bbaf9031c929fc142'}">
					<c:if test="${fns:getUser().id eq 'd5ec905f77714c6f8a216e5cbd14ff67' || fns:getUser().id eq 'd5ec905f77714c6f8a216e5cbd14ff67'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--警务督察--%>
				<c:when test="${article.category.id eq 'f0db6af079714a65a8ec11e3b08329c6'}">
					<c:if test="${fns:getUser().id eq '柳州处警务督察支队' || fns:getUser().id eq '北海处警务督察支队' || fns:getUser().id eq '南宁处警务督察支队' || fns:getUser().id eq 'b6de4151cd01411da212432ede766a05'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--组干--%>
				<c:when test="${article.category.id eq '3cc894f99fda44669f6a07370c00500b' || article.category.id eq 'ca4e51b1653e4c18b2724d446297db41'|| article.category.id eq '办公室' }">
					<%--组干账号--%>
					<c:choose>
						<c:when test="${fns:getUser().id eq '柳州处政治处组织干部室' || fns:getUser().id eq '南宁处政治处组织干部室' || fns:getUser().id eq '北海处政治处组织干部室' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3'}">
							<li>
								<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
							</li>
						</c:when>
						<c:when test="${fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb' ||fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' ||fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc' ||
										fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' || fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' ||
										fns:getUser().id eq '4103b50669e9422391fb70aa704266b1' || fns:getUser().id eq '96d26e10bd074eecbc6b8b3a619bec1d' || fns:getUser().id eq '3404d05bf9054a51ae9afbe40e44a718' || fns:getUser().id eq 'bd42300887ad417fa3f2fa9f554e6419' ||
										fns:getUser().id eq '1c19f6cc935f430f9f27295b761b1236' || fns:getUser().id eq '3850cecf34be44188f94b0edc552aff3' || fns:getUser().id eq '957de2956a384bad96adbaa35cb05520' || fns:getUser().id eq '69f857c3e1854021b5dee55c514026e3' ||
										fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' }">
							<li>
								<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
							</li>
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</c:when>
				<%--宣传思想--%>
				<c:when test="${article.category.id eq '04323757d2fd42a29d6347ad04608408' || article.category.id eq '0aaa242b53784f2e92bc19f9bbbab991'|| article.category.id eq '369bbb29d3734a86a39352ddae2ea352' || article.category.id eq '5dd8b3a5aebb4ec789fc01d50f24c6d0'
								|| article.category.id eq '6a0c50ab15d04939a2d3b30ae78fafc6'  || article.category.id eq '20ccf8eca0f944e9b712834398094bc6' || article.category.id eq 'e8b9380de81048519e3c96c911e0118b'
								|| article.category.id eq '4b0037eaba45452f935731d0330b832b'|| article.category.id eq '7305af95c34a41d9850899460ebc5e9d'|| article.category.id eq '6c06afbf33774e18820d6da8d7bd8d4e'
								|| article.category.id eq '2ebc60b79290451ca82caa11a0706f63'|| article.category.id eq 'b7c5372754f04fddbe2d5935de0bf431'}">
					<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
									|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>
				<%--教育训练--%>
				<c:when test="${article.category.id eq 'b0ec2628540e4876945e9e80e562b954' || article.category.id eq 'c094733ed7fd4123b420bc69442914ed' }">
					<c:if test="${fns:getUser().id eq '南宁处教育训练管理' || fns:getUser().id eq '柳州处教育训练管理' || fns:getUser().id eq '北海处教育训练管理' || fns:getUser().id eq '8a6819768aef40968e8f289842613276'}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>
				</c:when>

				<c:otherwise>
				<%--改为管理员管理所有	<c:if test="${fns:getUser().admin}">
						<li>
							<a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a>
						</li>
					</c:if>--%>
				</c:otherwise>
			</c:choose>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>栏目：</label><sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>栏目</th><th>标题</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
<%--				<td><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}">${fns:abbr(article.title,40)}</a></td>--%>
				<td>${fns:abbr(article.title,40)}</td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.user.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
				<td>
					<a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${article.category.id}-${article.id}${fns:getUrlSuffix()}" target="_blank">访问</a>
					<shiro:hasPermission name="cms:article:edit">
						<c:if test="${article.category.allowComment eq '1'}"><shiro:hasPermission name="cms:comment:view">
							<a href="${ctx}/cms/comment/?module=article&contentId=${article.id}&delFlag=2" onclick="return viewComment(this.href);">评论</a>
						</shiro:hasPermission></c:if>
						<%--<c:choose>
							&lt;%&ndash;制度规定都能看得见，办公室审核&ndash;%&gt;
							&lt;%&ndash;<c:when test="${article.category.id eq '18'}">
								<c:if test="${fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '取消处办公室'}">
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
								</c:if>
							</c:when>&ndash;%&gt;
							<c:when test="${article.category.id eq '220c0482c9a5442b9c6317ffdefc7381'|| article.category.id eq 'f2cbb2c8063b45b7bab02420777dcc1d'}">
								&lt;%&ndash;团委信息管理  团委&ndash;%&gt;
								<c:if test="${fns:getUser().id eq '南宁局团委信息管理' || fns:getUser().id eq '南宁处团委信息管理' || fns:getUser().id eq '北海处团委信息管理' || fns:getUser().id eq '柳州处团委信息管理'
								|| fns:getUser().id eq '柳州处团委' || fns:getUser().id eq '南宁处团委' || fns:getUser().id eq 'ff7f9fe2597b40429ded58f8b76a2f65' || fns:getUser().id eq '北海处团委'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							<c:when test="${article.category.id eq '335c69c108d243ca9fded21331dcad52'  }">
								&lt;%&ndash;工会信息管理  工会&ndash;%&gt;
								<c:if test="${fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							&lt;%&ndash;办公室&ndash;%&gt;
							<c:when test="${article.category.id eq '1' || article.category.id eq '6' || article.category.id eq '5d7f5d61b84243ddb53ce5c359e6c2f2' || article.category.id eq 'd82d42b9d0ab4c34b5405dbb8e398020' ||
							article.category.id eq 'c749a29cdfef44279339b3bdee2a5bff' || article.category.id eq '96c02b11a0814f258210ee2d7f128d5f' || article.category.id eq 'c0a6b0c5c94b406abcb86a76d11a5470' || article.category.id eq '88f067bce5e84ca8986879873aed0719' }">
								<c:choose>
									<c:when test="${fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '取消处办公室'}">
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:when>
									<c:otherwise>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${article.category.id eq 'f0db6af079714a65a8ec11e3b08329c6'}">
								<c:if test="${fns:getUser().id eq '柳州处警务督察支队' || fns:getUser().id eq '北海处警务督察支队' || fns:getUser().id eq '南宁处警务督察支队' || fns:getUser().id eq 'b6de4151cd01411da212432ede766a05'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							&lt;%&ndash;组干&ndash;%&gt;
							<c:when test="${article.category.id eq '3cc894f99fda44669f6a07370c00500b' || article.category.id eq 'ca4e51b1653e4c18b2724d446297db41'|| article.category.id eq '办公室' }">
								&lt;%&ndash;组干账号&ndash;%&gt;
								<c:if test="${fns:getUser().id eq '柳州处政治处组织干部室' || fns:getUser().id eq '南宁处政治处组织干部室' || fns:getUser().id eq '北海处政治处组织干部室' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							<c:when test="${article.category.id eq '0b452d0382cb458d81af3e5bf99f93b5'}">
								<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
									|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'
								|| fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							&lt;%&ndash;宣传思想&ndash;%&gt;
							<c:when test="${article.category.id eq '04323757d2fd42a29d6347ad04608408' || article.category.id eq '0aaa242b53784f2e92bc19f9bbbab991'|| article.category.id eq '369bbb29d3734a86a39352ddae2ea352' || article.category.id eq '5dd8b3a5aebb4ec789fc01d50f24c6d0'
								|| article.category.id eq '6a0c50ab15d04939a2d3b30ae78fafc6'  || article.category.id eq '20ccf8eca0f944e9b712834398094bc6' || article.category.id eq 'e8b9380de81048519e3c96c911e0118b'
								|| article.category.id eq '4b0037eaba45452f935731d0330b832b'}">
								<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
									|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							&lt;%&ndash;教育训练&ndash;%&gt;
							<c:when test="${article.category.id eq 'b0ec2628540e4876945e9e80e562b954' || article.category.id eq 'c094733ed7fd4123b420bc69442914ed'|| article.category.id eq '2ebc60b79290451ca82caa11a0706f63' }">
								<c:if test="${fns:getUser().id eq '南宁处教育训练管理' || fns:getUser().id eq '柳州处教育训练管理' || fns:getUser().id eq '北海处教育训练管理' || fns:getUser().id eq '8a6819768aef40968e8f289842613276'}">
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
								<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
								   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
							</c:otherwise>
						</c:choose>--%>
						<%--	    				<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>--%>
						<shiro:hasPermission name="cms:article:audit">
							<%--ddf
							<a href="${ctx}/cms/article/delete?id=${article.id}${article.delFlag ne 0?'&isRe=true':''}&categoryId=${article.category.id}" onclick="return confirmx('确认要${article.delFlag ne 0?'发布':'删除'}该文章吗？', this.href)" >${article.delFlag ne 0?'发布':'删除'}</a>
							--%>
							<c:choose>
								<%--管理员管理所有--%>
								<c:when test="${fns:getUser().admin}">
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
									<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
									<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
									   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>

								</c:when>
								<%--制度规定都能看得见，办公室审核--%>
								<c:when test="${article.category.id eq '18'}">
									<c:choose>
										<c:when test="${fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46'}">
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
											<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
										</c:when>
										<c:otherwise>
											<%--<c:if test="${fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c' || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637'}">
												<c:if test="${fns:getUser().id eq article.createBy.id}">
													<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
													<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
													   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
												</c:if>
											</c:if>--%>
										</c:otherwise>
									</c:choose>

								</c:when>
								<%--通知通报--%>
								<c:when test="${article.category.id eq 'f72a5b908848442cb5606c32c65d4632'}">
									<c:choose>
										<c:when test="${fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
											<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
										</c:when>
										<c:when test="${fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb' ||fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' ||fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc' ||
											fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' || fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' ||
											fns:getUser().id eq '4103b50669e9422391fb70aa704266b1' || fns:getUser().id eq '96d26e10bd074eecbc6b8b3a619bec1d' || fns:getUser().id eq '3404d05bf9054a51ae9afbe40e44a718' || fns:getUser().id eq 'bd42300887ad417fa3f2fa9f554e6419' ||
											fns:getUser().id eq '1c19f6cc935f430f9f27295b761b1236' || fns:getUser().id eq '3850cecf34be44188f94b0edc552aff3' || fns:getUser().id eq '957de2956a384bad96adbaa35cb05520' || fns:getUser().id eq '69f857c3e1854021b5dee55c514026e3' ||
											 fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' }">
											<c:if test="${fns:getUser().id eq article.createBy.id}">
												<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
												<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
												   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
											</c:if>
										</c:when>
										<c:otherwise>
											<%--处办公室可添加  不可发布--%>
											<c:if test="${fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c'  || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637'}">
												<c:if test="${fns:getUser().id eq article.createBy.id}">
													<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
													<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
													   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
												</c:if>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:when test="${article.category.id eq '220c0482c9a5442b9c6317ffdefc7381'|| article.category.id eq 'f2cbb2c8063b45b7bab02420777dcc1d'}">
									<%--团委信息管理  团委--%>
									<c:if test="${fns:getUser().id eq '南宁局团委信息管理' || fns:getUser().id eq '南宁处团委信息管理' || fns:getUser().id eq '北海处团委信息管理' || fns:getUser().id eq '柳州处团委信息管理'
										|| fns:getUser().id eq '柳州处团委' || fns:getUser().id eq '南宁处团委' || fns:getUser().id eq 'ff7f9fe2597b40429ded58f8b76a2f65' || fns:getUser().id eq '北海处团委'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--魅力警营--%>
								<c:when test="${article.category.id eq '0b452d0382cb458d81af3e5bf99f93b5'}">
									<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
									|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'
								|| fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<c:when test="${article.category.id eq '335c69c108d243ca9fded21331dcad52' }">
									<%--工会信息管理  工会--%>
									<c:if test="${fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除' || fns:getUser().id eq '已删除'
								|| fns:getUser().id eq '北海处工会' || fns:getUser().id eq '柳州处工会' || fns:getUser().id eq 'b91d9ac0c32847c4ab6f21e910959198' || fns:getUser().id eq '南宁处工会'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--宣传教育--%>
								<c:when test="${article.category.id eq 'c749a29cdfef44279339b3bdee2a5bff'}">
									<c:choose>
										<c:when test="${ fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c'}">
											<c:if test="${fns:getUser().id eq article.createBy.id}">
												<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
												<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
												   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
											</c:if>
										</c:when>
									<c:when test="${fns:getUser().id eq '94cef1a01d0a47f482dbce5e393d2291' || fns:getUser().id eq '93acf89191bd45bfaeac38aad9aee4e4' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '4e5e56b9fd8f4d3a80e97e59e07d8c5a'|| fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:when>
									</c:choose>
								</c:when>
								<%--警钟长鸣--%>
								<c:when test="${article.category.id eq article.category.id eq '96c02b11a0814f258210ee2d7f128d5f' }">
									<c:if test="${fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--办公室--%>
								<c:when test="${article.category.id eq '1' || article.category.id eq '6' || article.category.id eq '5d7f5d61b84243ddb53ce5c359e6c2f2' || article.category.id eq 'd82d42b9d0ab4c34b5405dbb8e398020' || article.category.id eq '127650291835482991a8b19ddd29029c' ||article.category.id eq '2' ||article.category.id eq '27' ||article.category.id eq '3cac8ac633dd43e2845f6c45b0c9c4ea' ||
								  article.category.id eq 'c0a6b0c5c94b406abcb86a76d11a5470'|| article.category.id eq '88f067bce5e84ca8986879873aed0719' || fns:getUser().id eq '9eb03b9877d74a349629bb5a8d49fbc0'}">
									<c:choose>
										<c:when test="${fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '取消处办公室' || fns:getUser().id eq '54e8fb917a8241c08c04bb3dbe4dee46' || fns:getUser().id eq '取消处办公室'}">
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
											<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
										</c:when>
										<c:when test="${fns:getUser().id eq 'bcb9ae19e88a478fb66bd47322c9b637' || fns:getUser().id eq 'c86a5e277ebb44c584972a81e039f890' || fns:getUser().id eq '8d498ce4a66642f2ac57fd557269fa5c'}">
											<c:if test="${fns:getUser().id eq article.createBy.id}">
												<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
												<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
												   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
											</c:if>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:when>
								<%--纪检监察--%>
								<%--10-7
								将nnjjjjcc账号里的内容管理调到jjxxgl账户里--%>
								<c:when test="${article.category.id eq '44c5b3f7d9e94c9897af22ef67d02292'|| article.category.id eq 'c55f7521049a4a1bbaf9031c929fc142'}">
									<c:if test="${fns:getUser().id eq 'd5ec905f77714c6f8a216e5cbd14ff67' || fns:getUser().id eq 'd5ec905f77714c6f8a216e5cbd14ff67'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--警务督察--%>
								<c:when test="${article.category.id eq 'f0db6af079714a65a8ec11e3b08329c6'}">
									<c:if test="${fns:getUser().id eq '柳州处警务督察支队' || fns:getUser().id eq '北海处警务督察支队' || fns:getUser().id eq '南宁处警务督察支队' || fns:getUser().id eq 'b6de4151cd01411da212432ede766a05'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--组干--%>
								<c:when test="${article.category.id eq '3cc894f99fda44669f6a07370c00500b' || article.category.id eq 'ca4e51b1653e4c18b2724d446297db41'|| article.category.id eq '办公室' }">
									<%--组干账号--%>
									<c:choose>
										<c:when test="${fns:getUser().id eq '柳州处政治处组织干部室' || fns:getUser().id eq '南宁处政治处组织干部室' || fns:getUser().id eq '北海处政治处组织干部室' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3'}">
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
											<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
											<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
											   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
										</c:when>
										<c:when test="${fns:getUser().id eq '8c95559336ab47c688e78b9d4bd04984' ||fns:getUser().id eq '6eb08ded66314165a692f76d8f660abb' ||fns:getUser().id eq 'fcaf9421273643d587baaa88735fe661' ||fns:getUser().id eq 'ff371386d33a407b9c1e652b613de2bc' ||
											fns:getUser().id eq 'b016b34a09bd45349b3e1ecc5fde9c5d' || fns:getUser().id eq '630ee4f59967404488d3a3f1fdec8381' || fns:getUser().id eq 'a699d2e49a3443739e451e294fb66410' || fns:getUser().id eq '6502f47ca7bf45539a848135ee3c6bc3' ||
											fns:getUser().id eq '4103b50669e9422391fb70aa704266b1' || fns:getUser().id eq '96d26e10bd074eecbc6b8b3a619bec1d' || fns:getUser().id eq '3404d05bf9054a51ae9afbe40e44a718' || fns:getUser().id eq 'bd42300887ad417fa3f2fa9f554e6419' ||
											fns:getUser().id eq '1c19f6cc935f430f9f27295b761b1236' || fns:getUser().id eq '3850cecf34be44188f94b0edc552aff3' || fns:getUser().id eq '957de2956a384bad96adbaa35cb05520' || fns:getUser().id eq '69f857c3e1854021b5dee55c514026e3' ||
											fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658'}">
											<c:if test="${fns:getUser().id eq article.createBy.id}">
												<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
												<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
												   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
											</c:if>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</c:when>
								<%--宣传思想--%>
								<c:when test="${article.category.id eq '04323757d2fd42a29d6347ad04608408' || article.category.id eq '0aaa242b53784f2e92bc19f9bbbab991'|| article.category.id eq '369bbb29d3734a86a39352ddae2ea352' || article.category.id eq '5dd8b3a5aebb4ec789fc01d50f24c6d0'
								|| article.category.id eq '6a0c50ab15d04939a2d3b30ae78fafc6'  || article.category.id eq '20ccf8eca0f944e9b712834398094bc6' || article.category.id eq 'e8b9380de81048519e3c96c911e0118b'
								|| article.category.id eq '4b0037eaba45452f935731d0330b832b'|| article.category.id eq '7305af95c34a41d9850899460ebc5e9d'|| article.category.id eq '6c06afbf33774e18820d6da8d7bd8d4e'
								|| article.category.id eq '2ebc60b79290451ca82caa11a0706f63' || article.category.id eq 'b7c5372754f04fddbe2d5935de0bf431'}">
									<c:if test="${fns:getUser().id eq '北海处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '柳州处政治处宣传教育室（民警训练基地）' || fns:getUser().id eq '8a6819768aef40968e8f289842613276' || fns:getUser().id eq '南宁处政治处宣传教育室（民警训练基地）'
									|| fns:getUser().id eq '南宁处宣传思想管理' || fns:getUser().id eq '柳州处宣传教育管理' || fns:getUser().id eq '北海处宣传思想管理' || fns:getUser().id eq '66937439b2124f328d1521968fab06db'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>
								<%--教育训练--%>
								<c:when test="${article.category.id eq 'b0ec2628540e4876945e9e80e562b954' || article.category.id eq 'c094733ed7fd4123b420bc69442914ed' }">
									<c:if test="${fns:getUser().id eq '南宁处教育训练管理' || fns:getUser().id eq '柳州处教育训练管理' || fns:getUser().id eq '北海处教育训练管理' || fns:getUser().id eq '8a6819768aef40968e8f289842613276'}">
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>
								</c:when>

								<c:otherwise>
									<%--改为管理员管理所有
									<c:if test="${fns:getUser().admin}">

										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=0&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要发布该文章吗？', this.href)">发布</a>
										<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
										<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}"
										   onclick="return confirmx('确认要删除该文章吗？', this.href)">删除</a>
									</c:if>--%>
								</c:otherwise>
							</c:choose>

<%--							<a href="${ctx}/cms/article/delete?id=${article.id}&delFlag=1&categoryId=${article.category.id}" onclick="return confirmx('确认要删除该文章吗？', this.href)" >删除</a>--%>

						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>