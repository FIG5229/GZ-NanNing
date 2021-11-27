<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="input" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="files、images、flash、thumb"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="打开文件管理的上传路径"%>
<%@ attribute name="selectMultiple" type="java.lang.Boolean" required="false" description="是否允许多选"%>
<%@ attribute name="simple" type="java.lang.Boolean" required="false" description="是否简易模式"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>
<%@ attribute name="maxWidth" type="java.lang.String" required="false" description="最大宽度"%>
<%@ attribute name="maxHeight" type="java.lang.String" required="false" description="最大高度"%>
<style>
    /* ----------------Reset Css--------------------- */
    html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp,
    small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td,
    article, aside, canvas, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary,
    time, mark, audio, video, input  {
        margin: 0;
        padding: 0;
        border: none;
        outline: 0;
        font-size: 100%;
        font: inherit;
        vertical-align: baseline;
    }

    html, body, form, fieldset, p, div, h1, h2, h3, h4, h5, h6 {
        -webkit-text-size-adjust: none;
    }

    article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section {
        display: block;
    }

    body {
        font-family: arial, sans-serif;
    }

    ol, ul {
        list-style: none;
    }

    blockquote, q {
        quotes: none;
    }

    blockquote:before, blockquote:after, q:before, q:after {
        content: '';
        content: none;
    }

    ins {
        text-decoration: none;
    }

    del {
        text-decoration: line-through;
    }

    table {
        border-collapse: collapse;
        border-spacing: 0;
    }

    /* ------------ */

    #${input}UploaderDiv .queueList {
        margin: 20px;
    }

    .element-invisible {
        position: absolute !important;
        clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
        clip: rect(1px,1px,1px,1px);
    }

    #${input}UploaderDiv .placeholder {
        border: 3px dashed #e6e6e6;
        min-height: 214px;
        padding-top: 50px;
        text-align: center;
        <%--background: url(${ctxStatic}/webuploader/images/image.png) center 3px no-repeat;--%>
        color: #cccccc;
        font-size: 18px;
        position: relative;
    }

    #${input}UploaderDiv .placeholder .webuploader-pick {
        font-size: 18px;
        background: #00b7ee;
        border-radius: 3px;
        line-height: 44px;
        padding: 0 30px;
        color: #fff;
        display: inline-block;
        margin: 20px auto;
        cursor: pointer;
        box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    }

    #${input}UploaderDiv .placeholder .webuploader-pick-hover {
        background: #00a2d4;
    }

    #${input}UploaderDiv .placeholder .flashTip {
        color: #666666;
        font-size: 12px;
        position: absolute;
        width: 100%;
        text-align: center;
        bottom: 20px;
    }
    #${input}UploaderDiv .placeholder .flashTip a {
        color: #0785d1;
        text-decoration: none;
    }
    #${input}UploaderDiv .placeholder .flashTip a:hover {
        text-decoration: underline;
    }

    #${input}UploaderDiv .placeholder.webuploader-dnd-over {
        border-color: #999999;
    }

    #${input}UploaderDiv .placeholder.webuploader-dnd-over.webuploader-dnd-denied {
        border-color: red;
    }

    #${input}UploaderDiv .filelist {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    #${input}UploaderDiv .filelist:after {
        content: '';
        display: block;
        width: 0;
        height: 0;
        overflow: hidden;
        clear: both;
    }

    #${input}UploaderDiv .filelist li {
        width: 110px;
        height: 110px;
        background: url(${ctxStatic}/webuploader/images/bg.png) no-repeat;
        text-align: center;
        margin: 0 8px 20px 0;
        position: relative;
        display: inline;
        float: left;
        overflow: hidden;
        font-size: 12px;
    }

    #${input}UploaderDiv .filelist li p.log {
        position: relative;
        top: -45px;
    }

    #${input}UploaderDiv .filelist li p.title {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow : ellipsis;
        top: 5px;
        text-indent: 5px;
        text-align: left;
    }

    #${input}UploaderDiv .filelist li p.progress {
        position: absolute;
        width: 100%;
        bottom: 0;
        left: 0;
        height: 8px;
        overflow: hidden;
        z-index: 50;
        background-color: transparent;
        box-shadow: none;
        background-image: none;
    }
    #${input}UploaderDiv .filelist li p.progress span {
        display: none;
        overflow: hidden;
        width: 0;
        height: 100%;
        background: #1483d8 url(${ctxStatic}/webuploader/images/progress.png) repeat-x;

        -webit-transition: width 200ms linear;
        -moz-transition: width 200ms linear;
        -o-transition: width 200ms linear;
        -ms-transition: width 200ms linear;
        transition: width 200ms linear;

        -webkit-animation: progressmove 2s linear infinite;
        -moz-animation: progressmove 2s linear infinite;
        -o-animation: progressmove 2s linear infinite;
        -ms-animation: progressmove 2s linear infinite;
        animation: progressmove 2s linear infinite;

        -webkit-transform: translateZ(0);
    }

    @-webkit-keyframes progressmove {
        0% {
            background-position: 0 0;
        }
        100% {
            background-position: 17px 0;
        }
    }
    @-moz-keyframes progressmove {
        0% {
            background-position: 0 0;
        }
        100% {
            background-position: 17px 0;
        }
    }
    @keyframes progressmove {
        0% {
            background-position: 0 0;
        }
        100% {
            background-position: 17px 0;
        }
    }

    #${input}UploaderDiv .filelist li p.imgWrap {
        position: relative;
        z-index: 2;
        line-height: 110px;
        vertical-align: middle;
        overflow: hidden;
        width: 110px;
        height: 110px;

        -webkit-transform-origin: 50% 50%;
        -moz-transform-origin: 50% 50%;
        -o-transform-origin: 50% 50%;
        -ms-transform-origin: 50% 50%;
        transform-origin: 50% 50%;

        -webit-transition: 200ms ease-out;
        -moz-transition: 200ms ease-out;
        -o-transition: 200ms ease-out;
        -ms-transition: 200ms ease-out;
        transition: 200ms ease-out;
    }

    #${input}UploaderDiv .filelist li img {
        width: 100%;
    }

    #${input}UploaderDiv .filelist li p.error {
        background: #f43838;
        color: #fff;
        position: absolute;
        bottom: 0;
        left: 0;
        height: 28px;
        line-height: 28px;
        width: 100%;
        z-index: 100;
    }

    #${input}UploaderDiv .filelist li .success {
        display: block;
        position: absolute;
        left: 0;
        bottom: 0;
        height: 40px;
        width: 100%;
        z-index: 200;
        background: url(${ctxStatic}/webuploader/images/success.png) no-repeat right bottom;
    }

    #${input}UploaderDiv .filelist div.file-panel {
        position: absolute;
        height: 0;
        filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#80000000', endColorstr='#80000000')\0;
        background: rgba( 0, 0, 0, 0.5 );
        width: 100%;
        top: 0;
        left: 0;
        overflow: hidden;
        z-index: 300;
    }

    #${input}UploaderDiv .filelist div.file-panel span {
        width: 24px;
        height: 24px;
        display: inline;
        float: right;
        text-indent: -9999px;
        overflow: hidden;
        background: url(${ctxStatic}/webuploader/images/icons.png) no-repeat;
        margin: 5px 1px 1px;
        cursor: pointer;
    }

    #${input}UploaderDiv .filelist div.file-panel span.rotateLeft {
        background-position: 0 -24px;
    }
    #${input}UploaderDiv .filelist div.file-panel span.rotateLeft:hover {
        background-position: 0 0;
    }

    #${input}UploaderDiv .filelist div.file-panel span.rotateRight {
        background-position: -24px -24px;
    }
    #uploader .filelist div.file-panel span.rotateRight:hover {
        background-position: -24px 0;
    }

    #${input}UploaderDiv .filelist div.file-panel span.cancel {
        background-position: -48px -24px;
    }
    #${input}UploaderDiv .filelist div.file-panel span.cancel:hover {
        background-position: -48px 0;
    }

    #${input}UploaderDiv .statusBar {
        height: 63px;
        border-top: 1px solid #dadada;
        padding: 0 20px;
        line-height: 63px;
        vertical-align: middle;
        position: relative;
    }

    #${input}UploaderDiv .statusBar .progress {
        border: 1px solid #1483d8;
        width: 198px;
        background: #fff;
        height: 18px;
        position: relative;
        display: inline-block;
        text-align: center;
        line-height: 20px;
        color: #6dbfff;
        position: relative;
        margin-right: 10px;
    }
    #${input}UploaderDiv .statusBar .progress span.percentage {
        width: 0;
        height: 100%;
        left: 0;
        top: 0;
        background: #1483d8;
        position: absolute;
    }
    #${input}UploaderDiv .statusBar .progress span.text {
        position: relative;
        z-index: 10;
    }

    #${input}UploaderDiv .statusBar .info {
        display: inline-block;
        font-size: 14px;
        color: #666666;
    }

    #${input}UploaderDiv .statusBar .btns {
        position: absolute;
        top: 10px;
        right: 20px;
        line-height: 40px;
    }

    #${input}FilePicker2 {
        display: inline-block;
        float: left;
    }

    #${input}UploaderDiv .statusBar .btns .webuploader-pick,
    #${input}UploaderDiv .statusBar .btns .uploadBtn,
    #${input}UploaderDiv .statusBar .btns .uploadBtn.state-uploading,
    #${input}UploaderDiv .statusBar .btns .uploadBtn.state-paused {
        background: #ffffff;
        border: 1px solid #cfcfcf;
        color: #565656;
        padding: 0 18px;
        display: inline-block;
        border-radius: 3px;
        margin-left: 10px;
        cursor: pointer;
        font-size: 14px;
        float: left;
    }
    #${input}UploaderDiv .statusBar .btns .webuploader-pick-hover,
    #${input}UploaderDiv .statusBar .btns .uploadBtn:hover,
    #${input}UploaderDiv .statusBar .btns .uploadBtn.state-uploading:hover,
    #${input}UploaderDiv .statusBar .btns .uploadBtn.state-paused:hover {
        background: #f0f0f0;
    }

    #${input}UploaderDiv .statusBar .btns .uploadBtn {
        background: #00b7ee;
        color: #fff;
        border-color: transparent;
    }
    #${input}UploaderDiv .statusBar .btns .uploadBtn:hover {
        background: #00a2d4;
    }

    #${input}UploaderDiv .statusBar .btns .uploadBtn.disabled {
        pointer-events: none;
        opacity: 0.6;
    }
