<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script>
	function openlDialog(url,name) {
		var url = "iframe:"+url;
		top.$.jBox.open(url, name,1000,600,{
			buttons:{"关闭":true},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	};
</script>
<!-- 人员信息导航 -->
<div id="modalNav">
	<div class="modal-custom">
		<div class="modal-custom-main">
				<div class="modal-custom-main-title">人员详细信息详情-${personnelBase.name}</div>
				<div class="modal-custom-content">
						<table id="modalPeoTb" class="table table-striped table-bordered table-condensed">
							<tbody>
								<c:forEach items="${list}" var="item" varStatus="status">
                                    <c:if test="${status.index%6==0&&status.index!=0}">
                                        </tr>
                                    </c:if>
									<c:if test="${status.index%6==0}">
										<tr>
									</c:if>
										<td><a href="javascript:void(0)" onclick="openlDialog('${ctx}${item.url}?idNumber=${personnelBase.idNumber}&mType=${mType}','${item.name}')">${item.name}(${item.totalnum})</a></td>
                                    <c:if test="${status.index==list.size()-1}">
                                        <td colspan="${5-status.index%6}"></td>
                                        </tr>
                                    </c:if>
								</c:forEach>
							</tbody>
						</table>
				</div>
		</div>
	</div>
</div>
