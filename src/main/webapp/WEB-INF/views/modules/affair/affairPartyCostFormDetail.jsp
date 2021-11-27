<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#print").click(function(){
			$("#printDiv").printThis({
				debug: false,
				importCSS: true,
				importStyle: true,
				printContainer: true,
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false
			});
		});
	});
</script>
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="printDiv">
					<div id="contentTable" class="modal-custom-content">
						<span>党员信息：</span>
						<div class="modal-custom-info2">
							<div class="modal-custom-info2-col modal-custom-info2-col1">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.name}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyCost.affairPartyMember.nation, 'nation', '')}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.birth}" pattern="yyyy-MM-dd"/></span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在党支部：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.partyBranch}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">工作岗位：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.workPlace}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.workDate}" pattern="yyyy-MM-dd"/></span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭住址：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.homeAddress}</span></div>
							</div>
							<div class="modal-custom-info2-col modal-custom-info2-col2">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" >身份证号：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.cardNum}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">籍贯：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.birthplace}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.education}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加入党组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyCost.affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/></span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span class="modal-custom-info2-value">${affairPartyCost.affairPartyMember.phoneNum}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">婚姻状况：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyCost.affairPartyMember.maritalStatus, 'affair_marital_status', '')}</span></div>
							</div>
							<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 300px;">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">性别：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.sex, 'sex', '')}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">是否台湾省籍：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.isTaiwan, 'yes_no', '')}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">人员类别一：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.personnelCategory, 'affair_personnel_category', '')}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">转为正式党员日期：</span><span class="modal-custom-info2-value" style="width: 150px;"><fmt:formatDate value="${affairPartyCost.affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/></span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">固定电话：</span><span class="modal-custom-info2-value" style="width: 150px;">${affairPartyCost.affairPartyMember.telephone}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">已减少原因：</span><span class="modal-custom-info2-value" style="width: 150px;">${fns:getDictLabel(affairPartyCost.affairPartyMember.deleteReason, 'delete_reason', '')}</span></div>

							</div>
						</div>
					</div>
					<div id="contentTable2" class="modal-custom-content">
						<span>党费信息：</span>
						<div class="modal-custom-info2">
							<div class="modal-custom-info2-col modal-custom-info2-col1">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">党员类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyCost.type, 'affair_personnel_type', '')}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">交纳党费比例：</span><span class="modal-custom-info2-value">${affairPartyCost.proportion}%</span></div>
							</div>
							<div class="modal-custom-info2-col modal-custom-info2-col2">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">缴费年度：</span><span class="modal-custom-info2-value">${affairPartyCost.year}</span></div>
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实交党费：</span><span class="modal-custom-info2-value">${affairPartyCost.cost2}元</span></div>
							</div>
							<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 300px;">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">缴费基数：</span><span class="modal-custom-info2-value" style="width: 150px;">${affairPartyCost.baseNum}元</span></div>
<%--								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">应交党费：</span><span class="modal-custom-info2-value" style="width: 150px;">${affairPartyCost.cost1}元</span></div>--%>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>