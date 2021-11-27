<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评价内容</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script>
       // if ("success" == "${result}") {
            //parent.window.location.href = "${ctx}/exam/examWorkflow/exam?id=${workflowId}";
       // }
    </script>
    <script type="text/javascript">
        function add() {
            var selectedBoxes = [];
            $('input[name="selectedBox"]').each(function () {
                selectedBoxes.push($(this).val());
            });
            var selectedRowNum = 0;
            if (null != $("#selectedNumber").val() && '' != $("#selectedNumber").val()) {
                selectedRowNum = parseInt($("#selectedNumber").val());
            }

            var i = 0;
            var rowNum = 0;
            $('input[name="standardbox"]:checked').each(function () {
                if ($.inArray($(this).val(), selectedBoxes) < 0) {
                    rowNum = selectedRowNum + i;
                    var html = "<tr id='r_" + $(this).val() + "'><td><input class='i-checks' type='checkbox' name='selectedBox' value='" + $(this).val() + "'/></td>"
                    html += $("#s_" + $(this).val()).html();
                    html = html.substr(0, html.lastIndexOf("<td>"));
                    html += "<td></td>";
                    html += "<td><input type='text' name='datas[" + rowNum + "].value' style='width:30px;' class='input-xlarge  number required'/></td>";
                    html += "<td><textarea name='datas[" + rowNum + "].items' rows='2' cols='8'></textarea></td>";
                    html += "<td>";
                    html += "<input id='datas" + rowNum + "-nameFile' name='datas[" + rowNum + "].path' type='hidden' value='' class='fileinput'/>";
                    html += "<ol id='datas" + rowNum + "-nameFilePreview'></ol>";
                    html += "<div id='datas" + rowNum + "-nameFileUploaderDiv'>";
                    html += "<div class='queueList'>";
                    html += "<div id='nameFileDndArea' class='placeholder'>";
                    html += "<div class='nameFileFilePicker' onclick='setNumber(" + rowNum + ")'></div>";
                    html += "</div>";
                    html += "</div>";
                    html += "<div class='statusBar' style='display:none;'>";
                    html += "<div class='progress'>";
                    html += "<span class='text'>0%</span>";
                    html += "<span class='percentage'></span>";
                    html += "</div><div class='info'></div>";
                    html += "<div class='btns'>";
                    html += "<div id='datas" + rowNum + "-nameFileFilePicker2'></div><div class='uploadBtn'>开始上传</div>";
                    html += "</div>";
                    html += "</div>";
                    html += "</div>";
                    html += "</td>";
                    html += "<td><input type='hidden' name='datas[" + rowNum + "].fillPerson' value=''/></td>";
                    html += "<td><input type='hidden' name='datas[" + rowNum + "].examPerson' value=''/></td>";
                    html += "<td><input type='text' name='datas[" + rowNum + "].detail' style='width:50px;' value=''></input></td>";
                    html += "<td>未提交</td>";
                    html += "<td><label><input class='i-checks' type='radio' name='datas[" + rowNum + "].operationStatus' value='2'/>不通过</label><label><input class='i-checks' type='radio' name='datas[" + rowNum + "].operationStatus' value='3' checked/>通过</label></td>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].standardId' value='" + $("#standardId").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].workflowId' value='" + $("#id").val() + "'/>";
                    html += "<input type='hidden' name='datas[" + rowNum + "].rowId' value='" + $(this).val() + "'/>";
                    html += "</tr>";
                    $("#r_body").append(html);
                    i++;
                }
            });
            init();
        }

        function reduce() {
            $('input[name="selectedBox"]:checked').each(function () {
                $("#r_" + $(this).val()).replaceWith("");
            });
        }

        function formSubmit(processType) {
            $("#processType").val(processType);
            $("#inputForm").submit();
        }

        function sendSubmit(processType, personType) {
            if(null == $("#personId").val()||'' == $("#personId").val()){
                jBox.alert("请选择推送人");
                return false;
            }
            $("#processType").val(processType);
            $("#personType").val(personType);
            $("#inputForm").submit();
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate();
        });
    </script>
