<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>评价内容</title>
    <link href="${ctxStatic}/common/common.css" type="text/css" rel="stylesheet"/>
    <link href="${ctxStatic}/common/modal-custom.css" type="text/css" rel="stylesheet"/>
    <meta name="decorator" content="default"/>
    <script>
        //if ("selfAppraise" == "${processType}") {
            //parent.window.location.href = "${ctx}/exam/examWorkflow/flowList?examType=${examType}";
        //}

        $(document).ready(function() {
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function(error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        });
        function formSubmit() {
            //debugger;er;
            $.jBox.tip('正在保存，请稍等...','loading');
            /*选中的行*/
            var selectedBoxes = [];
            $('input[name="standardbox"]').each(function () {
                if ($(this).is(':checked')) {
                    selectedBoxes.push($(this).val());
                }
            });
            let term='';
            selectedBoxes.forEach(value => {
                term+=value+',';
            })
            <%--let rowList = $.parseJSON('${rowlistJson}');--%>
            let standardId = $("#standardId").val();
            <%--window.parent.addBeta(selectedBoxes,rowList,standardId,'${workflowId}');--%>
            $.jBox.closeTip();
            /*选中的行在数据库中标记为选中*/
            $.ajax({
                type:"post",
                url: "${ctx}/exam/examWorkflowDatas/selectData",
                data: {
                    standardId: standardId,
                    workflowId: '${workflowId}',
                    objId: '${objId}',
                    fillPersonId: '${fillPersonId}',
                    rowId: selectedBoxes,
                    workflowStatus : '${workflowStatus}'
                },
                success: function(data){
                    if (data.success){
                        $.jBox.closeTip();
                        parent.$.jBox.close();
                        // console.log(data.result);
                    }
                }
            });
            window.parent.test= selectedBoxes;
    /*        $.ajax({
                type:"post",
                url: "${ctx}/exam/examWorkflow/saveSelfEvaluationTerm",
                data:{standardId:'${standardId}',workflowId:'${workflowId}',objId:'${objId}',fillPersonId:'${fillPersonId}',rowNum:term},
                success: function(data){
                    if (data.success){
                        $.jBox.closeTip();
                        window.parent.jBox.close();
                        // console.log(data.result);
                    }
                }
            });*/
        }
        if ("success" == "${saveResult}"){
            window.parent.jBox.close();
        }

        $("#examStandard").change(function(){
            if($("#examStandard").val() == '2'){
                alert("22222");
                alert($("#examStandard").val());
            }else {
                alert($("#examStandard").val())
            }
        });
        $("#examStandard").live('change',function(){
            //省略若干代码
            // alert($("#examStandard option:selected").val());
            $("#standardId").val($("#examStandard option:selected").val());
        });
        $("#examStandard").change(function(){
            if($("#examStandard").val() == '1'){
                console.log("33333");

            }else if($("#examStandard").val() == '2'){
                console.log("33333");
            }else if($("#examStandard").val() == '3'){
                //更改查询信息
                // partyMember(); alert($("#selectType2").val())
                console.log("33333");
            }else if($("#examStandard").val() == '4'){
                console.log("33333");
            }else if ($("#examStandard").val() == '5') {
                console.log("33333");
            }
        });

    </script>

</head>
<body>
<br>
<form:form id="queryForm" modelAttribute="examWorkflow" action="${ctx}/exam/examWorkflow/selectTerm" method="post" class="form-horizontal">

    <input type="hidden" name="workflowId" id="workflowId" value="${workflowId}" >
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="objId" name="objId" value="${objId}" type="hidden"/>
    <input id="fillPersonId" name="fillPersonId" value="${fillPersonId}" type="hidden"/>
    <div>
        <span>
            <label>考评标准：</label>
          <input id="standardText" type="text" name="standardText">
        </span>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <span>
            <label>模板类型：</label>
<%--            <select id="examStandard" style="width: 120px;">--%>
<%--                        <option value="${standardId}">${standardName}</option>--%>
<%--                    <c:forEach items="${standardInfoList}" var="standard" varStatus="status">--%>
<%--                        <c:if test="${standardId != standard.id}">--%>
<%--                            <option value="${standard.id}">${standard.name}</option>--%>
<%--                        </c:if>--%>
<%--                    </c:forEach>--%>
<%--            </select>--%>
            <form:select path="examStandard" style="width: 30%;" >
                <form:option value="" label=""/>
                <form:options items="${standardInfoList}" itemLabel="name" itemValue="id" htmlEscape="false"></form:options>
            </form:select>
        </span>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
</form:form>
<form:form id="inputForm" action="" method="post" class="form-horizontal">
    <sys:message content="${message}"/>
    <input id="id" name="id" value="${workflowId}" type="hidden"/>
    <input id="status" name="status" value="${status}" type="hidden"/>
    <input id="workflowStatus" name="workflowStatus" value="${workflowStatus}" type="hidden"/>
    <input id="standardId" name="standardId" value="${standardId}" type="hidden"/>
    <input id="selectedNumber" name="selectedNumber" value="${selectedNumber}" type="hidden"/>
    <input id="processType" name="processType" value="${processType}" type="hidden"/>
    <input id="personType" name="personType" value="${personType}" type="hidden"/>
    <input id="examType" name="examType" value="${examType}" type="hidden"/>
    <input id="num" name="num" value="0" type="hidden"/>

    <div id="modalSysSelf">
        <div class="modal-custom-content">

            <br>
            <div class="modal-custom-tb">
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
                    <c:forEach items="${rowlist}" var="row"  varStatus="status">
                        <tr id="s_${row.row_num}">
                            <c:forEach items="${columnList}" var="examStandardTemplateItemDefine">
                                <td>
                                        ${row.get(String.valueOf(examStandardTemplateItemDefine.id))}
                                </td>
                                <c:if test="${'2'.equals(examStandardTemplateItemDefine.columnType)}">
                                    <input type='hidden' name='standardDatas[${status.index}].value'
                                           value='${row.get(String.valueOf(examStandardTemplateItemDefine.id))}'/>
                                    <input type='hidden' name='standardDatas[${status.index}].valueType'
                                           value='1'/>
                                </c:if>
                                <c:if test="${'3'.equals(examStandardTemplateItemDefine.columnType)}">
                                    <input type='hidden' name='standardDatas[${status.index}].value'
                                           value='${row.get(String.valueOf(examStandardTemplateItemDefine.id))}'/>
                                    <input type='hidden' name='standardDatas[${status.index}].valueType'
                                           value='-1'/>
                                </c:if>
                                <c:if test="${'4'.equals(examStandardTemplateItemDefine.columnType)}">
                                    <input type='hidden' name='standardDatas[${status.index}].type'
                                           value='${row.get(String.valueOf(examStandardTemplateItemDefine.id))}'/>
                                </c:if>
                            </c:forEach>
                            <td>
                                <input class="i-checks" type="checkbox" name="standardbox" value="${row.row_num}"
                            <c:if test="${row.isSelected==1}">
                                       checked
                            </c:if>
                              >
                            </td>
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
<%--            <div class="modal-custom-tb-c">
                <div class="tb-btn tb-jia"><i class="icon-long-arrow-right" onclick="add()"></i></div>
                <div class="tb-btn tb-jian"><i class="icon-long-arrow-left" onclick="reduce()"></i></div>
            </div>--%>
        </div>
    </div>
    <input id="save" class="btn btn-primary" type="button" value="保存" onclick="formSubmit('save')" />
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
                    multiple:true,
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