<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>队伍作风纪律整顿管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#print").click(function(){
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint:function (){
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                    }
                });
            });
            //默认显示某个输入框
            var reason = "${reason}";
            if(reason != null && reason != '' && typeof reason != undefined){
                $("#reason").val(${reason}).trigger("change");
            }
            $("#reason").change(function(){
                $("#nd").val("");
                $("#sd").val("");
                $("#ed").val("");
                $("#qt").val("");
                year();
                console.log($("#reason").val())
            });
            function year() {
                if ($("#reason").val() == '1') {
                    $("#rd").attr("style","display:none");
                    $("#yr").attr("style","display:block");
                    $("#oy").attr("style","display:none")
                }else if($("#reason").val() == '2') {
                    $("#rd").attr("style","display:block");
                    $("#yr").attr("style","display:none");
                    $("#oy").attr("style","display:none")
                }else if($("#reason").val() == '3'){
                    $("#rd").attr("style","display:none");
                    $("#yr").attr("style","display:none");
                    $("#oy").attr("style","display:block")
                }else {
                    $("#rd").attr("style","display:none");
                    $("#yr").attr("style","display:block");
                    $("#oy").attr("style","display:none")
                }
            }
            year();
        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //弹出新的窗体
        function signDialog(noticeId){
            top.$.jBox.open("iframe:${ctx}/affair/affairTeamDisciplineSign?noticeId="+noticeId, "签收状态列表",700,400,{
                buttons:{"确定":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
        //批量发布
        function publishByIds() {
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                console.log($(this).val());
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                $.ajax({
                    type:"post",
                    url:"${ctx}/affair/affairTeamDiscipline/publishByIds",
                    data:{ids:ids},
                    dataType:"json",
                    success:function(data){
                        $.jBox.tip(data.message);
                        resetCheckBox("checkAll","myCheckBox");
                        location.reload();
                    }
                })
            } else {
                $.jBox.tip('请先选择要发布的内容');
            }
        }
        //批量取消发布
        function cancelByIds() {
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                console.log($(this).val());
                ids.push($(this).val());
            });
            if (ids.length > 0) {
                $.ajax({
                    type:"post",
                    url:"${ctx}/affair/affairTeamDiscipline/cancelByIds",
                    data:{ids:ids},
                    dataType:"json",
                    success:function(data){
                        $.jBox.tip(data.message);
                        resetCheckBox("checkAll","myCheckBox");
                        location.reload();
                    }
                })
            } else {
                $.jBox.tip('请先选择要取消发布的内容');
            }
        }
        //添加修改弹窗
        function openAddEditDialog(id) {
            var url = "iframe:${ctx}/affair/affairTeamDiscipline/form?id="+id;
            if (id == null || id == undefined){
                url = "iframe:${ctx}/affair/affairFileNotice/form";
            }
            top.$.jBox.open(url, "通知通报文件",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){
                    self.location.href="${ctx}/affair/affairTeamDiscipline/manageList?";
                }
            });
        }
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairTeamDiscipline/formDetail?id="+id;
            top.$.jBox.open(url, "通知通报文件",1000,600,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        }
        function openForm(url) {
            top.$.jBox.open("iframe:"+url, "纪律作风教育整顿",800,520,{
                buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairTeamDiscipline/manageList"}
            });
        }
    </script>
<head>
    <title>Title</title>
