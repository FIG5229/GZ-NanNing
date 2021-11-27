    <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>一键云搜</title>
<%--    <meta name="decorator" content="default"/>--%>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" />
    <script src="${ctxStatic}/bootstrap-select/bootstrap-select.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/bootstrap/2.3.1/css_default/bootstrap.css" rel="stylesheet" />
    <link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript"  src="${ctxStatic}/js/common.js"></script>
    <style type="text/css">
        .ipt-wrap {
            float: left;
            width: 680px;
            height: 48px;
        }

        .ipt-wrap input {
            width: 680px;
            height: 48px;
        }

        .search-btn {
            float: left;
            width: 108px;
            height: 48px;
            line-height: 48px;
            background: rgba(208, 40, 46, 1);
            text-align: center;
            font-size: 16px;
            font-weight: 500;
            color: rgba(255, 255, 255, 1);
            cursor: pointer;
        }

        .search-tag {
            overflow: hidden;
        }

        .search-tag .active {
            display: block;
        }

        .search-tip,
        .search-select {
            display: none;
            float: left;
            color: #BFBFBF;
        }

        .search-toggle {
            float: left;
            color: #BFBFBF;
            cursor: pointer;
        }

        .search-toggle i {
            padding-right: 5px;
        }

        .search-list {
            padding: 25px 5px 5px 5px;
        }

        .search-item {
            margin-bottom: 30px;
            height: 150px;
        }

        .search-name {
            font-size: 16px;
            font-weight: 500;
            color: #000;
            line-height: 24px;
        }

        .search-row {
            width: 1000px;
            overflow: hidden;
        }

        .search-pic {
            width: 96px;
            height: 108px;
            float: left;
            margin-right: 24px;
        }

        .search-pic img {
            width: 100%;
            height: 100%;
        }

        .search-info {
            width: 880px;
            padding-top: 5px;
            float: left;
            overflow: hidden;
        }

        .search-text {
            height: 20px;
            line-height: 20px;
        }

        .search-class span {
            padding-right: 16px;
            font-size: 14px;
            font-weight: 400;
            color: rgba(208, 40, 46, 1);
            line-height: 21px;
            cursor: pointer;
        }


    </style>
    <script type="text/javascript">

        function openlDialog(idNumber) {
            var url = "iframe:${ctx}/personnel/personnelBase/nav?idNumber="+idNumber;
            top.$.jBox.open(url, "",1000,610,{
                buttons:{"关闭":true},
                loaded:function(h){
                    $(".jbox-content", top.document).css("overflow-y","hidden");
                }
            });
        };

        $(document).ready(function () {
            $("#slpk").on("change", function() {
                if ($("#op0").is(':selected')) {
                    $("#base").css('display','inline-block')
                }else {
                    $("#base").css('display','none')
                }
                if ($("#op1").is(':selected')){
                    $("#reward").css('display','inline-block')
                } else {
                    $("#reward").css('display','none')
                }
                if ($("#op2").is(':selected')){
                    $("#punish").css('display','inline-block')
                }else {
                    $("#punish").css('display','none')
                }
                if ($("#op3").is(':selected')){
                    $("#yearCheck").css('display','inline-block')
                }else {
                    $("#yearCheck").css('display','none')
                }
                if ($("#op4").is(':selected')){
                    $("#xueli").css('display','inline-block')
                }else {
                    $("#xueli").css('display','none')
                }
            })
        });

    </script>
</head>
<body>
<br/>
<form:form id="inputForm" class="form-horizontal" action="${ctx}/search/findList"  modelAttribute="searchFor" method="post">
    <div class="control-group" style="border: none;">
        <div class="ipt-wrap">
            <input type="text" id="search-content" name="search-content" value="${searchContent}"/>
