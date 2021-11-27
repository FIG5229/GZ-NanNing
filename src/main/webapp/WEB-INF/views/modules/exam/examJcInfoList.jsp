<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖惩信息库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/exam/examJcInfo/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/exam/examJcInfo/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/exam/examJcInfo/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/exam/examJcInfo/");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=exam_examJcInfo", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/exam/examJcInfo"}});
			});
			$("#jcObject").change(function () {
				jcObject();
			});
			function jcObject() {
				if($("#jcObject").val() == '0'){
					$('#jcObjectPersonnel').css('display', 'inline-block');
					$('#idNumber').css('display', 'inline-block');
				}else if($("#ischeck").val() == '1'){
					$('#jcObjectPersonnel').css('display', 'none');
					$('#idNumber').css('display', 'none');
				}
			}
			//调用
			jcObject();
		});

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/exam/examJcInfo/formDetail?id="+id;
			top.$.jBox.open(url, "奖惩信息详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "奖惩信息库添加",860,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/exam/examJcInfo"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<c:choose>
			<c:when test="${mType==null}">
				<li ><a href="${ctx}/exam/examCheck?mType=1">查询</a></li>
				<shiro:hasPermission name="exam:examCheck:edit">
					<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="exam:examJcInfo:view">
					<li class="active"><a href="${ctx}/exam/examJcInfo/">奖惩信息推送情况管理</a></li>
				</shiro:hasPermission>
			</c:when>
			<c:otherwise>
				<li ><a href="${ctx}/exam/examCheck?mType=1">查询</a></li>
				<shiro:hasPermission name="exam:examCheck:edit">
					<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="exam:examCheck:edit">
					<li class="active"><a href="${ctx}/exam/examJcInfo/">奖惩信息推送情况管理</a></li>
				</shiro:hasPermission>
			</c:otherwise>
		</c:choose>--%>
		<%--奖惩推送页面--%>
			<c:choose>
				<c:when test="${mType==null}">
					<li><a href="${ctx}/exam/examCheck/">检查情况</a></li>
					<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
<%--					<shiro:hasPermission name="exam:examAutoEvaluation:view">--%>
						<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
<%--					</shiro:hasPermission>--%>
					<li class="active"><a href="${ctx}/exam/examJcInfo/">其他数据</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${ctx}/exam/examCheck?mType=1">检查情况</a></li>
					<shiro:hasPermission name="exam:examCheck:edit">
						<li ><a href="${ctx}/exam/examCheck/">检查情况管理</a></li>
					</shiro:hasPermission>
					<li><a href="${ctx}/exam/examCheckChild/">问题整改</a></li>
					<shiro:hasPermission name="exam:examCheck:edit">
						<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="exam:examAutoEvaluation:view">
						<li ><a href="${ctx}/exam/examAutoEvaluation/">推送管理</a></li>
					</shiro:hasPermission>
					<li class="active"><a href="${ctx}/exam/examJcInfo/">其他数据</a></li>
				</c:otherwise>
			</c:choose>
	</ul>
	<form:form id="searchForm" modelAttribute="examJcInfo" action="${ctx}/exam/examJcInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="奖惩信息库.xlsx"/>
		<ul class="ul-form">
			<li><label>奖惩类型：</label>
				<form:select path="jcType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jc_types')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li>
				<label class="control-label">奖惩对象：</label>
				<form:select  id="jcObject" path="jcObject" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jc_objects')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<form:input path="jcObjectPersonnel" htmlEscape="false" class="input-xlarge " cssStyle="display: none; width: 100px;" />
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "  cssStyle="display: none; width: 200px"/>
			</li>
			<li>
				<label class="control-label">单位：</label>

					<sys:treeselect id="jcObjectUnit" name="jcObjectUnitId" value="${examJcInfo.jcObjectUnitId}" labelName="jcObjectUnit" labelValue="${examJcInfo.jcObjectUnit}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/exam/examJcInfo/form')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/exam/examJcInfo/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/exam/examJcInfo'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>奖惩类型</th>
				<th>选择项</th>
				<th>名称</th>
				<th>加减分情况</th>
				<th>人</th>
				<th>单位</th>
				<th>使用模板</th>
				<shiro:hasPermission name="exam:examJcInfo:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="examJcInfo" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${examJcInfo.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					<fmt:formatDate value="${examJcInfo.jcDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(examJcInfo.jcType, 'jc_types', '')}
				</td>
				<td>
					${examJcInfo.changeType}
				</td>
				<td><a onclick="openDetailDialog('${examJcInfo.id}')">
					${examJcInfo.jcTypeName}
				</a>
				</td>
				<td>
					${examJcInfo.jcCode}
				</td>
				<td>
					${examJcInfo.jcObjectPersonnel}
				</td>
				<td>
					${examJcInfo.jcObjectUnit}
				</td>
				<td>
					${examJcInfo.myUseModelName}
				</td>
				<shiro:hasPermission name="exam:examJcInfo:edit"><td class="handleTd">
					<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfo/form?id=${examJcInfo.id}')">修改</a>
					<a href="${ctx}/exam/examJcInfo/delete?id=${examJcInfo.id}" onclick="return confirmx('确认要删除该奖惩信息库吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>