</head>
<body>
<form:form id="inputForm" action="${ctx}/exam/examWorkflow/appaise/data/save" method="post" class="form-horizontal">
    <input id="id" name="id" value="${workflowId}" type="hidden"/>
    <input id="status" name="status" value="${status}" type="hidden"/>
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="selectedNumber" name="selectedNumber" value="${selectedNumber}" type="hidden"/>
    <input id="processType" name="processType" value="${processType}" type="hidden"/>
    <input id="personType" name="personType" value="${personType}" type="hidden"/>
    <input id="examType" name="examType" value="${examType}" type="hidden"/>
    <input id="num" name="num" value="0" type="hidden"/>
    <div id="modalSysSelf">
        <div class="modal-custom-content">
            <div class="modal-custom-tb-l">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>选择</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="operStatus" value="-1"></c:set>
                    <c:forEach items="${rowlist}" var="row"  varStatus="status">
                        <c:set var="operStatus" value="${row.operationStatus}"></c:set>
                        <tr id="s_${row.row_num}">
                            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                <td>
                                        ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                </td>
                                <c:if test="${'2'.equals(examStandardTemplateItemDefine.columnType)||'3'.equals(examStandardTemplateItemDefine.columnType)}">
                                    <input type='hidden' name='standardDatas[${status.index}].value'
                                           value='${row.get(String.valueOf(examStandardTemplateItemDefine.id))}'/>
                                </c:if>
                            </c:forEach>
                            <td><input class="i-checks" type="checkbox" name="standardbox" value="${row.row_num}"
                            <c:if test="${row.isSelected==1}">
                                       checked
                            </c:if>
                            ></td>
                            <input type='hidden' name='standardDatas[${status.index}].standardId'
                                   value='${standardId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].workflowId'
                                   value='${workflowId}'/>
                            <input type='hidden' name='standardDatas[${status.index}].rowId'
                                   value='${row.row_num}'/>
                            <c:if test="${null != row.id}">
                                <input type='hidden' name='standardDatas[${status.index}].id'
                                       value='${row.id}'/>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-custom-tb-c">
                <div class="tb-btn tb-jia"><i class="icon-long-arrow-right" onclick="add()"></i></div>
                <div class="tb-btn tb-jian"><i class="icon-long-arrow-left" onclick="reduce()"></i></div>
            </div>
            <div class="modal-custom-tb-r">
                <table class="table table-striped table-bordered table-condensed" style="width: 100%;">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                            <th>${examStandardTemplateItemDefine.columnName}</th>
                        </c:forEach>
                        <th>上级检查评分情况</th>
                        <c:choose>
                            <c:when test="${'2'.equals(modleType)}">
                                <th>加分(实际)</th>
                            </c:when>
                            <c:otherwise>
                                <th>扣分(实际)</th>
                            </c:otherwise>
                        </c:choose>
                        <th>附件</th>
                        <th>自评人</th>
                        <th>考核人</th>
                        <th>理由</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="r_body">
                    <c:set var="num" value="0"></c:set>
                    <c:set var="step" value="1"></c:set>
                    <c:forEach items="${rowlist}" var="row"   varStatus="status">
                        <c:if test="${'1'.equals(row.isSelected)}">
                            <tr id="r_${row.row_num}">
                                <td>
                                    <input class='i-checks' type='checkbox' name='selectedBox'
                                           value='${row.row_num}'/>
                                </td>
                                <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                    <td>
                                            ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                    </td>
                                </c:forEach>
                                <td></td>
                                <td><input type='text' name='datas[${num}].value'
                                           style='width:30px;' class='input-xlarge  number required'
                                           value='${row.value}'/></td>
                                <td>
                                    <input id="datas${num}-nameFile" name="datas[${num}].path" type="hidden" value="${row.path}" class="fileinput"/>
                                    <ol id="datas${num}-nameFilePreview"></ol>
                                    <div id="datas${num}-nameFileUploaderDiv" class="nameFileUploaderDiv">
                                        <div class="queueList">
                                            <div id="nameFileDndArea" class='placeholder'>
                                                <div class="nameFileFilePicker" onclick="setNumber('${num}')"></div>
                                            </div>
                                        </div>
                                        <div class="statusBar" style="display:none;">
                                            <div class="progress">
                                                <span class="text">0%</span>
                                                <span class="percentage"></span>
                                            </div><div class="info"></div>
                                            <div class="btns">
                                                <div id="datas${num}-nameFileFilePicker2"></div><div class="uploadBtn">开始上传</div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>${row.fillPerson}</td>
                                <td>${row.examPerson}</td>
                                <td><input type='text' name='datas[${num}].detail' style='width:50px;' value='${row.detail}'></input></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${'3'.equals(row.operationStatus)&&row.status == 3}">
                                            已通过
                                        </c:when>
                                        <c:when test="${'-1'.equals(row.operationStatus)&&row.status == 3}">
                                            已通过
                                        </c:when>
                                        <c:when test="${'2'.equals(row.operationStatus)&&row.status == 3}">
                                            已退回
                                        </c:when>
                                        <c:when test="${'0'.equals(row.operationStatus)&&row.status == 3} ">
                                            已保存
                                        </c:when>
                                        <c:when test="${'3'.equals(row.operationStatus)&&row.status >2}">
                                            已通过
                                        </c:when>
                                        <c:when test="${'2'.equals(row.status)&&'3'.equals(row.operationStatus)}">
                                            已保存
                                        </c:when>
                                        <c:when test="${'2'.equals(row.status)&&'2'.equals(row.operationStatus)}">
                                            已退回
                                        </c:when>
                                        <c:when test="${'2'.equals(row.status)&&'0'.equals(row.operationStatus)}">
                                            已保存
                                        </c:when>
                                        <c:otherwise>未处理</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><label><input class='i-checks' type='radio' name='datas[${num}].operationStatus' value='2' <c:if test="${'2'.equals(row.operationStatus)}">checked</c:if>/>不通过</label><label><input class='i-checks' type='radio' name='datas[${num}].operationStatus' value='3' <c:if test="${!'2'.equals(row.operationStatus)}">checked</c:if>/>通过</label></td>
                                <input type='hidden' name='datas[${num}].standardId'
                                       value='${standardId}'/>
                                <input type='hidden' name='datas[${num}].workflowId'
                                       value='${workflowId}'/>
                                <input type='hidden' name='datas[${num}].rowId'
                                       value='${row.row_num}'/>
                                <input type='hidden' name='datas[${num}].id'
                                       value='${row.id}'/>
                            </tr>
                            <c:set var="num" value="${num+step}"></c:set>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right;">审核人：<input type="text" name="fill_person" value="${fns:getUser().name}" disabled></br></br>
                    <c:choose>
                        <c:when test="${'5'.equals(nextStep)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','partBureausSign')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'6'.equals(nextStep)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="确认并推送到" onclick="sendSubmit('departSign','groupAdjust')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true" disabled="disabled"/>
                        </c:when>
                        <c:when test="${'7'.equals(nextStep)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('departSign','mainBureausSign')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="${personId}" labelName="personName" labelValue="${person}"
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:when>
                        <c:when test="${'8'.equals(nextStep)}">
                            <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />&nbsp;&nbsp;&nbsp;&nbsp;
                            <input id="complete" class="btn btn-primary" type="button" value="结束本项工作考评" onclick="formSubmit('appraiseExam','')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                        </c:when>

                        <%--
                        <c:when test="${'3'.equals(nextStep)}">
                            <input  class="btn btn-primary" type="button" value="公示并推送到" onclick="formSubmit('appraiseExam')"/>
                            <select name="personId">
                                <c:forEach items="${userList}" var="user">
                                    <option value="${user.id}">${user.name}</option>
                                </c:forEach>
                            </select>
                        </c:when>--%>
                        <c:otherwise>
                            <input id="complete" class="btn btn-primary" type="button" value="结束并推送到" onclick="sendSubmit('appraiseExam')"   <c:if test="${'false'==isEdit}"> disabled="true" </c:if> />
                            <sys:treeselect id="person" name="personId" value="" labelName="" labelValue=""
                                            title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</form:form>
