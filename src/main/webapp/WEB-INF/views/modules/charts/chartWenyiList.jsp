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
                            $("#searchForm").attr("action","${ctx}/affair/affairWenyi/export?flag=true")
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
                }, closed: function () {
                    self.location.href = "${ctx}/affair/affairWenyi"
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
<form:form id="searchForm" modelAttribute="affairWenyi" action="${ctx}/affair/affairWenyi/literaryWorks" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="year" name="year" type="hidden" value="${affairWenyi.year}"/>
    <input id="month" name="month" type="hidden" value="${affairWenyi.month}"/>
    <input id="dateType" name="dateType" type="hidden" value="${affairWenyi.dateType}"/>
    <input id="label" name="label" type="hidden" value="${affairWenyi.label}"/>
    <input name="beginTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
           value="<fmt:formatDate value="${affairWenyi.startDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    <input name="endTime" type="hidden" readonly="readonly" maxlength="20" class="input-medium Wdate"
           value="<fmt:formatDate value="${affairWenyi.endDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
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

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="affairWenyi">
        <tr>

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
                <fmt:formatDate value="${affairWenyi.ratifyTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${affairWenyi.adoptMedia}
            </td>
            <td>
                <fmt:formatDate value="${affairWenyi.adoptTime}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${fns:getDictLabel(affairWenyi.checkType, 'check_type', '')}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>