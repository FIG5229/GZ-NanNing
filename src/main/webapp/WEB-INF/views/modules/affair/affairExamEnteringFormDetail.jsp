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
                loadCSS: ["${ctxStatic}/bootstrap/2.3.1/css_cerulean/bootstrap.min.css","${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/jeesite.css","${ctxStatic}/common/modal-custom.css"],
                pageTitle: "打印",
                removeInline: false,
                printDelay: 333,
                header: null,
                formValues: false,
                afterPrint:function (){
                    $('.download').css('display', 'table-cell');
                }
            });
        });
    });

</script>
<div id="modalInfo1">
    <div class="modal-custom">
        <div class="modal-custom-main">
            <div id="contentTable" class="modal-custom-content">
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">姓名：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.name}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">身份证号：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.idNumber}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">警号：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.alarm}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">机构：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.organ}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">考试名称：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.examName}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">考试类别：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.examType}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">考试科目：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.examSubject}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">得分：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.score}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-custom-info2">
                    <div class="modal-custom-info2-col modal-custom-info2-col1">
                        <div class="modal-custom-info2-row">
                            <span class="modal-custom-info2-key">是否通过：</span>
                            <span class="modal-custom-info2-value">${affairExamEntering.isPass}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-custom-info1-bottom">
                <div id="print" class="modal-custom-info1-btn red">打印</div>
            </div>
        </div>
    </div>
</div>
