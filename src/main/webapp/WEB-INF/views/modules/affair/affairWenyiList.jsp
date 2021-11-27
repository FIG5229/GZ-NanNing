<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>文艺作品管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //打印
            $("#print").click(function(){
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $('#checkTh').css('display', 'none');
                $('.checkTd').css('display', 'none');
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
                    afterPrint:function(){
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                        $('#checkTh').css('display', 'table-cell');
                        $('.checkTd').css('display', 'table-cell');
                    }
                });
            });
            //导出
            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairWenyi/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairWenyi/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action","${ctx}/affair/affairWenyi/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action","${ctx}/affair/affairWenyi/");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all', '导出当前页面数据': 'part','取消':'cancel'} });
                }
            );
            //导入
            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_wenyi", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairWenyi"}});
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        /*
        * 文艺作品添加/修改
        * */
        function openDialog(url) {
            top.$.jBox.open("iframe:" + url, "文艺作品", 800, 520, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        }

        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairWenyi/formDetail?id=" + id;
            top.$.jBox.open(url, "文艺作品详情", 1000, 500, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        }
        //提交送审
        function submitByIds() {
            if(null == $("#chuCheckMan").val() || "" ==  $("#chuCheckMan").val()){
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
            top.$.jBox.open("iframe:${ctx}/affair/affairWenyi/shenHeDialog?id="+id,"文艺作品审核",800,420,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },
                closed:function (){
                    self.location.href="${ctx}/affair/affairWenyi/list";
                }
            });
        }
        //推送详情弹窗
        function openPropellingDialog(id) {
            var url = "iframe:${ctx}/affair/affairWenyi/propelling?id="+id;
            top.$.jBox.open(url, "\n" + "数据推送",500,300,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function(){
                    self.location.href="${ctx}/affair/affairWenyi/list";
                }
            });
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairWenyi/">文艺作品</a></li>
   <%-- <li><a href="${ctx}/affair/affairCultureActivity/">文化活动</a></li>--%>
    <li><a href="${ctx}/affair/affairWenhua/">文化人才</a></li>
    <li><a href="${ctx}/affair/affairActivityMien/">活动风采</a></li>
