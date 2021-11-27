<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警衔信息管理</title>
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

		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceRank&isCover=1","警衔信息导入",800,520,{title:"警衔信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelPoliceRank&isCover=0","警衔信息导入",800,520,{title:"警衔信息导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'cancel') {
					$.jBox.tip('已取消');
				}
				return true;
			};
			$.jBox.confirm("请选择导入模式?", "数据导入确认", submit, { buttons: { '覆盖': 'cover', '插入': 'insert','取消':'cancel'} });
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
        //查看历史记录
		function openHistory(idNumber) {
			var url = "iframe:${ctx}/personnel/personnelPoliceRank/historyform?idNumber="+idNumber;
			top.$.jBox.open(url, "警衔信息",800,500,{
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
<c:choose>
	<c:when test="${mType eq '2'}">
	</c:when>
	<c:when test="${mType eq '1'}">
		<ul class="nav nav-tabs">
<%--			<li><a href="${ctx}/personnel/personnelPoliceRank/?mType=1">警衔信息</a></li>--%>
			<li  class="active"><a href="${ctx}/personnel/personnelPoliceRank/calculate?mType=${mType}">警衔测算</a></li>
			<li><a href="${ctx}/personnel/personnelPoliceRank/analysis?mType=${mType}">统计分析</a></li>
		</ul>
	</c:when>
	<c:otherwise>
	<%--	<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/personnel/personnelPoliceRank/?mType=1">警衔信息</a></li>
		</ul>--%>
	</c:otherwise>
</c:choose>

		<c:choose>
			<c:when test="${mType eq '2'}">
			</c:when>
			<c:when test="${mType eq '1'}">
				<form:form id="searchForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="mType" name="mType" type="hidden" value="${mType}"/>
					<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceRank.idNumber}"/>
					<input id="fileName" name="fileName" type="hidden" value="衔称信息集.xlsx"/>
					<ul class="ul-form">
						<li><label>起算日期：</label>
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
						<li><label>终止日期：</label>
							<input name="endBeginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.endBeginDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="endFinishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.endFinishDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</li>
						<li><label>姓名：</label>
							<form:input path="peopleName" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label>警衔名称：</label>
							<form:input path="name" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label>警衔类型：</label>
							<form:select path="type" class="input-medium">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('personnel_jxtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</li>
						<li><label>授衔令号：</label>
							<form:input path="approvalNumber" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label class="width120">授衔批准单位名称：</label>
							<form:input path="approvalUnitName" htmlEscape="false" class="input-medium"/>
						</li>
						<li class="clearfix"></li>
					</ul>
					<ul class="ul-form2">
						<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">
							<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?mType=1','${ctx}/personnel/personnelPoliceRank?mType=1')"/></li>
							<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceRank/deleteByIds','checkAll','myCheckBox')"/></li>
							<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceRank?mType=1')"/></li>
						</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceRank?mType=1&idNumber=${personnelPoliceRank.idNumber}'"/></li>
						<li class="clearfix"></li>
					</ul>
				</form:form>
			</c:when>
			<c:otherwise>
				<form:form id="searchForm" modelAttribute="personnelPoliceRank" action="${ctx}/personnel/personnelPoliceRank/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="mType" name="mType" type="hidden" value="${mType}"/>
					<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceRank.idNumber}"/>
					<input id="fileName" name="fileName" type="hidden" value="衔称信息集.xlsx"/>
					<ul class="ul-form">
						<li><label>起算日期：</label>
							<input name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.beginDate}" pattern="yyyy-MM-dd"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
							至
							<input name="finishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
								   value="<fmt:formatDate value="${personnelPoliceRank.finishDate}" pattern="yyyy-MM-dd"/>"
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
						</li>
						<li><label>警衔类型：</label>
							<form:select path="type" class="input-medium">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('personnel_jxtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</li>
						<li><label>授衔令号：</label>
							<form:input path="approvalNumber" htmlEscape="false" class="input-medium"/>
						</li>
						<li><label class="width120">授衔批准单位名称：</label>
							<form:input path="approvalUnitName" htmlEscape="false" class="input-medium"/>
						</li>
					</ul>
					<ul class="ul-form2">
						<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">
							<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?idNumber=${personnelPoliceRank.idNumber}','${ctx}/personnel/personnelPoliceRank?idNumber=${personnelPoliceRank.idNumber}')"/></li>
							<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceRank/deleteByIds','checkAll','myCheckBox')"/></li>
							<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelPoliceRank?idNumber=${personnelPoliceRank.idNumber}')"/></li>
						</shiro:hasPermission>
						<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
						<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelPoliceRank?idNumber=${personnelPoliceRank.idNumber}'"/></li>
						<li class="clearfix"></li>
					</ul>
				</form:form>
			</c:otherwise>
		</c:choose>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				</c:if>
				<th>序号</th>
				<c:if test="${mType eq '1'}">
				<%--<th>姓名</th>--%>
				<th>单位</th>
				<th>姓名</th>
					<th>身份证号</th>
				<th>性别</th>
				<th>出生时间</th>
				<th>参加工作时间</th>
				</c:if>
				<%--10-7 问题反馈修改--%>
<%--				<th>学制</th>--%>
				<%--2020年-01-21 王俊宇提出，不应该显示
				<th>应加学制年限</th>
				<th>职务</th>
				<th>职务层次</th>
				<th>任职时间</th>
				--%>
				<th>现任警衔</th>
				<%--和任职时间区分  避免误解--%>
				<th>警衔起算日期</th>
				<c:if test="${mType==null || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelPoliceRank:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceRank" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceRank.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<%--<td>
						${personnelPoliceRank.personName}
				</td>--%>
				<c:if test="${mType eq '1'}">
				<td>
						${personnelPoliceRank.unit}
				</td>
				<td>
<%--					<a href="javascript:" onclick="openHistory('${personnelPoliceRank.idNumber}')">--%>
						${personnelPoliceRank.peopleName}
<%--					</a>--%>
				</td>
					<td>
							${personnelPoliceRank.idNumber}
					</td>
				<td>
						${fns:getDictLabel(personnelPoliceRank.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceRank.birthdayTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceRank.workTime}" pattern="yyyy-MM-dd"/>
				</td>
				</c:if>
<%--				<td>--%>
<%--					${personnelPoliceRank.xuezhi}--%>
<%--				</td>--%>
		<%--
		2020-01-21 王俊宇提出不应该显示
		<td>
					${personnelPoliceRank.xuezhiYear}
				</td>
				<td>
						&lt;%&ndash;职务&ndash;%&gt;
						${personnelPoliceRank.jobAbbreviation}
				</td>
				<td>
						&lt;%&ndash;职务层次&ndash;%&gt;
						${fns:getDictLabel(personnelPoliceRank.jobLevel, 'personnel_zwcc', '')}

				</td>
				<td>
						&lt;%&ndash;任职时间&ndash;%&gt;
					<fmt:formatDate value="${personnelPoliceRank.jobStartDate}" pattern="yyyy-MM-dd"/>
				</td>--%>
				<td>
					${fns:getDictLabel(personnelPoliceRank.policeRankLevel,'police_rank_level' , "")}
<%--					${personnelPoliceRank.name}--%>
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceRank.startDate}" pattern="yyyy-MM-dd"/>
				</td>
				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<td class="handleTd">

						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">

<%--
								<c:if test="${personnelPoliceRank.createBy.id == fns:getUser().id}">
--%>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&mType=1"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--
								</c:if>
--%>

						</shiro:hasPermission>
						</td>
					</c:when>
					<c:otherwise>
						<td class="handleTd">
							<a onclick="openDetailDialog('${personnelPoliceRank.id}')">查看</a>
						<shiro:hasPermission name="personnel:personnelPoliceRank:edit">

<%--
								<c:if test="${personnelPoliceRank.createBy.id == fns:getUser().id}">
--%>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelPoliceRank/form?id=${personnelPoliceRank.id}','${ctx}/personnel/personnelPoliceRank?idNumber=${personnelPoliceRank.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelPoliceRank/delete?id=${personnelPoliceRank.id}&idNumber=${personnelPoliceRank.idNumber}"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--
								</c:if>
--%>
							</shiro:hasPermission>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>
