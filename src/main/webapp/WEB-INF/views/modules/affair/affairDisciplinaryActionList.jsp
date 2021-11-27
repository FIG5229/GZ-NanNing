<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>纪律处分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {

            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryAction/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryAction/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryAction/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairDisciplinaryAction/");
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
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairDisciplinaryAction", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryAction"}});
			});

			$("[data-toggle='popover']").popover();
			var reason = "${reason}";
			if(reason != null && reason != '' && typeof reason != undefined){
				$("#reason").val(${reason}).trigger("change");
			}
			$("#reason").change(function(){
				$("#nd").val("");
				$("#sd").val("");
				$("#ed").val("");
				$("#qt").val("");
				year();
				//console.log($("#reason").val())
			});
			function year() {
				if ($("#reason").val() == '1') {
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:block");
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '2') {
					$("#rd").attr("style","display:block");
					$("#yr").attr("style","display:none");
					$("#oy").attr("style","display:none")
				}else if($("#reason").val() == '3'){
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:none");
					$("#oy").attr("style","display:block")
				}else {
					$("#rd").attr("style","display:none");
					$("#yr").attr("style","display:block");
					$("#oy").attr("style","display:none")
				}
			}
			year();

			//处分种类改变时触发事件，联动子选项
			$("#disciplinaryType").change(function(){
				showAndHide();
			});
			$("#djSubOption").change(function(){
				dangJi();
			});
			//控制处分种类子选项下拉框的隐藏与显示
			function showAndHide(){
				if($("#disciplinaryType").val() == '1'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#s2id_xzSubOption').css('display', 'inline-block');
					$('#xzSubOption').css('display', 'inline-block');
					$('#s2id_djSubOption').css('display', 'none');
					$('#s2id_rySubOption').css('display', 'none');
					// $('#s2id_dzzSubOption').css('display', 'none');
					//$('#xinxi').css('display', 'inline-block');
				}else if($("#disciplinaryType").val() == '2'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#s2id_xzSubOption').css('display', 'none');
					$('#s2id_djSubOption').css('display', 'inline-block');
					$('#djSubOption').css('display', 'inline-block');
					//$('#xinxi').css('display', 'none');
				}
			}
			function dangJi() {
				if($("#disciplinaryType").val() == '2'){
					$('#s2id_defaultSubOption').css('display', 'none');
					$('#s2id_xzSubOption').css('display', 'none');
					$('#s2id_djSubOption').css('display', 'inline-block');
					$('#djSubOption').css('display', 'inline-block');
					if($("#djSubOption").val() == '1'){
						$('#s2id_zzSubOption').css('display', 'none');
						$('#s2id_xzSubOption').css('display', 'none');
						// $('#s2id_dzzSubOption').css('display', 'none');
						// $('#dzzSubOption').css('display', 'none');
						$('#s2id_rySubOption').css('display', 'inline-block');
						$('#rySubOption').css('display', 'inline-block');
						//$('#xinxi').css('display', 'inline-block');
					}
					/*else if($("#djSubOption").val() == '2'){
						$('#s2id_zzSubOption').css('display', 'none');
						$('#s2id_xzSubOption').css('display', 'none');
						$('#s2id_rySubOption').css('display', 'none');
						$('#rySubOption').css('display', 'none');
						$('#s2id_dzzSubOption').css('display', 'inline-block');
						$('#dzzSubOption').css('display', 'inline-block');
						$('#xinxi').css('display', 'none');
					}*/
				}

			}
			//调用
			showAndHide();
			dangJi();
			//打印
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
		//添加function,用于打开form添加页面弹窗,需要传一个url
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "纪律处分",900,600,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairDisciplinaryAction"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairDisciplinaryAction/formDetail?id="+id;
			top.$.jBox.open(url, "\n" +
					"纪律处分详情",900,600,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		$('#notPass').popover();
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairDisciplinaryAction/">纪律处分</a></li>
		<li><a href="${ctx}/affair/affairDisciplinaryActionDzz/">党组织处理</a></li>
	<%--		<li><a href="${ctx}/affair/affairDisciplinaryAction/affairDisciplinaryActionListSum">统计汇总</a></li>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="affairDisciplinaryAction" action="${ctx}/affair/affairDisciplinaryAction/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <input id="fileName" name="fileName" type="hidden" value="纪律处分表.xlsx"/>
		<ul class="ul-form">
			<li>
				<label>数据范围：</label>
				<select id="reason" style="width: 140px;" name="reason">
					<option value="2">全部</option>
					<option value="3">其他年份</option>
				</select>
			</li>
			<li id="yr"><label>年度：</label>
				<input id="nd" name="year" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="${affairDisciplinaryAction.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li id="rd"><label class="width120">处分决定时间：</label>
				<input id="sd" name="startApprovalDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="<fmt:formatDate value="${affairDisciplinaryAction.startApprovalDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input id="ed" name="endApprovalDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="<fmt:formatDate value="${affairDisciplinaryAction.endApprovalDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li id="oy"><label>其他年份：</label>
				<input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="${affairDisciplinaryAction.otherYear}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-small"/>
			</li>
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairDisciplinaryAction.unitId}" labelName="unit" labelValue="${affairDisciplinaryAction.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label>政治面貌：</label>
				<form:select path="zhengzhiFace" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('political_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>问题性质：</label>
				<form:select path="nature" class="input-small">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wenti')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>处分单位：</label>
				<form:select path="cfUnit" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_cf_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label class="width120">处分期满时间：</label>
				<input name="startInfluencePeriod" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="<fmt:formatDate value="${affairDisciplinaryAction.startInfluencePeriod}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				至
				<input name="endInfluencePeriod" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					   value="<fmt:formatDate value="${affairDisciplinaryAction.endInfluencePeriod}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form">
			<li><label class="width120">处分及处理方式：</label>
				<form:select id="disciplinaryType" path="disciplinaryType" class="input-small required" cssStyle="width: 150px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xzchufen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<%--默认下拉框--%>
				<form:select id="defaultSubOption" path="subOption" class="input-small" cssStyle="width: 150px;">
					<form:option value="" label=""/>
				</form:select>
					<%--行政子选项下拉框--%>
				<form:select id="xzSubOption" path="xzSubOption" class="input-small" cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xz_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<%--党纪选项下拉框--%>
				<form:select id="djSubOption" path="djSubOption" class="input-small " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<%--默认下拉框--%>
				<%--不清楚干什么用的--%>
				<%--<form:select id="zzSubOption" path="zzSubOption" class="input-small" cssStyle="width: 150px;">
					<form:option value="" label=""/>
				</form:select>--%>
					<%--党纪人员子选项下拉框--%>
				<form:select id="rySubOption" path="rySubOption" class="input-small " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<%--党纪党组织子选项下拉框--%>
				<%--<form:select id="dzzSubOption" path="dzzSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
					<form:option value="" label="--"/>
					<form:options items="${fns:getDictList('affair_dj_sub')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
				<%--<form:select path="disciplinaryType" class="input-xlarge " cssStyle="width: 150px;">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xzchufen')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					&lt;%&ndash;默认下拉框&ndash;%&gt;
				<form:select id="defaultSubOption" path="subOption" class="input-xlarge" cssStyle="width: 150px;">
					<form:option value="" label=""/>
				</form:select>
					&lt;%&ndash;行政子选项下拉框&ndash;%&gt;
				<form:select id="xzSubOption" path="xzSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_xz_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					&lt;%&ndash;党纪选项下拉框&ndash;%&gt;
				<form:select id="djSubOption" path="djSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					&lt;%&ndash;默认下拉框&ndash;%&gt;
				<form:select id="zzSubOption" path="zzSubOption" class="input-xlarge" cssStyle="width: 150px;">
					<form:option value="" label=""/>
				</form:select>
					&lt;%&ndash;党纪人员子选项下拉框&ndash;%&gt;
				<form:select id="rySubOption" path="rySubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_sub_option')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					&lt;%&ndash;党纪党组织子选项下拉框&ndash;%&gt;
				<form:select id="dzzSubOption" path="dzzSubOption" class="input-xlarge " cssStyle="display: none; width: 150px">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_dj_sub')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
			</li>


		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairDisciplinaryAction:add">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="affair:affairDisciplinaryAction:delete">
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairDisciplinaryAction/deleteByIds','checkAll','myCheckBox')"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
            <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
            <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairDisciplinaryAction'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="2"><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th rowspan="2">序号</th>
				<th colspan="2" style="text-align: center">时间</th>
				<th rowspan="2">姓名</th>
				<th rowspan="2">单位</th>
				<th rowspan="2">职务</th>
				<th rowspan="2">职级</th>
				<th rowspan="2">政治面貌</th>
				<th rowspan="2">问题性质</th>
				<th colspan="5" style="text-align: center">处分及处理方式</th>
				<th rowspan="2">处分单位</th>
				<th rowspan="2">文号</th>
				<th rowspan="2">处分决定时间</th>
				<th rowspan="2">处分期满时间</th>
				<th rowspan="2">是否立案</th>
				<shiro:hasPermission name="affair:affairDisciplinaryAction:edit"><th id="handleTh" rowspan="2">操作</th></shiro:hasPermission>
			</tr>
			<tr>
                <th>发生时间</th>
                <th>受理时间</th>
				<th>党纪处分</th>
				<th>行政处分</th>
				<th>司法处理</th>
				<th>组织处理</th>
				<th>其他方式</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairDisciplinaryAction" varStatus="status">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDisciplinaryAction.id}"/></td>
				<td>
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
              <td>
                    <fmt:formatDate value="${affairDisciplinaryAction.lrDate}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                    <fmt:formatDate value="${affairDisciplinaryAction.chfDate}" pattern="yyyy-MM-dd"/>
                </td>
				<td>
					${affairDisciplinaryAction.name}
				</td>
                <td>
                        ${affairDisciplinaryAction.unit}
                </td>
                <td>
                        ${affairDisciplinaryAction.job}
                </td>
                <td>
                        ${affairDisciplinaryAction.jobLevel}
                </td>
                <td>
						${fns:getDictLabel(affairDisciplinaryAction.zhengzhiFace, 'political_status', '')}
                </td>
                <td>
                        ${fns:getDictLabel(affairDisciplinaryAction.nature, 'affair_wenti', '')}
                </td>
				<td>
<%--						${fns:getDictLabel(affairDisciplinaryAction.subOption, 'affair_dj_type', '')}--%>
								${fns:getDictLabel(affairDisciplinaryAction.zzSubOption, 'affair_dj_sub_option', '')}
				</td>
				<td>
<%--						${fns:getDictLabel(affairDisciplinaryAction.disciplinaryType, 'affair_xzchufen', '')}--%>
								${fns:getDictLabel(affairDisciplinaryAction.xzSubOption, 'affair_xz_sub_option', '')}
				</td>
				<td>
						${affairDisciplinaryAction.sifa}
				</td>
				<td>
						${affairDisciplinaryAction.zuzhi}
				</td>
				<td>
						${affairDisciplinaryAction.other}
				</td>
                <td>
						${fns:getDictLabel(affairDisciplinaryAction.cfUnit, 'affair_cf_unit', '')}
                </td>
                <td>
                        ${affairDisciplinaryAction.fileNum}
                </td>
                <td>
                    <fmt:formatDate value="${affairDisciplinaryAction.approvalDate}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                    <fmt:formatDate value="${affairDisciplinaryAction.influencePeriod}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                        ${fns:getDictLabel(affairDisciplinaryAction.isLian, 'yes_no', '')}
                </td>
				<td class="handleTd">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairDisciplinaryAction.id}')">查看</a>
					<c:choose>
						<c:when test="${ 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
							<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
								<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
								<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="affair:affairDisciplinaryAction:delete">
								<a href="${ctx}/affair/affairDisciplinaryAction/delete?id=${affairDisciplinaryAction.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
							</shiro:hasPermission>
						</c:when>
						<c:otherwise>
							<c:if test="${'276d8cdc184748c8a5ff014221fb135a' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="affair:affairDisciplinaryAction:delete">
									<a href="${ctx}/affair/affairDisciplinaryAction/delete?id=${affairDisciplinaryAction.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'35737e5582804ef08502c7283db5c5cf' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'5a0766c9a3df41a88f5759a29886f1ae' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
							<c:if test="${affairTousujubaoguanli.createBy.id == fns:getUser().id}">
								<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
									<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
									<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
								</shiro:hasPermission>
							</c:if>
						</c:otherwise>
					</c:choose>
				<%--<shiro:hasPermission name="affair:affairDisciplinaryAction:edit">
						<a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairDisciplinaryAction/form?id=${affairDisciplinaryAction.id}')">修改</a>
					<a href="javascript:void(0)" onclick="openForm('${ctx}/exam/examJcInfoPlus/disciplinaryActionform?id=${affairDisciplinaryAction.id}')">推送到奖惩信息库</a>
				</shiro:hasPermission>
					<shiro:hasPermission name="affair:affairDisciplinaryAction:delete">
						<a href="${ctx}/affair/affairDisciplinaryAction/delete?id=${affairDisciplinaryAction.id}" onclick="return confirmx('确认要删除该纪律处分吗？', this.href)">删除</a>
					</shiro:hasPermission>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>