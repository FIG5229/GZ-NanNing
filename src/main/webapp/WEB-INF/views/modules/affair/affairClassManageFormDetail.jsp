<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
<link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            $('.download').css('display', 'none');
            $("#contentTable").printThis({
                debug: false,
                importCSS: true,
                importStyle: true,
                printContainer: true,
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css", "${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css", "${ctxStatic}/common/jeesite.css", "${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint: function () {
                    $('.download').css('display', '');
                }
            });
        });
    });
    function openDetailDialogtwo(id) {
        top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/formDetail?id="+id,"培训班详情",1200,600,{
            buttons:{"关闭":true},
            loaded:function () {
                $(".jbox-content",top.document).css("overflow-y","hidden");
            },closed:function () {self.location.href="${ctx}/affair/affairClassManage"
            }
        });
    }
    function keChengXinXi(id) {
        top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/idList?id="+id,"课程信息",1200,600,{
            buttons:{"关闭":true},
            loaded:function () {
                $(".jbox-content",top.document).css("overflow-y","hidden");
            },closed:function () {self.location.href="${ctx}/affair/affairClassManage"
            }
        });
    }
    function renYuanXinXi(id) {
        top.$.jBox.open("iframe:${ctx}/affair/affairClassManage/idListRenYuan?id="+id,"人员信息",1200,600,{
            buttons:{"关闭":true},
            loaded:function () {
                $(".jbox-content",top.document).css("overflow-y","hidden");
            },closed:function () {self.location.href="${ctx}/affair/affairClassManage"
            }
        });
    }
</script>

<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/affair/affairClassManage/formDetail?id=${id}&basic=${basic}">基本信息</a></li>
    <li><a href="${ctx}/affair/affairClassManage/idList?classId=${id}&look=${look}">课程信息</a></li>
    <li><a href="${ctx}/affair/affairClassManage/idListRenYuan?id=${id}&lookPers=${lookPers}">人员信息</a></li>
</ul>
    <!-- 详细信息1 -->
    <div id="modalInfo1">
        <div class="modal-custom">
            <div class="modal-custom-main">

                <div class="modal-custom-content">
                    <div id="contentTable" class="modal-custom-content">
                        <div class="modal-custom-info2">
                            <div class="modal-custom-info2-col modal-custom-info2-col1">
                                <div class="modal-custom-info2-row">
                                    <span class="modal-custom-info2-key">培训名称：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.name}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训年度：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.year}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">单位：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.unit}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">标题：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.title}</span></div>
                                <%--                            <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班类型：</span><span class="modal-custom-info2-value">${affairClassManage.type}</span></div>--%>
                                <div class="modal-custom-info2-row">
                                    <span class="modal-custom-info2-key">培训班类型：</span>
                                    <span class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.type, 'affair_train_type', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班层次：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.level, 'affair_train_level', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训方式：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.trainWay, 'affair_train_way', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班目的：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.purpose}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班内容：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.content}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班场地：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.site}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班对象：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.trainObject}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训班预算：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.budget}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训天数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.trainDay}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训人数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.count}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训费：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.fees}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">师资费：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.teacherFees}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">课程总分：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.classCount}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">评估分数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.score}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">应参训人数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.participateTrain}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实际参人数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.realParticipate}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">参训率：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.participateRate}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">已通过人数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.passedCount}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">未通过人数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.failCount}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">通过率：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.passRate}</span></div>
                            </div>
                            <div class="modal-custom-info2-col modal-custom-info2-col1">
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训开始时间：</span><span
                                        class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairClassManage.beganDate}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">培训结束时间：</span><span
                                        class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairClassManage.resultDate}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">渠道：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.channel, 'affair_train_level', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">实施状态：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.status, 'affair_train_status', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">开班状态：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.openStatus, 'affair_train_openStatus', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">建班状态：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.classStatus, 'affair_train_classStatus', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">结项状态：</span><span
                                        class="modal-custom-info2-value">${fns:getDictLabel(affairClassManage.pospStatus, 'affair_train_pospStatus', '')}</span>
                                </div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建人：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.creator}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">填报人：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.informant}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">创建时间：</span><span
                                        class="modal-custom-info2-value"><fmt:formatDate
                                        value="${affairClassManage.createTime}" pattern="yyyy-MM-dd"/></span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">主办部门：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.sponsorUnit}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">班主任：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.teacher}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">助教：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.assistant}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">班主任电话：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.teacherPhone}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">联系电话：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.phone}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">住宿费：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.accommodationFees}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">伙食费：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.boardWages}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">场地资料交通费：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.siteFees}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">其他费用：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.otherFees}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">费用总额：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.feesCount}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总时长：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.studyTime}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">平均学习时长：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.averageTime}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">学习总次数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.studyCount}</span></div>
                                <div class="modal-custom-info2-row"><span class="modal-custom-info2-key">平均学习次数：</span><span
                                        class="modal-custom-info2-value">${affairClassManage.averageCount}</span></div>
                            </div>
                        </div>
                        <%--<div class="modal-custom-info1-file">
                            <div class="modal-custom-info1-file-1">附件：</div>
                            <div class="modal-custom-info1-file-r">
                                <c:forEach items="${filePathList}" var="m" varStatus="status">
                                    <div class="modal-custom-info1-file-item">
                                        <span>${m.fileName}</span>
                                            &lt;%&ndash;<a href="#">在线预览</a>&ndash;%&gt;
                                        <a class="download" href="${ctx}/file/download?fileName=${m.path}">下载</a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>--%>
                    </div>
                    <div class="modal-custom-info1-bottom">
                        <div id="print" class="modal-custom-info1-btn red">打印</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
