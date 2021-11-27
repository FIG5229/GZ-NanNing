<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#print").click(function(){
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
                    formValues: false
                });
            });
        });
    </script>
    <style>
        #contentTable tr td {
            width: 25%;
        }
    </style>
</head>
<body>
<br>
<table id="contentTable" class="table table-bordered" style="width: 90%;margin: auto;text-align: center">
    <tr>
        <td class="tdKeepRight">类别名称</td>
        <td>${examObjectType.typeName}</td>
        <td class="tdKeepRight">考评类别</td>
        <td>${fns:getDictLabel(examObjectType.examType, 'kp_type', '')}</td>

    </tr>
    <tr>
        <td class="tdKeepRight">被考评对象</td>
        <td colspan="3">${examObjectType.objectUserName}</td>
    </tr>

</table>
<br>
<div class="modal-custom-info1-bottom">
    <div class="modal-custom-info1-btn red" id="print">打印</div>
</div>
</body>
</html>

