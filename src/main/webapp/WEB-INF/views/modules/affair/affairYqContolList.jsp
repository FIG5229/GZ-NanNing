<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>舆情管控管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairYqContol/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairYqContol/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairYqContol/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairYqContol/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_yqContol", "导入",800,520,{title:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairYqContol"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "舆情管控",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairYqContol/formDetail?id="+id;
			top.$.jBox.open(url, "舆情管控详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//推送功能
		function submitByIds() {
			if(null == $("#manName").val() || "" ==  $("#manName").val()){
				$.jBox.tip('请选择推送单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要推送的内容');
			}
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairYqContol:view"><li class="active"><a href="${ctx}/affair/affairYqContol/">舆情处置</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairYqContol" action="${ctx}/affair/affairYqContol/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="6.2舆情处置.xlsx"/>
		<ul class="ul-form">
			<li><label>舆情标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>发生日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairYqContol.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairYqContol.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>发现日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairYqContol.beginDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairYqContol.finishDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>舆情来源：</label>
				<form:select path="source" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_yqly')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>责任单位：</label>
						<sys:treeselect id="zrUnit" name="zrUnitId" value="${affairYqContol.zrUnitId}" labelName="zrUnit" labelValue="${affairYqContol.zrUnit}"
										title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>责任单位：</label>
						<sys:treeselect id="zrUnit" name="zrUnitId" value="${affairYqContol.zrUnitId}" labelName="zrUnit" labelValue="${affairYqContol.zrUnit}"
										title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>责任单位：</label>
				<sys:treeselect id="zrUnit" name="zrUnitId" value="${affairYqContol.zrUnitId}" labelName="zrUnit" labelValue="${affairYqContol.zrUnit}"
							title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>

			<%--<div class="control-group">
				<label class="control-label">责任单位：</label>
				<div class="controls">
					<sys:treeselect id="zrUnit" name="zrUnitId" value="${affairYqContol.zrUnitId}" labelName="zrUnit" labelValue="${affairYqContol.zrUnit}"
									title="责任单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true" dataMsgRequired="必填信息"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairYqContol:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairYqContol/form?id=${affairYqContol.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairYqContol/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairYqContol'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>舆情标题</th>
				<th>舆情发生日期</th>
				<th>舆情发现日期</th>
				<th>舆情来源</th>
				<th>处置情况</th>
				<th>舆情内容</th>
				<th>责任单位</th>
				<shiro:hasPermission name="affair:affairYqContol:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairYqContol" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairYqContol.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairYqContol.title}
				</td>
				<td>
					<fmt:formatDate value="${affairYqContol.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${affairYqContol.foundDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(affairYqContol.source, 'affair_yqly', '')}
				</td>
				<td>
						${fns:getDictLabel(affairYqContol.situation, 'affair_yqcz', '')}
				</td>
				<td>
					${affairYqContol.content}
				</td>
				<td>
						${affairYqContol.zrUnit}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairYqContol:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairYqContol.id}')">查看</a>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairYqContol/form?id=${affairYqContol.id}')">修改</a>
						<a href="${ctx}/affair/affairYqContol/delete?id=${affairYqContol.id}" onclick="return confirmx('确认要删除该舆情管控吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairYqContol" action="${ctx}/affair/affairYqContol/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择推送单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="man" name="manId" value="${affairYqContol.manId}"
							labelName="man" labelValue="${affairYqContol.man}"
							title="部门" url="/sys/office/treeData?type=3" allowClear="true"
							notAllowSelectParent="true" checked="true" isAll="true"/>

			<input  class="btn btn-primary" type="button" value="推送" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>