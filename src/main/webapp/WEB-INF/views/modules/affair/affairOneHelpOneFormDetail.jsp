<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#export").click(
            function(){
                var submit = function (v, h, f) {
                    if (v == 'all') {
                        $("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/");
                    }
                    if (v == 'part') {
                        $("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/export?flag=true")
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairOneHelpOne/");
                    }
                    if (v == 'cancel') {
                        $.jBox.tip('已取消');
                    }
                    return true;
                };
                $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
            }
        );
        $("#btnImport").click(function(){
            top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairOneHelpOne", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairOneHelpOne"}});
        });

    })
    function openForm(url) {
        top.$.jBox.open("iframe:"+url, "一帮一",800,520,{
            buttons:{"关闭":true},
            loaded:function(){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },closed:function (){self.location.href="${ctx}/affair/affairOneHelpOne/formDetail?id=${affairOneHelpOne.id}"}
        });
    }
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
            $('#handleTh').css('display', 'none');
            $('.handleTd').css('display', 'none');
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
                afterPrint:function (){
                    $('.download').css('display', '');
                    $('#handleTh').css('display', 'none');
                    $('.handleTd').css('display', 'none');
                }
            });
        });
    });
</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div class="modal-custom-content" id="contentTable">
                    <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">标题：</span><span
                            class="modal-custom-info2-value">${affairOneHelpOne.title}</span></div>
                    <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span
                            class="modal-custom-info2-value">${affairOneHelpOne.unit}</span></div>
                    <%-- <form:form id="searchForm" modelAttribute="affairOneHelpOne" action="${ctx}/affair/affairOneHelpOne/" method="post" class="breadcrumb form-search">
                        <input id="fileName" name="fileName" type="hidden" value="全局“一帮一”重困民警慰问情况.xlsx"/>
                        <div class="modal-custom-info2">
                            <div class="control-group">
                                <label class="control-label">标题：</label>
                                <div class="controls">
                                    ${affairOneHelpOne.title}
                                    &lt;%&ndash;<form:input path="title" htmlEscape="false" class="input-xlarge required"/>&ndash;%&gt;
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">单位：</label>
                                <div class="controls">
                                        ${affairOneHelpOne.title}
                                    &lt;%&ndash;<form:input path="unit" htmlEscape="false" class="input-xlarge required"/>
                                    <span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;
                                </div>
                            </div>
                        </div>
                    </form:form>--%>
                    <table id="cen" class="table table-striped table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>帮扶人姓名</th>
                            <th>职务</th>
                            <th>被帮扶姓名</th>
                            <th>单位职务</th>
                            <th>被帮扶人情况</th>
                            <th>被帮扶人住址</th>
                            <th>帮扶金额</th>
                            <th>联系电话</th>
                            <th>慰问时间</th>
                            <%--<shiro:hasPermission name="affair:affairOneHelpOne:edit">
                                <th id="handleTh">操作</th>
                            </shiro:hasPermission>--%>
                        </tr>
                        <c:forEach items="${page.list}" var="m" >
                        <tr>
                            <td>${m.name}</td>
                            <td>${m.job}</td>
                            <td>${m.beName}</td>
                            <td>${m.unitJob}</td>
                            <td>${m.situation}</td>
                            <td>${m.address}</td>
                            <td>${m.money}</td>
                            <td>${m.tel}</td>
                            <td><fmt:formatDate value="${m.date}" pattern="yyyy-MM-dd"/></td>
                            <%--<shiro:hasPermission name="affair:affairOneHelpOne:edit">
                                <td class="handleTd">
                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairOneHelpOneMain/form?id=${m.id}')">修改</a>
                                    <a href="${ctx}/affair/affairOneHelpOneMain/delete?id=${m.id}&mainId=${affairOneHelpOne.id}" onclick="return confirmx('确认要删除该内容吗？', this.href)">删除</a>
                                </td>
                            </shiro:hasPermission>--%>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
                 <div id="print" class="modal-custom-info1-btn red">打印</div>
          <%--  <div class="modal-custom-info1-bottom">
                <shiro:hasPermission name="affair:affairOneHelpOne:edit">
                    <div id="rewrite" class="modal-custom-info1-btn red" onclick="window.location.href='${ctx}/affair/affairOneHelpOne/formDetail?id=${affairOneHelpOne.id}'">重置</div>
                    <div id="input" class="modal-custom-info1-btn red" onclick="openForm('${ctx}/affair/affairOneHelpOneMain/form?mainId=${affairOneHelpOne.id}')">添加</div>
                </shiro:hasPermission>
                <div id="export" class="modal-custom-info1-btn red">导出</div>
                <div id="btnImport" class="modal-custom-info1-btn red">导入</div>
            </div>--%>
        </div>
    </div>
</div>