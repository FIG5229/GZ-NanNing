<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>考评内容</title>
    <meta name="decorator" content="default"/>
    <script>
        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
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
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate();
        });
        function change(objId) {
            var html = $("a[id='"+objId+"']").html();
                //$("#"+objId).html();
            if("展开"==html){
                $("a[id='"+objId+"']").html("隐藏")
                //$("#"+objId).html("隐藏");
                $("[type='"+objId+"']").show();
            }else{
                $("a[id='"+objId+"']").html("展开")
                //$("#"+objId).html("展开");
                $("[type$='"+objId+"']").hide();
            }
        }

        /*左侧选择后，右侧批量选择*/
        function setPersonsByType(type) {
            $("[id$='_"+type+"Id']").val($("#"+type+"Id").val());
            $("[id$='_"+type+"Name']").val($("#"+type+"Name").val());
        }
        var myIndex = 0;
        //打开选择窗口
        function openUnitOrPeolTable(type) {
            myIndex = 0;
            if(type.indexOf("datas") == 0){
                //通过后边的选择按钮，回显已选择人员信息
                var ids = $("input[id='"+type+"Id']").val();
                //var ids = $("#"+type+"Id").val();
                var names = $("input[id='"+type+"Name']").val();
                //var names = $("#"+type+"Name").val();
                if(ids !='' && ids !=null && ids !='undefined'){
                    addSelAddRow(ids.split(","),names.split(","))
                }
            }
            $("#selUnitOrPeoDiv").css("display","block");
            $("#save").hide();
            $("#selType").val(type);
            //$(document.body).append(addTable);
        }
        //关闭选择窗口
        function closeUnitOrPeolTable() {
            var submit = function (v, h, f) {
                if (v == 'queren') {
                    $("#selUnitOrPeoDiv").css("display","none");
                    //清除tbody内容
                    $("#selUnitOrPeoTbody").html('');
                    $("#selNameInput").val('');
                    $("#selIdInput").val('');
                    $("#selType").val('');
                    //显示主页面上保存按钮
                    $("#save").show();
                }
                if (v == 'cancel') {
                    $.jBox.tip('已取消');
                }
                return true;
            };
            $.jBox.confirm("确认要放弃本次选择？", "关闭", submit, { buttons: { '确认': 'queren', '取消':'cancel'} });
        }
        //通过x号关闭
        function closeUnitOrPeolDiv() {
            $("#selUnitOrPeoDiv").css("display","none");
            //清除tbody内容
            $("#selUnitOrPeoTbody").html('');
            $("#selNameInput").val('');
            $("#selIdInput").val('');
            $("#selType").val('');
            //显示主页面上保存按钮
            $("#save").show();
        }

        /*打开选择用户树*/
        function openUnitOrPeoTree() {
            var mySelIds = [], mySelNames = [], mySelPids = [];
            // 正常打开
            top.$.jBox.open("iframe:${ctx}/tag/treeselect?url=/sys/office/treeData?type=3"+"&module=false&checked=true&extId=false&isAll=false", "选择用户", 500, 620, {
                ajaxData:{selectIds: ""},buttons:{"确定":"ok", "关闭":"cancel"}, persistent: true, submit:function(v, h, f){
                    if (v=="ok"){
                        var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                        var nodes = []
                        //多选
                        nodes = tree.getCheckedNodes(true);
                        for(var i=0; i<nodes.length; i++) {
                            if (nodes[i].isParent){
                                continue; // 如果为复选框选择，则过滤掉父节点
                            }
                            mySelIds.push(nodes[i].id.replace(/u_/ig,""));
                            mySelNames.push(nodes[i].name);
                            mySelPids.push(nodes[i].pId);
                            //console.log(nodes[i])
                        }
                        addRow(mySelIds,mySelNames,mySelPids);
                    }
                }, loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });


        }

        //添加数据行
        function addRow(mySelIds,mySelNames,mySelPids) {
            var inputSelNames = $("#selNameInput").val();
            var inputSelIds = $("#selIdInput").val();
            loading("生成中");
            $.ajax({
                url:'${ctx}/exam/examWorkflowSegmentsTask/selUnitNameByPids',
                data:{mySelIds:mySelIds,mySelNames:mySelNames ,mySelPids:mySelPids},
                type:"post",
                traditional: true,
                success:function (d) {
                    //console.log(d.result);
                    var message = '';
                    if(d.result.length>0){
                        for(var i = 0;i<d.result.length;i++){
                            if(inputSelIds.indexOf(d.result[i].selId) == -1||inputSelNames.indexOf(d.result[i].selName) == -1){
                                var myTr = '<tr id="myTr'+myIndex+'">\n' +
                                    '    <td>\n' +
                                    '        <input style="margin-left:12px" type="checkbox" name="myCheckBox" value="'+d.result[i].selId+'" id="myCheckBox'+myIndex +'"/>\n' +
                                    '    </td>\n' +
                                    '    <td>'+(myIndex+1)+'</td>\n' +
                                    '    <td>'+d.result[i].unitName+'</td>\n' +
                                    '    <td>'+d.result[i].selName+'</td>\n' +
                                    '    <td><a id="btnCancel'+myIndex+'" href="javaScript:void(0);" onclick="removeTr(\'btnCancel'+myIndex+'\')">移除</a></td>\n' +
                                    '</tr>'
                                myIndex++;
                                $("#selUnitOrPeoTbody").append(myTr);
                                var inputSelIdArr;
                                var inputSelNameArr;
                                if(inputSelIds==''){
                                    inputSelIdArr = new Array();
                                    inputSelNameArr = new Array();
                                }else{
                                    inputSelIdArr = inputSelIds.split(",");
                                    inputSelNameArr = inputSelNames.split(",")
                                }
                                inputSelNameArr.push(d.result[i].selName);
                                inputSelIdArr.push(d.result[i].selId);
                                inputSelIds = inputSelIdArr.join(",");
                                inputSelNames = inputSelNameArr.join(",");
                            }else{
                                message +=  d.result[i].unitName+"单位下："+d.result[i].selName+';'
                            }
                        }
                        $("#selNameInput").val(inputSelNames);
                        $("#selIdInput").val(inputSelIds);
                    }
                    if(message!=''){
                        closeLoading();
                        $.jBox.tip('添加成功，'+message+"已添加，本次将不再进行添加！");
                    }else{
                        closeLoading();
                        $.jBox.tip("添加成功");
                    }
                },error:function (d) {
                    closeLoading();
                    $.jBox.tip("发生错误，请重试")
                }
            })

        }

        //回显已选择信息
        function addSelAddRow(mySelIds,mySelNames){
            $("#selNameInput").val(mySelNames.join(","));
            $("#selIdInput").val(mySelIds.join(","));
            loading("生成已选择信息");
            $.ajax({
                url:'${ctx}/exam/examWorkflowSegmentsTask/selUnitNameById',
                data:{mySelIds:mySelIds},
                type:"post",
                traditional: true,
                success:function (d) {
                    //console.log(d.result);
                    if(d.result.length>0){
                        for(var i = 0;i<d.result.length;i++){
                                var myTr = '<tr id="myTr'+myIndex+'">\n' +
                                    '    <td>\n' +
                                    '        <input style="margin-left:12px" type="checkbox" name="myCheckBox" value="'+d.result[i].selId+'" id="myCheckBox'+myIndex +'"/>\n' +
                                    '    </td>\n' +
                                    '    <td>'+(myIndex+1)+'</td>\n' +
                                    '    <td>'+d.result[i].unitName+'</td>\n' +
                                    '    <td>'+d.result[i].selName+'</td>\n' +
                                    '    <td><a id="btnCancel'+myIndex+'" href="javaScript:void(0);" onclick="removeTr(\'btnCancel'+myIndex+'\')">移除</a></td>\n' +
                                    '</tr>'
                                myIndex++;
                                $("#selUnitOrPeoTbody").append(myTr);
                        }
                    }
                    closeLoading();
                    $.jBox.tip("生成完成");
                },error:function (d) {
                    closeLoading();
                    $.jBox.tip("发生错误，请重试")
                }
            })
        }
        //单行移除
        function removeTr(d) {
            var removeId = '';
            if(d.indexOf("myCheckBox")!=-1){
                removeId = $("#"+d).val();
            }else{
                var str = d.replace("btnCancel","");
                removeId = $("#myCheckBox"+str).val();
            }
            var mySelNames = $("#selNameInput").val();
            var mySelIds = $("#selIdInput").val();
            var selIdArr = mySelIds.split(",")
            var selNameArr = mySelNames.split(",")
            for(var i = 0;i<selIdArr.length;i++){
                if(selIdArr[i] == removeId){
                    selIdArr.splice(i,1);
                    selNameArr.splice(i,1);
                }
            }
            mySelIds = selIdArr.join(",");
            mySelNames = selNameArr.join(",");
            $("#selNameInput").val(mySelNames);
            $("#selIdInput").val(mySelIds);
            var a=$("#"+d).parent().parent().attr('id');
            $("#"+a).remove();
        }
        //批量移除
        function batchRemove(){
            var checkboxs = document.getElementsByName("myCheckBox");
            if(typeof(checkboxs) == undefined ||checkboxs==null || checkboxs.length<=0){
                $.jBox.tip('请先选择要删除的内容');
            }else{
                var ids = new Array();
                for(var i = 0;i<checkboxs.length;i++){
                    if(checkboxs[i].checked == true){
                        ids.push(checkboxs[i].id);
                    }
                }
                //移除具体方法
                if(ids.length<=0){
                    $.jBox.tip('请先选择要删除的内容');
                }else{
                    for(var i=0;i<ids.length;i++){
                        removeTr(ids[i])
                    }
                }
            }

        }

        /*勾选所有的复选框*/
        specialChooseAll = function (obj,checkBoxsName) {
            if (obj.checked) {
                var checkboxs = document.getElementsByName(checkBoxsName);
                for (var i = 0; i < checkboxs.length; i++) {
                    if(checkboxs[i].checked == false){
                        checkboxs[i].click()
                    }
                }
            } else {
                var checkboxs = document.getElementsByName(checkBoxsName);
                for (var i = 0; i < checkboxs.length; i++) {
                    if(checkboxs[i].checked == true){
                        checkboxs[i].click()
                    }
                }
            }
        };

        //保存选择人员/单位信息
        function saveSelResult() {
            //获取选中的值
            var mySelNames = $("#selNameInput").val();
            var mySelIds = $("#selIdInput").val();
            var type = $("#selType").val();
            if(type.indexOf("datas") == 0){
                //赋值 单个
                $("#"+type+"Id").val(mySelIds);
                $("#"+type+"Name").val(mySelNames);
                $("#"+type+"datasSpan").text("已选择未保存");
            }else{
                //赋值 批量
                $("[id$='_"+type+"Id']").val(mySelIds);
                $("[id$='_"+type+"Name']").val(mySelNames);
                $("[id$='_"+type+"datasSpan']").text("已选择未保存");
            }
            //隐藏表格div
            $("#selUnitOrPeoDiv").css("display","none");
            //清除tbody内容
            $("#selUnitOrPeoTbody").html('');
            $("#selNameInput").val('');
            $("#selIdInput").val('');
            $("#selType").val('');
            //显示主页面上保存按钮
            $("#save").show();
            $.jBox.tip("更新选择成功")
        }
        //多选 查看已选择的人员/单位信息（弃）
        function seeSelDetail(d) {
            var selDetail = $("#"+d+"Name").val();
            if(selDetail==''){
                selDetail = '未选择';
            }
            var submit = function (v, h, f) {
                return true;
            };
            $.jBox.confirm(selDetail, "已选择人员/单位信息", submit, { buttons: { '关闭':'cancel'} });
        }
        //展示提示框
        function showMyTooltip(index,nameId){
            var names = $("input[id='"+nameId+"']").val();
            //var names = $("#"+nameId).val();
            var oldTooltipText =  $("#tooltip"+index).text();
            if(names!=null && names!='' && typeof names != undefined){
                $("#tooltip"+index).text("已选择："+names.substring(0,80)+"...");
            }else{
                $("#tooltip"+index).text("尚未选择");
            }
            $("#tooltip"+index).animate({ opacity: 0.8 }, 1000);
        }
        function hideMyTooltip(index){
            $("#tooltip"+index).animate({ opacity: 0 }, 300);
        }

        //人员快速选择，关联exam_Object_Type
        function openFastCheckFrom() {
            var fastCheckTypeValue = $('#fastCheckType').val();
            if(fastCheckTypeValue!=null && fastCheckTypeValue != '' && typeof(fastCheckTypeValue)!='undefined'){
                loading("生成中");
                $.ajax({
                    url:'${ctx}/exam/examWorkflowSegmentsTask/fastCheck',
                    data:{objectTypeId:fastCheckTypeValue},
                    type:"post",
                    traditional: true,
                    success:function (d) {
                        closeLoading();
                        if(d.success){
                           // 获取myselIds、myselNames、mySelPids
                            var myselIds = d.result.myselIds;
                            var myselNames = d.result.myselNames;
                            var mySelPids = d.result.mySelPids;
                            //console.log(myselNames+"   "+myselIds+"   "+mySelPids);
                            $("#selNameInput").val();
                            $("#selIdInput").val();
                            addRow(myselIds,myselNames,mySelPids);
                        }
                    },error:function (d) {
                        closeLoading();
                        $.jBox.tip("发生错误，请重试")
                    }
                })
            }else{
                if(typeof(fastCheckTypeValue)=='undefined'){
                    jBox.tip("快速选择发生错误："+fastCheckTypeValue)
                }else{
                    jBox.tip("请选择左侧下拉框内容")
                }
            }
        }

        function delSegmentTask(segmentId,stardardId){
            /*删除当前环节 当前标准所有的任务分配*/
        }
    </script>
    <style>
        #selUnitOrPeoDiv{
            display: none;
            width: 90%;
            height: 400px;
            position:fixed;
            left:5%;
            bottom:50px;
            box-shadow:0px 0px  10px 5px #aaa;
            /*position: absolute; left: 0; top: 0; right: 0; bottom: 0;*/
            margin: auto;
            background-color: white;
            border: 1px solid grey;
        }
        #selUnitOrPeoDivContent{
            width: 100%;
            height: 70%;
            overflow:auto;
        }
        .tooltip{
            position: fixed;
            width: 20%;
            min-height:50px;
            height: auto;
            background-color: black;
            color: #fff;
            text-align: left;
            border-radius: 6px;
            left: 40%
        }

    </style>
