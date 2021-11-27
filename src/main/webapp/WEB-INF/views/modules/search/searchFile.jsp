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

        .search-tip {
            display: none;
            float: left;
            color: #BFBFBF;
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
    <li><a href="${ctx}/search/searchStatistics">统计搜索</a></li>
    -->
    <li><a href="${ctx}/search/searchInstitution">机构搜索</a></li>
    <li class="active"><a href="${ctx}/search/searchFile">文件搜索</a></li>
</ul>
<br/>
<form id="inputForm" class="form-horizontal" action="${ctx}/search/formSearchFile">
    <div class="control-group" style="border: none;">
        <div class="ipt-wrap"><input type="text" id="search-content" name="search-content" value="${searchContent}"/>
        </div>
        <div class="search-btn" onclick="document.getElementById('inputForm').submit()"><i class="icon-search"></i>搜索
        </div>
    </div>
    <div class="search-tag">
        <div class="search-tip active">为您找到相关搜索${requestScope.esAttachmentPagerSearchResult.total}个</div>
    </div>
    <div class="search-list">
        <c:forEach items="${requestScope.esAttachmentPagerSearchResult.datas}" var="items">
        <div class="search-item">
            <div class="search-row">
                <div class="search-info">
                    <p class="search-text line1Hidden">文件名称：${items.fileName}</p>
                    <p class="search-text line1Hidden">基本信息：${items.fileText}</p>
                    <div>
                        <a href="">在线查看 </a>
                        <a href="">下载 </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </c:forEach>
    </div>
    <div class="pagination">${page}</div>
</form>
<script type="text/javascript">

</script>
</body>
</html>
