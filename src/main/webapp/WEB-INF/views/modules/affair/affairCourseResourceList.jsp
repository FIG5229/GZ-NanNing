<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程资源管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairCourseResource/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCourseResource/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairCourseResource/export?flag=true")
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairCourseResource/");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');

					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairCourseResource", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairCourseResource"}});
			})
		});

		//添加，修改弹窗
		function openDialog(url,type) {
			top.$.jBox.open("iframe:"+url, "type" == type,800,600 ,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCourseResource"}
			});
		}

		//课件上传
		function openSwfDialog(url) {
			top.$.jBox.open("iframe:"+url,"",800,600 ,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairCourseResource"}
			});
		}

		//常规设置
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairCourseResource/formDetail?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//教学设计
		function openJX(id) {
			var url = "iframe:${ctx}/affair/affairCourseResource/JXForm?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//教学设计
		function openXK(id) {
			var url = "iframe:${ctx}/affair/affairCourseResource/XKForm?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}

		//辅导教官
		function openFD(id) {
			var url = "iframe:${ctx}/affair/affairCourseTeacher/list?id="+id;
			top.$.jBox.open(url, "",900,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//课程研发
		function openKC(id) {
			var url = "iframe:${ctx}/affair/affairCourseResource/KCForm?id="+id;
			top.$.jBox.open(url, "",900,500,{
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
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="affairCourseResource" action="${ctx}/affair/affairCourseResource/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="课程资源表.xlsx"/>

		<ul class="ul-form">
			<li><label>课程编码：</label>
				<form:input path="code" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程标签：</label>
				<form:input path="label" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>课程类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('swf_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairCourseResource:edit">
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加在线课程" onclick="openDialog('${ctx}/affair/affairCourseResource/form?type=1 && id=${affairCourseResource.id}')"/>
				</li>
				<li class="btns">
					<input  class="btn btn-primary"  type="button" value="添加面授课程" onclick="openDialog('${ctx}/affair/affairCourseResource/form?type=2 && id=${affairCourseResource.id}')"/>
				</li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
										onclick="deleteByIds('${ctx}/affair/affairCourseResource/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairCourseResource'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处课程资源报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>课程名称</th>
				<th>课程分类</th>
				<th>课程类型</th>
				<th>课程标签</th>
				<th>学时</th>
				<th>是否公开课</th>
				<th>创建单位</th>
				<th>发布状态</th>
				<shiro:hasPermission name="affair:affairCourseResource:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairCourseResource" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairCourseResource.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairCourseResource.name}

				</td>
				<td>
						${affairCourseResource.classify}

				</td>
				<td>
					${fns:getDictLabel(affairCourseResource.type, 'swf_type', '')}
				</td>
				<td>
					${affairCourseResource.label}
				</td>
				<td>
					${affairCourseResource.time}
				</td>
				<td>
					${fns:getDictLabel(affairCourseResource.open, 'open_state', '')}
				</td>
				<td>
					${affairCourseResource.unit}
				</td>
				<td>
					${fns:getDictLabel(affairCourseResource.state, 'release_state', '')}
				</td>
				<td class="handleTd">
					<shiro:hasPermission name="affair:affairCourseResource:edit">
						<a href="javascript:void(0)" onclick="openDetailDialog('${affairCourseResource.id}')">常规设置</a>
						<a href="javascript:void(0)" onclick="openJX('${affairCourseResource.id}')">教学设计</a>
						<a href="javascript:void(0)" onclick="openXK('${affairCourseResource.id}')">选课设置</a>
						<a href="javascript:void(0)" onclick="openFD('${affairCourseResource.id}')">资源设置</a>
						<a href="javascript:void(0)" onclick="openKC('${affairCourseResource.id}')">课程研发</a>

						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairCourseResource/form?type=${affairCourseResource.type}&id=${affairCourseResource.id}')">修改</a>
						<a href="${ctx}/affair/affairCourseResource/delete?id=${affairCourseResource.id}" onclick="return confirmx('确认要删除该课程资源吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>