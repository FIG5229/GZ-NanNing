<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>督察问题库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var v = $("#reasons").val();
			$("#reason").val(v);
			$("[data-toggle='popover']").popover();
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairDcwtLibrary/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDcwtLibrary/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairDcwtLibrary/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairDcwtLibrary/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairDcwtLibrary", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairDcwtLibrary"}});
			});
		});

		function openDialog(url,title) {
			top.$.jBox.open("iframe:"+url, title,800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){$("#searchForm").submit();}
			});
		}


		/*function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit(function (e) {
				var form = $(this);
				var url = form.attr('action');
				$.ajax({
					url: "${ctx}/affair/affairDcwtLibrary/",
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: JSON.stringify(form.serializeJSON()),
					success: function (data) {
						if (data.success) {
							$('#reason').val(data.reason);
							//重新加载列表
							getData();
						} else {
							alert(data.message);
						}

					}
				});
				return false;
			})
        };*/
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			return false;
		}
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "督察问题库",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDcwtLibrary"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDcwtLibrary/formDetail?id="+id;
			top.$.jBox.open(url, "督察问题库",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairDcwtLibrary/propelling?id="+id;
			top.$.jBox.open(url, "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDcwtLibrary"}
			});
		}
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairDcwtLibrary/">督察问题库</a></li>
<%--		<shiro:hasPermission name="affair:affairDcwtLibrary:edit"><li><a href="${ctx}/affair/affairDcwtLibrary/form">督察问题库添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDcwtLibrary" action="${ctx}/affair/affairDcwtLibrary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="reasons" name="reasons" type="hidden" value="${reasons}"/>
		<input id="fileName" name="fileName" type="hidden" value="督察问题库表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>排序：</label>
				<select id="reason" style="width: 140px;" name="reason">
					<option value="" > </option>
					<option value="1" >开始时间</option>
					<option value="2">修改时间</option>
				</select>
			</li>
			<li><label>开始时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairDcwtLibrary.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairDcwtLibrary.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<%--<li><label>责任单位：</label>
				<sys:treeselect id="responsibleUnit" name="responsibleUnitId" value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairDcwtLibrary.responsibleUnit}"
								title="责任单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>--%>
			<li><label>存在不足：</label>
				<form:select path="problemCategory" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wtlb')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>整改情况：</label>
				<form:select path="rectification" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_zhenggai')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>监督单位：</label>
				<form:select path="supervisoryUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_jdunit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>督察方式：</label>
				<form:select path="dcType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dc_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>责任单位：</label>
				<sys:treeselect id="responsibleUnit" name="responsibleUnitId" value="${affairDcwtLibrary.responsibleUnitId}" labelName="responsibleUnit" labelValue="${affairDcwtLibrary.responsibleUnit}"
								title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" isAll="true" />
			</li>		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDcwtLibrary:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairDcwtLibrary/form?id=${affairDcwtLibrary.id}')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairDcwtLibrary:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDcwtLibrary/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDcwtLibrary'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>责任单位</th>
				<th>监督单位</th>
				<th>督察方式</th>
				<th>存在不足</th>
				<th>问题概述</th>
				<th>整改情况</th>
				<th>整改说明</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDcwtLibrary" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDcwtLibrary.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairDcwtLibrary.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairDcwtLibrary.finishDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairDcwtLibrary.responsibleUnit}
				</td>
				<td>
					${fns:getDictLabel(affairDcwtLibrary.supervisoryUnit, 'affair_jdunit', '')}
				</td>
				<td>
						${fns:getDictLabel(affairDcwtLibrary.dcType, 'affair_dc_type', '')}
				</td>
				<td>
					<c:forEach items="${affairDcwtLibrary.problemCategory.split(',')}" var="arr" varStatus="status">
						<c:choose>
							<c:when test="${status.index==0}">${fns:getDictLabel(arr, 'affair_wtlb', '')}</c:when>
							<c:otherwise>
								,${fns:getDictLabel(arr, 'affair_wtlb', '')}
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</td>
				<td>
						<c:choose>
							<c:when test="${!empty affairDcwtLibrary.foundProblem}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${affairDcwtLibrary.foundProblem}"  style="cursor: pointer;color: red">详情</a>
							</c:when>
							<c:otherwise>
								${affairDcwtLibrary.foundProblem}
							</c:otherwise>
						</c:choose>
				</td>
				<td>
					${fns:getDictLabel(affairDcwtLibrary.rectification, 'affair_zhenggai', '')}
				</td>
				<td>

						<c:choose>
							<c:when test="${!empty affairDcwtLibrary.processingSituation}">
								<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
								   data-content="${affairDcwtLibrary.processingSituation}"  style="cursor: pointer;color: red">详情</a>
							</c:when>
							<c:otherwise>
								${affairDcwtLibrary.processingSituation}
							</c:otherwise>
						</c:choose>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairDcwtLibrary.id}')">查看</a>
						<%--10-7问题反馈，使用派出所账号登陆，点击“警务督察”-》“监督问题库”，发现各派出所均有“推送”这一选项，应删除--%>

					<c:if test="${fns:getUser().id eq 'ca32723864644fa8979693dc9a539b91' || fns:getUser().id eq '1fdedc31fd6944eb8cbb9a279f4cb3c4' ||
				fns:getUser().id eq '26449823050b49c786f7baff26b6a7a2' || fns:getUser().id eq 'ad04613867cc41f081c78ac19f159263'}">
							<a href="javascript:void(0)" onclick="openPropellingDialog('${affairDcwtLibrary.id}')">推送</a>
						</c:if>

<%--					<a onclick="openPropellingDialog('${affairDcwtLibrary.id}')">推送</a>--%>

					<shiro:hasPermission name="affair:affairDcwtLibrary:update">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDcwtLibrary/form?id=${affairDcwtLibrary.id}')">修改</a>
<%--						<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examJcInfoPlus/affairDcwtLibraryform?id=${affairDcwtLibrary.id}')">推送到奖惩信息库</a>--%>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/exam/examAutoEvaluation/pushUnitRewardsLibrary?id=${affairDcwtLibrary.id}&pushFrom=督察问题库','推送到绩效考评')">推送到绩效考评</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairDcwtLibrary:delete">
							<a href="${ctx}/affair/affairDcwtLibrary/delete?id=${affairDcwtLibrary.id}" onclick="return confirmx('确认要删除该督察问题库吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>