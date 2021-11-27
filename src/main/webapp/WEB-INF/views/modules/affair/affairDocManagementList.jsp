<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文档管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //打开添加/修改页面窗口
        function openEditDialog(url,type) {
            top.$.jBox.open("iframe:" + url, "文档管理"+type, 1000, 600, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairDocManagement"
                }

            });
        }

        //查看详情/下载弹窗
        function openDetailDialog(id, type,title) {
            var url = "iframe:${ctx}/affair/affairDocManagement/formDetail?id=" + id + "&type=" + type;
            top.$.jBox.open(url, title, 800, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairDocManagement"
                }
            });
        }


        /*
        * 发布弹窗
        * */
        function openPropellingDialog(id) {
            var url = "iframe:${ctx}/affair/affairDocManagement/propelling?id=" + id + "&releaseStatus=0";
            top.$.jBox.open(url, "\n" + "发布", 500, 300, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairDocManagement"
                }
            });
        }

        //提交送审
        function submitByIds() {
            if(null == $("#oneCheckManId").val() || "" ==  $("#oneCheckManId").val()){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                //console.log($(this).val())
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                var idsStr = ids.join(",");
                $("#idsValue").val(idsStr);
                $("#searchForm2").submit();
            } else {
                $.jBox.tip('请先选择要提交的内容');
            }
        }
        //打开审核弹窗
        function openShDialog(id) {
            top.$.jBox.open("iframe:${ctx}/affair/affairDocManagement/shenHeDialog?id="+id,"文档管理审核",800,420,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },
                closed:function (){
                    self.location.href="${ctx}/affair/affairDocManagement/list";
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairDocManagement/" style="cursor: pointer">文档管理</a></li>
    <li><a href="${ctx}/affair/affairDocClassify/" style="cursor: pointer">文档分类</a></li>
</ul>

<form:form id="searchForm" modelAttribute="affairDocManagement" action="${ctx}/affair/affairDocManagement/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>文档编码：</label>
            <form:input path="docCode" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>文档名称：</label>
            <form:input path="docName" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>是否公开：</label>
            <form:select path="ispublic" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('docManage_isPublic')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>发布状态：</label>
            <form:select path="releaseStatus" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('certificate_status')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairDocClassify:edit">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openEditDialog('${ctx}/affair/affairDocManagement/form','添加')"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairDocManagement/deleteByIds','checkAll','myCheckBox')"/>
            </li>
        </shiro:hasPermission>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairDocManagement'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll'
                                onclick='chooseAll(this,"myCheckBox")'/></th>
        <th>文档编码</th>
        <th>文档名称</th>
        <th>文档分类</th>
        <th>是否公开</th>
        <th>发布状态</th>
        <th>创建人</th>
        <th>审核状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairDocManagement" varStatus="state">
        <tr>
            <td class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairDocManagement.id}"/>
            </td>
            <td>
                    ${affairDocManagement.docCode}
            </td>
            <td>
                    ${affairDocManagement.docName}
            </td>
            <td>
                    ${affairDocManagement.docClassifyName}
            </td>
            <td>
                    ${fns:getDictLabel(affairDocManagement.ispublic, 'docManage_isPublic', '')}
            </td>

            <td>
                <c:choose>
                    <c:when test="${affairDocManagement.releaseStatus == '0'}">
                        <a onclick="openPropellingDialog('${affairDocManagement.id}')"
                           style="cursor: pointer">${fns:getDictLabel(affairDocManagement.releaseStatus, 'certificate_status', '')}</a>
                    </c:when>
                    <c:otherwise>
                        ${fns:getDictLabel(affairDocManagement.releaseStatus, 'certificate_status', '')}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                    <%--${userList[state.count-1].name}--%>
                    ${fns:getUserById(affairDocManagement.createBy).getName()}
            </td>
            <td>
                    ${fns:getDictLabel(affairDocManagement.checkType, 'check_type', '')}
            </td>
            <td>
                <a onclick="openDetailDialog('${affairDocManagement.id}','see','文档详情')" style="cursor: pointer">查看</a>
                <shiro:hasPermission name="affair:affairDocManagement:edit">
                    <a onclick="openEditDialog('${ctx}/affair/affairDocManagement/form?id=${affairDocManagement.id}','修改')"
                       style="cursor: pointer">修改</a>
                </shiro:hasPermission>
                <c:choose>
                    <c:when test="${affairDocManagement.isdownload == '1'}">
                        <a class="download" onclick="openDetailDialog('${affairDocManagement.id}','down','下载')" style="cursor: pointer">下载</a>
                    </c:when>
                </c:choose>
                <shiro:hasPermission name="affair:affairDocManagement:shenhe">
                     <a onclick="openShDialog('${affairDocManagement.id}')" style="cursor: pointer">审核</a>
                </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<form:form id="searchForm2" modelAttribute="affairDocManagement" action="${ctx}/affair/affairDocManagement/submitByIds" method="post" class="breadcrumb form-search">
    <ul class="ul-form" style="text-align: right">
        <font color="red">请选择审核单位：</font>
        <input type="hidden" name="ids" id="idsValue"/>
        <sys:treeselect id="oneCheckMan" name="oneCheckId" value="${affairDocManagement.oneCheckId}" labelName="oneCheckMan" labelValue="${affairDocManagement.oneCheckMan}"
                        title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息" isAll="true"/>
        <input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
    </ul>
</form:form>
</body>
</html>