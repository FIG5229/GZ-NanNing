<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#print").click(function(){
			$('.download').css('display', 'none');
			$("#contentTable").printThis({
				debug: false,
				importCSS: true,
				importStyle: true,
				printContainer: true,
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false,
				afterPrint:function(){
					$('.download').css('display', '');
				}
			});
		});
	});
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<h6 style="color: #0d0d0d;font-size: 14px">人员信息</h6>
					<hr/>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户名：</span><span class="modal-custom-info2-value">${xunLiPersonnel.idNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${xunLiPersonnel.policeIdNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">出生日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${xunLiPersonnel.birthday}" pattern="yyyy-MM-dd"/></span></div>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件类型：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairXunLi.certificateType, 'affair_certificate_type', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${xunLiPersonnel.education}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">电子邮箱：</span><span class="modal-custom-info2-value">${affairXunLi.email}</span></div>
--%>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警衔：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiJob.policeRank, 'police_rank', '')}</span></div>
--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span class="modal-custom-info2-value">${xunLiPersonnel.personnelType}</span></div>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">行政职务：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiJob.administrativePost, 'administration_post', '')}</span></div>
--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">血型：</span><span class="modal-custom-info2-value">${xunLiPersonnel.bloodType}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参加工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${xunLiPersonnel.workDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiPersonnel.politicsFace, 'political_status', '')}</span></div>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">是否是班子成员：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiPersonnel.teamMembers,'yes_no','')}</span></div>
--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员状态：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiPersonnel.status, 'personnel_status', '')}</span></div>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">管理类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiJob.managementClass, 'management_type', '')}</span></div>
--%>
						<%--<c:forEach items="${xunLiXueLi}" var="xunLiXueLi" varStatus="status">
								<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">毕业学校：</span><span class="modal-custom-info2-value">${xunLiXueLi[0].schoolName}</span></div>
							</c:forEach>--%>
						</div>
						<div class="modal-custom-info2-col modal-custom-info2-col2">
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${xunLiPersonnel.name}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiPersonnel.sex, 'sex', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiPersonnel.nation, 'nation', '')}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">证件号：</span><span class="modal-custom-info2-value">${xunLiPersonnel.idNumber}</span></div>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专业：</span><span class="modal-custom-info2-value">${affairXunLi.major}</span></div>
--%>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学位：</span><span class="modal-custom-info2-value">${xunLiMes.degree}</span></div>
--%>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">移动电话：</span><span class="modal-custom-info2-value">${xunLiPersonnel.phoneNumber}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">用户状态：</span><span class="modal-custom-info2-value">${affairXunLi.userStatus}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警种：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiJob.policeClassification, 'police_classification', '')}</span></div>
--%>
<%--
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务级别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(xunLiTra.jobLevel, 'post_level', '')}</span></div>
--%>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">健康状况：</span><span class="modal-custom-info2-value">${xunLiPersonnel.healthStatus}</span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参见公安工作日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${xunLiPersonnel.publicSecurityDate}" pattern="yyyy-MM-dd"/></span></div>
							<div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加入组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${xunLiPersonnel.organizationDate}" pattern="yyyy-MM-dd"/></span></div>
						</div>
					</div>
					<%--<hr/>
					<h6 style="color: #0d0d0d;font-size: 14px">机构信息</h6>
					<hr/>
					<table id="contentTable1" class="table table-striped table-bordered table-condensed">
						<tr>
							<th>机构名称</th>
							<th>机构全路径</th>
							<th>创建日期</th>
						</tr>
						<tbody>
							<tr>
								<td>
										${affairXunLi.jiGouName}
								</td>
								<td>
										${affairXunLi.jiGouQLJ}
								</td>
								<td>
										${affairXunLi.creationTime}
								</td>
							</tr>
						</tbody>
					</table>

					<hr/>
					<h6 style="color: #0d0d0d;font-size: 14px">域和角色</h6>
					<hr/>
					<table id="contentTable2" class="table table-striped table-bordered table-condensed">
						<tr>
							<th>域名称</th>
							<th>默认角色</th>
						</tr>
						<tbody>
						<tr>
							<td>
								${affairXunLi.regionName}
							</td>
							<td>
								${affairXunLi.roleName}
							</td>
						</tr>
						</tbody>
					</table>
				<hr/>--%>
					<%--<div class="modal-custom-info1-file">
						<div class="modal-custom-info1-file-1">附件：</div>
						<div class="modal-custom-info1-file-r">
							<c:forEach items="${filePathList}" var="m" varStatus="status">
								<div class="modal-custom-info1-file-item">
									<span>${m.fileName}</span>
										&lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
									<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
								</div>
							</c:forEach>
						</div>
					</div>--%>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>
			</div>
		</div>
	</div>
</div>