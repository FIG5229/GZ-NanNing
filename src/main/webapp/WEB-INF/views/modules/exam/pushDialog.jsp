<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>考评单位</title>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
    </style>
</head>
<body>
<div id="content" class="form-horizontal">
    <form:form id="inputForm" action="${ctx}/exam/examPushHistory/saveBeta" method="post" class="form-horizontal">


        <input type="hidden" value="${workflowId}" name="workflowId">
        <input type="hidden" value="${workflowDataId}" name="workflowDatasId">
        <input type="hidden" value="${rowNum}" name="rowNum">
        <input type="hidden" value="${type}" name="type">
        <input type="hidden" value="${standardContent}" name="itemStanddard">
        <input type="hidden" value="${items}" name="itemDetail">
        <input type="hidden" value="${standardId}" name="standardId">
       <br>
        <label>考评推送类别：</label>
           <select id="examType" name="examType" class="input-medium">
               <option value="1" label="">局考处</option>
               <option value="2" label="">局考局机关</option>
               <option value="3" label="">处考所</option>
               <option value="4" label="">处考处机关及独立单位</option>
               <option value="5" label="">处领导班子考核</option>
               <option value="6" label="">中基层领导考核</option>
               <option value="7" label="">民警考核</option>
           </select>
<%--        <input type="submit" value="查询" class="btn btn-primary" hidden="hidden">--%>
        <br>
        <br>
        <br>
        <div id="jukaochu">
            <lable>推送给：</lable>
            <sys:treeselect id="jukaochuperson" name="objId" value="" labelName="objName"
                             labelValue="" checked="true"
                             title="用户" url="/exam/examWorkflowDatas/pushTree?type=2&condition=1" cssClass="" allowClear="true"
                             notAllowSelectParent="false" isAll="true"/>
        </div>
        <div id="jukaojujiguan" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="jukaojujiguanperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=2&condition=2" cssClass="" allowClear="true"
                            notAllowSelectParent="false" isAll="true"/>
        </div>
        <div id="chukaosuo" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="chukaosuoperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=2&condition=3" cssClass="" allowClear="true"
                            notAllowSelectParent="false" isAll="true"/>
        </div>
        <div id="chukaochujiguan" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="chukaochujiguanperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=2&condition=4" cssClass="" allowClear="true"
                            notAllowSelectParent="false" isAll="true"/>
        </div>
        <div id="chulingdao" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="chulingdaoperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true" examTreeType="5"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=3&condition=5" cssClass="" allowClear="true"
                            notAllowSelectParent="true" isAll="true"/>
        </div>
        <div id="jicenglingdao" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="jicenglingdaoperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true" examTreeType="6"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=3&condition=6" cssClass="" allowClear="true"
                            notAllowSelectParent="true" isAll="true"/>
        </div>
        <div id="minjing" style="display: none;">
            <lable>推送给：</lable>
            <sys:treeselect id="minjingperson" name="objId" value="" labelName="objName"
                            labelValue="" checked="true" examTreeType="7"
                            title="用户" url="/exam/examWorkflowDatas/pushTree?type=3&condition=7" cssClass="" allowClear="true"
                            notAllowSelectParent="true" isAll="true"/>
        </div>
        <input id="save" class="btn btn-primary" type="submit" value="保存" />
    </form:form>

</div>
<script type="text/javascript">

    if ("success" == "${saveResult}") {
        parent.$.jBox.close();
    }

        $("#examType").change(function(){
            let examType = $("#examType").val();
            switch (examType) {
                case "1":
                  $("#jukaochu").show();
                  $("#jukaojujiguan").hide();
                  $("#chukaosuo").hide();
                  $("#chukaochujiguan").hide();
                  $("#chulingdao").hide();
                  $("#jicenglingdao").hide();
                  $("#minjing").hide();
                    break;
                case "2":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").show();
                    $("#chukaosuo").hide();
                    $("#chukaochujiguan").hide();
                    $("#chulingdao").hide();
                    $("#jicenglingdao").hide();
                    $("#minjing").hide();
                    break;
                case "3":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").hide();
                    $("#chukaosuo").show();
                    $("#chukaochujiguan").hide();
                    $("#chulingdao").hide();
                    $("#jicenglingdao").hide();
                    $("#minjing").hide();
                    break;
                case "4":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").hide();
                    $("#chukaosuo").hide();
                    $("#chukaochujiguan").show();
                    $("#chulingdao").hide();
                    $("#jicenglingdao").hide();
                    $("#minjing").hide();
                    break;
                case "5":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").hide();
                    $("#chukaosuo").hide();
                    $("#chukaochujiguan").hide();
                    $("#chulingdao").show();
                    $("#jicenglingdao").hide();
                    $("#minjing").hide();
                    break;
                case "6":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").hide();
                    $("#chukaosuo").hide();
                    $("#chukaochujiguan").hide();
                    $("#chulingdao").hide();
                    $("#jicenglingdao").show();
                    $("#minjing").hide();
                    break;
                case "7":
                    $("#jukaochu").hide();
                    $("#jukaojujiguan").hide();
                    $("#chukaosuo").hide();
                    $("#chukaochujiguan").hide();
                    $("#chulingdao").hide();
                    $("#jicenglingdao").hide();
                    $("#minjing").show();
                    break;
            }
        });


    function push() {
        let tip = confirm("确认要推送给选中的单位吗!");
        if (tip == true) {
            var treeObj = $.fn.zTree.getZTreeObj("ztree");
            var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
            var array = new Array();
            for (var i = 0; i < nodes.length; i++) {
                if (!nodes[i].isParent)
                    array.push(nodes[i].id);
            }
            $.getJSON("${ctx}/exam/examPushHistory/saveBeta",
                {
                    "ids": array,
                    "status": '',
                    "standardId":"${standardId}",
                    "workflowId":'${workflowId}',
                    "workflowDatasId":'${workflowDataId}',
                    'objName':'${objName}',
                    "history":'${history}',
                    "processType":"${processType}",
                    "personType":'${personType}',
                    "rowNum":'${rowNum}'
                },
                function (data) {
                    if (data.success) {
                        parent.$.jBox.Close();
                    }else {
                        jBox.tip("推送失败，请重试");
                    }
                });

            /*选中的节点Id  不包含父节点，父节点是是单位iD，找不到用户Id*/
            // console.log(array);
        } else {

        }
    }
</script>
<script type="text/javascript">
    var setting = {
        view: {
            dblClickExpand: true,
            selectedMulti : true,//可以多选
            showLine: true
        },
        check: {
            enable: true ,//显示复选框
            chkStyle : "checkbox"
        },
        data: {
            key: {
                title:"t"
            },
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: null
            }
        },
        callback:{onClick:function(event, treeId, treeNode){

            }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/exam/examWorkflowDatas/pushTree?type=2",function(data){
            var tree = $.fn.zTree.init($("#ztree"), setting, data);
            tree.expandAll(true);
            var node = tree.getNodes()[0];
            tree.selectNode(node);
            setting.callback.onClick(null, tree.setting.treeId, node);
        });
    }
    refreshTree();
    var leftWidth = 250; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
        mainObj.css("width","auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height());
    }

</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>