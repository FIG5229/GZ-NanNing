<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习强国管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLearnPower/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLearnPower/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLearnPower/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLearnPower/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						/*'导出全部数据': 'all',*/
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出当前页面数据': 'all','取消':'cancel'} });
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLearnPower", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
					closed:function (){self.location.href="${ctx}/affair/affairLearnPower"}});
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
				}
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
					self.location.href="${ctx}/affair/affairLearnPower"
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairLearnDaily:view">
			<li>
				<a href="${ctx}/affair/affairLearnDaily/">单位政治理论学习</a>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairLearnPower:view">
			<li class="active" >
				<a href="${ctx}/affair/affairLearnPower/">学习强国</a>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairNetworkCollege:view">
			<li>
				<a href="${ctx}/affair/affairNetworkCollege/">中国干部网络学院</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<ul class="nav nav-tabs">
		<shiro:hasPermission name="affair:affairLearnPower:view">
			<li class="active" >
				<a href="${ctx}/affair/affairLearnPower/">月度学习强国</a>
			</li>
		</shiro:hasPermission>
		<shiro:hasPermission name="affair:affairLearnPowerYear:view">
			<li>
				<a href="${ctx}/affair/affairLearnPowerYear/">年度学习强国</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLearnPower" action="${ctx}/affair/affairLearnPower/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="2.3月度学习强国.xlsx"/>
		<ul class="ul-form">
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairLearnPower.unitId}" labelName="unit" labelValue="${affairLearnPower.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li>
				<label>时间：</label>
				<input name="time" path="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLearnPower.time}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairLearnPower:edit">
				<li class="btns">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairLearnPower/form?id=${affairLearnPower.id}')"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairLearnPower:edit">
				<%--推广使用问题0224
						学习强国个人账户登录有可以修改、删除功能，不合理，可能导致个人随意修改数据。（南宁处）
				--%>
				<c:if test="${isPerson eq 'false'}">
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLearnPower/deleteByIds','checkAll','myCheckBox')"/></li>
				</c:if>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairLearnPower:edit">
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input id="problemBtn" class="btn btn-primary" type="button" value="问题数据" onclick="openProblemDataDialog()"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLearnPower'"/></li>
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
				<th>时间</th>
				<th>姓名</th>
				<th>身份证号码</th>
				<%--<th>上月平台累计积分</th>--%>
				<%--<th>本月平台累计积分</th>--%>
				<th>本月学习积分</th>
				<th>年度学习积分</th>
				<shiro:hasPermission name="affair:affairLearnPower:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="affairLearnPower" varStatus="status">
			<tr>

				<td>
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
				<td>
						${affairLearnPower.thisYearStatistics}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairLearnPower:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairLearnPower.id}')">查看</a>
						<%--推广使用问题0224
							学习强国个人账户登录有可以修改、删除功能，不合理，可能导致个人随意修改数据。（南宁处）
						--%>
						<c:if test="${isPerson eq 'false'}">
							<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairLearnPower/form?id=${affairLearnPower.id}')">修改</a>
							<a href="${ctx}/affair/affairLearnPower/delete?id=${affairLearnPower.id}" onclick="return confirmx('确认要删除该学习强国吗？', this.href)">删除</a>
						</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<div style="color: red">--"月度学习强国"中每个身份证在每个月只可以存在一条记录，请勿重复添加 --</div>
	<div style="color: red">--时间字段格式应为 yyyy-MM 例如 2021-01</div>
</body>
</html>