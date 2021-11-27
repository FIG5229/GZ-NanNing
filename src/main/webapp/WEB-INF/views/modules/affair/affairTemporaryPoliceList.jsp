<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>临时抽调民警管理</title>
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
					afterPrint:function (){
						$('#handleTh').css('display', 'table-cell');
						$('.handleTd').css('display', 'table-cell');
					}
				});
			});
			//导出功能的JS
			$("#export").click(
					function(){
						var submit = function (v, h, f) {
							if (v == 'all') {
								$("#searchForm").attr("action","${ctx}/affair/affairTemporaryPolice/export");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTemporaryPolice/");
							}
							if (v == 'part') {
								$("#searchForm").attr("action","${ctx}/affair/affairTemporaryPolice/export?flag=true");
								$("#searchForm").submit();
								$("#searchForm").attr("action","${ctx}/affair/affairTemporaryPolice/");
							}
							if (v == 'cancel') {
								$.jBox.tip('已取消');
							}
							return true;
						};
						$.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
					}
			);
			//导入功能的JS
			$("#btnImport").click(function(){
				top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTemporaryPolice", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTemporaryPolice"}});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function openForm(url) {
			top.$.jBox.open("iframe:"+url, "临时抽调民警管理",800,520,{
				buttons:{"关闭":true},
				loaded:function(){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},closed:function (){self.location.href="${ctx}/affair/affairTemporaryPolice"}
			});
		}
		//详情弹窗
		function openDetailDialog(id) {
			var url = "iframe:${ctx}/affair/affairTemporaryPolice/formDetail?id="+id;
			top.$.jBox.open(url, "临时抽调民警详情",800,500,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
        }
        //审核功能
		function submitByIds() {
			if(null == $("#oneCheckManName").val() || "" ==  $("#oneCheckManName").val()){
				$.jBox.tip('请选择审核单位');
				return false;
			}
			var ids = [];
			$("input:checkbox[name=myCheckBox]:checked").each(function () {
				console.log($(this).val());
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
        function openDialog(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTemporaryPolice/shenHeDialog?id="+id,"临时抽调民警",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairTemporaryPolice/list/?repage";
				}
			});
        }

		function openCheck(id) {
			top.$.jBox.open("iframe:${ctx}/affair/affairTemporaryPolice/check?id="+id,"临时抽调民警",800,420,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
				closed:function (){
					self.location.href="${ctx}/affair/affairTemporaryPolice/list/?repage";
				}
			});
		}
    </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/affair/affairTemporaryPolice/">临时抽调民警管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="affairTemporaryPolice" action="${ctx}/affair/affairTemporaryPolice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="fileName" name="fileName" type="hidden" value="临时抽调民警.xlsx"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>所属单位：</label>
				<sys:treeselect id="ofUnit" name="ofUnitId" value="${affairTemporaryPolice.ofUnitId}" labelName="ofUnit" labelValue="${affairTemporaryPolice.ofUnit}"
					title="所属单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>抽调单位：</label>
				<sys:treeselect id="cdUnit" name="cdUnitId" value="${affairTemporaryPolice.cdUnitId}" labelName="cdUnit" labelValue="${affairTemporaryPolice.cdUnit}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
		</ul>
		<ul class="ul-form2">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="affair:affairTemporaryPolice:edit">
				<li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTemporaryPolice/form')"/></li>
				<li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTemporaryPolice/deleteByIds','checkAll','myCheckBox')"/></li>
				<li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			</shiro:hasPermission>
			<li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
			<li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTemporaryPolice'"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
				<th>序号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>所属单位</th>
				<th>抽调单位</th>
				<th>抽调时间</th>
				<th>截至时间</th>
				<th>状态</th>
				<th id="handleTh">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="affairTemporaryPolice" varStatus="status">
			<c:choose>
				<c:when test="${fns:getUser().office.id == affairTemporaryPolice.ofUnitId}">
					<c:if test="${ '11' eq affairTemporaryPolice.checkType }">
						<tr>
							<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTemporaryPolice.id}"/></td>
							<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
							<td>
									${affairTemporaryPolice.name}
							</td>
							<td>
									${fns:getDictLabel(affairTemporaryPolice.sex, 'sex', '')}
							</td>
							<td>
									${affairTemporaryPolice.ofUnit}
							</td>
							<td>
									${affairTemporaryPolice.cdUnit}
							</td>
							<td>
								<fmt:formatDate value="${affairTemporaryPolice.cdDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								<fmt:formatDate value="${affairTemporaryPolice.endDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
									${fns:getDictLabel(affairTemporaryPolice.checkType, 'temporary_type', '')}
							</td>
							<td class="handleTd">
								<a href="javascript:void(0)" onclick="openDetailDialog('${affairTemporaryPolice.id}')">查看</a>
								<shiro:hasPermission name="affair:affairTemporaryPolice:edit">
									<c:if test="${affairTemporaryPolice.createBy.id == fns:getUser().id}">
										<c:if test="${affairTemporaryPolice.checkType == '0'}">
											<a href="javascript:void(0)"
											   onclick="openForm('${ctx}/affair/affairTemporaryPolice/form?id=${affairTemporaryPolice.id}')">修改</a>
											<a href="${ctx}/affair/affairTemporaryPolice/delete?id=${affairTemporaryPolice.id}"
											   onclick="return confirmx('确认要删除该临时抽调民警管理吗？', this.href)">删除</a>
										</c:if>
									</c:if>
								</shiro:hasPermission>
								<c:choose>
									<c:when test="${fns:getUser().id == affairTemporaryPolice.createBy.id }">
										<c:if test="${('bfdf74f010c9466dba12c1589ecab7f3' != fns:getUser().id && '34e8d855cf6b4b1ab5e7e23e7aaba658' != fns:getUser().id && 'ec3ba2efdd404f2faa520f6e8a71ec4c' != fns:getUser().id && 'c90918faf2614baa8fa85230482bd43e' != fns:getUser().id)}">
											<c:if test="${affairTemporaryPolice.checkType == '0' || affairTemporaryPolice.checkType == '4' || affairTemporaryPolice.checkType == '5' || affairTemporaryPolice.checkType == '7'}">
												<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">提交送审</a>
											</c:if>
										</c:if>
									</c:when>
									<c:when test="${ isLd == true && (affairTemporaryPolice.checkType == '6')}">
										<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">推送</a>
									</c:when>
									<c:when test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e' eq fns:getUser().id) && (affairTemporaryPolice.checkType == '10' || affairTemporaryPolice.checkType == '9')}">
										<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">提交送审</a>
									</c:when>
								</c:choose>

								<shiro:hasPermission name="affair:affairTemporaryPolice:manage">
									<c:choose>
										<c:when test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e')}">
											<c:if test="${affairTemporaryPolice.checkType == '1'}">
												<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
											</c:if>
										</c:when>
										<c:when test="${affairTemporaryPolice.checkType == '2'}">
											<c:if test="${isLd == true  && (affairTemporaryPolice.threeCheckId == '' || affairTemporaryPolice.threeCheckId == null)}">
												<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
											</c:if>
										</c:when>
										<c:when test="${  affairTemporaryPolice.checkType == '3' && affairTemporaryPolice.threeCheckId eq fns:getUser().id}">
											<c:if test="${isLd == true}">
												<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
											</c:if>
										</c:when>
									</c:choose>
									<%--<c:if test="${affairTemporaryPolice.checkType == '1' || affairTemporaryPolice.checkType == '2' || affairTemporaryPolice.checkType == '3'}">
                                        <a onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
                                    </c:if>--%>
								</shiro:hasPermission>
								<c:if test="${'11' == affairTemporaryPolice.checkType}">
									<c:if test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e' eq fns:getUser().id)}">
									<a href="${ctx}/affair/affairTemporaryPolice/returnUnit?id=${affairTemporaryPolice.id}"
									   onclick="return confirmx('确认要退回原单位吗？', this.href)">退回</a>
									</c:if>
								</c:if>

								<c:choose>
									<%--抽调单位--%>
									<c:when test="${(affairTemporaryPolice.checkType eq '1' or affairTemporaryPolice.checkType eq '2') && ((fns:getUser().office.id eq affairTemporaryPolice.cdUnitId and isPerson != true ) or fns:getUser().id eq affairTemporaryPolice.createBy.id)}">
										<a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=1" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
									</c:when>
									<%--组干--%>
									<c:when test="${(affairTemporaryPolice.checkType eq '3' or affairTemporaryPolice.checkType eq '4' or affairTemporaryPolice.checkType eq '5') && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e')}">
										<a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=2" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
									</c:when>
									<%--分管领导--%>
									<c:when test="${(isPerson == true and isLd == true) && (affairTemporaryPolice.checkType eq '10' or affairTemporaryPolice.checkType eq '7')}">
										<a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=3" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
									</c:when>
									<%--特殊情况--0为未审核--%>
									<c:when test="${affairTemporaryPolice.checkType == '0'}">

									</c:when>
									<c:otherwise>

									</c:otherwise>
								</c:choose>


							</td>
						</tr>
					</c:if>
				</c:when>
				<c:otherwise>
					<tr>
						<td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTemporaryPolice.id}"/></td>
						<td>${(page.pageNo-1)*page.pageSize+status.index+1}</td>
						<td>
								${affairTemporaryPolice.name}
						</td>
						<td>
								${fns:getDictLabel(affairTemporaryPolice.sex, 'sex', '')}
						</td>
						<td>
								${affairTemporaryPolice.ofUnit}
						</td>
						<td>
								${affairTemporaryPolice.cdUnit}
						</td>
						<td>
							<fmt:formatDate value="${affairTemporaryPolice.cdDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${affairTemporaryPolice.endDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
								${fns:getDictLabel(affairTemporaryPolice.checkType, 'temporary_type', '')}
						</td>
						<td class="handleTd">
							<a href="javascript:void(0)" onclick="openDetailDialog('${affairTemporaryPolice.id}')">查看</a>
							<shiro:hasPermission name="affair:affairTemporaryPolice:edit">
								<c:if test="${affairTemporaryPolice.createBy.id == fns:getUser().id}">
									<c:if test="${affairTemporaryPolice.checkType == '0'}">
										<a href="javascript:void(0)"
										   onclick="openForm('${ctx}/affair/affairTemporaryPolice/form?id=${affairTemporaryPolice.id}')">修改</a>
										<a href="${ctx}/affair/affairTemporaryPolice/delete?id=${affairTemporaryPolice.id}"
										   onclick="return confirmx('确认要删除该临时抽调民警管理吗？', this.href)">删除</a>
									</c:if>
								</c:if>
							</shiro:hasPermission>
							<c:choose>
								<c:when test="${fns:getUser().id == affairTemporaryPolice.createBy.id }">
									<c:if test="${('bfdf74f010c9466dba12c1589ecab7f3' != fns:getUser().id && '34e8d855cf6b4b1ab5e7e23e7aaba658' != fns:getUser().id && 'ec3ba2efdd404f2faa520f6e8a71ec4c' != fns:getUser().id && 'c90918faf2614baa8fa85230482bd43e' != fns:getUser().id)}">
										<c:if test="${affairTemporaryPolice.checkType == '0' || affairTemporaryPolice.checkType == '4' || affairTemporaryPolice.checkType == '5' || affairTemporaryPolice.checkType == '7'}">
											<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">提交送审</a>
										</c:if>
									</c:if>
								</c:when>
								<c:when test="${ isLd == true && (affairTemporaryPolice.checkType == '6')}">
									<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">推送</a>
								</c:when>
								<c:when test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e' eq fns:getUser().id ) && (affairTemporaryPolice.checkType == '10' || affairTemporaryPolice.checkType == '9')}">
									<a href="javascript:void(0)" onclick="openCheck('${affairTemporaryPolice.id}')">提交送审</a>
								</c:when>
							</c:choose>

							<shiro:hasPermission name="affair:affairTemporaryPolice:manage">
								<c:choose>
									<c:when test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e' eq fns:getUser().id)}">
										<c:if test="${affairTemporaryPolice.checkType == '1'}">
											<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
										</c:if>
									</c:when>
									<c:when test="${affairTemporaryPolice.checkType == '2'}">
										<c:if test="${isLd == true  && (affairTemporaryPolice.threeCheckId == '' || affairTemporaryPolice.threeCheckId == null)}">
											<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
										</c:if>
									</c:when>
									<c:when test="${  affairTemporaryPolice.checkType == '3' && affairTemporaryPolice.threeCheckId eq fns:getUser().id}">
										<c:if test="${isLd == true}">
											<a href="javascript:void(0)" onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
										</c:if>
									</c:when>
								</c:choose>
								<%--<c:if test="${affairTemporaryPolice.checkType == '1' || affairTemporaryPolice.checkType == '2' || affairTemporaryPolice.checkType == '3'}">
                                    <a onclick="openDialog('${affairTemporaryPolice.id}')">审核</a>
                                </c:if>--%>
							</shiro:hasPermission>
							<c:if test="${'11' == affairTemporaryPolice.checkType}">
								<c:if test="${('bfdf74f010c9466dba12c1589ecab7f3' eq fns:getUser().id || '34e8d855cf6b4b1ab5e7e23e7aaba658' eq fns:getUser().id || 'ec3ba2efdd404f2faa520f6e8a71ec4c' eq fns:getUser().id || 'c90918faf2614baa8fa85230482bd43e' eq fns:getUser().id)}">
									<a href="${ctx}/affair/affairTemporaryPolice/returnUnit?id=${affairTemporaryPolice.id}"
									   onclick="return confirmx('确认要退回原单位吗？', this.href)">退回</a>
								</c:if>
							</c:if>


                            <c:choose>
                                <%--抽调单位--%>
                                <c:when test="${(affairTemporaryPolice.checkType eq '1' or affairTemporaryPolice.checkType eq '2') && ((fns:getUser().office.id eq affairTemporaryPolice.cdUnitId and isPerson != true ) or fns:getUser().id eq affairTemporaryPolice.createBy.id)}">
                                    <a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=1" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
                                </c:when>
                                <%--组干--%>
                                <c:when test="${(affairTemporaryPolice.checkType eq '3' or affairTemporaryPolice.checkType eq '4' or affairTemporaryPolice.checkType eq '5') && (fns:getUser().id eq 'bfdf74f010c9466dba12c1589ecab7f3' || fns:getUser().id eq '34e8d855cf6b4b1ab5e7e23e7aaba658' || fns:getUser().id eq 'ec3ba2efdd404f2faa520f6e8a71ec4c' || fns:getUser().id eq 'c90918faf2614baa8fa85230482bd43e')}">
                                    <a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=2" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
                                </c:when>
								<%--分管领导--%>
								<c:when test="${(isPerson == true and isLd == true) && (affairTemporaryPolice.checkType eq '10' or affairTemporaryPolice.checkType eq '7')}">
									<a href="${ctx}/affair/affairTemporaryPolice/revocation?id=${affairTemporaryPolice.id}&flag=3" onclick="return confirmx('确认要撤销该临时抽调民警管理吗？', this.href)">撤销至上一步</a>
								</c:when>
								<%--特殊情况--0为未审核--%>
                                <c:when test="${affairTemporaryPolice.checkType == '0'}">

                                </c:when>
                                <c:otherwise>

                                </c:otherwise>
                            </c:choose>

						</td>
					</tr>
				</c:otherwise>
			</c:choose>

		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<%--<shiro:hasPermission name="affair:affairTemporaryPolice:edit">
	<form:form id="searchForm2" modelAttribute="affairTemporaryPolice" action="${ctx}/affair/affairTemporaryPolice/submitByIds" method="post" class="breadcrumb form-search">
		<ul class="ul-form" style="text-align: right">
			<font color="red">请选择评定单位：</font>
			<input type="hidden" name="ids" id="idsValue"/>
			<sys:treeselect id="oneCheckMan" name="oneCheckId" value="${affairTemporaryPolice.oneCheckId}"
							labelName="oneCheckMan" labelValue="${affairTemporaryPolice.oneCheckMan}"
							title="部门" url="/sys/office/treeData?type=3" allowClear="true"
							notAllowSelectParent="true" checked="true" isAll="true"/>

			<input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
		</ul>
	</form:form>
	</shiro:hasPermission>--%>
	<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;抽调单位->组干部门->分管领导->组干部门->分管领导<br>

	<%--	由抽调单位发起申请，提交至组织干部处审核（公安处抽人提交至组干室），不通过发回抽调单位，通过也发回抽调单位，由抽调单位推送给分管领导审核。<br>

		&nbsp;&nbsp;&nbsp;&nbsp;有同意、不同意、理由（理由栏为选填），不同意退回抽调单位（上一阶段），同意则推送给组干部门，由组干部门推送给分管领导进行审核（同上，局处分权限推送）。<br>

		&nbsp;&nbsp;&nbsp;&nbsp;分管领导审核，有同意、不同意、理由（理由栏为选填），不同意退回上一阶段，同意则发给被抽调人员单位及所在公安处组干室，以预警形式提醒组干室及被抽调单位签收。--%></font>
</body>
</html>