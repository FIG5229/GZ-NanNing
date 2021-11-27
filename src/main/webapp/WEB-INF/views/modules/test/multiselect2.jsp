<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/3.3.7/css_default/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap/3.3.7/awesome/font-awesome.min.css">
    <link href="${ctxStatic}/css/ySelect.css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/js/ySelect.js" type="text/javascript"></script>
</head>
<body>
<div class="container" style="margin: 20px;">
    <!--
    <select id='m1' class="demo" multiple="multiple">
        <optgroup label="产品">
            <option value="1">加多宝凉茶</option>
            <option value="2">饮料</option>
            <option value="3">太太乐鸡精</option>
            <option value="4">嘉士伯啤酒</option>
        </optgroup>
        <optgroup label="字典">
            <option value="5">成功图像类型</option>
            <option value="6">门店类型</option>
            <option value="7">终端类型</option>
        </optgroup>
    </select>
-->
    <select id='m2' class="demo1" multiple="multiple" >
        <option value="1">加多宝凉茶</option>
        <option value="2">饮料</option>
        <option value="3">太太乐鸡精</option>
        <option value="4">嘉士伯啤酒</option>
        <option value="5">成功图像类型</option>
        <option value="6">门店类型</option>
    </select>
    <br/>
    <button id="current" class="btn btn-primary btn-sm">查看值</button>
</div>

<script>
    $(function() {
        //$('.demo').ySelect();
        $('.demo1').ySelect(
            {
                placeholder: '请先选择一些项目',
                searchText: '搜索',
                showSearch: true,
                numDisplayed: 4,
                overflowText: '已选中 {n}项',
                isCheck:false
            }
        );

        $("#current").click(function(){
            //alert($("#m1").ySelectedValues(","));
            alert($("#m1").ySelectedTexts(","));
        });

    });
</script>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">

</div>
</body>
</html>
