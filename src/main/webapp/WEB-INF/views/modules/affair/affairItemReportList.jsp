<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人事项报告表管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("[data-toggle='popover']").popover();
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairItemReport/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairItemReport/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairItemReport/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairItemReport/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairItemReport", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairItemReport"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        function openForm(url) {
            top.$.jBox.open("iframe:"+url, "个人事项报告表管理",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairItemReport"}
            });
        }
		$('#notPass').popover();
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairItemReport/formDetail?id="+id;
			top.$.jBox.open(url, "",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
        //审核弹窗
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairItemReport/shenHeDialog?id="+id, "个人事项审核",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairItemReport";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairItemReport/">个人事项报告</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairItemReport" action="${ctx}/affair/affairItemReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="个人事项报告.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>单位名称：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairItemReport.unitId}" labelName="unit" labelValue="${affairItemReport.unit}"
					title="单位名称" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>警号：</label>
				<form:input path="policeNo" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>审核状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('item_manage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairItemReport:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairItemReport/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairItemReport/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<shiro:hasPermission name="affair:affairItemReport:manage">
				<li class="btns"><input  class="btn btn-primary" type="button" value="审核" onclick="openDialog('${ctx}/affair/affairItemReport/shenHeDialog')"/></li>
			</shiro:hasPermission>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairItemReport'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
                <th>序号</th>
				<th>姓名</th>
				<th>单位名称</th>
				<th>警号</th>
				<th>婚姻状况</th>
				<th>户口性质</th>
				<th>户籍所在地详址</th>
				<th>审核状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairItemReport" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairItemReport.id}"/></td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
                <td>
                ${affairItemReport.name}
				</td>
				<td>
					${affairItemReport.unit}
				</td>
				<td>
					${affairItemReport.policeNo}
				</td>
				<td>
					${fns:getDictLabel(affairItemReport.marriageStatus, 'affair_marital_status', '')}
				</td>
				<td>
					${affairItemReport.hk}
				</td>
				<td>
					${affairItemReport.address}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairItemReport.status == '3'}">
							<a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
							   data-content="${affairItemReport.opinion}"  style="cursor: pointer;color: red">不通过</a>
						</c:when>
						<c:when test="${affairItemReport.status == '1'}">
							<font color="red">${fns:getDictLabel(affairItemReport.status, 'item_manage', '')}</font>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairItemReport.status, 'item_manage', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairItemReport.id}')">查看</a>
					<shiro:hasPermission name="affair:affairItemReport:edit">
					<c:if test="${affairItemReport.createBy.id == fns:getUser().id}">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairItemReport/form?id=${affairItemReport.id}')">修改</a>
                   	<a href="${ctx}/affair/affairItemReport/delete?id=${affairItemReport.id}" onclick="return confirmx('确认要删除该个人事项报告表吗？', this.href)">删除</a>
					</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>