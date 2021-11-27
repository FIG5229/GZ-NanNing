<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机构管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <style type="text/css">
        .ztree {
            overflow: auto;
            margin: 0;
            _margin-top: 10px;
            padding: 10px 0 0 10px;
        }
    </style>
</head>
<body>
<sys:message content="${message}"/>
<div id="content" class="row-fluid">
    <div id="left">
        <div id="ztree" class="ztree">
        </div>
    </div>
    <div id="right" style="margin-left: 60px;margin-top: 400px;">
        <form:form id="inputForm" modelAttribute="affairGonghuiPersonnel" action="${ctx}/affair/affairGonghuiPersonnel/editGonghui"
                   method="post" class="form-horizontal">
            <input id="editPersonIds" name="editPersonIds" type="hidden" value="${editPersonIds}"/>
            <input id="partBranch" name="partyBranch" type="hidden"/>
            <input id="partBranchId" name="partyBranchId" type="hidden"/>
        </form:form>
        <input id="saveButton" class="btn btn-primary" type="button" value="确定" onclick="save()"/>
    </div>
</div>
<script type="text/javascript">
    var setting = {
        data: {simpleData: {enable: true, idKey: "id", pIdKey: "pId", rootPId: '0'}},
        callback: {
            onClick: function (event, treeId, treeNode) {
                //var id = treeNode.pId == '0' ? '' :treeNode.pId;
                var id = treeNode.pId == '0' ? '' : treeNode.id;
                $("#partBranchId").val(id)
                $("#partBranch").val(treeNode.name)
            }
        }
    };

    function refreshTree() {
        $.getJSON("${ctx}/affair/affairOrganizationBulid/treeData", function (data) {
            //$.fn.zTree.init($("#ztree"), setting, data).expandAll(false);
            var treeObj = $.fn.zTree.init($("#ztree"), setting, data);
            var nodes = treeObj.getNodes();
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
        });
    }

    refreshTree();

    var leftWidth = 550; // 左侧窗口大小
    var htmlObj = $("html"), mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");

    function wSize() {
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x": "hidden", "overflow-y": "hidden"});
        mainObj.css("width", "auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }

    function save() {
        if ($("#partBranchId").val() != '' && $("#partBranchId").val() != undefined) {
            $("#inputForm").submit();
        } else {
            $.jBox.tip('请先选择单位');
        }
    }

    if ("sucess" == "${saveResult}") {
        parent.$.jBox.close();
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
