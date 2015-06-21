<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理系统</title>
<style>
body,div,ul,li,dl,dd,dt { margin:0; padding:0;}
li { list-style-type:none;}
.top { width:100%; height:80px; background:url(${contextPath}/images/top_bg.jpg);}
.top .top1 { width:1000px; height:80px; margin: 0 auto;}
.top .anniu { float:right; margin-top:25px;}
.top .anniu a { float:left; margin-right:13px;}
.top .anniu a:link,.top .anniu a:visited { width:52px; height:27px; display:block; text-decoration:none; line-height:27px; text-align:center; background:url(${contextPath}/images/anniu.jpg) no-repeat; font-size:15px; font-weight:bold; color:#454545;}
.top .dao { background:url(${contextPath}/images/dao_bg.jpg); width:100%; height:56px; float:left;}
.top .dao .daon { width:1000px; height:56px; margin: 0 auto; margin-left: 260px;}
.top .dao .daon ul li { float:left; height:56px; line-height:56px;}
.top .dao .daon ul .daob { background:#9BB817;}
.top .dao .daon ul .daob a { color:#FFF;}
.top .dao .daon ul li .daobian { display:block; width:8px; background:url(${contextPath}/images/dao_ce_bg.jpg) no-repeat; height:56px; float:right;}
.top .dao .daon ul li a { padding-left:30px; margin-right:30px; text-decoration:none; color:#5B5B5B; font-weight:bold; font-size:15px;}
.certen { float:left; width:100%;}
.main_visual { float:left; width:100%; height:100%; padding-bottom:35px;}
.main_image img { width:100%; height:100%;}
.certen .c2 { width:750px; margin: 0 auto;}
.certen .c2 dl { height:225px; width:228px; float:left; margin-right:20px;}
.certen .c2 dl dt img { width:228px; height:180px;  border:3px solid #BFDA48;-moz-border-radius: 15px;      /* Gecko browsers */
    -webkit-border-radius: 15px;   /* Webkit browsers */
    border-radius:15px;            /* W3C syntax */}
.certen .c2 dl dd { width:228px; text-align: center; margin-top:15px;}
.certen .c2 dl dd a { position:relative; padding-left:16px;}
.certen .c2 dl dd a:link,.certen .c2 dl dd a:visited { color:#C03A41; font-size:13px; text-decoration:none;}
.certen .c2 dl dd .adian { display:block; width:15px; height:15px; background:url(${contextPath}/images/Adian.jpg) no-repeat; position:absolute; top:0; left:0;}
.login { width:800px; margin: 100px auto; text-align: center;}
.down { width:100%; background:#9BB817; height:44px; color:#FFF; font-size:12px; font-weight:bold; text-align:center; line-height:44px; margin-bottom:2px; float:left; margin-top:80px;}
.down a { position:relative;}
.down a:link,.down a:visited { color:white; font-size:13px; text-decoration:none;}
.STYLE1 {
	font-size: 11pt;
	font-weight: bold;
}
</style>
<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${contextPath}/styles/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${contextPath}/styles/jquery/jquery.messager.js"></script>
<script src="${contextPath}/styles/treeTable/jquery.treeTable.min.js" type="text/javascript"></script>
<%-- form验证 --%>
<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>

<script src="${contextPath}/styles/dwz/js/dwz.min.js" type="text/javascript"></script>

<script src="${contextPath}/styles/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<%-- 自定义JS --%>
<script src="${contextPath}/styles/dwz/js/customer.js" type="text/javascript"></script>
<script src="${contextPath}/styles/postinsurance/js/postinsurance.js" type="text/javascript"></script>
<%-- upload --%>
<script src="${contextPath}/styles/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>

<script src="${contextPath}/js/DateFormat.js" type="text/javascript"></script>

<script type="text/javascript" src="${contextPath}/js/plupload-2.1.2/js/moxie.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/plupload-2.1.2/js/plupload.dev.js"></script>
</head>

<body>
<div class="top">
  <div class="top1">
    	<div class="logo"><a href="/web/index"><img src="${contextPath}/images/logo.png" width="202" height="80" style="margin-top:20px;border:none;" /></a>
            <div class="anniu">
            </div>
        </div>
  </div>
<div class="dao">
	<div class="daon">
		<ul>
			<li><a href="/web/index">首页</a><span class="daobian"></span></li>
        	<shiro:user>  
        	<li class="daob"><a href="/uploaddatamanage/uploaddata/index">数据服务</a><span class="daobian"></span></li>
            <li><a href="/uploaddatamanage/document/index">行业报告</a><span class="daobian"></span></li>
            </shiro:user>
        	<li><a href="#">新闻动态</a><span class="daobian"></span></li>
            <shiro:guest>
            <li><a href="/web/tologin">登录</a><span class="daobian"></span></li>
            </shiro:guest>
            <shiro:user>
            <li><a href="/members/user/index">用户管理</a><span class="daobian"></span></li>
            <li><a href="/web/logout">(<shiro:principal/>)退出</a><span class="daobian"></span></li>
            </shiro:user>
        </ul>
	</div>
</div>
</div>
<div class="certen">
  <div class="c2">
<div id="divUploadData" class="pageContent" layoutH="0">
	<div style="padding-left:100px;padding-top:30px; height:400px;">
		<div id="uploadContainer" style="width:800px;">
			年月
			<select id="listNY" name="ny" style="width:100px;">
				<c:forEach var="item" items="${ny}">
				<option value="${item}">${item}</option>
				</c:forEach>
			</select>
			模板
			<select id="lstTemplate" name="lstTemplate" style="width:100px;">
				<option value="0">标准模板</option>
				<option value="1">默认模板</option>
			</select>
		    <a class="btn btn-primary" id="pickfiles"><i class="icon-search"></i> 选择文件</a>
		    <a class="btn btn-success" id="uploadfiles"><i class="icon-ok icon-white"></i> 开始上传</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <br/>
		    <br/>
		    <a href="${contextPath}/download/上传模板(详细版).xls" target="_blank">上传模板xls格式下载</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="${contextPath}/download/上传模板(详细版).xlsx" target="_blank">上传模板xlsx格式下载</a>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href="${contextPath}/download/数据上传说明.pptx" target="_blank">数据上传说明下载</a>
		</div>
		<br />

		<pre id="console"></pre>
		<br />
		<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
	</div>
</div>
<script type="text/javascript">
    var iFileIndex = 0;
    var iFileCount = 0;
    var strFileGroup = "";
    var uploadChunkSize = 512;//kb
    var uploader = null;
    var ny = null;
    var template = 0;
    uploader = new plupload.Uploader({
        runtimes: 'flash,silverlight,html5,html4',
        browse_button: 'pickfiles',
        container: document.getElementById('uploadContainer'),
        url: '/uploaddatamanage/uploaddata/upload', // upload handler
        urlCheck: '/uploaddatamanage/uploaddata/check', // upload chunking checker
        chunk_size: uploadChunkSize + 'kb',
        send_file_name: true,
        send_chunk_number: true,
        flash_swf_url : '${contextPath}/js/plupload-2.1.2/js/Moxie.swf',
		silverlight_xap_url : '${contextPath}/js/plupload-2.1.2/js/Moxie.xap',
        //drop_element: true,
        multipart_params: {
            fileSize: 0,
            chunkSize: uploadChunkSize,
            fileCount: 0,
            fileIndex: 0,
            fileGroup: ""
        },
        multiple_queues: false,
        multi_selection: true,
        filters: {
            max_file_size: '1024mb',
            mime_types: [{ title: "Data files", extensions: "txt,csv,xls,xlsx,dbf,mdb" }]
        },
        init: {
            PostInit: function () {
                document.getElementById('filelist').innerHTML = '';

                document.getElementById('uploadfiles').onclick = function () {
			        $("#console").html(" ");                
                    // 检查当前选择月份，是否已上传
                	ny = $("#listNY").val();
                	template = $("#lstTemplate").val();
    		        $.ajax({
                         type: 'post',
                         url: "/uploaddatamanage/uploaddata/checkimportny",
                         dataType: "text",
                         data: {"ny": ny},
                         success: function (data) {
	                         var response = $.parseJSON(data);
							 if (response.result == "success") {
			                    uploader.start();
	                         } else {
	                         	alert(response.message);
	                         }
                         },
                         error: function (data) {
                         	 alert("尝试上传出错，请稍后重试。");
                         }
                     });                

                    return false;
                };
            },
            FilesAdded: function (up, files) {
                iFileIndex = 0;
                iFileCount = 0;
                strFileGroup = new Date().Format("yyyyMMddhhmmss");
                document.getElementById('filelist').innerHTML = '<div class="div_allinline">共选择了' + files.length + '文件。</div>';
                document.getElementById('console').innerHTML = "";
                
                var i = 0;
                plupload.each(files, function (file) {
                    i++;
                    document.getElementById('filelist').innerHTML += '<div class="div_allinline">' + '<div class="file" id="' + file.id + '">' + i + '、' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b><a class="file_delete" data-val=' + file.id + ' style="color:red;">&nbsp;&nbsp;取消&nbsp;&nbsp;</a></div>' + 
                    '<div id="div' + file.id + '" class="loading"><div id="loadingdiv' + file.id + '"  class="loadingdiv"></div></div>' + '</div>';
                    file.index = i;

                    iFileCount++;
                });

                uploader.settings.multipart_params.fileCount = iFileCount;
                uploader.settings.multipart_params.fileGroup = strFileGroup;
            },
            BeforeUpload: function (up, file) {
                uploader.settings.multipart_params.fileSize = file.origSize;
                iFileIndex++;
                uploader.settings.multipart_params.fileIndex = iFileIndex;

                $("#div" + file.id).css("display", "block");
            },
            FileUploaded: function (up, file, info) {
                if (iFileIndex !== iFileCount) {
                    return;
                }

                // 执行数据导入
                if (info != null && info.response != null) {
                    var response = $.parseJSON(info.response);
                    if (response.result != null && response.result == 'success' && response.strFileName != null) {
                        $("#console").html("开始导入数据");
                        var tImport = setInterval(function () {
                            $("#console").html("正在导入数据......");
                        }, 1000);

                        $.ajax({
                            type: 'post',
                            url: "/uploaddatamanage/uploaddata/import?flag=web",
                            dataType: "text",
                            data: { strFileGroup: strFileGroup, "ny": ny, "template": template },
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
				                            data: { strFileGroup: strFileGroup, "ny": ny },
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
                }
            },

            UploadProgress: function (up, file) {
                var iPercent = file.percent;
                var tag = document.getElementById(file.id).getElementsByTagName('b');
                var loadingdiv = $("#loadingdiv" + file.id);

                if (iPercent <= 100) {
                    if (tag) {
                        //tag[0].innerHTML = '<span>' + iPercent + "%</span>";
                    }

                    if (loadingdiv) {
                        loadingdiv.css("width", iPercent + "%"); //控制#loading div宽度 
                        loadingdiv.html(iPercent + "%"); //显示百分比 
                    }
                }
            },

            Error: function (up, err) {
                document.getElementById('console').innerHTML += "\n" + err.file.index + "、" + err.file.name + " 上传错误,错误代码：" + err.code + "，错误信息: " + err.message;

                //$("#div" + err.file.id).css("display", "none");
                //$("#loadingdiv" + err.file.id).css("display", "none");
            }
        }
    });

    uploader.init();

	$(document).on('click', '.file_delete', function(){
		$(this).parent().remove(); 
		var toremove = ''; 
		var id = $(this).attr("data-val"); 
		for(var i in uploader.files){ 
			if(uploader.files[i].id === id){ 
				toremove = i;
				break;
			} 
		}
		uploader.files.splice(toremove, 1);
		uploader.settings.multipart_params.fileCount -= 1;
		document.getElementById('filelist').innerHTML = '<div class="div_allinline">共选择了' + uploader.settings.multipart_params.fileCount + '文件。</div>';
	});

	$(document).bind("tabClosing", function(event){
		try {
			var tabid = $(event.target).attr('tabid');
			var currentTabid = $("#divUploadData").parent().attr('tabid');
			if(tabid==currentTabid) {
				if(uploader) {
	        		uploader.stop();
	        		uploader.destroy();
	        	}
			}
		} catch(e) {
		}
	});
</script> 
</div>
</div>

<%@ include file="../../webroot.inc.jsp" %>
</body>
</html>