<%--            <form:input path="searchContent" type="text" id="search-content" name="search-content" value="${searchContent}"/>--%>
        </div>
        <div class="search-btn" onclick="document.getElementById('inputForm').submit()"><i class="icon-search"></i>搜索
        </div><font color="red">请按照"性别：男；政治面貌：中共党员；年龄：40；"的格式输入(年龄是40以下的)，请用中文标点符号</font>
    </div>
    <div class="search-toggle"><i class="icon-filter"></i>搜索工具</div>
    <div class="search-tag">
        <div class="search-tip active">为您找到相关搜索个</div>
        <div class="search-select" style="height: 600px">
            <select id="slpk" class="selectpicker" data-live-search="true" multiple>
                <option id="op0" value="0">人员基本信息</option>
                <option id="op1" value="1">奖励信息</option>
                <option id="op2" value="2">惩戒信息</option>
                <option id="op3" value="3">年度考核信息</option>
                <option id="op4" value="4">学历信息</option>
            </select>
            <div>
                <div class="breadcrumb form-search"  id="base" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">性别：</label>
                            <select class="input-xlarge " id="sex">
                                <option></option>
                                <c:forEach items="${fns:getDictList('sex')}" var="sex">
                                    <option value="${sex.value}" label="${sex.label}">${sex.label}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="sexinfo" name="sexhidden" value="${sex}">
<%--                            <form:input path="sex" type="hidden" id="sexinfo" name="sexhidden" value="${sex}"/>--%>
                        </li>
                        <li>
                            <label class="control-label">民族：</label>
                            <select class="input-xlarge " id="nation">
                                <option></option>
                                <c:forEach items="${fns:getDictList('nation')}" var="nation">
                                    <option value="${nation.value}" label="${nation.label}">${nation.label}</option>
                                </c:forEach>
                            </select>
                                                                <input type="hidden" id="nationinfo" name="nahidden" value="${nation}">
<%--                            <form:input path="nation" type="hidden" id="nationinfo" name="nahidden" value="${nation}"/>--%>
                        </li>
                        <li>
                            <label class="control-label">政治面貌：</label>
                            <select class="input-xlarge " id="mianmao">
                                <option></option>
                                <c:forEach items="${fns:getDictList('political_status')}" var="mianmao">
                                    <option value="${mianmao.value}" label="${mianmao.label}">${mianmao.label}</option>
                                </c:forEach>
                            </select>
                                                                <input type="hidden" id="mminfo" name="mmhidden" value="${mianmao}">
<%--                            <form:input path="mianmao" type="hidden" id="mminfo" name="mmhidden" value="${mianmao}"/>--%>
                        </li>
                        <li>
                            <label class="control-label">工作单位名称：</label>
                            <div class="controls">
                                <sys:treeselect id="workunitName" name="workunitId" value="${personnelBase.workunitId}" labelName="workunitName" labelValue="${personnelBase.workunitName}"
                                                title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" cssStyle="height:30px"/>
                            </div>
                            <input type="hidden" id="wuinfo" name="wuinfo" value="${workunitName}">
                                <%--                            <form:input path="mianmao" type="hidden" id="wuinfo" name="workunitId" value="${workunitName}"/>--%>
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="reward" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">批准机关类别：</label>
                            <select class="input-xlarge " id="approcalOrgType">
                                <option></option>
                                <c:forEach items="${fns:getDictList('personnel_jgtype')}" var="approcalOrgType">
                                    <option value="${approcalOrgType.value}" label="${approcalOrgType.label}">${approcalOrgType.label}</option>
                                </c:forEach>
                            </select>
                                                                <input type="hidden" id="approcalOrgTypeInfo" name="orghidden" value="${approcalOrgType}">
<%--                            <form:input path="approcalOrgType" type="hidden" id="approcalOrgTypeInfo" name="orghidden" value="${approcalOrgType}"/>--%>
                        </li>
                        <li>
                            奖励名称：<input id="rewardName" name="rewardName">
                                                                <input type="hidden" id="rewardNameInfo" name="rewardNameInfo" value="${rewardName}">
