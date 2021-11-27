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
                        $("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/");
                    }
                    if (v == 'part') {
                        $("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/export?flag=true")
                        $("#searchForm").submit();
                        $("#searchForm").attr("action","${ctx}/affair/affairPoliceHome/");
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
            top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairPoliceHome", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairPoliceHome"}});
        });

    });
    function openForm(url) {
        top.$.jBox.open("iframe:"+url, "民警小家建设",800,520,{
            buttons:{"关闭":true},
            loaded:function(){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },closed:function (){self.location.href="${ctx}/affair/affairPoliceHome/formDetail?id=${affairPoliceHome.id}"}
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
                    <form:form id="searchForm" modelAttribute="affairPoliceHome" action="${ctx}/affair/affairPoliceHome/" method="post" class="breadcrumb form-search">
                        <input id="fileName" name="fileName" type="hidden" value="民警小家建设管理表.xlsx"/>
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">建设时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate value="${affairPoliceHome.pointDate}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">所属单位：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.unit}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">小家建设名称：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.pointUnit}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">建设项目：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairPoliceHome.project, 'affair_jsxm', '')}</span>
                            </div>
                        </div>
                       <%-- <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">内容：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.content}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">经办人：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.jingBan}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">申报单位审核人：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.unitShRen}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">处工会审核人：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.chuShOpinion}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">局工会审核人：</span>
                                <span class="modal-custom-info2-value">${affairPoliceHome.juShOpinion}</span>
                            </div>
                        </div>--%>
                        </form:form>
                        <table id="cen" class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>所需设备</th>
                                <th>建设数量</th>
                                <th>单价(元)</th>
                                <th>小计(元)</th>
                                <th>内容</th>
                                <th>经办人</th>
                                <th>申报单位审核人</th>
                                <th>处工会审核人</th>
                                <th>局工会审核人</th>
                                <%--<shiro:hasPermission name="affair:affairPoliceHome:edit">
                                    <th id="handleTh">操作</th>
                                </shiro:hasPermission>--%>
                            </tr>
                            <c:forEach items="${affairPoliceHome.policeHomeChildList}" var="m" >
                            <tr>
                                <td>${m.device}</td>
                                <td>${m.nums}</td>
                                <td>${m.price}</td>
                                <td>${m.sum}</td>
                                <td>${m.content}</td>
                                <td>${m.jingBan}</td>
                                <td>${m.unitShRen}</td>
                                <td>${m.chuShOpinion}</td>
                                <td>${m.juShOpinion}</td>
                                <%--<shiro:hasPermission name="affair:affairPoliceHome:edit">
                                    <td class="handleTd">
                                        <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairPoliceHome/formInDetail?id=${m.id}')">修改</a>
                                        <a href="${ctx}/affair/affairPoliceHome/delete?id=${m.id}&typeId=${affairPoliceHome.id}" onclick="return confirmx('确认要删除该民警小家建设吗？', this.href)">删除</a>
                                    </td>
                                </shiro:hasPermission>--%>
                            </tr>
                            </c:forEach>
                        </table>
                </div>
                <div class="modal-custom-info1-bottom">
<%--                    <div id="rewrite" class="modal-custom-info1-btn red" onclick="window.location.href='${ctx}/affair/affairPoliceHome/formDetail?id=${affairPoliceHome.id}'">重置</div>--%>
<%--                    <div id="input" class="modal-custom-info1-btn red" onclick="openForm('${ctx}/affair/affairPoliceHome/formInDetail?id=${affairPoliceHome.id}')">添加</div>--%>
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
<%--                    <div id="export" class="modal-custom-info1-btn red">导出</div>--%>
<%--                    <div id="btnImport" class="modal-custom-info1-btn red">导入</div>--%>
                </div>
            </div>
        </div>
    </div>
</div>