</head>
<%--任务分配人员选择页面--%>
<body>
<form:form id="inputForm" action="${ctx}/exam/examWorkflowSegmentsTask/assignDatasave" method="post" class="form-horizontal">
<%--    <input id="save" class="btn btn-primary" type="submit" value="保存"/>--%>
    <sys:message content="${message}"/>
    <input name="workflowId" value="${workflowId}" type="hidden"/>
    <input name="segmentId" value="${segmentId}" type="hidden"/>
    <input name="standardId" value="${examStandardTemplateDefine.examStardardId}" type="hidden"/>
    <input name="segmentName" value="${segmentName}" type="hidden"/>
    <div>
        <div class="modal-custom-content">
            <div class="modal-custom-tb-l">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>选择</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${rowList}" var="row"  varStatus="status">
                                <c:choose>
                                    <c:when test="${'block'.equals(row.display)}">
                                        <tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr style="display: none" type="${row.type}">
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach items="${columnList}" var="examStandardTemplateItemDefine" varStatus="columnIndex">
                                    <c:choose>
                                        <c:when test="${0==columnIndex.index&&'block'.equals(row.display)}">
                                            <td>
                                                <a href="javascript:void(0)" onclick="change('${row.type}')" id="${row.type}">展开</a>${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                                <%--选择主管领导时，此处应该单选 删除checked="true"--%>
                                                <c:choose>
                                                    <c:when test="${check eq 'isCheck'}">
                                                        <br>
                                                        <input id="selUnitOrPersonnel" class="btn btn-primary btn-small" type="button" value="批量选择" onclick="openUnitOrPeolTable('${row.type}')"/>
                                                        <%--<sys:treeselect id="${row.type}" name="" value="" labelName="" labelValue=""
                                                                smallBtn="true"   title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" checked="true" onchange="setPersonsByType('${row.type}')"/>--%>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <sys:treeselect id="${row.type}" name="" value="" labelName="" labelValue=""
                                                                smallBtn="true"   title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"  onchange="setPersonsByType('${row.type}')"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                    ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                            </td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <td>
                                        <%--选择主管领导时，此处应该单选 删除checked="true"--%>
                                    <c:choose>
                                        <c:when test="${check eq 'isCheck'}">
                                            <%-- <a href="javaScript:void(0)" onclick="seeSelDetail('datas${status.index}ids_${row.type}')">查看</a>--%>
                                            <input id="datas${status.index}ids_${row.type}Id" name="datas[${status.index}].ids" value="${row.ids}" type="hidden">
                                            <input id="datas${status.index}ids_${row.type}Name" name="datas[${status.index}].personNames" value="${row.personNames}" type="hidden">
                                            <c:choose>
                                                <c:when test="${not empty row.personNames}">
                                                    <span id="datas${status.index}ids_${row.type}datasSpan" >已选择：${fn:substring(row.personNames,0,5)}...</span>
                                                    <br>
                                                </c:when>
                                                <c:otherwise>
                                                    <span id="datas${status.index}ids_${row.type}datasSpan" >尚未选择</span>
                                                    <br>
                                                </c:otherwise>
                                            </c:choose>
                                            <input id="datas${status.index}ids_${row.type}btn" class="btn btn-primary btn-small" type="button" value="选择" onclick="openUnitOrPeolTable('datas${status.index}ids_${row.type}')" onmouseover="showMyTooltip(${status.index},'datas${status.index}ids_${row.type}Name')" onmouseout="hideMyTooltip(${status.index})"/>
                                            <%--<sys:treeselect id="datas${status.index}ids_${row.type}" name="datas[${status.index}].ids" value="${row.ids}" labelName="datas[${status.index}].personNames" labelValue="${row.personNames}"
                                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" checked="true"/>--%>
                                        </c:when>
                                        <c:otherwise>
                                            <sys:treeselect id="datas${status.index}ids_${row.type}" name="datas[${status.index}].ids" value="${row.ids}" labelName="datas[${status.index}].personNames" labelValue="${row.personNames}"
                                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
                                        </c:otherwise>
                                    </c:choose>

                                </td>
                                <c:choose>
                                    <c:when test="${not empty row.personNames}">
                                        <span id="tooltip${status.index}" class="tooltip" style="z-index: 9">已选择：${fn:substring(row.personNames,0,80)}...</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span id="tooltip${status.index}" class="tooltip" style="z-index: 9">尚未选择</span>
                                    </c:otherwise>
                                </c:choose>

                                <input name="datas[${status.index}].id" value="${row.taskId}" type="hidden"/>
                                <input name="datas[${status.index}].workflowId" value="${workflowId}" type="hidden"/>
                                <input name="datas[${status.index}].segmentId" value="${segmentId}" type="hidden"/>
                                <input name="datas[${status.index}].standardId" value="${examStandardTemplateDefine.examStardardId}" type="hidden"/>
                                <input  type="hidden" name="datas[${status.index}].rowNum" value="${row.row_num}">
                            </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right;">
<%--                    <input id="del" class="btn btn-primary" type="button" onclick="delSegmentTask('${segmentId}','${examStandardTemplateDefine.examStardardId}')" value="清空"/>--%>
                    <input id="save" class="btn btn-primary" type="submit" value="保存"/>
                </div>
            </div>
        </div>
    </div>

</form:form>
<%--人员分配 DIV--%>
<div id="selUnitOrPeoDiv" style="z-index: 10">
    <fieldset style="width: 99%">
        <legend style="font-size:16px;color: black;font-weight: bold;padding-left: 5px;">选择人员/单位<span style="float: right;color: gainsboro;margin-right: 2px;cursor: pointer" onclick="closeUnitOrPeolDiv()">X</span></legend>
    </fieldset>
    <div style="text-align: right;margin-right: 1%">
        <input type="text" id="selNameInput" style="display: none">
        <input type="text" id="selIdInput" style="display: none">
        <input type="text" id="selType" style="display: none">
        <select id ="fastCheckType"  style="text-align: left;min-width: 150px">
            <option value=""></option>
            <option value=""></option>
            <c:forEach items="${examObjectTypeList}" var="examObjectType" varStatus="status">
                <option value="${examObjectType.id}" >${examObjectType.typeName}</option>
            </c:forEach>

        </select>
        <input class="btn btn-primary" type="button" value="快速选择" onclick="openFastCheckFrom()"/>
        &nbsp; &nbsp;
        <input id="add" class="btn btn-primary" type="button" value="添加" onclick="openUnitOrPeoTree()"/>
        &nbsp; &nbsp;
        <input id="remove" class="btn" type="button" value="移除" onclick="batchRemove()"/>
    </div>
    <div id="selUnitOrPeoDivContent">
        <table id="selUnitOrPeoTable" class="table table-striped table-bordered table-condensed">
            <thead>
            <th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='specialChooseAll(this,"myCheckBox")'/>全选</th>
            <th>序号</th>
            <th>单位</th>
            <th>账号</th>
            <th>操作</th>
            </thead>
            <tbody id="selUnitOrPeoTbody">

            </tbody>
        </table>
    </div>
    <div style="text-align: center">
        <input id="saveSelResult" class="btn btn-primary" type="button" value="保存" onclick="saveSelResult()"/>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input id="cancel" class="btn" type="button" value="关闭" onclick="closeUnitOrPeolTable()"/>
    </div>
</div>
</body>
</html>