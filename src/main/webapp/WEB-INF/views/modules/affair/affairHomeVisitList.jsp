<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>家访管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

            $("#btnPrint").click(function(){
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

            $("#btnExport").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairHomeVisit/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairHomeVisit/list");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairHomeVisit/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairHomeVisit/list");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
                }
            );

            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairHomeVisit", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairHomeVisit/list"}});
            });

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }

        function openAddForm(url) {
            top.$.jBox.open("iframe:"+url, "家访添加",800,600,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }


        function openEditDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairHomeVisit/form?id="+id,"家访编辑",800,600,{
                buttons:{"关闭":true},
                loaded:function () {
                    $(".jbox-content",top.document).css("overflow-y","hidden");
                }
            });
        }
        function openDetailDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairHomeVisit/formDetail?id="+id,"家访明细",800,600,{
                buttons:{"关闭":true},
                loaded:function () {
                    $(".jbox-content",top.document).css("overflow-y","hidden");
                },closed:function () {self.location.href="${ctx}/affair/affairHomeVisit"

                }
            });
        }
        if("success"=="${saveResult}"){
            parent.$.jBox.close();
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/affair/affairTalkHeart/">谈心</a></li>
		<li class="active"><a href="${ctx}/affair/affairHomeVisit/">家访</a></li>
		<%--<shiro:hasPermission name="affair:affairHomeVisit:edit"><li><a href="${ctx}/affair/affairHomeVisit/form">心谈家访添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairHomeVisit" action="${ctx}/affair/affairHomeVisit/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="3.2家访.xlsx"/>

		<ul class="ul-form">
			<li><label>时间：</label>
				<input name="beginTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairHomeVisit.beginTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${affairHomeVisit.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairHomeVisit.unitId}" labelName="unit" labelValue="${affairHomeVisit.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairHomeVisit.unitId}" labelName="unit" labelValue="${affairHomeVisit.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairHomeVisit.unitId}" labelName="unit" labelValue="${affairHomeVisit.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label>家访人：</label>
				<form:input path="homeVisitors" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>家访方式：</label>
				<form:select path="mode" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talk_visits_mode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>家访对象：</label>
				<form:input path="visitObject" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairHomeVisit.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>月度：</label>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairHomeVisit.month}"
					   onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li><label>评定类别：</label>
				<form:select path="evaluate" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('talk_visits_abnormal')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairHomeVisit:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openAddForm('${ctx}/affair/affairHomeVisit/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairHomeVisit/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="btnPrint" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairHomeVisit'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>时间</th>
				<th>单位</th>
				<th>方式</th>
				<th>家访人</th>
				<th>家访对象</th>
				<th>简要内容</th>
				<th>家访结果</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairHomeVisit" varStatus="status">
			<tr
					<%--<c:choose>
						<c:when test="${affairHomeVisit.evaluate == '3'}">
							style="color: #4e8bff"
						</c:when>
						<c:when test="${affairHomeVisit.evaluate == '2'}">
							style="color: #d5bb38"
						</c:when>
						<c:when test="${affairHomeVisit.evaluate == '1'}">
							style="color: #d53732"
						</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>--%>
			>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairHomeVisit.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					<fmt:formatDate value="${affairHomeVisit.time}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${affairHomeVisit.unit}
				</td>
				<td>
					${fns:getDictLabel(affairHomeVisit.mode, 'talk_visits_mode', '')}
				</td>
				<td>
					${affairHomeVisit.homeVisitors}
				</td>
				<td>
					${affairHomeVisit.visitObject}
				</td>
				<td>
					${affairHomeVisit.content}
				</td>
				<td>
					${fns:getDictLabel(affairHomeVisit.evaluate, "talk_visits_abnormal", "")}
				</td>
				<td class="handleTd">
    				<a href="javascript:;" onclick="openDetailDialog('${affairHomeVisit.id}')">查看</a>
					<shiro:hasPermission name="affair:affairHomeVisit:edit">
						<a href="javascript:;" onclick="openEditDialog('${affairHomeVisit.id}')">修改</a>
						<a href="${ctx}/affair/affairHomeVisit/delete?id=${affairHomeVisit.id}" onclick="return confirmx('确认要删除该心谈家访吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>