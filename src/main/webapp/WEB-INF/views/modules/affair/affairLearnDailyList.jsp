<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单位政治理论学习</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLearnDaily/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLearnDaily/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairLearnDaily/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLearnDaily/");
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
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLearnDaily", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLearnDaily"}});
			});
		});

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairLearnDaily/formDetail?id="+id;
            top.$.jBox.open(url, "单位政治理论学习详情",1000,400,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
        //添加，修改弹窗
        function openDialog(url) {
            top.$.jBox.open("iframe:"+url, "单位政治理论学习",1200,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }



	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<shiro:hasPermission name="affair:affairLearnDaily:view">
		<li class="active" >
			<a href="${ctx}/affair/affairLearnDaily/">单位政治理论学习</a>
		</li>
	</shiro:hasPermission>
	<shiro:hasPermission name="affair:affairLearnPower:view">
		<li>
			<a href="${ctx}/affair/affairLearnPower/">学习强国</a>
		</li>
	</shiro:hasPermission>
	<shiro:hasPermission name="affair:affairNetworkCollege:view">
		<li>
			<a href="${ctx}/affair/affairNetworkCollege/">中国干部网络学院</a>
		</li>
	</shiro:hasPermission>
</ul>
	<form:form id="searchForm" modelAttribute="affairLearnDaily" action="${ctx}/affair/affairLearnDaily/" method="post" class="breadcrumb form-search">

        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="fileName" name="fileName" type="hidden" value="单位政治理论学习.xlsx"/>
		<ul class="ul-form">
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairLearnDaily.unitId}" labelName="unit" labelValue="${affairLearnDaily.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairLearnDaily.unitId}" labelName="unit" labelValue="${affairLearnDaily.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<li><label>学习时间：</label>
				<input name="enterDateStart" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLearnDaily.enterDateStart}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="enterDateEnd" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairLearnDaily.enterDateEnd}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li>
                <label>学习地点：</label>
                <form:input path="site" htmlEscape="false" class="input-medium"/>
            </li>
            <li>
                <label>主持人：</label>
                <form:input path="compere" htmlEscape="false" class="input-medium"/>
            </li>
			<li>
                <label>学习内容：</label>
                <form:input path="content" htmlEscape="false" class="input-medium"/>
            </li>


		</ul>
			<ul class="ul-form2">
				<li class="btns">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</li>
				<shiro:hasPermission name="affair:affairLearnDaily:edit">
					<li class="btns">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairLearnDaily/form?id=${affairLearnDaily.id}')"/></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairLearnDaily:edit">
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLearnDaily/deleteByIds','checkAll','myCheckBox')"/></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="affair:affairLearnDaily:edit">
					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLearnDaily'"/></li>

				<li class="clearfix"></li>
			</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>单位</th>
				<th>时间</th>
				<th>学习地点</th>
				<th>主持人</th>
				<th>参加人员</th>
				<th>人数</th>
				<th>学习内容</th>
				<%--<th>学习形式</th>
				<th>中心发言人</th>
				<th>补充发言人</th>
				<th>记录人</th>
				<th>实到人数</th>--%>
				<shiro:hasPermission name="affair:affairLearnDaily:edit">
					<th class="handleTd">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLearnDaily" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLearnDaily.id}"/>
				</td>
				<td>
						${status.index+1}
				</td>
				<td>
						${affairLearnDaily.unit}
				</td>
				<td>
					<fmt:formatDate value="${affairLearnDaily.learnTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						${affairLearnDaily.site}
				</td>
				<td>
						${affairLearnDaily.compere}
				</td>
				<td>
						${affairLearnDaily.participant}
				</td>
				<td>
						${affairLearnDaily.shouldBeTo}
				</td>
				<td>
						${affairLearnDaily.content}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairLearnDaily.id}')">查看</a>
					<shiro:hasPermission name="affair:affairLearnDaily:edit">
                        <a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairLearnDaily/form?id=${affairLearnDaily.id}')">修改</a>
                        <a href="${ctx}/affair/affairLearnDaily/delete?id=${affairLearnDaily.id}" onclick="return confirmx('确认要删除该单位政治理论学习吗？', this.href)">删除</a>
                    </shiro:hasPermission>
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>