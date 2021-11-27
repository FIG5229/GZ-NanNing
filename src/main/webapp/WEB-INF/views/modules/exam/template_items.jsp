<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#importForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
        function change() {
            var selected = $("#template").val();
            if(selected==-1){
                openForm('${ctx}/exam/examStandardTemplateDefine/');
            }else{
                $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/items");
                $('#importForm').attr("onsubmit","");
                $("#importForm").submit();
                $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/items");
                loading("正在查询，请稍等...")
                closeLoading();
            }
        }

        function openForm(url) {
            top.$.jBox.open("iframe:"+url, "考评标准模板格式添加",1000,620,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/exam/examStandardBaseInfo?standard_id=${standardId}"}
            });
        }

        function downLoadTemplate(){
            debugger
            let template = $('#template option:selected') .val();;
            if (template == -1){
                $.jBox.alert('请新建考评格式', 'jBox');
                return;
            }
            $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/downloadTemplate");
            $('#importForm').attr("onsubmit","");
            $("#importForm").submit();
            $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/items");
            closeLoading();
        }

        function openEditDialog(standardId,rowNum) {
            var url = "iframe:${ctx}/exam/examStandardTemplateData/form?itemId="+standardId+"&rowNum="+rowNum;
            top.$.jBox.open(url, "测评标准修改",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/exam/examStandardTemplateDefine/items?standard_id=${standard_id}&objType=${objType}&kpType=${kpType}&cycle=${cycle}"}
            });

        }

        function openAddForm(standardId) {
            // var templateId = $("#template").val();
            var templateId = '${templateId}';
            var url = "iframe:${ctx}/exam/examStandardTemplateData/addForm?examStardardId="+standardId+"&templateId="+templateId;
            top.$.jBox.open(url, "测评标准添加",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/exam/examStandardTemplateDefine/items?standard_id=${standard_id}&objType=${objType}&kpType=${kpType}&cycle=${cycle}"}
            });
        }

        if ("success" == "${saveResult}"){
            parent.$.jBox.close();
        }

        function importStandardData() {
            top.$.jBox.confirm("确认要导入 "+$("#template option:selected").text()+" 吗", '系统提示', function (v, h, f) {
                if (v == 'ok') {
                    $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/import");
                    $('#importForm').attr("onsubmit","");
                    $("#importForm").submit();
                    $('#importForm').attr("action","${ctx}/exam/examStandardTemplateDefine/import");
                }
            })
        }
        function exportStandardData() {
            /*隐藏没用啊*/
            $('#handleTh').css('display', 'none');
            $('.handleTd').css('display', 'none');
            dataExport("contentTable");
            $('#handleTh').css('display', 'table-cell');
            $('.handleTd').css('display', 'table-cell');
        }
    </script>
</head>
<body>
<div id="importBox">
    <!--
    <ul class="nav nav-tabs">
        <li><a href="${ctx}/exam/examStandardBaseInfo/">考评标准管理列表</a></li>
        <shiro:hasPermission name="exam:examStandardBaseInfo:edit"><li class="active"><a href="${ctx}/exam/examStandardBaseInfo/form">考评标准管理添加</a></li></shiro:hasPermission>
    </ul>-->

    <form id="importForm" action="${ctx}/exam/examStandardTemplateDefine/import" method="post" enctype="multipart/form-data"
          class="form-search" style="padding-left:20px;text-align:center;padding-top: 30px" onsubmit="loading('正在导入，请稍等...');">
        <input name ="standard_id" type="hidden" value="${standard_id}"/>
        <input name ="objType" type="hidden" value="${objType}"/>
        <input name ="kpType" type="hidden" value="${kpType}"/>
        <input name ="cycle" type="hidden" value="${cycle}"/>
        <select id="template" name="template" onchange="change()" style="width:350px;">
            <c:forEach items="${templateList}" var="examStandardTemplateDefine">
                <c:choose>
                    <c:when test="${examStandardTemplateDefine.id eq templateId}">
                        <option value="${examStandardTemplateDefine.id}" selected >${examStandardTemplateDefine.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${examStandardTemplateDefine.id}">${examStandardTemplateDefine.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <option value="-1">新建考评</option>
        </select>
        <input id="uploadFile" name="file" type="file" style="width:330px"/>
        <a href="javascript:;" onclick=downLoadTemplate()>下载模板</a>
        <input id="btnImportSubmit" class="btn btn-primary" type="button" value="   导    入   " onclick="importStandardData();"/>
        <input id="btnExportSubmit" class="btn btn-primary" type="button" value="   导    出   " onclick="exportStandardData();"/>
        &nbsp;&nbsp;&nbsp;
        <input id="btnAdd" class="btn btn-primary" type="button" value="   添    加   " onclick="openAddForm('${standard_id}')"/>
    </form>
    <sys:message content="${message}"/>
    <div><a href="javascript:;" style="color: red">（注意：导入前务必确认选择的标准正确）</a></div>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                <th>${examStandardTemplateItemDefine.columnName}</th>
            </c:forEach>
            <%--改为主表模板权限--%>
            <shiro:hasPermission name="exam:examStandardBaseInfo:edit"><th id="handleTh">操作</th></shiro:hasPermission>
        </tr>
        </thead>
        <tbody>
        <script>
            //console.log("${columnList}");
        </script>
        <c:forEach items="${rowlist}" var="row" varStatus="s">
            <tr>
                <c:forEach items="${columnList}" var="examStandardTemplateItemDefine" varStatus="status">
                    <td>
                        <%--获取到的数据与columnList的id对应不上为空--%>
                            ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                    </td>
                </c:forEach>
                <%--使用主表权限就可以--%>
                <shiro:hasPermission name="exam:examStandardBaseInfo:edit"><td class="handleTd">
                    <a href="javascript:;" onclick="openEditDialog('${standard_id}',${row.rowNum})">修改</a>
                    <a href="${ctx}/exam/examStandardTemplateData/delete?itemId=${standard_id}&rowNum=${row.rowNum}&objType=${objType}&kpType=${kpType}&cycle=${cycle}" onclick="return confirmx('确认要删除该模板项数据管理吗？',
                            this.href)">删除</a>
                </td></shiro:hasPermission>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>