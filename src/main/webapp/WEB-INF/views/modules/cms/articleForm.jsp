<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">归属栏目:</label>
			<div class="controls">
                <sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="required"/>&nbsp;
                <span>
                    <input id="url" type="checkbox" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()"><label for="url">外部链接</label>
                </span>
			</div>
		</div>
		<div class="control-group">
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
        <div id="linkBody" class="control-group" style="display:none">
            <label class="control-label">外部链接:</label>
            <div class="controls">
                <form:input path="link" htmlEscape="false" maxlength="200" class="input-xlarge"/>
                <span class="help-inline">绝对或相对地址。</span>
            </div>
        </div>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<span class="help-inline">多个关键字，用空格分隔。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权重:</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="200" class="input-mini required digits"/>&nbsp;
				<span>
					<input id="weightTop" type="checkbox" onclick="$('#weight').val(this.checked?'999':'0')"><label for="weightTop">置顶</label>
				</span>
				&nbsp;过期时间：
				<input id="weightDate" name="weightDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${article.weightDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline">数值越大排序越靠前，过期时间可为空，过期后取消置顶。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缩略图:</label>
			<div class="controls">
				<input type="hidden" id="image" name="image" value="${article.imageSrc}" />
				<sys:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/>
				<!--
				<form:hidden id="filesName" path="image" value="${article.imageSrc}"  htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:webuploader input="filesName" type="files" uploadPath="cms/files" selectMultiple="true"/>
				-->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正文:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/cms/article" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件:</label>
			<div class="controls">
				<input type="hidden" id="appendfile" name="appendfile" value="${article.appendfile}" />
				<c:choose>
					<%--1、 2、纪检监察栏目  3、廉政教育栏目--%>
					<c:when test="${'f2fa306bb4f446fc9c1d6f38e7033488'.equals(article.category.id) || '44c5b3f7d9e94c9897af22ef67d02292' eq article.category.id || 'c55f7521049a4a1bbaf9031c929fc142' eq article.category.id}">
						<sys:ckfinder input="appendfile"  type="files" uploadPath="/cms/article" selectMultiple="true"/>
					</c:when>
					<%---青橄榄
					<c:when test="${'f2cbb2c8063b45b7bab02420777dcc1d'.equals(article.category.id)}">
						<form:hidden id="filesName" path="appendfile" value="${article.appendfile}"  htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:webuploader input="filesName" type="files" uploadPath="cms/files" selectMultiple="true"/>
					</c:when>--%>
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
		<div class="control-group">
			<label class="control-label">来源:</label>
			<div class="controls">
				<form:input path="articleData.copyfrom" htmlEscape="false" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关文章:</label>
			<div class="controls">
				<form:hidden id="articleDataRelation" path="articleData.relation" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<ol id="articleSelectList"></ol>
				<a id="relationButton" href="javascript:" class="btn">添加相关</a>
				<script type="text/javascript">
					var articleSelect = [];
					function articleSelectAddOrDel(id,title){
						var isExtents = false, index = 0;
						for (var i=0; i<articleSelect.length; i++){
							if (articleSelect[i][0]==id){
								isExtents = true;
								index = i;
							}
						}
						if(isExtents){
							articleSelect.splice(index,1);
						}else{
							articleSelect.push([id,title]);
						}
						articleSelectRefresh();
					}
					function articleSelectRefresh(){
						$("#articleDataRelation").val("");
						$("#articleSelectList").children().remove();
						for (var i=0; i<articleSelect.length; i++){
							$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
							$("#articleDataRelation").val($("#articleDataRelation").val()+articleSelect[i][0]+",");
						}
					}
					$.getJSON("${ctx}/cms/article/findByIds",{ids:$("#articleDataRelation").val()},function(data){
						for (var i=0; i<data.length; i++){
							articleSelect.push([data[i][1],data[i][2]]);
						}
						articleSelectRefresh();
					});
					$("#relationButton").click(function(){
						top.$.jBox.open("iframe:${ctx}/cms/article/selectList?pageSize=8", "添加相关",$(top.document).width()-220,$(top.document).height()-180,{
							buttons:{"确定":true}, loaded:function(h){
								$(".jbox-content", top.document).css("overflow-y","hidden");
							}
						});
					});
				</script>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否允许评论:</label>
			<div class="controls">
				<form:radiobuttons path="articleData.allowComment" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐位:</label>
			<div class="controls">
				<form:checkboxes path="posidList" items="${fns:getDictList('cms_posid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间:</label>
			<div class="controls">
				<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<shiro:hasPermission name="cms:article:edit">
			<div class="control-group">
				<label class="control-label">发布状态:</label>
				<c:choose>

			<c:when test="${article.category.id.equals('04323757d2fd42a29d6347ad04608408')}">
				<%--栏目id对应权限--%>
				<%--对应的栏目拥有权限才显示 发布按钮--%>
				<shiro:hasPermission name="affair:article:tabloid">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
				</shiro:hasPermission>
			</c:when>
				<c:when test="${article.category.id.equals('2')}">
					<shiro:hasPermission name="affair:article:survey">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('6')}">
					<shiro:hasPermission name="affair:article:speech">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('18')}">
					<shiro:hasPermission name="affair:article:regulations">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('27')}">
					<shiro:hasPermission name="affair:article:mailbox">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('f72a5b908848442cb5606c32c65d4632')}">
					<shiro:hasPermission name="affair:article:notification">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('20c8c197133f4d9bac39351c207b7559')}">
					<shiro:hasPermission name="affair:article:special">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('3cac8ac633dd43e2845f6c45b0c9c4ea')}">
					<shiro:hasPermission name="affair:article:research">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('d82d42b9d0ab4c34b5405dbb8e398020')}">
					<shiro:hasPermission name="affair:article:briefing">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('220c0482c9a5442b9c6317ffdefc7381')}">
					<shiro:hasPermission name="affair:article:youth">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('44c5b3f7d9e94c9897af22ef67d02292')}">
					<shiro:hasPermission name="affair:article:supervision">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('5d7f5d61b84243ddb53ce5c359e6c2f2')}">
					<shiro:hasPermission name="affair:article:focusNews">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('335c69c108d243ca9fded21331dcad52')}">
					<shiro:hasPermission name="affair:article:protection">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('f0db6af079714a65a8ec11e3b08329c6')}">
					<shiro:hasPermission name="affair:article:inspector">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('4b0037eaba45452f935731d0330b832b')}">
					<shiro:hasPermission name="affair:article:bookstore">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('c55f7521049a4a1bbaf9031c929fc142')}">
					<shiro:hasPermission name="affair:article:education">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('c0a6b0c5c94b406abcb86a76d11a5470')}">
					<shiro:hasPermission name="affair:article:stone">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('e8b9380de81048519e3c96c911e0118b')}">
					<shiro:hasPermission name="affair:article:mustRead">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('d6ce6b47645446dbb7aa950e15438f57')}">
					<shiro:hasPermission name="affair:article:video">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('f2fa306bb4f446fc9c1d6f38e7033488')}">
					<shiro:hasPermission name="affair:article:electronic">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('3cc894f99fda44669f6a07370c00500b')}">
					<shiro:hasPermission name="affair:article:announcement">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('127650291835482991a8b19ddd29029c')}">
					<shiro:hasPermission name="affair:article:trends">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('96c02b11a0814f258210ee2d7f128d5f')}">
					<shiro:hasPermission name="affair:jingzhong:manage">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
				<c:when test="${article.category.id.equals('c749a29cdfef44279339b3bdee2a5bff')}">
					<shiro:hasPermission name="affair:xuanchuan:manage">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
					</shiro:hasPermission>
				</c:when>
					<c:when test="${article.category.id.equals('f2cbb2c8063b45b7bab02420777dcc1d')}">
						<shiro:hasPermission name="affair:article:olive">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('88f067bce5e84ca8986879873aed0719')}">
						<shiro:hasPermission name="affair:article:ntga">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('0b452d0382cb458d81af3e5bf99f93b5')}">
						<shiro:hasPermission name="affair:article:charm">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>








					<c:when test="${article.category.id.equals('369bbb29d3734a86a39352ddae2ea352')}">
						<shiro:hasPermission name="affair:article:brand">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('5dd8b3a5aebb4ec789fc01d50f24c6d0')}">
						<shiro:hasPermission name="affair:article:literature">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('6a0c50ab15d04939a2d3b30ae78fafc6')}">
						<shiro:hasPermission name="affair:article:read">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('20ccf8eca0f944e9b712834398094bc6')}">
						<shiro:hasPermission name="affair:article:media">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('b0ec2628540e4876945e9e80e562b954')}">
						<shiro:hasPermission name="affair:article:report">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('c094733ed7fd4123b420bc69442914ed')}">
						<shiro:hasPermission name="affair:article:model">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('2ebc60b79290451ca82caa11a0706f63')}">
						<shiro:hasPermission name="affair:article:thought">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('ca4e51b1653e4c18b2724d446297db41')}">
						<shiro:hasPermission name="affair:party:trends">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
						</shiro:hasPermission>
					</c:when>
					<c:when test="${article.category.id.equals('b7c5372754f04fddbe2d5935de0bf431')}">
						<shiro:hasPermission name="affair:article:praise">
							<span>
								<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
								<label for="delFlag1">发布</label>
							</span>
						</shiro:hasPermission>
					</c:when>


					<c:otherwise>
						<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
						</span>
					</c:otherwise>
				</c:choose>
