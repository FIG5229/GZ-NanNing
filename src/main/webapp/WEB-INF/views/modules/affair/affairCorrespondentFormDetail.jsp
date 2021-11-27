<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
				loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
				pageTitle: "打印",
				removeInline: false,
				printDelay: 333,
				header: null,
				formValues: false,
				afterPrint:function (){
					$('.download').css('display', '');
				}
			});
		});
	});
</script>
<div id="modalInfo1">
	<div class="modal-custom">
		<div class="modal-custom-main">
			<div class="modal-custom-content">
				<div id="contentTable" class="modal-custom-content">
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">姓名：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.name}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">单位：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.unit}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">职务：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.duty}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">联系方式：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.phone}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">身份证号：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.idNumber}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">培训情况：</span>
								<span class="modal-custom-info2-value">${affairCorrespondent.remarks}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">通讯员等级评定：</span>
								<span class="modal-custom-info2-value">${fns:getDictLabel(affairCorrespondent.star, "correspondent_star","")}</span>
							</div>
						</div>
					</div>



					<div class="modal-custom-info2-row" style="margin-top: 20px;">
						<span class="modal-custom-info2-key" >稿件相关：</span>
						<table id="contentTableC" class="table table-striped table-bordered table-condensed">
							<thead>
							<tr>
								<th>序号</th>
								<th>稿件标题</th>
								<th>稿件刊发时间</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${affairNews}" var="affairNews" varStatus="status">
								<tr>
									<td>${status.index+1}</td>
									<td>${affairNews.title}</td>
									<td><fmt:formatDate value="${affairNews.date}" pattern="yyyy-MM-dd"/></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
					<div>
						<span>附件：</span>
						<c:forEach items="${filePathList}" var="m" varStatus="status">
							<div>
								<span>${m.fileName}</span>
									<%--<a href="#">在线预览</a>--%>
								<a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="modal-custom-info1-bottom">
					<div id="print" class="modal-custom-info1-btn red">打印</div>
				</div>

			</div>
		</div>
	</div>
</div>