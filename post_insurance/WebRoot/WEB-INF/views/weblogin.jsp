<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>    
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<title>中邮保险广东分公司-运营管理平台</title>
<style>
body,div,ul,li,dl,dd,dt { margin:0; padding:0;}
li { list-style-type:none;}
.top { width:100%; height:80px; background:url(${contextPath}/images/top_bg.jpg);}
.top .top1 { width:800px; height:80px; margin: 0 auto;}
.top .top1 .logo{ margin-left:-120px;}
.top .anniu { float:right; margin-top:25px;}
.top .anniu a { float:left; margin-right:13px;}
.top .anniu a:link,.top .anniu a:visited { width:52px; height:27px; display:block; text-decoration:none; line-height:27px; text-align:center; background:url(${contextPath}/images/anniu.jpg) no-repeat; font-size:15px; font-weight:bold; color:#454545;}
.top .dao { background:url(${contextPath}/images/dao_bg.jpg); width:100%; height:56px; float:left;}
.top .dao .daon { width:800px; height:56px; margin: 0 auto; margin-left: 260px;}
.top .dao .daon ul li { float:left; height:56px; line-height:56px;}
.top .dao .daon ul .daob { background:#9BB817;}
.top .dao .daon ul .daob a { color:#FFF;}
.top .dao .daon ul li .daobian { display:block; width:8px; background:url(${contextPath}/images/dao_ce_bg.jpg) no-repeat; height:56px; float:right;}
.top .dao .daon ul li a { padding-left:30px; margin-right:30px; text-decoration:none; color:#5B5B5B; font-weight:bold; font-size:15px;}
.certen { float:left; width:100%;}
.main_visual { float:left; width:100%; height:100%; padding-bottom:0px;}
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
.certen .deng { width:549px; height:388px; background:url(${contextPath}/images/dengbg.jpg) no-repeat; margin: 30px auto; padding-top:110px;}
.certen .deng .dengju { width:308px; height:273px; margin: 0 auto; border:1px solid #DEDEDE; padding-left:20px; padding-top:12px; }
.certen .deng .dengju .dakuang { display:block; border:1px solid #666; height:38px; width:236px; border:1px solid #DEDEDE; border-left:0; margin-top:20px; outline:none; color:#B3B3B3; font-size:14px; padding-left:5px; padding-right:5px;}
.certen .deng .dengju a{  color:#65656D; font-size:13px; vertical-align:middle; text-decoration:none;}

.down { width:100%; background:#9BB817; height:44px; color:#FFF; font-size:12px; font-weight:bold; text-align:center; line-height:44px; margin-bottom:2px; float:left; margin-top:0px;}
.down a { position:relative;}
.down a:link,.down a:visited { color:white; font-size:13px; text-decoration:none;}
.STYLE1 {
	font-size: 11pt;
	font-weight: bold;
}
</style>

<script type="text/javascript" src="${contextPath}/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery.touchSlider.js"></script>
<link rel="stylesheet" href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" type="text/css"/>
<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
$(document).ready(function () {
	jQuery("#formID").validationEngine();
	$("#captcha").click(function(){
		$(this).attr("src", "${contextPath}/Captcha.jpg?time=" + new Date());
		return false;
	});
});
</script>
</head>

<body>
<div class="top">
  <div class="top1">
    	<div class="logo"><a href="/web/index"><img src="${contextPath}/images/logo.png" width="202" height="80" style="margin-top:20px;" /></a>
            <div class="anniu">
            </div>
        </div>
  </div>
<div class="dao">
	<div class="daon">
		<ul>
			<li><a href="/web/index">首页</a><span class="daobian"></span></li>
        	<li><a href="/web/todoList">待办任务</a><span class="daobian"></span></li>
            <li><a href="/web/prdList">上线产品</a><span class="daobian"></span></li>
        	<li><a href="#">常见问题</a><span class="daobian"></span></li>
            <shiro:guest>
            <li class="daob"><a href="/web/tologin">登录</a><span class="daobian"></span></li>
            </shiro:guest>
            <shiro:user>
            <li><a href="/web/logout">(<shiro:principal/>)退出</a><span class="daobian"></span></li>
            </shiro:user>
        </ul>
	</div>
</div>
</div>

<div class="certen">
  <div class="deng">
    	<div class="dengju">
    	  <form id="formID" method="post" action="${contextPath}/login">
    	    <input type="hidden" name="userType" value="admin"/>
			<input type="hidden" name="flag" value="web"/>
			<input type="hidden" name="rememberMe" value="false"/>
    	    <label for="username" style=" display:block; border:1px solid #666; height:40px; width:40px; border:1px solid #DEDEDE; border-right:0; background:url(${contextPath}/images/dengzhang.jpg) no-repeat 13px 10px; float:left;"></label>
    	    <input type="text" name="username" alt="可使用核心工号" class="textInput dakuang validate[required]" id="username"/>
  	        <label for="password" style=" margin-top:20px; display:block; border:1px solid #666; height:40px; width:40px; border:1px solid #DEDEDE; border-right:0; float:left; background:url(${contextPath}/images/dengmi.jpg) no-repeat 13px 10px;"></label>
  	        <input type="password" name="password" class="dakuang validate[required]" id="password"/>
  	        <br />
  	        <label for="captcha_key" style=" vertical-align:middle; font-size:12px; color:#9D9AA3">验证码</label>
  	        <input type="text" name="captcha_key" id="captcha_key" class="validate[required,maxSize[4]]" style=" vertical-align:middle; width:62px; padding-left:5px; outline:none;color:#B3B3B3; padding-right:5px; height:28px; border:1px solid #DEDEDE;"/>
  	        <img src="${contextPath}/Captcha.jpg" alt="点击刷新验证码" id="captcha" width="80" height="30" style=" vertical-align:middle;" />
			<br />
			<br />
    	    <input type="submit" name="Submit" value="登  录" style=" width:288px; height:37px; color:#FFF; font-weight:bold; border:0; background:#9CB816; font-size:17px;" />
			<c:if test="${msg!=null}">
				<p style="color: red; margin-left: 10px; font-size:12px">${msg }</p>
			</c:if>
    	  </form>
      </div>
      <div style=" color:#666; font-size:12px; width:510px; margin: 0 auto; margin-top:35px;">中邮保险广东分公司运营管理部提供技术支持。</div>
	</div>
</div>

<%@ include file="webroot.inc.jsp" %>
</body>
</html>
