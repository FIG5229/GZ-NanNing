<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
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
            $("#contentTableM").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                header: null,
                removeInline: false,
                printDelay: 333,
                formValues: false
            });
        });
    });


    function page(n, s) {
        $("#pageNo").val(n);
        $("#pageSize").val(s);
        $("#searchForm").submit();
        return false;
    }
    function openDetailDialog(id) {
        top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训计划审核详情",1200,600,{
            buttons:{"关闭":true},
            loaded:function () {
                $(".jbox-content",top.document).css("overflow-y","hidden");
            },closed:function () {self.location.href="${ctx}/affair/affairClassManage"

            }
        });
    }

    if("success"=="${saveResult}"){
        parent.$.jBox.close();
    }
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训年度：</span><span class="modal-custom-info2-value">${affairTrainApprove.trainYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">填报人：</span><span class="modal-custom-info2-value">${affairTrainApprove.informant}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已填报的计划培训班总数：<br></span><span class="modal-custom-info2-value">${affairTrainApprove.filledClassCount}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已审批通过的计划班培训数量：<br></span><span class="modal-custom-info2-value">${affairTrainApprove.incompleteApprovalCount}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">填报机构：</span><span class="modal-custom-info2-value">${affairTrainApprove.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">提交审批日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairTrainApprove.approveDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">未完成的计划班培训数量：<br></span><span class="modal-custom-info2-value">${affairTrainApprove.approvedClassCount}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已审批通过的计划班培训总人数：<br></span><span class="modal-custom-info2-value">${affairTrainApprove.approvedCount}</span></div>
                        </div>
                    </div>
                    <br>
                    <div class="modal-custom-info1-file">
<%--                        <div class="modal-custom-info1-file-1">附件：</div>--%>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
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