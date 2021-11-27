<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>固资管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGz/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGz/?tabFlag=${affairGz.tabFlag}");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGz/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGz/?tabFlag=${affairGz.tabFlag}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

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
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGz", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGz?tabFlag=${affairGz.tabFlag}"}});
			});
            $("[data-toggle='popover']").popover();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "固资管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairGz/?tabFlag="+$("#tabFlag").val()}
			});
		}
		function openDialog(myCheckBox) {
			var id = getIds(myCheckBox);
			if (id.length == 1) {
				top.$.jBox.open("iframe:${ctx}/affair/affairGz/shenHeDialog?id="+id, "固资审核",800,420,{
					buttons:{"关闭":true},
					loaded:function(){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},closed:function (){
						self.location.href="${ctx}/affair/affairGz/?tabFlag=${affairGz.tabFlag}";
					}
				});
			}else {
				$.jBox.tip('请先选择要审核的内容且只能单条审核');
			}
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGz/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"固资管理详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        $('#notPass').popover();
	</script>
</head>
<body>
<input type="hidden" value="${affairGz.tabFlag}" name="tabFlag" id="tabFlag">
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairGz/">管理</a></li>
	</ul>
	<c:choose>
		<c:when test="${'4c40b4dd2aee459286a37538978e6261' eq fns:getUser().id}">
			<ul class="nav nav-tabs">
				<c:choose>
					<c:when test="${1 == tabFlag}">
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=156">北海公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairGz/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:when test="${34 == tabFlag}">
						<li class="active"><a href="${ctx}/affair/affairGz/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:when test="${95 == tabFlag}">
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=34">南宁公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairGz/list?tabFlag=95">柳州公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=1">公安局机关</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=34">南宁公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=95">柳州公安处</a></li>
						<li class="active"><a href="${ctx}/affair/affairGz/list?tabFlag=156">北海公安处</a></li>
						<li><a href="${ctx}/affair/affairGz/list?tabFlag=1">公安局机关</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
	<form:form id="searchForm" modelAttribute="affairGz" action="${ctx}/affair/affairGz/?tabFlag=${affairGz.tabFlag}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="固资管理表.xlsx"/>
		<ul class="ul-form">
			<li><label>使用时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGz.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairGz.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>使用单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairGz.unitId}" labelName="unit" labelValue="${affairGz.unit}"
								title="使用单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>固资名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>保管人：</label>
				<form:input path="bgPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>验收人员：</label>
				<form:input path="ysPerson" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>合计总价：</label>
				<form:input path="minPrice" htmlEscape="false" class="input-medium"/>
				至
				<form:input path="maxPrice" htmlEscape="false" class="input-medium"/>元
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairGz:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairGz/form?tabFlag=${tabFlag}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGz/deleteByIds','checkAll','myCheckBox')"/></li>
		<%--		<shiro:hasPermission name="affair:affairGz:manage">
					<li class="btns"><input class="btn btn-primary"  type="button" value="审核" onclick="openDialog('myCheckBox')"/></li>
				</shiro:hasPermission>
		--%>	<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGz'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>使用时间</th>
				<th>使用单位</th>
				<th>固资名称</th>
				<th>编号</th>
				<th>条形码</th>
				<th>数量</th>
				<th>单价(元)</th>
				<th>保管人</th>
				<th>预计年限</th>
				<th>合计总价(元)</th>
				<th>型号</th>
				<th>规格</th>
             <th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGz" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGz.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairGz.date}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${affairGz.unit}
				</td>
				<td>
					${affairGz.name}
				</td>
				<td>
						${affairGz.serialNumber}
				</td>
				<td>
						${affairGz.barCode}
				</td>
				<td>
						${affairGz.num}
				</td>
				<td>
						${affairGz.price}
				</td>
				<td>
						${affairGz.bgPerson}
				</td>
				<td>
						${affairGz.userYear}
				</td>
				<td>
						${affairGz.totalPrice}
				</td>
				<td>
						${affairGz.model}
				</td>
				<td>
					${affairGz.specification}
				</td>
               <%-- <td>
                    <c:choose>
                        <c:when test="${affairGz.shType == '4'}">
                            <a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
                               data-content="${affairGz.shOpinion}"  style="cursor: pointer;color: red">审核未通过</a>
                        </c:when>
						<c:when test="${affairGz.shType == '2'}">
							<font color="red">${fns:getDictLabel(affairGz.shType, 'affair_query_shenhe', '')}</font>
						</c:when>
                        <c:otherwise>
                            ${fns:getDictLabel(affairGz.shType, 'affair_query_shenhe', '')}
                        </c:otherwise>
                    </c:choose>
                </td>--%>
				<td class="handleTd">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairGz.id}')">查看</a>
					<shiro:hasPermission name="affair:affairGz:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairGz/form?id=${affairGz.id}')">修改</a>
						<a href="${ctx}/affair/affairGz/delete?id=${affairGz.id}" onclick="return confirmx('确认要删除该固资管理吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<script>
	if ("success" == "${saveResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>