<script type="text/javascript">
    function nameFileDel(obj,num){
        var url = $(obj).prev().attr("url");
        $("#datas"+num+"-nameFile").val($("#datas"+num+"-nameFile").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
        nameFilePreview(num);
    }
    function nameFileDelAll(num){
        $("#datas"+num+"-nameFile").val("");
        nameFilePreview();
    }
    function nameFilePreview(num){
        var li, urls = $("#"+"datas"+num+"-nameFile").val().split("|");
        $("#datas"+num+"-nameFilePreview").children().remove();
        for (var i=0; i<urls.length; i++){
            if (urls[i]!=""){//
                li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
                li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+num+");\">×</a></li>";
                $("#datas"+num+"-nameFilePreview").append(li);
            }
        }
        if ($("#datas"+num+"-nameFilePreview").text() == ""){
            $("#datas"+num+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
        }
    }
    function setNumber(val) {
        $('#num').val(val);
    }

    function initFilePreview() {
        $(".fileinput").each(function (index,el) {
            var li, urls = $("#"+"datas"+index+"-nameFile").val().split("|");
            $("#datas"+index+"-nameFilePreview").children().remove();
            for (var i=0; i<urls.length; i++){
                if (urls[i]!=""){//
                    li = "<li><a href=\"/politics" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//
                    li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"nameFileDel(this,"+index+");\">×</a></li>";
                    $("#datas"+index+"-nameFilePreview").append(li);
                }
            }
            if ($("#datas"+index+"-nameFilePreview").text() == ""){
                $("#datas"+index+"-nameFilePreview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
            }
        });
    }
    initFilePreview();
</script>
<script type="text/javascript">
    function init(){
        $(function() {
            var num = $('#num').val();
            var $wrap = $('#datas'+num+'-nameFileUploaderDiv'),
                $wrap = $('.nameFileUploaderDiv'),
                // 图片容器
                $queue = $( '<ul class="filelist"></ul>' )
                    .appendTo( $wrap.find( '.queueList' ) ),

                // 状态栏，包括进度和控制按钮
                $statusBar = $wrap.find( '.statusBar' ),

                // 文件总体选择信息。
                $info = $statusBar.find( '.info' ),

                // 上传按钮
                $upload = $wrap.find( '.uploadBtn' ),

                // 没选择文件之前的内容。
                $placeHolder = $wrap.find( '.placeholder' ),
                $progress = $statusBar.find( '.progress' ).hide(),

                // 添加的文件数量
                fileCount = 0,
                // 添加的文件总大小
                fileSize = 0,

                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,

                // 缩略图大小
                thumbnailWidth = 110 * ratio,
                thumbnailHeight = 110 * ratio,

                // 可能有pedding, ready, uploading, confirm, done.
                state = 'pedding',

                // WebUploader实例
                uploader;
            // 实例化
            uploader = WebUploader.create({
                auto:true,
                pick: {
                    id: '.nameFileFilePicker',
                    label: '选择'
                },
                formData: {
                    path: "affair/exam"
                },
                //dnd: '#nameFileDndArea',
                //paste: '#nameFileUploaderDiv',
                swf: '${ctxStatic}/webuploader/0.1.5/Uploader.swf',
                chunked: false,
                server: '${ctx}/file/upload',
                // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                disableGlobalDnd: true,
                fileNumLimit: 20,
                fileSizeLimit: 200 * 1024 * 1024,    // 200 M
                fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
            });

            // 拖拽时不接受 js, txt 文件。
            uploader.on( 'dndAccept', function( items ) {
                var denied = false,
                    len = items.length,
                    i = 0,
                    // 修改js类型
                    unAllowed = 'text/plain;application/javascript ';

                for ( ; i < len; i++ ) {
                    // 如果在列表里面
                    if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                        denied = true;
                        break;
                    }
                }

                return !denied;
            });

            uploader.on('ready', function() {
                window.uploader = uploader;
            });

            // 当有文件添加进来时执行，负责view的创建
            function addFile( file ) {
                var $li = $( '<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    // '<p class="imgWrap"></p>'+
                    '<p class="progress"><span></span></p>' +
                    '</li>' ),

                    $btns = $('<div class="file-panel">' +
                        '<span class="cancel">删除</span>' +
                        '<span class="rotateRight">向右旋转</span>' +
                        '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
                    $prgress = $li.find('p.progress span'),
                    $wrap = $li.find( 'p.imgWrap' ),
                    $info = $('<p class="error"></p>'),

                    showError = function( code ) {
                        switch( code ) {
                            case 'exceed_size':
                                text = '文件大小超出';
                                break;
                            case 'interrupt':
                                text = '上传暂停';
                                break;
                            default:
                                text = '上传失败，请重试';
                                break;
                        }
                        $info.text( text ).appendTo( $li );
                    };

                file.on('statuschange', function( cur, prev ) {
                    if ( prev === 'progress' ) {
                        $prgress.hide().width(0);
                    } else if ( prev === 'queued' ) {
                        $li.off( 'mouseenter mouseleave' );
                        $btns.remove();
                    }
                    // 成功
                    if ( cur === 'error' || cur === 'invalid' ) {
                        showError( file.statusText );
                        // percentages[ file.id ][ 1 ] = 1;
                    } else if ( cur === 'interrupt' ) {
                        showError( 'interrupt' );
                    } else if ( cur === 'queued' ) {
                        // percentages[ file.id ][ 1 ] = 0;
                    } else if ( cur === 'progress' ) {
                        $info.remove();
                        $prgress.css('display', 'block');
                    } else if ( cur === 'complete' ) {
                        $li.append( '<span class="success"></span>' );
                    }
                    $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
                });
                $li.on( 'mouseenter', function() {
                    $btns.stop().animate({height: 30});
                });
                $li.on( 'mouseleave', function() {
                    $btns.stop().animate({height: 0});
                });
                $btns.on( 'click', 'span', function() {
                    var index = $(this).index(),
                        deg;
                    switch ( index ) {
                        case 0:
                            uploader.removeFile( file );
                            return;
                        case 1:
                            file.rotation += 90;
                            break;
                        case 2:
                            file.rotation -= 90;
                            break;
                    }
                    if ( supportTransition ) {
                        deg = 'rotate(' + file.rotation + 'deg)';
                        $wrap.css({
                            '-webkit-transform': deg,
                            '-mos-transform': deg,
                            '-o-transform': deg,
                            'transform': deg
                        });
                    } else {
                        $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                    }
                });
                $li.appendTo( $queue );
            }

            // 负责view的销毁
            function removeFile( file ) {
                var $li = $('#'+file.id);
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
            }

            function updateTotalProgress() {
                updateStatus();
            }

            function updateStatus() {
                var text = '', stats;
                var typeName = '文件', unit='份';
                if ( state === 'ready' ) {
                    text = '选中' + fileCount + unit + typeName+'，共' +
                        WebUploader.formatSize(fileSize) + '。';
                } else if ( state === 'confirm' ) {
                    stats = uploader.getStats();
                    if ( stats.uploadFailNum ) {
                        text = '已成功上传' + stats.successNum + unit + typeName+'，'
                        stats.uploadFailNum + unit + typeName+'上传失败，<a class="retry" href="#">重新上传</a>失败'+typeName+''
                    }

                } else {
                    stats = uploader.getStats();
                    text = '共' + fileCount + unit + '（' +
                        WebUploader.formatSize(fileSize) +
                        '），已上传' + stats.successNum + unit;

                    if (stats.uploadFailNum) {
                        text += '，失败' + stats.uploadFailNum + unit;
                    }
                }
                if($(".fileinput").length>0){
                    nameFilePreview($("#num").val());
                }
                //$info.html( text );
            }

            function setState( val ) {
                var file, stats;
                if ( val === state ) {
                    return;
                }
                $upload.removeClass( 'state-' + state );
                $upload.addClass( 'state-' + val );
                state = val;
                var num = $('#num').val();
                switch ( state ) {
                    case 'pedding':
                        $placeHolder.removeClass( 'element-invisible' );
                        $queue.hide();
                        $statusBar.addClass( 'element-invisible' );
                        uploader.refresh();
                        break;
                    case 'ready':
                        $placeHolder.addClass( 'element-invisible' );
                        $('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible');
                        $queue.show();
                        $statusBar.removeClass('element-invisible');
                        uploader.refresh();
                        break;
                    case 'uploading':
                        $('#datas'+num+'nameFileFilePicker2').addClass( 'element-invisible' );
                        $progress.show();
                        //$upload.text( '暂停上传' );
                        break;
                    case 'paused':
                        $progress.show();
                        // $upload.text( '继续上传' );
                        break;
                    case 'confirm':
                        $progress.hide();
                        $('#datas'+num+'nameFileFilePicker2').removeClass( 'element-invisible' );
                        //$upload.text( '开始上传' );
                        stats = uploader.getStats();
                        if ( stats.successNum && !stats.uploadFailNum ) {
                            setState( 'finish' );
                            return;
                        }
                        break;
                    case 'finish':
                        stats = uploader.getStats();
                        if ( stats.successNum ) {

                        } else {
                            // 没有成功的图片，重设
                            state = 'done';
                            location.reload();
                        }
                        break;
                }
                updateStatus();
            }

            uploader.onUploadProgress = function( file, percentage ) {
                var $li = $('#'+file.id),
                    $percent = $li.find('.progress span');
                $percent.css( 'width', percentage * 100 + '%' );
                //percentages[ file.id ][ 1 ] = percentage;
                updateTotalProgress();
            };

            uploader.onFileQueued = function( file ) {
                fileCount++;
                fileSize += file.size;
                if ( fileCount === 1 ) {
                    $placeHolder.addClass( 'element-invisible' );
                    $statusBar.show();
                }
                addFile( file );
                setState( 'ready' );
                updateTotalProgress();
            };

            uploader.onFileDequeued = function( file ) {
                fileCount--;
                fileSize -= file.size;
                if ( !fileCount ) {
                    setState( 'pedding' );
                }
                removeFile( file );
                updateTotalProgress();
            };

            uploader.on( 'uploadSuccess', function( file,data) {
                if(data.success){
                    var num = $('#num').val();
                    var url = data.result;
                    var newUrl = "";
                    var oldUrl = $("#datas"+num+"-nameFile").val();
                    if(oldUrl==""){
                        newUrl = url;
                    } else if (oldUrl.indexOf(url) == -1 ){
                        newUrl = oldUrl + "|" + url;
                    } else {
                        var uArr = url.split("/");
                        var name = uArr[uArr.length - 1];
                        alert(name + " 已存在");
                        newUrl = oldUrl;
                    }
                    $("#datas"+num+"-nameFile").val(newUrl);
                }
                $('.filelist').hide();
                $('.statusBar').hide();
            });

            uploader.on( 'all', function( type ) {
                var stats;
                switch( type ) {
                    case 'uploadFinished':
                        setState( 'confirm' );
                        break;
                    case 'startUpload':
                        setState( 'uploading' );
                        break;
                    case 'stopUpload':
                        setState( 'paused' );
                        break;
                }
            });

            uploader.onError = function( code ) {
                var text = '';
                if(code=="Q_TYPE_DENIED"){
                    text = "文件类型不支持，仅支持gif,jpg,jpeg,bmp,png格式图片";
                }else if(code=="F_DUPLICATE"){
                    text = "文件已选中，请勿重复上传";
                }else if(code="F_EXCEED_SIZE"){
                    text = "文件大小超出限制，不得超过5MB";
                } else{
                    text = 'Error: ' + code;
                }
                $info.html( text );
            };

            $upload.on('click', function() {
                if ( $(this).hasClass( 'disabled' ) ) {
                    return false;
                }
                if ( state === 'ready' ) {
                    uploader.upload();
                } else if ( state === 'paused' ) {
                    uploader.upload();
                } else if ( state === 'uploading' ) {
                    uploader.stop();
                }
            });
            $info.on( 'click', '.retry', function() {
                uploader.retry();
            } );
            $info.on( 'click', '.ignore', function() {
            } );
            $upload.addClass( 'state-' + state );
            updateTotalProgress();
        });
    }
    init();
</script>
</body>
</html>
