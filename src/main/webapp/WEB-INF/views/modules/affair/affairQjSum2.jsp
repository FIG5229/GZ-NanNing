<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>请假信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#export").click(

                function(){
                    dataExport('contentTable');
                }
            );
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //明细弹窗
        function openDialog(id,type) {
            var url = "iframe:${ctx}/affair/affairQjSum/list?unitId="+id+"&modelType="+type;
            if (type == null || type == undefined){
                url = "iframe:${ctx}/affair/affairQjSum/list?unitId="+id;
            }
            top.$.jBox.open(url, "请假明细",1000,500,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }

    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairQj" action="${ctx}/affair/affairQjSum/findByUnitId" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="unitId" name="unitId" type="hidden" value="${affairQjSum.unitId}"/>
    <ul class="ul-form">
        <li><label class="width140">请假开始时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairQj.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairQj.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairQjSum/list'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="text-align: center">单位</th>
        <th style="text-align: center">年休假</th>
        <th style="text-align: center">探亲假</th>
        <th style="text-align: center">婚假</th>
        <th style="text-align: center">病假</th>
        <th style="text-align: center">陪产假</th>
        <th style="text-align: center">产假</th>
        <th style="text-align: center">丧假</th>
        <th style="text-align: center">事假</th>
        <th style="text-align: center">工伤</th>
        <th style="text-align: center">出差</th>
        <th style="text-align: center">联系工作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic}" var="affairQjSum" varStatus="status">
        <tr>
            <td style="text-align: center">${affairQjSum.unitName}</td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',1)">${affairQjSum.nianXiu}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',2)">${affairQjSum.tanQin}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',3)">${affairQjSum.hun}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',4)">${affairQjSum.bing}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',5)">${affairQjSum.peiCan}
                </a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',6)">${affairQjSum.can}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',7)">${affairQjSum.sang}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',8)">${affairQjSum.shi}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',9)">${affairQjSum.gongShang}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',10)">${affairQjSum.chucai}</a>
            </td>
            <td style="text-align: center">
                <a onclick="openDialog('${affairQjSum.unitId}',11)">${affairQjSum.lianXi}</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>