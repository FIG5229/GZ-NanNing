<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>优抚信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelYoufu/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelYoufu/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/personnel/personnelYoufu/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/personnel/personnelYoufu/");
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
		/*	$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=personnel_personnelYoufu", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/personnel/personnelYoufu"}});
			});*/
		});
		//导入,  增加判断 覆盖/插入
		// 打开导入页面请求路径由 ${ctx}/file/template/download/view?id= xxxx
		// 调整为 ${ctx}/file/template/personnelBasesdownload/view?id= xxxx &isCover=1
		function openImportForm(url,title) {
			//日常联系情况信息导入
			if(title == null || title == ''){
				title = '优抚信息集导入';
			}
			var submit = function (v, h, f) {
				if (v == 'cover') {
					//template/personnelBasesdownload/view  人员基本信息集导入
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelYoufu&isCover=1",title,800,520,{title:title, buttons:{"关闭":true},
						bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",
						closed:function (){
							self.location.href=url;
						}});
				}
				if (v == 'insert') {
					top.$.jBox.open("iframe:${ctx}/file/template/personnelBasesdownload/view?id=personnel_personnelYoufu&isCover=0",title,800,520,{title:title, buttons:{"关闭":true},
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
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "优抚信息添加",800,520,{
				persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/personnel/personnelYoufu"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/personnel/personnelYoufu/formDetail?id="+id;
			top.$.jBox.open(url, "优抚信息",800,500,{
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
		<li class="active"><a href="${ctx}/personnel/personnelYoufu/">优抚信息</a></li>
		<shiro:hasPermission name="personnel:personnelYoufu:edit"><li><a href="${ctx}/personnel/personnelYoufu/form">优抚信息添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="personnelYoufu" action="${ctx}/personnel/personnelYoufu/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="优抚信息集.xlsx"/>
		<ul class="ul-form">
			<li><label>抚恤类别：</label>
				<form:select path="pensionType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('personnel_fuxu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>抚恤日期：</label>
				<input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${personnelYoufu.startDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${personnelYoufu.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<c:if test="${type==null}">
				<shiro:hasPermission name="personnel:personnelYoufu:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/personnel/personnelYoufu/form?id=${personnelYoufu.id}')"></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/personnel/personnelYoufu/deleteByIds','checkAll','myCheckBox')"/>
<%--					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入" onclick="openImportForm('${ctx}/personnel/personnelYoufu')"></li>--%>
				</shiro:hasPermission>
			</c:if>
<%--			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"></li>--%>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelYoufu'"></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<c:if test="${type!=null}">
				<th>姓名</th>
				<th>身份证号</th>
				</c:if>
				<th>抚恤类别</th>
				<th>抚恤日期</th>
				<th>抚恤金额</th>
				<th>抚恤单位名称</th>
				<th>抚恤事由</th>
				<th>备注</th>
				<c:if test="${type==null}">
					<shiro:hasPermission name="personnel:personnelYoufu:edit"><th id="handleTh">操作</th></shiro:hasPermission>
				</c:if>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personnelYoufu" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelYoufu.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<c:if test="${type!=null}">
				<td>
						${personnelPassport.personnelName}
				</td>
				<td>
						${personnelPassport.idNumber}
				</td>
				</c:if>
				<td>
					${fns:getDictLabel(personnelYoufu.pensionType, 'personnel_fuxu', '')}
				</td>
				<td><a onclick="openDetailDialog('${personnelYoufu.id}')">
					<fmt:formatDate value="${personnelYoufu.pensionDate}" pattern="yyyy-MM-dd"/>
				</a></td>
				<td>
					${personnelYoufu.money}
				</td>
				<td>
					${personnelYoufu.pensionUnit}
				</td>
				<td>
					${personnelYoufu.pensionCause}
				</td>
				<td>
					${personnelYoufu.remark}
				</td>
				<c:if test="${type==null}">
					<shiro:hasPermission name="personnel:personnelYoufu:edit"><td class="handleTd">
<%--						<c:if test="${personnelYoufu.createBy.id == fns:getUser().id}">--%>
							<a href="javascript:void(0)" onclick="openForm('${ctx}/personnel/personnelYoufu/form?id=${personnelYoufu.id}')">修改</a>
							<a href="${ctx}/personnel/personnelYoufu/delete?id=${personnelYoufu.id}" onclick="return confirmx('确认要删除该优抚信息吗？', this.href)">删除</a>
<%--						</c:if>--%>
					</td></shiro:hasPermission>
				</c:if>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
