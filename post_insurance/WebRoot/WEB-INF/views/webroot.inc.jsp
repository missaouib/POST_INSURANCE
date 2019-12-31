<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<style>
.contact { width:100%; background:#9BB817; height:35px; color:#FFF; font-size:11px; text-align:center; line-height:15px; margin-bottom:1px; float:left; margin-top:0px;}
</style>
<div class="down">
<a href="#">关于我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a href="#">联系我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; 
<a href="#">留言</a> &nbsp;&nbsp; |&nbsp;&nbsp; 
<shiro:guest><a href="/login?t=web">后台管理登录</a></shiro:guest><shiro:user><a href="/management/index">后台管理界面 </a></shiro:user>
<div class="contact">
CHINA POST INSURANCE 中邮保险广东分公司 Copyright &copy; 2013-2020, All Rights Reserve.<br>(建议使用Chrome、360、QQ、搜狗等浏览器且选则极速模式)
</div>

<script language=javascript>
globalUser = '';
<shiro:user>
globalUser = '<shiro:principal/>';
</shiro:user>
</script>
</div>
