<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>福利慰问管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
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
                            $("#searchForm").attr("action","${ctx}/affair/affairWelfareCondolences/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairWelfareCondolences/list?treeId");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairWelfareCondolences/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairWelfareCondolences/list");
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
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairBenefits", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairWelfareCondolences/list"}});
            });

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function openAddForm(url) {
			top.$.jBox.open("iframe:"+url, "会员福利",1200,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairWelfareCondolences"}
			});
		}


		function openEditDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairWelfareCondolences/form?id="+id,"会员福利编辑",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairWelfareCondolences"}
			});
		}
		function openDetailDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairWelfareCondolences/formDetail?id="+id,"会员福利详情",1200,600,{
				buttons:{"关闭":true},
				loaded:function () {
					$(".jbox-content",top.document).css("overflow-y","hidden");
				},closed:function () {self.location.href="${ctx}/affair/affairWelfareCondolences"

				}
			});
		}

		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairWelfareCondolences/">会员福利</a></li>
		<%--<shiro:hasPermission name="affair:affairWelfareCondolences:edit"><li><a href="${ctx}/affair/affairWelfareCondolences/form">福利慰问添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairWelfareCondolences" action="${ctx}/affair/affairWelfareCondolences/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="fileName" name="fileName" type="hidden" value="会员福利信息.xlsx"/>
		<ul class="ul-form">
			<%--<li><label>时间：</label>
				<input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWelfareCondolences.date}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>--%>
			<li><label>时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWelfareCondolences.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWelfareCondolences.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairWelfareCondolences.unitId}" labelName="unit" labelValue="${affairWelfareCondolences.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				<%--<form:input path="unit" htmlEscape="false" class="input-medium"/>--%>
			</li>
			<li><label>福利项目</label>
			<form:select path="type" class="input-medium">
			<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_benefits')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%--<form:input path="type" htmlEscape="false" class="input-medium"/>--%>
			</li>
				<%--隐藏优化--%>
		<%--	<li><label>标题：</label>
				<form:input path="title" htmlEscape="false" class="input-medium"/>
			</li>--%>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>经办人：</label>
					<form:input path="manager" htmlEscape="false" class="input-medium"/>
			</li>

			<%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairWelfareCondolences:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddForm('${ctx}/affair/affairWelfareCondolences/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairWelfareCondolences/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairWelfareCondolences'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
<%--				<th>标题</th>--%>
				<th>姓名</th>
				<th>单位</th>
				<th>福利项目</th>
				<th>慰问金（慰问品）</th>
				<th>发生日期</th>
				<th>经办人</th>
				<th>录入日期</th>
				<shiro:hasPermission name="affair:affairWelfareCondolences:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="affairWelfareCondolences" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWelfareCondolences.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
			<%--	<td>
						${affairWelfareCondolences.title}
				</td>--%>
				<td>
					${affairWelfareCondolences.name}
				</td>
				<td>
					${affairWelfareCondolences.unit}
				</td>
				<td>
					${fns:getDictLabel(affairWelfareCondolences.type, 'affair_benefits', '')}
				</td>
				<td>
					${affairWelfareCondolences.money}
				</td>
				<td>
					<fmt:formatDate value="${affairWelfareCondolences.occurDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairWelfareCondolences.manager}
				</td>
				<td>
					<fmt:formatDate value="${affairWelfareCondolences.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="handleTd">
				<a href="javascript:;" onclick="openDetailDialog('${affairWelfareCondolences.id}')">查看</a>
				<shiro:hasPermission name="affair:affairWelfareCondolences:edit">
    				<a href="javascript:;" onclick="openEditDialog('${affairWelfareCondolences.id}')">修改</a>
					<a href="${ctx}/affair/affairWelfareCondolences/delete?id=${affairWelfareCondolences.id}" onclick="return confirmx('确认要删除该福利慰问吗？', this.href)">删除</a>
				</shiro:hasPermission>
			</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>