</head>
<body>
<ul class="nav nav-tabs">
    <li ><a href="${ctx}/affair/affairLzxxjyActivities/">廉政教育</a></li>
    <shiro:hasPermission name="affair:affairLzxxjyActivities:manage">
        <li><a href="${ctx}/affair/affairLzxxjyActivities/manageList">廉政教育审核</a></li>
    </shiro:hasPermission>
    <li><a href="${ctx}/affair/affairTeamDiscipline/">纪律作风教育整顿</a></li>
    <shiro:hasPermission name="affair:affairTeamDiscipline:manage">
        <li class="active"><a href="${ctx}/affair/affairTeamDiscipline/manageList">纪律作风教育整顿审核</a></li>
    </shiro:hasPermission>
    <li ><a href="${ctx}/affair/affairEmpiricalPractice/">信息简报/经验交流</a></li>
    <%--<shiro:hasPermission name="affair:affairTeamDiscipline:edit"><li><a href="${ctx}/affair/affairTeamDiscipline/form">队伍作风纪律整顿添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairTeamDiscipline" action="${ctx}/affair/affairTeamDiscipline/manageList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li>
            <label>数据范围：</label>
            <select id="reason" style="width: 140px;" name="reason">
                <option value="2">全部</option>
                <option value="3">其他年份</option>
            </select>
        </li>
        <li id="yr"><label>年度：</label>
            <input id="nd" name="startYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTeamDiscipline.startYear}" pattern="yyyy"/>"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>
        <li id="rd"><label style="width: 100px;">活动开始时间：</label>
            <input id="sd" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTeamDiscipline.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input id="ed" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTeamDiscipline.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li id="oy"><label>其他年份：</label>
            <input id="qt" name="otherYear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${affairTeamDiscipline.otherYear}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>
        <li><label>名称：</label>
            <form:input path="eventName" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>单位：</label>
            <sys:treeselect id="unit" name="unitId" value="${affairTeamDiscipline.unitId}" labelName="unit" labelValue="${affairTeamDiscipline.unit}"
                            title="单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
        </li>

        <li><label style="width: 100px">活动结束时间：</label>
            <input name="startFinishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTeamDiscipline.startFinishDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endFinishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTeamDiscipline.endFinishDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairTeamDiscipline:manage">
            <li class="btns"><input  class="btn btn-primary"  type="button" value="添加" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form')"/></li>
            <li class="btns"><input id="sign" class="btn btn-primary" type="button" value="发布" onclick="publishByIds()"/></li>
            <li class="btns"><input id="cancel" class="btn btn-primary" type="button" value="取消发布" onclick="cancelByIds()"/></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
        <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除" onclick="deleteByIds('${ctx}/affair/affairTeamDiscipline/deleteByIds','checkAll','myCheckBox')"/></li>
        </shiro:hasPermission>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印" /></li>
        <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/affair/affairTeamDiscipline/manageList'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
        <th>序号</th>
        <th>活动名称</th>
        <th>单位</th>
        <th>活动开始时间</th>
        <th>发布状态</th>
        <th>签收状态</th>
        <shiro:hasPermission name="affair:affairTeamDiscipline:edit"><th id="handleTh">操作</th></shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairTeamDiscipline" varStatus="status">
        <c:choose>
            <%--<!-- 局机关部门，除了局纪检监察 -->--%>
            <c:when test="${ '1' == fns:getUser().company.id && '6' != fns:getUser().office.id}">
                <%--<!-- 三个处添加的信息看不到 -->--%>
                <c:choose>
                    <c:when test="${ '19e70728419d4051bd4f9f496fbf0d7c' == affairTeamDiscipline.createBy.id || 'affairTeamDiscipline' == affairTeamDiscipline.createBy.id || 'f278b35db9ca4f5d8418cc44acec36de' == affairTeamDiscipline.createBy.id}">

                    </c:when>
                    <%--<!-- 除了三个处添加的信息能够看到 -->--%>
                    <c:otherwise>
                        <tr>
                            <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTeamDiscipline.id}"/></td>
                            <td>
                                    ${(page.pageNo-1)*page.pageSize+status.index+1}
                            </td>
                            <td>
                                    ${affairTeamDiscipline.eventName}
                            </td>
                            <td>
                                    ${affairTeamDiscipline.unit}
                            </td>
                            <td>
                                <fmt:formatDate value="${affairTeamDiscipline.eventDate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td>
                                    ${fns:getDictLabel(affairTeamDiscipline.status, 'affair_publish_status', '')}
                            </td>
                            <td>
                                <c:if test="${not empty affairTeamDiscipline.sumNum && affairTeamDiscipline.sumNum != 0}">
                                    <a href="javascript:void(0)" onclick="signDialog('${affairTeamDiscipline.id}')">${affairTeamDiscipline.signNum}/${affairTeamDiscipline.sumNum}</a>
                                </c:if>
                            </td>
                            <td class="handleTd">
                                <a href="javascript:void(0)" onclick="openDetailDialog('${affairTeamDiscipline.id}')">查看</a>
                                <c:choose>
                                    <c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
                                        <c:choose>
                                            <c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairTeamDiscipline.createBy.id || 'd5ec905f77714c6f8a216e5cbd14ff67'  == affairTeamDiscipline.createBy.id}">}">

                                            </c:when>
                                            <c:otherwise>
                                                <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>
                                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/zhuanfa?id=${affairTeamDiscipline.id}')">转发</a>

                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                                                    <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                                                </shiro:hasPermission>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                                            <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>
                                            <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/zhuanfa?id=${affairTeamDiscipline.id}')">转发</a>

                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                                            <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                                        </shiro:hasPermission>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <tr>
                    <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTeamDiscipline.id}"/></td>
                    <td>
                            ${(page.pageNo-1)*page.pageSize+status.index+1}
                    </td>
                    <td>
                            ${affairTeamDiscipline.eventName}
                    </td>
                    <td>
                            ${affairTeamDiscipline.unit}
                    </td>
                    <td>
                        <fmt:formatDate value="${affairTeamDiscipline.eventDate}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>
                            ${fns:getDictLabel(affairTeamDiscipline.status, 'affair_publish_status', '')}
                    </td>
                    <td>
                        <c:if test="${not empty affairTeamDiscipline.sumNum && affairTeamDiscipline.sumNum != 0}">
                            <a href="javascript:void(0)" onclick="signDialog('${affairTeamDiscipline.id}')">${affairTeamDiscipline.signNum}/${affairTeamDiscipline.sumNum}</a>
                        </c:if>
                    </td>
                    <td class="handleTd">
                        <a href="javascript:void(0)" onclick="openDetailDialog('${affairTeamDiscipline.id}')">查看</a>
                        <c:choose>
                            <c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
                                <c:choose>
                                    <c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairTeamDiscipline.createBy.id || 'd5ec905f77714c6f8a216e5cbd14ff67'  == affairTeamDiscipline.createBy.id}">
                                        <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/zhuanfa?id=${affairTeamDiscipline.id}')">转发</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${fns:getUser().id == affairTeamDiscipline.createBy.id}">
                                        <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                                            <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>

                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                                            <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                                        </shiro:hasPermission>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${fns:getUser().id == affairTeamDiscipline.createBy.id}">
                                <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>
