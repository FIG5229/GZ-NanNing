<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专长信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelSkill/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelSkill/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelSkill/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelSkill/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);

//这是打印功能的JS
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
		});
		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelSkill", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "专长信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelSkill/formDetail?id="+id;
			var rurl = "iframe:${ctx}/personnel/personnelSkill?mType=1";
			top.$.jBox.open(url, "警衔信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rurl}
			});
        }
		//详情弹窗
		function openDetailDialogTwo(id,idNumber) {
			var url = "iframe:${ctx}/personnel/personnelSkill/formDetail?id="+id;
			var rurl = "iframe:${ctx}/personnel/personnelSkill?idNumber="+idNumber;
			top.$.jBox.open(url, "警衔信息",800,500,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rurl}
			});
		}

		//导入履历信息-新，增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url) {
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelSkill&isCover=1","高层次人才导入",800,520,{title:"高层次人才导入", buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelSkill&isCover=0","高层次人才导入",800,520,{title:"高层次人才导入", buttons:{"关闭":true},
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

    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/personnel/personnelTalents/">人才库</a></li>
		<li><a href="${ctx}/personnel/personnelSpeciality/?mType=1">专长信息</a></li>
		<li class="active"><a href="${ctx}/personnel/personnelSkill?mType=1">高层次人才</a></li>
	</ul>
	<c:choose>
		<c:when test="${mType eq '2'}"></c:when>
		<c:when test="${mType eq '1'}">
			<form:form id="searchForm" modelAttribute="personnelSkill" action="${ctx}/personnel/personnelSkill/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelSkill.idNumber}"/>
				<input id="fileName" name="fileName" type="hidden" value="高层次人才表.xlsx"/>
				<ul class="ul-form">
					<li><label>姓名：</label>
						<form:input path="personnelName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>特长：</label>
						<form:input path="speciality" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>人才类别：</label>
						<form:select path="category" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('talents_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelSkill:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelSkill/form?mType=1','${ctx}/personnel/personnelSkill?mType=1')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelSkill/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelSkill?mType=1')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelSkill?mType=1&idNumber=${personnelSkill.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="personnelSkill" action="${ctx}/personnel/personnelSkill/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="mType" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="专长信息集.xlsx"/>
				<ul class="ul-form">
					<li><label>姓名：</label>
						<form:input path="personnelName" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>特长：</label>
						<form:input path="speciality" htmlEscape="false" class="input-medium"/>
					</li>
					<li><label>人才类别：</label>
						<form:select path="category" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('talents_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelSkill:edit">
						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelSkill/form?idNumber=${personnelSkill.idNumber}','${ctx}/personnel/personnelSkill?idNumber=${personnelSkill.idNumber}')"/></li>
						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelSkill/deleteByIds','checkAll','myCheckBox')"/></li>
						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelSkill?idNumber=${personnelSkill.idNumber}')"/></li>
					</shiro:hasPermission>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelSkill?idNumber=${personnelSkill.idNumber}'"/></li>
					<li class="clearfix"></li>
				</ul>
			</form:form>
		</c:otherwise>
	</c:choose>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr><c:if test="${mType==null || mType eq '1'}">
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			</c:if>
				<th>序号</th>
				<th>单位</th>
				<th>姓名</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>出生年月</th>
				<th>特长</th>

				<%--<th>人才类别</th>
				<th>人才名称</th>
				<th>享受待遇</th>
				<th>人才用久居留权地名称</th>
				<th>专业类别描述</th>
				<th>人才称号批准日期</th>
				<th>人才称号批准单位</th>
				<th>人才称号批准单位隶属关系</th>
				<th>人才称号批准单位级别</th>--%>

				<%--<th>专长类别</th>
				<th>计算机使用程度</th>
				<th>等级程度</th>
				<th>专长认定单位名称</th>
				<th>专长类别补充</th>
				<th>专长描述</th>--%>

				<c:if test="${mType==null || mType eq '1'}">
				<shiro:hasPermission name="personnel:personnelSkill:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelSkill" varStatus="status">
			<tr>
				<c:if test="${mType==null || mType eq '1'}">
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelSkill.id}"/></td>
				</c:if>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${personnelSkill.unit}
				</td>
				<td>
						${personnelSkill.personnelName}
				</td>

				<td>
						${personnelSkill.idNumber}
				</td>
				<td>
						${fns:getDictLabel(personnelSkill.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelSkill.birthday}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
						${personnelSkill.speciality}
				</td>



				<%--<td>
						${fns:getDictLabel(personnelSkill.titleRatifyUnitRelation, 'talents_category', '')}
				</td>
				<td>
						${personnelSkill.talentsName}
				</td>
				<td>
						${personnelSkill.talentsWelfare}
				</td>
				<td>
						${personnelSkill.resideAddress}
				</td>
				<td>
						${personnelSkill.specialtyCategoryDescribe}
				</td>
				<td>
						<fmt:formatDate value="${personnelSkill.titleRatifyDate}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td>
						${personnelSkill.titleRatifyUnit}
				</td>
				<td>
						${personnelSkill.titleRatifyUnitGrade}
				</td>
				<td>
						${fns:getDictLabel(personnelSkill.titleRatifyUnitRelation, 'unit_rank', '')}

				</td>--%>



				<%--<td><a onclick="openDetailDialog('${personnelSkill.id}')">
					${fns:getDictLabel(personnelSkill.type, 'personnel_zclb', '')}
				</a></td>
				<td>
					${fns:getDictLabel(personnelSkill.computerDegree, 'personnel_jsjlevel', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelSkill.gradeDegree, 'personnel_djcd', '')}
				</td>
				<td>
					${personnelSkill.unitName}
				</td>
				<td>
					${personnelSkill.supplement}
				</td>
				<td>
					${personnelSkill.describe}
				</td>--%>

				<c:choose>
					<c:when test="${mType eq '2'}">

					</c:when>
					<c:when test="${mType eq '1' }">
						<shiro:hasPermission name="personnel:personnelSkill:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelSkill.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)"
									   onclick="openDetailDialog('${personnelSkill.id}')" style="cursor: pointer">查看</a>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelSkill/form?id=${personnelSkill.id}','${ctx}/personnel/personnelSkill?mType=1')">修改</a>
									<a href="${ctx}/personnel/personnelSkill/delete?id=${personnelSkill.id}&mType=1"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td>
						</shiro:hasPermission>
					</c:when>
					<c:otherwise>
						<shiro:hasPermission name="personnel:personnelSkill:edit">
							<td class="handleTd">
<%--								<c:if test="${personnelSkill.createBy.id == fns:getUser().id}">--%>
									<a href="javascript:void(0)" onclick="openDetailDialogTwo('${personnelSkill.id}','${personnelSkill.idNumber}')" style="cursor: pointer">查看</a>
									<a href="javascript:void(0)"
									   onclick="openForm('${ctx}/personnel/personnelSkill/form?id=${personnelSkill.id}','${ctx}/personnel/personnelSkill?idNumber=${personnelSkill.idNumber}')">修改</a>
									<a href="${ctx}/personnel/personnelSkill/delete?id=${personnelSkill.id}&idNumber=${personnelSkill.idNumber}"
									   onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>
<%--								</c:if>--%>
							</td></shiro:hasPermission>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
