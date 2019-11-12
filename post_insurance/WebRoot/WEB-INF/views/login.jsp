<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<title>中邮保险广东分公司-运营管理平台</title>
<link href="${contextPath}/styles/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/styles/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />

<!-- form验证 -->
<link rel="stylesheet" href="${contextPath}/styles/validationEngine/css/validationEngine.jquery.css" type="text/css"/>
<script src="${contextPath}/styles/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/styles/validationEngine/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="${contextPath}/styles/validationEngine/js/jquery.validationEngine-2.6.4.js" type="text/javascript" charset="utf-8"></script>
<script>
    jQuery(document).ready(function(){
        jQuery("#formID").validationEngine();
    });
    jQuery(document).ready(function(){
    	$("#captcha").click(function(){
    		$(this).attr("src", "${contextPath}/Captcha.jpg?time=" + new Date());
    		return false;
    	});
    });

function sform() {
	//alert("马上为你登录系统，可能需要十来秒~");
	$("#logMsg").hide();
	$("#loging").show();//style.display="block";
	$("#formID").submit(function(){
		$(":submit",this).attr("disabled","disabled");  
	});
	return true;
}
</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="${contextPath}/styles/dwz/themes/default/images/logo.png" />
			</h1>

			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="${contextPath}/web/index">前端网站</a></li>
						<li>&nbsp;</li>
					</ul>
				</div>
				<h2 class="login_title">请登录</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form method="post" action="${contextPath}/login" id="formID" >
					<input type="hidden" name="userType" value="admin">
					<c:if test="${msg!=null }">
						<p id="logMsg" style="color: red; margin-left: 10px;">${msg }</p>
					</c:if>
					<p id="loging" style="display:none; color: red; margin-left: 10px; font-size:12px">正在登录，请稍候……</p>
					<!-- 
					<p>
						<label>登录类型</label>
						<input type="radio" name="userType" value="member" ${userType!=null&&userType.equals("member")?"checked=\"checked\"":""}/> 连锁登录
						<input type="radio" name="userType" value="admin" ${userType!=null&&userType.equals("admin")?"checked=\"checked\"":userType==null?"checked=\"checked\"":""}/> 管理登录 
					</p>
					 -->
					<p>
						<label>用户名:</label>
						<input type="text" name="username" style="width: 150px;" title="可使用核心工号" class="validate[required] login_input" id="username" />
					</p>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;&nbsp;码:</label>
						<input type="password" name="password" style="width: 150px;" class="validate[required] login_input" id="password"/>
					</p>
					<p>
						<label>验证码:</label>
						<input type="text" id="captcha_key" style="width: 70px;float:left;" name="captcha_key" class="login_input validate[required,maxSize[4]]" size="6" />
						<span><img src="${contextPath}/Captcha.jpg" alt="点击刷新验证码" width="80" height="30" id="captcha"/></span>
					</p>
					<!-- 
					<p>
						<label>记住我:</label>
						<input type="checkbox" id="rememberMe" name="rememberMe"/>
					</p>
					 -->					
					<div class="login_bar" style="disply:block;float:left;">
						<input class="sub" type="submit" onclick="javascript:sform();" onkeydown="javascript:sform();" value=""/>
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="${contextPath}/styles/dwz/themes/default/images/login_banner.jpg" /></div>
			<div class="login_main">
				<ul class="helpList">
					<li><!-- a href="javascript:toggleBox('forgotPwd')">忘记密码?</a --></li>
				</ul>

				<div class="login_inner">
					<p>&nbsp;</p>
					<p>&nbsp;</p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2012-2014, , All Rights Reserve.
		</div>
	</div>
</body>
</html>