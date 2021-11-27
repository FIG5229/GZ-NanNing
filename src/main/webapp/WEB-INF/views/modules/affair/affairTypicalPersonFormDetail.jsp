<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet" />
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#print").click(function(){
            $('.download').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function(){
                    $('.download').css('display', '');
                }
            });
        });
    });
</script>
<!-- 详细信息1 -->
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div class="modal-custom-content">
                <div id="contentTable" class="modal-custom-content">
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">姓名：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.name}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">性别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTypicalPerson.sex,'sex','')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">民族：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTypicalPerson.nation, 'nation', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">政治面貌：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTypicalPerson.politicsFace, 'political_status', '')}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.unit}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">职务：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.job}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培树时间：</span>
                                <span class="modal-custom-info2-value"><fmt:formatDate value="${affairTypicalPerson.psTime}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培树级别：</span>
                                <span class="modal-custom-info2-value">${fns:getDictLabel(affairTypicalPerson.psLevel,'affair_approval_unitLevel','')}</span>
                            </div>
                        </div>
                        <div class="modal-custom-info2-col modal-custom-info2-col2">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">曾获荣誉：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.wonHonor}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培树目标：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.psTarget}</span>
                            </div>
                          <%--根据731问题跟踪 改为上传附件
                          <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培树方案：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.psProgramme}</span>
                            </div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培树部门：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.psDepartment}</span>
                            </div>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系人：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.contactPerson}</span>
                            </div>
                       <%--     <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">走访记录：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.visitRecord}</span>
                            </div>--%>
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">推送机构：</span>
                                <span class="modal-custom-info2-value">${affairTypicalPerson.pushOrganization}</span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">走访记录：</span></div>
                        <div class="modal-custom-info2-col">
                            <br>
                            <span class="modal-custom-info2-value">
                                   <table  class="table table-striped table-bordered table-condensed">
                                           <tr>
                                                <th>时间</th>
                                                <th>走访人</th>
                                                <th>单位</th>
                                                <th>职务</th>
                                                <th>检验情况</th>
                                           </tr>
                                       <c:forEach items="${affairTypicalPerson.personNewsList}" var="m" varStatus="status">
                                           <tr>
                                               <td>
                                                   <fmt:formatDate value="${m.time}" pattern="yyyy-MM-dd"/>
                                               </td>
                                               <td>${m.visitors}</td>
                                               <td>${m.unit}</td>
                                               <td>${m.job}</td>
                                               <td>${m.inspection}</td>
                                           </tr>
                                       </c:forEach>
                                       <c:forEach items="${newsList}" var="m" varStatus="status">
                                               <div class="modal-custom-info1-file-item">
                                                   <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                            target="_blank">${m.title}
                                                   </a></span>
                                               </div>
                                       </c:forEach>
                                   </table>
                               </span>

                            <%--根据731问题跟踪 改为上传附件
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span>
                                <span class="modal-custom-info2-value">
                                    <table>
                                        <c:forEach items="${affairTypicalTeam.typicalTeamChildList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${m.url}"  target="_blank">${m.title}</a></span>
                                                </div>
                                        </c:forEach>
                                        <c:forEach items="${newsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                             target="_blank">${m.title}
                                                    </a></span>
                                                </div>
                                        </c:forEach>
                                    </table>
                                </span>
                            </div>--%>
                        </div>
                    </div>

                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">曾获荣誉：</span></div>
                        <div class="modal-custom-info2-col">
                            <br>
                            <span class="modal-custom-info2-value">
                                   <table  class="table table-striped table-bordered table-condensed">
                                           <tr>

                                                <th>奖励名称</th>
                                                <th>奖励文号</th>
                                                <th>批准单位</th>
                                                <th>奖励类别</th>
                                           </tr>
                                       <c:forEach items="${rewardInquireList}" var="reward" varStatus="status">
                                           <tr>
                                               <td>${reward.title}</td>
                                               <td>${reward.fileNo}</td>
                                               <td>${reward.unit}</td>
                                               <td>${fns:getDictLabel(reward.type, 'affair_org_reward_punish', '')}</td>
                                           </tr>
                                       </c:forEach>
                                   </table>
                               </span>

                            <%--根据731问题跟踪 改为上传附件
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span>
                                <span class="modal-custom-info2-value">
                                    <table>
                                        <c:forEach items="${affairTypicalTeam.typicalTeamChildList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${m.url}"  target="_blank">${m.title}</a></span>
                                                </div>
                                        </c:forEach>
                                        <c:forEach items="${newsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                             target="_blank">${m.title}
                                                    </a></span>
                                                </div>
                                        </c:forEach>
                                    </table>
                                </span>
                            </div>--%>
                        </div>
                    </div>

                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">宣传报道：</span></div>
                        <div class="modal-custom-info2-col">
                            <br>
                            <span class="modal-custom-info2-value">
                                   <table  class="table table-striped table-bordered table-condensed">
                                           <tr>
                                                <th>标题</th>
                                           </tr>
                                      <c:forEach items="${articleList}" var="m" varStatus="status">
                                          <tr>
                                              <td>
                                                  <a href="${m.link}"
                                                     target="_blank">${m.title}</a>
                                              </td>

                                          </tr>
                                               <div class="modal-custom-info1-file-item">
                                                   <span></span>
                                               </div>
                                      </c:forEach>
                                   </table>
                               </span>

                            <%--根据731问题跟踪 改为上传附件
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span>
                                <span class="modal-custom-info2-value">
                                    <table>
                                        <c:forEach items="${affairTypicalTeam.typicalTeamChildList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${m.url}"  target="_blank">${m.title}</a></span>
                                                </div>
                                        </c:forEach>
                                        <c:forEach items="${newsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                             target="_blank">${m.title}
                                                    </a></span>
                                                </div>
                                        </c:forEach>
                                    </table>
                                </span>
                            </div>--%>
                        </div>
                    </div>

                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">专题简报：</span></div>
                        <div class="modal-custom-info2-col">
                            <br>
                            <span class="modal-custom-info2-value">
                                   <table  class="table table-striped table-bordered table-condensed">
                                           <tr>

                                                <th>标题</th>
                                                <th>媒体名称</th>
                                                <th>栏目</th>
                                                <th>作者</th>
                                           </tr>
                                      <c:forEach items="${newsList}" var="m" varStatus="status">
                                          <tr>
                                              <td>
                                                  <a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                     target="_blank">${m.title}</a>
                                              </td>
                                              <td>${m.mediaName}</td>
                                              <td>${m.lm}</td>
                                              <td>
                                                  <c:forEach items="${m.affairNewsAuthorList}" var="author" varStatus="status">
                                                      ${author.newsAuthor}
                                                  </c:forEach>
                                              </td>
                                          </tr>
                                          <div class="modal-custom-info1-file-item">
                                                   <span></span>
                                               </div>
                                      </c:forEach>
                                   </table>
                               </span>

                            <%--根据731问题跟踪 改为上传附件
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span>
                                <span class="modal-custom-info2-value">
                                    <table>
                                        <c:forEach items="${affairTypicalTeam.typicalTeamChildList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${m.url}"  target="_blank">${m.title}</a></span>
                                                </div>
                                        </c:forEach>
                                        <c:forEach items="${newsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                             target="_blank">${m.title}
                                                    </a></span>
                                                </div>
                                        </c:forEach>
                                    </table>
                                </span>
                            </div>--%>
                        </div>
                    </div>
                    <%--根据731问题跟踪 改为上传附件
                    <div class="modal-custom-info2">
                        <div class="modal-custom-info2-col modal-custom-info2-col1">
                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">事迹材料：</span>
                                <span class="modal-custom-info2-value">
                                    <table>
                                        <c:forEach items="${affairTypicalPerson.personNewsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${m.url}"  target="_blank">${m.title}</a></span>
                                                </div>
                                        </c:forEach>
                                        <c:forEach items="${newsList}" var="m" varStatus="status">
                                                <div class="modal-custom-info1-file-item">
                                                    <span><a href="${ctx}/affair/affairNews/formDetail?id=${m.id}"
                                                       target="_blank">${m.title}
                                                    </a></span>
                                                </div>
                                        </c:forEach>
                                    </table>
                                </span>
                            </div>
                        </div>
                    </div>--%>

                    <div class="modal-custom-info1-file">
                        <div class="modal-custom-info1-file-1">培树方案：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${programmeFileList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                        <br>
                        <div class="modal-custom-info1-file-1">事迹材料：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${materialsFileList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                        <br>
                        <div class="modal-custom-info1-file-1">附件：</div>
                        <div class="modal-custom-info1-file-r">
                            <c:forEach items="${filePathList}" var="m" varStatus="status">
                                <div class="modal-custom-info1-file-item">
                                    <span>${m.fileName}</span>
                                        <%--<a href="#">在线预览</a>--%>
                                    <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                <div class="modal-custom-info1-bottom">
                    <div id="print" class="modal-custom-info1-btn red">打印</div>
                </div>
            </div>
        </div>
    </div>
</div>