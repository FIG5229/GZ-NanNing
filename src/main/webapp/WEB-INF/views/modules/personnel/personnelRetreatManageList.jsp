<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>离退信息管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        //添加功能
        function openForm(url,pUrl) {
            top.$.jBox.open("iframe:"+url, "离退信息管理",800,520,{
                persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href=pUrl}
            });
        }
        //详情弹窗
        function openDetailDialog(id) {
            var url = "iframe:${ctx}/personnel/personnelRetreat/formDetail?id="+id;
            top.$.jBox.open(url, "离退信息",800,500,{
                persistent: true,  //设置点击窗口外不关闭的参数
				buttons:{"关闭":true},
                loaded:function(){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };
        function openDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/personnel/personnelRetreat/shenHeDialog?id="+id, "审核",800,420,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/personnel/personnelRetreat/manage";
                    }
                });
            }else {
                $.jBox.tip('请先选择要审核的内容且只能单条审核');
            }
        }
        //上传弹窗
        function openUploadDialog(myCheckBox) {
            var id = getIds(myCheckBox);
            if (id.length == 1) {
                top.$.jBox.open("iframe:${ctx}/personnel/personnelRetreat/uploadDialog?id="+id, "上传",850,550,{
                    buttons:{"关闭":true},
                    loaded:function(){
                        $(".jbox-content", top.document).css("overflow-y","hidden");
                    },closed:function (){
                        self.location.href="${ctx}/personnel/personnelRetreat/manage";
                    }
                });
            }else {
                $.jBox.tip('请先选择要上传的内容且只能单条上传');
            }
        }
    </script>
</head>
<body>
 <ul class="nav nav-tabs">
     <li ><a href="${ctx}/personnel/personnelRetreat?mType=3">离退休信息</a></li>
     <li class="active"><a href="${ctx}/personnel/personnelRetreat/manage">离退休手续办理</a></li>
     <li ><a href="${ctx}/personnel/personnelRetreatSum/statistic">离退休情况统计</a></li>
 </ul>
      <form:form id="searchForm" modelAttribute="personnelRetreat" action="${ctx}/personnel/personnelRetreat/manage" method="post" class="breadcrumb form-search">
            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
            <input id="idNumber" name="idNumber" type="hidden" value="${personnelRetreat.idNumber}" />
            <input id="isManage" name="isManage" type="hidden" value="${personnelRetreat.isManage}" />
            <input id="fileName" name="fileName" type="hidden" value="离退信息集.xlsx"/>
            <ul class="ul-form">
                <li><label>离退类别：</label>
                    <form:select path="type" class="input-medium">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('personnel_lttype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                </li>
                <li><label>离退日期：</label>
                    <input name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                           value="<fmt:formatDate value="${personnelRetreat.startDate}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                    -
                    <input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                           value="<fmt:formatDate value="${personnelRetreat.endDate}" pattern="yyyy-MM-dd"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                </li>
                <li><label>姓名：</label>
                    <form:input path="name" htmlEscape="false" class="input-medium"/>
                </li>

                <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
                <li class="btns"><input  class="btn btn-primary"  type="button" value="上传" onclick="openUploadDialog('myCheckBox')"/></li>
                <shiro:hasPermission name="personnel:personnelRetreat:manage">
                <li class="btns"><input  class="btn btn-primary" type="button" value="审核" onclick="openDialog('myCheckBox')"/></li>
                </shiro:hasPermission>
                <li class="btns"><input  class="btn btn-primary" type="button" value="重置" onclick="window.location.href='${ctx}/personnel/personnelRetreat/manage'"/></li>

                <li class="clearfix"></li>
            </ul>
        </form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr><c:if test="${mType==null}">
        <th><input style='margin-left:12px' type='checkbox'id='checkAll'onclick='chooseAll(this,"myCheckBox")'/>全选</th>
    </c:if>
        <th>序号</th>
        <th>姓名</th>
        <th>离退类别</th>
        <th>离退前级别</th>
        <th>离退后现管理单位名称</th>
        <th>离退日期</th>
        <th>离退干部现享受待遇</th>
        <th>离退干部现享受待遇类别</th>
        <th>离退休材料</th>
        <th>审核状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="personnelRetreat" varStatus="status">
        <tr><c:if test="${mType==null}">
            <td><input style='margin-left:12px' type='checkbox' name="myCheckBox" value="${personnelRetreat.id}"/></td>
        </c:if>
            <td>
                    ${(page.pageNo-1)*page.pageSize+status.index+1}
            </td>
            <td>
                    <a onclick="openDetailDialog('${personnelRetreat.id}')">${personnelRetreat.name}</a>
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.type, 'personnel_lttype', '')}
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.preLevel, 'personnel_ltqtype', '')}
            </td>
            <td>
                    ${personnelRetreat.nowUnitName}
            </td>
            <td>
                <fmt:formatDate value="${personnelRetreat.date}" pattern="yyyy-MM-dd"/>
            </td>
            <td>
                    ${personnelRetreat.treatment}
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.treatmentType, 'personnel_dytype', '')}
            </td>
            <td>
                    ${personnelRetreat.fileName}
            </td>
            <td>
                    ${fns:getDictLabel(personnelRetreat.status, 'personnel_retreat_status', '')}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
