<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>投诉举报管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#export").click(
                function () {
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairTousujubaoguanli/export");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairTousujubaoguanli/");
                        }
                        if (v == 'part') {
                            $("#searchForm").attr("action", "${ctx}/affair/affairTousujubaoguanli/export?flag=true");
                            $("#searchForm").submit();
                            $("#searchForm").attr("action", "${ctx}/affair/affairTousujubaoguanli/");
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, {
                        buttons: {
                            '导出全部数据': 'all',
                            '导出当前页面数据': 'part',
                            '取消': 'cancel'
                        }
                    });
                }
            );

            $("#btnImport").click(function(){
                top.$.jBox.open("iframe:${ctx}/file/template/download/view?id=affair_affairTousujubaoguanli", "导入",800,520,{itle:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！",closed:function (){self.location.href="${ctx}/affair/affairTousujubaoguanli"}});
            });
        });

        function openForm2(url) {
            top.$.jBox.open("iframe:" + url, "信访举报投诉", 900, 700, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairTousujubaoguanli"
                }
            });
        }

        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/affair/affairTousujubaoguanli/formDetail?id=" + id;
            top.$.jBox.open(url, "信访举报投诉详情", 1200, 800, {
                buttons: {"关闭": true},
                loaded: function (h) {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }
            });
        }
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/affair/affairTousujubaoguanli/zhuanbanDialog?id=" + id, "转办", 800, 420, {
                    buttons: {"关闭": true},
                    loaded: function () {
                        $(".jbox-content", top.document).css("overflow-y", "hidden");
                    }, closed: function () {
                        self.location.href = "${ctx}/affair/affairTousujubaoguanli/";
                    }
                });
            } else {
                $.jBox.tip('请先选择要转办的内容且只能单条转办');
            }
        }

        function submitByIds() {
            if (null == $("#chuCheckManName").val() || "" == $("#chuCheckManName").val()) {
                $.jBox.tip('请选择审核单位');
                return false;
            }
            var ids = [];
            $("input:checkbox[name=myCheckBox]:checked").each(function () {
                console.log($(this).val());
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
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            var reason = "${reason}";
            if(reason != null && reason != '' && typeof reason != undefined){
                $("#reason").val(${reason}).trigger("change");
            }
            $("#reason").change(function () {
                year();
            });

            function year() {
                if ($("#reason").val() == '1') {
                    $("#rd").attr("style", "display:none");
                    $("#yr").attr("style", "display:block")
                } else if ($("#reason").val() == '2') {
                    $("#rd").attr("style", "display:block");
                    $("#yr").attr("style", "display:none")
                } else {
                    $("#rd").attr("style", "display:none");
                    $("#yr").attr("style", "display:none")
                }
            }

            year();

            //处分种类改变时触发事件，联动子选项
            $("#forwardType").change(function () {
                showAndHide();
            });
            $("#ischeck").change(function () {
                isCheck();
            });

            //控制处分种类子选项下拉框的隐藏与显示
            function showAndHide() {
                if ($("#forwardType").val() == '0') {
                    $('#forwardUnit').css('display', 'none');
                } else if ($("#forwardType").val() == '1') {
                    $('#forwardUnit').css('display', 'inline-block');
                }
            }

            function isCheck() {
                if ($("#ischeck").val() == '1') {
                    $('#s2id_defaultSubOption').css('display', 'none');
                    $('#bjtype').css('display', 'inline-block');
                    $('#s2id_bjtype').css('display', 'none');
                } else if ($("#ischeck").val() == '2') {
                    $('#s2id_defaultSubOption').css('display', 'none');
                    $('#s2id_bjtype').css('display', 'inline-block');
                    $('#bjtype').css('display', 'inline-block');
                } else if ($("#ischeck").val() == '3') {
                    $('#s2id_defaultSubOption').css('display', 'none');
                    $('#bjtype').css('display', 'inline-block');
                    $('#s2id_bjtype').css('display', 'none');
                }
            }

            //调用
            showAndHide();
            isCheck();


            //问题性质切换
            $("#questionType").change(function () {
                console.log($("#questionType").val());
                switchType();
            });
            function switchType() {
                if ($("#questionType").val() == '1'){
                    $("#s2id_zjType").css('display', 'inline-block');
                    $("#s2id_sfType").css('display', 'none');
                    $("#sfType").val("");
                    $("#s2id_jjType").css('display', 'none');
                    $("#jjType").val("");
                }else if ($("#questionType").val() == '2'){
                    $("#s2id_zjType").css('display',  'none');
                    $("#zjType").val("");
                    $("#s2id_sfType").css('display', 'inline-block');
                    $("#s2id_jjType").css('display', 'none');
                    $("#jjType").val("");
                }else if ($("#questionType").val() == '3'){
                    $("#s2id_zjType").css('display',  'none');
                    $("#zjType").val("");
                    $("#s2id_sfType").css('display', 'none');
                    $("#sfType").val("");
                    $("#s2id_jjType").css('display','inline-block');
                }
            }
            switchType();


            $("#print").click(function () {
                $('#handleTh').css('display', 'none');
                $('.handleTd').css('display', 'none');
                $("#contentTable").printThis({
                    debug: false,
                    importCSS: false,
                    importStyle: false,
                    printContainer: true,
                    loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css"],
                    pageTitle: "打印",
                    removeInline: false,
                    printDelay: 333,
                    header: null,
                    formValues: false,
                    afterPrint: function () {
                        $('#handleTh').css('display', 'table-cell');
                        $('.handleTd').css('display', 'table-cell');
                    }
                });
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        //添加function,用于打开form添加页面弹窗,需要传一个url
        function openForm(url) {
            top.$.jBox.open("iframe:" + url, "信访举报投诉", 850, 700, {
                buttons: {"关闭": true},
                loaded: function () {
                    $(".jbox-content", top.document).css("overflow-y", "hidden");
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairTousujubaoguanli"
                }
            });
        }


    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairTousujubaoguanli/">信访举报投诉</a></li>
    <%--		<li ><a href="${ctx}/affair/affairTousujubaoguanli/statistic">问题归类</a></li>--%>
</ul>
<form:form id="searchForm" modelAttribute="affairTousujubaoguanli" action="${ctx}/affair/affairTousujubaoguanli/"
           method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="fileName" name="fileName" type="hidden" value="信访举报投诉表.xlsx"/>
    <ul class="ul-form">
        <li>
            <label>数据范围：</label>
            <select id="reason" style="width: 140px;" name="reason">
                <option value="0"></option>
                <option value="1">其他年份</option>
                <option value="2">全部</option>
            </select>
        </li>
        <li id="yr"><label>年度：</label>
            <input name="year" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="${affairTousujubaoguanli.year}"
                   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
        </li>
        <li id="rd"><label>收到时间：</label>
            <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            至
            <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${affairTousujubaoguanli.endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li><label>反映人：</label>
            <form:input path="informer" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label class="width120">被反映人：</label>
            <form:input path="repoter" htmlEscape="false" class="input-medium"/>
        </li>
        <li><label class="width120">被反映人单位：</label>
            <sys:treeselect id="repoterUnit" name="repoterUnitId" value="${affairTousujubaoguanli.repoterUnitId}"
                            labelName="repoterUnit" labelValue="${affairTousujubaoguanli.repoterUnit}"
                            title="被反映人单位" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true"
                            notAllowSelectParent="flase" isAll="true"/>
        </li>
        <li><label>问题类型：</label>
            <form:select id="questionType" path="questionType" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_qtType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <form:select id="zjType" path="zjType" class="input-xlarge " cssStyle="display: none">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_zjType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <form:select id="sfType" path="sfType" class="input-xlarge " cssStyle="display: none">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_sfType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
            <form:select id="jjType" path="jjType" class="input-xlarge " cssStyle="display: none">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_jjType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>媒介类型：</label>
            <form:select path="complaintWay" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_tousu')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
       <%-- <li><label>转批单位：</label>
            <form:select path="forwardType" class="input-xlarge required">
                <form:option value="" label="--"/>
                <form:options items="${fns:getDictList('approval_unit')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <form:input id="forwardUnit" path="forwardUnit" htmlEscape="false" class="input-xlarge required"
                        cssStyle="display: none;"/>
        </li>--%>
        <li><label>收到单位：</label>
            <form:select path="sdUnit" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
            <%--
                        <li><label>录入时间：</label>
                            <input name="startEntryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                                value="<fmt:formatDate value="${affairTousujubaoguanli.startEntryDate}" pattern="yyyy-MM-dd"/>"
                                onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                            至
                            <input name="endEntryDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                                   value="<fmt:formatDate value="${affairTousujubaoguanli.endEntryDate}" pattern="yyyy-MM-dd"/>"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                        </li>--%>
        <li><label>查处单位：</label>
            <form:select path="ccUnit" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_xfjb_unit')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
        <li><label>查办状态：</label>
            <form:select path="ischeck" class="input-xlarge required">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_cbtype')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
                <%--默认下拉框--%>
            <form:select id="defaultSubOption" path="subOption" class="input-xlarge">
                <form:option value="" label=""/>
            </form:select>
                <%--办结子选项下拉框--%>
            <form:select id="bjtype" path="bjType" class="input-xlarge required" cssStyle="display: none;">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('affair_bjtype_sub')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </li>
            <%--<li><label class="width120">是否纪律处分：</label>
                <form:select path="isdispose" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>
            <li><label>是否重复：</label>
                <form:select path="isrepeat" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>--%>
    </ul>
    <ul class="ul-form2">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <shiro:hasPermission name="affair:affairTousujubaoguanli:add">
            <li class="btns"><input class="btn btn-primary" type="button" value="添加"
                                    onclick="openForm2('${ctx}/affair/affairTousujubaoguanli/form')"/></li>
            <li class="btns"><input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
        </shiro:hasPermission>
            <%--<shiro:hasPermission name="affair:affairTousujubaoguanli:turn">
                <li class="btns"><input class="btn btn-primary"  type="button" value="转办" onclick="openDialog('myCheckBox')"/></li>
            </shiro:hasPermission>--%>
        <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
            <li class="btns"><input id="del" class="btn btn-primary" type="button" value="删除"
                                    onclick="deleteByIds('${ctx}/affair/affairTousujubaoguanli/deleteByIds','checkAll','myCheckBox')"/>
            </li>
        </shiro:hasPermission>
        <li class="btns"><input id="export" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="btns"><input id="print" class="btn btn-primary" type="button" value="打印"/></li>
        <li class="btns"><input class="btn btn-primary" type="button" value="重置"
                                onclick="window.location.href='${ctx}/affair/affairTousujubaoguanli'"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th><input style='margin-left:12px' type='checkbox' id='checkAll' onclick='chooseAll(this,"myCheckBox")'/>全选
        </th>
        <th>序号</th>
        <th>反映人</th>
        <th>媒介类型</th>
        <th>被反映人</th>
        <%--				<th>被反映人单位</th>--%>
        <th>问题类型</th>
<%--        <th>批转单位</th>--%>
        <th>收到单位</th>
        <th>收到时间</th>
        <th>是否重复</th>
        <th>查处单位</th>
        <th>查办状态</th>
        <th>是否纪律处分</th>
        <th>转办状态</th>
        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
            <th id="handleTh">操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairTousujubaoguanli" varStatus="status">
        <tr>
            <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${affairTousujubaoguanli.id}"/>
            </td>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    ${affairTousujubaoguanli.informer}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.complaintWay, 'affair_tousu', '')}
            </td>
            <td>
                    ${affairTousujubaoguanli.repoter}
            </td>
                <%--<td>
                    ${affairTousujubaoguanli.repoterUnit}
                </td>--%>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.questionType, 'affair_qtType', '')}
                        <c:if test="${affairTousujubaoguanli.questionType == '1'}">
                            ${fns:getDictLabel(affairTousujubaoguanli.zjType, 'affair_zjType', '')}
                        </c:if>
                        <c:if test="${affairTousujubaoguanli.questionType == '2'}">
                            ${fns:getDictLabel(affairTousujubaoguanli.sfType, 'affair_sfType', '')}
                        </c:if>
                        <c:if test="${affairTousujubaoguanli.questionType == '3'}">
                            ${fns:getDictLabel(affairTousujubaoguanli.jjType, 'affair_jjType', '')}
                        </c:if>
            </td>
          <%--  <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.forwardType, 'approval_unit', '')}
                <c:if test="${affairTousujubaoguanli.forwardType == '2'}">
                    -${affairTousujubaoguanli.forwardUnit}
                </c:if>
            </td>--%>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.sdUnit, 'affair_xfjb_unit', '')}
            </td>
            <td>
                <fmt:formatDate value="${affairTousujubaoguanli.receiveTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.isrepeat, 'yes_no', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.ccUnit, 'affair_xfjb_unit', '')}
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.ischeck, 'affair_cbtype', '')}
                <c:if test="${affairTousujubaoguanli.ischeck == '2'}">
                    -${fns:getDictLabel(affairTousujubaoguanli.subOption, 'affair_bjtype_sub', '')}
                    <c:if test="${not empty affairTousujubaoguanli.result}">
                        -${affairTousujubaoguanli.result}
                    </c:if>
                </c:if>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.isdispose, 'yes_no', '')}
                <c:if test="${affairTousujubaoguanli.isdispose == '0'}">
                    -${fns:getDictLabel(affairTousujubaoguanli.noPunish, 'affair_no_punish_type', '')}
                    <c:if test="${affairTousujubaoguanli.noPunish == '5'}">
                        -${affairTousujubaoguanli.otherMethod}
                    </c:if>
                </c:if>
            </td>
            <td>
                    ${fns:getDictLabel(affairTousujubaoguanli.zbStatus, 'zb_type', '')}
            </td>
            <td class="handleTd">
                <a href="javascript:void(0)" onclick="openDetailDialog('${affairTousujubaoguanli.id}')">查看 </a>
                <c:choose>
                    <%--纪检信息管理帐号办结后可以修改删除--%>
                    <c:when test="${ 'd5ec905f77714c6f8a216e5cbd14ff67' == fns:getUser().id}">
                        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                            <a href="javascript:void(0)"
                               onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
                            <a href="${ctx}/affair/affairTousujubaoguanli/delete?id=${affairTousujubaoguanli.id}"
                               onclick="return confirmx('确认要删除该投诉举报吗？', this.href)">删除</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                            <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                               onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                        </shiro:hasPermission>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${affairTousujubaoguanli.zbStatus == '2'}">

                            </c:when>
                             <c:otherwise>
                                 <c:if test="${'276d8cdc184748c8a5ff014221fb135a' == fns:getUser().id}">
                                     <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                         <a href="javascript:void(0)"
                                            onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
                                         <a href="${ctx}/affair/affairTousujubaoguanli/delete?id=${affairTousujubaoguanli.id}"
                                            onclick="return confirmx('确认要删除该投诉举报吗？', this.href)">删除</a>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                                         <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                            onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                                     </shiro:hasPermission>
                                 </c:if>
                                <c:if test="${'35737e5582804ef08502c7283db5c5cf' == fns:getUser().id}">
                                    <c:if test="${'19e70728419d4051bd4f9f496fbf0d7c' == affairTousujubaoguanli.createBy.id}">
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                            <a href="javascript:void(0)"
                                               onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                                            <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                               onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                                        </shiro:hasPermission>
                                    </c:if>
                                </c:if>
                                <c:if test="${'5a0766c9a3df41a88f5759a29886f1ae' == fns:getUser().id}">
                                    <c:if test="${'49e960f9fe6c4f7786ae894ffac51c7d' == affairTousujubaoguanli.createBy.id}">
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                            <a href="javascript:void(0)"
                                               onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                                            <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                               onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                                        </shiro:hasPermission>
                                    </c:if>
                                </c:if>
                                <c:if test="${'b25351b897104a698accd3583ceba19f' == fns:getUser().id}">
                                    <c:if test="${'f278b35db9ca4f5d8418cc44acec36de' == affairTousujubaoguanli.createBy.id}">
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                            <a href="javascript:void(0)"
                                               onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                                            <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                               onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                                        </shiro:hasPermission>
                                    </c:if>
                                </c:if>
                                <c:if test="${affairTousujubaoguanli.createBy.id == fns:getUser().id}">
                                    <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                        <a href="javascript:void(0)"
                                           onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                                        <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                           onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                                    </shiro:hasPermission>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
                    <%--<shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                        <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
                        <a href="${ctx}/affair/affairTousujubaoguanli/delete?id=${affairTousujubaoguanli.id}"
                           onclick="return confirmx('确认要删除该投诉举报吗？', this.href)">删除</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="affair:affairTousujubaoguanli:settle">
                        <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                           onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                    </shiro:hasPermission>--%>
            </td>
                <%--<shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                    <c:choose>
                        <c:when test="${affairTousujubaoguanli.isBanJie == '1'}">&lt;%&ndash;办结&ndash;%&gt;
                            <c:choose>
                                <c:when test="${isJiJianAdmin == '1'}">&lt;%&ndash;公安局纪检监察管理员&ndash;%&gt;
                                    <a href="javascript:void(0)" onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                </c:when>
                                <c:otherwise>
                                    <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
                                        <a href="${ctx}/affair/affairTousujubaoguanli/delete?id=${affairTousujubaoguanli.id}"
                                           onclick="return confirmx('确认要删除该投诉举报吗？', this.href)">删除</a>
                                    </shiro:hasPermission>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <shiro:hasPermission name="affair:affairTousujubaoguanli:edit">
                                <a href="javascript:void(0)"
                                   onclick="openForm('${ctx}/affair/affairTousujubaoguanli/form?id=${affairTousujubaoguanli.id}')">修改</a>
                                <a href="${ctx}/affair/affairTousujubaoguanli/banJie?id=${affairTousujubaoguanli.id}"
                                   onclick="return confirmx('确认要办结该投诉举报吗？', this.href)">办结</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="affair:affairTousujubaoguanli:delete">
                                <a href="${ctx}/affair/affairTousujubaoguanli/delete?id=${affairTousujubaoguanli.id}"
                                   onclick="return confirmx('确认要删除该投诉举报吗？', this.href)">删除</a>

                            </shiro:hasPermission>
                        </c:otherwise>
                    </c:choose>
                </shiro:hasPermission>--%>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
<shiro:hasPermission name="affair:affairTousujubaoguanli:turn">
    <form:form id="searchForm2" modelAttribute="affairTousujubaoguanli"
               action="${ctx}/affair/affairTousujubaoguanli/submitByIds" method="post" class="breadcrumb form-search">
        <ul class="ul-form" style="text-align: right">
            <font color="red">请选择转办单位：</font>
            <input type="hidden" name="ids" id="idsValue"/>
            <sys:treeselect id="chuCheckMan" name="chuCheckId" value="${affairTousujubaoguanli.chuCheckId}"
                            labelName="chuCheckMan" labelValue="${affairTousujubaoguanli.chuCheckMan}"
                            title="单位" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true"
                            notAllowSelectParent="true" dataMsgRequired="必填信息" isAll="true"/>
            <input class="btn btn-primary" type="button" value="转办"
                   onclick="submitByIds('${ctx}/affair/affairTousujubaoguanli/submitByIds')"/>
        </ul>
    </form:form>
</shiro:hasPermission>
</body>
</html>