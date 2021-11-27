<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/test/test/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });

            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=test", "添加",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
                });
            });

            $("#btnImport2").click(function(){
                top.$.jBox.open("iframe:${ctx}/test/test/template/download/view?id=test", "添加",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
                });
            });
        });
    </script>
</head>
<body>
<form:form id="searchForm" method="post" class="breadcrumb form-search ">
    <ul class="ul-form">
             <input type="hidden"  name="fileName" value="用户数据导出模板.xlsx"/>
            <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
            <input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
            <input id="btnImport2" class="btn btn-primary" type="button" value="导入"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
</body>
</html>