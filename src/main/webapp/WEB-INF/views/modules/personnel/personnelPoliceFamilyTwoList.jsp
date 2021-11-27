<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>民警家庭管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamilyTwo/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamilyTwo/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamilyTwo/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceFamilyTwo/");
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

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

		function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceFamily", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}

		//添加修改弹窗
		function openAddEditDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceFamilyTwo/form?id="+id;
			if (id == null || id == undefined){
				url = "iframe:${ctx}/personnel/personnelPoliceFamilyTwo/form?add=add";
			}
			top.$.jBox.open(url, "民警家庭数据",1000,600,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){
					self.location.href="${ctx}/personnel/personnelPoliceFamilyTwo/list";
				}
			});
		};

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelPoliceFamilyTwo/formDetail?id="+id;
			top.$.jBox.open(url, "民警家庭",1000,600,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/personnel/personnelPoliceFamilyTwo/shenHeDialog?id="+id,"民警家庭",800,420,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/personnel/personnelPoliceFamilyTwo/list/?repage";
				}
			});
		};

		function submitByIds() {
			if(null == $("#unitCheckId").val() || "" ==  $("#unitCheckId").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val())
				ids.push($(this).val());
			});
			if (ids.length > 0) {
				var idsStr = ids.join(",");
				$("#idsValue").val(idsStr);
				$("#searchForm2").submit();
			} else {
				$.jBox.tip('请先选择要提交的内容');
			}
		};

        function submitByIds1() {
            if(null == $("#chuCheckManName").val() || "" ==  $("#chuCheckManName").val()){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                console.log($(this).val());
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                var idsStr = ids.join(",");
                $("#idsValue").val(idsStr);
                $("#searchForm2").submit();
            } else {
                $.jBox.tip('请先选择要提交的内容');
            }
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/personnel/personnelPoliceFamilyTwo/">民警家庭</a></li>
<%--		<shiro:hasPermission name="personnel:PersonnelPoliceFamily:edit"><li><a href="${ctx}/personnel/PersonnelPoliceFamily/form">民警家庭添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="personnelPoliceFamilyTwo" action="${ctx}/personnel/personnelPoliceFamilyTwo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="民警家庭.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>出生日期：</label>
				<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${personnelPoliceFamilyTwo.birthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>政治面貌：</label>
				<form:select path="politicsFace" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddEditDialog()"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/personnel/personnelPoliceFamilyTwo/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="导入"
										onclick="excelImp('${ctx}/personnel/personnelPoliceFamilyTwo')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置"
									 onclick="window.location.href='${ctx}/personnel/personnelPoliceFamilyTwo'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>身份证号</th>
				<th>姓名</th>
				<th>称谓</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>政治面貌</th>
				<th>现状</th>
				<th>工作单位名称及职务</th>
				<th>工作单位所在政区</th>
				<th>审核状态</th>
				<shiro:hasPermission name="personnel:personnelPoliceFamily:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelPoliceFamilyTwo" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelPoliceFamilyTwo.id}"/></td>
				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${personnelPoliceFamilyTwo.idNumber}
				</td>
				<td>
					${personnelPoliceFamilyTwo.name}
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamilyTwo.relationship, 'family_appellation', '')}
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamilyTwo.sex, 'sex', '')}
				</td>
				<td>
					<fmt:formatDate value="${personnelPoliceFamilyTwo.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(personnelPoliceFamilyTwo.politicsFace, 'political_status', '')}
				</td>
				<td>
					${personnelPoliceFamilyTwo.status}
				</td>
				<td>
					${personnelPoliceFamilyTwo.unitNameJob}
				</td>
				<td>
					${personnelPoliceFamilyTwo.unitArea}
				</td>
                <td>
                    ${fns:getDictLabel(personnelPoliceFamilyTwo.checkType, 'personnel_police', '')}
				</td>

				<td class="handleTd">
						<a href="javascript:;" onclick="openDetailDialog('${personnelPoliceFamilyTwo.id}')">查看</a>
					<shiro:hasPermission name="personnel:personnelPoliceFamily:edit">
						<a href="javascript:;" onclick="openAddEditDialog('${personnelPoliceFamilyTwo.id}')">修改</a>
						<a href="${ctx}/personnel/personnelPoliceFamilyTwo/delete?id=${personnelPoliceFamilyTwo	.id}" onclick="return confirmx('确认要删除该民警家庭吗？', this.href)">删除</a>
					</shiro:hasPermission>

                    <c:if test="${personnelPoliceFamilyTwo.unitCheckId eq fns:getUser().id}">
                        <c:if test="${'2' eq personnelPoliceFamilyTwo.checkType || '6' eq personnelPoliceFamilyTwo.checkType}">
                                <a onclick="openDialog('${personnelPoliceFamilyTwo.id}')">审核</a>
                         </c:if>
                    </c:if>

                    <c:if test="${personnelPoliceFamilyTwo.juChuCheckId eq fns:getUser().id}">
                        <c:if test="${'3' eq personnelPoliceFamilyTwo.checkType}">
                            <a onclick="openDialog('${personnelPoliceFamilyTwo.id}')">审核</a>
                        </c:if>
                    </c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<form:form id="searchForm2" modelAttribute="personnelPoliceFamilyTwo" action="${ctx}/personnel/personnelPoliceFamilyTwo/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择评定单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="unitCheckId" path="unitCheckId"  class="input-xlarge required">
				<c:choose>
					<c:when test="${userUnit != ''}">
						<form:option value="${userUnitId}" label="${name}"/>
					</c:when>
				</c:choose>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>

        </ul>
	</form:form>
</body>
</html>