<%--                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/zhuanfa?id=${affairTeamDiscipline.id}')">转发</a>--%>

                                </shiro:hasPermission>
                                <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                                    <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                                </shiro:hasPermission>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:otherwise>
        </c:choose>
        <%--<tr>
            <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTeamDiscipline.id}"/></td>
            <td>
                ${status.index+1}
            </td>
            <td>
                ${affairTeamDiscipline.eventName}
            </td>
            <td>
                ${affairTeamDiscipline.unit}
            </td>
            <td>
                <fmt:formatDate value="${affairTeamDiscipline.eventDate}" pattern="yyyy-MM-dd"/>
            </td>
            <td class="handleTd">
                <a onclick="openDetailDialog('${affairTeamDiscipline.id}')">查看</a>
                <c:choose>
                    <c:when test="${  '31' == fns:getUser().office.id || '146'== fns:getUser().office.id || '266'== fns:getUser().office.id }">
                        <c:choose>
                            <c:when test="${'276d8cdc184748c8a5ff014221fb135a' == affairTeamDiscipline.createBy.id}">

                            </c:when>
                            <c:otherwise>
                                <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                                    <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                                </shiro:hasPermission>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <shiro:hasPermission name="affair:affairTeamDiscipline:edit">
                            <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTeamDiscipline/form?id=${affairTeamDiscipline.id}')">修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="affair:affairTeamDiscipline:delete">
                            <a href="${ctx}/affair/affairTeamDiscipline/delete?id=${affairTeamDiscipline.id}" onclick="return confirmx('确认要删除该廉政学习教育活动吗？', this.href)">删除</a>
                        </shiro:hasPermission>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>--%>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
