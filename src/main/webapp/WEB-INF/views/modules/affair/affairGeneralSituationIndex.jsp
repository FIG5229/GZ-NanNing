<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>党组织概况管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
    </style>
</head>
<body>
<div id="content" class="row-fluid">
    <div id="left" class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle">党组织<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
        </div>
        <div id="ztree" class="ztree"></div>
    </div>
    <div id="openClose" class="close">&nbsp;</div>
    <div id="right">
        <iframe id="officeContent" src="" width="100%" height="91%" frameborder="0"></iframe>
    </div>
</div>
<script type="text/javascript">
    var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:''}},
        callback:{onClick:function(event, treeId, treeNode){
                //var id = treeNode.pId == '0' ? '' :treeNode.pId;
                var id = treeNode.pId == '0' ? '' :treeNode.id;
                ////debugger;er;

                if (id == '1' || id == '2' || id == '34' || id ==  '95' || id == '156') {
                    /*if ('${fns:getUser().id}'!='ff7f9fe2597b40429ded58f8b76a2f65') {
                        $('#officeContent').attr("src","${ctx}/affair/affairWorkLead/list?treeId="+id+"&parentIds="+treeNode.pId+"&isTree=1");
                    }*/
                    //机关党委只保留年度工作安排和工作总结
                    if (id == '2'){
                        $('#officeContent').attr("src","${ctx}/affair/affairWorkPlan/list?treeId="+id+"&parentIds="+treeNode.pId+"&isTree=1");
                    } else {
                        $('#officeContent').attr("src","${ctx}/affair/affairWorkLead/list?treeId="+id+"&parentIds="+treeNode.pId+"&isTree=1");
                    }
                }else{
                    if('${fns:getUser().roleList.contains('3f1198e36a534b52a671bae729a0dca9')}'){
                        $('#officeContent').attr("src","${ctx}/affair/affairSystemConstruction/list?treeId="+id+"&parentIds="+treeNode.pId+"&isTree=1");
                    }else {
                        $('#officeContent').attr("src","${ctx}/affair/affairGeneralSituation/list?treeId="+id+"&parentIds="+treeNode.pId+"&isTree=1");
                    }

                }
            }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/affair/affairGeneralSituation/treeData2",function(data){
            var tree = $.fn.zTree.init($("#ztree"), setting, data);
            tree.expandAll(true);
            var node = tree.getNodes()[0];
           /* if(node.children != null && node.children != "" && node.children != undefined){
                node = node.children[0];
            }
            if(node.children != null && node.children != "" && node.children != undefined){
                node = node.children[0];
            }*/
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
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }

</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>