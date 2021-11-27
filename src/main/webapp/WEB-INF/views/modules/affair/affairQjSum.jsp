<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>请假信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("[data-toggle='popover']").popover();
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function openForm(url) {
            top.$.jBox.open("iframe:"+url, "请假申请单",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairQj"}
            });
        }
        //销假单页面弹窗
        function openFormCancel(url) {
            top.$.jBox.open("iframe:"+url, "销假单",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairQj"}
            });
        }
        //审核居处弹窗
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairQj/shenHeDialog?id="+id, "请假信息审核(局处)",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairQj/";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
        //审核部门弹窗
        function openDialog1(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairQj/depShenHeDialog?id="+id, "请假信息审核(部门)",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairQj/";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
        //审核人事弹窗
        function openDialog2(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairQj/hrShenHeDialog?id="+id, "请假信息审核(人事)",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/affair/affairQj/";
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
<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQjSum/list" method="post" class="breadcrumb form-search">
<%--    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="text-align: center">序号</th>
        <th style="text-align: center">姓名</th>
        <th style="text-align: center">部门</th>
        <th style="text-align: center">假别</th>
        <th style="text-align: center">休假开始时间</th>
        <th style="text-align: center">休假结束时间</th>
        <th style="text-align: center">实际休假天数</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairQj" varStatus="status">
        <tr>
            <td style="text-align: center">${(page.pageNo-1)*page.pageSize+status.index+1}</td>
            <td style="text-align: center">
                    ${affairQj.name}
            </td>
            <td style="text-align: center">
                    ${affairQj.unit}
            </td>
            <td style="text-align: center">
                    ${fns:getDictLabel(affairQj.type, 'personnel_xjtype', '')}
            </td>
            <td style="text-align: center">
                <fmt:formatDate value="${affairQj.startTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td style="text-align: center">
                <fmt:formatDate value="${affairQj.endTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td style="text-align: center">
                    ${affairQj.qjDay}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<div class="pagination">${page}</div>--%>
</body>
</html>