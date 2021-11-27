<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔测算</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript"  src="${ctxStatic}/js/export.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#searchForm").validate({
				submitHandler: function(form){
					loading('正在加载，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
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
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceRank/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceRank", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "警衔信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/personnel/personnelPoliceRank/formDetail?id="+id;
            top.$.jBox.open(url, "警衔信息",900,500,{
                persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

        function exportData() {
			dataExport("contentTable");
		}


		function queryData() {

			$("#searchForm").attr("action", "${ctx}/personnel/personnelPoliceRank/calculateBeta?removeCache=is");
			//debugger;er;
			$("#searchForm").submit();
			$("#searchForm").attr("action", "${ctx}/personnel/personnelPoliceRank/calculateBeta");
		}
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li><a href="${ctx}/personnel/personnelPoliceRank/?mType=${mType}">警衔信息</a></li>--%>
		<li class="active"><a href="${ctx}/personnel/personnelPoliceRank/calculateBeta?mType=${mType}">警衔测算</a></li>
<%--		<li><a href="${ctx}/personnel/personnelPoliceRank/analysis?mType=${mType}">统计分析</a></li>--%>
	</ul>
				<form:form id="searchForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/calculateBeta" onclick="queryData()" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value=""/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="mType" name="mType" type="hidden" value="${mType}"/>
					<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceRank.idNumber}"/>
					<input id="fileName" name="fileName" type="hidden" value="衔称信息集.xlsx"/>
					<ul class="ul-form">
						<%--<li><label>起算日期：</label>
							<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.beginDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.finishDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
						<li><label>授衔日期：</label>
							<input name="sxStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.sxStartDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="sxEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.sxEndDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>

						<li><label>警衔名称：</label>
							<form:input path="name" htmlEscape="false" class="input-medium"/>
						</li>--%>
						<li><label>预测类型：</label>
							<form:select path="calculateType" class="input-medium">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('type_rank_calculation')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</li>
							<li><label>截止日期：</label>
								<input name="endFinishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									   value="<fmt:formatDate value="${personnelPoliceRank.endFinishDate}" pattern="yyyy-MM-dd"/>"
									   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							</li>
					<%--	<li><label>授衔令号：</label>
							<form:input path="approvalNumber" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label class="width120">授衔批准单位名称：</label>
							<form:input path="approvalUnitName" htmlEscape="false" class="input-medium"/>
						</li>--%>
						<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
						<li class="btns"><input id="exportCeSuan" class="btn btn-primary" type="button" value="导出" onclick="exportData()"/></li>
						<li class="clearfix"></li>
					</ul>
					<%--<ul class="ul-form2">
						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">
							<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?mType=1','${ctx}/personnel/personnelPoliceRank?mType=1')"/></li>
							<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceRank/deleteByIds','checkAll','myCheckBox')"/></li>
							<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/personnel/personnelPoliceRank?mType=1')"/></li>
						</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceRank?mType=1&idNumber=${personnelPoliceRank.idNumber}'"/></li>
						<li class="clearfix"></li>
					</ul>--%>
				</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
<%--				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>--%>
				</c:if>
				<th>序号</th>
<%--				<th>单位</th>--%>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>参加工作日期</th>
				<th>学制年限</th>
				<th>职务</th>
				<th>职务层次</th>
				<th>任职时间</th>
				<th>变动原因</th>
				<th>现任警衔</th>
				<th>现任警衔起算日期</th>
				<th>拟授警衔</th>
				<th>新警衔起算日期</th>
				<th>备注</th>
				<th>存档情况</th>
<%--				<shiro:hasPermission name="personnel:personnelPoliceRank:edit"><th id="handleTh">操作</th></shiro:hasPermission>--%>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnnel" varStatus="status">
			<tr>

<%--				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnnel.id}"/></td>--%>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
<%--				<td>${personnnel.unit}</td>--%>
				<td>
						${personnnel.peopleName}

				</td>
				<td>
						${fns:getDictLabel(personnnel.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.birthdayTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnnel.publicSecurityDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${personnnel.xuezhiYear}
				</td>
				<td>
						${personnnel.jobAbbreviation}
				</td>
				<td>
						${fns:getDictLabel(personnnel.jobLevel,"personnel_zwcc","")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<%--${personnnel.changeReason}--%>
					<%--改为拟任变动原因--%>
<%--					<c:choose>--%>
<%--						<c:when test="${calculateType == '1'}">首授</c:when>--%>
<%--						<c:when test="${calculateType == '2'}">首授</c:when>--%>
<%--						<c:when test="${calculateType == '3'}">选升</c:when>--%>
<%--						<c:when test="${calculateType == '4'}">按期晋升</c:when>--%>
<%--						<c:when test="${calculateType == '5'}">按期晋升</c:when>--%>
<%--						<c:when test="${calculateType == '6'}"></c:when>--%>
<%--						<c:when test="${calculateType == '7'}"></c:when>--%>
<%--						<c:otherwise>--%>
							${personnnel.changeReason}
<%--						</c:otherwise>--%>
<%--					</c:choose>--%>
				</td>
				<td>
						${fns:getDictLabel(personnnel.policeRankLevel, "police_rank_level", "")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${fns:getDictLabel(personnnel.policeRankLevel+1, "police_rank_level", "")}
				</td>
				<td>
					<fmt:formatDate value="${personnnel.endDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>

				</td>
				<td>

				</td>


					<%--<td class="handleTd">

                    <shiro:hasPermission name="personnel:personnelPoliceRank:edit">

                            <c:if test="${personnnelBase.createBy.id == fns:getUser().id}">
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?mType=1')">修改</a>
                                <a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&mType=1"
                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
                            </c:if>

                    </shiro:hasPermission>
                    </td>--%>
			</tr>
		</c:forEach>
		<%--<c:forEach items="${page.list}" var="personnnelBase" varStatus="status">
			<tr>

				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnnelBase.id}"/></td>

				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnnelBase.name}

				</td>
				<td>
					${fns:getDictLabel(personnnelBase.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnnelBase.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnnelBase.workDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${personnnelBase.jxyjxznx}
				</td>
				<td>
					${personnnelBase.jobAbbreviation}
				</td>
				<td>
&lt;%&ndash;					${personnnelBase.jobLevel}&ndash;%&gt;
				</td>
				<td>
					${fns:getDictLabel(personnnelBase.status, 'personnel_xctype', '')}
				</td>

				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>

						&lt;%&ndash;<td class="handleTd">

						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">

								<c:if test="${personnnelBase.createBy.id == fns:getUser().id}">
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&mType=1"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
								</c:if>

						</shiro:hasPermission>
						</td>&ndash;%&gt;
			</tr>
		</c:forEach>--%>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
