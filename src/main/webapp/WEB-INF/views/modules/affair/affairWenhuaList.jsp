<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文化人才管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
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
			/*导出数据*/
			$("#export").click(
					function () {
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action", "${ctx}/affair/affairWenhua/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairWenhua/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action", "${ctx}/affair/affairWenhua/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action", "${ctx}/affair/affairWenhua/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
							buttons: {
								'导出全部数据': 'all',
								'导出当前页面数据': 'part',
								'取消': 'cancel'
							}
						});
					}
			);

			/*导入数据*/
			$("#btnImport").click(function () {
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_Wenhua", "导入", 800, 520, {
					itle: "导入数据", buttons: {"关闭": true},
					bottomText: "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！", closed: function () {
						self.location.href = "${ctx}/affair/affairWenhua"
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
		function openDialog(url) {
			top.$.jBox.open("iframe:"+url, "文化人才",800,520,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairWenhua/formDetail?id=" + id;
			top.$.jBox.open(url, "文采艺人详情", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		}
		//提交申报
		function submitByIds() {
			if(null == $("#chuCheckMan").val() || "" ==  $("#chuCheckMan").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				//console.log($(this).val())
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
		//打开审核弹窗
		function openShDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairWenhua/shenHeDialog?id="+id,"文化艺人审核",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairWenhua/list";
				}
			});
		}
		//推送详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairWenhua/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
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
		<li><a href="${ctx}/affair/affairWenyi/">文艺作品</a></li>
		<%--<li><a href="${ctx}/affair/affairCultureActivity/">文化活动</a></li>--%>
		<li class="active"><a href="${ctx}/affair/affairWenhua/">文化人才</a></li>
		<li><a href="${ctx}/affair/affairActivityMien/">活动风采</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairWenhua" action="${ctx}/affair/affairWenhua/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="5.4文化人才.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
				<%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
			<c:choose>
				<c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit" labelValue="${affairWenhua.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false" isAll="true"/>
					</li>
				</c:when>
				<c:otherwise>
					<li><label>单位：</label>
						<sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit" labelValue="${affairWenhua.unit}"
										title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
					</li>
				</c:otherwise>
			</c:choose>
			<%--<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairWenhua.unitId}" labelName="unit" labelValue="${affairWenhua.unit}"
								title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li><label>特长：</label>
				<form:select path="specialty" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_wenyi_specialty')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>入会情况：</label>
				<form:select path="joinType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('affair_ruhui')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>

			<li><label>奖项级别：</label>
				<form:select path="reward" cssStyle="width:176px;" class="input-xlarge ">
					<form:option value="" label=""/>
                    <form:options items="${fns:getDictList('affair_wenyi_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>

			<li class="clearfix"></li>

			<ul class="ul-form2">
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
				<shiro:hasPermission name="affair:affairWenhua:edit">
					<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openDialog('${ctx}/affair/affairWenhua/form')"/></li>
					<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairWenhua/deleteByIds','checkAll','myCheckBox')"/></li>
<%--					<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>--%>
				</shiro:hasPermission>
				<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
				<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
				<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairWenhua'"/></li>
				<li class="clearfix"></li>
			</ul>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>姓名</th>
				<th>单位</th>
				<th>特长</th>
				<th>入会情况</th>
				<th>奖项情况</th>
				<th>审核状态</th>
				<shiro:hasPermission name="affair:affairWenhua:edit"><th id="handleTh">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairWenhua">
			<tr>
				<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWenhua.id}"/></td>
				<td>
					${affairWenhua.name}
				</td>
				<td>
					${affairWenhua.unit}
				</td>
				<td>
					${fns:getDictLabel(affairWenhua.specialty, 'affair_wenyi_specialty', '')}
				</td>
				<td>
					${fns:getDictLabel(affairWenhua.joinType, 'affair_ruhui', '')}
				</td>
				<td>
					${affairWenhua.reward}
				</td>
				<td>
					${fns:getDictLabel(affairWenhua.checkType, 'check_type', '')}
				</td>
				<td class="handleTd">
					<a onclick="openDetailDialog('${affairWenhua.id}')" style="cursor: pointer">查看</a>
					<shiro:hasPermission name="affair:affairWenhua:edit">
						<a href="javascript:void(0)" onclick="openDialog('${ctx}/affair/affairWenhua/form?id=${affairWenhua.id}')">修改</a>
						<a href="${ctx}/affair/affairWenhua/delete?id=${affairWenhua.id}" onclick="return confirmx('确认要删除该文化人才吗？', this.href)">删除</a>
				</shiro:hasPermission>
<%--					<shiro:hasPermission name="affair:affairWenhua:shenhe">--%>
						<c:if test="${'5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">
							<c:if test="${affairWenhua.chuCheckId == fns:getUser().id || affairWenhua.juCheckId == fns:getUser().id }">
								<a onclick="openShDialog('${affairWenhua.id}')" style="cursor: pointer">审核</a>
							</c:if>
						</c:if>
<%--					</shiro:hasPermission>--%>
<%--					<c:if test="${'5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">--%>
						<a onclick="openPropellingDialog('${affairWenhua.id}')">推送</a>
<%--					</c:if>--%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>

	<form:form id="searchForm2" modelAttribute="affairWenhua" action="${ctx}/affair/affairWenhua/submitByIds" method="post" class="breadcrumb form-search">
		<%--<ul class="ul-form" style="text-align: right">
			<font color="red">请选择申报单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="chuCheckMan" name="chuCheckId" value="${affairActivityMien.chuCheckId}" labelName="chuCheckMan" labelValue="${affairActivityMien.chuCheckMan}"
							title="单位" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息" isAll="true"/>
			<input  class="btn btn-primary" type="button" value="申报" onclick="submitByIds('${ctx}/affair/affairActivityMien/submitByIds')"/>
		</ul>--%>

		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择审核单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<form:select id="chuCheckMan" path="chuCheckId"   class="input-xlarge required">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('affair_mein_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
</body>
</html>