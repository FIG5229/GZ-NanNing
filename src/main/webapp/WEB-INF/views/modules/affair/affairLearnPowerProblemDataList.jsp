<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习强国问题数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#print").click(function(){
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});

		});



		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairLearnPower/formDetail?id="+id;
			top.$.jBox.open(url, "学习强国详情",1000,400,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //添加，修改弹窗
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "学习强国",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLearnPower/affairLearnPowerProblemDataList"}
			});
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //问题数据页面
        function openProblemDataDialog() {
			top.$.jBox.open("iframe:${ctx}/affair/affairLearnPower/affairLearnPowerProblemDataList", "学习强国问题数据页面",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					//self.location.href="${ctx}/affair/affairLearnPower"
				}
			});
		}
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairLearnPower" action="${ctx}/affair/affairLearnPower/affairLearnPowerProblemDataList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>创建人：</label>
				<sys:treeselect id="createByIdpro" name="createById_pro" value="${affairLearnPower.createById_pro}" labelName="createByName_pro" labelValue="${affairLearnPower.createByName_pro}"
								title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"  />

			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLearnPower:edit">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLearnPower/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLearnPower/affairLearnPowerProblemDataList'"/></li>
			<li class="clearfix"></li>
		</ul> 
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>时间</th>
				<th>姓名</th>
				<th>身份证号码</th>
				<th>本月学习积分</th>
				<th>创建人</th>
				<shiro:hasPermission name="affair:affairLearnPower:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLearnPower" varStatus="status">
			<tr>

				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLearnPower.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${affairLearnPower.unit}
				</td>
				<td>
						${affairLearnPower.time}
				</td>

				<td>
						${affairLearnPower.name}
				</td>
				<td>
						${affairLearnPower.idNumber}
				</td>
				<%--<td>
						${affairLearnPower.lastMonthIntegral}
				</td>--%>
				<%--<td>
						${affairLearnPower.thisMonthStatistics}
				</td>--%>
				<td>
						${affairLearnPower.thisMonthIntegral}
				</td>
				<%--<td>
						${affairLearnPower.thisYearStatistics}
				</td>--%>
				<td>${fns:getUserById(affairLearnPower.createBy.id).name}</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairLearnPower:edit">
						<a onclick="openDetailDialog('${affairLearnPower.id}')">查看</a>
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairLearnPower/form?id=${affairLearnPower.id}')">修改</a>
						<a href="${ctx}/affair/affairLearnPower/affairLearnPowerProblemDataList/delete?id=${affairLearnPower.id}" onclick="return confirmx('确认要删除该学习强国吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div style="color: red">--"月度学习强国"中每个身份证在每个月只可以存在一条记录，请勿重复添加 </div>
	<div style="color: red">--该页面所展示数据为问题数据，出现该问题数据原因可能为：导入时，未将时间的单元格格式设置为文本格式 </div>
	<div style="color: red">--时间字段格式应为 yyyy-MM 例如 2021-01 </div>
</body>
</html>