</style>
<style>
    .form-horizontal .controls {text-align:left; overflow-x: unset;overflow-y: unset;}
</style>
<ol id="${input}Preview"></ol>
<c:if test="${!readonly}">
    <div id="${input}UploaderDiv">
        <div class="queueList">
            <div id="${input}DndArea" class='<c:if test="${!simple}">placeholder</c:if>'>
                <div id="${input}FilePicker"></div>
                <c:if test="${!simple}">
                    <p>或将
                        <c:if test="${type eq 'thumb' || type eq 'images'}">图片</c:if>
                        <c:if test="${type eq 'files'}">文件</c:if>
                        拖到这里，单次最多可选
                        <c:if test="${selectMultiple}">20</c:if>
                        <c:if test="${!selectMultiple}">1</c:if>
                        <c:if test="${type eq 'thumb' || type eq 'images'}">张</c:if>
                        <c:if test="${type eq 'files'}">份</c:if>
                    </p>
                </c:if>
            </div>
        </div>
        <div class="statusBar" style="display:none;">
            <div class="progress">
                <span class="text">0%</span>
                <span class="percentage"></span>
            </div><div class="info"></div>
            <div class="btns">
                <div id="${input}FilePicker2"></div><div class="uploadBtn">开始上传</div>
            </div>
        </div>
    </div>
