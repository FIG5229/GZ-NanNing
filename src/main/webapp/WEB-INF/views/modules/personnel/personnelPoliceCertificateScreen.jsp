<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>人民警察证信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/makeCertificate");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/makeCertificate?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/");
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#checkTh').css('display', 'table-cell');
						$('.checkTd').css('display', 'table-cell');
					}
				});
			});
		});
		/*function excelImp(url){
			top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelPoliceCertificate", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
				bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href=url}});
		}*/
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url,rUrl) {
			top.$.jBox.open("iframe:"+url, "人民警察证信息管理",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href=rUrl}
			});
		}
		//详情弹窗
		function openDetailDialog(idNumber) {
			var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/newFormDetail?idNumber="+idNumber;
			top.$.jBox.open(url, "人民警察证信息",800,500,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};

		function openScreenDialog() {

			$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/screen");
			$("#searchForm").submit();
			$("#searchForm").attr("action","${ctx}/personnel/personnelPoliceCertificate/");

		}

		function openBackFillDialog(){
			var status=$('#status').val();
			var startEndDate=$('#startEndDate').val();
			var endEndDate=$('#endEndDate').val();
			var url = "iframe:${ctx}/personnel/personnelPoliceCertificate/backFillView?status="+status+"&startEndDate="+startEndDate
			+"&endEndDate="+endEndDate;
			top.$.jBox.open(url, "人民警察证信息一键回填时间",800,600,{
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


			<form:form id="searchForm" modelAttribute="personnelPoliceCertificate" action="${ctx}/personnel/personnelPoliceCertificate/screen" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="idNumber" name="idNumber" type="hidden" value="${personnelPoliceCertificate.idNumber}"/>
				<input id="type" name="mType" type="hidden" value="${mType}"/>
				<input id="fileName" name="fileName" type="hidden" value="申请办理警察证人员信息.xlsx"/>
				<ul class="ul-form">
					<li><label>警察证状态：</label>
						<form:select path="status" class="input-medium">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('personnel_jcztype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</li>
					<li><label>有效期截止日期：</label>
						<input name="startEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.startEndDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						至
						<input name="endEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
							   value="<fmt:formatDate value="${personnelPoliceCertificate.endEndDate}" pattern="yyyy-MM-dd"/>"
							   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					</li>
				</ul>
				<ul class="ul-form2">
					<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
					<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
<%--						<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?mType=1','${ctx}/personnel/personnelPoliceCertificate?mType=1')"/></li>--%>
<%--						<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelPoliceCertificate/deleteByIds','checkAll','myCheckBox')"/></li>--%>
<%--						<li class="btns"><input  class="btn btn-primary" type="button" value="导入" onclick="excelImp('${ctx}/personnel/personnelPoliceCertificate?mType=1')"/></li>--%>
					</shiro:hasPermission>
<%--					<li class="btns"><input id="zz" class="btn btn-primary" type="button" value="制证"/></li>--%>
<%--					<li class="btns"><input id="screen" class="btn btn-primary" type="button" value="筛选"--%>
<%--											onclick="openScreenDialog()"/></li>--%>
					<li class="btns"><input id="backFill" class="btn btn-primary" type="button" value="一键回填"
											onclick="openBackFillDialog()"/></li>
					<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
					<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
					<li class="btns"><input  class="btn btn-primary" type="button" value="重置"
					onclick="window.location.href='${ctx}/personnel/personnelPoliceCertificate/screen?startEndDate=${personnelPoliceCertificate.startEndDate}&endEndDate=${personnelPoliceCertificate.startEndDate}&state=${personnelPoliceCertificate.status}'"/>
				</ul>
			</form:form>


	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>出生年月</th>
				<th>警号</th>
				<th>血型</th>
				<th>身份证号</th>
				<th>警衔</th>
				<th>性别</th>
				<th>单位</th>
				<th>部门职位</th>
				<th>机构代码</th>

				<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit"><th id="handleTh">操作</th></shiro:hasPermission>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="policeCertificate" varStatus="status">
			<tr>

				<td class="checkTd"><input style='margin-left:12px' type='checkbox' name="myCheckBox"/></td>

				<td>
					${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
						${policeCertificate.name}
				</td>
				<td>
					<fmt:formatDate value="${policeCertificate.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${policeCertificate.policeIdNumber}
				</td>
				<td>
					${policeCertificate.bloodType}
				</td>
				<td>
					${policeCertificate.idNumber}
				</td>
				<td>
						${policeCertificate.rank}
				</td>
				<td>
					${fns:getDictLabel(policeCertificate.sex, 'sex', '')}
				</td>
				<td>
						${policeCertificate.workunitName}
				</td>
				<td>
						${policeCertificate.jobAbbreviation}
				</td>
				<td>
						${policeCertificate.unitCode}
				</td>



						<td class="handleTd">
							<a href="javascript:;" onclick="openDetailDialog('${policeCertificate.idNumber}')">查看</a>
							<shiro:hasPermission name="personnel:personnelPoliceCertificate:edit">
                                <c:if test="${personnelPoliceCertificate.createBy.id == fns:getUser().id}">
                                    <%--<a href="javascript:void(0)"
                                       onclick="openForm('${ctx}/personnel/personnelPoliceCertificate/form?id=${policeCertificate.idNumber}','${ctx}/personnel/personnelPoliceCertificate?mType=1')">修改</a>--%>
                                    <%--<a href="${ctx}/personnel/personnelPoliceCertificate/delete?id=${policeCertificate.idNumber}&mType=1"
                                       onclick="return confirmx('确认要删除该离退信息吗？', this.href)">删除</a>--%>
                                </c:if>
							</shiro:hasPermission>
						</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
<script>
	if ("success" == "${backFillResult}"){
		parent.$.jBox.close();
	}
</script>
</body>
</html>