<%--				<shiro:hasAnyPermissions name="affair:article:tabloid,affair:article:survey,affair:article:regulations,affair:article:notification,
affair:article:briefing,affair:article:youth,affair:article:trends,affair:article:protection,affair:article:bookstore,affair:article:stone,
affair:article:mustRead,affair:article:video,affair:article:electronic,affair:article:speech,affair:article:mailbox,affair:article:special,
affair:article:research,affair:article:supervision,affair:article:focusNews,affair:article:announcement,affair:article:inspector,affair:article:education">
					<span>
						<input id="delFlag1" name="delFlag" class="required" type="radio" value="0" checked="checked">
						<label for="delFlag1">发布</label>
					</span>
				</shiro:hasAnyPermissions>--%>
					<span>
						<input id="delFlag2" name="delFlag" class="required" type="radio" value="2">
						<label for="delFlag2">审核</label>
					</span>
					<span>
						<input id="delFlag3" name="delFlag" class="required" type="radio" value="1">
						<label for="delFlag3">删除</label>
					</span>
					<span class="help-inline"></span>
				</div>
			</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="cms:category:edit">
            <div class="control-group">
                <label class="control-label">自定义内容视图:</label>
                <div class="controls">
                      <form:select path="customContentView" class="input-medium">
                          <form:option value="" label="默认视图"/>
                          <form:options items="${contentViewList}" htmlEscape="false"/>
                      </form:select>
                      <span class="help-inline">自定义内容视图名称必须以"${article_DEFAULT_TEMPLATE}"开始</span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">自定义视图参数:</label>
                <div class="controls">
                      <form:input path="viewConfig" htmlEscape="true"/>
                      <span class="help-inline">视图参数例如: {count:2, title_show:"yes"}</span>
                </div>
            </div>
		</shiro:hasPermission>
		<c:if test="${not empty article.id}">
			<div class="control-group">
				<label class="control-label">查看评论:</label>
				<div class="controls">
					<input id="btnComment" class="btn" type="button" value="查看评论" onclick="viewComment('${ctx}/cms/comment/?module=article&contentId=${article.id}&status=0')"/>
					<script type="text/javascript">
						function viewComment(href){
							top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-180,{
								buttons:{"关闭":true},
								loaded:function(h){
									$(".jbox-content", top.document).css("overflow-y","hidden");
									$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
									$("body", h.find("iframe").contents()).css("margin","10px");
								}
							});
							return false;
						}
					</script>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>