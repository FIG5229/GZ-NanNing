<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党委中心组学习管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//处分种类改变时触发事件，联动子选项
			$("#method").change(function(){
				showAndHide();
			});
			//控制处分种类子选项下拉框的隐藏与显示
			function showAndHide(){
				if($("#method").val() == '1' || $("#method").val() == '2' || $("#method").val() == '3' || $("#method").val() == '5'){
					$('#otherMethod').css('display', 'none');
				}else if($("#method").val() == '4'){
					$('#otherMethod').css('display', 'inline-block');
				}
			}
			showAndHide();
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairGroupStudy/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGroupStudy/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairGroupStudy/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairGroupStudy/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairGroupStudy", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairGroupStudy"}});
			});

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
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "党委中心组学习",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairGroupStudy/formDetail?id="+id;
			top.$.jBox.open(url, "党委中心组学习详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairGroupStudy:view"><li  class="active"><a href="${ctx}/affair/affairGroupStudy/">党委中心组学习</a></li></shiro:hasPermission>
<%--		<shiro:hasPermission name="affair:affairFocusStudy:view"><li><a href="${ctx}/affair/affairFocusStudy/">党支部集中学习</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairGroupStudy" action="${ctx}/affair/affairGroupStudy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="2.1党委中心组学习.xlsx"/>
		<ul class="ul-form">
			<li><label>学习时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairGroupStudy.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairGroupStudy.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>学习地点：</label>
				<form:input path="place" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>学习形式：</label>
				<form:select id="method" path="method" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_study_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:input id="otherMethod" path="otherMethod" htmlEscape="false" class="input-xlarge" cssStyle="display: none;"/>
			</li>
			<li><label>主持人：</label>
				<form:input path="host" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label class="width120">中心发言人员：</label>
				<form:input path="zxPerson" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairGroupStudy:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairGroupStudy/form?id=${affairGroupStudy.id}')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairGroupStudy/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairGroupStudy'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>学习时间</th>
				<th>学习地点</th>
				<th>学习形式</th>
				<th>主持人</th>
				<th>中心发言人员</th>
				<th>记录人</th>
				<th>学习内容</th>
				<shiro:hasPermission name="affair:affairGroupStudy:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairGroupStudy" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairGroupStudy.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairGroupStudy.organization}
				</td>
				<td>
					<fmt:formatDate value="${affairGroupStudy.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairGroupStudy.place}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairGroupStudy.method == '4'}">
							${fns:getDictLabel(affairGroupStudy.method, 'affair_study_type', '')}&nbsp;${affairGroupStudy.otherMethod}
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairGroupStudy.method, 'affair_study_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					${affairGroupStudy.host}
				</td>
				<td>
					${affairGroupStudy.zxPerson}
				</td>
				<td>
					${affairGroupStudy.recorder}
				</td>
				<td>
						${affairGroupStudy.content}
				</td>

				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairGroupStudy.id}')">查看</a>
					<shiro:hasPermission name="affair:affairGroupStudy:edit">
							<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairGroupStudy/form?id=${affairGroupStudy.id}')">修改</a>
							<a href="${ctx}/affair/affairGroupStudy/delete?id=${affairGroupStudy.id}" onclick="return confirmx('确认要删除该党委中心组学习吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>