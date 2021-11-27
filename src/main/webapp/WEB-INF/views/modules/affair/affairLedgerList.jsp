<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>在职干部档案登记花名册管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <script type="text/javascript">
        function changeZaiZhi()
        {
            var div1 = document.getElementById("d1");
            var div2 = document.getElementById("d2");
            var div3 = document.getElementById("d3");
            div1.style.display="block";
            div2.style.display="none";
            div3.style.display="none";
            document.getElementById("zaizhi").setAttribute("src","${ctx}/affair/affairArchiveRegister/");
        }
        function changeLiTui()
        {
            var div1 = document.getElementById("d1");
            var div2 = document.getElementById("d2");
            var div3 = document.getElementById("d3");
            div1.style.display="none";
            div2.style.display="block";
            div3.style.display="none";
            document.getElementById("litui").setAttribute("src","${ctx}/affair/affairRetire/");
        }
        function changeSiWang()
        {
            var div1 = document.getElementById("d1");
            var div2 = document.getElementById("d2");
            var div3 = document.getElementById("d3");
            div1.style.display="none";
            div2.style.display="none";
            div3.style.display="block";
            document.getElementById("siwang").setAttribute("src","${ctx}/affair/affairDeath/");
        }
        $(document).ready(function() {
            $("#fileUnit").on('change',function () {
                var flag = $("#fileUnit").val();
                $("#hid").val(flag);
            });
            $("#export").click(
                function(){
                    var submit = function (v, h, f) {
                        if (v == 'all') {
                            $("#searchForm").attr("action","${ctx}/affair/affairArchiveRegister/export?fileUnit="+$("#hid").val());
                            $("#searchForm").submit();
                        }
                        if (v == 'cancel') {
                            $.jBox.tip('已取消');
                        }
                        return true;
                    };
                    $.jBox.confirm("您是否要导出数据？", "数据导出确认", submit, { buttons: { '导出全部数据': 'all','取消':'cancel'} });
                }
            );
        });
        function zaiZhi() {
            $("#zzLi").addClass("active");
            $("#ltLi").removeClass("active");
            $("#swLi").removeClass("active");
            $("#ifr").attr('src','${ctx}/affair/affairArchiveRegister/list?fileUnit='+$("#hid").val())
        }
        function liTui() {
            $("#zzLi").removeClass("active");
            $("#ltLi").addClass("active");
            $("#swLi").removeClass("active");
            $("#ifr").attr('src','${ctx}/affair/affairRetire/list?fileUnit='+$("#hid").val())
        }
        function siWang() {
            $("#zzLi").removeClass("active");
            $("#ltLi").removeClass("active");
            $("#swLi").addClass("active");
            $("#ifr").attr('src','${ctx}/affair/affairDeath/list?fileUnit='+$("#hid").val())
        }
        function chaxun() {
            zaiZhi();
        }
        //批量导出弹窗
        function openAccountExport(url) {
            top.$.jBox.open("iframe:"+url, "批量导出",800,520,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                },closed:function (){self.location.href="${ctx}/affair/affairArchiveRegister/taizhang"}
            });
        }
    </script>


</head>
<form:form id="searchForm" modelAttribute="affairArchiveRegister" action="${ctx}/affair/affairArchiveRegister/" method="post" class="breadcrumb form-search">
<input id="fileName" name="fileName" type="hidden" value="干部档案台账.xlsx"/>
</form:form>
<%--
<ul class="ul-form2">
    <input id="zz" class="btn btn-primary" type="button" value="在职" target="zaizhi" onclick="changeZaiZhi()"/>
    <input id="lt" class="btn btn-primary" type="button" value="离退" target="litui" onclick="changeLiTui()"/>
    <input id="sw" class="btn btn-primary" type="button" value="死亡" target="siwang" onclick="changeSiWang()"/>
</ul>
--%>
<br>
<ul class="ul-form">
        <label>档案管理单位：</label>
        <select name="fileUnit" id="fileUnit" class="input-medium">
            <option  value=""></option>
            <c:forEach items="${fns:getDictList('personnel_daunit')}" var="item" >
                <option value="${item.value}">${item.label}</option>
            </c:forEach>
        </select>
    <input type="hidden" name="hid" id="hid">
     <input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="chaxun()"/>
    <li class="btns"><input  class="btn btn-primary" type="button" value="导出" onclick="openAccountExport('${ctx}/affair/affairLedgerInto/accountExport?fileUnit='+$('#hid').val())"/></li>
</ul>
<ul class="nav nav-tabs">
    <li class="active" id="zzLi"><a href="#" id="zz" onclick="zaiZhi()">在职</a></li>
    <li  id="ltLi"><a href="#" id="lt" onclick="liTui()">离退</a></li>
    <li id="swLi"><a href="#" id="sw" onclick="siWang()">死亡</a></li>
</ul>
<div id="d1" style="display: block">
    <iframe name="ifr" id="ifr" src="" style="" height="600px" width="1200px" frameborder="0" >
    </iframe>
</div>
<%--<div id="d1" style="display: block">
    <iframe name="zaizhi" id="zaizhi" src="${ctx}/affair/affairArchiveRegister/" style="" height="600px" width="1200px" frameborder="0" >
    </iframe>
</div>
<div id="d2" style="display: none">
    <iframe name="litui" id="litui" src="${ctx}/affair/affairRetire/" style="" height="600px" width="1200px" frameborder="0" >
    </iframe>
</div>
<div id="d3" style="display: none">
    <iframe name="siwang" id="siwang" src="${ctx}/affair/affairDeath/" style="" height="600px" width="1200px" frameborder="0" >
    </iframe>
</div>--%>


</body>
</html>