</ul>
<form:form id="searchForm" modelAttribute="affairWenyi" action="${ctx}/affair/affairWenyi/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="5.3文艺作品.xlsx"/>
    <ul class="ul-form">
        <li><label>作品名称：</label>
            <form:input path="proName" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>类别：</label>
            <form:select path="type" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wenyi_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>作者姓名：</label>
            <form:input path="peoName" htmlEscape="false" class="input-medium"/>
        </li>
            <%--11.6问题反馈 管理账号应该能选择全部单位，现在只能选择宣教部门账号（三个公安处的共同问题）kevin.jia--%>
        <c:choose>
            <c:when test="${ 'd30e324c8f73492d9b74103374fbc689' eq fns:getUser().id || 'e3ac8381fb3247e0b64fd6e3c48bddc1' eq fns:getUser().id || '66937439b2124f328d1521968fab06db' eq fns:getUser().id || 'd154234ecb35470e84fb95e53726866b' eq fns:getUser().id}">
                <li><label>单位名称：</label>
                    <sys:treeselect id="unit" name="unitId" value="${affairWenyi.unitId}" labelName="unitName"
                                    labelValue="${affairWenyi.unitName}"
                                    title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                                    notAllowSelectParent="false" isAll="true"/>
                </li>
            </c:when>
            <c:otherwise>
                <li><label>单位名称：</label>
                    <sys:treeselect id="unit" name="unitId" value="${affairWenyi.unitId}" labelName="unitName"
                                    labelValue="${affairWenyi.unitName}"
                                    title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                                    notAllowSelectParent="false"/>
                </li>
            </c:otherwise>
        </c:choose>
        <li><label>奖项名称：</label>
            <form:input path="awardName" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>奖项级别：</label>
            <form:select path="awardLevel" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_wenyi_level')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>批准单位：</label>
            <form:input path="ratifyUnit" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label>批准时间：</label>
            <input name="ratifyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairWenyi.ratifyTime}" pattern="yyyy-MM-dd HH:mm"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});"/>
        </li>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairWenyi:edit">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openDialog('${ctx}/affair/affairWenyi/form')"/></li>
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairWenyi/deleteByIds','checkAll','myCheckBox')"/>
            </li>
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
        </shiro:hasPermission>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairWenyi'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th id="checkTh"><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/></th>
        <th>作品名称</th>
        <th>类别</th>
        <th>作者姓名</th>
        <th>单位名称</th>
        <th>奖项名称</th>
        <th>奖项级别</th>
        <th>批准单位</th>
        <th>批准时间</th>
        <th>采用媒体及版面</th>
        <th>采用时间</th>
        <th>审核状态</th>
        <th id="handleTh">操作</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairWenyi">
        <tr>
            <td  class="checkTd">
                <input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairWenyi.id}"/>
            </td>
            <td>
                    ${affairWenyi.proName}
            </td>
            <td>
                    ${fns:getDictLabel(affairWenyi.type, 'affair_wenyi_type', '')}
            </td>
            <td>
                    ${affairWenyi.peoName}
            </td>
            <td>
                    ${affairWenyi.unitName}
            </td>
            <td>
                    ${affairWenyi.awardName}
            </td>
            <td>
                    ${fns:getDictLabel(affairWenyi.awardLevel, 'affair_wenyi_level', '')}
            </td>
            <td>
                    ${affairWenyi.ratifyUnit}
            </td>
            <td>
                <fmt:formatDate value="${affairWenyi.ratifyTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                    ${affairWenyi.adoptMedia}
            </td>
            <td>
                <fmt:formatDate value="${affairWenyi.adoptTime}" pattern="yyyy-MM-dd HH:mm"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairWenyi.checkType, 'check_type', '')}
            </td>
            <td class="handleTd">
                <a onclick="openDetailDialog('${affairWenyi.id}')" style="cursor: pointer">查看</a>
                <shiro:hasPermission name="affair:affairWenyi:edit">
                    <a href="javascript:void(0)"
                       onclick="openDialog('${ctx}/affair/affairWenyi/form?id=${affairWenyi.id}')">修改</a>
                    <a href="${ctx}/affair/affairWenyi/delete?id=${affairWenyi.id}"
                       onclick="return confirmx('确认要删除该文艺作品吗？', this.href)">删除</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="affair:affairWenyi:shenhe">
                    <c:if test="${'5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">
                        <c:if test="${affairWenyi.chuCheckId == fns:getUser().id || affairWenyi.juCheckId == fns:getUser().id }">
                            <a onclick="openShDialog('${affairWenyi.id}')" style="cursor: pointer">审核</a>
                        </c:if>
                    </c:if>
                </shiro:hasPermission>
                <c:if test="${'5'== fns:getUser().office.id ||  '29'== fns:getUser().office.id || '144'== fns:getUser().office.id || '265'== fns:getUser().office.id }">
                    <a onclick="openPropellingDialog('${affairWenyi.id}')">推送</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<form:form id="searchForm2" modelAttribute="affairWenyi" action="${ctx}/affair/affairWenyi/submitByIds" method="post" class="breadcrumb form-search">
    <ul class="ul-form" style="text-align: right">
        <font color="red">请选择审核单位：</font>
        <input type="hidden" name="ids" id="idsValue"/>
        <form:select id="chuCheckMan" path="chuCheckId"   class="input-xlarge required">
            <form:option value="" label=""/>
            <form:options items="${fns:getDictList('affair_mein_shenhe')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
        </form:select>
        <input  class="btn btn-primary" type="button" value="提交送审" onclick="submitByIds()"/>
    </ul>
</form:form>
</body>
</html>