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
					<ul class="nav nav-tabs">
						<shiro:hasPermission name="affair:affairCourseResource:view">
							<li class="active">
								<a href="${ctx}/affair/affairCourseResource/formDetail?id=${affairCourseResource.id}">基本信息</a>
							</li>
							<li>
								<a href="${ctx}/affair/affairCourseResource/SwfForm?id=${affairCourseResource.id}">课件上传</a>
							</li>
							<li>
								<a href="${ctx}/affair/affairCourseResource/ZSForm?id=${affairCourseResource.id}">知识点设置</a>
							</li>
							<li>
								<a  href="${ctx}/affair/affairCourseResource/GXForm?id=${affairCourseResource.id}">共享设置</a>
							</li>

						</shiro:hasPermission>
					</ul>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程名称：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.name}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程编码：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.code}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程类型：</span>
								<span class="modal-custom-info2-value" style="width: 105px;">${fns:getDictLabel(affairCourseResource.type, 'swf_type', '')}</span>
							</div>
						</div>
					</div>


					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程标签：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.label}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">学时：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.time}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">辅导，授权讲师：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.teacher}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">是否为公开课：</span>
								<span class="modal-custom-info2-value" style="width: 105px;">${fns:getDictLabel(affairCourseResource.state, 'open_state', '')}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程分类：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.classify}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程语言：</span>
								<span class="modal-custom-info2-value" style="width: 105px;">${fns:getDictLabel(affairCourseResource.language, 'language_classify', '')}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">作者：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.author}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">制作单位：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.unit}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程简介：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.description}</span>
							</div>
						</div>
					</div>
					<div class="modal-custom-info2">
						<div class="modal-custom-info2-col modal-custom-info2-col1">
							<div class="modal-custom-info2-row">
								<span class="modal-custom-info2-key">课程封面：</span>
								<span class="modal-custom-info2-value">${affairCourseResource.adjunct}</span>
							</div>
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