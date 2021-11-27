<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>党支部集中学习管理</title>
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
								$("#searchForm").attr("action","${ctx}/affair/affairFocusStudy/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairFocusStudy/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairFocusStudy/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairFocusStudy/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairFocusStudy", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairFocusStudy"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "党支部集中学习",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairFocusStudy"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairFocusStudy/formDetail?id="+id;
			top.$.jBox.open(url, "党支部集中学习详情",800,500,{
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
		<shiro:hasPermission name="affair:affairGroupStudy:view"><li ><a href="${ctx}/affair/affairGroupStudy/">党委中心组学习</a></li></shiro:hasPermission>
<%--		<shiro:hasPermission name="affair:affairFocusStudy:view"><li class="active"><a href="${ctx}/affair/affairFocusStudy/">党支部集中学习</a></li></shiro:hasPermission>--%>
<%--		<shiro:hasPermission name="affair:affairLearnDaily:view"><li ><a href="${ctx}/affair/affairLearnDaily/">单位政治学习</a></li></shiro:hasPermission>--%>

		<%--		<shiro:hasPermission name="affair:affairFocusStudy:edit"><li><a href="${ctx}/affair/affairFocusStudy/form">党支部集中学习添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairFocusStudy" action="${ctx}/affair/affairFocusStudy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="党支部集中学习表.xlsx"/>
		<ul class="ul-form">
			<li><label>学习时间：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairFocusStudy.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairFocusStudy.endDate}" pattern="yyyy-MM-dd"/>"
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
			<shiro:hasPermission name="affair:affairFocusStudy:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairFocusStudy/form?id=${affairFocusStudy.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairFocusStudy:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairFocusStudy/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairFocusStudy:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairFocusStudy'"/></li>
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
				<th>学习内容</th>
				<th>学习时间</th>
				<th>学习地点</th>
				<th>学习形式</th>
				<th>主持人</th>
				<th>中心发言人员</th>
				<th>记录人</th>
				<shiro:hasPermission name="affair:affairFocusStudy:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairFocusStudy" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairFocusStudy.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
                <td>
                    ${affairFocusStudy.unit}
                </td>
				<td>
					${affairFocusStudy.content}
				</td>
				<td>
					<fmt:formatDate value="${affairFocusStudy.date}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairFocusStudy.place}
				</td>
				<td>
					<c:choose>
						<c:when test="${affairFocusStudy.method == '4'}">
							${fns:getDictLabel(affairFocusStudy.method, 'affair_study_type', '')}&nbsp;${affairFocusStudy.otherMethod}
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(affairFocusStudy.method, 'affair_study_type', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					${affairFocusStudy.host}
				</td>
				<td>
					${affairFocusStudy.zxPerson}
				</td>
				<td>
					${affairFocusStudy.recorder}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairFocusStudy:edit">
							<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairFocusStudy/form?id=${affairFocusStudy.id}')">修改</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairFocusStudy:delete">
						<a href="${ctx}/affair/affairFocusStudy/delete?id=${affairFocusStudy.id}" onclick="return confirmx('确认要删除该党支部集中学习吗？', this.href)">删除</a>
					</shiro:hasPermission>
					<a onclick="openDetailDialog('${affairFocusStudy.id}')">查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>