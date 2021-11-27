<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>劳资管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairLabor/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairLabor/list?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all', '取消':'cancel'} });
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
					pageTitle: "打印",
					removeInline: false,
					printDelay: 333,
					header: null,
					formValues: false,
					afterPrint:function(){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
						$('#headTitle').css('display', 'none');
					}
				});
			});
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLabor", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLabor/list?treeId=${treeId}"}});
			});
		});
		function page(n,s){
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "劳资",1200,640,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairLabor/list?treeId=${treeId}"}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairLabor/list?treeId=${treeId}">个人每月工资明细</a></li>
		<li><a href="${ctx}/affair/affairSalaryGather/list?treeId=${treeId}">个人工资汇总</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairLabor" action="${ctx}/affair/affairLabor/list?treeId=${treeId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="劳资表.xlsx"/>
		<input id="u" name="u" type="hidden" value="${treeId}"/>
		<ul class="ul-form">
			<li><label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairLabor.unitId}" labelName="unit" labelValue="${affairLabor.unit}"
								title="单位" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLabor.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>

			<li><label>月度：</label>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLabor.month}"
					   onclick="WdatePicker({dateFmt:'M',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLabor/form?id=${affairLabor.id}')"/></li>
			<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLabor/deleteByIds','checkAll','myCheckBox')"/></li>
			<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLabor/list?treeId=${treeId}'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >劳资表</td>
			</tr>
		</table>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th rowspan="4"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th rowspan="4" style="text-align: center">序号</th>
				<th rowspan="4" style="text-align: center">时间</th>
				<th colspan="3" style="text-align: center">个人信息</th>
				<th rowspan="4" style="text-align: center">工资对应行政级别</th>工资对应行政级别
				<th colspan="42" style="text-align: center">当月应发工资收入</th>
				<th colspan="8" style="text-align: center">当月个人扣缴（元）</th>
				<th colspan="5" style="text-align: center">机构、劳统信息</th>
				<th rowspan="4">备注</th>
				<shiro:hasPermission name="affair:affairLabor:edit"><th id="handleTh" rowspan="4" style="text-align: center">操作</th></shiro:hasPermission>
			</tr>
		<tr>
			<th rowspan="3" style="text-align: center">姓名</th>
			<th rowspan="3" style="text-align: center">单位</th>
			<th rowspan="3" style="text-align: center">身份证号</th>
			<th rowspan="3" style="text-align: center">合计</th>
			<th colspan="2" style="text-align: center">基本工资</th>
			<th></th>
			<th colspan="37" style="text-align: center">津贴补贴</th>
			<th rowspan="3" style="text-align: center">年终一次性奖金</th>
			<th rowspan="3" style="text-align: center">合计</th>
			<th colspan="5" style="text-align: center">社会保险</th>
			<th rowspan="3" style="text-align: center">住房公积金</th>
			<th rowspan="3" style="text-align: center">个人所得税</th>
			<th rowspan="3" style="text-align: center">所在铁路公安局</th>
			<th rowspan="3" style="text-align: center">所在公安处（局机关、直属支队）</th>
			<th rowspan="3" style="text-align: center">部门</th>
			<th rowspan="3" style="text-align: center">劳统人员分类</th>
			<th rowspan="3" style="text-align: center">当年接受毕业生、复转军人标识</th>
		</tr>
		<tr>
			<th rowspan="2" style="text-align: center">1、职务工资、试用期工资</th>
			<th rowspan="2" style="text-align: center">2、级别工资</th>
			<th rowspan="2" style="text-align: center">1、国家统一的津贴补贴</th>
			<th style="text-align: center">（1）</th>
			<th style="text-align: center">（2）</th>
			<th style="text-align: center">（3）</th>
			<th style="text-align: center">（4）</th>
			<th style="text-align: center">（5）</th>
			<th style="text-align: center">（6）</th>
			<th style="text-align: center">（7）</th>
			<th style="text-align: center">（8）</th>
			<th style="text-align: center">（9）</th>
			<th style="text-align: center">（10）</th>
			<th style="text-align: center">（11）</th>
			<th style="text-align: center">（12）</th>
			<th style="text-align: center">（13）</th>
			<th style="text-align: center">（14）</th>
			<th style="text-align: center">（15）</th>
			<th style="text-align: center">（16）</th>
			<th style="text-align: center">（17）</th>
			<th style="text-align: center">（18）</th>
			<th rowspan="2" style="text-align: center">2、规范津贴补贴</th>
			<th style="text-align: center">（1）</th>
			<th style="text-align: center">（2）</th>
			<th rowspan="2" style="text-align: center">3、改革性 补贴</th>
			<th style="text-align: center">（1）</th>
			<th style="text-align: center">（2）</th>
			<th style="text-align: center">（3）</th>
			<th style="text-align: center">（4）</th>
			<th style="text-align: center">（5）</th>
			<th rowspan="2" style="text-align: center">4、奖励性补贴和其他</th>
			<th style="text-align: center">（1）</th>
			<th style="text-align: center">（2）</th>
			<th style="text-align: center">（3）</th>
			<th style="text-align: center">（4）</th>
			<th style="text-align: center">（5）</th>
			<th style="text-align: center">（6）</th>
			<th style="text-align: center">（7）</th>
			<th style="text-align: center">（8）</th>
			<th style="text-align: center">（9）</th>
			<th rowspan="2" style="text-align: center">基本养老保险</th>
			<th rowspan="2" style="text-align: center">职业年金</th>
			<th rowspan="2" style="text-align: center">基本医疗保险</th>
			<th rowspan="2" style="text-align: center">补充医疗保险</th>
			<th rowspan="2" style="text-align: center">生育保险等</th>
		</tr>
		<tr>
			<th style="text-align: center">加班补贴</th>
			<th style="text-align: center">警衔津贴</th>
			<th style="text-align: center">执勤岗位津贴</th>
			<th style="text-align: center">艰苦边远地区津贴</th>
			<th style="text-align: center">住宅公务电话费</th>
			<th style="text-align: center">移动通讯费补贴</th>
			<th style="text-align: center">禁食猪肉补贴</th>
			<th style="text-align: center">女同志卫生费</th>
			<th style="text-align: center">独生子女父母奖励</th>
			<th style="text-align: center">防暑降温费</th>
			<th style="text-align: center">信访工作人员岗位津贴</th>
			<th style="text-align: center">1993年工改保留补贴</th>
			<th style="text-align: center">西藏特殊津贴</th>
			<th style="text-align: center">高海拔地区折算工龄补贴</th>
			<th style="text-align: center">新疆保留地区补贴</th>
			<th style="text-align: center">特区津贴</th>
			<th style="text-align: center">乡镇工作补贴</th>
			<th style="text-align: center">增不抵缴临时性补贴</th>
			<th style="text-align: center">（1）工作性津贴</th>
			<th style="text-align: center">（2）生活性补贴</th>
			<th style="text-align: center">住宅取暖补贴</th>
			<th style="text-align: center">住房提租补贴</th>
			<th style="text-align: center">物业服务补贴</th>
			<th style="text-align: center">上下班交通补贴</th>
			<th style="text-align: center">改革性补贴</th>
			<th style="text-align: center">乘务补贴（含高原）</th>
			<th style="text-align: center">线路津贴</th>
			<th style="text-align: center">安全治安挂钩考核</th>
			<th style="text-align: center">精神文明奖</th>
			<th style="text-align: center">人民警察奖励</th>
			<th style="text-align: center">公务员奖励</th>
			<th style="text-align: center">讲课费</th>
			<th style="text-align: center">往年减员补发平均额</th>
			<th style="text-align: center">补发往年工资及津补</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairLabor" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLabor.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
						${affairLabor.year}-${affairLabor.month}
				</td>
				<td style="text-align: center">
						${affairLabor.name}
				</td>
				<td style="text-align: center">
						${affairLabor.unit}
				</td>
				<td style="text-align: center">
						${affairLabor.idNumber}
				</td>
				<td style="text-align: center">
						${affairLabor.level}
				</td>
				<td style="text-align: center">
						${affairLabor.sum}

				</td>

				<td style="text-align: center">
						${affairLabor.jbSalary}
				</td>
				<td style="text-align: center">
						${affairLabor.jbGradeSalary}
				</td>
				<td style="text-align: center">
						${affairLabor.gjSum}
				</td>
				<td style="text-align: center">
						${affairLabor.jiabanAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jingxianAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.zhiqinAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jkbyAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.telephoneFee}
				</td>
				<td style="text-align: center">
						${affairLabor.mobileFee}
				</td>
				<td style="text-align: center">
						${affairLabor.jszrAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.nvHygieneFee}
				</td>
				<td style="text-align: center">
						${affairLabor.onlyChildAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.fangshuAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.xinfangAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gonggai1993Allowance}
				</td>
				<td style="text-align: center">
						${affairLabor.xizangAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.highAltitudeAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.xinjiangAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.sarAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.townshipAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.linshiAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.guifanSum}
				</td>
				<td style="text-align: center">
						${affairLabor.workingAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.livingAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gaigeSum}
				</td>
				<td style="text-align: center">
						${affairLabor.zhuzhaiAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.zhufangAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.wuyeAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jiaotongAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gaigeAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.rewardSum}
				</td>
				<td style="text-align: center">
						${affairLabor.chengwuAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.xianluAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.anquanAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jingshenAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jingchaAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gongwuyuanAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jiangkeAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.jianyuanAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gongziAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.yearEndAwards}
				</td>
				<td style="text-align: center">
						${affairLabor.baoxianSum}
				</td>
				<td style="text-align: center">
						${affairLabor.baseYanglaoAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.zhiyeAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.baseYiliaoAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.buchongYiliaoAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.shengyuAllowance}
				</td>
				<td style="text-align: center">
						${affairLabor.gongjijin}
				</td>
				<td style="text-align: center">
						${affairLabor.personalIncomeTax}
				</td>
				<td style="text-align: center">
						${affairLabor.whereGonganju}
				</td>
				<td style="text-align: center">
						${affairLabor.whereGonganchu}
				</td>
				<td style="text-align: center">
						${affairLabor.department}
				</td>
				<td style="text-align: center">
						${affairLabor.personnelType}
				</td>
				<td style="text-align: center">
								${fns:getDictLabel(affairLabor.isLogo, 'yes_no', '')}
				</td>
				<td style="text-align: center">
						${affairLabor.remark}
				</td>
				<shiro:hasPermission name="affair:affairLabor:edit"><td class="handleTd">
					<c:if test="${affairLabor.createBy.id == fns:getUser().id}">
						<a href="javascript:void(0)"
						   onclick="openForm('${ctx}/affair/affairLabor/form?id=${affairLabor.id}')">修改</a>
						<a href="${ctx}/affair/affairLabor/delete?id=${affairLabor.id}&treeId=${treeId}"
						   onclick="return confirmx('确认要删除该劳资吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>