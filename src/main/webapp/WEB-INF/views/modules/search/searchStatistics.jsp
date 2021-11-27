<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>一键云搜</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .ipt-wrap {
            float: left;
            width: 680px;
            height: 48px;
        }

        .ipt-wrap input {
            width: 680px;
            height: 38px;
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
            width: 680px;
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
            float: right;
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
            height: 124px;
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

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/search/searchPeople">人员搜索</a></li>
    <li><a href="${ctx}/search/searchThing">事务搜索</a></li>
    <!--
    <li  class="active"  ><a href="${ctx}/search/searchStatistics">统计搜索</a></li>
    -->
    <li><a href="${ctx}/search/searchInstitution">机构搜索</a></li>
    <li><a href="${ctx}/search/searchFile">文件搜索</a></li>
</ul>
<br/>
<form id="inputForm" class="form-horizontal" action="${ctx}/search/formSearchPeople">
    <div class="control-group" style="border: none;">
        <div class="ipt-wrap"><input type="text" id="search-content" name="search-content"/></div>
        <div class="search-btn" onclick="document.getElementById('inputForm').submit()"><i class="icon-search"></i>搜索
        </div>
    </div>
    <div class="search-tag">
        <div class="search-tip active">为您找到相关搜索约50个</div>
        <div class="search-select">
            <select id="condition" name="condition">
                <option value="0" selected>所有条件</option>
                <option value="1">姓名</option>
                <option value="2">身份证号</option>
                <option value="3">警号</option>
            </select>
        </div>
        <div class="search-toggle"><i class="icon-filter"></i>搜索工具</div>
    </div>
    <div class="search-list">
<%--        <div class="search-item">--%>
<%--            <div class="search-name">张三丰</div>--%>
<%--            <div class="search-row">--%>
<%--                <div class="search-pic"><img src="../../../../politics/static/images/nav-left-bg.png" alt=""></div>--%>
<%--                <div class="search-info">--%>
<%--                    <p class="search-text line1Hidden">基本信息：姓名：张三丰，性别：男，民族：汉，出生日期：民族：汉，出生日期：1900-09-07</p>--%>
<%--                    <p class="search-text line1Hidden">男出生日期：1998-09-0…--%>
<%--                        XXX，父子，男出生日期：1998-09-0…警衔信息：警衔名称：一级警司，授衔日期：2014-05-01，授衔批准单位名称：南宁铁路公安局… </p>--%>
<%--                    <p class="search-text line1Hidden">家庭成员及社会关系信息：XXX，父子，男出生日期：1998-09-08，…XXX，父子，男出生日期：1998-09-08</p>--%>
<%--                    <p class="search-class line1Hidden">--%>
<%--                        <span>奖励信息集</span>--%>
<%--                        <span class="search-more">更多<i class="icon-angle-right"></i><i--%>
<%--                                class="icon-angle-right"></i></span>--%>
<%--                    </p>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="search-item">--%>
<%--            <div class="search-name">张三丰</div>--%>
<%--            <div class="search-row">--%>
<%--                <div class="search-pic"><img src="../../../../politics/static/images/nav-left-bg.png" alt=""></div>--%>
<%--                <div class="search-info">--%>
<%--                    <p class="search-text line1Hidden">基本信息：姓名：张三丰，性别：男，民族：汉，出生日期：民族：汉，出生日期：1900-09-07</p>--%>
<%--                    <p class="search-text line1Hidden">男出生日期：1998-09-0…--%>
<%--                        XXX，父子，男出生日期：1998-09-0…警衔信息：警衔名称：一级警司，授衔日期：2014-05-01，授衔批准单位名称：南宁铁路公安局… </p>--%>
<%--                    <p class="search-text line1Hidden">家庭成员及社会关系信息：XXX，父子，男出生日期：1998-09-08，…XXX，父子，男出生日期：1998-09-08</p>--%>
<%--                    <p class="search-class line1Hidden">--%>
<%--                        <span>奖励信息集</span>--%>
<%--                        <span class="search-more">更多<i class="icon-angle-right"></i><i--%>
<%--                                class="icon-angle-right"></i></span>--%>
<%--                    </p>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
    <div class="pagination">${page}</div>
</form>
<script type="text/javascript">
    $('.search-toggle').click(function () {
        if ($('.search-tip').hasClass('active')) {
            $('.search-tip').removeClass('active');
            $('.search-select').addClass('active');
        } else {
            $('.search-tip').addClass('active');
            $('.search-select').removeClass('active');
        }
    })
</script>
</body>
</html>
