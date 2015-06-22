<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<style>
.contact { width:100%; background:#9BB817; height:20px; color:#FFF; font-size:11px; text-align:center; line-height:20px; margin-bottom:2px; float:left; margin-top:0px;}
</style>
<div class="down">
<a href="#">关于我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a href="#">联系我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; 
<a href="#">留言</a> &nbsp;&nbsp; |&nbsp;&nbsp; 
<shiro:guest><a href="/login?t=web">后台管理登录</a></shiro:guest><shiro:user><a href="/management/index">后台管理界面 </a></shiro:user>
<div class="contact">
CHINA POST INSURANCE 中邮保险广东分公司：020-38181638 Aming（QQ:6901028）
</div>

<script language=javascript>
globalUser = '';
<shiro:user>
globalUser = '<shiro:principal/>';
</shiro:user>
</script>
</div>
