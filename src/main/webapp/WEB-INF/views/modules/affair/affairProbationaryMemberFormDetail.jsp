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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false
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
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span><span class="modal-custom-info2-value">${affairProbationaryMember.name}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">身份证号：</span><span class="modal-custom-info2-value">${affairProbationaryMember.idNumber}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">警号：</span><span class="modal-custom-info2-value">${affairProbationaryMember.policeNo}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairProbationaryMember.sex, 'sex', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所在党支部：</span><span class="modal-custom-info2-value">${affairProbationaryMember.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">人员类别：</span><span class="modal-custom-info2-value">${fns:getDictLabel(affairProbationaryMember.type, 'affair_personnel_category', '')}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学历：</span><span class="modal-custom-info2-value">${affairProbationaryMember.education}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">加入党组织日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairProbationaryMember.joinDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">转为正式党员日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairProbationaryMember.turnDate}" pattern="yyyy-MM-dd"/></span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                        </div>
                    </div>

                </div>
                <%--              <div class="modal-custom-info1-file">
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
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>