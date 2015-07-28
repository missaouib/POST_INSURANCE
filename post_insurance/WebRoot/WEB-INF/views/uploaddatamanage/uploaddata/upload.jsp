<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<link href="${contextPath}/js/bootstrap/css/plupload.css" rel="stylesheet" />
<script type="text/javascript" src="${contextPath}/js/plupload-2.1.2/js/moxie.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/plupload-2.1.2/js/plupload.dev.js"></script>

<div id="divUploadData" class="pageContent" layoutH="0">
	<div style="padding-left:100px;padding-top:30px;">
		<div id="uploadContainer">
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
		    <a class="button" id="pickfiles"><i class="icon-search"></i><span>选择文件</span></a>
		    <a class="button" id="uploadfiles"><i class="icon-ok icon-white"></i><span>开始上传</span></a>
		</div>
		<br />
		<br />
		<br />
		<br />
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
    var memo = "";
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
            fileGroup: "",
            ny: ny
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
                	uploader.settings.multipart_params.ny = ny;
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
                            url: "/uploaddatamanage/uploaddata/import",
                            dataType: "text",
                            data: { strFileGroup: strFileGroup, "ny": ny, "template": template, "memo": memo },
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
