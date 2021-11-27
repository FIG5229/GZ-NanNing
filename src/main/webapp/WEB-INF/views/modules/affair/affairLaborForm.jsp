<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>劳资管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="affairLabor" action="${ctx}/affair/affairLabor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="unit" name="unitId" value="${affairLabor.unitId}" labelName="unit" labelValue="${affairLabor.unit}"
								title="发布部门" url="/affair/affairLaborOffice/treeData" cssClass="required" allowClear="true" notAllowSelectParent="false"/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<form:input path="year" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">年度：</label>
			<div class="controls">
				<input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLabor.year}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月度：</label>
			<div class="controls">
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="${affairLabor.month}"
					   onclick="WdatePicker({dateFmt:'M',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idNumber" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工资对应行政级别：</label>
			<div class="controls">
				<form:input path="level" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本工资-职务工资、试用期工资：</label>
			<div class="controls">
				<form:input path="jbSalary" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本工资-级别工资：</label>
			<div class="controls">
				<form:input path="jbGradeSalary" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">国家统一的津贴补贴：</label>
			<div class="controls">
				<form:input path="gjSum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">1、加班补贴：</label>
			<div class="controls">
				<form:input path="jiabanAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2、警衔津贴：</label>
			<div class="controls">
				<form:input path="jingxianAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">3、执勤岗位津贴：</label>
			<div class="controls">
				<form:input path="zhiqinAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">4、艰苦边远地区津贴：</label>
			<div class="controls">
				<form:input path="jkbyAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">5、住宅公务电话费：</label>
			<div class="controls">
				<form:input path="telephoneFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">6、移动通讯费补贴：</label>
			<div class="controls">
				<form:input path="mobileFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">7、禁食猪肉补贴：</label>
			<div class="controls">
				<form:input path="jszrAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">8、女同志卫生费：</label>
			<div class="controls">
				<form:input path="nvHygieneFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">9、独生子女父母奖励：</label>
			<div class="controls">
				<form:input path="onlyChildAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">10、防暑降温费：</label>
			<div class="controls">
				<form:input path="fangshuAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">11、信访工作人员岗位津贴：</label>
			<div class="controls">
				<form:input path="xinfangAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">12、1993年工改保留补贴：</label>
			<div class="controls">
				<form:input path="gonggai1993Allowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">13、西藏特殊津贴：</label>
			<div class="controls">
				<form:input path="xizangAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">14、高海拔地区折算工龄补贴：</label>
			<div class="controls">
				<form:input path="highAltitudeAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">15、新疆保留地区补贴：</label>
			<div class="controls">
				<form:input path="xinjiangAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">16、特区津贴：</label>
			<div class="controls">
				<form:input path="sarAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">17、乡镇工作补贴：</label>
			<div class="controls">
				<form:input path="townshipAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">18、增不抵缴临时性补贴：</label>
			<div class="controls">
				<form:input path="linshiAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">19、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia19" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">20、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia20" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">21、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia21" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">22、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia22" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">23、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia23" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">24、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia24" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">25、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia25" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">26、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia26" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">27、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia27" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">28、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia28" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">29、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia29" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">30、国家补贴空白行：</label>
			<div class="controls">
				<form:input path="guojia30" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">规范津贴补贴：</label>
			<div class="controls">
				<form:input path="guifanSum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">1、工作性津贴：</label>
			<div class="controls">
				<form:input path="workingAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2、生活性津贴：</label>
			<div class="controls">
				<form:input path="livingAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">改革性补贴：</label>
			<div class="controls">
				<form:input path="gaigeSum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">1、住宅取暖补贴：</label>
			<div class="controls">
				<form:input path="zhuzhaiAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2、住房提租补贴：</label>
			<div class="controls">
				<form:input path="zhufangAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">3、物业服务补贴：</label>
			<div class="controls">
				<form:input path="wuyeAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">4、上下班交通补贴：</label>
			<div class="controls">
				<form:input path="jiaotongAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">5、改革性补贴：</label>
			<div class="controls">
				<form:input path="gaigeAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">6、改革性补贴空白行：</label>
			<div class="controls">
				<form:input path="gaige6" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">7、改革性补贴空白行：</label>
			<div class="controls">
				<form:input path="gaige7" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">8、改革性补贴空白行：</label>
			<div class="controls">
				<form:input path="gaige8" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">9、改革性补贴空白行：</label>
			<div class="controls">
				<form:input path="gaige9" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">10、改革性补贴空白行：</label>
			<div class="controls">
				<form:input path="gaige10" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">奖励性补贴和其他：</label>
			<div class="controls">
				<form:input path="rewardSum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">1、乘务补贴（含高原）：</label>
			<div class="controls">
				<form:input path="chengwuAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">2、线路津贴：</label>
			<div class="controls">
				<form:input path="xianluAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">3、安全治安挂钩考核：</label>
			<div class="controls">
				<form:input path="anquanAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">4、精神文明奖：</label>
			<div class="controls">
				<form:input path="jingshenAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">5、人民警察奖励：</label>
			<div class="controls">
				<form:input path="jingchaAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">6、公务员奖励：</label>
			<div class="controls">
				<form:input path="gongwuyuanAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">7、讲课费：</label>
			<div class="controls">
				<form:input path="jiangkeAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">8、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward8" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">9、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward9" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">10、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward10" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">11、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward11" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">12、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward12" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">13、奖励性补贴空白行：</label>
			<div class="controls">
				<form:input path="reward13" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">14、往年减员补发平均额：</label>
			<div class="controls">
				<form:input path="jianyuanAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">15、补发往年工资及津补：</label>
			<div class="controls">
				<form:input path="gongziAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年终一次性奖金：</label>
			<div class="controls">
				<form:input path="yearEndAwards" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">社会保险合计：</label>
			<div class="controls">
				<form:input path="baoxianSum" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">基本养老保险：</label>
			<div class="controls">
				<form:input path="baseYanglaoAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职业年金：</label>
			<div class="controls">
				<form:input path="zhiyeAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">基本医疗保险：</label>
			<div class="controls">
				<form:input path="baseYiliaoAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">补充医疗保险：</label>
			<div class="controls">
				<form:input path="buchongYiliaoAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生育保险等：</label>
			<div class="controls">
				<form:input path="shengyuAllowance" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">住房公积金：</label>
			<div class="controls">
				<form:input path="gongjijin" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">个人所得税：</label>
			<div class="controls">
				<form:input path="personalIncomeTax" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在铁路公安局：</label>
			<div class="controls">
				<form:input path="whereGonganju" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在公安处（局机关、直属支队）：</label>
			<div class="controls">
				<form:input path="whereGonganchu" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="department" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">劳统人员分类：</label>
			<div class="controls">
				<form:input path="personnelType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当年接收毕业生、复转兵人标识：</label>
			<div class="controls">
				<form:select path="isLogo" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="affair:affairLabor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.$.jBox.close();"/>
		</div>
	</form:form>
	<script>
		if("success"=="${saveResult}"){
			parent.$.jBox.close();
		}
	</script>
</body>
</html>