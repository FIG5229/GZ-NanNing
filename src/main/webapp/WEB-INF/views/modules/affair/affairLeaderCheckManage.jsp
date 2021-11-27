<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>领导干部年度考核表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            //这是打印功能的JS
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
                            $("#searchForm").attr("action","${ctx}/affair/affairLeaderCheck/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairLeaderCheck/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairLeaderCheck/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairLeaderCheck/");
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
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairLeaderCheck", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairLeaderCheck"}});
            });
            $("[data-toggle='popover']").popover();
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //添加function,用于打开form添加页面弹窗
        function openForm(url) {
            top.$.jBox.open("iframe:"+url, "领导干部年度考核",800,520,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairLeaderCheck"}
            });
        }
        //批量提交
        function submitByIds() {
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                console.log($(this).val())
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                $.ajax({
                    type:"post",
                    url:"${ctx}/affair/affairLeaderCheck/submitByIds",
                    data:{ids:ids},
                    dataType:"json",
                    success:function(data){
                        $.jBox.tip(data.message);
                        resetCheckBox("checkAll","myCheckBox");
                        location.reload();
                    }
                })
            } else {
                $.jBox.tip('请先选择要提交的内容');
            }
        };
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairLeaderCheck/formDetail?id="+id;
            top.$.jBox.open(url, "领导干部年度考核详情",800,500,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
        //审核弹窗
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairLeaderCheck/shenHeDialog?id="+id, "领导干部年度审核",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairLeaderCheck/";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
        $('#notPass').popover();
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li ><a href="${ctx}/affair/affairLeaderCheck">领导干部年度考核</a></li>
    <li class="active"><a href="${ctx}/affair/affairLeaderCheck/manageList">领导干部年度考核(审核)</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairLeaderCheck" action="${ctx}/affair/affairLeaderCheck/manageList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="领导干部年度考核.xlsx"/>
    <ul class="ul-form">
        <li><label class="width120">领领导干部名称：</label>
            <form:input path="name" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>诉职年度：</label>

            <input name="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${affairLeaderCheck.date}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>
        <li><label>测评等次：</label>
            <form:select path="grade" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('assessment_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" data-url="" value="查询"/></li>
        <shiro:hasPermission name="affair:affairLeaderCheck:edit">
            <li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairLeaderCheck/form')"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairLeaderCheck/deleteByIds','checkAll','myCheckBox')"/></li>
            <li class="btns"><input class="btn btn-primary" type="button" value="提交" onclick="submitByIds()"/></li>
            <shiro:hasPermission name="affair:affairLeaderCheck:manage">
                <li class="btns"><input  class="btn btn-primary" type="button" value="审核" onclick="openDialog('myCheckBox')"/></li>
            </shiro:hasPermission>
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
        </shiro:hasPermission>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairLeaderCheck'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
        <th>序号</th>
        <th>领领导干部名称</th>
        <th>单位</th>
        <th>诉职年度</th>
        <th>测评时间</th>
        <th>参加测评人数</th>
        <th>测评等次</th>
        <th>审核状态</th>
        <shiro:hasPermission name="affair:affairLeaderCheck:edit"><th id="handleTh">操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairLeaderCheck" varStatus="status">
        <tr>
            <td>
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairLeaderCheck.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairLeaderCheck.name}
           </td>
            <td>
                    ${affairLeaderCheck.unit}
            </td>
            <td>
                    ${affairLeaderCheck.date}
            </td>
            <td>
                <fmt:formatDate value="${affairLeaderCheck.gradeDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairLeaderCheck.num}
            </td>
            <td>
                    ${fns:getDictLabel(affairLeaderCheck.grade, 'assessment_level', '')}
            </td>
            <td>
                <c:choose>
                    <c:when test="${affairLeaderCheck.status == '3'}">
                        <a id="notPass" data-container="body" data-toggle="popover" data-placement="right"
                           data-content="${affairLeaderCheck.opinion}"  style="cursor: pointer;color: red">不通过</a>
                    </c:when>
                    <c:otherwise>
                        ${fns:getDictLabel(affairLeaderCheck.status, 'current_state', '')}
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="handleTd">
                <a onclick="openDetailDialog('${affairLeaderCheck.id}')">查看 </a>
            <shiro:hasPermission name="affair:affairLeaderCheck:edit">
                <c:if test="${affairLeaderCheck.createBy.id == fns:getUser().id}">
                    <a href="javascript:void(0)"
                       onclick="openForm('${ctx}/affair/affairLeaderCheck/form?id=${affairLeaderCheck.id}')">修改</a>
                    <a href="${ctx}/affair/affairLeaderCheck/delete?id=${affairLeaderCheck.id}"
                       onclick="return confirmx('确认要删除该领导干部年度考核表吗？', this.href)">删除</a>
                </c:if>
            </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>