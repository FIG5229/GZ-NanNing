<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警学习统计报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//打印
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
			$("#export").click(function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStudyStatistics/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStudyStatistics/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStudyStatistics/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairPoliceStudyStatisticsaffairPoliceStudyStatistics/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_policeStudyStatistics", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceStudyStatistics"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        /*
        * 添加/修改弹窗
        * */
		function openForm(url,type) {
			top.$.jBox.open("iframe:"+url, "民警学习统计报表"+type,1000,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairPoliceStudyStatistics"}
			});
		}

		/*
     * 课程详情
     * */
		function openCourseDetail(alarm) {
			var url = "iframe:${ctx}/affair/affairPoliceStudyStatistics/formDetail?alarm="+alarm;
			top.$.jBox.open(url, "课程详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

	</script>
</head>
<body>
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairPoliceStudyStatistics/">民警学习统计报表列表</a></li>
		<shiro:hasPermission name="affair:affairPoliceStudyStatistics:edit"><li><a href="${ctx}/affair/affairPoliceStudyStatistics/form">民警学习统计报表添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="affairPoliceStudyStatistics" action="${ctx}/affair/affairPoliceStudyStatistics/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="民警学习统计报表.xlsx"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="nickName" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>警号：</label>
				<form:input path="alarm" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属机构：</label>
				<sys:treeselect id="unitId" name="unitId" value="${affairPoliceStudyStatistics.unitId}" labelName="unitName" labelValue="${affairPoliceStudyStatistics.unitName}"
					title="所属机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairPoliceStudyStatistics/form','添加')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairPoliceStudyStatistics/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairPoliceStudyStatistics'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="handleTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>用户名</th>
				<th>姓名</th>
				<th>警号</th>
				<th>所属机构</th>
				<th>学习课程总数</th>
				<th>学习总次数</th>
				<th>学习总时长(小时)</th>
				<th>通过课程数</th>
				<th>课程通过率</th>
				<shiro:hasPermission name="affair:affairPoliceStudyStatistics:edit"><th id="checkTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairPoliceStudyStatistics" varStatus="status">
			<tr>
				<td class="handleTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairPoliceStudyStatistics.id}"/>
				</td>
				<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
				<td>
					${affairPoliceStudyStatistics.nickName}
				</td>
				<td>
					${affairPoliceStudyStatistics.name}
				</td>
				<td>
					${affairPoliceStudyStatistics.alarm}
				</td>
				<td>
					${affairPoliceStudyStatistics.unitName}
				</td>
				<td>
					<a href="javascript:void(0)" onclick="openCourseDetail('${affairPoliceStudyStatistics.alarm}')">
						${affairPoliceStudyStatistics.courseNum}
					</a>
				</td>

				<td>
					${affairPoliceStudyStatistics.studyTotalNumber}
				</td>
				<td>
					${affairPoliceStudyStatistics.studyTotalDate}
				</td>
				<td>
					${affairPoliceStudyStatistics.passCourseNum}
				</td>
				<td>
					${affairPoliceStudyStatistics.coursePassRate}
				</td>
				<shiro:hasPermission name="affair:affairPoliceStudyStatistics:edit">
					<c:choose>
						<c:when test="${'1'==fns:getUser().id || '5'== fns:getUser().office.id || ('5'!= fns:getUser().office.id && affairPoliceStudyStatistics.createOrgId == fns:getUser().office.id)}">
							<td class="checkTd">
								<a href="javascript:void(0)"
								   onclick="openForm('${ctx}/affair/affairPoliceStudyStatistics/form?id=${affairPoliceStudyStatistics.id}','修改')">修改</a>
								<a href="${ctx}/affair/affairPoliceStudyStatistics/delete?id=${affairPoliceStudyStatistics.id}"
								   onclick="return confirmx('确认要删除该民警学习统计报表吗？', this.href)">删除</a>
							</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>