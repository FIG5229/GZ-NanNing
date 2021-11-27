<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>台账批量导出功能</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        /*勾选所有的复选框*/
        chooseAll = function (obj,checkBoxsName) {
            if (obj.checked) {
                var checkboxs = document.getElementsByName(checkBoxsName);
                for (var i = 0; i < checkboxs.length; i++) {
                    checkboxs[i].checked = true;
                }
            } else {
                var checkboxs = document.getElementsByName(checkBoxsName);
                for (var i = 0; i < checkboxs.length; i++) {
                    checkboxs[i].checked = false;
                }
            }
        };
        //得到被选中的checkbox的数据id
        function getIds(checkBoxsName) {
            var ids = [];
            $("input:checkbox[type=checkbox]:checked").each(function () {
                if ($(this).val() != "on"){
                    ids.push($(this).val());
                }
            });
            console.log(ids);
            return ids;
        }
        //导出功能的JS
        $("#export").click(
            function(){
                var submit = function (v, h, f) {
                    if (v == 'all') {
                        $("#searchForm").attr("action","${ctx}/affair/affairArchiveRegister/export?fileUnit="+${fileUnit});
                        $("#searchForm").submit();
                    }
                    if (v == 'part') {
                        dataExport('contentTable');
                    }
                    if (v == 'cancel') {
                        $.jBox.tip('已取消');
                    }
                    return true;
                };
                $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
            }
        );
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="affairLedgerInto" action="${ctx}/affair/affairArchiveRegister/export" method="post" class="breadcrumb form-search">
    <input id="fileUnit" name="fileUnit" type="hidden" value="${fileUnit}"/>
    <input id="fileName" name="fileName" type="hidden" value="干部档案台账.xlsx"/>
    <ul>
        <h1><p style="text-align:center">导出</p></h1>
    </ul>
    <ul class="ul-form">
        <input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选
    </ul><br><br><br>


<ul class="ul-form">

        <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="1"/>在职
        <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="2"/>离退
        <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="3"/>死亡

</ul>

    <ul>
        <li class="btns"><input id="" class="btn btn-primary" type="submit" value="导出"/></li>
    </ul>
</form:form>
</body>
</html>