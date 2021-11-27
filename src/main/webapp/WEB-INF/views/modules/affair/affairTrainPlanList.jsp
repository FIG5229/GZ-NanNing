<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>培训计划报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {


            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairTrainPlan/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairTrainPlan/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairTrainPlan/export?flag=true")
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairTrainPlan/");
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
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTrainPlan", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTrainPlan"}});
            });
		});


        //添加，修改弹窗
        function openDialog(url) {
            top.$.jBox.open("iframe:"+url, " ",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairTrainPlan"}
            });
        }

        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairTrainPlan/formDetail?id="+id;
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
	<form:form id="searchForm" modelAttribute="affairTrainPlan" action="${ctx}/affair/affairTrainPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="fileName" name="fileName" type="hidden" value="培训计划报表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>培训年度：</label>
				<input name="year" path="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairTrainPlan.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
            <li><label>填报机构：</label>
                <sys:treeselect id="organ" name="organId" value="${affairTrainPlan.organId}" labelName="organ" labelValue="${affairTrainPlan.organ}"
                                title="填报机构" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
            </li>
        </ul>
        <ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
            <shiro:hasPermission name="affair:affairTrainPlan:edit">
                <li class="btns">
                    <input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairTrainPlan/form?id=${affairTrainPlan.id}')"/>
                </li>
                <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
                <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                        onclick="deleteByIds('${ctx}/affair/affairTrainPlan/deleteByIds','checkAll','myCheckBox')"/></li>
            </shiro:hasPermission>
            <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
            <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
            <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTrainPlan'"/></li>
            <li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >南宁铁路公安处培训计划报表</td>
			</tr>
		</table>
		<table  class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
                <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
                <th>序号</th>
				<th>单位</th>
				<th>培训名称</th>
				<th>培训目的</th>
				<th>培训对象</th>
				<th>培训内容</th>
				<th>培训时间</th>
				<th>天数</th>
				<th>培训地点</th>
				<th>年度</th>
				<th>参训人数</th>
				<th>培训费</th>
				<th>师资费</th>
				<th>列支渠道</th>
				<th>填报机构</th>
				<th>实施状态</th>
				<shiro:hasPermission name="affair:affairTrainPlan:edit"><th class="handleTd">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTrainPlan" varStatus="status">
			<tr>
				<td class="checkTd">
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTrainPlan.id}"/>
				</td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td>
					${affairTrainPlan.unit}
				</td>
				<td>
					${affairTrainPlan.name}
				</td>
				<td>
					${affairTrainPlan.goal}
				</td>
				<td>
					${affairTrainPlan.target}
				</td>
				<td>
					${affairTrainPlan.content}
				</td>
				<td>
					<fmt:formatDate value="${affairTrainPlan.time}" pattern="yyyy-MM"/>
				</td>
				<td>
					${affairTrainPlan.day}
				</td>
				<td>
					${affairTrainPlan.place}
				</td>
				<td>
					${affairTrainPlan.year}
				</td>
				<td>
					${affairTrainPlan.number}
				</td>
				<td>
					${affairTrainPlan.trainExpense}
				</td>
				<td>
					${affairTrainPlan.teacherExpense}
				</td>
				<td>
					${affairTrainPlan.trench}
				</td>
				<td>
					${affairTrainPlan.organ}
				</td>
				<td>
					${affairTrainPlan.state}
				</td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairTrainPlan.id}')">查看</a>
					<shiro:hasPermission name="affair:affairTrainPlan:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairTrainPlan/form?id=${affairTrainPlan.id}')">修改</a>
						<a href="${ctx}/affair/affairTrainPlan/delete?id=${affairTrainPlan.id}" onclick="return confirmx('确认要删除该培训计划报表吗？', this.href)">删除</a>
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