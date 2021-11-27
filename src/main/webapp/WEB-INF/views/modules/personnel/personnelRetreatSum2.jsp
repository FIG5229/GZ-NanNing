<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>离退信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //明细弹窗
        function openDialog(id,type) {
            var url = "iframe:${ctx}/personnel/personnelRetreatSum/list?unitId="+id+"&modelType="+type;
            if (type == null || type == undefined){
                url = "iframe:${ctx}/personnel/personnelRetreatSum/list?unitId="+id;
            }
            top.$.jBox.open(url, "休养明细",1000,500,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreatSum/findByUnitId" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="unitId" name="unitId" type="hidden" value="${personnelRetreatSum.unitId}"/>
    <ul class="ul-form">
        <li><label>离退日期：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreatSum/findByUnitId'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>单位</th>
        <th>现员</th>
        <th>其中男</th>
        <th>其中女</th>
        <th>平均年龄</th>
        <th>55-60岁</th>
        <th>61-65岁</th>
        <th>66-70岁</th>
        <th>71-75岁</th>
        <th>76-80岁</th>
        <th>81岁以上</th>
        <th>离休</th>
        <th>退休</th>
        <th>合计</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${statistic}" var="personnelRetreatSum" varStatus="status">
        <tr>
            <td>${personnelRetreatSum.unitName}</td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',1)">${personnelRetreatSum.nowMan}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',2)">${personnelRetreatSum.man}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',3)">${personnelRetreatSum.woman}</a>
            </td>
            <td>
                ${personnelRetreatSum.avgAge}
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',5)">${personnelRetreatSum.age1}
                </a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',6)">${personnelRetreatSum.age2}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',7)">${personnelRetreatSum.age3}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',8)">${personnelRetreatSum.age4}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',9)">${personnelRetreatSum.age5}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',10)">${personnelRetreatSum.age6}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',11)">${personnelRetreatSum.liXiu}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',12)">${personnelRetreatSum.tuiXiu}</a>
            </td>
            <td>
                <a onclick="openDialog('${personnelRetreatSum.unitId}',13)">${personnelRetreatSum.nowMan}</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>