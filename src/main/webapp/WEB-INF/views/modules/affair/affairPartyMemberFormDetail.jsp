<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#print").click(function(){
			$("#contentTable").printThis({
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
	//组织关系转接弹窗
	function openTransferDialog() {
		//var url = "iframe:${ctx}/affair/affairRelationshipTransfer/partyMemberForm?name=${affairPartyMember.name}&idNumber=${affairPartyMember.cardNum}&oldOrganization=${affairPartyMember.partyBranch}&oldOrganizationId=${affairPartyMember.partyBranchId}";
		var url = "iframe:${ctx}/affair/affairRelationshipTransfer/form?name=${affairPartyMember.name}&idNumber=${affairPartyMember.cardNum}&oldOrganization=${affairPartyMember.partyBranch}&oldOrganizationId=${affairPartyMember.partyBranchId}";
		top.$.jBox.open(url, "组织关系转接",800,530,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			},closed:function (){
				closeLoading();
			}
		});
	};
</script>
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairPartyMember.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyMember.sex, 'sex', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否台湾省籍：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyMember.isTaiwan, 'yes_no', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairPartyMember.personnelCategory, 'affair_personnel_category', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加入党组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairPartyMember.joinDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">固定电话：</span><span class="modal-custom-info2-value">${affairPartyMember.telephone}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">家庭住址：</span><span class="modal-custom-info2-value">${affairPartyMember.homeAddress}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2" style="width: 400px;">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">身份证号：</span><span class="modal-custom-info2-value" style="width: 260px;">${affairPartyMember.cardNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">民族：</span><span class="modal-custom-info2-value" style="width: 260px;">${fns:getDictLabel(affairPartyMember.nation, 'nation', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">出生日期：</span><span class="modal-custom-info2-value" style="width: 260px;"><fmt:formatDate value="${affairPartyMember.birth}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">所在党支部：</span><span class="modal-custom-info2-value" style="width: 260px;">${affairPartyMember.partyBranch}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">转为正式党员日期：</span><span class="modal-custom-info2-value" style="width: 260px;"><fmt:formatDate value="${affairPartyMember.turnDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">参加工作日期：</span><span class="modal-custom-info2-value" style="width: 260px;"><fmt:formatDate value="${affairPartyMember.workDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 140px;">已减少原因：</span><span class="modal-custom-info2-value" style="width: 260px;">${fns:getDictLabel(affairPartyMember.deleteReason, 'delete_reason', '')}</span></div>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col3" style="width: 250px;">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key" style="width: 115px;">警号：</span><span class="modal-custom-info2-value" style="width: 115px;">${affairPartyMember.policeNo}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"  style="width: 115px;">籍贯：</span><span class="modal-custom-info2-value" style="width: 115px;">${affairPartyMember.birthplace}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"  style="width: 115px;">学历：</span><span class="modal-custom-info2-value" style="width: 115px;">${fns:getDictLabel(affairPartyMember.education, 'affair_party_member_xueli', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"  style="width: 115px;">工作岗位：</span><span class="modal-custom-info2-value" style="width: 115px;">${affairPartyMember.workPlace}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"  style="width: 115px;">联系电话：</span><span class="modal-custom-info2-value" style="width: 115px;">${affairPartyMember.phoneNum}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key"  style="width: 115px;">婚姻状况：</span><span class="modal-custom-info2-value" style="width: 115px;">${fns:getDictLabel(affairPartyMember.maritalStatus, 'affair_marital_status', '')}</span></div>


						</div>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
					<%--
					<div id="transfer" class="modal-custom-info1-btn red" onclick="openTransferDialog()">组织关系转接</div>
					--%>
				</div>
			</div>
		</div>
	</div>
</div>