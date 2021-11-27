<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人工资汇总</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
	<script type="text/javascript">

		$(document).ready(function() {
			$("#export").click(
					function(){
						//debugger;er;
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairSalaryGather/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSalaryGather/list");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出数据': 'all', '取消':'cancel'} });
					}
			);
			/*$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairSalaryGather/export?treeId=${treeId}");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairSalaryGather/list?treeId=${treeId}");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all','取消':'cancel'} });
					}
			);*/
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
				},closed:function (){self.location.href="${ctx}/affair/affairSalaryGather/list"}
			});
		}

		//详情弹窗
		function openDetailDialog(idNumber,year) {
			var url = "iframe:${ctx}/affair/affairLabor/formDetail?idNumber="+idNumber+"&year="+year;
			top.$.jBox.open(url, "明细",1250,550,{
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
		<li><a href="${ctx}/affair/affairLabor/list?treeId=${treeId}">个人每月工资明细</a></li>
		<li class="active"><a href="${ctx}/affair/affairSalaryGather/list?treeId=${treeId}">个人工资汇总</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairSalaryGather" action="${ctx}/affair/affairSalaryGather/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="个人工资汇总.xlsx"/>
		<ul class="ul-form">
			<%--<li>
				<label>单位：</label>
				<sys:treeselect id="unit" name="unitId" value="${affairSalaryGather.unitId}" labelName="unit" labelValue="${affairSalaryGather.unit}"
								title="单位" url="/affair/affairLaborOffice/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>--%>
			<li>
					<label>姓名：</label>
					<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li>
				<label>年度：</label>
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairSalaryGather.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairSalaryGather/list'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div id="contentTable">
		<table id="headTitle"  class="table table-striped table-bordered table-condensed" style="display: none;">
			<tr>
				<td style="text-align: center; font-size: 36px;padding-top: 10px;" >个人工资汇总表</td>
			</tr>
		</table>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th rowspan="4"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
			<th rowspan="4" style="text-align: center">序号</th>
			<th rowspan="4" style="text-align: center">时间</th>
			<th colspan="3" style="text-align: center">个人信息</th>
			<th rowspan="4" style="text-align: center">工资对应行政级别</th>
			<th colspan="42" style="text-align: center">当月应发工资收入</th>
			<th colspan="8" style="text-align: center">当月个人扣缴（元）</th>
			<th colspan="5" style="text-align: center">机构、劳统信息</th>
			<th rowspan="4" style="text-align: center">备注</th>
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
			<th style="text-align: center"> 加班补贴</th>
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
		<c:forEach items="${affairSalaryGatherList}" var="affairSalaryGatherList" varStatus="status">
			<tr>
				<td>
					<input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairSalaryGatherList.id}"/>
				</td>
				<td style="text-align: center">
						${(page.pageNo-1)*page.pageSize+status.index+1}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.year}
				</td>
				<td style="text-align: center">
					<a href="javascript:void(0)" onclick="openDetailDialog('${affairSalaryGatherList.idNumber}','${affairSalaryGatherList.year}')">${affairSalaryGatherList.name}</a>
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.unit}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.idNumber}
				</td>
                <td style="text-align: center">
						${affairSalaryGatherList.level}
                </td>
				<td style="text-align: center">
						${affairSalaryGatherList.sum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jbSalary}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jbGradeSalary}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gjSum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jiabanAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jingxianAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.zhiqinAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jkbyAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.telephoneFee}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.mobileFee}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jszrAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.nvHygieneFee}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.onlyChildAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.fangshuAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.xinfangAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gonggai1993Allowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.xizangAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.highAltitudeAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.xinjiangAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.sarAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.townshipAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.linshiAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.guifanSum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.workingAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.livingAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gaigeSum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.zhuzhaiAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.zhufangAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.wuyeAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jiaotongAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gaigeAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.rewardSum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.chengwuAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.xianluAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.anquanAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jingshenAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jingchaAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gongwuyuanAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jiangkeAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.jianyuanAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gongziAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.yearEndAwards}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.baoxianSum}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.baseYanglaoAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.zhiyeAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.baseYiliaoAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.buchongYiliaoAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.shengyuAllowance}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.gongjijin}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.personalIncomeTax}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.whereGonganju}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.whereGonganchu}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.department}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.personnelType}
				</td>
				<td style="text-align: center">
						${fns:getDictLabel(affairSalaryGatherList.isLogo, 'yes_no', '')}
				</td>
				<td style="text-align: center">
						${affairSalaryGatherList.remark}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>