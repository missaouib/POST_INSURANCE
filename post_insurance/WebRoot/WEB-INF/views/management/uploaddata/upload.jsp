<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="${contextPath}/styles/webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/styles/upload/upload.css">
<script type="text/javascript" src="${contextPath}/styles/webuploader/webuploader.js"></script>
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
text-shadow: 0 1px 0 #fff;
background-image: -webkit-linear-gradient(top,#fff 0,#e0e0e0 100%);
background-image: linear-gradient(to bottom,#fff 0,#e0e0e0 100%);
background-repeat: repeat-x;
border-color: #dbdbdb;
border-color: #ccc;
}
.btn {
text-align: center;
cursor: pointer;
border: 1px solid transparent;
width: 80px;
height: 30px;
overflow: hidden;
}
-->
</style>
<h2 class="contentTitle">上传保单数据</h2>

<div class="pageContent" style="margin: 0 10px" layoutH="50">
	模板:
		<form:select path="template" name="template" id="template" style="width:100px;" items="${templateList}"/>
	<div id="uploader" class="wu-example">
	    <!--用来存放文件信息-->
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
	<br />
	<br />
	<br />
	<br />
	<br />

	<pre id="console"></pre>
	<br />
</div>

<script type="text/javascript"> 
var strFileGroup = new Date().Format("yyyyMMddhhmmss");
var template = $("#template").val();
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
        server: '/management/uploaddata/upload',
		
        // 选择文件的按钮。可选。
        accept: {
            title: '请选择数据文件',
            extensions: 'csv,xls'
        },
        
        formData:{
            fileGroup: strFileGroup
        },
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        
       	disableGlobalDnd: true,
        fileNumLimit: 3,
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
            $.ajax({
                type: 'post',
                url: "/uploaddatamanage/uploaddata/import",
                dataType: "text",
                data: { "strFileGroup": strFileGroup, "ny": 201506, "template": template, "memo": "" },
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
                        if(window.confirm("数据已上传，但由于格式问题未能入库，将对后续的数据分析产生影响,是否当月报数以该文件为准？")) {
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