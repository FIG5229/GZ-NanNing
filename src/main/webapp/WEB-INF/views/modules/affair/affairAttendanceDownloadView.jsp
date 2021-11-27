<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title></title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
        });

        function save() {
            $.ajax({
                type: "post",
                url: '${ctx}/affair/affairAttendance/importSave',
                data: {
                    unitId: $("#unitId").val(),
                    unit: $("#unitName").val(),
                    year: $("#year").val(),
                    mouth: $("#mouth").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.success == true) {
                        $.jBox.tip("保存成功");
                    } else {
                        $.jBox.tip("保存失败");
                    }
                }
            })
        }
    </script>
</head>
<body>
<div id="importBox">
    <form id="importForm" action="${ctx}${url}" method="post" enctype="multipart/form-data"
          class="form-horizontal" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
        <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>
        <span style="color: red">注意：导入前请务必填写全以下内容，并点击保存后再导入文件，否则导入无效!</span></br>
        <div style="margin-top: 5px;">
            <label class="control-label">所在单位：</label>
            <div class="controls">
                <sys:treeselect id="unit" name="unitId" value="" labelName="unit" labelValue=""
                                title="所在单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
                                dataMsgRequired="必填信息"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div style="margin-top: 5px;">
            <label class="control-label">年度：</label>
            <div class="controls">
                <input id="year" name="year">年
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        <div style="margin-top: 5px;">
            <label class="control-label">月度：</label>
            <div class="controls">
                <input id="mouth" name="mouth">月
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
        </br>
        <input class="btn btn-primary" type="button" value="保存" onclick="save()"/>
        <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="导入"/>
        <a href="${ctx}/file/template/download?fileName=${template}">下载模板</a>
    </form>
</div>
<c:if test="${not empty message}">
    <c:if test="${not empty message}"><c:set var="ctype"
                                             value="${fn:indexOf(message,'失败') eq -1?'success':'error'}"/></c:if>
    <div id="messageBox" class="alert alert-${ctype} hide">
        <button data-dismiss="alert" class="close">×</button>
            ${message}</div>
    <script type="text/javascript">
        if (!$.jBox.tip.mess) {
            $.jBox.tip.mess = 1;
            $("#messageBox").show();
        }
    </script>
</c:if>
<script>
    if ("success" == "${result}") {
        parent.$.jBox.close();
    }
</script>
</body>
</html>