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
								$("#searchForm").attr("action", "${ctx}/affair/affairWenhua/export?flag=true")
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
				},closed:function (){self.location.href="${ctx}/affair/affairWenhua"}
			});
		};
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairWenhua/formDetail?id=" + id;
			top.$.jBox.open(url, "文采艺人详情", 800, 500, {
				buttons: {"关闭": true},
				loaded: function (h) {
					$(".jbox-content", top.document).css("overflow-y", "hidden");
				}
			});
		};
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
		};
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
		};
		//推送详情弹窗
		function openPropellingDialog(id) {
			var url = "iframe:${ctx}/affair/affairWenhua/propelling?id="+id;
			top.$.jBox.open(url, "\n" + "数据推送",500,300,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		};
	</script>
</head>
<body>
	<c:choose>
		<c:when test="${talent eq 'literaryTalent'}">
			<form:form id="searchForm" modelAttribute="affairWenhua" action="${ctx}/affair/affairWenhua/literaryTalent" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="year" name="year" type="hidden" value="${affairWenhua.year}"/>
				<input id="month" name="month" type="hidden" value="${affairWenhua.month}"/>
				<input id="dateType" name="dateType" type="hidden" value="${affairWenhua.dateType}"/>
				<input id="label" name="label" type="hidden" value="${affairWenhua.label}"/>
				<input name="beginTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWenhua.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="endTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWenhua.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</form:form>
		</c:when>
		<c:otherwise>
			<form:form id="searchForm" modelAttribute="affairWenhua" action="${ctx}/affair/affairWenhua/talentJoin" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="year" name="year" type="hidden" value="${affairWenhua.year}"/>
				<input id="month" name="month" type="hidden" value="${affairWenhua.month}"/>
				<input id="dateType" name="dateType" type="hidden" value="${affairWenhua.dateType}"/>
				<input id="label" name="label" type="hidden" value="${affairWenhua.label}"/>
				<input name="beginTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWenhua.startDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<input name="endTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${affairWenhua.endDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</form:form>
		</c:otherwise>
	</c:choose>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>单位</th>
				<th>特长</th>
				<th>入会情况</th>
				<th>奖项情况</th>
				<th>审核状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairWenhua">
			<tr>
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

			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>