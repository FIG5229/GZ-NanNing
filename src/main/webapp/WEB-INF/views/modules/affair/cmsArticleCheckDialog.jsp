<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>审核</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function setCheckType(checkType) {
            if(3== checkType && !(null == $("#officeExamineId").val() || "" ==  $("#officeExamineId").val() ||
                null == $("#officeDirectorExamineId").val() || "" ==  $("#officeDirectorExamineId").val())){
                $.jBox.tip('请选择审核单位');
                return false;
            }
            $("#checkType").val(checkType);
            $.post($("#inputForm").attr('action'), $("#inputForm").serialize(), function(){
                window.parent.window.jBox.close();
            });
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="article" action="${ctx}/affair/article/shenHe" method="post" class="form-horizontal" cssStyle="margin-top: 20px;">
    <form:hidden path="id"/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">审核人：</label><span>${fns:getUser().name}</span>
    </div>
<%--
    <c:choose>
        <c:when test="${fns:getUser().id eq '' || fns:getUser().id eq '' || fns:getUser().id eq ''}">
            <div class="control-group">
                <label class="control-label">处团委意见：</label>
                <form:select path="ctwOpinion" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('affair_tw_opinion')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </c:when>
        <c:when test="${fns:getUser().id eq ''}">
            <div class="control-group">
                <label class="control-label">局团委意见：</label>
                <form:select path="opinion" class="input-xlarge ">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('affair_tw_opinion')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </div>
        </c:when>
    </c:choose>
--%>

    <input id="checkType" name="checkType"  type="hidden"/>
    <div class="controls">
    <c:choose>
        <%--单位领导推送给局办公室--%>
        <%--<c:when test="${fns:getUser().id == article.unitExamineId}">
            <label>转送上一级：</label>
            <form:select path="officeExamineId"  class="input-xlarge required">
                <c:choose>
                <c:when test="${article.unitExamineId == fns:getUser().id}">
                    <shiro:hasRole name="bureau_ld">
                        <form:option value="54e8fb917a8241c08c04bb3dbe4dee46" label="公安局办公室"/>
                    </shiro:hasRole>
                </c:when>
                <c:when test="${article.officeExamineId == fns:getUser().id}">
                    <form:option value="9c38dc31ac364600a6a52179c7af267d" label="办公室主任"/>
                </c:when>
                <c:when test="${article.officeDirectorExamineId == fns:getUser().id}">
                    <form:option value="90e2377d44be4d58a5e4b297df273c89" label="公安局政委"/>
                    <form:option value="373a31be22524878855667db6af7a34a" label="公安局政治部主任"/>
                    <form:option value="44bec9ee797c4f0c9600416332efb530" label="公安局纪委书记"/>
                </c:when>
                <c:otherwise>
&lt;%&ndash;                    <form:option value="账号待添加" label="账号待添加"/>&ndash;%&gt;
                </c:otherwise>
            </c:choose>
            </form:select>
            <div>
                <br><br><br><br>
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并转送" onclick="setCheckType(3)"/>&nbsp;
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(0)"/>&nbsp;
            </div>
        </c:when>--%>
        <%--局办公室推送给办公室主任--%>
        <c:when test="${fns:getUser().id == article.officeExamineId}">
            <label>转送上一级：</label>
            <form:select path="officeDirectorExamineId"  class="input-xlarge required">
                <c:choose>
                    <c:when test="${article.unitExamineId == fns:getUser().id}">
                        <shiro:hasRole name="bureau_ld">
                            <form:option value="54e8fb917a8241c08c04bb3dbe4dee46" label="公安局办公室"/>
                        </shiro:hasRole>
                    </c:when>
                    <c:when test="${article.unitExamineId == fns:getUser().id}">
                        <shiro:hasRole name="bureau_ld">
                            <form:option value="54e8fb917a8241c08c04bb3dbe4dee46" label="公安局办公室"/>
                        </shiro:hasRole>
                    </c:when>
                    <c:when test="${article.officeExamineId == fns:getUser().id}">
                        <form:option value="9c38dc31ac364600a6a52179c7af267d" label="办公室主任"/>
                    </c:when>
                    <c:when test="${article.officeDirectorExamineId == fns:getUser().id}">
                        <form:option value="90e2377d44be4d58a5e4b297df273c89" label="公安局政委"/>
                        <form:option value="373a31be22524878855667db6af7a34a" label="公安局政治部主任"/>
                        <form:option value="44bec9ee797c4f0c9600416332efb530" label="公安局纪委书记"/>
                    </c:when>
                    <c:otherwise>
<%--                        <form:option value="账号待添加" label="账号待添加"/>--%>
                    </c:otherwise>
                </c:choose>
            </form:select>
            <div>
                <br><br><br><br>
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并转送" onclick="setCheckType(2)"/>&nbsp;
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(4)"/>&nbsp;
            </div>
        </c:when>
        <%--办公室主任推送给局领导--%>
        <c:when test="${fns:getUser().id == article.officeDirectorExamineId}">
            <label>转送上一级：</label>
            <form:select path="finalExamineId"  class="input-xlarge required">
                <c:choose>
                    <c:when test="${article.unitExamineId == fns:getUser().id}">
                        <shiro:hasRole name="bureau_ld">
                            <form:option value="54e8fb917a8241c08c04bb3dbe4dee46" label="公安局办公室"/>
                        </shiro:hasRole>
                    </c:when>
                    <c:when test="${article.unitExamineId == fns:getUser().id}">
                        <shiro:hasRole name="bureau_ld">
                            <form:option value="54e8fb917a8241c08c04bb3dbe4dee46" label="公安局办公室"/>
                        </shiro:hasRole>
                    </c:when>
                    <c:when test="${article.officeExamineId == fns:getUser().id}">
                        <form:option value="9c38dc31ac364600a6a52179c7af267d" label="办公室主任"/>
                    </c:when>
                    <c:when test="${article.officeDirectorExamineId == fns:getUser().id}">
                        <form:option value="90e2377d44be4d58a5e4b297df273c89" label="公安局政委"/>
                        <form:option value="373a31be22524878855667db6af7a34a" label="公安局政治部主任"/>
                        <form:option value="44bec9ee797c4f0c9600416332efb530" label="公安局纪委书记"/>
                    </c:when>
                    <c:otherwise>
<%--                        <form:option value="账号待添加" label="账号待添加"/>--%>
                    </c:otherwise>
                </c:choose>
            </form:select>
            <div>
                <br><br><br><br>
                <input id="btn" class="btn btn-primary" type="button" value="通过并发布" onclick="setCheckType(0)"/>&nbsp;
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并转送" onclick="setCheckType(3)"/>&nbsp;
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(5)"/>&nbsp;
            </div>
        </c:when>
        <c:when test="${fns:getUser().id == article.finalExamineId}">
            <div>
                <br>
                <br>
                <br>
                <br>
                <input id="btnSubmit" class="btn btn-primary" type="button" value="通过并发布" onclick="setCheckType(0)"/>&nbsp;
                <input id="btnSubmit1" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(6)"/>&nbsp;
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${fns:getUser().id == '90e2377d44be4d58a5e4b297df273c89' || fns:getUser().id == '373a31be22524878855667db6af7a34a' || fns:getUser().id == '44bec9ee797c4f0c9600416332efb530'}">
                <div>
                    <br>
                    <br>
                    <br>
                    <br>
                    <input id="btnSubmit3" class="btn btn-primary" type="button" value="通过并发布" onclick="setCheckType(0)"/>&nbsp;
                    <input id="btnSubmit4" class="btn btn-primary" type="button" value="不通过" onclick="setCheckType(7)"/>&nbsp;
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
        </div>
</form:form>
</body>
</html>