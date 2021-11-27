<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>读书先进--集体管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#btnPrint").click(function(){
				$('#handleTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('#checkTh').css('display', 'none');
				$('.checkTd').css('display', 'none');
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

			$("#btnExport").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedCollective/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedCollective/list?treeId");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedCollective/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairAdvancedCollective/list");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairAdvancedCollective", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairAdvancedCollective/list"}});
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairAdvancedCollective/form?id="+id,"",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				}
			});
		}

		//添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, " ",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}


		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairAdvancedCollective/formDetail?id="+id,"",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairAdvancedCollective"
				}
			});
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairBookCatalog/">历年书目列表</a></li>
		<li class="active"><a href="${ctx}/affair/affairAdvancedCollective/ ">读书先进--集体</a></li>
		<li><a href="${ctx}/affair/affairAdvancedPerson/ ">读书先进--个人</a></li>
		<li><a href="${ctx}/affair/affairActive/ ">活动情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairAdvancedCollective" action="${ctx}/affair/affairAdvancedCollective/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="读书先进集体表.xlsx"/>
		<ul class="ul-form">
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairAdvancedCollective.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairAdvancedCollective.unitId}" labelName="unit" labelValue="${affairAdvancedCollective.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairAdvancedCollective.unitId}" labelName="unit" labelValue="${affairAdvancedCollective.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairAdvancedCollective.unitId}" labelName="unit" labelValue="${affairAdvancedCollective.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairAdvancedCollective:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairAdvancedCollective/form?id=${affairAdvancedCollective.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairAdvancedCollective/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairAdvancedCollective'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>年度</th>
				<th>单位</th>
				<shiro:hasPermission name="affair:affairAdvancedCollective:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairAdvancedCollective" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairAdvancedCollective.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairAdvancedCollective.year}
				</td>
				<td>
						${affairAdvancedCollective.unit}
				</td>
				<td class="handleTd">
					<a href="javascript:;" onclick="openDetailDialog('${affairAdvancedCollective.id}')">查看</a>
					<shiro:hasPermission name="affair:affairAdvancedCollective:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairAdvancedCollective.id}')">修改</a>
						<a href="${ctx}/affair/affairAdvancedCollective/delete?id=${affairAdvancedCollective.id}" onclick="return confirmx('确认要删除该读书先进--集体吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>