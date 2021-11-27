<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统计分析</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
<%--		<li><a href="${ctx}/personnel/personnelPoliceRank/?mType=${mType}">警衔信息</a></li>--%>
		<li><a href="${ctx}/personnel/personnelPoliceRank/calculateBeta?mType=${mType}">警衔测算</a></li>
		<li class="active"><a href="${ctx}/personnel/personnelPoliceRank/analysis?mType=${mType}">统计分析</a></li>
	</ul>
				<form:form id="searchForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/analysis" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
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
						</li>--%>
						<%--<li><label>授衔日期：</label>
							<input name="sxStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.sxStartDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="sxEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.sxEndDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
						<li><label>终止日期：</label>
							<input name="endBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.endBeginDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="endFinishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.endFinishDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
						<li><label>警衔名称：</label>
							<form:input path="name" htmlEscape="false" class="input-medium"/>
						</li>--%>
						<li><label>统计类型：</label>
							<form:select path="analysisType" class="input-medium">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('rank_analysis_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</li>
						<li><label>时间：</label>
							<input name="years" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="${personnelPoliceRank.years}"
								   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
						</li>
						<%--<li><label>授衔令号：</label>
							<form:input path="approvalNumber" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label class="width120">授衔批准单位名称：</label>
							<form:input path="approvalUnitName" htmlEscape="false" class="input-medium"/>
						</li>--%>
						<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
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
	<c:choose>
		<c:when test="${analysisType == '1'}">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>行政警衔</th>
					<th>一级监督</th>
					<th>二级监督</th>
					<th>三级监督</th>
					<th>一级警督</th>
					<th>二级警督</th>
					<th>三级警督</th>
					<th>一级警司</th>
					<th>二级警司</th>
					<th>三级警司</th>
					<th>一级警员</th>
					<th>二级警员</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${administrationChangeList}" var="list" varStatus="status">
					<tr>

						<td>
								${fns:getDictLabel(list.change_reason,"dict_change_reason","")}
						</td>
						<td>
								${list.commissionerfirst}
						</td>
						<td>
								${list.commissionersecond}
						</td>
						<td>
								${list.commissionerthird}
						</td>
						<td>
								${list.inspectorfirst}
						</td>
						<td>
								${list.inspectorsecond}
						</td>
						<td>
								${list.inspectorthird}
						</td>
						<td>
								${list.superintendentfirst}
						</td>
						<td>
								${list.superintendentsecond}
						</td>
						<td>
								${list.superintendentthird}
						</td>
						<td>
								${list.policeofficerfirst}
						</td>
						<td>
								${list.policeofficersecond}
						</td>

					</tr>
				</c:forEach>
				</tbody>
			</table>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>技术警衔</th>
					<th>一级监督</th>
					<th>二级监督</th>
					<th>三级监督</th>
					<th>一级警督</th>
					<th>二级警督</th>
					<th>三级警督</th>
					<th>一级警司</th>
					<th>二级警司</th>
					<th>三级警司</th>
					<th>一级警员</th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${technologyChangeList}" var="list" varStatus="status">
					<tr>

						<td>
								${fns:getDictLabel(list.change_reason,"dict_change_reason","")}
						</td>
						<td>
								${list.commissionerfirst}
						</td>
						<td>
								${list.commissionersecond}
						</td>
						<td>
								${list.commissionerthird}
						</td>
						<td>
								${list.inspectorfirst}
						</td>
						<td>
								${list.inspectorsecond}
						</td>
						<td>
								${list.inspectorthird}
						</td>
						<td>
								${list.superintendentfirst}
						</td>
						<td>
								${list.superintendentsecond}
						</td>
						<td>
								${list.superintendentthird}
						</td>
						<td>
								${list.policeofficerfirst}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:when test="${analysisType == '2'}">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>变动情况</th>
					<th>一级监督</th>
					<th>二级监督</th>
					<th>三级监督</th>
					<th>一级警督</th>
					<th>二级警督</th>
					<th>三级警督</th>
					<th>一级警司</th>
					<th>二级警司</th>
					<th>三级警司</th>
					<th>一级警员</th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="list" varStatus="status">
					<tr>

						<td>
							${fns:getDictLabel(list.change_reason,"dict_change_reason","")}
						</td>
						<td>
							${list.commissionerfirst}
						</td>
						<td>
								${list.commissionersecond}
						</td>
						<td>
								${list.commissionerthird}
						</td>
						<td>
								${list.inspectorfirst}
						</td>
						<td>
								${list.inspectorsecond}
						</td>
						<td>
								${list.inspectorthird}
						</td>
						<td>
								${list.superintendentfirst}
						</td>
						<td>
								${list.superintendentsecond}
						</td>
						<td>
								${list.superintendentthird}
						</td>
						<td>
								${list.policeofficer}
						</td>

				<%--		<td class="handleTd">

							<shiro:hasPermission name="personnel:personnelPoliceRank:edit">

								<c:if test="${personnelPoliceRank.createBy.id == fns:getUser().id}">
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&mType=1"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
								</c:if>

							</shiro:hasPermission>
						</td>--%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:when test="${analysisType == '3'}">

		</c:when>
		<c:when test="${analysisType == '4'}">

		</c:when>
		<%--晋升警衔名单--%>
		<c:when test="${analysisType == '5'}">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
				<tr>
					<th>序号</th>
					<th>单位</th>
					<th>职务</th>
					<th>职级</th>
					<th>姓名</th>
					<th>性别</th>
					<th>出生年月</th>
					<th>参加工作时间</th>
					<th>任现职级时间</th>
					<th>现任警衔</th>
					<th>现任警衔时间</th>
					<th>拟变动警衔</th>

				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="list" varStatus="status">
					<tr>
						<td>
							${status.index+1}
						</td>
						<td>
								${list.workunit_name}
						</td>
						<td>
								${list.job_fullname}
						</td>
						<td>
								${fns:getDictLabel(list.job_level, "personnel_zwcc", "")}
						</td>
						<td>
								${list.name}
						</td>
						<td>
								${fns:getDictLabel(list.sex, "sex", "")}
						</td>
						<td>
							<fmt:formatDate value="${list.birthday}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${list.work_date}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${list.start_date}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							${fns:getDictLabel(list.police_rank_level,"police_rank_level","")}
						</td>
						<td>
							<fmt:formatDate value="${list.start_date}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
								三级警督
						</td>

							<%--		<td class="handleTd">

                                        <shiro:hasPermission name="personnel:personnelPoliceRank:edit">

                                            <c:if test="${personnelPoliceRank.createBy.id == fns:getUser().id}">
                                                <a href="javascript:void(0)"
                                                   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?mType=1')">修改</a>
                                                <a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&mType=1"
                                                   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
                                            </c:if>

                                        </shiro:hasPermission>
                                    </td>--%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>

		</c:otherwise>
	</c:choose>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>
