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
                removeInline: false,
                printDelay: 333,
                header: null,
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
        top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
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
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训年度：</span><span class="modal-custom-info2-value">${affairEducationTrain.trainYear}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">填报人：</span><span class="modal-custom-info2-value">${affairEducationTrain.informant}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已填报的计划培训班总数：<br></span><span class="modal-custom-info2-value">${affairEducationTrain.filledClassCount}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已审批通过的计划班培训数量：<br></span><span class="modal-custom-info2-value">${affairEducationTrain.incompleteApprovalCount}</span></div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">填报机构：</span><span class="modal-custom-info2-value">${affairEducationTrain.unit}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">最后一次填报日期：</span><span class="modal-custom-info2-value"><fmt:formatDate value="${affairEducationTrain.lastDate}" pattern="yyyy-MM-dd"/></span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">未完成的计划班培训数量：<br></span><span class="modal-custom-info2-value">${affairEducationTrain.approvedClassCount}</span></div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已审批通过的计划班培训总人数：<br></span><span class="modal-custom-info2-value">${affairEducationTrain.approvedCount}</span></div>
                        </div>
                    </div>
                    <br>
                    <div class="modal-custom-info2-row">
                        <span class="modal-custom-info2-key">已审批通过培训班列表：</span>
                        <br>
                        <sys:message content="${message}"/>
                        <table id="contentTableM" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选</th>
                                <th>序号</th>
                                <th>名称</th>
                                <th>培训班类型</th>
                                <th>培训层次</th>
                                <th>培训天数</th>
                                <th>培训人数</th>
                                <th>实施状态</th>
                                    <shiro:hasPermission name="affair:affairClassManage:edit"><th>操作</th></shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${affairClassManage}" var="affairClassManage" varStatus="status">
                                <tr>
                                    <td class="checkTd">
                                        <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairClassManage.id}"/>
                                    </td>
                                    <td>
                                            ${(page.pageNo-1)*page.pageSize+status.index+1}
                                    </td>
                                    <td>
                                            ${affairClassManage.name}
                                    </td>
                                    <td>
                                            ${affairClassManage.type}
                                    </td>
                                    <td>
                                            ${affairClassManage.level}
                                    </td>
                                    <td>
                                            ${affairClassManage.trainDay}
                                    </td>
                                    <td>
                                            ${affairClassManage.count}
                                    </td>
                                    <td>
                                            ${affairClassManage.status}
                                    </td>
                                    <td class="handleTd">
                                        <a href="javascript:;" onclick="openDetailDialog('${affairClassManage.id}')">查看</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


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