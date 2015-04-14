<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<style>
.contact { width:100%; background:#9BB817; height:20px; color:#FFF; font-size:11px; text-align:center; line-height:20px; margin-bottom:2px; float:left; margin-top:0px;}
</style>
<div class="down">
<a href="#">关于我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; <a href="#">联系我们</a> &nbsp;&nbsp;|&nbsp;&nbsp; 
<a href="http://wpa.qq.com/msgrd?v=3&uin=2802049717&site=qq&menu=yes" target="_blank">在线咨询&nbsp;&nbsp;<img src="/images/qq_online.gif" style="vertical-align:middle;border:none;margin-top:-5px;" /></a> &nbsp;&nbsp;|&nbsp;&nbsp; 
<a href="/members/message/toMsgCreate">留言</a> &nbsp;&nbsp; 
<shiro:guest><a href="/login?t=web">&nbsp;&nbsp; </a></shiro:guest><shiro:user><a href="/members/index">&nbsp;&nbsp; </a></shiro:user>
<div class="contact">
CFDA南方医药经济研究所  广州标点医药信息有限公司 数据中心 商务联系：13560469097 何涛 
</div>

<script language=javascript>
globalUser = '';
<shiro:user>
globalUser = '<shiro:principal/>';
</shiro:user>
</script>
<script language=javascript src="http://183.63.90.180:8001/picopcs/pcstj2.js"></script>
</div>
