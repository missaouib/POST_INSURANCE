<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script type="text/javascript" src="${contextPath}/styles/webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/styles/upload/upload.css">

<style>
<!--
p {
display: block;
-webkit-margin-before: 1em;
-webkit-margin-after: 1em;
-webkit-margin-start: 0px;
-webkit-margin-end: 0px;
}

h4 {
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-weight: 500;
line-height: 1.1;
color: inherit;
}

.btn-default {
width: 80px;
line-height: 23px;
text-align: center;
color: #fff;
border-radius: 5px;
margin:0 20px 20px 0;
position: relative;
overflow: hidden;
}
.btn {
border:0px solid #1e7db9;
box-shadow: 0 1px 2px #8fcaee inset,0 -1px 0 #497897 inset,0 -2px 3px #8fcaee inset;
background: -webkit-linear-gradient(top,#42a4e0,#2e88c0);
background: -moz-linear-gradient(top,#42a4e0,#2e88c0);
background: linear-gradient(top,#42a4e0,#2e88c0);
}

.webuploader-container {
	position: relative;
}
.webuploader-element-invisible {
	position: absolute !important;
	clip: rect(1px 1px 1px 1px); /* IE6, IE7 */
    clip: rect(1px,1px,1px,1px);
}
.webuploader-pick {
	position: relative;
	display: inline-block;
	cursor: pointer;
	background: #00b7ee;
	padding: 10px 15px;
	color: #fff;
	text-align: center;
	border-radius: 3px;
	overflow: hidden;
}
.webuploader-pick-hover {
	background: #00a2d4;
}

.webuploader-pick-disable {
	opacity: 0.6;
	pointer-events:none;
}

.wu-example {
    position: relative;
    top: 155px;
    padding: 15px 15px 15px;
    margin: 15px 0;
    background-color: #fafafa;
    box-shadow: inset 0 3px 6px rgba(0, 0, 0, .05);
    border-color: #e5e5e5 #eee #eee;
    border-style: solid;
    border-width: 1px 0;
}
.wu-example:after {
    position: absolute;
    top: 10px;
    left: 15px;
    font-size: 12px;
    font-weight: bold;
    color: #bbb;
    text-transform: uppercase;
    letter-spacing: 1px;
}

#picker {
    display: inline-block;
    line-height: 1.428571429;
    vertical-align: middle;
    margin: 0 12px 0 0;
}
#picker .webuploader-pick {
    padding: 6px 12px;
    display: block;
}

p, h4 {
    -webkit-text-size-adjust: none;
}
-->
</style>
<div class="pageContent">
<form method="post" action="${contextPath }/notice/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="58" style="margin: 0 10px">
		<p>
			<label>标题：</label>
			<input type="text" name="noticeTitle" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>接收人：</label>
			<input type="text" name="receiver" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p>
			<label>接收机构：</label>
			<input type="text" name="receiveOrg" class="input-medium validate[required,maxSize[32]] required" maxlength="32"/>
		</p>
		<p class="nowrap">
			<label>接收机构：</label>
			<textarea name="content" class="required" rows="7" cols="80"></textarea>
		</p>
		<p>
			<div id="uploader" class="wu-example">
			    <div id="thelist" class="uploader-list"></div>
			    <div class="btns">
			        <div id="picker">选择文件</div>
			        <button id="ctlBtn" class="btn btn-default">开始上传</button>
			    </div>
			</div>
			<br />
			<br />
			<br />
			<br />
			<pre id="console"></pre>
			<br />
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>

<script type="text/javascript"> 
var strFileGroup = new Date().Format("yyyyMMddhhmmss");

//文件上传
jQuery(function() {
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;
    
    uploader = WebUploader.create({
        // 不压缩image
        resize: false,
        // swf文件路径
        swf: '${contextPath}/styles/webuploader/Uploader.swf',
        // 文件接收服务端。
        server: '/notice/upload',
        // 选择文件的按钮。可选。
        accept: {
            title: '请选择数据文件',
            extensions: 'csv,xls,zip,rar,doc,et,wps,ppt'
        },
        formData:{
            fileGroup: strFileGroup
        },
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
       	disableGlobalDnd: true,
        fileNumLimit: 2,
        fileSizeLimit: 200 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 50 * 1024 * 1024    // 50 M
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.append( '<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
        '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');
        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
              '<div class="progress-bar" role="progressbar" style="width: 0%">' +
              '</div>' +
            '</div>').appendTo( $li ).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');
        $percent.css( 'width', percentage * 100 + '%' );
    });

    uploader.on( 'uploadSuccess', function( file, response  ) {
        $( '#'+file.id ).find('p.state').text('已上传');
        
        if (response.result != null && response.result == 'success' && response.strFileName != null) {
            $("#console").html("开始导入数据");
            var tImport = setInterval(function () {
                $("#console").html("正在导入数据......");
            }, 1000);
            template = $("#template").val();
            //alert(template);
            $.ajax({
                type: 'post',
                url: "/uploaddatamanage/uploaddata/import",
                dataType: "text",
                data: { "strFileGroup": strFileGroup, "ny": "${ny}", "template": template, "memo": "" },
                success: function (data) {
                    clearInterval(tImport);
                    var response = $.parseJSON(data);
                    if(response.result == "confirm") {
                    	if(window.confirm(response.message + "，是否继续导入？")) {
                    		$("#console").html("导入数据成功。");
                    	} else {
		                        $.ajax({
	                            type: 'post',
	                            url: "/uploaddatamanage/uploaddata/cancelimport",
	                            dataType: "text",
	                            data: { strFileGroup: strFileGroup, "ny": 1 },
	                            success: function (data) {
	                                var response = $.parseJSON(data);
									if (response.result == "success") {
	                                    $("#console").html("取消导入数据。");
	                                } else {
	                                    $("#console").html("取消导入数据失败！");
	                                    if (response.message != null) {
	                                        alert(response.message);
	                                    }
	                                }
	                            }
	                        });
                    	}
                    } else if (response.result == "success") {
                        $("#console").html("导入数据成功。");
                    } else {
                        if (response.message != null) {
                            alert(response.message);
                        }
                        if(window.confirm("数据已上传，但由于格式问题未能入库,是否以该文件为准？")) {
                        	$("#console").html("数据已上传，但由于格式问题未能入库，将对后续的数据分析产生影响。");
                        } else {
                        	$.ajax({
	                            type: 'post',
	                            url: "/uploaddatamanage/uploaddata/cancelupload",
	                            dataType: "text",
	                            data: { strFileGroup: strFileGroup, "ny": ny },
	                            success: function (data) {
	                            }
	                        });
                        }
                    }
                }
            });
        } else {
            alert(response.message);
        }
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('p.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
        }

        if ( state === 'uploading' ) {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});
</script>