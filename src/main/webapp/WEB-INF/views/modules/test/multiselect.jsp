<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap-select/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.css" rel="stylesheet" />
    <script type="text/javascript">
        $(function() {
            $(".selectpicker").selectpicker({
                noneSelectedText : '请选择'//默认显示内容
            });
            //数据赋值
            var select = $("#slpk");
            select.append("<option value='1'>香蕉</option>");
            select.append("<option value='2'>苹果</option>");
            select.append("<option value='3'>橘子</option>");
            select.append("<option value='4'>石榴</option>");
            select.append("<option value='5'>棒棒糖</option>");
            select.append("<option value='6'>桃子</option>");
            select.append("<option value='7'>陶子</option>");
            //初始化刷新数据
            $(window).on('load', function() {
                $('.selectpicker').selectpicker('refresh');
            });
        });
    </script>
</head>
<body>
<select id="slpk" class="selectpicker" data-live-search="true" multiple>
    <option value='8'>测试</option>
</select>
</select>
</body>
</html>
