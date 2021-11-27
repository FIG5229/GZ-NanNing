<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位集体奖励申报管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairXcRewardDeclaration/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXcRewardDeclaration/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairXcRewardDeclaration/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairXcRewardDeclaration/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_XcRewardDeclaration", "导入",800,520,{title:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairXcRewardDeclaration"}});
			});

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "单位奖励申报",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        function submitByIds() {
			if(null == $("#chuCheckManName").val() || "" ==  $("#chuCheckManName").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
        }
        function openShDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairXcRewardDeclaration/shenHeDialog?id="+id,"奖励申报审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairXcRewardDeclaration/list";
				}
			});
        }
        //详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairXcRewardDeclaration/formDetail?id="+id;
			top.$.jBox.open(url, "单位申报详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //添加审核弹窗
		/*function openShDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairXcRewardDeclaration/shenHeDialog?id="+id, "审核",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairXcRewardDeclaration/";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}*/
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairXcRewardDeclaration/">单位集体奖励申报</a></li>
		<li><a href="${ctx}/affair/affairRewardDeclaration/">个人奖励申报</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairXcRewardDeclaration" action="${ctx}/affair/affairXcRewardDeclaration/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="4.3单位集体奖励申报表 .xlsx"/>
		<ul class="ul-form">
			<li><label>申报时间：</label>
				<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairXcRewardDeclaration.beginDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairXcRewardDeclaration.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairXcRewardDeclaration.unitId}" labelName="unit" labelValue="${affairXcRewardDeclaration.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label>奖励名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>荣誉级别：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_approval_unitLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
<%--					更换为统一字典<form:options items="${fns:getDictList('affair_jtsb_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairXcRewardDeclaration:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairXcRewardDeclaration/form?id=${affairXcRewardDeclaration.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairXcRewardDeclaration/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairXcRewardDeclaration'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>申报时间</th>
				<th>申报单位</th>
				<th>审核通过单位</th>
				<th>奖励名称</th>
				<th>简要事迹</th>
				<th>申报状态</th>
				<shiro:hasPermission name="affair:affairXcRewardDeclaration:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairXcRewardDeclaration" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairXcRewardDeclaration.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairXcRewardDeclaration.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairXcRewardDeclaration.unit}
				</td>
				<td>
					${affairXcRewardDeclaration.approvalUnit}
				</td>
				<td>
					${affairXcRewardDeclaration.name}
				</td>
				<td>
					${affairXcRewardDeclaration.remark}
				</td>
				<td>
						${fns:getDictLabel(affairXcRewardDeclaration.sbType, 'sb_type', '')}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairXcRewardDeclaration.id}')">查看</a>
				<shiro:hasPermission name="affair:affairXcRewardDeclaration:delete">
					<a href="${ctx}/affair/affairXcRewardDeclaration/delete?id=${affairXcRewardDeclaration.id}" onclick="return confirmx('确认要删除该单位集体奖励申报吗？', this.href)">删除</a>
			</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairXcRewardDeclaration:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairXcRewardDeclaration/form?id=${affairXcRewardDeclaration.id}')">修改</a>
					</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairXcRewardDeclaration:shenhe">
					<c:if test="${ '5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">
					<a onclick="openShDialog('${affairXcRewardDeclaration.id}')">审核</a>
					</c:if>
				</shiro:hasPermission>
			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="affairXcRewardDeclaration" action="${ctx}/affair/affairXcRewardDeclaration/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择申报单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="chuCheckMan" name="chuCheckId" value="${affairXcRewardDeclaration.chuCheckId}" labelName="chuCheckMan" labelValue="${affairXcRewardDeclaration.chuCheckMan}"
							title="单位" url="/sys/office/treeData?type=4" cssClass="required" allowClear="true" notAllowSelectParent="false" dataMsgRequired="必填信息" isAll="true"/>
			<input  class="btn btn-primary" type="button" value="申报" onclick="submitByIds('${ctx}/affair/affairXcRewardDeclaration/submitByIds')"/>
		</ul>
	</form:form>
</body>
</html>