<%--                            <form:input path="rewardName" type="rewardNameInfo" id="rewardNameInfo" name="orghidden" value="${rewardName}"/>--%>
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="punish" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">惩戒类别：</label>
                            <div class="controls">
                                <select class="input-xlarge " id="cjType">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('personnel_cjtype')}" var="cjType">
                                        <option value="${cjType.value}" label="${cjType.label}">${cjType.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="cjTypeInfo" name="cjtypehidden" value="${cjType}">
<%--                                <form:input path="cjType" type="hidden" id="cjTypeInfo" name="cjtypehidden" value="${cjType}"/>--%>
                            </div>
                        </li>
                        <li>
                            <div class="controls">
                                惩戒名称：<input id="cjName" name="cjName">
                                <input type="hidden" id="cjNameInfo" name="cjNameInfo" value="${cjName}">
<%--                                <form:input path="cjName" type="hidden" id="cjNameInfo" name="cjNameInfo" value="${cjName}"/>--%>
                            </div>
                        </li>
                        <li>
                            <label class="control-label">惩戒批准机关类别：</label>
                            <div class="controls">
                                <select class="input-xlarge " id="cjOrgType">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('personnel_jgtype')}" var="cjOrgType">
                                        <option value="${cjOrgType.value}" label="${cjOrgType.label}">${cjOrgType.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="cjOrgTypeInfo" name="cothidden" value="${cjOrgType}">
<%--                                <form:input path="cjOrgType" type="hidden" id="cjOrgTypeInfo" name="cothidden" value="${cjOrgType}"/>--%>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="yearCheck" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <div class="controls">
                                考核年度：<input id="year" name="year">
                                <input type="hidden" id="yearInfo" name="yearInfo" value="${year}">
<%--                                <form:input path="year" type="hidden" id="yearInfo" name="yearInfo" value="${year}"/>--%>
                            </div>
                        </li>
                        <li>
                            <div class="controls">
                                考核结论：<input id="conclusion" name="conclusion">
                                <input type="hidden" id="conclusionInfo" name="cjNameInfo" value="${conclusion}">
<%--                                <form:input path="conclusion" type="hidden" id="conclusionInfo" name="cjNameInfo" value="${conclusion}"/>--%>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="breadcrumb form-search" id="xueli" style="display: none">
                <ul class="ul-form">
                    <li>
                        <div class="controls">
                            学历名称：<input id="xlName" name="xlName">
                            <input type="hidden" id="xlNameInfo" name="cjNameInfo" value="${xlName}">
<%--                            <form:input path="xlName" type="hidden" id="xlNameInfo" name="cjNameInfo" value="${xlName}"/>--%>
                        </div>
                    </li>
                    <li>
                        <div class="controls">
                            毕业院校名称：<input id="schoolName" name="schoolName">
                            <input type="hidden" id="schoolNameInfo" name="cjNameInfo" value="${schoolName}">
<%--                            <form:input path="schoolName" type="hidden" id="schoolNameInfo" name="cjNameInfo" value="${schoolName}"/>--%>
                        </div>
                    </li>
                </ul>


            </div>
        </div>

    </div>
    <div class="search-list">
        </br>
        <c:forEach items="${list}" var="items">
            <div class="search-item" style="height: 150px;">
                <div class="search-name">${items.name}</div>
                <div class="search-row">
                    <div class="search-pic"></div>
                    <div class="search-info">
                            ${items.result}
                        <div>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">教育培训(进修)信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">奖励信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">出国境信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">更多 >> </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="pagination">${page}</div>
</form:form>
<%--<form id="inputForm" class="form-horizontal" action="${ctx}/search/findList" modelattribute="" ttribute="searchFor">
    <div class="control-group" style="border: none;">
        <div class="ipt-wrap"><input type="text" id="search-content" name="search-content" value="${searchContent}"/>
    </div>
    <div class="search-btn" onclick="document.getElementById('inputForm').submit()"><i class="icon-search"></i>搜索
    </div><font color="red">请按照"性别：男；政治面貌：中共党员；年龄：40；"的格式输入(年龄是40以下的)，请用中文标点符号</font>
    </div>
    <div class="search-toggle"><i class="icon-filter"></i>搜索工具</div>
    <div class="search-tag">
        <div class="search-tip active">为您找到相关搜索个</div>
        <div class="search-select" style="height: 600px">
            <select id="slpk" class="selectpicker" data-live-search="true" multiple>
                <option id="op0" value="0">人员基本信息</option>
                <option id="op1" value="1">奖励信息</option>
                <option id="op2" value="2">惩戒信息</option>
                <option id="op3" value="3">年度考核信息</option>
                <option id="op4" value="4">学历信息</option>
            </select>
            <div>
                <div class="breadcrumb form-search"  id="base" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">性别：</label>
                                <select class="input-xlarge " id="sex">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('sex')}" var="sex">
                                        <option value="${sex.value}" label="${sex.label}">${sex.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="sexinfo" name="sexhidden" value="${sex}">
&lt;%&ndash;                           <form:input path="sex" type="hidden" id="sexinfo" name="sexhidden" value="${sex}"/>&ndash;%&gt;
                        </li>
                        <li>
                            <label class="control-label">民族：</label>
                                <select class="input-xlarge " id="nation">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('nation')}" var="nation">
                                        <option value="${nation.value}" label="${nation.label}">${nation.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="nationinfo" name="nahidden" value="${nation}">
&lt;%&ndash;                            <form:input path="nation" type="hidden" id="nationinfo" name="nahidden" value="${nation}"/>&ndash;%&gt;
                        </li>
                        <li>
                            <label class="control-label">政治面貌：</label>
                                <select class="input-xlarge " id="mianmao">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('political_status')}" var="mianmao">
                                        <option value="${mianmao.value}" label="${mianmao.label}">${mianmao.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="mminfo" name="mmhidden" value="${mianmao}">
&lt;%&ndash;                            <form:input path="mianmao" type="hidden" id="mminfo" name="mmhidden" value="${mianmao}"/>&ndash;%&gt;
                        </li>
                        <li>
                            <label class="control-label">工作单位名称：</label>
                            <div class="controls">
                                <sys:treeselect id="workunitName" name="workunitId" value="${personnelBase.workunitId}" labelName="workunitName" labelValue="${personnelBase.workunitName}"
                                                title="单位" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" cssStyle="height:30px"/>
                            </div>
                            <input type="hidden" id="wuinfo" name="wuinfo" value="${workunitName}">
&lt;%&ndash;                            <form:input path="mianmao" type="hidden" id="wuinfo" name="workunitId" value="${workunitName}"/>&ndash;%&gt;
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="reward" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">批准机关类别：</label>
                                <select class="input-xlarge " id="approcalOrgType">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('personnel_jgtype')}" var="approcalOrgType">
                                        <option value="${approcalOrgType.value}" label="${approcalOrgType.label}">${approcalOrgType.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="approcalOrgTypeInfo" name="orghidden" value="${approcalOrgType}">
&lt;%&ndash;                            <form:input path="approcalOrgType" type="hidden" id="approcalOrgTypeInfo" name="orghidden" value="${approcalOrgType}"/>&ndash;%&gt;
                        </li>
                        <li>
                                奖励名称：<input id="rewardName" name="rewardName">
                                <input type="hidden" id="rewardNameInfo" name="rewardNameInfo" value="${rewardName}">
&lt;%&ndash;                            <form:input path="rewardName" type="rewardNameInfo" id="rewardNameInfo" name="orghidden" value="${rewardName}"/>&ndash;%&gt;
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="punish" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <label class="control-label">惩戒类别：</label>
                            <div class="controls">
                                <select class="input-xlarge " id="cjType">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('personnel_cjtype')}" var="cjType">
                                        <option value="${cjType.value}" label="${cjType.label}">${cjType.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="cjTypeInfo" name="cjtypehidden" value="${cjType}">
&lt;%&ndash;                                <form:input path="cjType" type="hidden" id="cjTypeInfo" name="cjtypehidden" value="${cjType}"/>&ndash;%&gt;
                            </div>
                        </li>
                        <li>
                            <div class="controls">
                                惩戒名称：<input id="cjName" name="cjName">
                                <input type="hidden" id="cjNameInfo" name="cjNameInfo" value="${cjName}">
&lt;%&ndash;                                <form:input path="cjName" type="hidden" id="cjNameInfo" name="cjNameInfo" value="${cjName}"/>&ndash;%&gt;
                            </div>
                        </li>
                        <li>
                            <label class="control-label">惩戒批准机关类别：</label>
                            <div class="controls">
                                <select class="input-xlarge " id="cjOrgType">
                                    <option></option>
                                    <c:forEach items="${fns:getDictList('personnel_jgtype')}" var="cjOrgType">
                                        <option value="${cjOrgType.value}" label="${cjOrgType.label}">${cjOrgType.label}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="cjOrgTypeInfo" name="cothidden" value="${cjOrgType}">
&lt;%&ndash;                                <form:input path="cjOrgType" type="hidden" id="cjOrgTypeInfo" name="cothidden" value="${cjOrgType}"/>&ndash;%&gt;
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="breadcrumb form-search" id="yearCheck" style="display: none">
                    <ul class="ul-form">
                        <li>
                            <div class="controls">
                                考核年度：<input id="year" name="year">
                                <input type="hidden" id="yearInfo" name="yearInfo" value="${year}">
&lt;%&ndash;                                <form:input path="year" type="hidden" id="yearInfo" name="yearInfo" value="${year}"/>&ndash;%&gt;
                            </div>
                        </li>
                        <li>
                            <div class="controls">
                                考核结论：<input id="conclusion" name="conclusion">
                                <input type="hidden" id="conclusionInfo" name="cjNameInfo" value="${conclusion}">
&lt;%&ndash;                                <form:input path="conclusion" type="hidden" id="conclusionInfo" name="cjNameInfo" value="${conclusion}"/>&ndash;%&gt;
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="breadcrumb form-search" id="xueli" style="display: none">
                <ul class="ul-form">
                    <li>
                        <div class="controls">
                            学历名称：<input id="xlName" name="xlName">
                            <input type="hidden" id="xlNameInfo" name="cjNameInfo" value="${xlName}">
&lt;%&ndash;                            <form:input path="xlName" type="hidden" id="xlNameInfo" name="cjNameInfo" value="${xlName}"/>&ndash;%&gt;
                        </div>
                    </li>
                    <li>
                        <div class="controls">
                            毕业院校名称：<input id="schoolName" name="schoolName">
                            <input type="hidden" id="schoolNameInfo" name="cjNameInfo" value="${schoolName}">
&lt;%&ndash;                            <form:input path="schoolName" type="hidden" id="schoolNameInfo" name="cjNameInfo" value="${schoolName}"/>&ndash;%&gt;
                        </div>
                    </li>
                </ul>


            </div>
        </div>

    </div>
    <div class="search-list">
        </br>
        <c:forEach items="${list}" var="items">
            <div class="search-item" style="height: 150px;">
                <div class="search-name">${items.name}</div>
                <div class="search-row">
                    <div class="search-pic"></div>
                    <div class="search-info">
                        ${items.result}
                        <div>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">教育培训(进修)信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">奖励信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">出国境信息集 </a>
                            <a href="javascript:void(0)" onclick="openlDialog('${items.idNumber}')">更多 >> </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="pagination">${page}</div>
</form>--%>
<script type="text/javascript">
    $('.search-toggle').click(function () {
        if ($('.search-tip').hasClass('active')) {
            $('.search-tip').removeClass('active');
            $('.search-select').addClass('active');
        } else {
            $('.search-tip').addClass('active');
            $('.search-select').removeClass('active');
        }
    });

     $("#slpk").change(function () {

            $("#sex").change(function () {
                var sex = $("#sex option:selected").val();
                $("#sexinfo").val(sex);
                console.log($("#sexinfo").val())
            });
            $("#nation").change(function () {
                var nation = $("#nation option:selected").val();
                $("#nationinfo").val(nation);
                console.log($("#nationinfo").val())
            });
            $("#mianmao").change(function () {
                var mianmao = $("#mianmao option:selected").val();
                $("#mminfo").val(mianmao);
                console.log($("#mminfo").val())
            });
            $("#approcalOrgType").change(function () {
                var approcalOrgType = $("#approcalOrgType option:selected").val();
                $("#approcalOrgTypeInfo").val(approcalOrgType);
                console.log($("#approcalOrgTypeInfo").val())
            });
            $("#cjType").change(function () {
                var cjType = $("#cjType option:selected").val();
                $("#cjTypeInfo").val(cjType);
                console.log($("#cjTypeInfo").val())
            });
            $("#cjOrgType").change(function () {
                var cjOrgType = $("#cjOrgType option:selected").val();
                $("#cjOrgTypeInfo").val(cjOrgType);
                console.log($("#cjOrgTypeInfo").val())
            });
    })
</script>
</body>
</html>
