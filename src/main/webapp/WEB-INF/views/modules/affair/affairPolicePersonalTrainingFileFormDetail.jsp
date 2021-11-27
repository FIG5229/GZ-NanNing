<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>民警个人训历档案报表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#print").click(function(){
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
                    formValues: false
                });
            });
        });
    </script>
    <style>
        body{
            text-align: center;
        }


      /* color:windowtext;
       font-size:12.0pt;
       font-weight:700;
       font-style:normal;
       text-align: center;
       word-break:break-word;*/
    </style>
</head>
<body>
<div style="color: red;text-align: left">${message}</div>
<c:choose>
    <c:when test="${empty affairPolicePersonalTrainingFile}">
        <h4>未从系统中查到该人员训历档案</h4>
    </c:when>
    <c:otherwise>
        <table id="contentTable" class="table table-striped table-bordered table-condensed" style="margin: 2% 0 2% 0">
            <tbody>
            <tr>
                <td colspan="5" class="style1"  style="text-align: center">
                    <h3>铁路公安机关民警个人训历档案</h3>
                </td>
            </tr>
            <tr height="33">
                <td class="style2"  style="text-align: center">姓名</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.name}</td>
                <td class="style2"  style="text-align: center">性别</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.sex}</td>
                <td rowspan="5"  class="style4"  style="position:relative;overflow:visible;">
                    <%--<shape type="#_x0000_t75" id="Picture 1" spid="_x0000_s1026" filled="f"
                           style="position:absolute;text-indent:0;margin-left:0.0pt;margin-top:0.0pt;width:118.49819pt;height:128.24805pt;z-index:1">
                        <imagedata title="image1" src='../../../../politics/static/images/icon-login.png'></imagedata>
                        <o:lock aspectratio="t"></o:lock><v:stroke on="f"></v:stroke></shape><!--[if !vml]-->
                    <div style="mso-ignore:vglayout;position:absolute;top:0px;left:0px;z-index:5;margin-left:0.0pt;margin-top:0.0pt;width:118.49819pt;height:128.24805pt">
                        <img src='../../../../politics/static/images/icon-login.png' class="img-responsive" xmlns:v="urn:schemas-microsoft-com:vml"
                             v:shapes="Picture 1" style="width:158.0px;height:171.0px;"></div>--%><!--[endif]-->
                </td>
            </tr>
            <tr height="35">
                <td class="style2"  style="text-align: center">单位</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.unit}</td>
                <td class="style2"  style="text-align: center">职务</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.job}</td>
            </tr>
            <tr height="36">
                <td class="style2"  style="text-align: center">出生年月</td>
                <td class="style5"  style="text-align: center"><fmt:formatDate value="${affairPolicePersonalTrainingFile.birthday}" pattern="yyyy-MM-dd"/></td>
                <td class="style2"  style="text-align: center">入警时间</td>
                <td class="style5"  style="text-align: center"><fmt:formatDate value="${affairPolicePersonalTrainingFile.hiredate}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr height="36">
                <td class="style2"  style="text-align: center">学历</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.education}</td>
                <td class="style2"  style="text-align: center">政治面貌</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.politicsFace}</td>
            </tr>
            <tr height="31">
                <td class="style2"  style="text-align: center">专业技术职称</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.technicalTitle}</td>
                <td class="style2"  style="text-align: center">联系电话</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.phone}</td>
            </tr>
            <tr height="34">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    在线课程
                </td>
            </tr>
            <tr height="41">
                <td class="style2"  style="text-align: center">参加次数</td>
                <td class="style2"  style="text-align: center">通过率</td>
                <td class="style2"  style="text-align: center">平均分</td>
                <td class="style2"  style="text-align: center">总课时(分钟)</td>
                <td class="style2"  style="text-align: center">学分</td>
            </tr>
            <tr height="40">
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.onlineCourseNum}</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.onlineCoursePassing}</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.onlineCourseAverage}</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.onlineCourseTotalTime}</td>
                <td class="style3"  style="text-align: center">${affairPolicePersonalTrainingFile.onlineCourseCredit}</td>
            </tr>
            <tr height="40">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    培训班课程
                </td>
            </tr>
            <tr height="41">
                <td class="style2"  style="text-align: center">参加次数</td>
                <td class="style2"  style="text-align: center">通过率</td>
                <td class="style2"  style="text-align: center">平均分</td>
                <td class="style2" style="text-align: center">总课时(分钟)</td>
                <td class="style2" style="text-align: center">学分</td>
            </tr>
            <tr height="45">
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.trainCourseNum}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.trainCoursePassing}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.trainCourseAverage}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.trainCourseTotalTime}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.trainCourseCredit}</td>
            </tr>
            <tr height="44">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    岗位练兵
                </td>
            </tr>
            <tr height="42">
                <td  colspan="2" class="style7" style="text-align: center">参加次数
                </td>
                <td  colspan="3" class="style7" style="text-align: center">总时长(小时)
                </td>
            </tr>
            <tr height="42">
                <td  colspan="2" class="style4" style="text-align: center">
                        ${affairPolicePersonalTrainingFile.jobTrainingNum}
                </td>
                <td  colspan="3" class="style4" style="text-align: center">
                        ${affairPolicePersonalTrainingFile.jobTrainingTotalTime}
                </td>
            </tr>
            <tr height="40">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    委外培训
                </td>
            </tr>
            <tr height="44">
                <td class="style2" style="text-align: center">参加次数</td>
                <td class="style2" style="text-align: center">完成率</td>
                <td class="style2" style="text-align: center">主要类别</td>
                <td class="style2" style="text-align: center">最高级别</td>
                <td class="style2" style="text-align: center">取得证书</td>
            </tr>
            <tr height="40">
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.outTrainingNum}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.outTrainingFinish}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.outTrainingMainType}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.outTrainingHighestLevel}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.outTrainingGetCcie}</td>
            </tr>
            <tr height="44">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    交流学习
                </td>
            </tr>
            <tr height="41">
                <td class="style2" style="text-align: center">参加次数</td>
                <td class="style2" style="text-align: center">完成率</td>
                <td class="style2" style="text-align: center">主要岗位</td>
                <td class="style2" style="text-align: center">最高规格</td>
                <td class="style2" style="text-align: center">总时长</td>
            </tr>
            <tr height="40">
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.exchangeStudyNum}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.exchangeStudyFinish}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.exchangeStudyMainJob}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.exchangeStudyHighestSpec}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.exchangeStudyTotalTime}</td>
            </tr>
            <tr height="40">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    考试
                </td>
            </tr>
            <tr height="39">
                <td class="style2" style="text-align: center">参加次数</td>
                <td class="style2" style="text-align: center">合格率</td>
                <td class="style2" style="text-align: center">平均分</td>
                <td class="style2" style="text-align: center">作弊次数</td>
                <td class="style3" ></td>
            </tr>
            <tr height="38">
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.examNum}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.examPass}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.examAverage}</td>
                <td class="style3" style="text-align: center">${affairPolicePersonalTrainingFile.examCheat}</td>
                <td class="style3" style="text-align: center"></td>
            </tr>
            <tr height="39">
                <td colspan="5" class="style6"  style="color:windowtext;font-size:12.0pt;font-weight:700;font-style:normal;text-align: center;word-break:break-word;">
                    教育训练积分
                </td>
            </tr>
            <tr height="29">
                <td  colspan="5" class="style4"  style="text-align: center">
                        ${affairPolicePersonalTrainingFile.trainingIntegral}
                </td>
            </tr>
            <tr height="212">
                <td  colspan="5" class="style8" valign="top">
                    <pre>${affairPolicePersonalTrainingFile.expertOpinion}</pre>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="modal-custom-info1-bottom">
            <div class="modal-custom-info1-btn red" id="print">打印</div>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>