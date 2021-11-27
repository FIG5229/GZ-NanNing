<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医疗保险管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairMedicalInsurance/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairMedicalInsurance/");
							}

							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all','取消':'cancel'} });
					}
			);

			$("#print").click(function(){
				$('#handleTh').css('display', 'none');
				$('#cbTh').css('display', 'none');
				$('.handleTd').css('display', 'none');
				$('.cbTd').css('display', 'none');
				$('#headTitle').addClass("table table-striped table-bordered table-condensed");
				$('#headTitle').removeAttr('style');
				$("#contentTable").printThis({
					debug: false,
					importCSS: false,
					importStyle: false,
					printContainer: true,
					loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
					pageTitle: "",
					removeInline: false,
					printDelay: 333,
					header: "",
					formValues: false,
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('#cbTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('.cbTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});

			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairMedicalInsurance", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairMedicalInsurance"}});
			});


		});
		function page(n,s){
			/*$("#pageNo").val(n);
			$("#pageSize").val(s);*/
			$("#searchForm").submit();
			return false;
		}

		//添加，修改弹窗
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "医疗保险",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMedicalInsurance"}
			});
		}

		//生成弹窗
		function shengCheng() {
			var url = "iframe:${ctx}/affair/affairMedicalInsurance/shengCheng";
			top.$.jBox.open(url, "医疗保险",1200,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairMedicalInsurance"}
			});
		}

		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairMedicalInsurance/formDetail?id="+id;
			top.$.jBox.open(url, "医疗保险",800,500,{
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
		<li class="active"><a href="${ctx}/affair/affairMedicalInsurance/">医疗保险</a></li>
		<li><a href="${ctx}/affair/affairMedicareChange/">医保关系变动</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairMedicalInsurance" action="${ctx}/affair/affairMedicalInsurance/" method="post" class="breadcrumb form-search">
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<input id="fileName" name="fileName" type="hidden" value="医疗保险表.xlsx"/>
		<ul class="ul-form">
			<li><label>时间：</label>
<%--				<form:input path="timeYear" htmlEscape="false" class="input-medium"/>--%>
				<input name="timeYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairMedicalInsurance.timeYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
		</ul>
		<ul class="ul-form2">

			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</li>
			<shiro:hasPermission name="affair:affairMedicalInsurance:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="生成" onclick="shengCheng()"/>
				</li>
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairMedicalInsurance/form?mType=1','${ctx}/affair/affairMedicalInsurance?mType=1')"/>
				</li>
				<li class="btns">
					<input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairMedicalInsurance/deleteByIds','checkAll','myCheckBox')"/>
				</li>
				<li class="btns">
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
				</li>
			</shiro:hasPermission>
			<li class="btns">
				<input id="export" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="btns">
				<input id="print" class="btn btn-primary" type="button" value="打印"/>
			</li>
			<li class="btns">
				<input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairMedicalInsurance'"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >医疗保险表</td>
			</tr>
		</table>
		<table class="table table-striped table-bordered table-condensed">

		<thead>
			<tr>
				<th id="cbTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th style="text-align: center">序号</th>
				<th style="text-align: center">时间</th>
				<th style="text-align: center">单位</th>
				<th style="text-align: center">姓名</th>
                <th style="text-align: center">性别</th>
				<th style="text-align: center">出生年月</th>
				<th style="text-align: center">年龄</th>
				<th style="text-align: center">身份证号</th>
				<th style="text-align: center">缴费基数</th>
				<th style="text-align: center">个人缴费比例</th>
				<th style="text-align: center">月个人缴费</th>
				<th style="text-align: center">单位缴费比例</th>
				<th style="text-align: center">月单位缴费</th>
				<th style="text-align: center">月个账划入比例</th>
				<th style="text-align: center">月个账划入</th>
				<th style="text-align: center">补充资金月个账划入比例</th>
				<th style="text-align: center">补充资金月个账划入</th>
				<th style="text-align: center">全区公务员平均月工资</th>
				<th style="text-align: center">年度补助比例</th>
				<th style="text-align: center">年度个账划入</th>
				<shiro:hasPermission name="affair:affairMedicalInsurance:edit"><th style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairMedicalInsurance" varStatus="status">
			<tr>
				<td class="cbTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairMedicalInsurance.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.timeYear}
				</a></td>
				<td style="text-align: center">
						${affairMedicalInsurance.unit}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.name}
				</td>
                <td style="text-align: center">
								${fns:getDictLabel(affairMedicalInsurance.sex, 'sex', '')}
                </td>
				<td style="text-align: center">
					<fmt:formatDate value="${affairMedicalInsurance.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.age}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.idNumber}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.cardinalNumber}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.individualProportion}%
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.individualPayment}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.unitProportion}%
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.unitPayment}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.monthAccount}%
				</td>
				<td style="text-align: center">
						${affairMedicalInsurance.monthAccountDelimit}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.addition}%
				</td>
				<td style="text-align: center">
						${affairMedicalInsurance.additionFunds}
				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.averageSalary}
				</td>
				<td style="text-align: center">
					<c:choose>
						<c:when test="${affairMedicalInsurance.annualProportion != '' and affairMedicalInsurance.annualProportion != null}">
							${affairMedicalInsurance.annualProportion}%
						</c:when>
						<c:when test="${affairMedicalInsurance.annualProportionOver != '' and affairMedicalInsurance.annualProportionOver != null}">
							${affairMedicalInsurance.annualProportionOver}%
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>

				</td>
				<td style="text-align: center">
					${affairMedicalInsurance.annualAccountDelimit}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairMedicalInsurance.id}')">查看</a>
					<shiro:hasPermission name="affair:affairMedicalInsurance:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairMedicalInsurance/form?id=${affairMedicalInsurance.id}')">修改</a>
						<a href="${ctx}/affair/affairMedicalInsurance/delete?id=${affairMedicalInsurance.id}" onclick="return confirmx('确认要删除该养老保险吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
<%--	<div class="pagination">${page}</div>--%>
</body>
</html>