</c:if>
<script type="text/javascript">
    function ${input}Del(obj){
        var url = $(obj).prev().attr("url");
        $("#${input}").val($("#${input}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
        // var fileName = url;
        // var files = uploader.getFiles();
        // $.each(files.reverse(), function(index, f){
        //     if(fileName.endsWith("/" + f.name) || fileName.endsWith("\\" + f.name)) {
        //         uploader.removeFile(f);
        //         return ;
        //     }
        // });

        ${input}Preview();
    }
    function ${input}DelAll(){
        $("#${input}").val("");
        ${input}Preview();
    }
    function ${input}Preview(){
        var li, urls = $("#${input}").val().split("|");
        $("#${input}Preview").children().remove();
        for (var i=0; i<urls.length; i++){
            if (urls[i]!=""){//<c:if test="${type eq 'thumb' || type eq 'images'}">
                li = "<li><img src=\"${pageContext.request.contextPath}"+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:${empty maxWidth ? 200 : maxWidth}px;max-height:${empty maxHeight ? 200 : maxHeight}px;_height:${empty maxHeight ? 200 : maxHeight}px;border:0;padding:3px;\">";//</c:if><c:if test="${type ne 'thumb' && type ne 'images'}">
                li = "<li><a href=\"${pageContext.request.contextPath}" +urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";//</c:if>
                li += "&nbsp;&nbsp;<c:if test="${!readonly}"><a href=\"javascript:\" onclick=\"${input}Del(this);\">×</a></c:if></li>";
                $("#${input}Preview").append(li);
            }
        }
        if ($("#${input}Preview").text() == ""){
            $("#${input}Preview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
        }
    }
    ${input}Preview();
</script>
<script type="text/javascript">
    (function( $ ){
        $(function() {
            var $wrap = $('#${input}UploaderDiv'),

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
                <c:if test="${!simple}">
                    $placeHolder = $wrap.find( '.placeholder' ),
                </c:if>
                <c:if test="${simple}">
                    $placeHolder = $("#${input}DndArea"),
                </c:if>


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

                // 所有文件的进度信息，key为file id
                percentages = {},
                // 判断浏览器是否支持图片的base64
                isSupportBase64 = ( function() {
                    var data = new Image();
                    var support = true;
                    data.onload = data.onerror = function() {
                        if( this.width != 1 || this.height != 1 ) {
                            support = false;
                        }
                    }
                    data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                    return support;
                } )(),

                // 检测是否已经安装flash，检测flash的版本
                flashVersion = ( function() {
                    var version;

                    try {
                        version = navigator.plugins[ 'Shockwave Flash' ];
                        version = version.description;
                    } catch ( ex ) {
                        try {
                            version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                        } catch ( ex2 ) {
                            version = '0.0';
                        }
                    }
                    version = version.match( /\d+/g );
                    return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
                } )(),

                supportTransition = (function(){
                    var s = document.createElement('p').style,
                        r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                    s = null;
                    return r;
                })(),

                // WebUploader实例
                uploader;

            if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {

                // flash 安装了但是版本过低。
                if (flashVersion) {
                    (function(container) {
                        window['expressinstallcallback'] = function( state ) {
                            switch(state) {
                                case 'Download.Cancelled':
                                    alert('您取消了更新！')
                                    break;

                                case 'Download.Failed':
                                    alert('安装失败')
                                    break;

                                default:
                                    alert('安装已成功，请刷新！');
                                    break;
                            }
                            delete window['expressinstallcallback'];
                        };

                        var swf = './expressInstall.swf';
                        // insert flash object
                        var html = '<object type="application/' +
                            'x-shockwave-flash" data="' +  swf + '" ';

                        if (WebUploader.browser.ie) {
                            html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                        }

                        html += 'width="100%" height="100%" style="outline:0">'  +
                            '<param name="movie" value="' + swf + '" />' +
                            '<param name="wmode" value="transparent" />' +
                            '<param name="allowscriptaccess" value="always" />' +
                            '</object>';

                        container.html(html);

                    })($wrap);

                    // 压根就没有安转。
                } else {
                    $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
                }

                return;
            } else if (!WebUploader.Uploader.support()) {
                alert( 'Web Uploader 不支持您的浏览器！');
                return;
            }

            // 实例化
            uploader = WebUploader.create({
                pick: {
                    id: '#${input}FilePicker',
                    label: '点击选择' + <c:if test="${type eq 'thumb' || type eq 'images'}">'图片'</c:if><c:if test="${type eq 'files'}">'文件'</c:if>

                },
                formData: {
                    path: "${uploadPath}"
                },
                dnd: '#${input}DndArea',
                paste: '#${input}UploaderDiv',
                swf: '${ctxStatic}/webuploader/0.1.5/Uploader.swf',
                chunked: false,
                // chunkSize: 512 * 1024,
                server: '${ctx}/file/upload',
                // runtimeOrder: 'flash',

                <c:if test="${type eq 'thumb' || type eq 'images'}">
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                },
                </c:if>

                // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                disableGlobalDnd: true,
                fileNumLimit: 20,
                fileSizeLimit: 200 * 1024 * 1024,    // 200 M
                fileSingleSizeLimit: 5000 * 1024 * 1024    // 50 M
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

            // uploader.on('filesQueued', function() {
            //     uploader.sort(function( a, b ) {
            //         if ( a.name < b.name )
            //           return -1;
            //         if ( a.name > b.name )
            //           return 1;
            //         return 0;
            //     });
            // });

            // 添加“添加文件”的按钮，
            uploader.addButton({
                id: '#${input}FilePicker2',
                label: '继续添加'
            });

            uploader.on('ready', function() {
                window.uploader = uploader;
            });

            // 当有文件添加进来时执行，负责view的创建
            function addFile( file ) {
                var $li = $( '<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>'+
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

                if ( file.getStatus() === 'invalid' ) {
                    showError( file.statusText );
                } else {
                    // @todo lazyload
                    $wrap.text( '预览中' );
                    uploader.makeThumb( file, function( error, src ) {
                        var img;

                        if ( error ) {
                            $wrap.text( '不能预览' );
                            return;
                        }

                        if( isSupportBase64 ) {
                            img = $('<img src="'+src+'">');
                            $wrap.empty().append( img );
                        } else {
                            $.ajax('../../server/preview.php', {
                                method: 'POST',
                                data: src,
                                dataType:'json'
                            }).done(function( response ) {
                                if (response.result) {
                                    img = $('<img src="'+response.result+'">');
                                    $wrap.empty().append( img );
                                } else {
                                    $wrap.text("预览出错");
                                }
                            });
                        }
                    }, thumbnailWidth, thumbnailHeight );

                    percentages[ file.id ] = [ file.size, 0 ];
                    file.rotation = 0;
                }

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
                        percentages[ file.id ][ 1 ] = 1;
                    } else if ( cur === 'interrupt' ) {
                        showError( 'interrupt' );
                    } else if ( cur === 'queued' ) {
                        percentages[ file.id ][ 1 ] = 0;
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
                        // use jquery animate to rotation
                        // $({
                        //     rotation: rotation
                        // }).animate({
                        //     rotation: file.rotation
                        // }, {
                        //     easing: 'linear',
                        //     step: function( now ) {
                        //         now = now * Math.PI / 180;

                        //         var cos = Math.cos( now ),
                        //             sin = Math.sin( now );

                        //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                        //     }
                        // });
                    }


                });

                $li.appendTo( $queue );
            }

            // 负责view的销毁
            function removeFile( file ) {
                var $li = $('#'+file.id);

                delete percentages[ file.id ];
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
            }

            function updateTotalProgress() {
                var loaded = 0,
                    total = 0,
                    spans = $progress.children(),
                    percent;

                $.each( percentages, function( k, v ) {
                    total += v[ 0 ];
                    loaded += v[ 0 ] * v[ 1 ];
                } );

                percent = total ? loaded / total : 0;


                spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
                spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
                updateStatus();
            }

            function updateStatus() {
                var text = '', stats;

                <c:if test="${type eq 'thumb' || type eq 'images'}">var typeName = '图片', unit='张';</c:if>
                <c:if test="${type eq 'files'}">var typeName = '文件', unit='份';</c:if>
                if ( state === 'ready' ) {
                    // text = '选中' + fileCount + unit + typeName+'';
                    text = '选中' + fileCount + unit + typeName+'，共' +
                        WebUploader.formatSize(fileSize) + '。';
                    // text = '选中' + fileCount + '张图片，共' +
                    //     WebUploader.formatSize( fileSize ) + '。';
                } else if ( state === 'confirm' ) {
                    stats = uploader.getStats();
                    if ( stats.uploadFailNum ) {
                        // text = '已成功上传' + stats.successNum+ '张照片至XX相册，'+
                        // stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片'
                        text = '已成功上传' + stats.successNum + unit + typeName+'，'
                            stats.uploadFailNum + unit + typeName+'上传失败，<a class="retry" href="#">重新上传</a>失败'+typeName+''
                    }

                } else {
                    stats = uploader.getStats();
                    // text = '共' + fileCount + '张（' +
                    //     WebUploader.formatSize( fileSize )  +
                    //     '），已上传' + stats.successNum + '张';
                    //
                    // if ( stats.uploadFailNum ) {
                    //     text += '，失败' + stats.uploadFailNum + '张';
                    // }

                    text = '共' + fileCount + unit + '（' +
                        WebUploader.formatSize(fileSize) +
                        '），已上传' + stats.successNum + unit;

                    if (stats.uploadFailNum) {
                        text += '，失败' + stats.uploadFailNum + unit;
                    }
                }

                ${input}Preview(); //NEW CODE
                $info.html( text );
            }

            function setState( val ) {
                var file, stats;

                if ( val === state ) {
                    return;
                }

                $upload.removeClass( 'state-' + state );
                $upload.addClass( 'state-' + val );
                state = val;

                switch ( state ) {
                    case 'pedding':
                        $placeHolder.removeClass( 'element-invisible' );
                        $queue.hide();
                        $statusBar.addClass( 'element-invisible' );
                        uploader.refresh();
                        break;

                    case 'ready':
                        $placeHolder.addClass( 'element-invisible' );
                        $( '#${input}FilePicker2' ).removeClass( 'element-invisible');
                        $queue.show();
                        $statusBar.removeClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'uploading':
                        $( '#${input}FilePicker2' ).addClass( 'element-invisible' );
                        $progress.show();
                        $upload.text( '暂停上传' );
                        break;

                    case 'paused':
                        $progress.show();
                        $upload.text( '继续上传' );
                        break;

                    case 'confirm':
                        $progress.hide();
                        $( '#${input}FilePicker2' ).removeClass( 'element-invisible' );
                        $upload.text( '开始上传' );

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
                percentages[ file.id ][ 1 ] = percentage;
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
                    var url = data.result;
                    <c:if test="${selectMultiple}">
                    <%--$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));--%>
                    var newUrl = "";
                    var oldUrl = $("#${input}").val();
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
                    $("#${input}").val(newUrl);
                    </c:if>
                    <c:if test="${!selectMultiple}">
                    $("#${input}").val(url);
                    </c:if>
                    // uploader.cancelFile(file);

                    // uploader.trigger( 'fileDequeued', file ); //NEW CODE
                }
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
                    text = "文件大小超出限制，不得超过5G";
                } else{
                    text = 'Error: ' + code;
                }
                $info.html( text );
                // alert( 'Eroor: ' + code );
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
                // alert( 'todo' );
            } );

            $upload.addClass( 'state-' + state );
            updateTotalProgress();
        });

    })( jQuery );
</script>